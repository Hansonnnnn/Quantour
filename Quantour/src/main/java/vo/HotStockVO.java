package vo;

/**
 * 该类为最热股票的VO，初定选取最热股票的依据为：近一周内交易量最大的股票
 * @author wf
 *
 */
public class HotStockVO implements Comparable<HotStockVO>{
	private int Code;
	private String name;//股票的名称
	private long volume;
	public int getCode() {
		return Code;
	}
	public void setCode(int code) {
		Code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getVolume() {
		return volume;
	}
	public void setVolume(long volume) {
		this.volume = volume;
	}
	public HotStockVO(int code, String name, long volume) {
		super();
		Code = code;
		this.name = name;
		this.volume = volume;
	}
	
	public HotStockVO() {
	}
	@Override
	public int compareTo(HotStockVO o) {
		if (this.volume>o.volume) {
			return 1;
		}else if (this.volume==o.volume) {
			return 0;
		}else {
			return -1;	
		}
	}
	
	
}
