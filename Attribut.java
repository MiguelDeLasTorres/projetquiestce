package helloworld;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Attribut {
	private String clef;
	private ArrayList<String> valeurs;
	
	@JsonCreator
	public Attribut(@JsonProperty("clef") String clef, @JsonProperty("valeurs") ArrayList<String> valeurs) {
		this.setClef(clef);
		this.setValeurs(valeurs);
	}

	public ArrayList<String> getValeurs() {
		return valeurs;
	}

	public void setValeurs(ArrayList<String> valeurs) {
		this.valeurs = valeurs;
	}
	
	public String toString() {
		return clef + "\n" + String.join(", ", valeurs);
		
	}

	public String getClef() {
		return clef;
	}
	public void setClef(String clef) {
		this.clef = clef;
	}
}
