package helloworld;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Attribut {
	private String clef;
	private ArrayList<String> valeurs;
	
	@JsonCreator
	public Attribut(@JsonProperty("clef") String clef) {
		this.setClef(clef);
		this.valeurs = new ArrayList<>();
	}
	
	@JsonCreator
	public Attribut(@JsonProperty("clef") String clef, @JsonProperty("valeurs") ArrayList<String> valeurs) {
		this.setClef(clef);
		this.setValeurs(valeurs);
	}
	
	public String toString() {
		return clef + "\n" + String.join(", ", valeurs);
		
	}
	
	public void addValeur(String val) {
		this.valeurs.add(val);
	}
	
	public void delValeur(String val) {
		for (String string : valeurs) {
			if (string.equals(val)) {
				this.valeurs.remove(string);
			}
		}
	}
	/*
	 * Renvoie l'attribut d'un tableau d'attribut dont la clef correspond avec la chaine de caractère donnée en paramètre
	 */
	
	public static Attribut getAttribut(ArrayList<Attribut> listAttribut, String clef) {
		for (Attribut attribut : listAttribut) {
			if (clef.equals(attribut.getClef())) {
				return attribut;
			}
		}
		return null;
	}

	public ArrayList<String> getValeurs() {
		return valeurs;
	}

	public void setValeurs(ArrayList<String> valeurs) {
		this.valeurs = valeurs;
	}
	
	public String getClef() {
		return clef;
	}
	public void setClef(String clef) {
		this.clef = clef;
	}
}
