package bussinesslogicservice;

import model.StockPlate;
import vo.StockVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by xiezhenyu on 2017/4/5.
 * 该接口类包含所有关于板块内容的操作
 */
public interface BlockService {
    /**
     * 该方法返回某个板块内的所有股票
     * @param name 板块名称
     * @return
     */
    public Map<Integer, StockVO> getStocksInBlock(Date date,StockPlate name);
}
