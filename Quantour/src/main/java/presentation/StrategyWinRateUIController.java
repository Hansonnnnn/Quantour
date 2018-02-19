package presentation;

import bussinesslogic.StrategyEESBL;
import bussinesslogicservice.GetExcessEarningAndStrategyService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.StockPlate;
import model.StrategyType;
import model.TimeType;
import presentation.chart.ExcessEarningsAndStrategyChart;
import presentation.dialog.MyDialog;
import presentation.viewHelper.LogoRotate;
import presentation.viewHelper.Trolley;
import utility.DateHelper;
import utility.DatePickerHelper;
import vo.StrategyExcessEarningVO;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by LENOVO on 2017/4/16.
 */
public class StrategyWinRateUIController {

    @FXML private CheckBox strategyCheckBox;
    @FXML private CheckBox averageCheckBox;
    @FXML private HBox strategyHBox;
    @FXML private HBox averageHBox;
    @FXML private ComboBox<String> stockPoolComboBox;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;

    @FXML private CheckBox formCheckBox;
    @FXML private CheckBox holdCheckBox;
    @FXML private TextField holdTextField;

    @FXML private ComboBox<Integer> averageComboBox;
    @FXML private VBox dataVBox;
    @FXML private VBox chartVBox;
    @FXML private HBox infoHBox;


    private Stage stage;
    private VBox beforeVBox;
    private ScrollPane contentScrollPane;
    private Date startDate;
    private Date endDate;
    private StrategyExcessEarningVO strategyExcessEarningVO;
    private GetExcessEarningAndStrategyService getExcessEarningAndStrategyService;
    private Trolley trolley;
    private LogoRotate logoRotate;
    String labelStyle = "-fx-text-fill:#DDDDDD;-fx-font-size:18px";
    public void init(Stage stage,VBox beforeVBox,ScrollPane contentScrollPane){
        this.stage = stage;
        this.beforeVBox = beforeVBox;
        this.contentScrollPane = contentScrollPane;
        strategyCheckBox.setSelected(true);
        stockPoolComboBox.setValue("全部股票");
        averageComboBox.setValue(5);
        formCheckBox.setSelected(true);
        trolley = Trolley.getTrolley();
        logoRotate = LogoRotate.getLogoRotate();
        getExcessEarningAndStrategyService = new StrategyEESBL();
        DatePickerHelper.datePickerCustomize(startDatePicker,endDatePicker);
        stockPoolComboBox.setItems(FXCollections.observableArrayList("全部股票","主板","中小板","创业板","自选股"));
        averageComboBox.setItems(FXCollections.observableArrayList(5,10,20));
    }

    /**
     * 点击返回按钮，返回到上一个界面
     */
    @FXML
    private void back(){
        contentScrollPane.setContent(beforeVBox);
    }

    @FXML
    private void toStrategyBox(){
        averageCheckBox.setSelected(false);
        strategyCheckBox.setSelected(true);
        strategyHBox.setVisible(true);
        averageHBox.setVisible(false);
    }

    @FXML
    private void toAverageBox(){
        strategyCheckBox.setSelected(false);
        averageCheckBox.setSelected(true);
        averageHBox.setVisible(true);
        strategyHBox.setVisible(false);
    }

    /**
     * 选中形成期，将持有期设为未选中
     */
    @FXML
    private void toForm(){
        formCheckBox.setSelected(true);
        holdCheckBox.setSelected(false);
    }

    /**
     * 选中持有期，将形成期设为未选中
     */
    @FXML
    private void toHold(){
        formCheckBox.setSelected(false);
        holdCheckBox.setSelected(true);
    }

    /**
     * 点击显示关系图按钮，显示对应图表
     */
    @FXML
    private void toShowChart(){
        logoRotate.noStopRotate();
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                getData();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        showData();
                        logoRotate.rotateStop();
                    }
                });
                return 1;
            }
        };
        new Thread(task).start();



    }

    /**
     * 获取回测数据
     */
    private void getData(){
        //判断datepicker是否为空
        if ((startDatePicker.getValue() != null) && (endDatePicker.getValue() != null)){
            startDate = DateHelper.localDateToDate(startDatePicker.getValue());
            endDate = DateHelper.localDateToDate(endDatePicker.getValue());
            String stockPoolName = stockPoolComboBox.getValue();
            //判断选择的策略类型
            if(strategyCheckBox.isSelected()){
                //判断动量策略选中的是形成期还是持有期
                if(formCheckBox.isSelected()){
                    //判断形成期或持有期输入框是否为空
                    if((!holdTextField.equals("")) && holdTextField != null){
                        int strategyPeriod = Integer.parseInt(holdTextField.getText());
                        getStrategyExcessEarningVO(stockPoolName,StrategyType.momentumDriven,TimeType.GrowTime,strategyPeriod);
                    }else{
                        new MyDialog(stage,"请输入形成期",2);
                    }
                }else{
                    if((!holdTextField.equals("")) && holdTextField != null){
                        int strategyPeriod = Integer.parseInt(holdTextField.getText());
                        getStrategyExcessEarningVO(stockPoolName,StrategyType.momentumDriven,TimeType.HoldTime,strategyPeriod);
                    }else{
                        new MyDialog(stage,"请输入持有期",2);
                    }
                }
            }else{
                int averagePeriod = averageComboBox.getValue();
                getStrategyExcessEarningVO(stockPoolName,StrategyType.MeanReversionDriven,TimeType.GrowTime,averagePeriod);
            }
        }else{
            new MyDialog(stage,"请选择回测区间",2);
        }
    }

    /**
     * 获取StrategyExcessEarningVO
     * @param poolName 股票池名
     * @param strategyType 策略类型
     * @param timeType 形成期还是持有期
     */
    private void getStrategyExcessEarningVO(String poolName, StrategyType strategyType, TimeType timeType,int nums){
        if(poolName.equals("全部股票")){
            strategyExcessEarningVO = getExcessEarningAndStrategyService.getAllStocksEESVO(startDate,endDate,strategyType,
                    timeType,nums,2,2,30);
        }else if(poolName.equals("自选股")){
            if(trolley.getSelectedStockNames().size() >= 100){
                ArrayList<String> nameList = new ArrayList<>();
                for(int i = 0; i < trolley.getSelectedStockNames().size();i++){
                    nameList.add(trolley.getSelectedStockNames().get(i).getName());
                }
                strategyExcessEarningVO = getExcessEarningAndStrategyService.getUserStocksEESVO(nameList,startDate,endDate,strategyType,
                        timeType,nums,2,2,30);
            }else{
                new MyDialog(stage,"自选股票数须超过100支",2);
            }
        }else{
            strategyExcessEarningVO = getExcessEarningAndStrategyService.getStockPoolEESVO(chooseStockPlate(poolName),startDate,endDate,
                    strategyType,timeType,nums,2,2,30);
        }
    }


    /**
     * 判断股票池选择的是哪个版块
     * @param stockPlate 股票池名称
     * @return 返回对应股票池
     */
    private StockPlate chooseStockPlate(String stockPlate){
        if(stockPlate.equals("主板")){
            return StockPlate.CSI300;
        }else if(stockPlate.equals("创业板")){
            return StockPlate.CHINEXT;
        }else{
            return StockPlate.SME;
        }
    }

    /**
     * 显示数据和图表
     */
    private void showData(){
        dataVBox.getChildren().clear();
        Map<Integer,Double> excessEarningMap = strategyExcessEarningVO.getExcessEarining();
        Map<Integer,Double> strategyWinRateMap = strategyExcessEarningVO.getStrategyWinRate();
        for(Integer integer: excessEarningMap.keySet()){
            double excessEarning = excessEarningMap.get(integer);
            double strategyWinRate = strategyWinRateMap.get(integer);
            addData(integer,excessEarning,strategyWinRate);
        }
        chartVBox.getChildren().clear();
        chartVBox.getChildren().add(new ExcessEarningsAndStrategyChart("超额收益 vs 全样本-不同计算周期",excessEarningMap));
        chartVBox.getChildren().add(new ExcessEarningsAndStrategyChart("策略胜率%-不同计算周期",strategyWinRateMap));
        infoHBox.setVisible(true);
    }

    /**
     * 添加超额收益和策略胜率数据
     */
    private void addData(int period,double excessEarning,double strategyWinRate){
        Label periodLabel = new Label(String.valueOf(period));
        Label excessEarningLabel = new Label(format(excessEarning)+"%");
        Label strategyWinRateLabel = new Label(format(strategyWinRate)+"%");
        Pane dataPane = new Pane(periodLabel,excessEarningLabel,strategyWinRateLabel);
        dataPane.setPrefSize(400,20);
        periodLabel.setLayoutX(90);
        periodLabel.setLayoutY(0);
        excessEarningLabel.setLayoutX(195);
        excessEarningLabel.setLayoutY(0);
        strategyWinRateLabel.setLayoutX(286);
        strategyWinRateLabel.setLayoutY(0);
        periodLabel.setStyle(labelStyle);
        excessEarningLabel.setStyle(labelStyle);
        strategyWinRateLabel.setStyle(labelStyle);
        dataVBox.getChildren().add(dataPane);
    }

    // 格式化浮点数结果
    private String format(double d) {
        DecimalFormat dFormat=new DecimalFormat("#0.00");
        String string1=dFormat.format(d);
        return string1;
    }

}
