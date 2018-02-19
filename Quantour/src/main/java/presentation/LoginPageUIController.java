package presentation;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Created by LENOVO on 2017/3/9.
 */
public class LoginPageUIController {

    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;

    public void init(Stage stage){
        this.stage = stage;
        drag();
    }

    /**
     * 点击关闭按钮，关闭登录对话框
     */
    @FXML
    private void loginClose(){
        stage.close();
    }

    /**
     * 使登录对话框可拖动
     */
    private void drag(){
        stage.getScene().setOnMousePressed((MouseEvent event) -> {
            event.consume();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        stage.getScene().setOnMouseDragged((MouseEvent event)->{
            event.consume();
            stage.setX(event.getScreenX()-xOffset);
            stage.setY(event.getScreenY()-yOffset);
        });
    }
}
