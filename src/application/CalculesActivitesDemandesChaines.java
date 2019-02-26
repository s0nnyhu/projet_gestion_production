package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import donnees.DonneesLibraryCSV;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CalculesActivitesDemandesChaines {
	
	private String listeProdImpossible;
	
	public CalculesActivitesDemandesChaines() {

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
