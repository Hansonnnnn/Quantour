package presentation.chart;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import vo.ExcessEarningsAndStrategyVO;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by xiezhenyu on 2017/3/29.
 * 该类为表示超额收益率和策略胜率与不同形成期/持有期之间关系绘制面积图
 */
public class ExcessEarningsAndStrategyChart extends VBox{
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private XYChart.Series series;
    private AreaChart<String, Number> areaChart;
    private int Xhigh;
    private int Xlow;
    private double Yhigh;
    private double Ylow;

    public ExcessEarningsAndStrategyChart(String title, Map<Integer,Double> dataMap) {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        series = new XYChart.Series();
//        series.setName(name);
        getDataForChart(dataMap);
//        getMaxAndMinValueOfXY(dataMap);
        areaChart = new AreaChart<String, Number>(xAxis, yAxis);
        areaChart.setAnimated(true);
        areaChart.setTitle(title);
        areaChart.getData().add(series);
        areaChart.setPrefSize(700, 450);
        areaChart.setVerticalGridLinesVisible(false);
        this.getChildren().add(areaChart);
    }

    private void getDataForChart(Map<Integer,Double> dataMap) {
        for(Map.Entry<Integer,Double> entry: dataMap.entrySet()){
            series.getData().add(new XYChart.Data(String.valueOf(entry.getKey()),entry.getValue()));
        }
    }

    private void getMaxAndMinValueOfXY(ArrayList<ExcessEarningsAndStrategyVO> dataList)
    {
        Xhigh = dataList.get(0).getPeriod();
        Xlow = dataList.get(0).getPeriod();
        Yhigh = dataList.get(0).getRate();

    }
}
