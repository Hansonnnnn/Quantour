package vo.iterationThreeVO.strategyPageVO;

/**
 * Created by xiezhenyu on 2017/5/24.
 * 该VO为策略回测界面显示回测结果的大vo，里面包含画收益曲线的vo、画收益周期直方图的vo
 *
 *
 * 一次策略回测产生一个该VO，所以装载时一次全部装入
 */
public class StrategyCallbackVO {
    //收益曲线对应的VO
    private EarningsLineVO earningsLineVO;

    //收益周期对应的VO
    private EarningsCircleVO earningCircleVO;

    //策略评测结果对应的VO
    private StrategyEvalutingVO strategyEvalutingVO;

    //策略回测展示策略参数对应的VO（alpha、beta参数等对应的VO）
    private ParamDataVO paramStrategyDataVO;

    //策略回测展示基准参数对应的VO（alpha、beta参数等对应的VO）
    private ParamDataVO paramBaseDataVO;

    public StrategyCallbackVO(EarningsLineVO earningsLineVO, EarningsCircleVO earningCircleVO, StrategyEvalutingVO strategyEvalutingVO, ParamDataVO paramStrategyDataVO, ParamDataVO paramBaseDataVO) {
        this.earningsLineVO = earningsLineVO;
        this.earningCircleVO = earningCircleVO;
        this.strategyEvalutingVO = strategyEvalutingVO;
        this.paramStrategyDataVO = paramStrategyDataVO;
        this.paramBaseDataVO = paramBaseDataVO;
    }

    public EarningsLineVO getEarningsLineVO() {
        return earningsLineVO;
    }

    public void setEarningsLineVO(EarningsLineVO earningsLineVO) {
        this.earningsLineVO = earningsLineVO;
    }

    public EarningsCircleVO getEarningCircleVO() {
        return earningCircleVO;
    }

    public void setEarningCircleVO(EarningsCircleVO earningCircleVO) {
        this.earningCircleVO = earningCircleVO;
    }

    public StrategyEvalutingVO getStrategyEvalutingVO() {
        return strategyEvalutingVO;
    }

    public void setStrategyEvalutingVO(StrategyEvalutingVO strategyEvalutingVO) {
        this.strategyEvalutingVO = strategyEvalutingVO;
    }

    public ParamDataVO getParamStrategyDataVO() {
        return paramStrategyDataVO;
    }

    public void setParamStrategyDataVO(ParamDataVO paramStrategyDataVO) {
        this.paramStrategyDataVO = paramStrategyDataVO;
    }

    public ParamDataVO getParamBaseDataVO() {
        return paramBaseDataVO;
    }

    public void setParamBaseDataVO(ParamDataVO paramBaseDataVO) {
        this.paramBaseDataVO = paramBaseDataVO;
    }
}
