package bussinesslogic.helper;

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
import po.YieldPO;
import utility.DateHelper;
import vo.StockVO;

public class MomentumGetComparator {
	static int csi300=300;
	static String csiName="沪深300";
	static int sme=399005;
	static String smeName="中小板指";
	static int chinext=399006; 
	static String chinextName="创业板指";
	    public static  ArrayList<String> selectHighStocks(ArrayList<String> selectLists,
				int processingDays,int resultNums,Date startDate){
	    	selectLists.remove(chinextName);
	    	selectLists.remove(csiName);
	    	selectLists.remove(smeName);
			StockStrategyService stockStrategyService=new StockStrategyServiceImpl();
			//保存需要买入股票的名称
			ArrayList<String> result=new ArrayList<>();			
			//保存涨幅的list，需要排序
			ArrayList<YieldPO> resultHighLists=stockStrategyService.getYieldOfSelectsStocks(selectLists, processingDays, startDate);
			//对涨幅进行排序
			resultHighLists.sort(new Comparator<YieldPO>() {
				@Override
				public int compare(YieldPO o1, YieldPO o2) {
					if(o1.getYield()>o2.getYield()){
						return 1;
					}
					if(o1.getYield()==o2.getYield()){
					    return 0;
					}
					if(o1.getYield()<o2.getYield()){
						return -1;
					}
					return 0;
				}
			});
			//得到后resultNums位
			for(int i=1;i<=resultNums;i++){
			    result.add(resultHighLists.get(resultHighLists.size()-i).getName());
			}
			
		    return result;	
	    }
	    
		public static ArrayList<String> removeIncreaseLimitInAll(Date startDate){
	    	StockDataService stockDataService=new StockDataServiceImpl();
	    	ArrayList<String> stocksList=new ArrayList<>();
	    	Map<Integer, StockPO> allStocks= stockDataService.getStocksByDate(startDate);
	    	Map<Integer, StockPO> yesAllStocks=stockDataService.getStocksByDate(DateHelper.add(startDate, -1));
//            allStocks.remove(chinext);
//            allStocks.remove(csi300);
//            allStocks.remove(sme);
//            yesAllStocks.remove(chinext);
//            yesAllStocks.remove(csi300);
//            yesAllStocks.remove(sme);
            
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
	    
		 public static ArrayList<String > removeIncreaseLimitInPlate(StockPlate plate,Date startDate){
		    	BlockService blockService=new BlockServiceImpl();
		    	ArrayList<String> stocksList=new ArrayList<>();
		    	Map<Integer, StockVO> plateStocks=blockService.getStocksInBlock(startDate, plate);
		    	Map<Integer, StockVO> yesPlateStocks=blockService.getStocksInBlock(DateHelper.add(startDate, -1),plate);
//		    	plateStocks.remove(chinext);
//		    	plateStocks.remove(csi300);
//		    	plateStocks.remove(sme);
//		    	yesPlateStocks.remove(chinext);
//		    	yesPlateStocks.remove(csi300);
//		    	yesPlateStocks.remove(sme);
	            
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
		    
		    public static  ArrayList<String > removeIncreaseLimitInSelect(ArrayList<String> searchLists,Date startDate){
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
							|| (entry.getValue() == null)){
		    			continue;
		    		}
		    		double c=searchStocks.get(entry.getKey()).getCloseValue();
		    		double c1=entry.getValue().getCloseValue();
		    		if((!(((c-c1)*100/c1)>=(10-0.01*100/c1)))||(!((((c1-c)*100/c1)>=(10-0.01*100/c1))))){
		    			stocksList.add(entry.getValue().getName());
		    		}
		    	}
		    	
		    	return stocksList;	
		    }
		    
}
