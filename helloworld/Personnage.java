package helloworld;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.*;

public class Personnage {
	private String name;
	private String filepath;
	private ArrayList<Valeur> valeurs = new ArrayList<>();
	
	@JsonCreator
	public Personnage(@JsonProperty("name")String name, @JsonProperty("filepath")String filepath, @JsonProperty("valeurs")ArrayList<Valeur> valeurs) {
		this.name = name;
		this.filepath = filepath;
		this.valeurs.addAll(valeurs);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public ArrayList<Valeur> getValeurs() {
		return valeurs;
	}
	public void setValeurs(ArrayList<Valeur> valeurs) {
		this.valeurs = valeurs;
	}
	
	/*
	 * Renvoie la valeur correspondant à la clef donnée en paramètre 
	 * parmi l'ensemble des valeurs associé à un personnage.
	 */
	public Valeur getValeurFromClef(String clef) {
		for (Valeur v : this.valeurs) {
			if(v.getClef().equals(clef))return v; 
		}
		return valeurs.get(0);
	}
	
	/*
	 * Renvoie vrai si la valeur en paramètre correspond à une des valeurs du personnage.
	 */
	public boolean verifValeur(Valeur v) {
		return this.getValeurFromClef(v.getClef()).getValeur().equals( v.getValeur() );
	}
	
	public static boolean verifPersonnage(ArrayList<Personnage> personnages, String nom) {
		for (Personnage personnage : personnages) {
			if (personnage!=null) {
			return personnage.getName().equals(nom);
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		String s="  "+this.name + "\nfilepath : " + this.filepath + "\n";
		for(Valeur v : this.valeurs) {
			s+=v.getClef() + " : " + v.getValeur() + "\n"; 
		}
		return s;
	}
}
