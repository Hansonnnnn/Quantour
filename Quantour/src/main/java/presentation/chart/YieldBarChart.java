package presentation.chart;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import vo.ColumnDiagramVO;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by LENOVO on 2017/4/16.
 */
public class YieldBarChart extends VBox{
    private ColumnDiagramVO columnDiagramVO;
    private BarChart<String,Number> barChart;
    private CategoryAxis xAxis;
    private NumberAxis yAix;
    private XYChart.Series series1;
    private XYChart.Series series2;
    private double high = 0;

    public YieldBarChart(ColumnDiagramVO columnDiagramVO){
        this.columnDiagramVO = columnDiagramVO;
        xAxis = new CategoryAxis();
        yAix = new NumberAxis();
        series1 = new XYChart.Series();
        series2 = new XYChart.Series();
        series1.setName("正收益次数");
        series2.setName("负收益次数");
        getRateData(columnDiagramVO.getPeData(),columnDiagramVO.getNeData());
//        getMaxValue(columnDiagramVO.getPeData(),columnDiagramVO.getNeData());
        barChart = new BarChart(xAxis,yAix);
        barChart.getData().addAll(series1,series2);
        barChart.setPrefSize(920,600);
        barChart.setMinSize(920,600);
        barChart.setMaxSize(920,600);
        this.getChildren().add(barChart);
        this.getStylesheets().add(getClass().getResource("../css/OpStock.css").toExternalForm());
    }

    /**
     * 表格添加数据
     * @param peData 正收益率
     * @param naData 负收益率
     */
    private void getRateData(Map<Integer,Integer> peData,Map<Integer,Integer> naData){
//        series1.getData().add(new XYChart.Data("1%",15));
//        series1.getData().add(new XYChart.Data("2%",16));
//        series1.getData().add(new XYChart.Data("3%",17));
//        series1.getData().add(new XYChart.Data("4%",18));
//        series1.getData().add(new XYChart.Data("5%",19));
//        series1.getData().add(new XYChart.Data("6%",20));
//
//        series2.getData().add(new XYChart.Data("1%",9));
//        series2.getData().add(new XYChart.Data("2%",14));
//        series2.getData().add(new XYChart.Data("3%",15));
//        series2.getData().add(new XYChart.Data("4%",15));
//        series2.getData().add(new XYChart.Data("5%",18));
//        series2.getData().add(new XYChart.Data("6%",20));

        for(Map.Entry<Integer,Integer> entry: peData.entrySet()){
            series1.getData().add(new XYChart.Data(toPercentage(Math.abs(entry.getKey())),entry.getValue()));
        }
        for(Map.Entry<Integer,Integer> entry: naData.entrySet()){
            series2.getData().add(new XYChart.Data(toPercentage(Math.abs(entry.getKey())),entry.getValue()));
        }
    }

    /**
     * 获取正收益率和负收益率频数的最大值
     * @param peData 正收益率
     * @param naData 负收益率
     */
    private void getMaxValue(Map<Double,Integer> peData,Map<Double,Integer> naData){
        for(int value:peData.values()){
            if(high < value){
                high = value;
            }
        }
        for(int value:naData.values()){
            if(high < value){
                high = value;
            }
        }
    }

    /**
     * 将double转换成保留两位小数的百分数
     */
    private String toPercentage(double num){
        DecimalFormat df = new DecimalFormat("#0.00");
        String result = df.format(num);
        return result+"%";
    }
}
