package bussinesslogic.helper;

import java.util.ArrayList;
import java.util.Date;

import model.StockPlate;
import model.StrategyType;
import utility.DateHelper;

public class StrategyHelper {
	
	/**
	 * 
	 * @param lastDate 从这个日期之前开始回测，不包括这一天
	 * @param growDays 形成期
	 * @param type 测率类型
	 * @return 返回需要购买的股票的name
	 */
    public static  ArrayList<String> getGreastSttocksInAll(Date lastDate,int growDays,StrategyType type){
    	Date tmpDate=new Date();
    	tmpDate=lastDate;
    	ArrayList<String> stockList=new ArrayList<>();//需要购买的股票集合
    	ArrayList<String > limitList=new ArrayList<>();//涨/跌停的股票
    	
    	//动量策略
    	if(type.equals(StrategyType.momentumDriven)){
			stockList=MomentumGetComparator.removeIncreaseLimitInAll(tmpDate);
			//如果这一天是休息日就重新选
			while(stockList.size()==0){
				tmpDate=DateHelper.add(tmpDate, 1);
				stockList=MomentumGetComparator.removeIncreaseLimitInAll(tmpDate);
			}
			int nums=(int)stockList.size()/5;
			if(stockList.size()%5!=0)
				nums++;	
			//筛选前面的股票
			stockList=MomentumGetComparator.selectHighStocks(stockList, growDays, nums-limitList.size(), tmpDate);
		
		//均值回归策略
		}else{
			//得到所有能买股票
			stockList=AVGGetComparator.removeLimitInAll(tmpDate);
			//日期
			while(stockList.size()==0){
				tmpDate=DateHelper.add(tmpDate, 1);
				stockList=AVGGetComparator.removeLimitInAll(tmpDate);
			}
			int nums=(int)stockList.size()/5;
			stockList=AVGGetComparator.selectHighStocks(stockList, growDays, nums, tmpDate);
		}
    	return stockList;
    }
    
    public static  ArrayList<String> getGreastSttocksInPlate(Date lastDate,int growDays,StrategyType type
    		,StockPlate plate){
    	Date tmpDate=new Date();
    	tmpDate=lastDate;
    	ArrayList<String> stockList=new ArrayList<>();
    	ArrayList<String > limitList=new ArrayList<>();
    	
    	if(type.equals(StrategyType.momentumDriven)){
			stockList=MomentumGetComparator.removeIncreaseLimitInPlate(plate, tmpDate);
			while(stockList.size()==0){
				tmpDate=DateHelper.add(tmpDate, 1);
				stockList=MomentumGetComparator.removeIncreaseLimitInPlate(plate, tmpDate);
			}
			int nums=(int)stockList.size()/5;
			if(stockList.size()%5!=0)
				nums++;	
			stockList=MomentumGetComparator.selectHighStocks(stockList, growDays, nums-limitList.size(), tmpDate);
		
		//均值回归策略
		}else{
			stockList=AVGGetComparator.removeLimitInPlate(plate, tmpDate);
			while(stockList.size()==0){
				tmpDate=DateHelper.add(tmpDate, 1);
				stockList=AVGGetComparator.removeLimitInPlate(plate, tmpDate);
			}
			int nums=(int)stockList.size()/5;
			stockList=AVGGetComparator.selectHighStocks(stockList, growDays, nums, tmpDate);
		}
    	return stockList;
    }
    
    public static  ArrayList<String> getGreastSttocksInSelect(Date lastDate,int growDays,StrategyType type
    		,ArrayList<String > selectLists){
    	Date tmpDate=new Date();
    	tmpDate=lastDate;
    	ArrayList<String> stockList=new ArrayList<>();
    	ArrayList<String > limitList=new ArrayList<>();
    	if(type.equals(StrategyType.momentumDriven)){
			stockList=MomentumGetComparator.removeIncreaseLimitInSelect(selectLists, tmpDate);
			while(stockList.size()==0){
				tmpDate=DateHelper.add(tmpDate, 1);
				stockList=MomentumGetComparator.removeIncreaseLimitInSelect(selectLists, tmpDate);
			}
			int nums=(int)stockList.size()/5;
			if(stockList.size()%5!=0)
				nums++;	
			stockList=MomentumGetComparator.selectHighStocks(stockList, growDays, nums-limitList.size(), tmpDate);
		
		//均值回归策略
		}else{
			stockList=AVGGetComparator.removeLimitInSelect(selectLists, tmpDate);
			while(stockList.size()==0){
				tmpDate=DateHelper.add(tmpDate, 1);
				stockList=AVGGetComparator.removeLimitInSelect(selectLists, tmpDate);
			}
			int nums=(int)stockList.size()/5;
			stockList=AVGGetComparator.selectHighStocks(stockList, growDays, nums, tmpDate);
		}
    	return stockList;
    }
}
