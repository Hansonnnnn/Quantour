package vo;
/**
 * 当日总交易量、涨停股票数、跌停股票数、涨幅超过 5%的股票数，跌幅超过 5%的股票数，
 * 开盘‐收盘大于 5%*上一个交易日收盘价的 股票个数、开盘‐收盘小于‐5%*上一个交易日收盘价的股票个数。
 * @author xiezhenyu
 *
 */
public class MarketInfoVO {
	private double volumn;// 当日总交易量
	private int amount_Of_LimitUp;//涨停股票数
	private int amount_Of_LimitDown;//跌停股票数
	private int amount_Of_UpFive;//涨幅超过 5%的股票数
	private int amount_Of_DownFive;//跌幅超过 5%的股票数
	private int amount_Of_MoreFive;// 开盘‐收盘大于 5%*上一个交易日收盘价的 股票个数
	private int amount_Of_LessFive;//开盘‐收盘小于‐5%*上一个交易日收盘价的股票个数
	
	public double getVolumn() {
		return volumn;
	}
	public void setVolumn(double volumn) {
		this.volumn = volumn;
	}
	public int getAmount_Of_LimitUp() {
		return amount_Of_LimitUp;
	}
	public void setAmount_Of_LimitUp(int amount_Of_LimitUp) {
		this.amount_Of_LimitUp = amount_Of_LimitUp;
	}
	public int getAmount_Of_LimitDown() {
		return amount_Of_LimitDown;
	}
	public void setAmount_Of_LimitDown(int amount_Of_LimitDown) {
		this.amount_Of_LimitDown = amount_Of_LimitDown;
	}
	public int getAmount_Of_UpFive() {
		return amount_Of_UpFive;
	}
	public void setAmount_Of_UpFive(int amount_Of_UpFive) {
		this.amount_Of_UpFive = amount_Of_UpFive;
	}
	public int getAmount_Of_DownFive() {
		return amount_Of_DownFive;
	}
	public void setAmount_Of_DownFive(int amount_Of_DownFive) {
		this.amount_Of_DownFive = amount_Of_DownFive;
	}
	public int getAmount_Of_MoreFive() {
		return amount_Of_MoreFive;
	}
	public void setAmount_Of_MoreFive(int amount_Of_MoreFive) {
		this.amount_Of_MoreFive = amount_Of_MoreFive;
	}
	public int getAmount_Of_LessFive() {
		return amount_Of_LessFive;
	}
	public void setAmount_Of_LessFive(int amount_Of_LessFive) {
		this.amount_Of_LessFive = amount_Of_LessFive;
	}
	
	
}
