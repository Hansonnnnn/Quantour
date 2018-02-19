package dao.dao;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.StockPlate;
import po.StockPO;

public interface StockDao {
	//insert a PO
	public boolean insert(StockPO stockPO);
	
	//search a StockPO with its id
	public Map<Date, StockPO> findByID(int stockPOId);
	
	public Map<Date, StockPO> findByName(String name);
	
	public Map<Integer,StockPO> getStocksByDate(Date date);
	
	public Map<Integer,StockPO> getStocksByDateAndPlate(Date date,StockPlate plate);
	
	//get all StockPO data
//	public Map<Integer, LinkedHashMap<Date, StockPO>> findAll();
	
	//get the data by using hql
	public List<StockPO> getStockPOData(String hql) ;
	
	public Map<String, Integer> getStockList();
	
	public int nameToCode(String name);
}


