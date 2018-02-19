package bussinesslogicservice;

import java.util.ArrayList;
import java.util.Date;

import model.StockPlate;
import model.StrategyType;
import model.TimeType;
import vo.StrategyExcessEarningVO;
/**
 * 
 * @author yk
 * 计算不同持有期或生成期的超额省略
 *
 */
public interface GetExcessEarningAndStrategyService {
	
	/**
	 * 计算所有股票不同持有期或形成期的超额收益率和策略胜率
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param strategyType 策略类型——动量策略OR均值回归
	 * @param timeType 时间类型——动量策略形成期或者是持有期，均值回归只能是形成期，而且形成期只有5，10，20可选
	 * @param nums 选定时间的长度，固定的时间长度是多少
	 * @param firstDays 未选定的时间的开始，第一个是几天的持有期或形成期
	 * @param iterval 未选定时间的间隔，下一个形成期或持有期是firstDays+iterval*n
	 * @param coloms 需要显示多少个回测结果，n的最大值是多少
	 * @return 返回比较的VO
	 */
     public StrategyExcessEarningVO getAllStocksEESVO(Date startDate,Date endDate,StrategyType strategyType,TimeType timeType
    		 ,int nums,int firstDays,int iterval,int coloms);
     
     /**
 	 * 计算板块股票不同持有期或形成期的超额收益率和策略胜率
 	 * @param plate 股票的板块
 	 * @param startDate 开始日期
 	 * @param endDate 结束日期
 	 * @param strategyType 策略类型——动量策略OR均值回归
 	 * @param timeType 时间类型——动量策略形成期或者是持有期，均值回归只能是形成期，而且形成期只有5，10，20可选
 	 * @param nums 选定时间的长度
 	 * @param firstDays 未选定的时间的开始
 	 * @param iterval 未选定时间的间隔
 	 * @return 返回比较的VO
 	 */
     public StrategyExcessEarningVO getStockPoolEESVO(StockPlate plate,Date startDate,Date endDate,StrategyType strategyType,TimeType timeType
    		 ,int nums,int firstDays,int iterval,int coloms);
     
     /**
 	 * 计算选定股票不同持有期或形成期的超额收益率和策略胜率
 	 * @param selectedStocks 选定股票的列表
 	 * @param startDate 开始日期
 	 * @param endDate 结束日期
 	 * @param strategyType 策略类型——动量策略OR均值回归
 	 * @param timeType 时间类型——动量策略形成期或者是持有期，均值回归只能是形成期，而且形成期只有5，10，20可选
 	 * @param nums 选定时间的长度
 	 * @param firstDays 未选定的时间的开始
 	 * @param iterval 未选定时间的间隔
 	 * @return 返回比较的VO
 	 */
     public StrategyExcessEarningVO getUserStocksEESVO(ArrayList<String>selectedStocks,Date startDate,Date endDate,StrategyType strategyType,TimeType timeType
    		 ,int nums,int firstDays,int iterval,int coloms);
}
