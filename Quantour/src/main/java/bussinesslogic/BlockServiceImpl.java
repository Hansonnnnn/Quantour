package bussinesslogic;

import bussinesslogic.helper.StockPOToVO;
import bussinesslogicservice.BlockService;
import data.BlockDataServiceImpl;
import dataService.BlockDataService;
import model.StockPlate;
import po.StockPO;
import vo.StockVO;

import java.util.Date;
import java.util.Map;

/**
 * Created by wf on 2017/4/6.
 * 该类实现BlockService接口
 */
public class BlockServiceImpl implements BlockService{
    private BlockDataService service;

    public BlockServiceImpl(){service = new BlockDataServiceImpl();}

    @Override
    public Map<Integer, StockVO> getStocksInBlock(Date date,StockPlate name) {
        Map<Integer, StockPO> stockPOs = service.getStocksByDateAndPlate(date, name);
        if (stockPOs!=null)
        {
            return StockPOToVO.stockPOMapToStockVOMap(stockPOs);
        }else
        {
            System.out.println("BlockServiceImpl getStocksInBlock(Date date,StockPlate name) error");
            return null;
        }
    }
}
