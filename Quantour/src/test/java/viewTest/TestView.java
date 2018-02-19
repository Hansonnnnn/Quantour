package viewTest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.HistoryClosePageUI;

public class TestView extends Application{
	public void start(Stage stage)
	{
		Scene scene = new Scene(new HistoryClosePageUI(stage, 0));
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
