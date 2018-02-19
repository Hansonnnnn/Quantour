package businessLogicTest;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import bussinesslogic.StockInfoBL;
import bussinesslogicservice.StockInfoService;
import vo.MarketInfoVO;
import vo.StockAverageValueVO;

public class StockLogicTest {
    
	StockInfoService stockInfoService=new StockInfoBL();
	StockInfoBL stock=new StockInfoBL();
	@Test
	public void getMarketerInfoTest(){
		@SuppressWarnings("deprecation")
		MarketInfoVO marketInfoVO=stockInfoService.getMarketInfo(new Date("2014/04/29"));
		assertEquals(1, marketInfoVO.getAmount_Of_LessFive());
	}
	@Test
	public void getLogTest(){
		    System.out.println(stockInfoService.getLogRateVariance(new Date("2014/04/23"), new Date("2014/04/29"), "1"));
	}
    
	@Test
	public void compareTwoStocks(){
	    System.out.println(stockInfoService.compareTwoStock(new Date("2014/04/22"), new Date("2014/04/29"), "1", "2").size());	
	}
	
	@Test
	public void getTimeTset(){
       getTime();		
		stockInfoService.getAllStocks(new Date("2014/04/22"));
		getTime();
	}
	
	@Test
	public void getAverage(){
		ArrayList<StockAverageValueVO> list=stockInfoService.getStockForAverageLine(5,new Date("2014/04/22") ,new Date("2014/04/29"), "1");
//	    System.out.println(list.get(0));
		assertEquals(2, list.size());
		
	}
	
	private  static void getTime(){
		Date dt=new Date();//如果不需要格式,可直接用dt,dt就是当前系统时间
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//设置显示格式
		String nowTime="";
		nowTime= df.format(dt);//用DateFormat的format()方法在dt中获取并以yyyy/MM/dd HH:mm:ss格式显示
	    System.out.println(nowTime);
	}
	@Test
	public void getRate(){
		System.out.println(stockInfoService.getLogRate(new Date("2014/04/22"), new Date("2014/04/23"), "1").size());
	}
	
	@Test
	public void getStocksTest(){
		System.out.println(stock.getspecificStocksData(new Date("2013/04/22"), new Date("2013/04/30"), "1").size());
	}}
