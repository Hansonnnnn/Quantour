package utility;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import presentation.StockDetailsPageUI;
import vo.StockVO;

/**
 * Created by LENOVO on 2017/4/11.
 */
public class EventHelper {

    /**
     * 双击列表项，跳转至对应股票界面
     */
    public static void tableViewEvent(VBox beforeVBox,TableView tableView, Stage stage, ScrollPane contentScrollPane){
        tableView.setRowFactory(tv -> {
            TableRow<StockVO> tableRow = new TableRow<>();
            tableRow.setOnMouseClicked(event -> {
                if(!tableRow.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                    StockVO stockVO = (StockVO)tableView.getItems().get(tableRow.getIndex());
                    contentScrollPane.setContent(new StockDetailsPageUI(beforeVBox,stage,stockVO.getName(),contentScrollPane));
                }
            });

            return tableRow;
        });
    }
}
