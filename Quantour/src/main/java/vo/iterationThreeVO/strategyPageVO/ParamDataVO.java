package vo.iterationThreeVO.strategyPageVO;

/**
 * Created by xiezhenyu on 2017/5/25.
 * 该VO包含所以策略的指数数据，如总收益、年化收益、夏普比率、最大回撤率、收益波动率、信息比率、Beta、Alpha
 */
public class ParamDataVO {
    //阿尔法系数
    private double alpha;

    //贝塔系数
    private double  beta;

    //年化收益率
    private double annualizedRateOfReturn;

    //夏普比率
    private double sharpeRatio;

    //最大回撤率
    private double biggestReturn;

    //总收益
    private double rateOfTotalReturn;



    /**
     * 使用这个构造函数分别装在两个VO
     * @param alpha
     * @param beta
     * @param annualizedRateOfReturn
     * @param sharpeRatio
     * @param biggestReturn
     * @param rateOfTotalReturn
     */
    public ParamDataVO(double alpha, double beta, double annualizedRateOfReturn, double sharpeRatio, double biggestReturn, double rateOfTotalReturn) {
        this.alpha = alpha;
        this.beta = beta;
        this.annualizedRateOfReturn = annualizedRateOfReturn;
        this.sharpeRatio = sharpeRatio;
        this.biggestReturn = biggestReturn;
        this.rateOfTotalReturn = rateOfTotalReturn;
    }



    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getAnnualizedRateOfReturn() {
        return annualizedRateOfReturn;
    }

    public void setAnnualizedRateOfReturn(double annualizedRateOfReturn) {
        this.annualizedRateOfReturn = annualizedRateOfReturn;
    }

    public double getSharpeRatio() {
        return sharpeRatio;
    }

    public void setSharpeRatio(double sharpeRatio) {
        this.sharpeRatio = sharpeRatio;
    }

    public double getBiggestReturn() {
        return biggestReturn;
    }

    public void setBiggestReturn(double biggestReturn) {
        this.biggestReturn = biggestReturn;
    }

    public double getRateOfTotalReturn() {
        return rateOfTotalReturn;
    }

    public void setRateOfTotalReturn(double rateOfTotalReturn) {
        this.rateOfTotalReturn = rateOfTotalReturn;
    }
}
