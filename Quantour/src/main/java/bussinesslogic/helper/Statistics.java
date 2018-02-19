package bussinesslogic.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;


public class Statistics {
	
    //计算期望
	public static double calculateAVG(Map<Date , Double> list){
    	double sum=0;
    	for (Entry<Date, Double> entry :list.entrySet()) {
    		sum+=entry.getValue();
    	}
    	return (sum/list.size());
    }
    //计算协方差
    public static double calaulateCOV(Map<Date, Double>list1,Map<Date, Double>list2){
    	Map<Date, Double> list=new LinkedHashMap<>();
    	for(Entry<Date, Double> entry:list1.entrySet()){
    		list.put(entry.getKey(), (entry.getValue()*list2.get(entry.getKey())));
    	}
    	return (calculateAVG(list)-(calculateAVG(list1)*calculateAVG(list2)));
    }
    //计算list的数据期望
    public static double calculateAVGList(ArrayList<Double> lists){
    	double avg=0;
    	for(int i=0;i<lists.size();i++){
    		avg+=lists.get(i);
    	}
    	return avg/lists.size();
    }
    
}
