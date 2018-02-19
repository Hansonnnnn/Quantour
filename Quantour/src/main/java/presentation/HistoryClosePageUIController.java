package presentation;

import java.util.Date;

import bussinesslogicservice.StockInfoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import vo.StockCloseValueVO;

public class HistoryClosePageUIController {
	@FXML private TableView<StockCloseValueVO> tableView;
	@FXML private TableColumn<StockCloseValueVO, Boolean> stockIdColumn;
	@FXML private TableColumn<StockCloseValueVO, Boolean> stockNameColumn;
	@FXML private TableColumn<StockCloseValueVO, Boolean> dateColumn;
	@FXML private TableColumn<StockCloseValueVO, Boolean> closeValueColumn;
	
	private Stage stage;
	private int id;
	
	private StockInfoService stockInfoService;
	private ObservableList<StockCloseValueVO> stockData;
	
	public void init(Stage stage, int id)
	{
		this.stage = stage;
		this.id = id;
		initTableView();
	}
	
	private void initTableView()
	{
		stockIdColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
		stockNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		closeValueColumn.setCellValueFactory(new PropertyValueFactory<>("closeValue"));
		
		stockData = FXCollections.observableArrayList(
				new StockCloseValueVO(new Date(), 10, 2, "Alibaba"),
				new StockCloseValueVO(new Date(), 20, 3, "Huawei"));
//		for (StockCloseValueVO stockCloseValueVO : stockInfoService.getHistoryCloseValue(Integer.toString(id)).values())
//		{
//			stockData.add(stockCloseValueVO);
//		}
		tableView.setItems(stockData);
	}
}
