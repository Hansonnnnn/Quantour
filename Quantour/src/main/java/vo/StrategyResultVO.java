package vo;

import java.util.Date;
import java.util.Map;

public class StrategyResultVO {
	
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
    
    //累计收益率，以日期记
    private Map<Date, Double> accumulativRate;
    
    //每天的收益率
    private Map<Date, Double> everyDayRate;
    
    
	//基准的每天的收益率
    private Map<Date,Double> everyDayBaseRate;
    
    //基准年化收益率
    private double baseAnnualizedRateOfReturn;
	
    //基准累计收益率，以日期记
    private Map<Date, Double> baseAccumulativRate;
    
    //天数
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

	public Map<Date, Double> getAccumulativRate() {
		return accumulativRate;
	}

	public void setAccumulativRate(Map<Date, Double> accumulativRate) {
		this.accumulativRate = accumulativRate;
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

	public Map<Date, Double> getBaseAccumulativRate() {
		return baseAccumulativRate;
	}

	public void setBaseAccumulativRate(Map<Date, Double> baseAccumulativRate) {
		this.baseAccumulativRate = baseAccumulativRate;
	}
	
	public Map<Date, Double> getEveryDayRate() {
		return everyDayRate;
	}

	public void setEveryDayRate(Map<Date, Double> everyDayAccumulativeRate) {
		this.everyDayRate = everyDayAccumulativeRate;
	}

	public Map<Date, Double> getEveryDayBaseRate() {
		return everyDayBaseRate;
	}

	public void setEveryDayBaseRate(Map<Date, Double> everyDayBaseAccumulativeRate) {
		this.everyDayBaseRate = everyDayBaseAccumulativeRate;
	}

}
