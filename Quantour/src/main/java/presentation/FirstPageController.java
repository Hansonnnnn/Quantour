package presentation;


import java.time.LocalDate;
import java.util.Map;

import bussinesslogic.StockInfoBL;
import javafx.animation.KeyValue;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Popup;

import bussinesslogicservice.StockInfoService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import presentation.dialog.MyDialog;
import presentation.viewHelper.LogoRotate;
import presentation.viewHelper.Trolley;
import vo.HotStockVO;


public class FirstPageController {
    @FXML
    private VBox mainVBox;
    @FXML
    private Label sliderLabel;
    @FXML
    private VBox contentVBox;
    @FXML
    private ScrollPane contentScrollPane;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button trolleyButton;
    @FXML
    private ImageView windImageView;

    private Popup popup;
    private ListView<String> popupListView;
    private VBox popVBox;
    private Button button;
    private ObservableList<String> items = FXCollections.observableArrayList();
    private Stage stage;
    private Scene thisScene;
    private boolean isOpen = false;
    private Popup stockListPopup;
    private Trolley trolley;
    private LogoRotate logoRotate;
    private BlockPageUI blockPageUI;

    private StockInfoService stockInfoService;

    private ObservableList<HotStockVO> hotStockData;

    private final String volume = "今日总成交量";
    private final String amount_Of_LimitUp = "涨停股票数";
    private final String amount_Of_LimitDown = "股票跌停数";
    private final String amount_Of_UpFive = "涨幅超过 5%的股票数";
    private final String amount_Of_DownFive = "跌幅超过 5%的股票数";
    private final String amount_Of_MoreFive = "开盘‐收盘大于 5%*上一个交易日收盘价的股票个数";
    private final String amount_Of_LessFive = "开盘‐收盘小于‐5%*上一个交易日收盘价的股票个数";

    private double xOffset = 0;
    private double yOffset = 0;

    public void init(Stage stage, Scene thisScene) {
        this.stage = stage;
        this.thisScene = thisScene;
        stockInfoService = new StockInfoBL();
        mainVBox.setOnMouseClicked((MouseEvent e)->{
            isOpen = false;
        });
        trolley = Trolley.getTrolley();
        trolley.setCountLabel(trolleyButton);
        logoRotate = LogoRotate.getLogoRotate();
        logoRotate.setLogoImageView(windImageView);
        blockPageUI = new BlockPageUI(stage,contentScrollPane);
        stockListPopup = new Popup();
        initPopup();
        contentScrollPane.setContent(new MarketInfoUI(stage, contentScrollPane));
        windowMove();
        popListViewEvent();
        addToolTip();
        new StockDetailsPageUI(null,stage, "深物业A",contentScrollPane);
    }

    /**
     * 实现界面最小化监听
     */
    @FXML
    private void minimize() {
        stage.setIconified(true);
    }

    /**
     * 实现界面关闭
     */
    @FXML
    private void close() {
        Platform.exit();
        System.exit(0);
    }


    /**
     * 实现界面能够随鼠标点击拖动
     */
    @FXML
    private void windowMove() {
        mainVBox.setOnMousePressed((MouseEvent event) -> {
            event.consume();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        mainVBox.setOnMouseDragged((MouseEvent event) -> {
            event.consume();
            stage.setX(event.getScreenX() - xOffset);
            popup.setX(searchTextField.localToScreen(0, 0).getX());

            //根据自己的需求，做不同的判断
            if (event.getScreenY() - yOffset < 0) {
                stage.setY(0);
            } else {
                stage.setY(event.getScreenY() - yOffset);
            }
            popup.setY(searchTextField.localToScreen(0, 0).getY() + searchTextField.getHeight() + 2);
        });
    }

    /**
     * 点击登录按钮，弹出登录界面
     */
    @FXML
    private void toLoginStage() {
        new LoginPageUI(stage);
    }

    /**
     * 点击市场情况导航栏，跳转至该界面
     */
    @FXML
    private void toMarkerInfo() {
        moveOperationButtonBackground(sliderLabel, 200);
        logoRotate.imageviewRotate();
        contentScrollPane.setContent((new MarketInfoUI(stage, contentScrollPane)));
    }

    /**
     * 点击个股信息按钮，跳转至该界面
     */
    @FXML
    private void toStockDetails() {
        moveOperationButtonBackground(sliderLabel, 250);
        logoRotate.imageviewRotate();
        contentScrollPane.setContent(blockPageUI);
    }

    /**
     * 点击股票比较按钮，跳转至该界面
     */
    @FXML
    private void toStockCompare() {
        moveOperationButtonBackground(sliderLabel, 300);
        logoRotate.imageviewRotate();
        contentScrollPane.setContent(new ComparePageUI(stage,contentScrollPane,"1","10", LocalDate.of(2014,2,14),
                LocalDate.of(2014,3,28)));
    }

    /**
     * 点击股票策略按钮，跳转至该界面
     */
    @FXML
    private void toHistoryRecord() {
        moveOperationButtonBackground(sliderLabel, 350);
        logoRotate.imageviewRotate();
        contentScrollPane.setContent(new StrategyChooseUI(stage,contentScrollPane));
    }


    /**
     * 设置按钮背景色滑动效果
     */
    private void moveOperationButtonBackground(Label label, double y) {
        Timeline timeline = new Timeline();
        timeline.setAutoReverse(false);
        KeyValue newY = new KeyValue(label.layoutYProperty(), y);
        KeyFrame kf = new KeyFrame(Duration.millis(300), newY);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }



    /**
     * 点击界面上方搜索框，弹出待选择列表
     */
    @FXML
    private void toShowPopup() {
        String keyWord = searchTextField.getText();
        keyWord = keyWord.replaceAll(" ", "");
        Map<String, Integer> map = stockInfoService.searchStock(keyWord);
        if (map.size() == 0 || map == null || keyWord.equals("")) {
            popup.hide();
        } else {
            items.clear();
            for (String name : map.keySet()) {
                items.add(name);
            }
            popupListView.setItems(items);
            if (items.size() <= 11) {
                popupListView.setPrefHeight(items.size() * 35 + 2);
            } else {
                popupListView.setPrefHeight(Region.USE_COMPUTED_SIZE);
            }
            popupListView.refresh();
            popup.show(searchTextField, searchTextField.localToScreen(0, 0).getX(),
                    searchTextField.localToScreen(0, 0).getY() + searchTextField.getHeight() + 2);
        }
    }

    /**
     * 响应搜索提示框的监听事件
     */
    private void popListViewEvent() {
        popupListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String name = popupListView.getSelectionModel().getSelectedItem().toString();
                searchTextField.setText(name);
                contentScrollPane.setContent(new StockDetailsPageUI(blockPageUI,stage, name,contentScrollPane));
                popup.hide();
            }
        });
    }

    @FXML
    private void toTextChange() {
        button.setText(searchTextField.getText());
    }

    /**
     * 初始化popup控件
     */
    private void initPopup() {
        popup = new Popup();
        popupListView = new ListView<>();
        popupListView.setItems(items);
        popupListView.setId("popupListView");
        popVBox = new VBox();
        popVBox.getChildren().add(popupListView);
        popupListView.setPrefHeight(Region.USE_COMPUTED_SIZE);
        popVBox.setPrefWidth(280);
        popup.getContent().add(popVBox);
        popupListView.getStylesheets().add(FirstPageController.class.getResource("css/Beauty.css").toExternalForm());
        popup.setAutoHide(true);
        popup.setAutoFix(false);
        popup.setConsumeAutoHidingEvents(false);
        popup.setHideOnEscape(false);
    }

    @FXML
    private void search() {
        String keyWord = searchTextField.getText();
        keyWord = keyWord.replaceAll(" ", "");
        Map<String, Integer> map = stockInfoService.searchStock(keyWord);
        if(keyWord == null || keyWord.equals("") || map.size() == 0 || map == null){
            new MyDialog(stage,"未找到对应股票",2);
        }else{
            String stockName = null;
            boolean firstStock = true;
            for (String name : map.keySet()) {
                if(firstStock){
                    stockName = name;
                    firstStock = false;
                }else{
                    break;
                }
            }
            contentScrollPane.setContent(new StockDetailsPageUI(blockPageUI,stage,stockName,contentScrollPane));
        }
    }

    /*
     *添加提示框
     */
    private void addToolTip(){
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("自选股列表");
        trolleyButton.setTooltip(tooltip);
    }

    /**
     * 点击自选股按钮，显示自选股列表
     */
    @FXML
    private void showTrolley(){
        if(isOpen){
            stockListPopup.hide();
            isOpen = false;
        }else{
            stockListPopup = new TrolleyPageUI(stage,trolleyButton);
            isOpen = true;
        }
    }
}

