package presentation;

import bussinesslogic.BlockServiceImpl;
import bussinesslogic.StockInfoBL;
import bussinesslogicservice.BlockService;
import bussinesslogicservice.StockInfoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.StockPlate;
import presentation.viewHelper.SelectButtonCell;
import presentation.viewHelper.StockNameButtonCell;
import presentation.viewHelper.Trolley;
import utility.DateHelper;
import utility.EventHelper;
import vo.StockVO;

import java.util.Date;
import java.util.Map;

/**
 * Created by xiezhenyu on 2017/3/26.
 */
public class BlockPageUIController {
    @FXML private TableView<StockVO> tableView;
    @FXML private TableColumn<StockVO, Boolean> idColumn;
    @FXML private TableColumn<StockVO, Boolean> nameColumn;
    @FXML private TableColumn<StockVO, Boolean> openColumn;
    @FXML private TableColumn<StockVO, Boolean> closeColumn;
    @FXML private TableColumn<StockVO, Boolean> highColumn;
    @FXML private TableColumn<StockVO, Boolean> lowColumn;
    @FXML private TableColumn<StockVO, Boolean> volumeColumn;
    @FXML private TableColumn<StockVO, Boolean> selectButtonColumn;
    @FXML private Button sliderLabel;
    @FXML private ToggleButton allStocksButton;
    @FXML private ToggleButton mainBlockButton;
    @FXML private ToggleButton zxBlockButton;
    @FXML private ToggleButton cyBlockButton;

    private Stage stage;
    private ScrollPane contentScrollPane;
    private VBox thisVBox;

    private ObservableList<StockVO> allStocksData;
    private ObservableList<StockVO> mainBlockData;
    private ObservableList<StockVO> SMEBlockData;
    private ObservableList<StockVO> CHINEXTBlockData;

    private StockInfoService stockInfoService;
    private BlockService blockService;
    private Date currentDate = DateHelper.stringToDate("2014-04-29");
    private Trolley trolley;

    public void init(VBox thisVBox,Stage satge,ScrollPane contentScrollPane)
    {
        this.stage = stage;
        this.contentScrollPane = contentScrollPane;
        this.thisVBox = thisVBox;
        EventHelper.tableViewEvent(thisVBox,tableView,stage,contentScrollPane);
        stockInfoService = new StockInfoBL();
        blockService = new BlockServiceImpl();
        trolley = Trolley.getTrolley();
//        trolley.clearButtonAndImageView();
        initTable(currentDate);
    }

    public void initTable(Date date)
    {
        idColumn.setCellValueFactory(new PropertyValueFactory<StockVO, Boolean>("id"));
        openColumn.setCellValueFactory(new PropertyValueFactory<StockVO, Boolean>("openValue"));
        closeColumn.setCellValueFactory(new PropertyValueFactory<StockVO, Boolean>("closeValue"));
        highColumn.setCellValueFactory(new PropertyValueFactory<StockVO, Boolean>("highValue"));
        lowColumn.setCellValueFactory(new PropertyValueFactory<StockVO, Boolean>("lowValue"));
        volumeColumn.setCellValueFactory(new PropertyValueFactory<StockVO, Boolean>("volume"));

        allStocksData = FXCollections.observableArrayList();

        for (StockVO stockvo: stockInfoService.getAllStocks(date).values())
        {
            allStocksData.add(stockvo);
            trolley.getStockNames().add(stockvo.getName());
        }
        tableView.setItems(allStocksData);
        nameColumn.setCellFactory(new Callback<TableColumn<StockVO,Boolean>, TableCell<StockVO,Boolean>>() {
            @Override
            public TableCell call(TableColumn param) {
                return new StockNameButtonCell(thisVBox,tableView,stage,contentScrollPane);
            }
        });
        selectButtonColumn.setCellFactory(new Callback<TableColumn<StockVO, Boolean>, TableCell<StockVO, Boolean>>() {
            @Override
            public TableCell<StockVO, Boolean> call(TableColumn<StockVO, Boolean> param) {
                return new SelectButtonCell(tableView);
            }
        });
    }

    /**
     * 为沪深板块(主板)表格加载数据
     */
    private void initMainBlockData()
    {
        mainBlockData = FXCollections.observableArrayList();
        Map<Integer, StockVO> stockVOMap = blockService.getStocksInBlock(currentDate, StockPlate.CSI300);
        if(stockVOMap!=null)
        {
            for(StockVO stockVO : stockVOMap.values())
            {
                mainBlockData.add(stockVO);
            }
        }

    }

    /**
     * 为中小板表格加载数据
     */
    private void initSMEBlockData()
    {
        SMEBlockData = FXCollections.observableArrayList();
        Map<Integer, StockVO> stockVOMap = blockService.getStocksInBlock(currentDate, StockPlate.SME);
        if(stockVOMap!=null)
        {
            for(StockVO stockVO : stockVOMap.values())
            {
                SMEBlockData.add(stockVO);
            }
        }
    }

    /**
     * 为创业板表格加载数据
     */
    private void initCHINEXTBlockData()
    {
        CHINEXTBlockData = FXCollections.observableArrayList();
        Map<Integer, StockVO> stockVOMap = blockService.getStocksInBlock(currentDate, StockPlate.CHINEXT);
        if(stockVOMap!=null)
        {
            for(StockVO stockVO : stockVOMap.values())
            {
                CHINEXTBlockData.add(stockVO);
            }
        }
    }



    @FXML
    private void allStocks()
    {
        if (sliderLabel.getLayoutX()!=0)
        {
            sliderLabel.setText("全部股票");
            sliderLabel.setLayoutX(0);
            sliderLabel.setLayoutY(9);
            //加载全部板块的所有股票信息
            tableView.setItems(allStocksData);
        }

    }

    @FXML
    private void mainBlock()
    {
        if(sliderLabel.getLayoutX()!=108)
        {
            sliderLabel.setText("主板");
            sliderLabel.setLayoutX(108);
            sliderLabel.setLayoutY(9);
            //加载主板所有股票信息
            if(mainBlockData==null)
            {
                initMainBlockData();
                tableView.setItems(mainBlockData);
            }else
            {
                tableView.setItems(mainBlockData);
            }
        }

    }

    @FXML
    private void zxBlock()
    {
        if(sliderLabel.getLayoutX()!=216)
        {
            sliderLabel.setText("中小板");
            sliderLabel.setLayoutX(216);
            sliderLabel.setLayoutY(9);
            //加载中小板所有股票信息
            if(SMEBlockData==null)
            {
                initSMEBlockData();
                tableView.setItems(SMEBlockData);
            }else
            {
                tableView.setItems(SMEBlockData);
            }
        }

    }

    @FXML
    private void cyBlock()
    {
        if(sliderLabel.getLayoutX()!=327)
        {
            sliderLabel.setText("创业板");
            sliderLabel.setLayoutX(327);
            sliderLabel.setLayoutY(9);
            //加载创业板里面所有的股票
            if(CHINEXTBlockData==null)
            {
                initCHINEXTBlockData();
                tableView.setItems(CHINEXTBlockData);
            }else
            {
                tableView.setItems(CHINEXTBlockData);
            }
        }
    }


}
