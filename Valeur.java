package helloworld;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Valeur {
	private String clef;
	private String valeur;
	
	@JsonCreator
	public Valeur(@JsonProperty("clef")String clef,@JsonProperty("valeur")String valeur) {
		this.setClef(clef);
		this.setValeur(valeur);
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeurs) {
		this.valeur = valeurs;
	}

	public String getClef() {
		return clef;
	}

	public void setClef(String clef) {
		this.clef = clef;
	}

}
