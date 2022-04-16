package helloworld;

import java.util.ArrayList;
import java.util.Scanner;

public class CreationDataSet {
	
	Scanner in = new Scanner(System.in);
	private ArrayList<Personnage> pers = new ArrayList<>();
	private ArrayList<Attribut> atts = new ArrayList<>();
	
	public CreationDataSet() {
		
	}
	
	public CreationDataSet(ArrayList<Personnage> pers, ArrayList<Attribut> atts) {
		this.pers = pers;
		this.atts = atts;
	}
	
	public ArrayList<Attribut> getAtts(){
		return this.atts;
	}
	
	public ArrayList<Personnage> getPers(){
		return this.pers;
	}
	
	
	
	
	public void AjoutAttribut() {
		ArrayList<String> valeurs = new ArrayList<>();
		
		System.out.println("Entrez le nom de l'attribut");
		
		String nom = in.nextLine();
		System.out.println("Entrez le nombre de valeurs");
		
		int v = in.nextInt()+1;
		
		for(int i=0; i< v; i++) {
			System.out.println("Entrez une valeur");
			String val = in.nextLine();
			if (!val.equals(""))valeurs.add(val);
		}
		
		Attribut a = new Attribut(nom, valeurs);
		
		System.out.println(a.toString());
		this.atts.add(a);
	}
	
	public void AjoutPersonnage(String datapath) {
		
		ArrayList<Valeur> valeurs = new ArrayList<>();
		System.out.println("Entrez le nom du personnage");
		String nom ="";
		while(nom=="")nom = in.nextLine();
		
		System.out.println("Entrez l'image du personnage");
		String image = datapath+ in.nextLine();
		
		for(Attribut a : this.atts ) {
			
			System.out.println("Attribut " + a.toString());
			
			System.out.println("Choisissez la valeur pour cet attribut entre 1 et " + a.getValeurs().size() );
			int i = in.nextInt()-1;
			
			
			valeurs.add(new Valeur(a.getClef(), a.getValeurs().get(i) ));
			System.out.println("/////////////////////");
		}
		
		Personnage p = new Personnage(nom, image, valeurs);
		System.out.println(p.toString());
		System.out.println("/////////////////////");
		
		this.pers.add(p);
	}
	
	public boolean UniqueDataSet() {
		int nbAtt = this.atts.size();
		
		for(Personnage p1 : pers) {
			for(Personnage p2 : pers) {
				int temp =0;
				for(int i=0; i<nbAtt; i++ ) {
					if(p1.getValeurs().get(i).getValeur().equals(p2.getValeurs().get(i).getValeur() ) && p1!=p2) 
					{
						temp++;
					}
					else break;
				}
				if(temp ==nbAtt)return false;
			}
		}
		
		return true;
	}
	
	public int maxNbPers() {
		int cpt=1;
		for(Attribut a : atts) {
			cpt*= a.getValeurs().size();
		}
		return cpt;
	}
	
	
}
