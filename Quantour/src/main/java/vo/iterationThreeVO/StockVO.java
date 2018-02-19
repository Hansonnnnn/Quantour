package vo.iterationThreeVO;

/**
 * Created by xiezhenyu on 2017/5/24.
 */
public class StockVO {
    private String name;//股票名称
    private int id;//股票id

    /**
     * 属性还没有补全
     */


    private double highValue;//最高值
    private double lowValue;//最低值
    private double openValue;//开盘价
    private double closeValue;//收盘价
    private double amount_Of_Increase;//涨幅
    private double amount_Of_Drop;//跌幅
    private double logRate;//对数收益率
    private long volume;//成交量

    //	private double logRateVariance;//对数收益率方差
    public double getHighValue() {
        return highValue;
    }
    public void setHighValue(double highValue) {
        this.highValue = highValue;
    }
    public double getLowValue() {
        return lowValue;
    }
    public void setLowValue(double lowValue) {
        this.lowValue = lowValue;
    }
    public double getOpenValue() {
        return openValue;
    }
    public void setOpenValue(double openValue) {
        this.openValue = openValue;
    }
    public double getCloseValue() {
        return closeValue;
    }
    public void setCloseValue(double closeValue) {
        this.closeValue = closeValue;
    }
    public double getAmount_Of_Increase() {
        return amount_Of_Increase;
    }
    public void setAmount_Of_Increase(double amount_Of_Increase) {
        this.amount_Of_Increase = amount_Of_Increase;
    }
    public double getAmount_Of_Drop() {
        return amount_Of_Drop;
    }
    public void setAmount_Of_Drop(double amount_Of_Drop) {
        this.amount_Of_Drop = amount_Of_Drop;
    }
    public double getLogRate() {
        return logRate;
    }
    public void setLogRate(double logRate) {
        this.logRate = logRate;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public long getVolume() {return volume;}
    public void setVolume(long volume) {this.volume = volume;}
    @Override
    public String toString() {
        return "StockVO [name=" + name + ", id=" + id + ", highValue=" + highValue + ", lowValue=" + lowValue
                + ", openValue=" + openValue + ", closeValue=" + closeValue + ", amount_Of_Increase="
                + amount_Of_Increase + ", amount_Of_Drop=" + amount_Of_Drop + ", logRate=" + logRate + ", volume="
                + volume + "]";
    }
}
