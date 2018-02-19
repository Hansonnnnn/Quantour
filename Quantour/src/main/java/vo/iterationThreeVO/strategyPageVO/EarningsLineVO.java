package vo.iterationThreeVO.strategyPageVO;

import java.util.Date;
import java.util.Map;

/**
 * Created by xiezhenyu on 2017/5/25.
 * 该类是用来展示年收益曲线时用到的VO
 */
public class EarningsLineVO {
    //基准收益率的数据，一个日期对应一个收益率（不用对数收益率）
    private Map<Date, Double> baseEarningsData;
    //策略收益率的数据，要求同上
    private Map<Date, Double> strategyEarningsData;

    public Map<Date, Double> getBaseEarningsData() {
        return baseEarningsData;
    }

    public void setBaseEarningsData(Map<Date, Double> baseEarningsData) {
        this.baseEarningsData = baseEarningsData;
    }

    public Map<Date, Double> getStrategyEarningsData() {
        return strategyEarningsData;
    }

    public void setStrategyEarningsData(Map<Date, Double> strategyEarningsData) {
        this.strategyEarningsData = strategyEarningsData;
    }
}
