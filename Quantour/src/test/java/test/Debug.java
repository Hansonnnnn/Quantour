package test;

import java.util.Calendar;
import java.util.List;

import bussinesslogic.StockInfoBL;
import vo.HotStockVO;

public class Debug {
	public static void main(String[] args) {
		StockInfoBL stockInfoBL = new StockInfoBL();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2014, 4, 1);
		List<HotStockVO> list = stockInfoBL.getHotStocks(calendar.getTime());
		System.out.println(list.size());
	}
}
