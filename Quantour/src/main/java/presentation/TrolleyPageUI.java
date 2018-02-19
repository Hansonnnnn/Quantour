package presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by xiezhenyu on 2017/4/8.
 * 该类为购物车展示界面
 */
public class TrolleyPageUI extends Popup{
    public TrolleyPageUI(Stage owner, Node node){
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("fxml/TrolleyPage.fxml"));
        VBox vBox = new VBox();
        vBox.setPrefSize(320,400);
        try{
            vBox.getChildren().add(fxmlLoader.load());
            this.getContent().add(vBox);

        }catch (IOException e) {
            e.printStackTrace();
        }
        setAutoHide(true);
        setAutoFix(false);
        setConsumeAutoHidingEvents(false);
        setHideOnEscape(false);
        TrolleyPageUIController trolleyPageUIController=fxmlLoader.getController();
        trolleyPageUIController.init(this);
        show(node,node.localToScreen(0,-vBox.getPrefHeight()-2).getX(),
                node.localToScreen(0,-vBox.getPrefHeight()-2).getY());
    }
}
