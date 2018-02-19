package presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by LENOVO on 2017/3/9.
 */
public class LoginPageUI extends Stage{
    public LoginPageUI(Stage owner){
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("fxml/LoginPage.fxml"));
        try{
            Scene scene=new Scene(fxmlLoader.load());
            this.setScene(scene);

        }catch (IOException e) {
            e.printStackTrace();
        }
        initOwner(owner);
        LoginPageUIController loginPageUIController=fxmlLoader.getController();
        loginPageUIController.init(this);
        initStyle(StageStyle.UNDECORATED);
        initModality(Modality.WINDOW_MODAL);
        showAndWait();
    }
}
