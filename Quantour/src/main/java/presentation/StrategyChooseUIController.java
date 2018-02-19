package presentation;

import bussinesslogic.ColumnDiagramServiceImpl;
import bussinesslogic.StrategyVSBaseBL;
import bussinesslogicservice.ColumnDiagramService;
import bussinesslogicservice.StrategyVSBaseService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.StockPlate;
import model.StrategyType;
import presentation.dialog.MyDialog;
import presentation.viewHelper.LogoRotate;
import presentation.viewHelper.Trolley;
import utility.DateHelper;
import utility.DatePickerHelper;
import vo.ColumnDiagramVO;
import vo.StrategyResultVO;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by LENOVO on 2017/4/11.
 */
public class StrategyChooseUIController {

    @FXML
    private ComboBox<String> strategyComboBox;
    @FXML
    private DatePicker strategyStartDatePicker;
    @FXML
    private DatePicker strategyEndDatePicker;
    @FXML
    private TextField formTextField;
    @FXML
    private TextField strategyHoldTextField;

    @FXML
    private ComboBox<String> averageComboBox;
    @FXML
    private DatePicker averageStartDatePicker;
    @FXML
    private DatePicker averageEndDatePicker;
    @FXML
    private ComboBox<Integer> lineComboBox;
    @FXML
    private TextField averageHoldTextField;

    private Stage stage;
    private ScrollPane contentScrollPane;
    private VBox thisVBox;
    private Trolley trolley;
    private StrategyVSBaseService strategyVSBaseService;
    private ColumnDiagramService columnDiagramService;
    private StrategyResultVO strategyResultVO;
    private ColumnDiagramVO columnDiagramVO;
    private String strategyStockPool;
    private Date startDate;
    private Date endDate;
    private int formPeriod;
    private int holdPeriod;
    private LogoRotate logoRotate;

    private ArrayList<String> stockNames = new ArrayList<>();

    public void init(Stage stage, ScrollPane contentScrollPane, VBox thisVBox) {
        this.stage = stage;
        this.contentScrollPane = contentScrollPane;
        this.thisVBox = thisVBox;
        trolley = Trolley.getTrolley();
        logoRotate = LogoRotate.getLogoRotate();
        strategyVSBaseService = new StrategyVSBaseBL();
        strategyComboBox.setValue("全部股票");
        averageComboBox.setValue("全部股票");
        lineComboBox.setValue(5);
        columnDiagramService = new ColumnDiagramServiceImpl();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        ObservableList stockPoolList = FXCollections.observableArrayList("全部股票", "主板", "中小板", "创业板", "自选股");
        ObservableList averageLineList = FXCollections.observableArrayList("5", "10", "20");
        strategyComboBox.setItems(stockPoolList);
        averageComboBox.setItems(stockPoolList);
        lineComboBox.setItems(averageLineList);
        DatePickerHelper.datePickerCustomize(strategyStartDatePicker, strategyEndDatePicker);
        DatePickerHelper.datePickerCustomize(averageStartDatePicker, averageEndDatePicker);
    }

    /**
     * 点击超额收益和策略胜率按钮，跳转至对应界面
     */
    @FXML
    private void toWinRate() {
        contentScrollPane.setContent(new StrategyWinRateUI(stage, thisVBox, contentScrollPane));
    }

    /**
     * 点击开始动量策略按钮，显示动量策略结果
     */
    @FXML
    private void toStrategyResult() {
        logoRotate.noStopRotate();
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                showStrategy();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        contentScrollPane.setContent(new StrategyResultUI(thisVBox, stage, contentScrollPane, strategyResultVO, columnDiagramVO));
                        logoRotate.rotateStop();
                    }
                });
                return 1;
            }
        };
        new Thread(task).start();

//        Thread taskThread = new Thread(new Task<Void>() {
//            @Override
//            public Void call() throws Exception {
//                showStrategy();
//                return null;
//            }
//
//            @Override protected void cancelled() { super.cancelled(); }
//            @Override protected void failed   () { super.failed   (); }
//            @Override protected void succeeded() { super.succeeded(); }
//
////            private void terminate() {
////                stop.fire();
////                setInactive();
////                runMutex.release();
////            }
//        });
//        taskThread.setDaemon(true);
//        taskThread.start();
    }

    /**
     * 获取动量策略回测结果数据
     */
    private void showStrategy() {


        if (strategyIsComplete()) {
            stockNames.clear();
            String strategyStockPool = strategyComboBox.getValue();
            Date startDate = DateHelper.localDateToDate(strategyStartDatePicker.getValue());
            Date endDate = DateHelper.localDateToDate(strategyEndDatePicker.getValue());
            formPeriod = Integer.parseInt(formTextField.getText());
            holdPeriod = Integer.parseInt(strategyHoldTextField.getText());
            ArrayList<String> stockNames = new ArrayList<>();
            for (int i = 0; i < trolley.getSelectedStockNames().size(); i++) {
                stockNames.add(trolley.getSelectedStockNames().get(i).getName());
            }
            if (strategyStockPool.equals("全部股票")) {

                strategyResultVO = strategyVSBaseService.useStrategyWithinAllStocks(startDate, endDate, formPeriod, holdPeriod, StrategyType.momentumDriven);
                columnDiagramVO = columnDiagramService.getDataWithinAllStocks(startDate, endDate, formPeriod, holdPeriod, StrategyType.momentumDriven);
            } else if (strategyStockPool.equals("自选股")) {
                if (stockNames.size() >= 100) {
//                    logoRotate.imageviewRotate();
                    strategyResultVO = strategyVSBaseService.useStrategyWithinSomeStocks(startDate, endDate, stockNames, formPeriod, holdPeriod, StrategyType.momentumDriven);
                    columnDiagramVO = columnDiagramService.getDataWithinSomeStocks(startDate, endDate, formPeriod, holdPeriod,
                            trolley.getSelectedStockNames(), StrategyType.momentumDriven);
                } else {
                    new MyDialog(stage, "自选股票数须超过100支才能回测", 2);
                }
            } else {
//                logoRotate.imageviewRotate();
                strategyResultVO = strategyVSBaseService.useStrategyGenerally(startDate, endDate, chooseStockPlate(strategyStockPool), formPeriod,
                        holdPeriod, StrategyType.momentumDriven);
                columnDiagramVO = columnDiagramService.getDataWithinPlates(startDate, endDate, formPeriod, holdPeriod,
                        chooseStockPlate(strategyStockPool), StrategyType.momentumDriven);
            }
        }
    }


    /**
     * 判断动量策略信息是否输入完整
     *
     * @reurn 判断结果
     */
    private boolean strategyIsComplete() {
        if ((!strategyComboBox.getValue().equals("")) && strategyComboBox.getValue() != null) {
            if (strategyStartDatePicker.getValue() != null && strategyEndDatePicker.getValue() != null) {
                if ((!formTextField.getText().equals("")) && (formTextField.getText() != null)) {
                    if ((!strategyHoldTextField.getText().equals("")) && (strategyHoldTextField.getText() != null)) {
                        return true;
                    } else {
                        new MyDialog(stage, "请输入持有期", 2);
                    }
                } else {
                    new MyDialog(stage, "请输入形成期", 2);
                }
            } else {
                new MyDialog(stage, "请选择回测区间", 2);
            }
        } else {
            new MyDialog(stage, "请选择股票池", 2);
        }
        return false;
    }

    /**
     * 判断股票池选择的是哪个版块
     *
     * @param stockPlate 股票池名称
     * @return 返回对应股票池
     */
    private StockPlate chooseStockPlate(String stockPlate) {
        if (stockPlate.equals("主板")) {
            return StockPlate.CSI300;
        } else if (stockPlate.equals("中小板")) {
            return StockPlate.SME;
        } else {
            return StockPlate.CHINEXT;
        }
    }

    /**
     * 点击开始均值回归按钮，显示均值回归结果
     */
    @FXML
    private void toAverageResult() {
        logoRotate.noStopRotate();
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                showAverage();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        contentScrollPane.setContent(new StrategyResultUI(thisVBox, stage, contentScrollPane, strategyResultVO, columnDiagramVO));
                        logoRotate.rotateStop();
                    }
                });
                return 1;
            }
        };
        new Thread(task).start();
    }

    /**
     * 获取均值回归回测结果数据
     */
    private void showAverage(){
        if (AverageIsComplete()) {
            stockNames.clear();
            String strategyStockPool = averageComboBox.getValue();
            Date startDate = DateHelper.localDateToDate(averageStartDatePicker.getValue());
            Date endDate = DateHelper.localDateToDate(averageEndDatePicker.getValue());
            formPeriod = lineComboBox.getValue();
            holdPeriod = Integer.parseInt(averageHoldTextField.getText());
            for (int i = 0; i < trolley.getSelectedStockNames().size(); i++) {
                stockNames.add(trolley.getSelectedStockNames().get(i).getName());
            }
            if (strategyStockPool.equals("全部股票")) {
                strategyResultVO = strategyVSBaseService.useStrategyWithinAllStocks(startDate, endDate, formPeriod, holdPeriod, StrategyType.MeanReversionDriven);
                columnDiagramVO = columnDiagramService.getDataWithinAllStocks(startDate, endDate, formPeriod, holdPeriod, StrategyType.MeanReversionDriven);
            } else if (strategyStockPool.equals("自选股")) {
                if (trolley.getSelectedStockNames().size() >= 100) {
                    strategyResultVO = strategyVSBaseService.useStrategyWithinSomeStocks(startDate, endDate, stockNames, formPeriod, holdPeriod, StrategyType.MeanReversionDriven);
                    columnDiagramVO = columnDiagramService.getDataWithinSomeStocks(startDate, endDate, formPeriod, holdPeriod,
                            trolley.getSelectedStockNames(), StrategyType.MeanReversionDriven);
                } else {
                    new MyDialog(stage, "自选股票数须超过100支才能回测", 2);
                }
            } else {
                strategyResultVO = strategyVSBaseService.useStrategyGenerally(startDate, endDate, chooseStockPlate(strategyStockPool), formPeriod,
                        holdPeriod, StrategyType.MeanReversionDriven);
                columnDiagramVO = columnDiagramService.getDataWithinPlates(startDate, endDate, formPeriod, holdPeriod,
                        chooseStockPlate(strategyStockPool), StrategyType.MeanReversionDriven);
            }
        }
    }

    /**
     * 判断均值回归信息是否填写完整
     */
    private boolean AverageIsComplete() {
        if ((!averageComboBox.getValue().equals("")) && averageComboBox.getValue() != null) {
            if (averageStartDatePicker.getValue() != null && averageEndDatePicker.getValue() != null) {
                if ((!lineComboBox.getValue().equals("")) && lineComboBox.getValue() != null) {
                    if ((!averageHoldTextField.getText().equals("")) && averageHoldTextField.getText() != null) {
                        return true;
                    } else {
                        new MyDialog(stage, "请输入持有期", 2);
                    }
                } else {
                    new MyDialog(stage, "请选择均线", 2);
                }
            } else {
                new MyDialog(stage, "请选择回测区间", 2);
            }
        } else {
            new MyDialog(stage, "请选择股票池", 2);
        }
        return false;
    }
}
