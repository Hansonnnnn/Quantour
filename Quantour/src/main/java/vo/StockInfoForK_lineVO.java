package vo;

import java.util.Date;

public class StockInfoForK_lineVO {
	private Date date;
	private double openValue;//开盘价
	private double highValue;//最高值
	private double lowValue;//最低值
	private double closeValue;//收盘价
	private double adjustCloseValue;//复权收盘价
	private int code;//股票id
	private String name;//股票名称
	
	public StockInfoForK_lineVO() {
	}
	
	public StockInfoForK_lineVO(Date date, double openValue, double highValue, double lowValue, double closeValue,
			double adjustCloseValue, int code, String name) {
		super();
		this.date = date;
		this.openValue = openValue;
		this.highValue = highValue;
		this.lowValue = lowValue;
		this.closeValue = closeValue;
		this.adjustCloseValue = adjustCloseValue;
		this.code = code;
		this.name = name;
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

	@Override
	public String toString() {
		return "StockInfoForK_lineVO [date=" + date + ", openValue=" + openValue + ", highValue=" + highValue
				+ ", lowValue=" + lowValue + ", closeValue=" + closeValue + ", adjustCloseValue=" + adjustCloseValue
				+ ", code=" + code + ", name=" + name + "]";
	}
	
	
	
}
