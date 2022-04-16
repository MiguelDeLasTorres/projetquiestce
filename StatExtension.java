package helloworld;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StatExtension {
	
	@JsonProperty("Parties gagn�es") 
	int PartiesGagnees;
	@JsonProperty("Nombre de tours moyen")
	float NombreDeToursMoyen;
	@JsonProperty("Efficacit� Moyenne (pourcent)")
	int EfficaciteMoyenne;
	@JsonProperty("Meilleure efficacit� (pourcent)")
	int MeilleureEfficacite;
	@JsonProperty("Les personnages que vous avez trouv�s")
	ArrayList<String> PersonnagesTrouves = new ArrayList<String>();
	
	@JsonCreator
	public StatExtension(@JsonProperty("Parties gagn�es") 
	int PartiesGagnees,
	@JsonProperty("Nombre de tours moyen")
	float NombreDeToursMoyen,
	@JsonProperty("Efficacit� Moyenne (pourcent)")
	int EfficaciteMoyenne,
	@JsonProperty("Meilleure efficacit� (pourcent)")
	int MeilleureEfficacite,
	@JsonProperty("Les personnages que vous avez trouv�s")
	ArrayList<String> PersonnagesTrouves) {
		this.PartiesGagnees = PartiesGagnees;
		this.NombreDeToursMoyen = NombreDeToursMoyen;
		this.EfficaciteMoyenne = EfficaciteMoyenne;
		this.MeilleureEfficacite = MeilleureEfficacite;
		this.PersonnagesTrouves.addAll(PersonnagesTrouves);
	}
	
	public void UpdateStats(int eff, int nbTours, String pers) {
		nbTours++;
		
		this.NombreDeToursMoyen = (this.NombreDeToursMoyen * this.PartiesGagnees + nbTours)/(this.PartiesGagnees+1);
		this.NombreDeToursMoyen = (float) (Math.round(this.NombreDeToursMoyen * 100.0) / 100.0);
		this.EfficaciteMoyenne = (this.EfficaciteMoyenne * this.PartiesGagnees + eff)/(this.PartiesGagnees+1);
		if(this.MeilleureEfficacite < eff)this.MeilleureEfficacite = eff;
		this.PartiesGagnees++;
		if(this.PersonnagesTrouves.size()==0)this.PersonnagesTrouves.add(pers);
		else {
			for(int i=0; i< this.PersonnagesTrouves.size();i++) {
				if(this.PersonnagesTrouves.get(i).equals(pers))break;
			
				if(i == this.PersonnagesTrouves.size()-1) {
					this.PersonnagesTrouves.add(pers);
				}
			}
		}
		
		JacksonParser parser = new JacksonParser();
		
		try {
			parser.EnregistrerStats("stats.json", this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static StatExtension GetStats() {
		JacksonParser parser = new JacksonParser(); 
		StatExtension stats = null;
		try {
			 stats = (StatExtension) parser.ChargerStats("stats.json");
		} catch (IOException e) {
			System.out.println("Pas de fichier de statistiques, il sera cr�� lorsque vous aurez gagn� une partie.");
		}
		if(stats ==null) {
			stats = new StatExtension(0,0,0,0,new ArrayList<String>());
		}
		return stats;
	}
}