package helloworld;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainApp extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
		Scene scene = new Scene(root,800,750 );
		primaryStage.setTitle("Générateur");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(750);
		primaryStage.setMaxWidth(801);
		primaryStage.setMaxHeight(751);
		//On vous offre 1 pixel par c�t� de rien
		primaryStage.getIcons().add(new Image("file:images/spyware.png"));
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}
