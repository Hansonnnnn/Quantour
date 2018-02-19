package data;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import bussinesslogic.BlockServiceImpl;
import bussinesslogicservice.BlockService;
import model.StockPlate;
import po.StockPO;

import org.junit.Before;
import org.junit.Test;

import bussinesslogic.StockInfoBL;
import bussinesslogicservice.StockInfoService;
import dataService.BlockDataService;
import vo.StockVO;

public class StockInfoServiceTest {
	StockInfoService stockInfoService;
	BlockService blockService;
	BlockDataService blockDataService;
	@Before
	public void setUpBeforeClass() throws Exception {
		stockInfoService=new StockInfoBL();
		blockService = new BlockServiceImpl();
		blockDataService = new BlockDataServiceImpl();
	}

/*	@Test
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

	@Test
	public void testGetLogRateVariance() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHistoryCloseValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchStock() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHotStocks() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLogRate() {
		fail("Not yet implemented");
	}
	@Test
	public void testGetStockForAverageLine() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testGetAllStocks() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2014, 4, 1);
		Map<Integer, StockVO> stockVOs = stockInfoService.getAllStocks(calendar.getTime());
		for (StockVO stockVO : stockVOs.values()) {
			System.out.println(stockVO);
		}
	}

	@Test
	public void testGetBlockStocks()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(2014, 4, 1);
		Map<Integer, StockVO> stockVOs = blockService.getStocksInBlock(calendar.getTime(), StockPlate.CHINEXT);
		for (StockVO stockVO : stockVOs.values()) {
			System.out.println(stockVO);
		}
	}
	
	@Test
	public void test12(){
		StockInfoBL stockInfoBL=new StockInfoBL();
		stockInfoBL.getStockForKLine(new Date("2012/12/4"), new Date("2013/3/4"), "1");
//        StockDataService service=new StockDataServiceImpl();
//        service
	}
	@Test
	public void test(){
		/*Map<Integer, StockVO> map = blockService.getStocksInBlock(new Date("2012/12/4"),StockPlate.CSI300 );
	//	map.remove(300);
		Map<Integer, StockVO> map2 = blockService.getStocksInBlock(new Date("2012/12/4"),StockPlate.CSI300 );
		System.out.println(map==map2);
		System.out.println(map.size()+"  "+map2.size());*/
		Map<Integer, StockPO> map = blockDataService.getStocksByDateAndPlate(new Date("2012/12/4"),StockPlate.CSI300);
		StockPO stockPO = map.remove(300);
		Map<Integer, StockPO> map2 = blockDataService.getStocksByDateAndPlate(new Date("2012/12/4"),StockPlate.CSI300);
		System.out.println(map.size()+"  "+map2.size());
		System.out.println(map==map2);
	}
}
