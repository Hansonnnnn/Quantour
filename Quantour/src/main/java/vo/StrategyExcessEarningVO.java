package vo;

import java.util.LinkedHashMap;
import java.util.Map;

public class StrategyExcessEarningVO {
    //超额收益率
	private Map<Integer, Double> excessEarining;
	
	//策略胜率
	private Map<Integer, Double> strategyWinRate;
	
	public StrategyExcessEarningVO(){
		excessEarining=new LinkedHashMap<>();
		strategyWinRate=new LinkedHashMap<>();
	}
	
	public Map<Integer, Double> getExcessEarining() {
		return excessEarining;
	}
	public void setExcessEarining(Map<Integer, Double> excessEarining) {
		this.excessEarining = excessEarining;
	}
	public Map<Integer, Double> getStrategyWinRate() {
		return strategyWinRate;
	}
	public void setStrategyWinRate(Map<Integer, Double> strategyWinRate) {
		this.strategyWinRate = strategyWinRate;
	}
	
    
	
}
