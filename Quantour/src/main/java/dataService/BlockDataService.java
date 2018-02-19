package dataService;

import model.StockPlate;
import po.StockPO;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by xiezhenyu on 2017/4/5.
 */
public interface BlockDataService {
	public Map<Integer, StockPO> getStocksByDateAndPlate(Date date,StockPlate plate);
}
