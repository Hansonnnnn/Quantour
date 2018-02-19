package driver;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import presentation.FirstPageUI;

public class Main extends Application
{
	public void start(Stage stage)
	{
		stage.setScene(new FirstPageUI(new Group(), stage));
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
