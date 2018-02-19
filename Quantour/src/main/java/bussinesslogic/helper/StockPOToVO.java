package bussinesslogic.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import po.StockPO;
import vo.StockInfoForK_lineVO;
import vo.StockVO;

public class StockPOToVO {

	public static ArrayList<StockInfoForK_lineVO> StockPOstoLineVOList(Map<Date, StockPO> stockPOs) {
		ArrayList<StockInfoForK_lineVO> list = new ArrayList<>();
		StockInfoForK_lineVO lineVO;
		for (StockPO stockPO : stockPOs.values()) {
			lineVO = new StockInfoForK_lineVO(stockPO.getDate(), stockPO.getOpenValue(), stockPO.getHighValue(),
					stockPO.getLowValue(), stockPO.getCloseValue(), stockPO.getAdjustCloseValue(), stockPO.getCode(),
					stockPO.getName());
			list.add(lineVO);
		}
		return list;

	}
	
	public static StockVO stockPoToVo(StockPO stockPO){
		StockVO stockVO=new StockVO();
		stockVO.setName(stockPO.getName());
		stockVO.setId(stockPO.getCode());
		stockVO.setHighValue(stockPO.getHighValue());
		stockVO.setLowValue(stockPO.getLowValue());
		stockVO.setOpenValue(stockPO.getOpenValue());
		stockVO.setCloseValue(stockPO.getCloseValue());
		stockVO.setVolume(stockPO.getVolume());
		stockVO.setLogRate(stockPO.getLogRate());
		
		double d=format((stockPO.getOpenValue()-stockPO.getCloseValue())/stockPO.getCloseValue());
		if (d>0) {
			stockVO.setAmount_Of_Increase(d);
		}else{
			stockVO.setAmount_Of_Drop(d);
		}
	
		return stockVO;
		
	}

	public static Map<Integer, StockVO> stockPOMapToStockVOMap(Map<Integer, StockPO> stockPOs)
	{
		StockVO stockVO = null;
		Map<Integer, StockVO> stockVOs = new LinkedHashMap<>();
		for (int code : stockPOs.keySet()) {
			stockVO = stockPoToVo(stockPOs.get(code));
			stockVOs.put(code, stockVO);
		}
		return stockVOs;
	}

	private static  double format(double d){
		BigDecimal b=new BigDecimal(d);
		double result=b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}
	
	
}
