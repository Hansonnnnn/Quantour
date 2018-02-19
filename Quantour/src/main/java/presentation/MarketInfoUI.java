package presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by xiaoJun on 2017/3/10.
 */
public class MarketInfoUI extends VBox{
    public MarketInfoUI(Stage stage, ScrollPane contentScrollPane){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/MarketInfo1.fxml"));
        try{
            this.getChildren().add(fxmlLoader.load());
        }catch(IOException e){
            e.printStackTrace();
        }

        MarketInfoUIController marketInfoUIController = fxmlLoader.getController();
        marketInfoUIController.init(this,stage,contentScrollPane);
    }
}
