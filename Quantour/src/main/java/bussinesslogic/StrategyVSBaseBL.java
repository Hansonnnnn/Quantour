package bussinesslogic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import bussinesslogic.helper.AVGGetComparator;
import bussinesslogic.helper.DepositRate;
import bussinesslogic.helper.MomentumGetComparator;
import bussinesslogic.helper.PlateProfit;
import bussinesslogic.helper.Statistics;
import bussinesslogic.helper.StrategyHelper;
import bussinesslogicservice.StrategyVSBaseService;
import data.StockDataServiceImpl;
import data.StockStrategyServiceImpl;
import dataService.StockDataService;
import dataService.StockStrategyService;
import model.StockPlate;
import model.StrategyType;
import po.YieldPO;
import utility.DateHelper;
import vo.StrategyResultVO;

public class StrategyVSBaseBL implements StrategyVSBaseService{
    StockDataService stockDataService=new StockDataServiceImpl();
    StockStrategyService stockStrategyService = new StockStrategyServiceImpl();
    @Override
	public StrategyResultVO useStrategyWithinAllStocks(Date startDate, Date endDate, 
			int becomingDays,int possessingDays,
			 StrategyType type) {
    	StrategyResultVO strategyResultVO=new StrategyResultVO();
    	
		Date lastDate=startDate;
		Date tempDate=startDate;
        
        //初始化，得到累计和每个持有期的收益率 
		Map<Date, Double> accumulativeRate=new LinkedHashMap<>();
		Map<Date, Double> everyDayRate=new LinkedHashMap<>();
		Map<Date, Double> baseAccumulativeRate=new LinkedHashMap<>();
		Map<Date, Double> baseEveryDayRate=new LinkedHashMap<>();
		accumulativeRate.put(startDate, 0.0);
		baseAccumulativeRate.put(startDate, 0.0);
		while (!startDate.after(endDate)){
			//加日期
			if(!DateHelper.add(startDate, possessingDays).after(endDate)){
            	startDate=DateHelper.add(startDate, possessingDays);
            }else {
            	startDate=DateHelper.add(endDate, 1);
            }
			ArrayList<String> limit=new ArrayList<>();//涨停、跌停的股票
			ArrayList<String> stockList=new ArrayList<>();//等待买入的股票
			ArrayList<String> baseLists=new ArrayList<>();//基准股票
			
             stockList=StrategyHelper.getGreastSttocksInAll(lastDate,becomingDays, type);
             
			//计算持有期内的收益
            ArrayList<YieldPO> getProfitRate=stockStrategyService.getYieldOfSelectsStocks(stockList, possessingDays, startDate);
            double profitRate=0;
            for(int i=0;i<getProfitRate.size();i++){
            	profitRate+=getProfitRate.get(i).getYield();
            }
            profitRate/=getProfitRate.size();
            //每个持有期的收益率
            everyDayRate.put(startDate, profitRate);
            
            //每个持有期的累积
            profitRate+=1;
            profitRate*=(1+accumulativeRate.get(lastDate));
            accumulativeRate.put(startDate, profitRate-1);
            
            Date ldDate=new Date();
            ldDate=lastDate;
            //计算每个持有期的基准的收益率
            baseLists=MomentumGetComparator.removeIncreaseLimitInAll(lastDate);
            // 如果baseLists为空就重新选
            while(baseLists.size()==0){
            	ldDate=DateHelper.add(ldDate, 1);
            	baseLists=MomentumGetComparator.removeIncreaseLimitInAll(ldDate);
            }
            ArrayList<YieldPO> baseProfit=stockStrategyService.getYieldOfSelectsStocks(baseLists, possessingDays, startDate);

            double baseProfitRate=0.0;
            for(int i=0;i<baseProfit.size();i++){
		    	baseProfitRate+=baseProfit.get(i).getYield();
		    }
            baseProfitRate/=baseProfit.size();
            baseEveryDayRate.put(startDate, baseProfitRate);
            //计算基准累计收益率
            baseProfitRate+=1;
            baseProfitRate*=(1+accumulativeRate.get(lastDate));
            baseAccumulativeRate.put(startDate, baseProfitRate-1);
            lastDate=startDate;
            
		}
		//设置值
		strategyResultVO.setAccumulativRate(accumulativeRate);
		strategyResultVO.setBaseAccumulativRate(baseAccumulativeRate);
		strategyResultVO.setEveryDayBaseRate(baseEveryDayRate);
		strategyResultVO.setEveryDayRate(everyDayRate);
		
		//计算参数
		return this.calculateResult(strategyResultVO,tempDate,endDate,possessingDays);
		}

    @Override
	public StrategyResultVO useStrategyGenerally(Date startDate, Date endDate, StockPlate plate, int becomingDays,
			int possessingDays,  StrategyType type) {
		Date lastDate=new Date();
		lastDate=startDate;
		
		Date tempDate=new Date();
		tempDate=startDate;
		StrategyResultVO strategyResultVO=new StrategyResultVO();
        
        //初始化，得到累计和每日的收益率 
		Map<Date, Double> accumulativeRate=new LinkedHashMap<>();
		Map<Date, Double> everyDayRate=new LinkedHashMap<>();
		Map<Date, Double> baseAccumulativeRate=new LinkedHashMap<>();
		Map<Date, Double> baseEveryDayRate=new LinkedHashMap<>();
		accumulativeRate.put(startDate, 0.0);
		baseAccumulativeRate.put(startDate, 0.0);
		while (!startDate.after(endDate)){
			//加日期
            if(!DateHelper.add(startDate, possessingDays).after(endDate)){
            	startDate=DateHelper.add(startDate, possessingDays);
            }else{
            	startDate=DateHelper.add(endDate, 1);
            }
            
			ArrayList<String> limitDown=new ArrayList<>();//跌停的股票
			ArrayList<String> stockList=new ArrayList<>();//等待买入的股票
			ArrayList<String> baseLists=new ArrayList<>();//基准股票
			
             stockList=StrategyHelper.getGreastSttocksInPlate(lastDate, possessingDays, type, plate);			
			//计算持有期内的收益
            ArrayList<YieldPO> getProfitRate=stockStrategyService.getYieldOfSelectsStocks(stockList, possessingDays, startDate);
            double profitRate=0;
            for(int i=0;i<getProfitRate.size();i++){
            	profitRate+=getProfitRate.get(i).getYield();
            }
            profitRate/=getProfitRate.size();
            
            //每个形成期的收益率
            everyDayRate.put(startDate, profitRate);
            
            //每个持有期的累积
            profitRate+=1;
            profitRate*=(1+accumulativeRate.get(lastDate));
            accumulativeRate.put(startDate, profitRate-1);
            
            Date ldDate=new Date();
            ldDate=lastDate;
            //计算每个持有期的基准的收益率
//            baseLists=MomentumGetComparator.removeIncreaseLimitInAll(lastDate);
//            // 如果baseLists为空就重新选
//            while(baseLists.size()==0){
//            	ldDate=DateHelper.add(ldDate, 1);
//            	baseLists=MomentumGetComparator.removeIncreaseLimitInAll(ldDate);
//            }
//            
//            ArrayList<YieldPO> baseProfit=stockStrategyService.getYieldOfSelectsStocks(baseLists, possessingDays,startDate);
		    double baseProfitRate=0.0;
//            for(int i=0;i<baseProfit.size();i++){
//		    	baseProfitRate+=baseProfit.get(i).getYield();
//		    }
//            baseProfitRate/=baseProfit.size();
            baseProfitRate=PlateProfit.getPlateProfit(startDate, plate, possessingDays);
            baseEveryDayRate.put(startDate, baseProfitRate);
            
            //计算基准累计收益率
            baseProfitRate+=1;
            baseProfitRate*=(1+accumulativeRate.get(lastDate));
            baseAccumulativeRate.put(startDate, baseProfitRate-1);
		    
            lastDate=startDate;
            
		}
		//设置值
		strategyResultVO.setAccumulativRate(accumulativeRate);
		strategyResultVO.setBaseAccumulativRate(baseAccumulativeRate);
		strategyResultVO.setEveryDayBaseRate(baseEveryDayRate);
		strategyResultVO.setEveryDayRate(everyDayRate);
		
		//计算参数
		return this.calculateResult(strategyResultVO,tempDate,endDate,possessingDays);
	}

	@Override
	public StrategyResultVO useStrategyWithinSomeStocks(Date startDate, Date endDate, ArrayList<String> selectedStocks,
			int becomingDays, int possessingDays,  StrategyType type) {
		Date lastDate=startDate;
		Date tempDate=startDate;
		StrategyResultVO strategyResultVO=new StrategyResultVO();
        
        //初始化，得到累计和每日的收益率 
		Map<Date, Double> accumulativeRate=new LinkedHashMap<>();
		Map<Date, Double> everyDayRate=new LinkedHashMap<>();
		Map<Date, Double> baseAccumulativeRate=new LinkedHashMap<>();
		Map<Date, Double> baseEveryDayRate=new LinkedHashMap<>();
		accumulativeRate.put(startDate, 0.0);
		baseAccumulativeRate.put(startDate, 0.0);
		while (startDate.before(endDate)){
			//加日期
            if(!DateHelper.add(startDate, possessingDays).after(endDate)){
            	startDate=DateHelper.add(startDate, possessingDays);
            }else{
            	startDate=DateHelper.add(endDate, 1);
            }
            
			ArrayList<String> limitDown=new ArrayList<>();//跌停的股票
			ArrayList<String> stockList=new ArrayList<>();//等待买入的股票
			ArrayList<String> baseLists=new ArrayList<>();//基准股票
			
			stockList=StrategyHelper.getGreastSttocksInSelect(lastDate, possessingDays, type, selectedStocks);
			//计算持有期内的收益
            ArrayList<YieldPO> getProfitRate=stockStrategyService.getYieldOfSelectsStocks(stockList, possessingDays, startDate);
            double profitRate=0;
            for(int i=0;i<getProfitRate.size();i++){
            	profitRate+=getProfitRate.get(i).getYield();
            }
            
            profitRate/=getProfitRate.size();
            
            //每个形成期的收益率
            everyDayRate.put(startDate, profitRate);
            
            //每个持有期的累积
            profitRate+=1;
            profitRate*=(1+accumulativeRate.get(lastDate));
            accumulativeRate.put(startDate, profitRate-1);
            
//            Date ldDate=new Date();
//            ldDate=lastDate;
            //计算每个持有期的基准的收益率
//            baseLists=MomentumGetComparator.removeIncreaseLimitInAll(lastDate);
            // 如果baseLists为空就重新选
//            while(baseLists.size()==0){
//            	ldDate=DateHelper.add(ldDate, 1);
//            	baseLists=MomentumGetComparator.removeIncreaseLimitInAll(ldDate);
//            }
            ArrayList<YieldPO> baseProfit=stockStrategyService.getYieldOfSelectsStocks(selectedStocks, possessingDays,startDate);
            double baseProfitRate=0.0;
            for(int i=0;i<baseProfit.size();i++){
		    	baseProfitRate+=baseProfit.get(i).getYield();
		    }
            baseProfitRate/=baseProfit.size();
            baseEveryDayRate.put(startDate, baseProfitRate);
            
            //计算基准累计收益率
            baseProfitRate+=1;
            baseProfitRate*=(1+accumulativeRate.get(lastDate));
            baseAccumulativeRate.put(startDate, baseProfitRate-1);
		    
            lastDate=startDate;
		}
		//设置值
		strategyResultVO.setAccumulativRate(accumulativeRate);
		strategyResultVO.setBaseAccumulativRate(baseAccumulativeRate);
		strategyResultVO.setEveryDayBaseRate(baseEveryDayRate);
		strategyResultVO.setEveryDayRate(everyDayRate);
		
		//计算参数
		return this.calculateResult(strategyResultVO,tempDate,endDate,possessingDays);
		}

		
		//计算其他的值
		private StrategyResultVO calculateResult(StrategyResultVO strategyResultVO,Date startDate,Date endDate,int holdDays){
			strategyResultVO.setCount(strategyResultVO.getAccumulativRate().size());
			
			//计算年化收益率
			double baseYear=0.0;
			baseYear=(Statistics.calculateAVG(strategyResultVO.getEveryDayBaseRate())/holdDays)*365;
			strategyResultVO.setBaseAnnualizedRateOfReturn(format((baseYear*100)));

			double profitYear=0.0;
			profitYear=(Statistics.calculateAVG(strategyResultVO.getEveryDayRate())/holdDays)*365;
			strategyResultVO.setAnnualizedRateOfReturn(format((profitYear*100)));
			
			//首先计算贝塔系数
		    double beta=0;
			beta=Statistics.calaulateCOV(strategyResultVO.getEveryDayBaseRate(), strategyResultVO.getEveryDayRate());
			beta/=Statistics.calaulateCOV(strategyResultVO.getEveryDayBaseRate(), strategyResultVO.getEveryDayBaseRate());
			strategyResultVO.setBeta(format(beta));
			
			//计算夏普比率
			double sharpRate=0;
			sharpRate=Statistics.calculateAVG(strategyResultVO.getEveryDayRate())-DepositRate.getRate(startDate);
			sharpRate/=Math.sqrt(Statistics.calaulateCOV(strategyResultVO.getEveryDayRate(), strategyResultVO.getEveryDayRate()));
			strategyResultVO.setSharpeRatio(format(sharpRate));
			
			//计算阿尔法
			double alpha=0;
			alpha=(profitYear-DepositRate.getRate(startDate));
			double temp=( Statistics.calculateAVG(strategyResultVO.getEveryDayBaseRate())- DepositRate.getRate(startDate));
			alpha=alpha-(beta*temp);
			strategyResultVO.setAlpha(format(alpha));					
            //计算最大回撤
            double  maxBack=0;
            double  peak=0;
            ArrayList<Double> list=new ArrayList<>();
       
            for (Map.Entry<Date, Double > entry : strategyResultVO.getEveryDayRate().entrySet()) {
           			list.add(entry.getValue());
         	}
            for(int i=0;i<list.size();i++){
            	if(list.get(i)>peak){
            		peak=list.get(i);
            	}
            	double back=peak-list.get(i);
                if(back>maxBack){
                	maxBack=back;
                }
            }
            strategyResultVO.setBiggestReturn(format(maxBack*100));
            
            return strategyResultVO;
		}
		
		// 格式化浮点数结果
		private double format(double d) {
					DecimalFormat dFormat=new DecimalFormat("#0.00");
					String string1=dFormat.format(d);
					return Double .parseDouble(string1);
		}
}
