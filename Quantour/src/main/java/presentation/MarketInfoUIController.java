package presentation;

import bussinesslogic.StockInfoBL;
import bussinesslogicservice.StockInfoService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import presentation.dialog.MyDialog;
import presentation.viewHelper.StockNameButtonCell;
import utility.DateHelper;
import utility.DatePickerHelper;
import utility.EventHelper;
import vo.MarketInfoVO;
import vo.StockVO;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by LENOVO on 2017/3/10.
 */
public class MarketInfoUIController {
    @FXML private TableView<StockVO> tableView;
    @FXML private TableColumn<StockVO, Boolean> idColumn;
    @FXML private TableColumn<StockVO, Boolean> nameColumn;
    @FXML private TableColumn<StockVO, Boolean> openColumn;
    @FXML private TableColumn<StockVO, Boolean> closeColumn;
    @FXML private TableColumn<StockVO, Boolean> highColumn;
    @FXML private TableColumn<StockVO, Boolean> lowColumn;
    @FXML private TableColumn<StockVO, Boolean> volumeColumn;
    @FXML private Button searchDateButton;

    @FXML private Label volumeLabel;
    @FXML private Label firstLabel;
    @FXML private Label secondLabel;
    @FXML private Label thirdLabel;
    @FXML private Label fourthLabel;
    @FXML private Label fifthLabel;
    @FXML private Label sixthLabel;

    @FXML private DatePicker datePicker;

    private ObservableList<StockVO> stockData;

    private StockInfoService stockInfoService;
    private Stage stage;
    private ScrollPane contentScrollPane;
    private VBox thisVbox;
    private int count = -1;
    public void init(VBox thisVbox,Stage stage, ScrollPane contentScrollPane)
    {
        this.stage = stage;
        this.contentScrollPane = contentScrollPane;
        this.thisVbox =thisVbox;
        stockInfoService = new StockInfoBL();
        EventHelper.tableViewEvent(thisVbox,tableView,stage,contentScrollPane);
        datePicker.setValue(DateHelper.dateToLocalDate(DateHelper.stringToDate("2014-04-29")));
        DatePickerHelper.disableInWeek(datePicker);
        initLabel(DateHelper.stringToDate("2014-04-29"));
        initTable(DateHelper.stringToDate("2014-04-29"));
    }

    private void initLabel(Date date)
    {
        if(stockInfoService.getMarketInfo(date)==null)
        {
            System.out.println("数据层返回市场信息为空");
        }else{
            MarketInfoVO marketInfoVO = stockInfoService.getMarketInfo(date);
            volumeLabel.setText(toFormat(marketInfoVO.getVolumn()));
            firstLabel.setText(Integer.toString(marketInfoVO.getAmount_Of_LimitUp()));
            secondLabel.setText(Integer.toString(marketInfoVO.getAmount_Of_LimitDown()));
            thirdLabel.setText(Integer.toString(marketInfoVO.getAmount_Of_UpFive()));
            fourthLabel.setText(Integer.toString(marketInfoVO.getAmount_Of_DownFive()));
            fifthLabel.setText(Integer.toString(marketInfoVO.getAmount_Of_MoreFive()));
            sixthLabel.setText(Integer.toString(marketInfoVO.getAmount_Of_LessFive()));
        }


    }

    private void initTable(Date date){

//        nameColumn.setStyle("-fx-text-fill:#2E6AAE;-fx-cursor:hand");
//        nameColumn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                System.out.println("clicked");
//                int index = tableView.getSelectionModel().getSelectedIndex();
//                new MyDialog(stage,stockData.get(index).getName(),2);
//            }
//        });
        openColumn.setCellValueFactory(new PropertyValueFactory<StockVO, Boolean>("openValue"));
        closeColumn.setCellValueFactory(new PropertyValueFactory<StockVO, Boolean>("closeValue"));
        highColumn.setCellValueFactory(new PropertyValueFactory<StockVO, Boolean>("highValue"));
        lowColumn.setCellValueFactory(new PropertyValueFactory<StockVO, Boolean>("lowValue"));
        volumeColumn.setCellValueFactory(new PropertyValueFactory<StockVO, Boolean>("volume"));

        stockData = FXCollections.observableArrayList();
        for (StockVO stockvo: stockInfoService.getAllStocks(date).values())
        {
            stockData.add(stockvo);
        }
        tableView.setItems(stockData);


        idColumn.setCellValueFactory(new PropertyValueFactory<StockVO, Boolean>("id"));
//        nameColumn.setCellValueFactory(new PropertyValueFactory<StockVO, Boolean>("name"));
        nameColumn.setCellFactory(new Callback<TableColumn<StockVO,Boolean>, TableCell<StockVO,Boolean>>() {
            @Override
            public TableCell call(TableColumn param) {
                return new StockNameButtonCell(thisVbox,tableView,stage,contentScrollPane);
            }
        });
    }

    @FXML
    private void search()
    {
        if(datePicker.getValue()!=null){
            initLabel(DateHelper.localDateToDate(datePicker.getValue()));
            initTable(DateHelper.localDateToDate(datePicker.getValue()));

        }else{
            new MyDialog(stage,"请选择日期",2);
        }
    }

    /**
     * 将科学计数法转化为带两位小数的以万为单位的数字
     */
    private String toFormat(double num){
        double result = num/10000;
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(result)+"万";
    }
}
