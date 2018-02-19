package presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vo.ColumnDiagramVO;
import vo.StrategyResultVO;

import java.io.IOException;

/**
 * Created by LENOVO on 2017/4/16.
 */
public class StrategyResultUI extends VBox{
    public StrategyResultUI(VBox beforeVBox, Stage stage, ScrollPane contentScrollPane, StrategyResultVO strategyResultVO, ColumnDiagramVO columnDiagramVO){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/StrategyResult.fxml"));
        try{
            this.getChildren().add(fxmlLoader.load());
        }catch(IOException e){
            e.printStackTrace();
        }

        StrategyResultUIController strategyResultUIController = fxmlLoader.getController();
        strategyResultUIController.init(beforeVBox,stage,contentScrollPane,strategyResultVO,columnDiagramVO);
    }
}
