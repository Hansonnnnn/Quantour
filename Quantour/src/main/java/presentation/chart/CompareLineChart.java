package presentation.chart;

import dao.DateHelper;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import vo.LogVO;
import vo.StockCloseValueVO;

import javax.lang.model.type.ArrayType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by LENOVO on 2017/3/15.
 */
public class CompareLineChart extends VBox{

    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private XYChart.Series series1;
    private XYChart.Series series2;
    private LineChart<String,Number> lineChart;
    private double high = 0;
    private double low = 0;

    /**
     * 用于比较两支股票一段时间收盘价的构造函数
     * @param stock1 第一支股票这段时间的收盘价数据
     * @param stock2 第二支股票这段时间的收盘价数据
     */
    public CompareLineChart(Map<Date, StockCloseValueVO> stock1,Map<Date,StockCloseValueVO> stock2,String name1,String name2){
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        series1 = new XYChart.Series();
        series1.setName(name1);
        series2 = new XYChart.Series();
        series2.setName(name2);
        getCloseData(stock1,stock2);
        getMaxAndMinInCloseValue(stock1,stock2);
        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(high * 1.02);
        yAxis.setLowerBound(low * 0.98);
        yAxis.setTickUnit((high * 1.02 - low * 0.98) / 15);
        lineChart = new LineChart<String,Number>(xAxis,yAxis);
        lineChart.setAnimated(true);
        lineChart.getData().addAll(series1,series2);
        lineChart.setPrefSize(920,600);
        lineChart.setMinSize(920,600);
        lineChart.setMaxSize(920,600);
        lineChart.setCreateSymbols(false);
        lineChart.setVerticalGridLinesVisible(false);
        this.getChildren().add(lineChart);
    }

    /**
     * 用于比较两支股票一段时间对数收益率的构造函数
     * @param stockList1 第一支股票这段时间的对数收益率数据
     * @param stockList2 第二支股票这段时间的对数收益率数据
     */
    public CompareLineChart(ArrayList<LogVO> stockList1,ArrayList<LogVO> stockList2,String name1,String name2){
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        series1 = new XYChart.Series();
        series1.setName(name1);
        series2 = new XYChart.Series();
        series2.setName(name2);
        getLogData(stockList1,stockList2);
        getMaxAndMinInLogValue(stockList1,stockList2);
        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(high * 1.02);
        yAxis.setLowerBound(low * 0.98);
        yAxis.setTickUnit((high * 1.02 - low * 0.98) / 15);
        lineChart = new LineChart<>(xAxis,yAxis);
        lineChart.setAnimated(true);
        lineChart.getData().addAll(series1,series2);
        lineChart.setPrefSize(920,600);
        lineChart.setMinSize(920,600);
        lineChart.setMaxSize(920,600);
        lineChart.setCreateSymbols(false);
        lineChart.setVerticalGridLinesVisible(false);
        this.getChildren().add(lineChart);
    }

    /**
     * 获取收盘价数据
     */
    private void getCloseData(Map<Date, StockCloseValueVO> stock1,Map<Date,StockCloseValueVO> stock2){
        for (StockCloseValueVO stockCloseValueVO: stock1.values())
        {
            series1.getData().add(new XYChart.Data(dateToString(stockCloseValueVO.getDate()),stockCloseValueVO.getCloseValue()));
        }
        for(StockCloseValueVO stockCloseValueVO: stock2.values()){
            series2.getData().add(new XYChart.Data(dateToString(stockCloseValueVO.getDate()),stockCloseValueVO.getCloseValue()));
        }
    }

    /**
     * 获取收盘价数据的最大值和最小值
     */
    private void getMaxAndMinInCloseValue(Map<Date, StockCloseValueVO> stock1,Map<Date,StockCloseValueVO> stock2){
        for(StockCloseValueVO stockCloseValueVO: stock1.values()){
            low = stockCloseValueVO.getCloseValue();
            break;
        }
        for(StockCloseValueVO stockCloseValueVO: stock1.values()){
            if(high < stockCloseValueVO.getCloseValue()){
                high = stockCloseValueVO.getCloseValue();
            }
            if(low > stockCloseValueVO.getCloseValue()){
                low = stockCloseValueVO.getCloseValue();
            }
        }
        for(StockCloseValueVO stockCloseValueVO: stock2.values()){
            double closeValue = stockCloseValueVO.getCloseValue();
            if(high < closeValue){
                high = closeValue;
            }
            if(low > closeValue){
                low = closeValue;
            }
        }
    }


    /**
     * 获取对数收益率数据
     */
    private void getLogData(ArrayList<LogVO> list1, ArrayList<LogVO> list2){
        for(int i = 0; i < list1.size(); i++){
            series1.getData().add(new XYChart.Data(dateToString(list1.get(i).getDate()),list1.get(i).getLogValue()));
        }
        for(int i = 0; i < list2.size(); i++){
            series2.getData().add(new XYChart.Data(dateToString(list2.get(i).getDate()),list2.get(i).getLogValue()));
        }
    }

    /**
     * 获取对数收益率的最大值和最小值
     */
    private void getMaxAndMinInLogValue(ArrayList<LogVO> list1,ArrayList<LogVO> list2){
        high = list1.get(0).getLogValue();
        low = list1.get(0).getLogValue();
        for(int i = 0; i <list1.size(); i++){
            double closeValue1 = list1.get(i).getLogValue();
            double closeValue2 = list2.get(i).getLogValue();
            if(high < closeValue1){
                high = closeValue1;
            }
            if (low > closeValue1){
                low = closeValue1;
            }
            if(high < closeValue2){
                high = closeValue2;
            }

            if(low > closeValue2){
                low = closeValue2;
            }
        }
    }

    /**
     * 将日期Date转化成yyyy-MM-dd的格式
     */
    private String dateToString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String dateString = dateFormat.format(date);
        return dateString;
    }
}
