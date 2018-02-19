package presentation;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FirstPageUI extends Scene
{
		public FirstPageUI(Parent root, Stage stage)
		{
			super(root);
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/FirstPage2.fxml"));
			try{
				this.setRoot(fxmlLoader.load());
			}catch(IOException e)
			{
				e.printStackTrace();
			}
			
			FirstPageController controller = fxmlLoader.getController();
			controller.init(stage, this);
		}
}
