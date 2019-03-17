
package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class CalculesActivitesTempsChaines {
	private String listeProdImpossible;
	
	public CalculesActivitesTempsChaines() {

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
	
	public void calculTemps(ArrayList<Element> elements, ArrayList<ChaineDeProduction> list_chaines_usine, ArrayList<ChaineDeProduction> chaines, Double[] niveau, ArrayList<Stockage> stockages) {
		ArrayList<ChaineDeProduction> chaines_independantes = new ArrayList<>();
		ArrayList<ChaineDeProduction> chaines_entrees_limites = new ArrayList<>();
		ArrayList<ChaineDeProduction> chaines_entrees_limites_non_concurrence = new ArrayList<>();

		//Liste des chaines se disputant une meme entree
		LinkedHashSet<ChaineDeProduction> chaine_disputant_meme_entree = new LinkedHashSet<>();

		//Listes des dépendances pour les chaines en entrée limités avec concurrence:
		ArrayList<ChaineDeProduction> chaines_entrees_limites_concurrence_dependances_fils = new ArrayList<>();
		//Listes des chaines dont les dependances se trouvent dans chaines_entrees_limites_concurrence_dependances_fils
		ArrayList<ChaineDeProduction> chaines_entrees_limites_concurrence_peres = new ArrayList<>();
		
		//Listes des dépendances pour les chaines en entrée limités sans concurrence:
		ArrayList<ChaineDeProduction> chaines_entrees_limites_non_concurrence_dependances_fils = new ArrayList<>();
		//Listes des chaines dont les dependances se trouvent dans chaines_entrees_limites_non_concurrence_dependances_fils
		ArrayList<ChaineDeProduction> chaines_entrees_limites_non_concurrence_peres = new ArrayList<>();
		
		
		
		HashMap<ChaineDeProduction, ArrayList<ChaineDeProduction>> chaines_entrees_limites_concurrence_peres_fils = new HashMap<>();
		HashMap<ChaineDeProduction, ArrayList<ChaineDeProduction>> chaines_entrees_limites_non_concurrence_peres_fils = new HashMap<>();
		
		int i = 0;
		for(ChaineDeProduction c: chaines) {
			//Mise à jour du temps de la chaine en fonction du niveau d'activité
			c.setTemps((int) (niveau[i]*c.getTemps()));
			
			//Mise à jour des stocks en entrée et sortie de la chaine en fonction du niveau d'activité
			for (Element elementChaine : c.getEntree().keySet()) {
				c.getEntree().put(elementChaine, (double) c.getEntree().get(elementChaine) * niveau[i]);
			}
			for (Element elementChaine : c.getSortie().keySet()) {
				c.getSortie().put(elementChaine, (double) c.getSortie().get(elementChaine) * niveau[i]);
			}
			
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
			
			//Gestion du stockage des sorties des chaines
			HashMap<Element, Double> sorties = c.getSortie();
			String stockImpossible = "";
			for(Element sortie : sorties.keySet()) { //pour chaque sortie
				double qteProduite = sortie.getQuantite(); //on recup�re la quantit� produite
				int nbCuves = (int) Math.ceil(qteProduite/sortie.getStockage().getCapacite()); //on calcule le nombre de cuves � utiliser
				if(nbCuves < sortie.getStockage().getQuantiteDispo()) {
					for(Stockage sto : stockages) {
						if(sortie.getStockage().getCode().equals(sto.getCode())) {
							sto.reduireQuantite(nbCuves); // on met a jour les moyens de stockage dispo							
						}
					}
				}
				else {
					stockImpossible += "Il manque "+nbCuves+" recipent(s) pour stocker des "+sortie.getNom()+"\n";
				}
			}
			//Gestion du stockage des entree des chaines
			HashMap<Element, Double> entrees = c.getEntree();
			for(Element entree : entrees.keySet()) { //pour chaque sortie
				double qteProduite = entree.getQuantite(); //on recup�re la quantit� produite
				Stockage simulStockage = new Stockage(entree.getStockage());
				int nbCuves = (int) Math.ceil(qteProduite/simulStockage.getCapacite()); //on calcule le nombre de cuves � utiliser
				if(nbCuves < entree.getStockage().getQuantiteDispo()) {
					for(Stockage sto : stockages) {
						if(entree.getStockage().getCode().equals(sto.getCode())) {
							sto.reduireQuantite(nbCuves); // on met a jour les moyens de stockage dispo							
						}
					}
				}
				else {
					stockImpossible += "Il manque "+nbCuves+" recipent(s) pour stocker des "+entree.getNom()+" en entree de la chaine "+c.getNom()+"\n";
				}
			}
			System.out.println(stockImpossible);
			i++;
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
			//System.out.println("Chaine demarrage independantes: " + c.getCode());
		}
		
		for (ChaineDeProduction c : chaine_disputant_meme_entree) {
			//System.out.println("Chaine dont les quantites demandées en entrée sont limitées (non achetable) et se dipsutant un element commun: " + c.getCode());
		}
		
		for (ChaineDeProduction c1 : chaines_entrees_limites) {
			if (!chaine_disputant_meme_entree.contains(c1)) {
				chaines_entrees_limites_non_concurrence.add(c1);
				//System.out.println("Chaine dont les quantites demandées en entrée sont limitées (non achetable): " + c1.getCode());
			}
		}
		
		
		/*
		 * CHAINES INDEPENDANTES
		 */
		// Rappel: Les elements en entrées des chaines_independantes sont tous achetable
		for (ChaineDeProduction c : chaines_independantes) {
			this.majEntree(c, elements);
			this.majSortie(c, elements);
			System.out.println(c.getCode() + " mettra " + c.getTemps() + "h pour produire les élements suivants:");
			for (Element e : c.getSortie().keySet()) {
				System.out.println("\t > " + c.getSortie().get(e) + " " + e.getNom());
			}
		}

		
		
		/*
		 * CHAINES DONT LES QUANTITES SONT LIMITEES MAIS NE SONT PAS EN CONCURRENCE
		 */
		//Pour les chaines dont les quantites demandées en entrée sont limitées (non achetable) mais ne se disputent pas les memes elements avec les autres
		//On récupère les chaines dont elle dépend
		for (ChaineDeProduction c : chaines) {
			for (ChaineDeProduction chaine_pere : chaines_entrees_limites_non_concurrence) {
				for(Element elEntree : chaine_pere.getEntree().keySet()) {
					for (Element elSorti: c.getSortie().keySet()) {
						if ((elEntree.getCode() == elSorti.getCode()) && elEntree.getAchat()==0) {
							chaines_entrees_limites_non_concurrence_dependances_fils.add(c);
							chaines_entrees_limites_non_concurrence_peres.add(chaine_pere);
						}
					}
				}
			}
		}
		
		//On remplie ensuite la chaine (graphe) chaines_limites_non_concurrence_peres_fils
		for (ChaineDeProduction c: chaines_entrees_limites_non_concurrence) {
			ArrayList<ChaineDeProduction> a = new ArrayList<>();
			a = this.get_dependances_chaines_concurrence(chaines_entrees_limites_non_concurrence_dependances_fils, 
															chaines_entrees_limites_non_concurrence_peres, c.getCode());
			chaines_entrees_limites_non_concurrence_peres_fils.put(c, a);
		}		

		
		//Affiche les chaines dont dependent les chaines dont les elements en entrees sont non achetable mais aucune concurrence
		for (ChaineDeProduction c : chaines_entrees_limites_non_concurrence_peres_fils.keySet()) {
			System.out.println("Chaines entrees limités sans concurrence " + c.getCode());
			if (chaines_entrees_limites_non_concurrence_peres_fils.get(c).isEmpty()) {
				System.out.println("La chaine dont dépend " + c.getCode() + " n'a pas été sélectionner");
				for (Element eChaine: c.getEntree().keySet()) {
					for (Element eStock : elements) {
						//Element non achetable en quantité insuffisante
						if (eChaine.getCode() == eStock.getCode() && eStock.getAchat() == 0) {
							if ((eStock.getQuantite() < c.getEntree().get(eChaine))) {
								System.out.println(c.getCode() + " ne dispose pas d'élement suffisant pour produire");
								System.out.println("Element concerné: " + eChaine.getCode() + ":" + eChaine.getNom());
							}
							else {
								System.out.println("OK");
							}
						}
						//Element achetable en quantité insuffisante
						else if(eChaine.getCode() == eStock.getCode()){
							if ((eStock.getQuantite() < c.getEntree().get(eChaine))) {
								System.out.println(c.getCode() + " ne dispose pas d'élement suffisant pour produire");
								System.out.println("\tElement concerné: " + eChaine.getCode() + ":" + eChaine.getNom());
							}
						}
					}
				}
				
			}
			else {
				for (ChaineDeProduction c1 : chaines_entrees_limites_non_concurrence_peres_fils.get(c)) {
					System.out.println("Dependances: " + c1.getCode());
				}
			}
		}
		
		/*
		 * CHAINES DONT LES QUANTITES SONT LIMITEES ET SONT EN CONCURRENCE
		 */
		//Pour les chaines dont les quantites demandées en entrée sont limitées (non achetable) et se disputent les memes élements
		//On recupère les chaines dont elle dépend
		for (ChaineDeProduction c : chaines) {
			for (ChaineDeProduction c1 : chaine_disputant_meme_entree) {
				for(Element elEntree : c1.getEntree().keySet()) {
					for (Element elSorti: c.getSortie().keySet()) {
						if (elEntree.getCode() == elSorti.getCode() && elEntree.getAchat()==0) {
							//System.out.println("La chaine " + c1.getCode() + " depend de " + c.getCode());
							chaines_entrees_limites_concurrence_dependances_fils.add(c);
							chaines_entrees_limites_concurrence_peres.add(c1);
						}
					}
				}
			}
		}
		
		//On remplie ensuite la chaine (graphe) chaines_limites_concurrence_peres_fils
		for (ChaineDeProduction c: chaine_disputant_meme_entree) {
			ArrayList<ChaineDeProduction> a = new ArrayList<>();
			a = this.get_dependances_chaines_concurrence(chaines_entrees_limites_concurrence_dependances_fils, 
															chaines_entrees_limites_concurrence_peres, c.getCode());
			chaines_entrees_limites_concurrence_peres_fils.put(c, a);
		}		
		
		for (ChaineDeProduction c : chaines_entrees_limites_concurrence_peres_fils.keySet()) {
			//Si les chaines dont dépend la chaine qui demande des entrées non achetable est vide
			if (chaines_entrees_limites_concurrence_peres_fils.get(c).isEmpty()) {
				System.out.println("La chaine dont dépend " + c.getCode() + " n'a pas été sélectionner");
				for (Element eChaine: c.getEntree().keySet()) {
					for (Element eStock : elements) {
						//Element non achetable en quantité insuffisante
						if (eChaine.getCode() == eStock.getCode() && eStock.getAchat() == 0) {
							if ((eStock.getQuantite() < c.getEntree().get(eChaine))) {
								System.out.println(c.getCode() + " ne dispose pas d'élement suffisant pour produire");
								System.out.println("\tElement concerné: " + eChaine.getCode() + ":" + eChaine.getNom());
							}
							else {
								System.out.println("OK");
							}
						}
						//Element achetable en quantité insuffisante
						else if(eChaine.getCode() == eStock.getCode()){
							if ((eStock.getQuantite() < c.getEntree().get(eChaine))) {
								System.out.println(c.getCode() + " ne dispose pas d'élement suffisant pour produire");
								System.out.println("Element concerné: " + eChaine.getCode() + ":" + eChaine.getNom());
							}
						}
					}
				}
			}
			else {
				for (ChaineDeProduction c1 : chaines_entrees_limites_concurrence_peres_fils.get(c)) {
					System.out.println("Dependances: " + c1.getCode());
				}
			}
		}
		
	}
	

	public String getChaineIndependant(String str) {
		return str;
	}
	
	private ArrayList<ChaineDeProduction> get_dependances_chaines_concurrence(ArrayList<ChaineDeProduction> list_dependances,
			ArrayList<ChaineDeProduction> list_peres, String code) {
		int i = 0;
		ArrayList<ChaineDeProduction> a = new ArrayList<>();
		
		for (ChaineDeProduction c : list_dependances) {
			if (list_peres.get(i).getCode() == code) {
				a.add(c);
			}
			i++;
		}
		return a;
	}
	
	
	private ChaineDeProduction rechercheChaine(ArrayList<ChaineDeProduction> c, String code) {
		for(ChaineDeProduction c1 : c) {
			if(c1.getCode() == code) {
				return c1;
			}
		}
		return null;
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
	
	private void majEntree(ChaineDeProduction c, ArrayList<Element> elements) {
		for (Element elEntree: c.getEntree().keySet()) {
			for (Element elStock : elements) {
				if (elEntree.getCode() == elStock.getCode()) {
					double newQuantite = elStock.getQuantite() - (c.getEntree().get(elEntree));
					elStock.setQuantite(newQuantite);
				}
			}
		}
	}
	
	private void majSortie(ChaineDeProduction c, ArrayList<Element> elements) {
		for (Element elSorti : c.getSortie().keySet()) {
			for (Element elStock: elements) {
				if (elSorti.getCode() == elStock.getCode()) {
					elStock.setQuantite(elStock.getQuantite() + (c.getSortie().get(elSorti)));
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
