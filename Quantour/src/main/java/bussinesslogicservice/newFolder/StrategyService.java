package bussinesslogicservice.newFolder;

import model.PrecisionIndex;
import model.StockPlate;
import model.StrategyStandard;
import vo.iterationThreeVO.strategyPageVO.PrecisionVO;
import vo.iterationThreeVO.strategyPageVO.StrategyCallbackVO;
import vo.iterationThreeVO.strategyPageVO.StrategyIndexVO;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by xiezhenyu on 2017/5/24.
 */
public interface StrategyService {
    /**
     * 该方法用来返回"使用已有策略"（区别于用户自己新建策略）的回测结果
     * @param stockPlate 股票池：全部股票、沪深300、中小板、创业板，自选股
     * @param strategyStandard 策略基准：沪深300、中小板、创业板
     * @param possessingDays 调仓周期==持有期
     * @param maxHoldNum 最大持有数（相当于迭代二的前20%）
     * @param startDate 回测的开始日期
     * @param endDate 回测的结束日期
     * @return StrategyCallbackVO   关于该VO的描述见该VO文件
     */
    public StrategyCallbackVO getCalResultOnExistStrategy(StockPlate stockPlate, StrategyStandard strategyStandard,
                                                   int possessingDays, int maxHoldNum, Date startDate, Date endDate);

    /**
     *
     * @param stockPlate 股票池：全部股票、沪深300、中小板、创业板
     * @param strategyStandard 策略基准：沪深300、中小板、创业板
     * @param possessingDays 调仓周期==持有期
     * @param maxHoldNum 最大持有数（相当于迭代二的前20%）
     * @param startDate 回测的开始日期
     * @param endDate 回测的结束日期
     * @param strategyStandardVOS 用户创建策略时选择的筛选指标
     * @return
     */
    public StrategyCallbackVO getCalResultOnNewStrategy(StockPlate stockPlate, StrategyStandard strategyStandard,
                                                        int possessingDays, int maxHoldNum, Date startDate, Date endDate,
                                                        ArrayList<StrategyIndexVO> strategyStandardVOS);

    /**
     * 该方法用来返回展示BP算法精确性
     * @return PrecisionVO的描述见该VO文件
     */
    public PrecisionVO getDataForShowPrecision();


}
