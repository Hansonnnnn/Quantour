package vo;
/**
 * 
 * @author yk
 *用来进行比较排序均线回归的类
 */
public class AVGLineValueVO {
     String name;
     double AVGValue;
     double todayValue;
     
     public AVGLineValueVO(String name,double AVGValue,double todayValue){
    	 this.name=name;
    	 this.AVGValue=AVGValue;
    	 this.todayValue=todayValue;
     }
     public double getTodayValue() {
		return todayValue;
	}
	public void setTodayValue(double todayValue) {
		this.todayValue = todayValue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getAVGValue() {
		return AVGValue;
	}
	public void setAVGValue(double aVGValue) {
		this.AVGValue = aVGValue;
	}
	
     
     
}
