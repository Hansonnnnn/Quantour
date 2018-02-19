package data;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.dao.StockDao;
import dao.daoImpl.StockDaoImpl;
import dataService.StockDataService;
import po.StockPO;
import utility.LetterHelper;

public class StockDataServiceImpl implements StockDataService {

	private StockDao stockDaoImpl;

	public StockDataServiceImpl() {
		stockDaoImpl = new StockDaoImpl();
	}

	@Override
	public Map<Date, StockPO> getSpecificDateStock(Date start, Date end, int stockPOId) {
		Map<Date, StockPO> stockPOs = null;
		try {
			stockPOs = stockDaoImpl.findByID(stockPOId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getSpecificDateStock(start, end, stockPOs);
	}

	@Override
	public Map<Date, StockPO> getSpecificDateStock(Date start, Date end, String name) {
		Map<Date, StockPO> stockPOs = null;
		try {
			stockPOs = stockDaoImpl.findByName(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getSpecificDateStock(start, end, stockPOs);
	}

	@Override
	public Map<Integer, StockPO> getStocksByDate(Date date) {

		try {
			return stockDaoImpl.getStocksByDate(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Integer> searchStock(String nameOrCode) {
		Map<String, Integer> all = null;
		try {
			all = stockDaoImpl.getStockList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map<String, Integer> map = new LinkedHashMap<>();

		if (isNumeric(nameOrCode)) {
			for (String string : all.keySet()) {
				if (String.valueOf(all.get(string)).contains(nameOrCode)) {
					map.put(string, all.get(string));
				}
			}
		} 
		else if (isLetter(nameOrCode)) {
			LetterHelper letterHelper = new LetterHelper();
			nameOrCode = nameOrCode.toUpperCase();
			String firstLetter;
			for (String string : all.keySet()) {
				firstLetter = letterHelper.String2Alpha(string);
				if (firstLetter.contains(nameOrCode)) {
					map.put(string, all.get(string));
				}
			}
		}
		else {
			
			for(String string: all.keySet()){
				if (string.contains(nameOrCode)) {
					map.put(string, all.get(string));
				}
			}
		}

		return map;
	}

	
	
	private Map<Date, StockPO> getSpecificDateStock(Date start, Date end, Map<Date, StockPO> stockPOs) {
		if (start == null || end == null) {
			return stockPOs;
		}
		Date temp = null;
		Map<Date, StockPO> map = new LinkedHashMap<>();
		for (Iterator<Date> iterator = stockPOs.keySet().iterator(); iterator.hasNext();) {
			temp = (Date) iterator.next();
			if (temp.before(start) || temp.after(end)) {
				continue;
			} else {
				map.put(temp, stockPOs.get(temp));
			}
		}
		return map;

	}

	private boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	private boolean isLetter(String str) {
		Pattern pattern = Pattern.compile("[a-zA-Z]*");
		Matcher isLet = pattern.matcher(str);
		if (!isLet.matches()) {
			return false;
		}
		return true;
	}
	
	
}
