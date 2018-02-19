package presentation;

import bussinesslogic.StockInfoBL;
import bussinesslogicservice.StockInfoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import presentation.chart.CompareLineChart;
import presentation.dialog.MyDialog;
import utility.DateHelper;
import utility.DatePickerHelper;
import vo.LogVO;
import vo.StockCloseValueVO;
import vo.StockCompare;
import vo.StockVO;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ComparePageUIController {

    @FXML
    private TextField idTextField1;
    @FXML
    private TextField idTextField2;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Label stockNameOneLabel;
    @FXML
    private Label stockNameTwoLabel;
    @FXML
    private Label oneHighLabel;
    @FXML
    private Label twoHighLabel;
    @FXML
    private Label oneLowLabel;
    @FXML
    private Label twoLowLabel;
    @FXML
    private Label oneUpDownLabel;
    @FXML
    private Label twoUpDownLabel;
    @FXML
    private Label biggerNameLabelOne;
    @FXML
    private Label biggerNameLabelTwo;
    @FXML
    private Button sliderLabel;
    @FXML
    private VBox chartVBox;
    @FXML
    private HBox nameHBox;
    @FXML
    private Label logLabel1;
    @FXML
    private Label logLabel2;
    @FXML
    private ToggleButton closeValueButton;
    @FXML
    private ToggleButton logValueButton;

    private Stage stage;
    private ScrollPane contentScrollPane;
    private VBox thisVBox;
    private DateHelper dateHelper;
    private StockInfoService stockInfoService;
    private Map<Date, StockCloseValueVO> stockMap1;
    private Map<Date, StockCloseValueVO> stockMap2;
    private ArrayList<LogVO> stockList1;
    private ArrayList<LogVO> stockList2;
    private String stockName1;
    private String stockName2;
    private String redStyle = "-fx-text-fill: #C10D01;-fx-font-size: 18px;";
    private String greenStyle = "-fx-text-fill: #2C7C02;-fx-font-size: 18px;";
    public void init(VBox thisVBox,Stage stage,ScrollPane contentScrollPane,String stock1,String stock2,LocalDate startDate,LocalDate endDate) {
        this.stage = stage;
        this.contentScrollPane = contentScrollPane;
        this.thisVBox = thisVBox;
        dateHelper = new DateHelper();
        stockInfoService = new StockInfoBL();
        DatePickerHelper.datePickerCustomize(startDatePicker,endDatePicker);
        idTextField1.setText(stock1);
        idTextField2.setText(stock2);
        stockName1 = stock1;
        stockName2 = stock2;
        showCompareInfo(stock1,stock2,DateHelper.localDateToDate(startDate),DateHelper.localDateToDate(endDate));
    }

    /**
     * 点击比较按钮，显示柱状图，比较两支股票的情况
     */
    @FXML
    private void compare() {
        String stock1 = null;
        String stock2 = null;
        if (idTextField1.getText() != null && !idTextField1.getText().isEmpty() && idTextField2.getText() != null && !idTextField2.getText().isEmpty()) {
            stock1 = idTextField1.getText();
            stock2 = idTextField2.getText();
            if (startDatePicker.getValue() != null && endDatePicker.getValue() != null) {
                Date startDate = dateHelper.localDateToDate(startDatePicker.getValue());
                Date endDate = dateHelper.localDateToDate(endDatePicker.getValue());
                showCompareInfo(stock1,stock2,startDate,endDate);
            } else {
                //提示对话框
                new MyDialog(stage,"请输入完整日期信息",2);
            }
        } else {
            //提示对话框
            new MyDialog(stage,"请输入完整股票名称",2);
        }
    }

    /**
     * 显示对比后的股票数据
     * @param stock1 股票1的名称或代码
     * @param stock2 股票2的名称或代码
     * @param startDate 开始日期
     * @param endDate 结束日期
     */
    private void showCompareInfo(String stock1,String stock2,Date startDate,Date endDate){
        stockMap1 = stockInfoService.getHistoryCloseValue(startDate,endDate,stock1);
        stockMap2 = stockInfoService.getHistoryCloseValue(startDate,endDate,stock2);
        stockList1 = stockInfoService.getLogRate(startDate,endDate,stock1);
        stockList2 = stockInfoService.getLogRate(startDate,endDate,stock2);
        Map<Integer, StockCompare> stockInfo = stockInfoService.compareTwoStock(startDate, endDate, stock1, stock2);
        if (stockMap1 != null && stockMap2 != null && stockInfo !=null) {
            StockCompare one = stockInfo.get(1);
            StockCompare two = stockInfo.get(2);
            stockName1 = one.getName();
            stockName2 = two.getName();
            stockNameOneLabel.setText(one.getName());
            stockNameTwoLabel.setText(two.getName());
            oneHighLabel.setText(Double.toString(one.getHighistPrice()));
            twoHighLabel.setText(Double.toString(two.getHighistPrice()));
            oneLowLabel.setText(Double.toString(one.getLowistPrice()));
            twoLowLabel.setText(Double.toString(two.getLowistPrice()));
            logLabel1.setText(String.valueOf(stockInfoService.getLogRateVariance(startDate,endDate,stock1)));
            logLabel2.setText(String.valueOf(stockInfoService.getLogRateVariance(startDate,endDate,stock2)));
            if (one.getAmount_Of_Increase() == 0) {
                oneUpDownLabel.setText(String.valueOf(one.getAmount_Of_Drop())+"%");
            } else {
                oneUpDownLabel.setText(String.valueOf(one.getAmount_Of_Increase())+"%");
            }

            if (two.getAmount_Of_Increase() == 0) {
                twoUpDownLabel.setText(String.valueOf(two.getAmount_Of_Drop())+"%");
            } else {
                twoUpDownLabel.setText(String.valueOf(two.getAmount_Of_Increase())+"%");
            }
            nameHBox.setVisible(true);
            closeValueButton.setDisable(false);
            logValueButton.setDisable(false);
            sliderLabel.setDisable(false);
            if (one.getAmount_Of_Increase() == 0) {
                oneUpDownLabel.setStyle(greenStyle);
                oneUpDownLabel.setText(Double.toString(one.getAmount_Of_Drop()));
            } else {
                oneUpDownLabel.setStyle(redStyle);
                oneUpDownLabel.setText(Double.toString(one.getAmount_Of_Increase()));
            }

            if (two.getAmount_Of_Increase() == 0) {
                twoUpDownLabel.setStyle(greenStyle);
                twoUpDownLabel.setText(Double.toString(two.getAmount_Of_Drop()));
            } else {
                twoUpDownLabel.setStyle(redStyle);
                twoUpDownLabel.setText(Double.toString(two.getAmount_Of_Increase()));
            }
            nameHBox.setVisible(true);
            biggerNameLabelOne.setText(one.getName());
            biggerNameLabelTwo.setText(two.getName());
            chartVBox.getChildren().remove(0);
            chartVBox.getChildren().add(new CompareLineChart(stockMap1,stockMap2,stockName1,stockName2));
        }
    }

    /**
     * 点击收盘价按钮，显示收盘价比较图表
     */
    @FXML
    private void toCloseValueChart(){
        sliderLabel.setText("收盘价");
        sliderLabel.setLayoutX(0);
        sliderLabel.setLayoutY(26);
        chartVBox.getChildren().remove(0);
        chartVBox.getChildren().add(new CompareLineChart(stockMap1,stockMap2,stockName1,stockName2));
    }


    /**
     * 点击对数收益率按钮，显示对数收益率比较图表
     */
    @FXML
    private void toLogValueChart(){
        sliderLabel.setText("对数收益率");
        sliderLabel.setLayoutX(108);
        sliderLabel.setLayoutY(26);
        chartVBox.getChildren().remove(0);
        chartVBox.getChildren().add(new CompareLineChart(stockList1,stockList2,stockName1,stockName2));
    }

    @FXML
    private void toStock1Detail(){
        contentScrollPane.setContent(new StockDetailsPageUI(thisVBox,stage,stockName1,contentScrollPane));
    }

    @FXML
    private void toStock2Detail(){
        contentScrollPane.setContent(new StockDetailsPageUI(thisVBox,stage,stockName2,contentScrollPane));
    }

}
