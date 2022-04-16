package helloworld;

import java.util.ArrayList;

public class Extensions {
	
	
	//n nb existants d'elements
	//r nb d'éléments que l'on selectionne
	public static ArrayList<int[]> generate(int n, int r) {
	    ArrayList<int[]> combinaisons = new ArrayList<>();
	    int[] combinaison = new int[r];

	    // initialiser avec la plus petite combinaison
	    for (int i = 0; i < r; i++) {
	        combinaison[i] = i;
	    }

	    while (combinaison[r - 1] < n) {
	        combinaisons.add(combinaison.clone());

	         // generation de la prochaine combinaison
	        int t = r - 1;
	        while (t != 0 && combinaison[t] == n - r + t) {
	            t--;
	        }
	        combinaison[t]++;
	        for (int i = t + 1; i < r; i++) {
	            combinaison[i] = combinaison[i - 1] + 1;
	        }
	    }
	    return combinaisons;
	}

	public static int nbEssaisMin(Personnage pR, ArrayList<Personnage> pers) {
		//Transformation des données en tableau montrant les différences entre le personnage recherché et les autres
		ArrayList<Valeur> liVa = pR.getValeurs();
		int nbVa = liVa.size();
		int nbPers = pers.size();
		int[][] tab= new int[pers.size()][liVa.size()];
		int i = 0;
		for (Valeur vPR : liVa) {
			int j=0;
			for (Personnage p : pers) {
				for (Valeur v : p.getValeurs()) {
					if (v.getClef().equals(vPR.getClef())) {
						if (v.getValeur().equals(vPR.getValeur())) {
							tab[j][i] = 1;
						}
					}
				}
				j++;
			}
			i++;
		}
		//Calcul des arrangements d'attributs possibles
		//Ces arrangements croissent en taille jusqu'à trouver un arrangement qui permet de trouver le personnage recherché
		
		for(i = 1; i< nbVa; i++) {//Pour chaque taille i de combinaison
			ArrayList<int[]> combinaisons = Extensions.generate(nbVa, i);
			for(int[] combi : combinaisons) {//Pour chaque combinaison de taille i
				int persDifferents = 0;
				for(int j =0; j<nbPers;j++) {//Pour chaque Personnage
					int pareil=0;
					for(int k=0; k<i;k++) {//Pour chaque Valeur
						pareil += tab[j][combi[k]];//Ajouter la valeur
					}
					if(pareil<i)persDifferents++;//Si le personnage n'est pas identique pour ces valeurs, ajouter 1
				}
				
				if(persDifferents==nbPers-1) {//Si il n'y a qu'un seul personnage pareil (lui-même)
					return i;//Renvoyer la taille de la combinaison
				}
			}	
		}
		return nbVa;
	}
}
