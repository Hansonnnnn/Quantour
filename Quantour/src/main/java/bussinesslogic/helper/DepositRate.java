package bussinesslogic.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DepositRate {
    public static double  getRate(Date date){
    	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
    	String start =simpleDateFormat.format(date);
    	String year=start.substring(0,4);
    	int year1=Integer.parseInt(year);
    	switch (year1){
    	case 2005: return 0.0225;
    	case 2006: return 0.0252;
    	case 2007: return 0.036; 
    	case 2008: return 0.0306;
    	case 2009: return 0.0225;
    	case 2010: return 0.0225;
    	case 2011: return 0.0325;
    	case 2012: return 0.03;
    	case 2013: return 0.0325;
    	case 2014: return 0.033;
    	}
    	return 0;
    }
}
