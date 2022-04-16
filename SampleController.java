package helloworld;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SampleController implements Initializable{

	public ComboBox<String> selectionAttribut;
	public ComboBox<String> selectionValeurs;
	public Label listeAttribut;
	public Label status;
	public Label labelCompteur;
	public BorderPane root;
	public MenuItem fileOpen;
	public MenuItem exitButton;
	public GridPane grid;
	public Button addButton;
	public Button validButton;
	public Label LabelNbQuestions;
	JacksonParser parser = new JacksonParser();
	FileChooser fileChooser = new FileChooser();
	Game game;
	ArrayList<Valeur> listeValeurs = new ArrayList<>();
	String folder = System.getProperty("user.dir");
	int width;
	int nombreTour = 0;
	int nbQuestions = 0;
	int minQuestions=0;
	String croix = "/images/cross.png";
	ArrayList<Attribut> E3;
	ArrayList<Personnage> P3;
	ArrayList<String> images;
	File selectedFile;
	StatExtension stats;
	
	
	public void addLabels(GridPane grid, int width, ArrayList<String> images) {
		this.grid = new GridPane();
		this.grid.setVgap(50);
		this.grid.setHgap(10);
		this.grid.setPadding(new Insets(10,10,10,10));
		for(int j=0; j < images.size(); j++) {
			
			Image image = new Image("file:"+images.get(j));
			if (image.isError()) {
				image = new Image(images.get(j));
			}
			ImageView iv = new ImageView(image);
			iv.setFitHeight(100);
			iv.setFitWidth(100);
			GridPane.setHalignment(iv, HPos.RIGHT);
			this.grid.add(iv, j%width, j/width);
		}
		root.setCenter(this.grid);
	}

	
	public void supprimerPersonnage( int width, int index) {
		Image image = new Image("file:images/cross.png");
		ImageView iv = new ImageView(image);
		iv.setFitHeight(100);
		iv.setFitWidth(100);
		GridPane.setHalignment(iv, HPos.RIGHT);
		this.grid.add(iv, index%width, index/width);
	}	
	
	public void loadAttribut(Game game) {
		ArrayList<Attribut> attributs = game.getAttributsUtilises();
		selectionAttribut.getItems().clear();
		for (Attribut attribut : attributs) {
			selectionAttribut.getItems().add(attribut.getClef());
		}
	}

	@FXML public void setValeurs() {
		resetValeurs();
		if (!selectionValeurs.getItems().isEmpty()) {
			selectionValeurs.getItems().clear();
			selectionValeurs.setPromptText("Choisir une Valeur");
		}
		ArrayList<String> valeurs = game.getValeurFromAttribut(selectionAttribut.getValue());
		for (String string : valeurs) {
			selectionValeurs.getItems().add(string);
		}
	}
	
	public void selectionValeurClick() {
		addButton.setDisable(false);
	}
	
	public void resetValeurs() {
		if (!selectionValeurs.getItems().isEmpty()) {
			selectionValeurs.getItems().clear();
		}
	}

	public void addButtonClick() {
		if (listeAttribut.getText()=="") {
			listeAttribut.setText(selectionAttribut.getValue()+" = "+selectionValeurs.getValue());
		}else {
			listeAttribut.setText(listeAttribut.getText()+" et "+selectionAttribut.getValue()+" = "+selectionValeurs.getValue());
		}
		listeValeurs.add(new Valeur(selectionAttribut.getValue(), selectionValeurs.getValue()));
	}
	
	public void cancelButtonClick() {
		listeAttribut.setText("");
		listeValeurs.clear();
		selectionAttribut.setValue("");
		addButton.setDisable(true);
	}
	
	public void valideButtonClick() {
		nbQuestions+=listeValeurs.size();
  		game.verifAttribut(listeValeurs);
  		initGame(game);
  		cancelButtonClick();
  		labelCompteur.setText("Tour :" + ++nombreTour);
		addButton.setDisable(true);
	}
	
	public void newButton() {
		Stage stage = new Stage();
		stage.getIcons().add(new Image("file:images/spyware.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
         
         
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        

        Label label = new Label("Voulez-vous recommencer ?");
        Button validButton = new Button("Oui");
        Button cancelButton = new Button("Non");
        Image image = new Image("file:images/anonymity.png");
		ImageView iv = new ImageView(image);
		iv.setFitHeight(50);
		iv.setFitWidth(50);
		GridPane.setConstraints(iv, 0, 0);
        GridPane.setConstraints(label, 1, 0, 3, 1);
        GridPane.setConstraints(cancelButton, 3, 1);
        GridPane.setConstraints(validButton, 1, 1);
        validButton.setOnAction(e -> {
        	restart();
        	stage.close();
        });
        cancelButton.setOnAction(e -> stage.close());
        
        layout.setHgap(10);
        layout.setVgap(15);
        layout.setPadding(new Insets(10, 10, 10, 10));
        
        layout.getChildren().addAll(label, validButton, cancelButton, iv);
        
        Scene scene = new Scene(layout, 250, 100);  
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setTitle("êtes-vous sûr ?");
        stage.setScene(scene);
        stage.show();
 	}
	
	
	public void initGame(Game g) {
        status.setText(g.getStatus());
        int nbP = g.getPersonnages().size();
        int nbL = g.nbPersonnagesRestants();
        
        
          for (int i = 0; i < nbP; i++) {
        	  for (int j = 0; j < nbL; j++) {
        		  if(g.getLiP().get(j).getName().equals(g.getPersonnages().get(i).getName())) {
        			  break; 
        		  }
        		  if(j +1 == nbL) supprimerPersonnage(width, i);
            }
          }
          if (nbL<2) {
        	  int efficacite = (int)(((float)minQuestions/(float)nbQuestions)*100);
        	  stats.UpdateStats(efficacite, nombreTour, game.getPR().getName());
              showWin(efficacite);
          }
    }
	public void openButtonClick() {
		Stage stage = new Stage();
		String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
		fileChooser.setInitialDirectory(new File(currentPath));
		selectedFile = fileChooser.showOpenDialog(stage);
		String datapath = ""+selectedFile;
		try {
			Game g = (Game)parser.ChargerPartie(datapath);
			initGame(g);
			game = g;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveClick() {
		try {
			parser.EnregistrerPartie("game.json", game);
			status.setText("Vous avez sauvegardé ...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void openPersonnageClick() {
		Stage stage = new Stage();
		String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
		fileChooser.setInitialDirectory(new File(currentPath));
		selectedFile = fileChooser.showOpenDialog(stage);
		String datapath = ""+selectedFile;
		try {
			Personnage[] P2 = (Personnage[]) parser.ChargerPersonnage(datapath);
			P3 = new ArrayList<>( Arrays.asList(P2));
			status.setText("L'ensemble des personnages sera changé à la prochaine partie,\n"
					+ "veuillez vous assurez que les attributs correspondent");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void openAttributClick() {
		Stage stage = new Stage();
		String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
		fileChooser.setInitialDirectory(new File(currentPath));
		selectedFile = fileChooser.showOpenDialog(stage);
		String datapath = ""+selectedFile;
		try {
			Attribut[] E2 = (Attribut[]) parser.ChargerAttributs(datapath);
			E3 = new ArrayList<>( Arrays.asList(E2));
			status.setText("L'ensemble des attributs sera changé à la prochaine partie,\n"
					+ "veuillez vous assurez que les personnages correspondent");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void exitButton() {
		saveClick();
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
	}
	
	public void helpButton() {
		Stage stage = new Stage();
		stage.getIcons().add(new Image("file:images/help.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
         
         
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);

        Label label = new Label("Les règles : \n - Choisissez un attribut \n"
        		+ " - Choisissez une valeur \n - Appuyez sur Ajouter \n - Appuyez sur Valider\n"
        		+ " - Essayez de trouver par élimination la bonne personne");
        Button okButton = new Button("OK !");
        okButton.setOnAction(e -> stage.close());
        
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(label, okButton);
        
        Scene scene = new Scene(layout, 330, 150);  
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setTitle("Aide");
        stage.setScene(scene);
        stage.show();
	}
	
	
	
	public void statButton() {
		Stage stage = new Stage();
		stage.getIcons().add(new Image("file:images/analytics.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
         
        AnchorPane layout = new AnchorPane();
        Label label;
        
        if(stats.PartiesGagnees == 0) {
        	label = new Label("Désolé, vous devez gagner une partie pour voir vos statistiques");
        }
        else {
        	String persTrouves = "";
        	for(String s : stats.PersonnagesTrouves)persTrouves+= s + ", ";
        	persTrouves = persTrouves.substring(0, persTrouves.length() - 2);
        	if(stats.PersonnagesTrouves.size()>1) {
        		persTrouves = "\nLes " +stats.PersonnagesTrouves.size()+ 
        				" personnages que vous avez déjà trouvés : \n"+ persTrouves;
        	}
        	else {
        		persTrouves = "\nLe personnage que vous avez déjà trouvé : \n"+ persTrouves;
        	}
        	
        	 label = new Label("Parties gagnées : " +stats.PartiesGagnees +
        			  "\nNombre de tours moyen : " +stats.NombreDeToursMoyen +
        			  "\nEfficacité Moyenne : " +stats.EfficaciteMoyenne+"%" + 
        			  "\nMeilleure efficacité : " +stats.MeilleureEfficacite+"%" +
        			  persTrouves
        			  );
        }
        
        label.setWrapText(true);
        label.setPadding(new Insets(10, 10, 10,10));
        AnchorPane.setTopAnchor(label, 0.0);
        label.setTextAlignment(TextAlignment.JUSTIFY);
        label.setMaxWidth(287);
        
        Button okButton = new Button("OK !");
        okButton.setAlignment(Pos.BOTTOM_RIGHT);
        AnchorPane.setBottomAnchor(okButton, 0.0);
        AnchorPane.setRightAnchor(okButton, 0.0);
        okButton.setOnAction(e -> stage.close());
        
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(label, okButton);
        
        Scene scene = new Scene(layout, 330, 330);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setTitle("Statistiques");
        stage.setMinWidth(330);
        stage.setMaxWidth(330);
        stage.setMinHeight(330);
        stage.setScene(scene);
        stage.show();
	}
	
	public void showWin(int eff) {
		Stage stage = new Stage();
		stage.getIcons().add(new Image("file:images/spyware.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
         
         
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        
        Label label = new Label("Félicitations, vous m'avez trouvé"
        		+ "\nJe suis " + game.getPR().getName() 
        		+"\nNombre de questions utilisées : " + nbQuestions
        		+"\nEfficacité : "+ eff +"%");
        Image image = new Image("file:"+game.getPR().getFilepath());
        if (image.isError()) {
			image = new Image(game.getPR().getFilepath());
		}
        Button okButton = new Button("Nouvelle Partie !");
        okButton.setOnAction(e -> {
        	stage.close();
        	restart();
        });
		ImageView iv = new ImageView(image);
		iv.setFitHeight(200);
		iv.setFitWidth(200);
		GridPane.setConstraints(iv, 0, 0);
        GridPane.setConstraints(label, 1, 0,2,3);
		GridPane.setConstraints(okButton, 1, 1);
		
        
        layout.setHgap(5);
        layout.setVgap(15);
        layout.setPadding(new Insets(10, 10, 10, 10));
        
        layout.getChildren().addAll(label, iv, okButton);
        
        Scene scene = new Scene(layout, 500,250 );  
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setTitle("Vous avez gagné !");
        stage.setScene(scene);
        stage.show();
	}
	
	public void restart() {
		game = new Game(P3, E3);
		nombreTour = 0;
		nbQuestions = 0;
		width = 8;
		labelCompteur.setText("Tour : " + nombreTour);
		images = getImages(game);
		loadAttribut(game);
		resetValeurs();
		initGame(game);
		grid.getChildren().clear();
		addLabels(grid, width, images);
		
		
		///////////////////////////////////////Extension
		minQuestions = Extensions.nbEssaisMin(game.getPR(), game.getPersonnages());
		System.out.println("le minimum de questions est " +minQuestions);
		LabelNbQuestions.setText("Nombre minimum de questions pour gagner : "+minQuestions+" questions.");
	}
	
	public void easyWin() {
		while (!game.getPR().equals(P3.get(23))) {
			restart();
		}
	}
	
	public ArrayList<String> getImages(Game g) {
		images = new ArrayList<>();
		for (Personnage personnage : g.getPersonnages()) {
			images.add(personnage.getFilepath());
		}
		return images;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		System.out.println("Initialisation");
		addButton.setDisable(true);

		Attribut[] E2 = null;
		Personnage[] P2 = null;
		
		selectedFile = new File("personnage.json");
		
		try {
			E2 = (Attribut[]) parser.ChargerAttributs("attributs.json");
			P2 = (Personnage[]) parser.ChargerPersonnage("personnages.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		E3 = new ArrayList<>( Arrays.asList(E2));
		P3 = new ArrayList<>( Arrays.asList(P2));
		
		stats = StatExtension.GetStats();

		restart();
	}
}