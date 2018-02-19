package presentation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by xiezhenyu on 2017/3/26.
 */
public class BlockPageUI extends VBox
{
    public BlockPageUI(Stage stage, ScrollPane contentScrollPane)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/BlockPage.fxml"));
        try
        {
            this.getChildren().add(fxmlLoader.load());
        }catch(IOException e)
        {
            e.printStackTrace();
        }

        BlockPageUIController controller = fxmlLoader.getController();
        controller.init(this,stage,contentScrollPane);
    }
}
