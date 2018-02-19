package dao.daoImpl;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.DateHelper;
import dao.connection.TxtConnection;
import dao.dao.StockDao;
import model.StockPlate;
import po.StockPO;
import utility.PlateHelper;

/**
 * @author 凡
 *
 */
public class StockDaoImpl implements StockDao{

	public Map<Integer,LinkedHashMap<Date, StockPO>> CSI300stockPOs = null;
	public Map<Integer,LinkedHashMap<Date, StockPO>> SMEstockPOs = null;
	public Map<Integer,LinkedHashMap<Date, StockPO>> CHINEXTstockPOs = null;
	public Map<StockPlate,Map<Integer,LinkedHashMap<Date, StockPO>>> plates = null;
	private Map<String , Integer> nameToCode;
	
	public StockDaoImpl() {
		new TxtConnection();
		CSI300stockPOs = TxtConnection.CSI300stockPOs;
		SMEstockPOs = TxtConnection.SMEstockPOs;
		CHINEXTstockPOs = TxtConnection.CHINEXTstockPOs;
		plates = new LinkedHashMap<>();
		plates.put(StockPlate.CSI300, CSI300stockPOs);
		plates.put(StockPlate.SME, SMEstockPOs);
		plates.put(StockPlate.CHINEXT, CHINEXTstockPOs);
		nameToCode = TxtConnection.nameToCode;
	}
	
	@Override
	public boolean insert(StockPO stockPO)  {
		
		
		 
		return false;
	}


	@Override
	public List<StockPO> getStockPOData(String hql)  {
		
		
		 
		return null;
	}
	@Override
	public Map<Date, StockPO> findByID(int stockPOId)  {
		/*Map<Date, StockPO> stockPOs = new LinkedHashMap<>();
		for (StockPO stockPO : stocks) {
			if (stockPO.getCode()==stockPOId) {
				stockPOs.put(stockPO.getDate(), stockPO);
			}
		}
		return stockPOs;*/
		Map<Integer, LinkedHashMap<Date, StockPO>> stockPOs = plates.get(PlateHelper.codeToPlate(stockPOId));
		return stockPOs.get(stockPOId);
	}
	@Override
	public Map<Date, StockPO> findByName(String name)  {
		/*Map<Date, StockPO> stockPOs = new LinkedHashMap<>();
		for (StockPO stockPO : stocks) {
			if (stockPO.getName().equals(name)) {
				stockPOs.put(stockPO.getDate(), stockPO);
			}
		}
		return stockPOs;
		*/
		int code = 0;
		try {
			code = nameToCode.get(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(name);
		}
		return findByID(code);
	}
	@Override
	public Map<Integer, StockPO> getStocksByDate(Date date){
		Map<Integer, StockPO> stocks = new LinkedHashMap<>();
		LocalDate localDate1 = DateHelper.dateToLocalDate(date);  
		LocalDate localDate2 ;
		for (Map<Integer, LinkedHashMap<Date, StockPO>> stockPOs: plates.values()) {
			for (Map<Date, StockPO> aStock : stockPOs.values()) {
				for (Date date2 : aStock.keySet()) {
					localDate2 = DateHelper.dateToLocalDate(date2);
					if (localDate1.equals(localDate2)) {
						stocks.put(aStock.get(date2).getCode(),aStock.get(date2));
						break;
					}
				}
			}
		}
		
		/*for (StockPO stockPO : stocks) {
			localDate2 = DateHelper.dateToLocalDate(stockPO.getDate());
			if (localDate1.equals(localDate2)) {
				stockPOs.put(stockPO.getCode(), stockPO);
			}
		}*/
	/*	for (StockPO stockPO : stocks) {
	
			if (sameDate(date, stockPO.getDate())) {
				stockPOs.put(stockPO.getCode(), stockPO);
			}
		}*/
		
		return stocks;
		
	}

	@Override
	public Map<String, Integer> getStockList()  {
		
		return nameToCode;
	}

	@Override
	public Map<Integer, StockPO> getStocksByDateAndPlate(Date date, StockPlate plate) {
		Map<Integer, StockPO> stocks = new LinkedHashMap<>();
		Map<Integer, LinkedHashMap<Date, StockPO>> stockPOs = plates.get(plate);
		LocalDate localDate1 = DateHelper.dateToLocalDate(date);  
		LocalDate localDate2 ;
		for (Map<Date, StockPO> aStock : stockPOs.values()) {
			for (Date date2 : aStock.keySet()) {
				localDate2 = DateHelper.dateToLocalDate(date2);
				if (localDate1.equals(localDate2)) {
					stocks.put(aStock.get(date2).getCode(),aStock.get(date2));
					break;
				}
			}
		}
		
		 
		return stocks;
	}

	@Override
	public int nameToCode(String name) {
		return nameToCode.get(name);
	}
	
	

	/**
	 * @TODO：判断两个date对象是否同天
	 * @param d1
	 * @param d2
	 * @return
	 */
	/*private boolean sameDate(Date d1, Date d2) {  
	    if(null == d1 || null == d2)  
	        return false;  
	    //return getOnlyDate(d1).equals(getOnlyDate(d2));  
	    Calendar cal1 = Calendar.getInstance();  
	    cal1.setTime(d1);  
	    Calendar cal2 = Calendar.getInstance();  
	    cal2.setTime(d2);  
	    return  cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6);  
	}  */
}
