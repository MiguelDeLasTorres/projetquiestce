package helloworld;

import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HelloWorld extends Application {
	
	public void addLabels(GridPane root, int n, int m) {
		for(int i=0; i<n; i++) {
			
			for(int j=1; j<m+1; j++) {
				Image image = new Image("file:///C:/Miguel/images/nicolas_sarkozy.jpg");
			    ImageView iv = new ImageView(image);
			    iv.setFitHeight(100);
			    iv.setFitWidth(100);
			    GridPane.setHalignment(iv, HPos.RIGHT);
		        root.add(iv, i, j);
			}
		}
	}
   @Override
   public void start(Stage primaryStage) throws Exception {
       GridPane root = new GridPane();

       root.setPadding(new Insets(20));
       root.setHgap(25);
       root.setVgap(15);  
       
       Label labelTitle = new Label("Qui C");
       GridPane.setHalignment(labelTitle, HPos.RIGHT);
       root.add(labelTitle, 0, 0,6,1);
       
       addLabels(root,12,12);
       
       Button loginButton = new Button("Login");
       
       // Horizontal alignment for Login button.
       GridPane.setHalignment(loginButton, HPos.RIGHT);
       root.add(loginButton, 13, 1,1,1);
       

       Scene scene = new Scene(root, 300, 300);
       primaryStage.setTitle("GridPanel Layout Demo (o7planning.org)");
       primaryStage.setScene(scene);
       primaryStage.show();
   }

   public static void main(String[] args) throws IOException {
	   
       launch(args);
   }

}