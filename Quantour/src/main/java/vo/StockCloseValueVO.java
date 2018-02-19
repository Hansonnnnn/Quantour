package vo;

import java.util.Date;

public class StockCloseValueVO {
	private Date date;
	private double closeValue;//收盘价
	private int code;//股票id
	private String name;//股票名称
	
	public StockCloseValueVO() {
	}
	public StockCloseValueVO(Date date, double closeValue, int code, String name) {
		super();
		this.date = date;
		this.closeValue = closeValue;
		this.code = code;
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getCloseValue() {
		return closeValue;
	}
	public void setCloseValue(double closeValue) {
		this.closeValue = closeValue;
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
		return "StockCloseValueVO [date=" + date + ", closeValue=" + closeValue + ", code=" + code + ", name=" + name
				+ "]";
	}

	
	
}
