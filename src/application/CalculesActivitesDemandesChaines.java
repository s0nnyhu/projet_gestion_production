package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CalculesActivitesDemandesChaines {
	
	private double coutVenteTotal;
	private double efficacite;
	private double totalAchatUsine;
	private String listeProdImpossible;
	
	public CalculesActivitesDemandesChaines() {
		this.coutVenteTotal = 0;
		this.efficacite = 0;
		this.totalAchatUsine = 0;
	}

	public void calcul(ArrayList<Element> elements, ArrayList<ChaineDeProduction> chaines, Double[] niveau,ArrayList<Element> listAchat, ArrayList<Production> production) {
		boolean estPossible;
		int indexListeAchat = 0;
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
    			//c.setSortie(null);
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
    			coutVente = this.majSortie(c, elements, niveau, coutVente, i);
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
        						else
        						{
	        						listAchat.add(e);
	        						//this.txtListeAchat.appendText(listeAchat.get(indexListeAchat).getCode() + ": " + Math.abs(listeAchat.get(indexListeAchat).getQuantite()) + "\n");
	        						totalAchatChaine += e.getQuantite() * e.getAchat();
	        						indexListeAchat++;
	        					}
	        				}
        				}
        				
        			}
    			}
				efficacite = coutVente-totalAchatChaine;
    		}
			if (estPossible == false) {
				
				this.listeProdImpossible += c.getCode() + ": PRODUCTION IMPOSSIBLE\n";
				//System.out.println(c.getCode() + ": PRODUCTION IMPOSSIBLE\n");
    			production.add(new Production(c, 0, 0));
    		}
    		else {
    			production.add(new Production(c,coutVente, efficacite));
    		}
    	}
		
		for (int j=0; j<production.size(); j++) {
			Production p = production.get(j);//Production courante
			int quantiteProduite = 0;
			for(double val : p.getChaine().getSortie().values()) {
				quantiteProduite += val;
			}
			quantiteProduite *= niveau[j];
			if(p.getDemande() <= quantiteProduite) {
				p.setSatisDemande("Satisfaite");
			}
			else {
				double percent = (quantiteProduite * 100)/p.getDemande();
				p.setSatisDemande(String.format("%.2f", percent)+"% satisfait(s)");
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
	
	private double majSortie(ChaineDeProduction c, ArrayList<Element> elements, Double[] niveau, double coutVente, int indice) {
		for (Element elSorti : c.getSortie().keySet()) {
			for (Element elStock: elements) {
				if (elSorti.getCode() == elStock.getCode()) {
					elStock.setQuantite(elStock.getQuantite() + (c.getSortie().get(elSorti)*niveau[indice]));
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
