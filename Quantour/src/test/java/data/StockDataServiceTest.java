package data;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Map;

import javax.swing.text.Highlighter.Highlight;

import org.junit.Before;
import org.junit.Test;

import dao.DateHelper;
import dao.connection.TxtConnection;
import data.StockDataServiceImpl;
import dataService.StockDataService;
import po.StockPO;

public class StockDataServiceTest {

	private StockDataService stockDataServiceImpl;
	@Before
	public void init(){
		new TxtConnection();
		stockDataServiceImpl = new StockDataServiceImpl();
	}
	@Test
	public void testGetSpecificDateStockDateDateInt() {
		Map<Date, StockPO> map2 = stockDataServiceImpl.getSpecificDateStock(DateHelper.stringToDate("03/29/11"), DateHelper.stringToDate("04/11/11"), 1);
		for (StockPO stockPO : map2.values()) {
			System.out.println(stockPO);
		}
	}

	@Test
	public void testGetSpecificDateStockDateDateString() {
		
	}

	@Test
	public void testGetStocksByDate() {
		Map<Integer, StockPO> map = stockDataServiceImpl.getStocksByDate(DateHelper.stringToDate("29/04/14"));
		for (StockPO stockPO : map.values()) {
			System.out.println(stockPO);
		}
	}

	@Test
	public void testSearchStocks(){
		Map<String, Integer> map = stockDataServiceImpl.searchStock("ly");
		for (String string : map.keySet()) {
			System.out.println(string);
		}
	}
}
