package vo.iterationThreeVO.strategyPageVO;

/**
 * Created by xiezhenyu on 2017/5/25.
 * 展示策略评测结果时用到该VO（雷达图）
 */
public class StrategyEvalutingVO {
    //收益指数
    private int earningsValue;
    //实盘指数
    private int realPlateValue;
    //稳定性
    private int robustnessValue;
    //流动性
    private int mobilityValue;
    //抗风险性
    private int anti_riskValue;

    public StrategyEvalutingVO() {

    }

    public StrategyEvalutingVO(int earningsValue, int realPlateValue, int robustnessValue, int mobilityValue, int anti_riskValue) {
        this.earningsValue = earningsValue;
        this.realPlateValue = realPlateValue;
        this.robustnessValue = robustnessValue;
        this.mobilityValue = mobilityValue;
        this.anti_riskValue = anti_riskValue;
    }

    public int getEarningsValue() {
        return earningsValue;
    }

    public void setEarningsValue(int earningsValue) {
        this.earningsValue = earningsValue;
    }

    public int getRealPlateValue() {
        return realPlateValue;
    }

    public void setRealPlateValue(int realPlateValue) {
        this.realPlateValue = realPlateValue;
    }

    public int getRobustnessValue() {
        return robustnessValue;
    }

    public void setRobustnessValue(int robustnessValue) {
        this.robustnessValue = robustnessValue;
    }

    public int getMobilityValue() {
        return mobilityValue;
    }

    public void setMobilityValue(int mobilityValue) {
        this.mobilityValue = mobilityValue;
    }

    public int getAnti_riskValue() {
        return anti_riskValue;
    }

    public void setAnti_riskValue(int anti_riskValue) {
        this.anti_riskValue = anti_riskValue;
    }
}
