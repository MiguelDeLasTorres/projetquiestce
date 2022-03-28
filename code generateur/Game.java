package helloworld;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Game {

	@JsonProperty("personnages") 
	private ArrayList<Personnage> personnages = new ArrayList<>();
	@JsonProperty("attributs") 
	private ArrayList<Attribut> attributs = new ArrayList<>();
	@JsonProperty("liP") 
	private ArrayList<Personnage>  liP = new ArrayList<>();//personnagesRestants
	@JsonProperty("attributsUtilises") 
	private ArrayList<Attribut> attributsUtilises = new ArrayList<>();
	private Personnage pR;//personnage recherche
	private int nbTotalPers;
	private String status;

	public Game(ArrayList< Personnage> personnages, ArrayList<Attribut> attributs) {
		this.attributs.addAll(attributs);
		this.attributsUtilises.addAll(attributs);
		this.personnages.addAll(personnages);
		this.liP.addAll(personnages);
		this.nbTotalPers = personnages.size();
		this.pR = choixPersonnage();
	}
	
	@JsonCreator
	public Game(@JsonProperty("personnages")ArrayList<Personnage> personnages, 
			@JsonProperty("attributs") ArrayList<Attribut> attributs,
			@JsonProperty("liP") ArrayList<Personnage>  liP,
			@JsonProperty("attributsUtilises") ArrayList<Attribut> attributsUtilises,
			@JsonProperty("pR") Personnage pR,
			@JsonProperty("nbTotalPers") int nbTotalPers,
			@JsonProperty("status") String status
			
			) {
		
		this.personnages.addAll(personnages);
		this.attributs.addAll(attributsUtilises);
		this.liP.addAll(liP);
		this.attributsUtilises.addAll(attributsUtilises);
		this.pR=pR;
		this.nbTotalPers=nbTotalPers;
		this.status=status;
	}
	
	//Choisit un personnage aleatoirement
	private Personnage choixPersonnage() {
		int randIndex = ThreadLocalRandom.current().nextInt(0, this.nbTotalPers);
		System.out.println("Personnage recherche " + this.personnages.get(randIndex).getName());
		return this.personnages.get(randIndex);
	}
	
	// Liste de personnage, enleve les personnages ayant les attributs correspondant si corrects ou les autres sinon
	public ArrayList<Personnage> verifAttribut(ArrayList<Valeur> liV){
		
		for ( Valeur v : liV) {
			System.out.println("Verification de l attribut : " +v.getClef() + ", valeur : " +v.getValeur());
			if(this.pR.verifValeur(v) ) {
				System.out.println("Le PR a comme "+ v.getClef() + " : " + v.getValeur());
				setStatus("Le personnage recherché a comme "+ v.getClef() + " : " + v.getValeur());
				for(Personnage p : new ArrayList<Personnage>(this.liP)) {
					if( !p.verifValeur(v) ) {
						this.SupprimerPersonnage(p);
					}
				}
			}
			else {
				System.out.println("Le PR n'a pas comme "+ v.getClef() + " : " + v.getValeur());
				setStatus("Le personnage recherché n'a pas comme "+ v.getClef() + " : " + v.getValeur());
				for(Personnage p : new ArrayList<Personnage>(this.liP)) {
					if( p.verifValeur(v)) {
						this.SupprimerPersonnage(p);
					}
				}
			}
		}
		if(this.nbPersonnagesRestants() <2) {
			this.finPartie();
		}
		else {
			prochainTour();
		}
		
		return this.liP;
	}
	
	//Supprimer le personnage en paramètre
	private void SupprimerPersonnage(Personnage p) {
		System.out.println(p.getName() + " a ete elimine!");
		this.liP.remove(p);
	}
	
	private void prochainTour() {
		System.out.println("Personnages restants " +this.nbPersonnagesRestants());
		for(Personnage p :this.liP)
			System.out.println( p.getName());
		System.out.println("--------------------------------");
	}
	
	private void finPartie() {
		System.out.println("--------------------------------");
		System.out.println("Partie terminee!\n Le personnage " + this.pR.getName()+ " a ete decouvert!");
	}
	
	public ArrayList<String> getValeurFromAttribut(String clef) {
		ArrayList<String> valeurs = new ArrayList<>();
		for (Attribut attribut : attributsUtilises) {
			if (clef.equals(attribut.getClef())) {
				return attribut.getValeurs();
			}
		}
		return valeurs;
	}
	
	public int nbPersonnagesRestants() {
		return liP.size();
	}
	@JsonIgnore
	public ArrayList<String> getListDatapath(){
		ArrayList<String> listDatapath = new ArrayList<>();
		for (Personnage personnage : personnages) {
			 listDatapath.add(personnage.getFilepath());
		}
		return listDatapath;
	}
	
	public ArrayList<Personnage> getLiP(){
		return this.liP;
	}
	public ArrayList<Attribut> getAttributs() {
		return attributs;
	}

	public void setAttributs(ArrayList<Attribut> attributs) {
		this.attributs = attributs;
	}

	public ArrayList<Attribut> getAttributsUtilises() {
		return attributsUtilises;
	}

	public void setAttributsUtilises(ArrayList<Attribut> attributsUtilises) {
		this.attributsUtilises = attributsUtilises;
	}
	
	public ArrayList<Personnage> getPersonnages() {
		return personnages;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	public Personnage getPR() {
		return this.pR;
	}
	
	public void setPr(Personnage pR) {
		this.pR = pR;
	}
	
}