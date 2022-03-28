package helloworld;

import java.util.ArrayList;

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
	
	//Peut servir un jour
	public static Valeur getValeurFromArray(ArrayList<Valeur> valeurs, String clef) {
		for (Valeur valeur : valeurs) {
			if (valeur.getClef().equals(clef)) {
				return valeur;
			}
		}
		return null;
	}
	
	public static boolean equalsArray(ArrayList<Valeur> valeurs1, ArrayList<Valeur> valeurs2) {
		int x = 0;
		for (Valeur valeur : valeurs1) {
			for (Valeur valeur2 : valeurs2) {
				if(valeur.equals(valeur2)) {
					x++;
				}
			}
		}
		return x== valeurs1.size();
	}
		
	public boolean equals(Valeur v) {
		return (this.getClef().equals(v.getClef()) && (this.getValeur().equals(v.getValeur())));
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
