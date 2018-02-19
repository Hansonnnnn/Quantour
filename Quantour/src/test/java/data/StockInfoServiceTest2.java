package data;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import bussinesslogic.StockInfoBL;
import bussinesslogicservice.StockInfoService;
import dao.DateHelper;
import vo.HotStockVO;
import vo.StockCloseValueVO;

public class StockInfoServiceTest2 {
	private StockInfoService stockInfoService;
	
	
	@Before
	public void init(){
		stockInfoService  = new StockInfoBL();
	}
	/*@Test
	public void testGetStockForKLine() {
		fail("Not yet implemented");
	}

	@Test
	public void testCompareTwoStock() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMarketInfo() {
		fail("Not yet implemented");
	}
*/
	
	@Test
	public void testGetLogRateVariance() {
	}

	@Test
	public void testGetHotStocks() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2011, 3, 1);
		List<HotStockVO> list = stockInfoService.getHotStocks(calendar.getTime());
		for (HotStockVO hotStockVO : list) {
			System.out.println(hotStockVO.getCode()+" "+hotStockVO.getVolume());
		}
	}

	
	@Test
	public void testGetHistoryCloseValue() {
		Map<Date, StockCloseValueVO> map = stockInfoService.getHistoryCloseValue(DateHelper.stringToDate("03/01/13"), DateHelper.stringToDate("03/07/13"),"151");
		for (Date date : map.keySet()) {
			System.out.println(map.get(date));
		}
	}
	@Test
	public void testSearchStock() {
		Map<String, Integer> map = stockInfoService.searchStock("业");
		for (String string : map.keySet()) {
			System.out.println(map.get(string)+" "+string);
		}
	}

}
