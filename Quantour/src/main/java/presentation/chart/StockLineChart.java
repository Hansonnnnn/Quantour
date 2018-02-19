package presentation.chart;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import vo.StockAverageValueVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by xiaoJun on 2017/3/13.
 */
public class StockLineChart extends VBox{

    private ArrayList<StockAverageValueVO> averageValueList;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private XYChart.Series series;
    private LineChart<String,Number> lineChart;
    private double highValue = 0;
    private double lowValue;

    /**
     * 用于画均线图的构造函数
     * @param averageValueList
     */
    public StockLineChart(ArrayList<StockAverageValueVO> averageValueList){
        this.averageValueList = averageValueList;
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        getMaxAndMinValue();
        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(highValue * 1.02);
        yAxis.setLowerBound(lowValue * 0.98);
        yAxis.setTickUnit((highValue * 1.02 - lowValue * 0.98) / 10);
        lineChart = new LineChart<String,Number>(xAxis,yAxis);
        series = new XYChart.Series();
        getSingleData();//获取单组数据
        lineChart.setPrefSize(920,600);
        lineChart.setMaxSize(920,600);
        lineChart.setMinSize(920,600);
        lineChart.setAnimated(true);
        lineChart.getData().add(series);
        lineChart.setCreateSymbols(false);
        lineChart.setVerticalGridLinesVisible(false);
        this.getChildren().add(lineChart);
    }




    /**
     * 获取折线图的数据
     */
    private void getSingleData(){
        for(int i = 0; i < averageValueList.size(); i++){
            StockAverageValueVO stockAverageValueVO = averageValueList.get(i);
            series.getData().add(new XYChart.Data(dateToString(stockAverageValueVO.getDate()),stockAverageValueVO.getAverageValue()));
        }
    }

    /**
     * 将Date转成yyyy-MM-dd样式的字符串
     */
    private String dateToString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String dateString = dateFormat.format(date);
        return dateString;
    }

    /**
     *获取折线图数据的最大值和最小值
     */
    private void getMaxAndMinValue(){
        lowValue = averageValueList.get(0).getAverageValue();
        for(int i = 0; i < averageValueList.size(); i++){
            double averageValue = averageValueList.get(i).getAverageValue();
            if(highValue < averageValue){
                highValue = averageValue;
            }
            if(lowValue > averageValue){
                lowValue = averageValue;
            }
        }
    }

    /**
     * 设置折线图的动画效果
     */
    private void addAnimation(){
        Timeline tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(500),
                (ActionEvent actionEvent) -> {
                    lineChart.getData().stream().forEach((series) -> {
                        series.getData().stream().forEach((data) -> {
                            data.setYValue(data.getYValue());
                        });
                    });
                }
        ));

        tl.setCycleCount(Animation.INDEFINITE);
        tl.setAutoReverse(true);
        tl.play();
    }
}
