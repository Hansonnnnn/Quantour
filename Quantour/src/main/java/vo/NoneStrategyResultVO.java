package vo;

import java.util.Date;
import java.util.Map;

public class NoneStrategyResultVO {
	
	//基准年化收益率
    private double baseAnnualizedRateOfReturn;
	
    //累计收益率，以日期记
    private Map<Date, Double> accumulativRrate;
    
    private int count;
    
    public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getBaseAnnualizedRateOfReturn() {
		return baseAnnualizedRateOfReturn;
	}

	public void setBaseAnnualizedRateOfReturn(double baseAnnualizedRateOfReturn) {
		this.baseAnnualizedRateOfReturn = baseAnnualizedRateOfReturn;
	}

	public Map<Date, Double> getAccumulativRrate() {
		return accumulativRrate;
	}

	public void setAccumulativRrate(Map<Date, Double> accumulativRrate) {
		this.accumulativRrate = accumulativRrate;
	}
}
