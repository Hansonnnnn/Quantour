package presentation.viewHelper;

/**
 * Created by LENOVO on 2017/4/11.
 */

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import presentation.MarketInfoUIController;
import presentation.StockDetailsPageUI;
import vo.StockVO;

/**
 * 在股票列表中添加股票name按钮，点击按钮跳转至该股票界面
 */
public class StockNameButtonCell extends TableCell<StockVO,Boolean> {
    private TableView table;
    private Stage stage;
    private ScrollPane contentScrollPane;
    private VBox beforeVBox;
    private Button stockNameButton = new Button();
    public StockNameButtonCell(VBox beforeVBox,TableView<StockVO> tableView, Stage stage, ScrollPane contentScrollPane){
        this.table = tableView;
        this.stage = stage;
        this.contentScrollPane = contentScrollPane;
        this.beforeVBox = beforeVBox;
    }

    @Override
    protected  void updateItem(Boolean t,boolean empty){
        super.updateItem(t,empty);
        if(empty){
            setGraphic(null);
            setText(null);
        }else{
            StockVO stockVO = (StockVO) table.getItems().get(getTableRow().getIndex());
            stockNameButton.setText(stockVO.getName());
            stockNameButton.getStylesheets().add(MarketInfoUIController.class.getResource("css/Beauty.css").toExternalForm());
            stockNameButton.getStyleClass().add("stockNameButton");
            stockNameButton.setOnAction((ActionEvent e)->{
                System.out.println(stockVO.getName());
                contentScrollPane.setContent(new StockDetailsPageUI(beforeVBox,stage,stockVO.getName(),contentScrollPane));
            });
            setGraphic(stockNameButton);
            setText(null);
        }
    }
}
