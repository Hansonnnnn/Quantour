package bussinesslogic.helper;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.experimental.theories.Theories;

import data.StockDataServiceImpl;
import dataService.StockDataService;
import model.StockPlate;
import po.StockPO;
import utility.DateHelper;

public class PlateProfit {
	
    public static double getPlateProfit(Date endDate,StockPlate plate,int processDays){
    	StockDataService service=new StockDataServiceImpl();
    	Date startDate=DateHelper.add(endDate, -processDays);
    	Map<Date, StockPO> base=new LinkedHashMap<>();
    	int csi300=300;
    	int sme=399005;
    	int chinext=399006;
    	switch (plate){
    	case CSI300: 
    		base=service.getSpecificDateStock(startDate, endDate, csi300);
    	    if(!base.containsKey(startDate)){
                base.put(startDate, getStartDayStockPo(startDate, csi300));
    	    }
    		break;
    	case SME:
    		base=service.getSpecificDateStock(startDate, endDate, sme);
    		if(!base.containsKey(startDate)){
                base.put(startDate, getStartDayStockPo(startDate, sme));
    	    }
    		break;
    	case CHINEXT:
    		base =service.getSpecificDateStock(startDate, endDate, chinext);
    		if(!base.containsKey(startDate)){
                base.put(startDate, getStartDayStockPo(startDate, chinext));
    	    }
    		break;
    	}
    	double startPrice=0.0;
    	double endPrice =0.0;
//    	while(!base.containsKey(startDate)){
//    		startDate=DateHelper.add(startDate, 1);
//    	}
    	startPrice=base.get(startDate).getAdjustCloseValue();
    	
    	while(!base.containsKey(endDate)){
    		endDate=DateHelper.add(endDate, -1);
    	}
    	
    	endPrice=base.get(endDate).getAdjustCloseValue();
    	return (endPrice-startPrice)/startPrice;
    }
    
    private static StockPO getStartDayStockPo(Date startDate,int stockId){
    	StockDataService service=new StockDataServiceImpl();
    	Date temp=new Date();
    	temp=startDate;
    	Map<Date, StockPO> startPO=new LinkedHashMap<>();
    	startPO=service.getSpecificDateStock(temp, temp, stockId);
    	while(startPO.size()==0){
    		temp=DateHelper.add(temp, -1);
    		startPO=service.getSpecificDateStock(temp, temp, stockId);
    	}
    	return startPO.get(temp);
    }
}
