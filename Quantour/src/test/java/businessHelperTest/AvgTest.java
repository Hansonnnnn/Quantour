package businessHelperTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import bussinesslogic.helper.AVGGetComparator;

public class AvgTest {
    
	@Test
	public void LimitTest() {
		ArrayList<String > list=AVGGetComparator.removeLimitInAll(new Date("2013/5/7"));
		System.out.println(AVGGetComparator.selectHighStocks(list, 10, 10, new Date("2013/5/7")));
		System.out.println(list.size());
	}
}
