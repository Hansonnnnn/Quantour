package bussinesslogic;

import java.text.DecimalFormat;
import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

//import java.util.LinkedHashMap;
//import java.util.Map;
//import bussinesslogic.helper.DataHelper;
import bussinesslogic.helper.AVGGetComparator;
import bussinesslogic.helper.MomentumGetComparator;
import bussinesslogic.helper.PlateProfit;
import bussinesslogic.helper.Statistics;
import bussinesslogic.helper.StrategyHelper;
import bussinesslogicservice.GetExcessEarningAndStrategyService;
import data.StockDataServiceImpl;
import data.StockStrategyServiceImpl;
import dataService.StockDataService;
import dataService.StockStrategyService;
import model.StockPlate;
import model.StrategyType;
import model.TimeType;
import po.StockPO;
//import po.StockPO;
import po.YieldPO;
import utility.DateHelper;
import vo.StrategyExcessEarningVO;
//import vo.StrategyResultVO;

public class StrategyEESBL implements GetExcessEarningAndStrategyService{
    StockDataService stockDataService=new StockDataServiceImpl();
    StockStrategyService stockStrategyService = new StockStrategyServiceImpl();
    
	@Override
	public StrategyExcessEarningVO getAllStocksEESVO(Date startDate, Date endDate, StrategyType strategyType,
			TimeType timeType, int nums, int firstDays, int iterval,int coloms ) {
		
		StrategyExcessEarningVO strategyExcessEarningVO=new StrategyExcessEarningVO();
		
		for(int i=0;i<coloms&&!DateHelper.add(startDate, i*iterval).after(endDate);i++){
			ArrayList<Double> exe=new ArrayList<>();
			
			//得到超额收益率的
			if(timeType.equals(TimeType.GrowTime)){
				exe=this.getAllstocksExcessEarning(startDate, endDate, strategyType, nums, (firstDays+i*iterval));
			}else{
				exe=this.getAllstocksExcessEarning(startDate, endDate, strategyType, firstDays+i*iterval, nums);
			}
			
			strategyExcessEarningVO.getExcessEarining().put((firstDays+i*iterval), Statistics.calculateAVGList(exe)*100);
		    
			//计算策略胜率
			double rate=0;
			for(int j=0;j<exe.size();j++){
		    	if(exe.get(j)>0){
		    		rate++;
		    	}
		    }
			
			rate=rate/exe.size()*100;
			strategyExcessEarningVO.getStrategyWinRate().put((firstDays+i*iterval), format(rate));
		}
		return strategyExcessEarningVO;
	}

	@Override
	public StrategyExcessEarningVO getStockPoolEESVO(StockPlate plate, Date startDate, Date endDate,
			StrategyType strategyType, TimeType timeType, int nums, int firstDays, int iterval,int coloms) {
        StrategyExcessEarningVO strategyExcessEarningVO=new StrategyExcessEarningVO();
		
		for(int i=0;i<coloms&&DateHelper.add(startDate, i*iterval).before(endDate);i++){
			ArrayList<Double> exe=new ArrayList<>();
			if(timeType.equals(TimeType.GrowTime)){
				exe=this.getBlocksExcessEarning(plate,startDate, endDate, strategyType, nums, (firstDays+i*iterval));
			}else{
				exe=this.getBlocksExcessEarning(plate,startDate, endDate, strategyType, firstDays+i*iterval, nums);
			}
			strategyExcessEarningVO.getExcessEarining().put((firstDays+i*iterval), Statistics.calculateAVGList(exe)*100);
		    double rate=0;
			for(int j=0;j<exe.size();j++){
		    	if(exe.get(j)>0){
		    		rate++;
		    	}
		    }
			rate=rate/exe.size()*100;
			strategyExcessEarningVO.getStrategyWinRate().put((firstDays+i*iterval), format(rate));
		}
		return strategyExcessEarningVO;
		
	}

	@Override
	public StrategyExcessEarningVO getUserStocksEESVO(ArrayList<String> selectedStocks,
			Date startDate, Date endDate, StrategyType strategyType, TimeType timeType, int nums, int firstDays,
			int iterval,int coloms) {
        StrategyExcessEarningVO strategyExcessEarningVO=new StrategyExcessEarningVO();
		
		for(int i=0;i<coloms&&DateHelper.add(startDate, i*iterval).before(endDate);i++){
			ArrayList<Double> exe;
			if(timeType.equals(TimeType.GrowTime)){
				exe=this.getSomeStocksExcessEarning(selectedStocks,startDate, endDate, strategyType, nums, (firstDays+i*iterval));
			}else{
				exe=this.getSomeStocksExcessEarning(selectedStocks,startDate, endDate, strategyType, firstDays+i*iterval, nums);
			}
			strategyExcessEarningVO.getExcessEarining().put((firstDays+i*iterval), Statistics.calculateAVGList(exe)*100);
		    double rate=0;
			for(int j=0;j<exe.size();j++){
		    	if(exe.get(j)>0){
		    		rate++;
		    	}
		    }
			rate=rate/exe.size()*100;
			strategyExcessEarningVO.getStrategyWinRate().put((firstDays+i*iterval), format(rate));
		}
		return strategyExcessEarningVO;
	}
	
	private ArrayList<Double> getAllstocksExcessEarning(Date startDate,Date endDate,StrategyType strategyType,
			int processDays,int holdDays){
		StockDataService service=new StockDataServiceImpl(); 
		ArrayList<Double> superRate=new ArrayList<>();
        Date lastDate=startDate;
		
        while (startDate.before(endDate)){	
			
			//加日期
            if(!DateHelper.add(startDate, holdDays).after(endDate)){
            	startDate=DateHelper.add(startDate, holdDays);
            }else{
            	startDate=DateHelper.add(endDate, 1);
            }
            
			ArrayList<String> limitDown=new ArrayList<>();//跌停的股票
			ArrayList<String> stockList=new ArrayList<>();//等待买入的股票
			ArrayList<String> baseLists=new ArrayList<>();//基准股			

			stockList=StrategyHelper.getGreastSttocksInAll(lastDate, processDays, strategyType);
			//计算持有期内的收益
            ArrayList<YieldPO> getProfitRate=stockStrategyService.getYieldOfSelectsStocks(stockList, holdDays, startDate);
            double profitRate=0;
            for(int i=0;i<getProfitRate.size();i++){
            	profitRate+=getProfitRate.get(i).getYield();
            }
            profitRate/=getProfitRate.size();
            
            //计算每个持有期的基准的收益率
            Date lDate=new Date();
            lDate=lastDate;
            Map<Integer, StockPO> allList=new LinkedHashMap<>();
            		
            while(allList.size()==0){
            	allList=service.getStocksByDate(lDate);
            	lDate=DateHelper.add(lDate, 1);
            }
            for(Map.Entry<Integer, StockPO> entry : allList.entrySet()){
            	baseLists.add(entry.getValue().getName());
            }
//            baseLists=MomentumGetComparator.removeIncreaseLimitInAll(lastDate);
//            Date ldDate=new Date();
//            ldDate=lastDate;
//            while(baseLists.size()==0){
//            	ldDate=DateHelper.add(ldDate, 1);
//            	baseLists=MomentumGetComparator.removeIncreaseLimitInAll(ldDate);
//            }
            ArrayList<YieldPO> baseProfit=stockStrategyService.getYieldOfSelectsStocks(baseLists, holdDays,startDate);
		    double baseProfitRate=0.0;
            for(int i=0;i<baseProfit.size();i++){
		    	baseProfitRate+=baseProfit.get(i).getYield();
		    }
            baseProfitRate/=baseProfit.size();
            
            superRate.add(format(profitRate-baseProfitRate));
            lastDate=startDate;
		}	
		return superRate;
	}
	
	private ArrayList<Double> getBlocksExcessEarning(StockPlate plate,Date startDate,Date endDate,StrategyType strategyType,
			int processDays,int holdDays){
		ArrayList<Double> superRate=new ArrayList<>();
        Date lastDate=startDate;
		while (startDate.before(endDate)){	
			//加日期
			
            if(!DateHelper.add(startDate, holdDays).after(endDate)){
            	startDate=DateHelper.add(startDate, holdDays);
            }else{
            	startDate=DateHelper.add(endDate, 1);
            }

            ArrayList<String> limitDown=new ArrayList<>();//跌停的股票
			ArrayList<String> stockList=new ArrayList<>();//等待买入的股票
			ArrayList<String> baseLists=new ArrayList<>();//基准股票

			stockList=StrategyHelper.getGreastSttocksInPlate(lastDate, processDays, strategyType, plate);
			//计算持有期内的收益
            ArrayList<YieldPO> getProfitRate=stockStrategyService.getYieldOfSelectsStocks(stockList, holdDays,startDate);
            double profitRate=0;
            for(int i=0;i<getProfitRate.size();i++){
            	profitRate+=getProfitRate.get(i).getYield();
            }
            profitRate/=getProfitRate.size();
            
            
//            //计算每个持有期的基准的收益率
//            
//            baseLists=MomentumGetComparator.removeIncreaseLimitInPlate(plate,lastDate);
//            ArrayList<YieldPO> baseProfit=stockStrategyService.getYieldOfSelectsStocks(baseLists, holdDays,startDate);
		    double baseProfitRate=0.0;
//            for(int i=0;i<baseProfit.size();i++){
//		    	baseProfitRate+=baseProfit.get(i).getYield();
//		    }
//            baseProfitRate/=baseProfit.size();
            baseProfitRate=PlateProfit.getPlateProfit(startDate, plate, holdDays);            
            superRate.add(format(profitRate-baseProfitRate));
            
            lastDate=startDate;
		}	
		return superRate;	
	}
	
	private ArrayList<Double> getSomeStocksExcessEarning(ArrayList<String> selectLists,Date startDate,Date endDate,StrategyType strategyType,
			int processDays,int holdDays){
		StockDataService service=new StockDataServiceImpl();
		ArrayList<Double> superRate=new ArrayList<>();

		Date lastDate=startDate;
		while (startDate.before(endDate)){
			//加日期
            if(!DateHelper.add(startDate, holdDays).after(endDate)){
            	startDate=DateHelper.add(startDate, holdDays);
            }else{
            	startDate=DateHelper.add(endDate, 1);
            }
			ArrayList<String> limitDown=new ArrayList<>();//跌停的股票
			ArrayList<String> stockList=new ArrayList<>();//等待买入的股票
			ArrayList<String> baseLists=new ArrayList<>();//基准股票

			stockList=StrategyHelper.getGreastSttocksInSelect(lastDate, processDays, strategyType, selectLists);
			//计算持有期内的收益
            ArrayList<YieldPO> getProfitRate=stockStrategyService.getYieldOfSelectsStocks(stockList, holdDays, startDate);
            double profitRate=0;
            for(int i=0;i<getProfitRate.size();i++){
            	profitRate+=getProfitRate.get(i).getYield();
            }
            profitRate/=getProfitRate.size();
            
            //计算每个持有期的基准的收益率
            
//            baseLists=MomentumGetComparator.removeIncreaseLimitInSelect(selectLists,lastDate);
           
//            Date lDate=new Date();
//            lDate=lastDate;
//            while(baseLists.size()==0){
//            	lDate=DateHelper.add(lDate, 1);
//            	baseLists=MomentumGetComparator.removeIncreaseLimitInSelect(selectLists,lastDate);
//            }
            
            ArrayList<YieldPO> baseProfit=stockStrategyService.getYieldOfSelectsStocks(selectLists, holdDays, startDate);
		    double baseProfitRate=0.0;
            for(int i=0;i<baseProfit.size();i++){
		    	baseProfitRate+=baseProfit.get(i).getYield();
		    }
            
            baseProfitRate/=baseProfit.size();
            
            superRate.add(format(profitRate-baseProfitRate));
            
            lastDate=startDate;
		}	
		return superRate;
	}
	// 格式化浮点数结果
	private double format(double d) {
		DecimalFormat dFormat=new DecimalFormat("#.00");
		String string1=dFormat.format(d);
		return Double .parseDouble(string1);
	}
}
