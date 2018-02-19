package presentation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import bussinesslogic.StockInfoBL;
import bussinesslogicservice.StockInfoService;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import presentation.chart.KlineChart;
import presentation.chart.StockLineChart;
import presentation.dialog.MyDialog;
import presentation.viewHelper.Trolley;
import utility.DateHelper;
import utility.DatePickerHelper;
import vo.StockAverageValueVO;
import vo.StockInfoForK_lineVO;
import vo.StockVO;

public class StockDetailsPageUIController {

    @FXML
    private TextField conditionsField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Button sliderLabel;
    @FXML
    private VBox graphVBox;
    @FXML
    private VBox chartVBox;
    @FXML
    private ToggleButton kLineButton;
    @FXML
    private ToggleButton averageLineButton;
    @FXML
    private Label stockNameLabel;
    @FXML
    private Label stockIdLabel;
    @FXML
    private Label stockPriceLabel;
    @FXML
    private ImageView stockPriceImageView;
    @FXML
    private Label stockOpenPriceLabel;
    @FXML
    private Label stockClosePriceLabel;
    @FXML
    private Label stockMaxPriceLabel;
    @FXML
    private Label stockMinPriceLabel;
    @FXML
    private Button addStockButton;
    @FXML
    private ImageView addStockImageView;
    @FXML
    private TextField compareStockName;



    private Stage stage;
    private String stockName;
    private VBox beforeVBox;
    private ScrollPane contentScrollPane;
    private int chartType = 1;

    private StockVO stockVO;
    private StockInfoService stockInfoService;
    private ImageView kLineImageView;
    private SwingNode kLineSwingNode;
    private VBox averageLineVBox;
    private Trolley trolley;
    private Image image = new Image(getClass().getResourceAsStream("image/add2.png"));
    private String confirmStyle = "-fx-text-fill:white;-fx-font-family:\"微软雅黑\";-fx-border-radius: 4px;-fx-background-color: linear-gradient(#2E4E7E, #2E4E6E);";
    private String disableStyle ="-fx-text-fill:#B2B2B2;-fx-font-family:\"微软雅黑\";-fx-border-radius: 4px;-fx-background-color: linear-gradient(#525252, #535353);";

    public void init(VBox beforeVBox,Stage stage,String stockName,ScrollPane contentScrollPane) {
        this.stage = stage;
        this.stockName = stockName;
        this.contentScrollPane = contentScrollPane;
        this.beforeVBox = beforeVBox;
        stockInfoService = new StockInfoBL();
        trolley = Trolley.getTrolley();
        trolley.setOptionButtonAndImageView(addStockButton,addStockImageView);
        DatePickerHelper.datePickerCustomize(startDatePicker,endDatePicker);
        initLabel();
        kLineImageView = new ImageView();
        datePickerEvent(startDatePicker,startDatePicker,endDatePicker);
        datePickerEvent(endDatePicker,startDatePicker,endDatePicker);
    }

    /**
     * 初始化股票基本信息
     */
    private void initLabel(){
        Date date = DateHelper.stringToDate("2014-04-29");
        Image riseImage = new Image(getClass().getResourceAsStream("image/rise.png"));
        Image declineImage = new Image(getClass().getResourceAsStream("image/decline.png"));
        stockVO = stockInfoService.getStocks(stockName,date);
        stockNameLabel.setText(stockVO.getName());
        stockIdLabel.setText(String.valueOf(stockVO.getId()));
        stockPriceLabel.setText(String.valueOf(stockVO.getOpenValue()));
        if(trolley.isSelected(stockVO)){
            addStockButton.getStyleClass().removeAll();
            addStockButton.getStyleClass().add("disableButton");
            addStockButton.setText("已加自选股");
            addStockImageView.setImage(image);
            addStockButton.setDisable(true);
        }else{
            addStockButton.getStyleClass().removeAll();
            addStockButton.getStyleClass().add("confirmButton");
            addStockButton.setText("加入自选股");
            addStockImageView.setImage(new Image(getClass().getResourceAsStream("image/add.png")));
            addStockButton.setDisable(false);
        }

        if(stockVO.getOpenValue() <= stockVO.getCloseValue()){
            stockPriceLabel.setStyle("-fx-background-color: transparent;-fx-text-fill: #C10D01;-fx-font-size: 30px");
            stockPriceImageView.setImage(riseImage);
        }else{
            stockPriceLabel.setStyle("-fx-background-color: transparent;-fx-text-fill: #2C7C02;-fx-font-size: 30px");
            stockPriceImageView.setImage(declineImage);
        }
        stockOpenPriceLabel.setText(String.valueOf(stockVO.getOpenValue()));
        stockClosePriceLabel.setText(String.valueOf(stockVO.getCloseValue()));
        stockMaxPriceLabel.setText(String.valueOf(stockVO.getHighValue()));
        stockMinPriceLabel.setText(String.valueOf(stockVO.getLowValue()));
        showKLineChart(DateHelper.localDateToDate(startDatePicker.getValue()),DateHelper.localDateToDate(endDatePicker.getValue()));
    }

    /**
     * 显示K线图
     * @param startDate 开始日期
     * @param endDate 结束日期
     */
    private void showKLineChart(Date startDate,Date endDate){
        ArrayList<StockInfoForK_lineVO> infoVOList = stockInfoService.getStockForKLine(startDate, endDate, stockName);
        kLineSwingNode = new KlineChart(infoVOList, startDate, endDate);
        chartVBox.getChildren().add(kLineSwingNode);
    }

    /**
     * 显示均线图
     * @param startDate 开始日期
     * @param endDate 结束日期
     */
    private void showAverageLineChart(Date startDate,Date endDate){
        ArrayList<StockAverageValueVO> averageValue5VOList = stockInfoService.getStockForAverageLine(5,startDate,endDate,stockName);
        chartVBox.getChildren().add(new StockLineChart(averageValue5VOList));
    }

    /**
     * 点击K线图按钮，显示股票的K线图
     */
    @FXML
    private void toKLineChart() {
        sliderLabel.setText("K线图");
        sliderLabel.setLayoutX(0);
        sliderLabel.setLayoutY(38);
        chartType = 1;
        contentScrollPane.setVvalue(0.65);
        chartVBox.getChildren().remove(0);
        showKLineChart(DateHelper.localDateToDate(startDatePicker.getValue()),DateHelper.localDateToDate(endDatePicker.getValue()));
    }

    /**
     * 点击均线图按钮，显示均线图
     */
    @FXML
    private void toAverageLineChart() {
        sliderLabel.setText("均线图");
        sliderLabel.setLayoutX(73);
        sliderLabel.setLayoutY(38);
        chartType = 2;
        contentScrollPane.setVvalue(0.65);
        chartVBox.getChildren().remove(0);
        showAverageLineChart(DateHelper.localDateToDate(startDatePicker.getValue()),DateHelper.localDateToDate(endDatePicker.getValue()));
    }

    /**
     * 设置DatePicker点击事件监听
     * @param eventDatePicker 要增加监听的DatePicker
     * @param startDatePicker 开始日期DatePicker
     * @param endDatePicker 结束日期DatePicker
     */
    private void datePickerEvent(DatePicker eventDatePicker,DatePicker startDatePicker,DatePicker endDatePicker){
        eventDatePicker.setOnAction((ActionEvent e)->{
            Date startDate = DateHelper.localDateToDate(startDatePicker.getValue());
            Date endDate = DateHelper.localDateToDate(endDatePicker.getValue());
            chartVBox.getChildren().remove(0);
            if(chartType == 1){
                showKLineChart(startDate,endDate);
            }else{
                showAverageLineChart(startDate,endDate);
            }
        });
    }

    /**
     * 将该股票添加至自选股列表
     */
    @FXML
    private void addToOptionalStock(){
        addStockButton.setStyle(disableStyle);
        addStockButton.setText("已加自选股");
        addStockButton.setDisable(true);
        addStockImageView.setImage(image);
        int index = 0;
        for(int i = 0;i < trolley.getStockNames().size();i++){
            if(stockVO.getName().equals(trolley.getStockNames().get(i))){
                index = i;
                break;
            }
        }
        trolley.addStock(stockVO,trolley.getStockImageViews().get(index),trolley.getStockButtons().get(index));
    }

    /**
     * 点击返回按钮，返回到上一个界面
     */
    @FXML
    private void back(){
        contentScrollPane.setContent(beforeVBox);
    }

    @FXML
    private void toCompare(){
        if((compareStockName.getText() != null) && (!compareStockName.equals(""))){
            contentScrollPane.setContent(new ComparePageUI(stage,contentScrollPane,stockName,compareStockName.getText(),
                    startDatePicker.getValue(),endDatePicker.getValue()));
        }else{
            new MyDialog(stage,"请输入待比较的股票名或ID",2);
        }
    }
}
