package presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by LENOVO on 2017/4/11.
 */
public class StrategyChooseUI extends VBox{
    public StrategyChooseUI(Stage stage, ScrollPane contentScrollPane){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/StrategyChoose.fxml"));
        try{
            this.getChildren().add(fxmlLoader.load());
        }catch(IOException e){
            e.printStackTrace();
        }

        StrategyChooseUIController strategyChooseUIController = fxmlLoader.getController();
        strategyChooseUIController.init(stage,contentScrollPane,this);
    }

}
