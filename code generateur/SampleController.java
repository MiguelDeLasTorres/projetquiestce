package helloworld;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class SampleController implements Initializable{

	public ComboBox<String> attributs;
	public ComboBox<String> valeurs;
	public TextField textNom;
	public TextField attributField;
	public TextField valeurField;
	public ImageView imagePreview;
	public ImageView imageActive;
	public ListView<String> valeurActive;
	public Label labelNom;
	public Label nbAttribut;
	public Label status;
	public Button attributV;
	public Button valeurV;
	public Button valideButton;
	int indexPersonnage = 0;
	DirectoryChooser directoryChooser = new DirectoryChooser();
	ArrayList<String> listImages = new ArrayList<>();
	ArrayList<Attribut> listAttribut = new ArrayList<>();
	ArrayList<Valeur> listValeurs = new ArrayList<>();
	Personnage[] listPersonnage;
	JacksonParser parser = new JacksonParser();
	
    private static String OS = System.getProperty("os.name").toLowerCase();
	
    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0
                || OS.indexOf("nux") >= 0
                || OS.indexOf("aix") > 0);
    }
    
	/*
	 * Set la liste d'image listImages avec les images pr�sent dans le dossier s�lectionn�, et affiche la premier image
	 */
	@FXML
	public void fileButtonClick() {
		Stage stage = new Stage();
		DirectoryChooser dc = new DirectoryChooser();		
		File directory = dc.showDialog(stage);
		String[] images = null;
		imagePreview.setImage(null);
		listImages.clear();
		//On v�rifie si un dossier est s�lectionn� puis si il y a des images
		try {
			try {
				images = directory.list(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						return name.toLowerCase().endsWith(".png") || 
								name.toLowerCase().endsWith(".jpg") ||
								name.toLowerCase().endsWith(".jpeg") ||
								name.toLowerCase().endsWith(".gif") ||
								name.toLowerCase().endsWith(".bmp");
					}
				});
				for (String string : images) {
					if (isWindows()) {
						listImages.add("file:"+directory.getAbsolutePath()+"\\"+string);
					}else if (isUnix() || isMac()){
						listImages.add("file:"+directory.getAbsolutePath()+"/"+string);						
					}
				}
				listPersonnage = new Personnage[listImages.size()];
				Image first = new Image(listImages.get(indexPersonnage));
				imagePreview.setImage(first);
			}catch (IndexOutOfBoundsException e) {
				status.setText("Le dossier s�lectionn� ne contient pas d'images !");
			}
		}catch (NullPointerException e) {
			status.setText("Veuillez s�lectionner un dossier !");
		}
	}
	
	/*
	 * Fl�che gauche et droite permettant de changer de personnage
	 */
	
	public void leftArrowClick() {
		Image image; 
		if (!listImages.isEmpty()) {
			if (indexPersonnage!=0) {indexPersonnage--;}
			else {indexPersonnage=listImages.size()-1;}
			try {
				resetAllValue();
				setPreview(listPersonnage[indexPersonnage]);//On v�rifie si il y a un personnage � l'index demand� sinon on cr�e un page vierge
				}
			catch (NullPointerException e) {
				resetAllValue();
				}
			image = new Image(listImages.get(indexPersonnage));
			imagePreview.setImage(image);
		}
		
	}
	
	public void rightArrowClick() {
		Image image;
		if (!listImages.isEmpty()) {
			if (indexPersonnage!=listImages.size()-1) {indexPersonnage++;}
			else {indexPersonnage = 0;}
			try {
				resetAllValue();
				setPreview(listPersonnage[indexPersonnage]);//On v�rifie si il y a un personnage � l'index demand� sinon on cr�e un page vierge
				}
			catch (NullPointerException e) {
				resetAllValue();
				}
			image = new Image(listImages.get(indexPersonnage));
			imagePreview.setImage(image);
		}
	}
	
	/*
	 * Les boutons pour valider et supprimer les valeurs et les attributs
	 */
	
	public void attributVClick() {
		if (attributField.getText().length()>0 && !attributs.getItems().contains(attributField.getText())) {
			attributs.getItems().add(attributField.getText());
			listAttribut.add(new Attribut(attributField.getText()));
			attributs.getSelectionModel().select(attributField.getText());
			attributField.clear();
		}
	}
	
	public void attributClear() {
		if (attributs.getValue()!=null) {
			listAttribut.remove(Attribut.getAttribut(listAttribut, attributs.getValue()));
			attributs.getItems().remove(attributs.getSelectionModel().getSelectedItem());
		}
	}
	
	public void valeurVClick() {
		if (valeurField.getText().length()>0 && !valeurs.getItems().contains(valeurField.getText())) {
			valeurs.getItems().add(valeurField.getText());
			Attribut.getAttribut(listAttribut, attributs.getValue()).addValeur(valeurField.getText());
			valeurs.getSelectionModel().select(valeurField.getText());
			valeurField.clear();
		}
	}
	
	public void valeurClear() {
		if (valeurs.getValue()!=null) {
			Attribut.getAttribut(listAttribut, attributs.getValue()).delValeur(valeurs.getValue());
			valeurs.getItems().remove(valeurs.getSelectionModel().getSelectedItem());
		}
	}
	
	public void nomFill() {
		if (valeurs.getValue()!=null && textNom.getText().length()>0) {
			valideButton.setDisable(false);
		}else {
			valideButton.setDisable(true);
		}
	}
	
	public void attributsCBClick() {
		if (attributs.getValue()!=null) {
			valeurV.setDisable(false);
		valeurs.getItems().clear();
		if (!listAttribut.isEmpty()) {
			Attribut att = Attribut.getAttribut(listAttribut, attributs.getValue());
			for (String str : att.getValeurs()) {
				valeurs.getItems().add(str);
			}
		}
		}else {
			valeurV.setDisable(true);
		}
	}
	
	public void valeursCBClick() {
		if (valeurs.getValue()!=null && textNom.getText().length()>0) {
			valideButton.setDisable(false);
		}else {
			valideButton.setDisable(true);
		}
	}
	
	/*
	 * Pour ajouter les valeurs au tableau de valeurs associ�es au personnage s�lectionn�.
	 */
	
	public void setPreview(Personnage pers) {
		resetPreview();
		textNom.setText(pers.getName());
		imageActive.setImage(new Image(pers.getFilepath()));
		labelNom.setText(pers.getName());
		for (Valeur val : pers.getValeurs()) {
			valeurActive.getItems().add(val.getClef()+"  :  "+val.getValeur());
		}
		nbAttribut.setText(pers.getValeurs().size()+"/"+listAttribut.size());
	}
	
	public void resetPreview() {
		imageActive.setImage(null);
		labelNom.setText("");
		valeurActive.getItems().clear();
		
	}
	
	public void resetValue() {
		valeurs.getItems().clear();
		attributField.clear();
		valeurField.clear();
		valeurV.setDisable(true);
		valideButton.setDisable(true);
		attributs.getSelectionModel().select(-1);
	}
	
	public void resetAllValue() {
		resetValue();
		resetPreview();
		textNom.clear();
		listValeurs.clear();
		nbAttribut.setText("");
	}
	
	public boolean checkAttribut() {
		for (Personnage pers : listPersonnage) {
			if (pers==null) {
				return false;
			}
			if (pers.getValeurs().size()!=listAttribut.size()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean checkValidity() {
		for (int i = 0; i < listPersonnage.length; i++) {
			ArrayList<Valeur> valeursI = listPersonnage[i].getValeurs();
			for (int j = i+1; j < listPersonnage.length; j++) {
				ArrayList<Valeur> valeursJ = listPersonnage[j].getValeurs();
				if (Valeur.equalsArray(valeursJ, valeursI)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void valideButton() {
		if (!listImages.isEmpty()) {
			status.setText("Ajout des valeurs");
			Valeur val = new Valeur(attributs.getValue(), valeurs.getValue());
			listValeurs.add(val);
			if (listPersonnage[indexPersonnage]==null) {
				Personnage personnage = new Personnage(textNom.getText(), listImages.get(indexPersonnage), listValeurs);
				listPersonnage[indexPersonnage] = personnage;
			}else {
				if (Valeur.getValeurFromArray(listPersonnage[indexPersonnage].getValeurs(),val.getClef())==null) {	
					listPersonnage[indexPersonnage].getValeurs().add(val);
				}else {
					listPersonnage[indexPersonnage].getValeurFromClef(val.getClef()).setValeur(val.getValeur());
				}
			}
			setPreview(listPersonnage[indexPersonnage]);
			resetValue();
		}else {
			status.setText("Vous n'avez pas s�lectionn� de dossier");
		}
	}
	
	public void cancelButton() {
		resetValue();
		valeurV.setDisable(false);
	}
	
	public void saveButton() {
		try {
			if (checkAttribut() && checkValidity()) {
				try {
					parser.EnregistrerAttributs("Attribut.json", listAttribut);
					parser.EnregistrerPersonnages("Personnage.json", listPersonnage);
					status.setText("Vous avez sauvegard� !");
				}catch (IOException e){
					status.setText("Erreur lors de la sauvegarde !");
					e.printStackTrace();
				}
			}else {
				status.setText("Vous n'avez pas compl�t� les caract�ristiques de chaque personnages !");
			}
		}catch(NullPointerException e) {
			status.setText("Vous n'avez rien � sauvegarder !");
		}
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
