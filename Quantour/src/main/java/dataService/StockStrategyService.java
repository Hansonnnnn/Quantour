package dataService;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import po.StockPO;
import po.YieldPO;

public interface StockStrategyService {
   
    /**
     *计算选定股票的所需要的股票 ，需要确定当天某股票是否可以交易，不能交易的需要剔除
     * @param selectedLists 所选股票名称或编号
     * @param Growdays 形成期的天数
     * @param startDate 回测的起止日期
     * @return 返回所选股票的名称
     */
    public ArrayList<YieldPO> getYieldOfSelectsStocks(ArrayList<String> selectedLists , int Growdays,Date startDate);
    
    public Map<String, Map<Date, StockPO>> getSomeStocksOfSomeDays(Date date,int days,ArrayList<String> stockLists);
        
    public Map<String, StockPO> getSomeStocks(Date date,ArrayList<String> stockLists);
}
