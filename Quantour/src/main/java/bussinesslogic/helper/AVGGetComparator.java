package bussinesslogic.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

import bussinesslogic.BlockServiceImpl;
import bussinesslogicservice.BlockService;
import data.StockDataServiceImpl;
import data.StockStrategyServiceImpl;
import dataService.StockDataService;
import dataService.StockStrategyService;
import model.StockPlate;
import po.StockPO;
import utility.DateHelper;
import vo.AVGLineValueVO;
import vo.StockVO;

public class AVGGetComparator {
	static int csi300=300;
	static String csiName="沪深300";
	static int sme=399005;
	static String smeName="中小板指";
	static int chinext=399006; 
	static String chinextName="创业板指";
	
	public static ArrayList<String> selectHighStocks(ArrayList<String> selectLists,
			int processingDays,int resultNums,Date startDate){
		selectLists.remove(chinextName);
    	selectLists.remove(csiName);
    	selectLists.remove(smeName);
		StockStrategyService stockStrategyService=new StockStrategyServiceImpl();
        
		//当日的股票信息		
		Map<String , StockPO> todayValue=stockStrategyService.getSomeStocks(startDate, selectLists);
		//一段时间内所有的股票信息
		Map<String, Map<Date, StockPO>> holdLists=stockStrategyService.getSomeStocksOfSomeDays(startDate, processingDays, selectLists);
		//需要进行排序的list
		ArrayList<AVGLineValueVO> resultHighLists=new ArrayList<>();
		//保存结果
		ArrayList<String> result=new ArrayList<>();
		
		//遍历所有的备选股票信息，得到保存均值的VO
		for(Map.Entry<String, Map<Date, StockPO>> entry:holdLists.entrySet()){
			double avg=0;
			Map<Date , StockPO> tMap=entry.getValue();
			for(Map.Entry<Date, StockPO> entry1:tMap.entrySet()){
				    avg=avg+entry1.getValue().getAdjustCloseValue();
			}
			AVGLineValueVO avg1=new AVGLineValueVO(entry.getKey(), avg/tMap.size(), todayValue.get(entry.getKey()).getAdjustCloseValue()); 
	        resultHighLists.add(avg1);
		}
		//对均值VO进行排序
		resultHighLists.sort(new Comparator<AVGLineValueVO>() {

			@Override
			public int compare(AVGLineValueVO avg1, AVGLineValueVO avg2) {
				if(format((avg1.getAVGValue()-avg1.getTodayValue())/avg1.getAVGValue())>
				format((avg2.getAVGValue()-avg2.getTodayValue())/avg2.getAVGValue())){
					return 1;
				}
				if(format((avg1.getAVGValue()-avg1.getTodayValue())/avg1.getAVGValue())==
				format((avg2.getAVGValue()-avg2.getTodayValue())/avg2.getAVGValue())){
					return 0;
				}
				return -1;
			}
		        	
		});
		//得到后resultNums位
		for(int i=1;i<=resultNums;i++){
		    result.add(resultHighLists.get(resultHighLists.size()-i).getName());
		}
		
		return result;
	}
	
	public static ArrayList<String> removeLimitInAll(Date startDate){
    	StockDataService stockDataService=new StockDataServiceImpl();
    	ArrayList<String> stocksList=new ArrayList<>();
    	Map<Integer, StockPO> allStocks= stockDataService.getStocksByDate(startDate);
    	Map<Integer, StockPO> yesAllStocks=stockDataService.getStocksByDate(DateHelper.add(startDate, -1));
    	
    	//获得没有涨停没有跌停的股票名称
    	for(Map.Entry<Integer, StockPO> entry:yesAllStocks.entrySet()){
    		if(!allStocks.containsKey(entry.getKey())){
    			continue;
    		}
    		double c=allStocks.get(entry.getKey()).getAdjustCloseValue();
    		double c1=entry.getValue().getAdjustCloseValue();
    		if((!(((c-c1)*100/c1)>=(10-0.01*100/c1)))||(!((((c1-c)*100/c1)>=(10-0.01*100/c1))))){
    			stocksList.add(entry.getValue().getName());
    		}
    	}
    	return stocksList;
    }
    
    public static ArrayList<String > removeLimitInPlate(StockPlate plate,Date startDate){
    	BlockService blockService=new BlockServiceImpl();
    	ArrayList<String> stocksList=new ArrayList<>();
    	Map<Integer, StockVO> plateStocks=blockService.getStocksInBlock(startDate, plate);
    	Map<Integer, StockVO> yesPlateStocks=blockService.getStocksInBlock(DateHelper.add(startDate, -1),plate);
    	
    	//获得没有涨停没有跌停的股票名称
    	for(Map.Entry<Integer, StockVO> entry:yesPlateStocks.entrySet()){
    		if(!plateStocks.containsKey(entry.getKey())){
    			continue;
    		}
    		double c=plateStocks.get(entry.getKey()).getCloseValue();
    		double c1=entry.getValue().getCloseValue();
    		if((!(((c-c1)*100/c1)>=(10-0.01*100/c1)))||(!((((c1-c)*100/c1)>=(10-0.01*100/c1))))){
    			stocksList.add(entry.getValue().getName());
    		}
    	}
    	
    	return stocksList;
    }
    
    public static  ArrayList<String > removeLimitInSelect(ArrayList<String> searchLists,Date startDate){
		StockStrategyService stockStrategyService=new StockStrategyServiceImpl();
		ArrayList<String> stocksList=new ArrayList<>();
		Map<String, StockPO> searchStocks=stockStrategyService.getSomeStocks(startDate, searchLists);
		Map<String, StockPO> yesSearchStocks=stockStrategyService.getSomeStocks(DateHelper.add(startDate, -1),searchLists);
//		    	searchStocks.remove(chinext);
//		    	searchStocks.remove(csi300);
//		    	searchStocks.remove(sme);
//		    	yesSearchStocks.remove(chinext);
//		    	yesSearchStocks.remove(csi300);
//		    	yesSearchStocks.remove(sme);
		//获得没有涨停没有跌停的股票名称
		for(Map.Entry<String, StockPO> entry:yesSearchStocks.entrySet()){
			if((!searchStocks.containsKey(entry.getKey())) || searchStocks.get(entry.getKey()) == null
				||  entry.getValue() == null){
				continue;
			}
			double c=searchStocks.get(entry.getKey()).getCloseValue();
			double c1=entry.getValue().getCloseValue();
			if((!(((c-c1)*100/c1)>=(10-0.01*100/c1)))||(!((((c1-c)*100/c1)>=(10-0.01*100/c1))))){
				stocksList.add(entry.getValue().getName());
			}
		}

		return stocksList;
//    	StockStrategyService stockStrategyService=new StockStrategyServiceImpl();
//    	ArrayList<String> stocksList=new ArrayList<>();
//    	Map<String, StockPO> searchStocks=stockStrategyService.getSomeStocks(startDate, searchLists);
//    	Map<String, StockPO> yesSearchStocks=stockStrategyService.getSomeStocks(DateHelper.add(startDate, -1),searchLists);
//
//    	//获得没有涨停没有跌停的股票名称
//    	for(Map.Entry<String, StockPO> entry:yesSearchStocks.entrySet()){
//			if((!searchStocks.containsKey(entry.getKey())) || searchStocks.get(entry.getKey()) == null
//					|| (entry.getValue() == null)){
//				continue;
//			}
//			System.out.println("s     ");
//    		double c=searchStocks.get(entry.getKey()).getCloseValue();
//			System.out.println("r    ");
//    		double c1=entry.getValue().getCloseValue();
//			System.out.println("g     ");
//    		if((!(((c-c1)*100/c1)>=(10-0.01*100/c1)))||(!((((c1-c)*100/c1)>=(10-0.01*100/c1))))){
//    			stocksList.add(entry.getValue().getName());
//    		}
//    	}
//
//    	return stocksList;
    }
 // 格式化浮点数结果
 	private static double format(double d) {
 		BigDecimal b = new BigDecimal(d);
 		double result = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
 		return result;
 	}
}
