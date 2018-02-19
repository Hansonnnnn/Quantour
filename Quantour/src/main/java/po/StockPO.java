package po;

import java.util.Date;

/**
 * @author 凡
 *
 */
public class StockPO {
	private int Serial;
	private Date date;
	private double openValue;//开盘价
	private double highValue;//最高值
	private double lowValue;//最低值
	private double closeValue;//收盘价
	private long volume;//交易量
	private double adjustCloseValue;//复权收盘价
	private int code;//股票id
	private String name;//股票名称
	private String market;//股票市场
	private double logRate;//对数收益率
	
	public StockPO() {
	}

	public StockPO(int serial, Date date, double openValue, double highValue, double lowValue, double closeValue,
			long volume, double adjustCloseValue, int code, String name, String market, double logRate) {
		super();
		Serial = serial;
		this.date = date;
		this.openValue = openValue;
		this.highValue = highValue;
		this.lowValue = lowValue;
		this.closeValue = closeValue;
		this.volume = volume;
		this.adjustCloseValue = adjustCloseValue;
		this.code = code;
		this.name = name;
		this.market = market;
		this.logRate = logRate;
	}

	public int getSerial() {
		return Serial;
	}

	public void setSerial(int serial) {
		Serial = serial;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getOpenValue() {
		return openValue;
	}

	public void setOpenValue(double openValue) {
		this.openValue = openValue;
	}

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

	public double getCloseValue() {
		return closeValue;
	}

	public void setCloseValue(double closeValue) {
		this.closeValue = closeValue;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public double getAdjustCloseValue() {
		return adjustCloseValue;
	}

	public void setAdjustCloseValue(double adjustCloseValue) {
		this.adjustCloseValue = adjustCloseValue;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public double getLogRate() {
		return logRate;
	}

	public void setLogRate(double logRate) {
		this.logRate = logRate;
	}

	@Override
	public String toString() {
		return "StockPO [Serial=" + Serial + ", date=" + date + ", openValue=" + openValue + ", highValue=" + highValue
				+ ", lowValue=" + lowValue + ", closeValue=" + closeValue + ", volume=" + volume + ", adjustCloseValue="
				+ adjustCloseValue + ", code=" + code + ", name=" + name + ", market=" + market + ", logRate=" + logRate
				+ "]";
	}

	
	
	
}
