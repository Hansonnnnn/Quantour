package bussinesslogicservice;

import model.StockPlate;
import model.StrategyType;
import vo.ColumnDiagramVO;
import vo.TrolleyVO;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by xiezhenyu on 2017/4/15.
 * 该接口用于为实现收益率直方图提供服务
 */
public interface ColumnDiagramService {
    /**
     * 该方法用来对全部股票进行某种策略回溯，返回关于回溯结果的收益率分布直方图的vo组成的Map
     * PEData表示Positive Earnings Data，表明该方法用于返回正收益率组成的VO
     * @param startDate 回测的开始时间
     * @param endDate 回测的结束时间
     * @param becomingDays 形成期
     * @param possessingDays 持有期
     * @param type 策略类型
     * @return
     */
    public ColumnDiagramVO getDataWithinAllStocks(Date startDate, Date endDate, int becomingDays,
                                                                  int possessingDays, StrategyType type);

    /**
     * 对某个板块
     * @param startDate 回测的开始时间
     * @param endDate 回测的结束时间
     * @param becomingDays 形成期
     * @param possessingDays 持有期
     * @param plate 所选板块
     * @param type 策略类型
     * @return
     */
    public ColumnDiagramVO getDataWithinPlates(Date startDate, Date endDate, int becomingDays,
                                                                  int possessingDays, StockPlate plate, StrategyType type);


    /**
     * 对自选股票
     * @param startDate 回测的开始时间
     * @param endDate 回测的结束时间
     * @param becomingDays 形成期
     * @param possessingDays 持有期
     * @param trolleyVOS 用户所选股票的集合（TrolleyVO是一个包含用户选择的股票信息的VO， 里面有股票名称、ID）
     * @param type 策略类型
     * @return
     */
    public ColumnDiagramVO getDataWithinSomeStocks(Date startDate, Date endDate, int becomingDays,
                                                                   int possessingDays, ArrayList<TrolleyVO> trolleyVOS, StrategyType type);


}
