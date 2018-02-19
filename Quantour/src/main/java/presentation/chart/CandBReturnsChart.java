package presentation.chart;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import vo.LogVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by xiezhenyu on 2017/3/28.
 * 该类为绘制累计收益率和基准收益率的折线图
 */

public class CandBReturnsChart extends VBox {
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private XYChart.Series series1;
    private XYChart.Series series2;
    private LineChart<String, Number> lineChart;
    private double high = 0;
    private double low = 0;

    public CandBReturnsChart(Map<Date, Double> accumulativeRate, Map<Date, Double> baseAccumulativeRate) {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        series1 = new XYChart.Series();
        series1.setName("策略");
        series2 = new XYChart.Series();
        series2.setName("基准");
        getLogRate(accumulativeRate, baseAccumulativeRate);
//        getMaxAndMinInLogValue(cumulativeLogRates, baseLogRates);
//        yAxis.setAutoRanging(false);
//        yAxis.setUpperBound(high * 1.02);
//        yAxis.setLowerBound(low * 0.98);
//        yAxis.setTickUnit((high * 1.02 - low * 0.98) / 15);
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setAnimated(true);
        lineChart.getData().addAll(series1, series2);
        lineChart.setTitle("累计收益率");
        lineChart.setPrefSize(700, 600);
        lineChart.setCreateSymbols(false);
        lineChart.setVerticalGridLinesVisible(false);
        this.getChildren().add(lineChart);
        this.setPrefSize(800, 700);
        this.getStylesheets().add(getClass().getResource("../css/OpStock.css").toExternalForm());
    }

    /**
     * 加入图表数据
     *
     * @param accumulativeRate     策略累计收益率
     * @param baseAccumulativeRate 基准累计收益率
     */
    private void getLogRate(Map<Date, Double> accumulativeRate, Map<Date, Double> baseAccumulativeRate) {
        for (Map.Entry<Date, Double> entry : accumulativeRate.entrySet()) {
            series1.getData().add(new XYChart.Data(dateToString(entry.getKey()), entry.getValue()));
        }
        for (Map.Entry<Date, Double> entry : baseAccumulativeRate.entrySet()) {
            series2.getData().add(new XYChart.Data(dateToString(entry.getKey()), entry.getValue()));
        }
    }

    /**
     * 获取对数收益率的最大值和最小值
     */
    private void getMaxAndMinInLogValue(ArrayList<LogVO> list1, ArrayList<LogVO> list2) {
        high = list1.get(0).getLogValue();
        low = list1.get(0).getLogValue();
        for (int i = 0; i < list1.size(); i++) {
            double closeValue1 = list1.get(i).getLogValue();
            double closeValue2 = list2.get(i).getLogValue();
            if (high < closeValue1) {
                high = closeValue1;
            }
            if (low > closeValue1) {
                low = closeValue1;
            }
            if (high < closeValue2) {
                high = closeValue2;
            }

            if (low > closeValue2) {
                low = closeValue2;
            }
        }
    }

    /**
     * 将日期Date转化成yyyy-MM-dd的格式
     */
    private String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String dateString = dateFormat.format(date);
        return dateString;
    }
}
