package businessHelperTest;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import bussinesslogic.helper.StrategyHelper;
import data.StockStrategyServiceImpl;
import dataService.StockStrategyService;
import model.StrategyType;

public class HelperTest {

	@Test
	public void test() {
		Date d1=new Date("2013/4/18");
		
		System.out.println(StrategyHelper.getGreastSttocksInAll(d1, 20, StrategyType.momentumDriven).size());
		System.out.println(d1.toLocaleString());
	}
	
	@Test
	public void test2(){
		Date d1=new Date("2013/4/18");
		ArrayList<String > list=StrategyHelper.getGreastSttocksInAll(d1, 10, StrategyType.momentumDriven);
		StockStrategyService service=new StockStrategyServiceImpl();
//		System.out.println(list.size());
//		System.out.println(list);
		System.out.println(service.getYieldOfSelectsStocks(list, 10, d1));
	}
	//

}
