package po;

public class YieldPO {
	String name;
	double yield;
	
	
	public YieldPO(String name, double yield) {
		super();
		this.name = name;
		this.yield = yield;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getYield() {
		return yield;
	}
	public void setYield(double yield) {
		this.yield = yield;
	}
	@Override
	public String toString() {
		return "YieldPO [name=" + name + ", yield=" + yield + "]";
	}
	
	
}
