package bussinesslogicservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import vo.*;

/**
 * @author 凡
 *
 */
public interface StockInfoService {
	/**
	 * 该方法根据开始日期、结束日期、股票名称来为界面绘制K线图提供服务
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param nameOrCode 股票名称或ID
	 * @return K线图的图片
	 */
	public ArrayList<StockInfoForK_lineVO> getStockForKLine(Date startDate, Date endDate, String nameOrCode);
	
	/**
	 * 该方法根据开始日期、结束日期、股票Id来为界面绘制K线图提供服务
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param info1 股票1的信息（可能是id，可能是name）
	 * @param info2 股票2的信息 （可能是id，可能是name）
	 * @return 
	 */
	
	public Map<Integer, StockCompare> compareTwoStock(Date startDate, Date endDate, String info1, String info2);
	/**
	 * 该方法根据用户输入的查询日期为界面提供该日期市场上所有的股票信息的服务
	 * @param date 所查询的日期
	 * @return 
	 */
	public MarketInfoVO getMarketInfo(Date date);
	
	
	/**
	 * 该方法根据时间段和股票信息算出这段时间内该股票的对数收益率方差
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param stockInfo 股票信息（可能是数字可能是名称）
	 * @return 返回对数收益率*100
	 */
	public double getLogRateVariance(Date startDate, Date endDate, String stockInfo);
	
	/**
	 * @TODO：获得一致股票的历史收盘价
	 * @param nameOrCode 股票ID或名称
	 * @return Map(key为日期，value为包含所需信息的vo)
	 */
	public Map<Date, StockCloseValueVO> getHistoryCloseValue(Date startDate, Date endDate, String nameOrCode) ;
	
	/**
	 * @TODO：模糊查找股票
	 * @param nameOrCode 股票ID或名称
	 * @return Map(key为股票名称，value为股票Code)
	 */
	public Map<String, Integer> searchStock(String nameOrCode);

	/**
	 * @TODO：过去一周交易量最大的股票TOP20
	 * @param date
	 * @return 
	 */
	public List<HotStockVO> getHotStocks(Date date);
	
	/**
	 * TODO:得到一段时间内的对数收益率
	 * @param startDate 开始日期
	 * @param endDate  结束日期
	 * @param nameOrCode 信息
	 * @return 返回对数收益率的list
	 */
	public ArrayList<LogVO> getLogRate(Date startDate,Date endDate,String nameOrCode);
	
	/**
	 * TODO:得到这一天的所有股票
	 * @param date 日期
	 * @return 股票，key是股票的Id
	 */
	public Map<Integer, StockVO> getAllStocks(Date date);

	/**
	 * TODO:得到画均线图的所有股票数据
	 * @param numOfLine 所画均线图为几日均线图
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param nameOrCode 股票名称或代码
	 * @return 返回StockCloseValueVO，因为画均线图只需要收盘价即可
	 */
	public ArrayList<StockAverageValueVO> getStockForAverageLine(int numOfLine, Date startDate, Date endDate, String nameOrCode);
    
	public StockVO getStocks(String info,Date date);
}
