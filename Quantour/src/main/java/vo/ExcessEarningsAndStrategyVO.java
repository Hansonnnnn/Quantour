package vo;

/**该VO用作绘制超额收益率和策略胜率与不同形成期/持有期之间关系的图
 * Created by xiezhenyu on 2017/3/29.
 */
public class ExcessEarningsAndStrategyVO {
    private double rate;
    private int period;
    private String type;
    
    public ExcessEarningsAndStrategyVO(double rate, int period, String type)
    {
        this.rate = rate;
        this.period = period;
        this.type = type;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
