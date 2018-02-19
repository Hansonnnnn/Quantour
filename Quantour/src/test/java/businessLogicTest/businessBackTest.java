package businessLogicTest;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import bussinesslogic.ColumnDiagramServiceImpl;
import bussinesslogic.StrategyEESBL;
import bussinesslogic.StrategyVSBaseBL;
import bussinesslogicservice.ColumnDiagramService;
import bussinesslogicservice.GetExcessEarningAndStrategyService;
import bussinesslogicservice.StrategyVSBaseService;
import model.StrategyType;
import vo.ColumnDiagramVO;
import vo.StrategyResultVO;

public class businessBackTest {
    ColumnDiagramService columnDiagramService=new ColumnDiagramServiceImpl();
    StrategyVSBaseService  strategyVSBaseBL=new StrategyVSBaseBL();
    GetExcessEarningAndStrategyService g=new StrategyEESBL();
	@Test
	public void test() {
	    	ColumnDiagramVO columnDiagramVO=
	    			columnDiagramService.getDataWithinAllStocks(
	    					new Date("2013/3/21"), new Date("2013/5/21"), 20, 10
	    					, StrategyType.MeanReversionDriven);
	        System.out.println(columnDiagramVO.getWinRate());
	        System.out.println(columnDiagramVO.getPeCircleNum());
	        System.out.println(columnDiagramVO.getNeCircleNum());
	}
	
	@Test
	public void test2(){
		StrategyResultVO strategyResultVO=strategyVSBaseBL.useStrategyWithinAllStocks(
				new Date("2013/3/21"), new Date("2013/5/21"), 20, 10, StrategyType.momentumDriven);
	    System.out.println(strategyResultVO.getAlpha());
	    System.out.println(strategyResultVO.getBeta());
        System.out.println(strategyResultVO.getAnnualizedRateOfReturn());
        System.out.println(strategyResultVO.getBiggestReturn());
        System.out.println(strategyResultVO.getSharpeRatio());
	}
}
