package vo;
/**
 * 
 * @author yangkai
 *这个vo主要用于两个stock的比较
 *数据包括但不局限于这两只股票这段时间的，最低值、最高值、涨幅/跌幅
 */
public class StockCompare {
	private int id;	//股票的id
    private String  name;	//股票的名称
    private double lowistPrice;	//股票的历史最低价格
    private double highistPrice;	//股票的历史最高价格
    private double amount_Of_Drop;	//涨幅，如果为0那么可是没有涨
    private double amount_Of_Increase;		//跌幅，如果为0那么可能没有跌
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public double getLowistPrice() {
		return lowistPrice;
	}
	public void setLowistPrice(double lowistPrice) {
		this.lowistPrice = lowistPrice;
	}
	
	public double getHighistPrice() {
		return highistPrice;
	}
	public void setHighistPrice(double highistPrice) {
		this.highistPrice = highistPrice;
	}
	
	public double getAmount_Of_Drop() {
		return amount_Of_Drop;
	}
	public void setAmount_Of_Drop(double amount_Of_Drop) {
		this.amount_Of_Drop = amount_Of_Drop;
	}
	
	public double getAmount_Of_Increase() {
		return amount_Of_Increase;
	}
	public void setAmount_Of_Increase(double amount_Of_Increase) {
		this.amount_Of_Increase = amount_Of_Increase;
	}
}
