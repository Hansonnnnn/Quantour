package data;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import dao.dao.StockDao;
import dao.daoImpl.StockDaoImpl;
import dataService.StockDataService;
import dataService.StockStrategyService;
import po.StockPO;
import po.YieldPO;
import utility.DateHelper;

public class StockStrategyServiceImpl implements StockStrategyService {
	private StockDao stockDaoImpl;

	public StockStrategyServiceImpl(){
		stockDaoImpl = new StockDaoImpl();
	}

	
	
	@Override
	public Map<String, StockPO> getSomeStocks(Date date,ArrayList<String > stockLists){
		Map<Integer, StockPO> allStocks = stockDaoImpl.getStocksByDate(date);
		if (allStocks.size()==0) {
			return getSomeStocks(DateHelper.rollBackOneDay(date), stockLists);
		}
		Map<String, StockPO> result = new LinkedHashMap<>();
		Map<String, Integer> nameToCode = stockDaoImpl.getStockList();
		for (String string : stockLists) {
			result.put(string, allStocks.get(nameToCode.get(string)));
		}
		
		return result;
	}



	@Override
	public ArrayList<YieldPO> getYieldOfSelectsStocks(ArrayList<String> selectedLists, int Growdays, Date startDate) {
		ArrayList<YieldPO> result = new ArrayList<>();
		Date temp = startDate;
		Date temp2 = DateHelper.rollBackOneDay(startDate);
		for (int i = 0; i < Growdays; i++) {
			temp = DateHelper.rollBackOneDay(temp);
		}
		Map<Integer, StockPO> map1 = stockDaoImpl.getStocksByDate(temp);
		while(map1.size()==0){
			temp = DateHelper.rollBackOneDay(temp);
			map1 = stockDaoImpl.getStocksByDate(temp);
		}
		Map<Integer, StockPO> map2 = stockDaoImpl.getStocksByDate(temp2);
		while(map2.size()==0){
			temp = DateHelper.rollBackOneDay(temp);
			map2 = stockDaoImpl.getStocksByDate(temp);
		}
		StockPO stockPO1 = null;
		StockPO stockPO2 = null;
		for (String string : selectedLists) {
			int code = stockDaoImpl.nameToCode(string);
			stockPO1 = map1.get(code);
			stockPO2 = map2.get(code);
			if (stockPO1==null||stockPO2==null) {
				result.add(new YieldPO(string,0));
				continue;
			}
			double yield = (stockPO2.getAdjustCloseValue()-stockPO1.getOpenValue())/stockPO1.getOpenValue();
			result.add(new YieldPO(string,yield));
		}
		 
		return result;
	}



	@Override
	public Map<String, Map<Date, StockPO>> getSomeStocksOfSomeDays(Date date, int days,
			ArrayList<String> stockLists) {
		Date temp = date;
		for (int i = 0; i < days; i++) {
			temp = DateHelper.rollBackOneDay(temp);
		}
		StockDataService stockDataService = new StockDataServiceImpl();
		Map<String,  Map<Date, StockPO>> result = new LinkedHashMap<>();
		for (String string : stockLists) {
			result .put(string,stockDataService.getSpecificDateStock(temp, DateHelper.rollBackOneDay(date), string));
		}
	
		return result;
	}
	
}
