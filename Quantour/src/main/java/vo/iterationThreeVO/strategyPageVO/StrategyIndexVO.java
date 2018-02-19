package vo.iterationThreeVO.strategyPageVO;

import model.CompareIndex;
import model.StrategyIndex;

/**
 * Created by xiezhenyu on 2017/5/25.
 * 该VO用来存放用户新建策略时，使用的筛选条件
 */
public class StrategyIndexVO {
    //筛选指标，见枚举类StrategyIndex
    private StrategyIndex strategyIndex;
    //比较指标，见枚举类CompareIndex
    private CompareIndex compareIndex;
    //值
    private double value;

    public StrategyIndexVO() {

    }

    public StrategyIndexVO(StrategyIndex strategyIndex, CompareIndex compareIndex, double value) {
        this.strategyIndex = strategyIndex;
        this.compareIndex = compareIndex;
        this.value = value;
    }

    public StrategyIndex getStrategyIndex() {
        return strategyIndex;
    }

    public void setStrategyIndex(StrategyIndex strategyIndex) {
        this.strategyIndex = strategyIndex;
    }

    public CompareIndex getCompareIndex() {
        return compareIndex;
    }

    public void setCompareIndex(CompareIndex compareIndex) {
        this.compareIndex = compareIndex;
    }

    public double getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
