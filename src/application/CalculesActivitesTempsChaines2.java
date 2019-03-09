
package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class CalculesActivitesTempsChaines2 {
	private String listeProdImpossible;
	
	public CalculesActivitesTempsChaines2() {

	}

	public void calcul(ArrayList<Element> elements, ArrayList<ChaineDeProduction> chaines, Double[] niveau,ArrayList<Element> listAchat, ArrayList<Production> production) {
		boolean estPossible = false;
		int i = 0;
		this.listeProdImpossible = "";
		double coutVente = 0;
		double efficacite = 0;
		double totalAchatChaine = 0;
		
		for (ChaineDeProduction c : chaines) {
        	coutVente = 0;
        	efficacite = 0;
        	totalAchatChaine = 0;
			estPossible = true;
			
    		if (niveau[i] == 0) {
    			//Si le niveau de production est à 0, la chaine ne produit rien
    			estPossible = false;
    		}
    		else {
    			/*
    			 * Pour chaque élement en entrée dans la chaine de production, on récupère la quantité nécessaire et
    			 * s'il correspond à l'élement de notre tableau des éléments, on met à jour la quantité 
    			 * dans notre tableau des élements.
    			 */
    			this.majEntree(c, elements, niveau, i);
    			/*
    			 * Pour chaque élement en sortie de la chaine de production, on récupère la quantité sortie et
    			 * s'il correspond à l'élement de notre tableau des éléments, on met à jour la quantité 
    			 * dans notre tableau des élements.
    			 */
    			this.majSortie(c, elements, niveau, i);
    			
    			//Calcul du cout de vente des éléments en sortie pour la chaine actuelle
    			coutVente = this.calculCoutVente(c, elements);
    			/*
    			 * Pour chaque élement en entrée dans la chaine de production,
    			 * s'il correspond à l' un des élements de notre tableau d'éléments et qu'il une quantité négative,
    			 * on remplit notre tableau de liste d'élements à acheter en fonction et on fait la somme du prix d'achat
    			 * de chaque élement à acheter
    			 */
    			for (Element elEntree: c.getEntree().keySet()) {
        			for (Element e : elements) {
        				if (e.getCode() == elEntree.getCode()) {
        					if (e.getQuantite() < 0) {
        						if (e.getAchat() == 0) {
        							estPossible = false;
        						}
        						else{
	        						listAchat.add(e);
	        						totalAchatChaine += Math.abs(e.getQuantite()) * e.getAchat();
	        					}
	        				}
        				}
        				
        			}
    			}
				efficacite = coutVente-totalAchatChaine;
    		}
			if (estPossible == false) {
				this.listeProdImpossible += c.getCode() + ": PRODUCTION IMPOSSIBLE\n";
				
				Production p = new Production(c, 0, 0);
				p.setSatisDemande("0% satisfait");
    			production.add(p);
    		}
    		else {
    			Production p = new Production(c,coutVente, efficacite);
    			
    			double quantiteProduite = 0;
    			for(double val : p.getChaine().getSortie().values()) {
    				quantiteProduite += val;
    			}
    			quantiteProduite *= niveau[i];
    			if(p.getDemande() <= quantiteProduite) {
    				p.setSatisDemande("Satisfaite");
    			}
    			else {
    				double percent = (quantiteProduite * 100)/p.getDemande();
    				p.setSatisDemande(String.format("%.2f", percent)+"% satisfait(s)");	
    			}
    			
    			production.add(p);
    		}
    	}
		i++;
	}
	
	public void calculTemps(ArrayList<Element> elements, ArrayList<ChaineDeProduction> chaines, Double[] niveau) {
		ArrayList<ChaineDeProduction> chaines_independantes = new ArrayList<>();
		ArrayList<ChaineDeProduction> chaines_entrees_limites = new ArrayList<>();
		ArrayList<ChaineDeProduction> chaines_entrees_limites_non_concurrence = new ArrayList<>();

		//Liste des chaines se disputant une meme entree
		LinkedHashSet<ChaineDeProduction> chaine_disputant_meme_entree = new LinkedHashSet<>();
		//Listes des dépendances pour les chaines en entree limités:
		//Exemple <chaine1, C001><chaine2, c001> <chaine3,C004>
		HashMap<ChaineDeProduction, String> chaines_entrees_limites_non_concurrence_dependances = new HashMap<>();
		for(ChaineDeProduction c: chaines) {
			boolean achetable = true;
			//Si un des élements en entrées n'a pas de prix d'achat, alors on met achetable à false et on ajoute la chaine courantes
			//dans la liste des chaines dont les elements en entrees sont en quantités limités
			for (Element eltsEntrees : c.getEntree().keySet()) {
				if (eltsEntrees.getAchat() == 0) {
					achetable = false;
				}
			}
			if(achetable) {
				chaines_independantes.add(c);
			}
			else {
				chaines_entrees_limites.add(c);
			}
		}
		
		//Pour chaque chaines dont les élements en entrées sont en quantités limités, on sépare ceux qui se disputent les meme chaines
		boolean entree_commun = true;
		for(ChaineDeProduction c1: chaines_entrees_limites) {
			for(ChaineDeProduction c2: chaines_entrees_limites) {
				for (Element e1: c1.getEntree().keySet()) {
					for (Element e2: c2.getEntree().keySet()) {
						if ((e1.getCode() == e2.getCode()) && (c1.getCode() != c2.getCode())) {
							chaine_disputant_meme_entree.add(c1);
							chaine_disputant_meme_entree.add(c2);
						}
					}
				}
			}
		}
		
		for (ChaineDeProduction c: chaines_independantes) {
			System.out.println("Chaine demarrage independantes: " + c.getCode());
		}
		
		for (ChaineDeProduction c : chaine_disputant_meme_entree) {
			System.out.println("Chaine dont les quantites demandées en entrée sont limitées (non achetable) et se dipsutant un element commun: " + c.getCode());
		}
		
		for (ChaineDeProduction c1 : chaines_entrees_limites) {
			if (!chaine_disputant_meme_entree.contains(c1)) {
				chaines_entrees_limites_non_concurrence.add(c1);
				System.out.println("Chaine dont les quantites demandées en entrée sont limitées (non achetable): " + c1.getCode());
			}
		}
		
		//Pour les chaines dont les quantites demandées en entrée sont limitées (non achetable) mais ne se disputent pas les memes elements avec les autres
		//On récupère la chaine dont elle dépend
		for (ChaineDeProduction c : chaines) {
			for (ChaineDeProduction c1 : chaines_entrees_limites_non_concurrence) {
				for(Element elEntree : c1.getEntree().keySet()) {
					for (Element elSorti: c.getSortie().keySet()) {
						if (elEntree.getCode() == elSorti.getCode()) {
							chaines_entrees_limites_non_concurrence_dependances.put(c, c1.getCode());
						}
					}
				}
			}
		}
		
		for (ChaineDeProduction c : chaines_entrees_limites_non_concurrence_dependances.keySet()) {
			System.out.println("La chaine: " + chaines_entrees_limites_non_concurrence_dependances.get(c) + " depand de la chaine " + c.getCode());
		}
		
		
		//Pour les chaines dont les quantites demandées en entrée sont limitées (non achetable) et se disputent les memes élements
		for (ChaineDeProduction c : chaines) {
			for (ChaineDeProduction c1 : chaine_disputant_meme_entree) {
				for(Element elEntree : c1.getEntree().keySet()) {
					for (Element elSorti: c.getSortie().keySet()) {
						if (elEntree.getCode() == elSorti.getCode()) {
							//System.out.println(c.getCode());
						}
					}
				}
			}
		}
	}
	
	private void majEntree(ChaineDeProduction c, ArrayList<Element> elements, Double[] niveau, int indice) {
		for (Element elEntree: c.getEntree().keySet()) {
			for (Element elStock : elements) {
				if (elEntree.getCode() == elStock.getCode()) {
					double newQuantite = elStock.getQuantite() - (c.getEntree().get(elEntree)*niveau[indice]);
					elStock.setQuantite(newQuantite);
				}
			}
		}
	}
	
	private void majSortie(ChaineDeProduction c, ArrayList<Element> elements, Double[] niveau, int indice) {
		for (Element elSorti : c.getSortie().keySet()) {
			for (Element elStock: elements) {
				if (elSorti.getCode() == elStock.getCode()) {
					elStock.setQuantite(elStock.getQuantite() + (c.getSortie().get(elSorti)*niveau[indice]));
				}
			}
		}
	}
	
	private double calculCoutVente(ChaineDeProduction c, ArrayList<Element> elements) {
		double coutVente = 0;
		for (Element elSorti : c.getSortie().keySet()) {
			for (Element elStock: elements) {
				if (elSorti.getCode() == elStock.getCode()) {
					if (elStock.getVente() != 0) {
    					coutVente += elStock.getQuantite() * elStock.getVente();
					}
				}
			}
		}
		return coutVente;
	}
	
	public String getListeProdImpossible() {
		return listeProdImpossible;
	}

	public void setListeProdImpossible(String listeProdImpossible) {
		this.listeProdImpossible = listeProdImpossible;
	}
	
}
