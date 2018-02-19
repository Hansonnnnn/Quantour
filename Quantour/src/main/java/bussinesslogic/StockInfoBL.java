package bussinesslogic;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bussinesslogic.helper.StockPOToVO;
import bussinesslogicservice.StockInfoService;
import data.StockDataServiceImpl;
import dataService.StockDataService;
import javafx.scene.chart.PieChart.Data;
import po.StockPO;
import utility.DateHelper;
import vo.*;

public class StockInfoBL implements StockInfoService {

	private StockDataService stockDataService;

	public StockInfoBL() {
		stockDataService = new StockDataServiceImpl();
	}

	@Override
	public ArrayList<StockInfoForK_lineVO> getStockForKLine(Date startDate, Date endDate, String nameOrCode) {
		Map<Date, StockPO> stockPOs;
		if(isNumeric(nameOrCode)){
			stockPOs=stockDataService.getSpecificDateStock(startDate, endDate, Integer.parseInt(nameOrCode));
		}else{
			stockPOs=stockDataService.getSpecificDateStock(startDate, endDate, nameOrCode);
		}
		ArrayList<StockInfoForK_lineVO> lineVOs = StockPOToVO.StockPOstoLineVOList(stockPOs);
		// Image image = KLinePainter.draw(lineVOs);
		return lineVOs;
	}

	@Override
	public Map<Date, StockCloseValueVO> getHistoryCloseValue(Date startDate, Date endDate, String nameOrCode) {
		Map<Date, StockPO> stockPOs=this.getspecificStocksData(startDate, endDate, nameOrCode);
//		if (isNumeric(nameOrCode)) {
//			stockPOs = stockDataService.getSpecificDateStock(startDate, endDate, Integer.parseInt(nameOrCode));
//		} else {
//			stockPOs = stockDataService.getSpecificDateStock(startDate, endDate, nameOrCode);
//		}
		Map<Date, StockCloseValueVO> map = new LinkedHashMap<>();
		for (StockPO stockPO : stockPOs.values()) {
			map.put(stockPO.getDate(), new StockCloseValueVO(stockPO.getDate(), stockPO.getCloseValue(),
					stockPO.getCode(), stockPO.getName()));
		}
		return map;
	}

	@Override
	public MarketInfoVO getMarketInfo(Date date) {

		Date yesDate = new Date(); // 表示前一天的时间

		// 计算出前一天的日期
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		yesDate = calendar.getTime();

		MarketInfoVO marketerInfo = new MarketInfoVO();
		Map<Integer, StockPO> todayStockPOs = stockDataService.getStocksByDate(date);
       
		Map<Integer, StockPO> yseStockPOs = stockDataService.getStocksByDate(yesDate);
		/*
		 * Debug
		 */
		for (int i : todayStockPOs.keySet()) {
			if (yseStockPOs.keySet().contains(i)) {
				continue;
			}
		}
		
		if (todayStockPOs != null) {
			
			for (Map.Entry<Integer, StockPO> entry : todayStockPOs.entrySet()) {
				
				
				StockVO stockVO = StockPOToVO.stockPoToVo(entry.getValue());
				marketerInfo.setVolumn(marketerInfo.getVolumn() + entry.getValue().getVolume());

				// 关于涨幅值的
				if (stockVO.getAmount_Of_Increase() > 0.1) {
					marketerInfo.setAmount_Of_LimitUp(marketerInfo.getAmount_Of_LimitUp() + 1);
				}
				if (stockVO.getAmount_Of_Increase() > 0.05) {
					marketerInfo.setAmount_Of_UpFive(marketerInfo.getAmount_Of_UpFive() + 1);
				}

				// 关于跌幅的
				if (stockVO.getAmount_Of_Drop() < -0.1) {
					marketerInfo.setAmount_Of_LimitDown(marketerInfo.getAmount_Of_LimitDown() + 1);
				}
				if (stockVO.getAmount_Of_Increase() < -0.05) {
					marketerInfo.setAmount_Of_DownFive(marketerInfo.getAmount_Of_DownFive() + 1);
				}

				if(!yseStockPOs.containsKey(entry.getKey())){
				    Map<Date, StockPO> list=stockDataService.getSpecificDateStock(date, date, entry.getKey());
				    yseStockPOs.put(entry.getKey(), list.get(date));
				}
				
				if ((entry.getValue().getOpenValue()
						- entry.getValue().getCloseValue()) > (yseStockPOs.get(entry.getKey()).getCloseValue()
								* 0.05)) {
					marketerInfo.setAmount_Of_MoreFive(marketerInfo.getAmount_Of_MoreFive() + 1);
				}

				if ((entry.getValue().getOpenValue()
						- entry.getValue().getCloseValue()) < (yseStockPOs.get(entry.getKey()).getCloseValue()
								* (-0.05))) {
					marketerInfo.setAmount_Of_LessFive(marketerInfo.getAmount_Of_LessFive() + 1);
				}
			}
			return marketerInfo;
		}
//		return null;
		return marketerInfo;
	}

	@Override
	public Map<Integer, StockCompare> compareTwoStock(Date startDate, Date endDate, String info1, String info2) {

		Map<Integer, StockCompare> map = new LinkedHashMap<>();
		Map<Date, StockPO> stocks1=this.getspecificStocksData(startDate, endDate, info1);
		Map<Date, StockPO> stocks2=this.getspecificStocksData(startDate, endDate, info2);
		StockCompare stockCompare1 = new StockCompare();
		StockCompare stockCompare2 = new StockCompare();


		if (stocks1 == null) {
			return null;
		}

		if (stocks2 == null) {
			return null;
		}
		//设置最初的最低价
        stockCompare1.setLowistPrice(stocks1.get(startDate).getLowValue());
        stockCompare2.setLowistPrice(stocks2.get(startDate).getLowValue());
		
        // 遍历stock1，得到历史最高价和最低价
		for (Map.Entry<Date, StockPO> entry : stocks1.entrySet()) {
			if (entry.getValue().getLowValue() < stockCompare1.getLowistPrice()) {
				stockCompare1.setLowistPrice(entry.getValue().getLowValue());
			}
			if (entry.getValue().getHighValue() > stockCompare1.getHighistPrice()) {
				stockCompare1.setHighistPrice(entry.getValue().getHighValue());
			}
		}
		// 遍历stock2，得到历史最高价和最低价
		for (Map.Entry<Date, StockPO> entry : stocks2.entrySet()) {
			if (entry.getValue().getLowValue() < stockCompare2.getLowistPrice()) {
				stockCompare2.setLowistPrice(entry.getValue().getLowValue());
			}
			if (entry.getValue().getHighValue() > stockCompare2.getHighistPrice()) {
				stockCompare2.setHighistPrice(entry.getValue().getHighValue());
			}
		}
        
		//得到第一只股票的涨（跌）幅
		double rate1 = (stocks1.get(endDate).getCloseValue() - stocks1.get(startDate).getCloseValue())
				/ stocks1.get(startDate).getCloseValue();

		if (rate1 >= 0) {
			stockCompare1.setAmount_Of_Increase(format(rate1)*100);
		} else {
			stockCompare1.setAmount_Of_Drop(format(rate1)*100);
		}
		//设置股票2的id和name
		stockCompare1.setId(stocks1.get(startDate).getCode());
		stockCompare1.setName(stocks1.get(startDate).getName());
        
		//得到第二只股票的涨（跌）幅
		double rate2 = (stocks2.get(endDate).getCloseValue() - stocks2.get(startDate).getCloseValue())
				/ stocks2.get(startDate).getCloseValue();
		if (rate2 >= 0) {
			stockCompare2.setAmount_Of_Increase(format(rate2)*100);
		} else {
			stockCompare2.setAmount_Of_Drop(format(rate2)*100);
		}
		//设置股票2的id和name
		stockCompare2.setId(stocks2.get(startDate).getCode());
		stockCompare2.setName(stocks2.get(startDate).getName());

		map.put(1, stockCompare1);
		map.put(2, stockCompare2);

		return map;
	}

	@Override
	public double getLogRateVariance(Date startDate, Date endDate, String stockInfo) {
		ArrayList<LogVO> logVOS = getLogRate(startDate, endDate, stockInfo);
		ArrayList<Double> logRateList = new ArrayList<>();
		int sum = 0;
		for (LogVO logVO : logVOS) {
			double d = Math.log(logVO.getLogValue());
			sum+=d;
			logRateList.add(d);
		}

		return format(this.cal_Variance(logRateList, sum/logRateList.size()));
	}

	@Override
	public Map<String, Integer> searchStock(String nameOrCode) {
		return stockDataService.searchStock(nameOrCode);
	}

	
	@Override
	public List<HotStockVO> getHotStocks(Date date) {
		Date temp = date;
		Map<Integer, StockPO> map1 = stockDataService.getStocksByDate(temp);
		temp = DateHelper.add(date, -1);
		Map<Integer, StockPO> map2 = stockDataService.getStocksByDate(temp);
		temp = DateHelper.add(date, -1);
		Map<Integer, StockPO> map3 = stockDataService.getStocksByDate(temp);
		temp = DateHelper.add(date,-1);
		Map<Integer, StockPO> map4 = stockDataService.getStocksByDate(temp);
		temp = DateHelper.add(date,-1);
		Map<Integer, StockPO> map5 = stockDataService.getStocksByDate(temp);
		LinkedList<HotStockVO> hotStockVOs = new LinkedList<>();
		long sum;
		for (int Code : map1.keySet()) {
			sum = 0;
			if (map1.get(Code) != null) {
				sum += map1.get(Code).getVolume();
			}
			if (map2.get(Code) != null) {
				sum += map2.get(Code).getVolume();
			}
			if (map3.get(Code) != null) {
				sum += map3.get(Code).getVolume();
			}
			if (map4.get(Code) != null) {
				sum += map4.get(Code).getVolume();
			}
			if (map5.get(Code) != null) {
				sum += map5.get(Code).getVolume();
			}
	
			hotStockVOs.add(new HotStockVO(Code, map1.get(Code).getName(), sum));
		}
		Collections.sort(hotStockVOs);
		Collections.reverse(hotStockVOs);
		List<HotStockVO> result = hotStockVOs.subList(0, 20);
	
		return result;
	}

	@Override
	public ArrayList<LogVO> getLogRate(Date startDate, Date endDate, String stockInfo) {

		Map<Date, StockPO> stockPOs=this.getspecificStocksData(startDate, endDate, stockInfo);
		ArrayList<LogVO> logVOs = new ArrayList<>();

		startDate = DateHelper.add(startDate,-1);

		Iterator<Date> iterator = stockPOs.keySet().iterator();
		Date yesterday;
		Date today = iterator.next();
		double logRate;
		int code = stockPOs.get(today).getCode();
		String name = stockPOs.get(today).getName();
		while (iterator.hasNext()) {
			yesterday = today;
			today = iterator.next();
			logRate = stockPOs.get(today).getCloseValue() / stockPOs.get(yesterday).getCloseValue();

			logVOs.add(new LogVO(today, logRate, code, name));

		}

		return logVOs;
	}

	@Override
	public Map<Integer, StockVO> getAllStocks(Date date) {
		Map<Integer, StockPO> stockPOs =new LinkedHashMap<>();
		while(stockPOs.size()==0){
		    stockPOs=stockDataService.getStocksByDate(date);
		    date=DateHelper.add(date, 1);
		}
		Map<Integer, StockVO> stockVOs = new LinkedHashMap<>();
		for (Map.Entry<Integer, StockPO> entry : stockPOs.entrySet()) {
			stockVOs.put(entry.getKey(), StockPOToVO.stockPoToVo(entry.getValue()));
		}
		return stockVOs;
	}

	@Override
	public ArrayList<StockAverageValueVO> getStockForAverageLine(int numOfLine, Date startDate, Date endDate,
			String nameOrCode) {
        if(DateHelper.isWeekends(startDate)){
        	startDate=DateHelper.add(startDate, 1);
        }
        
		// 判断是否合理
		if (DateHelper.add(startDate, numOfLine - 1).after(endDate)) {
			return null;
		}
		
		// 算均线图
		Map<Date, StockPO> stocks =this.getspecificStocksData(startDate, endDate, nameOrCode);
		ArrayList<Double> prices = new ArrayList<>();
		ArrayList<StockAverageValueVO> averagePrices = new ArrayList<>();
		Date tempDate=new Date();
		tempDate=startDate;
//		if (isNumeric(nameOrCode)) {
//			stocks = stockDataService.getSpecificDateStock(startDate, endDate, Integer.parseInt(nameOrCode));
//		} else {
//			stocks = stockDataService.getSpecificDateStock(startDate, endDate, nameOrCode);
//		}

		// 把所有的收盘价放到一个list里

		while (!startDate.after(endDate)) {
			prices.add(stocks.get(startDate).getCloseValue());
			startDate = DateHelper.add(startDate, 1);
		}
		// 算均值
		double average = 0.0;
		for (int i = 0; i < numOfLine - 1; i++) {
			average += prices.get(i);
		}
		int count = numOfLine - 1;
		while (prices.size() >= numOfLine) {
			average += prices.get(numOfLine - 1);

			StockAverageValueVO stockAverageValueVO = new StockAverageValueVO();
			stockAverageValueVO.setAverageValue(average / numOfLine);
			stockAverageValueVO.setDate(DateHelper.add(tempDate,count));
			count += 1;

			averagePrices.add(stockAverageValueVO);

			average -= prices.get(0);
			prices.remove(0);
		}
		return averagePrices;
	}

	@Override
	public StockVO getStocks(String info, Date date) {
//			return StockPOToVO.stockPoToVo(this.getSpecificStocksData(date, this.add(date, 1), Integer.parseInt(info)).get(date));
			return StockPOToVO.stockPoToVo(this.getspecificStocksData(date, DateHelper.add(date, 1), info).get(date));
		
	}

	// 判断是否由数字组成
	private boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	// 计算方差
	private double cal_Variance(ArrayList<Double> priceList, double average) {
		double rateVariance = 0;// 保存结果
		for (int i = 0; i < priceList.size(); i++) {
			rateVariance += Math.pow((priceList.get(i) - average), 2);
		}

		rateVariance /= priceList.size();
		return rateVariance * 100;
	}

	// 格式化浮点数结果
				private double format(double d) {
					DecimalFormat dFormat=new DecimalFormat("#.0000");
					String string1=dFormat.format(d);
					return Double .parseDouble(string1);
				}

	

	public Map<Date, StockPO> getspecificStocksData(Date startDate,Date endDate,String info){
		Map<Date , StockPO> list=new LinkedHashMap<>();

		if(isNumeric(info)){
			list=stockDataService.getSpecificDateStock(startDate, endDate, Integer.parseInt(info));
		}else{
			list=stockDataService.getSpecificDateStock(startDate, endDate, info);
		}
		
		if (list.get(startDate)==null) {
			StockPO po;
			Map<Date , StockPO> start=new LinkedHashMap<>();
			Date temp=new Date();
			temp=startDate;
			
			while(start.size()==0){
				temp=DateHelper.add(temp, -1);

				if(isNumeric(info)){
					start=stockDataService.getSpecificDateStock(startDate, startDate, Integer.parseInt(info));
				}else{
					start=stockDataService.getSpecificDateStock(startDate, startDate, info);
				}
			}
			
			po=start.get(temp);
			po.setDate(startDate);
			list.put(startDate, po);
		}
		
		while(!startDate.after(endDate)){
			if(!list.containsKey(startDate)){
				Date date1=new Date();
				date1=DateHelper.add(startDate, -1);
				while(list.get(date1)==null){
				    date1=DateHelper.add(date1,-1);
				}
				StockPO po=list.get(date1);
				po.setDate(startDate);
				list.put(startDate, po);
			}
			startDate=DateHelper.add(startDate, 1);
		}
		return list;
	}
}