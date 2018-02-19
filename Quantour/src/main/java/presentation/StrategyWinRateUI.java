package presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by xiaoJun on 2017/4/16.
 */
public class StrategyWinRateUI extends VBox{

    public StrategyWinRateUI(Stage stage, VBox beforeVBox, ScrollPane scrollPane){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/StrategyWinRate.fxml"));
        try{
            this.getChildren().add(fxmlLoader.load());
        }catch(IOException e){
            e.printStackTrace();
        }

        StrategyWinRateUIController strategyWinRateUIController = fxmlLoader.getController();
        strategyWinRateUIController.init(stage,beforeVBox,scrollPane);
    }
}
