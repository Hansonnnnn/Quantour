package data;

import dataService.BlockDataService;
import model.StockPlate;
import po.StockPO;

import java.util.Date;
import java.util.Map;

import dao.dao.StockDao;
import dao.daoImpl.StockDaoImpl;

/**
 * Created by xiezhenyu on 2017/4/5
 * 该类实现BlockDataService
 */
public class BlockDataServiceImpl implements BlockDataService{
	private StockDao stockDaoImpl;

	public BlockDataServiceImpl(){
		stockDaoImpl = new StockDaoImpl();
	}

	@Override
	public Map<Integer, StockPO> getStocksByDateAndPlate(Date date, StockPlate plate) {
		try {
			return stockDaoImpl.getStocksByDateAndPlate(date, plate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

  
}
