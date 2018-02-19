package bussinesslogicservice;

import model.StockPlate;
import model.StrategyType;
import vo.LogVO;
import vo.NoneStrategyResultVO;
import vo.StockVO;
import vo.StrategyResultVO;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by xiezhenyu on 2017/3/26.
 * 比较用户策略和基准的结果
 */
public interface StrategyVSBaseService {
    /**
     * 该方法为用户选择所有股票和某种策略，返回回测结果的方法
     * @param startDate 用户选择的开始日期
     * @param endDate 用户选择的结束日期
     * @param becomingDays 动量策略(形成期)/均值回归策略(取几日均线图)
     * @param possessingDays 持有时间
     * @param processingStcoksNums 动量策略(板块内所有股票数的20%)/均值回归策略(用户选择的持股数)
     * @param type 策略种类(动量策略/均值回归策略)(见枚举类StrategyType)
     * @return 返回策略结果的VO
     */
    public StrategyResultVO useStrategyWithinAllStocks(Date startDate, Date endDate, int becomingDays, int possessingDays, StrategyType type);


    /**
     * 该方法为用户选定某版块内股票和某种策略，返回回测结果的方法
     * @param startDate 用户选择的开始日期
     * @param endDate 用户选择的结束日期
     * @param plate 选择的板块名称
     * @param becomingDays 动量策略(形成期)/均值回归策略(取几日均线图)
     * @param possessingDays 持有时间
     * @param possessingStocksNums 动量策略(板块内所有股票数的20%)/均值回归策略(用户选择的持股数)
     * @param type 策略种类(动量策略/均值回归策略)(见枚举类StrategyType)
     * @return 返回策略结果的VO
     */
    public StrategyResultVO useStrategyGenerally(Date startDate, Date endDate, StockPlate plate, int becomingDays, int possessingDays
    ,StrategyType type);


    /**
     * 该方法为用户选定多只股票和某种策略，返回回测结果的方法
     * @param startDate 用户选择的开始日期
     * @param endDate 用户选择的结束日期
     * @param selectedStocks 用户选定的多只股票的股票名的集合
     * @param becomingDays 动量策略(形成期)/均值回归策略(取几日均线图)
     * @param possessingDays 持有时间
     * @param possessingStocksNums 动量策略(板块内所有股票数的20%)/均值回归策略(用户选择的持股数)
     * @param type 策略种类(动量策略/均值回归策略)(见枚举类StrategyType)
     * @return 返回策略结果的VO
     */
    public StrategyResultVO useStrategyWithinSomeStocks(Date startDate, Date endDate, ArrayList<String> selectedStocks, int becomingDays, int possessingDays
            , StrategyType type);
}
