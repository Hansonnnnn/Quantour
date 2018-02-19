package presentation.dialog;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Created by LENOVO on 2016/12/16.
 */
public class MyDialogController {
    @FXML private ImageView dialogImageView;
    @FXML private Label contentLabel;

    private Stage stage;
    private double xOffset;
    private double yOffset;

    public void init(Stage stage, String content, int myDialogType){
        this.stage=stage;
        contentLabel.setText(content);
        drag();
    }

    /**
     * 点击确认按钮，关闭对话框
     */
    @FXML
    private void confirm(){
        stage.close();
    }

    /**
     * 点击退出按钮，退出对话框
     */
    @FXML
    private void cancle(){
        stage.close();
    }

    /**
     * 使对话框可拖动
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
