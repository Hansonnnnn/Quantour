package bussinesslogic;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.SimpleFormatter;

import bussinesslogic.helper.AVGGetComparator;
import bussinesslogic.helper.MomentumGetComparator;
import bussinesslogic.helper.StrategyHelper;
import bussinesslogicservice.ColumnDiagramService;
import data.StockStrategyServiceImpl;
import dataService.StockStrategyService;
import model.StockPlate;
import model.StrategyType;
import po.YieldPO;
import utility.DateHelper;
import vo.ColumnDiagramVO;
import vo.TrolleyVO;

public class ColumnDiagramServiceImpl implements ColumnDiagramService{

	@Override
	public ColumnDiagramVO getDataWithinAllStocks(Date startDate, Date endDate, int becomingDays, int possessingDays,
			StrategyType type) {
		StockStrategyService service=new StockStrategyServiceImpl();
		int  maxProfit=0;
//        int  neMaxProfit=0;
        
		//初始化，得到累计和每日的收益率 
		ColumnDiagramVO columnDiagramVO=new ColumnDiagramVO();
		Map<Integer, Integer>peData=new LinkedHashMap<>();
		Map<Integer , Integer>neData=new LinkedHashMap<>();
		int peCircleNum=0;
		int neCircleNum=0;
		
		Date lastDate=new Date();
		lastDate=startDate;
		while (startDate.before(endDate)){	
			ArrayList<String> limitDown=new ArrayList<>();//跌停的股票
			ArrayList<String> stockList=new ArrayList<>();//等待买入的股票
			
			//加日期
            if(!DateHelper.add(startDate, possessingDays).after(endDate)){
            	startDate=DateHelper.add(startDate, possessingDays);
            }else{
            	startDate=DateHelper.add(endDate, 1);
            }

			stockList=StrategyHelper.getGreastSttocksInAll(lastDate,becomingDays, type);
			//计算持有期内的收益
            ArrayList<YieldPO> getProfitRate=service.getYieldOfSelectsStocks(stockList, possessingDays, startDate);
            
            //收益率
            double profitRate=0;
            for(int i=0;i<getProfitRate.size();i++){
            	profitRate+=getProfitRate.get(i).getYield();
            }
            profitRate/=getProfitRate.size();
            
            profitRate = format(profitRate);
            if(profitRate>0){
            	profitRate+=0.01;
            }else{
            	profitRate-=0.01;
            }
            int key=(int)(profitRate*100);
//            if(key>=0){
//            	key++;
//            }
//            if(key<0){
//            	key--;
//            }
//           
            if(Math.abs(key)>maxProfit){
            	maxProfit=Math.abs(key);
            }
//            if(neMaxProfit<key){
//            	neMaxProfit=key;
//            }
            
            //添加到结果里
            if(profitRate>=0){
            	peCircleNum++;
            	if(peData.containsKey(key)){
            		peData.put(key, peData.get(key)+1);
            	}else{
            		peData.put(key, 1);
            	}
            }else{
            	neCircleNum++;
            	if(neData.containsKey(key)){
            		neData.put(key, neData.get(key)+1);
            	}else{
            		neData.put(key, 1);
            	}
            }
              
            lastDate=startDate;
		}
		
		for(int i=1;i<maxProfit;i++){
			if(!peData.containsKey(i)){
				peData.put(i, 0);
			}
			if(!neData.containsKey(-i)){
				neData.put(-i, 0);
			}
		}
//		for(int i=-1;i>neMaxProfit;i--){
//			if(!neData.containsKey(i)){
//				neData.put(i, 0);
//			}
//		}
		
		neData=this.getOrder(neData);
		peData=this.getOrder(peData);
		//设置值
		columnDiagramVO.setNeCircleNum(neCircleNum);
		columnDiagramVO.setNeData(neData);
		columnDiagramVO.setPeCircleNum(peCircleNum);
		columnDiagramVO.setPeData(peData);
		columnDiagramVO.setWinRate(divideInt(peCircleNum,(peCircleNum+neCircleNum)));
//		for(Map.Entry<Integer, Integer> entry : peData.entrySet()){
//			System.out.println("entry.key="+entry.getKey()+"---"+"entry.value="+entry.getValue());
//		}
		return columnDiagramVO;
	}

	@Override
	public ColumnDiagramVO getDataWithinPlates(Date startDate, Date endDate, int becomingDays, int possessingDays,
			StockPlate plate, StrategyType type) {
        StockStrategyService service=new StockStrategyServiceImpl();
        int  maxProfit=0;
//        int  neMaxProfit=0;
        
        //初始化，得到累计和每日的收益率 
		ColumnDiagramVO columnDiagramVO=new ColumnDiagramVO();
		Map<Integer, Integer>peData=new LinkedHashMap<>();
		Map<Integer , Integer>neData=new LinkedHashMap<>();
		int peCircleNum=0;
		int neCircleNum=0;
		
		Date lastDate=new Date();
		lastDate=startDate;
		while (startDate.before(endDate)){	
			ArrayList<String> limitDown=new ArrayList<>();//跌停的股票
			ArrayList<String> stockList=new ArrayList<>();//等待买入的股票
			
			//加日期
            if(!DateHelper.add(startDate, possessingDays).after(endDate)){
            	startDate=DateHelper.add(startDate, possessingDays);
            }else{
            	startDate=DateHelper.add(endDate, 1);
            }
            
            stockList=StrategyHelper.getGreastSttocksInPlate(lastDate, becomingDays, type, plate);
			//计算持有期内的收益
            ArrayList<YieldPO> getProfitRate=service.getYieldOfSelectsStocks(stockList, possessingDays, startDate);

            double profitRate=0;
            for(int i=0;i<getProfitRate.size();i++){
            	profitRate+=getProfitRate.get(i).getYield();
            }
            profitRate/=getProfitRate.size();         
           	
            
            profitRate = format(profitRate);
            if(profitRate>0){
            	profitRate+=0.01;
            }else{
            	profitRate-=0.01;
            }
            int key=(int)(profitRate*100);
    
            if(Math.abs(key)>maxProfit){
            	maxProfit=Math.abs(key);
            }
            
                      //添加到结果里
            if(profitRate>=0){
            	peCircleNum++;
            	if(peData.containsKey(key)){
            		peData.put(key, peData.get(key)+1);
            	}else{
            		peData.put(key, 1);
            	}
            }else{
            	neCircleNum++;
            	if(neData.containsKey(key)){
            		neData.put(key, neData.get(key)+1);
            	}else{
            		neData.put(key, 1);
            	}
            }
            
           lastDate=startDate;
		}
		
		for(int i=1;i<=maxProfit;i++){
			if(!peData.containsKey(i)){
				peData.put(i, 0);
			}
			if(!neData.containsKey(-i)){
				neData.put(-i, 0);
			}
		}
		neData=this.getOrder(neData);
		peData=this.getOrder(peData);
		
		//设置值
		columnDiagramVO.setNeCircleNum(neCircleNum);
		columnDiagramVO.setNeData(neData);
		columnDiagramVO.setPeCircleNum(peCircleNum);
		columnDiagramVO.setPeData(peData);
		columnDiagramVO.setWinRate(divideInt(peCircleNum,(peCircleNum+neCircleNum)));
		
		return columnDiagramVO;
	}

	@Override
	public ColumnDiagramVO getDataWithinSomeStocks(Date startDate, Date endDate, int becomingDays, int possessingDays,
			ArrayList<TrolleyVO> trolleyVOS, StrategyType type) {
		int  maxProfit=0;
//        int  neMaxProfit=0;
        
        StockStrategyService service=new StockStrategyServiceImpl();
		ArrayList<String> selectStocks=new ArrayList<>();
		for(int i=0;i<trolleyVOS.size();i++){
			selectStocks.add(trolleyVOS.get(i).getName());
		}
        //初始化，得到累计和每日的收益率 
		ColumnDiagramVO columnDiagramVO=new ColumnDiagramVO();
		Map<Integer, Integer>peData=new LinkedHashMap<>();
		Map<Integer , Integer>neData=new LinkedHashMap<>();
		int peCircleNum=0;
		int neCircleNum=0;
		
		Date lastDate=new Date();
		lastDate=startDate;
		while (startDate.before(endDate)){	
			ArrayList<String> limitDown=new ArrayList<>();//跌停的股票
			ArrayList<String> stockList=new ArrayList<>();//等待买入的股票
			
			//加日期
            if(!DateHelper.add(startDate, possessingDays).after(endDate)){
            	startDate=DateHelper.add(startDate, possessingDays);
            }else{
            	startDate=DateHelper.add(endDate, 1);
            }
            
			stockList=StrategyHelper.getGreastSttocksInSelect(lastDate, becomingDays, type, selectStocks);
			//计算持有期内的收益
            ArrayList<YieldPO> getProfitRate=service.getYieldOfSelectsStocks(stockList, possessingDays, startDate);
           
            double profitRate=0;
            for(int i=0;i<getProfitRate.size();i++){
            	profitRate+=getProfitRate.get(i).getYield();
            }
            profitRate/=getProfitRate.size();
            
            profitRate = format(profitRate);
            if(profitRate>0){
            	profitRate+=0.01;
            }else{
            	profitRate-=0.01;
            }
            int key=((int)(profitRate*100));
//            if(key>=0){
//            	key++;
//            }
//            if(key<0){
//            	key--;
//            }
            if(Math.abs(key)>maxProfit){
            	maxProfit=Math.abs(key);
            }
            
            //添加到结果里
            if(profitRate>=0){
            	peCircleNum++;
            	if(peData.containsKey(key)){
            		peData.put(key, peData.get(key)+1);
            	}else{
            		peData.put(key, 1);
            	}
            }else{
            	neCircleNum++;
            	if(neData.containsKey(key)){
            		neData.put(key, neData.get(key)+1);
            	}else{
            		neData.put(key, 1);
            	}
            }
            lastDate=startDate;
		}
		for(int i=1;i<=maxProfit;i++){
			if(!peData.containsKey(i)){
				peData.put(i, 0);
			}
			if(!neData.containsKey(-i)){
				neData.put(-i, 0);
			}
		}
		neData=this.getOrder(neData);
		peData=this.getOrder(peData);
		
		//设置值
		columnDiagramVO.setNeCircleNum(neCircleNum);
		columnDiagramVO.setNeData(neData);
		columnDiagramVO.setPeCircleNum(peCircleNum);
		columnDiagramVO.setPeData(peData);
		columnDiagramVO.setWinRate(divideInt(peCircleNum,(peCircleNum+neCircleNum)));
		return columnDiagramVO;
	}
			// 格式化浮点数结果
			private double format(double d) {
				DecimalFormat dFormat=new DecimalFormat("#.0000");
				String string1=dFormat.format(d);
				return Double .parseDouble(string1);
			}
			
			private double divideInt(int a,int b){
				double a1=a;
				double b1=b;
				double c=format(a1/b1);
				return c;
			}
			
	private Map<Integer, Integer> getOrder(Map<Integer, Integer> map){
		List<Map.Entry<Integer, Integer>> infoIds =new ArrayList<Map.Entry<Integer, Integer>>(map.entrySet());
		
		//排序
		Collections.sort(infoIds, new Comparator<Map.Entry<Integer, Integer>>() {   
		    public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {      		    	
		    	if (Math.abs(o1.getKey()) == Math.abs(o2.getKey())){
		    		return 0;
		    	}else{
		    		return  Math.abs(o1.getKey()) - Math.abs(o2.getKey());
		    	}
		    }
		}); 
		
        /*转换成新map输出*/
		LinkedHashMap<Integer, Integer> newMap = new LinkedHashMap <Integer, Integer>();
		
		for(Map.Entry<Integer,Integer> entity : infoIds){
			newMap.put(entity.getKey(), entity.getValue());
		}
		
		return newMap;
	}

}

