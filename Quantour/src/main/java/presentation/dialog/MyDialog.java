package presentation.dialog;

import javafx.beans.NamedArg;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by LENOVO on 2016/12/16.
 */
public class MyDialog extends Stage{

    public MyDialog(Stage owner,String content,int myDialogType){
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("../fxml/MyDialog.fxml"));
        try{
            Scene scene=new Scene(fxmlLoader.load());
            this.setScene(scene);

        }catch (IOException e) {
            e.printStackTrace();
        }
        initOwner(owner);
        MyDialogController myDialogController=fxmlLoader.getController();
        myDialogController.init(this,content,myDialogType);
        initStyle(StageStyle.UNDECORATED);
        initModality(Modality.WINDOW_MODAL);
        showAndWait();
    }
}
