package vo;

import java.util.Date;

/**
 * 
 * @author  yk
 *
 */
public class StockAverageValueVO {
    
	private  Date date;
	private  double averageValue;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getAverageValue() {
		return averageValue;
	}
	public void setAverageValue(double averageValue) {
		this.averageValue = averageValue;
	}
}
