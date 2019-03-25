
package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CalculesActivitesTempsChaines {
	private boolean estPossible;
	private String stockImpossible;
	private String chainesIndependants;
	private String chainesDependantsSansConcurrences;
	private String chainesDependantsAvecConcurrences;
	private int tpsIndependant;
	private int tpsSansConcurrences;
	private int tpsAvecConcurrences;
	private int tpsTotal;
	

	/**
	 * Permet de calculer l'efficacité d'une chaine de production, en prenant en compte les élements à acheter et en mettant à jour le stock
	 * @param elements
	 * @param chaines
	 * @param niveau
	 * @param listAchat
	 * @param production
	 */
	public void calcul(ArrayList<Element> elements, ArrayList<ChaineDeProduction> chaines, Double[] niveau,ArrayList<Element> listAchat, ArrayList<Production> production) {
		boolean estPossible = false;
		int i = 0;
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
	
	/**
	 * Permet d'évaluer si la production est possible dans les temps, en prenant en compte les élements non achetables et le stockage
	 * @param elements
	 * @param list_chaines_usine
	 * @param chaines
	 * @param niveau
	 */
	public void calculTemps(ArrayList<Element> elements, ArrayList<ChaineDeProduction> list_chaines_usine, ArrayList<ChaineDeProduction> chaines, Double[] niveau) {
		this.estPossible = true;
		this.chainesIndependants = "";
		this.chainesDependantsSansConcurrences = "";
		this.chainesDependantsAvecConcurrences = "";
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
			this.stockImpossible = "";
			for(Element sortie : sorties.keySet()) { //pour chaque sortie
				double qteProduite = sortie.getQuantite(); //on recupère la quantité produite
				int nbCuves = (int) Math.ceil(qteProduite/sortie.getStockage().getCapacite()); //on calcule le nombre de cuves à utiliser
				if(nbCuves < sortie.getStockage().getQuantiteDispo()) {
					sortie.getStockage().reduireQuantite(nbCuves);
				}
				else {
					this.stockImpossible += "Il manque "+nbCuves+" recipent(s) pour stocker des "+sortie.getNom()+"\n";
				}
			}
			//Gestion du stockage des entree des chaines
			HashMap<Element, Double> entrees = c.getEntree();
			for(Element entree : entrees.keySet()) { //pour chaque sortie
				double qteProduite = entree.getQuantite(); //on recupère la quantité produite
				Stockage simulStockage = new Stockage(entree.getStockage());
				int nbCuves = (int) Math.ceil(qteProduite/simulStockage.getCapacite()); //on calcule le nombre de cuves à utiliser
				if(nbCuves < entree.getStockage().getQuantiteDispo()) {
					entree.getStockage().reduireQuantite(nbCuves);
					/*
					for(Stockage sto : stockages) {
						if(entree.getStockage().getCode().equals(sto.getCode())) {
							sto.reduireQuantite(nbCuves); // on met a jour les moyens de stockage dispo							
						}
					}
					*/
				}
				else {
					this.stockImpossible += "Il manque "+nbCuves+" recipent(s) pour stocker des "+entree.getNom()+" en entree de la chaine "+c.getNom()+"\n";
				}
			}
			i++;
		}
		
		//Pour chaque chaines dont les élements en entrées sont en quantités limités, on sépare ceux qui se disputent les meme chaines
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
		this.tpsIndependant = 0;
		for (ChaineDeProduction c : chaines_independantes) {
			this.majEntree(c, elements);
			this.majSortie(c, elements);
			this.chainesIndependants += c.getCode() + " mettra " + c.getTemps() + "h pour produire les élements suivants:\n";
			//Vu que les chaînes démarrent en parallèle, le temps mis seras celle la plus longue
			if (c.getTemps()>=this.tpsIndependant) {
				this.tpsIndependant = c.getTemps();
			}
			for (Element e : c.getSortie().keySet()) {
				this.chainesIndependants += "\t > " + c.getSortie().get(e) + " " + e.getNom() + "\n";
			}
		}
		this.chainesIndependants += "\nTemps mis: " + this.tpsIndependant + "h\n\n";
		

		
		
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

		this.tpsSansConcurrences = 0;
		//Affiche les chaines dont dependent les chaines dont les elements en entrees sont non achetable mais aucune concurrence
		for (ChaineDeProduction c : chaines_entrees_limites_non_concurrence_peres_fils.keySet()) {
			this.majEntree(c, elements);
			this.majSortie(c, elements);
			System.out.println("Chaines entrees limités sans concurrence " + c.getCode());
			if (chaines_entrees_limites_non_concurrence_peres_fils.get(c).isEmpty()) {
				this.chainesDependantsSansConcurrences += "La chaine dont dépend " + c.getCode() + " n'a pas été sélectionner\n";
				for (Element eChaine: c.getEntree().keySet()) {
					for (Element eStock : elements) {
						//Element non achetable en quantité insuffisante
						if (eChaine.getCode() == eStock.getCode() && eStock.getAchat() == 0) {
							if ((eStock.getQuantite() < c.getEntree().get(eChaine))) {
								this.estPossible = false;
								this.chainesDependantsSansConcurrences += c.getCode() + " ne dispose pas d'élement suffisant pour produire\n";
								this.chainesDependantsSansConcurrences += "\tElement concerné: " + eChaine.getCode() + ":" + eChaine.getNom() + "\n";
							}
							else {
								this.chainesDependantsSansConcurrences += "(Element en stock suffisant), " + c.getCode() + " mettra " + c.getTemps() + "h pour finir\n";
							}
						}
					}
				}
				
			}
			else {
				for (ChaineDeProduction c1 : chaines_entrees_limites_non_concurrence_peres_fils.get(c)) {
					this.chainesDependantsSansConcurrences += c.getCode() + " démarrera après " + c1.getCode() + " et mettra " + c.getTemps() + "h + " + c1.getTemps() + "h, soit " + (c1.getTemps() + c.getTemps()) + "h pour produire les élements suivants:\n";
					if (c1.getTemps() + c.getTemps() >= this.tpsSansConcurrences) {
						this.tpsSansConcurrences = c1.getTemps() + c.getTemps();
					}
					for (Element e : c.getSortie().keySet()) {
						//System.out.println("\t > " + c.getSortie().get(e) + " " + e.getNom());
						this.chainesDependantsSansConcurrences += "\t > " + c.getSortie().get(e) + " " + e.getNom() + "\n";
					}
				}
			}
		}
		this.chainesDependantsSansConcurrences += "Temps mis: " + this.tpsSansConcurrences + "h\n\n";
		
		/*
		 * CHAINES DONT LES QUANTITES SONT LIMITEES ET SONT EN CONCURRENCE
		 */
		//Pour les chaines dont les quantites demandées en entrée sont limitées (non achetable) et se disputent les memes élements
		//On recupère les chaines dont elle dépend
		//Nous aurons ainsi une suite de deux chaines, l'une avec les fils et l'autre avec les pères
		//Exemple:
		//chaines_entrees_limites_concurrence_dependances_fils : C1, C1, C3, C4,
		//chaines_entrees_limites_concurrence_peres			   : P1, P2, P2, P1,
		// Ainsi, comme l'arraylist est ordonné, on saura que P1 et P2 dépendent de C1 etc
		
		this.tpsAvecConcurrences = 0;
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
		
		Map<ChaineDeProduction, Integer> peres_tpsFils = new HashMap<>();
		for (ChaineDeProduction c : chaines_entrees_limites_concurrence_peres_fils.keySet()) {
			//Si les chaines dont dépend la chaine qui demande des entrées non achetable est vide
			if (chaines_entrees_limites_concurrence_peres_fils.get(c).isEmpty()) {
				for (Element eChaine: c.getEntree().keySet()) {
					for (Element eStock : elements) {
						//Element non achetable en quantité insuffisante
						if (eChaine.getCode() == eStock.getCode() && eStock.getAchat() == 0) {
							if ((eStock.getQuantite() < c.getEntree().get(eChaine))) {
								this.chainesDependantsAvecConcurrences += c.getCode() + " ne dispose pas d'élement suffisant pour produire";
								this.chainesDependantsAvecConcurrences += "\tElement concerné: " + eChaine.getCode() + ":" + eChaine.getNom();
								System.out.println(c.getCode() + " ne dispose pas d'élement suffisant pour produire");
								System.out.println("\tElement concerné: " + eChaine.getCode() + ":" + eChaine.getNom());
							}
							else {
								this.majEntree(c, elements);
								this.majSortie(c, elements);
								this.chainesDependantsAvecConcurrences += c.getCode() + " a besoin de " + eStock.getNom() + ":\n";
								this.chainesDependantsAvecConcurrences += "\t(Element en stock suffisant), " + c.getCode() + " mettra " + c.getTemps() + "h pour finir\n";
							}
						}
					}
				}
			}
			else {
				int tpsFils = 0;
				for (ChaineDeProduction c1 : chaines_entrees_limites_concurrence_peres_fils.get(c)) {
					tpsFils += c1.getTemps();
					
				}
				peres_tpsFils.put(c,  tpsFils);
			}
		}
		
		this.tpsAvecConcurrences = 0;
		Map<ChaineDeProduction, Integer> ChainesTrieesTempsAscendant = peres_tpsFils.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		for (ChaineDeProduction c2: ChainesTrieesTempsAscendant.keySet()) {
			//Le temps total sera donc le temps maximum
			this.majEntree(c2, elements);
			this.majSortie(c2, elements);
			if(c2.getTemps() + ChainesTrieesTempsAscendant.get(c2) > this.tpsAvecConcurrences) {
				this.tpsAvecConcurrences = c2.getTemps() + ChainesTrieesTempsAscendant.get(c2);
			}
			for (ChaineDeProduction c3 : chaines_entrees_limites_concurrence_peres_fils.keySet()) {
				if (c2.getCode() == c3.getCode()) {
					this.chainesDependantsAvecConcurrences += c3.getCode() + " mettra " + c3.getTemps() + "h et démarrera après:\n";
					//On récupère les dépendanes
					for (ChaineDeProduction c4 : chaines_entrees_limites_concurrence_peres_fils.get(c3)) {
						this.chainesDependantsAvecConcurrences += "\t>" + c4.getCode() + "\n";
						//On verifie si la quantité produite par le fils en sortie est suffisante
						for (Element eFils : c4.getSortie().keySet()) {
							for (Element ePeres: c2.getEntree().keySet()) {
								if (eFils.getCode() == ePeres.getCode()) {
									if (c4.getSortie().get(eFils) < c2.getEntree().get(ePeres)) {
										this.estPossible = false;
									}
								}
							}
						}
					}
					this.chainesDependantsAvecConcurrences += "\t pour produire les élements suivants: \n";
					for (Element e : c2.getSortie().keySet()) {
						this.chainesDependantsAvecConcurrences += "\t\t > " + c2.getSortie().get(e) + " " + e.getNom() + "\n";
					}
				}
			}
		}
		this.chainesDependantsAvecConcurrences += "Temps mis: " + this.tpsAvecConcurrences + "h\n\n\n";
	}
	
	
	/**
	 * @return temps total de production
	 */
	public int getTpsTotal() {
		this.tpsTotal = 0;
		if (this.tpsSansConcurrences >= this.tpsAvecConcurrences) {
			this.tpsTotal = this.tpsSansConcurrences;
		}
		else {
			this.tpsTotal = this.tpsAvecConcurrences;
		}
		if (this.tpsTotal <= this.tpsIndependant) {
			this.tpsTotal = this.tpsIndependant;
		}
		return tpsTotal;
	}
	
	
	/**
	 * @return temps total de production (String)
	 */
	public String getStrTpsTotal() {
		//Vu les démarrages en parallèles et la prises en compte du temps directements dans les dépendances,
		//Le temps total sera le temps maximal
		this.tpsTotal = 0;
		if (this.tpsSansConcurrences >= this.tpsAvecConcurrences) {
			this.tpsTotal = this.tpsSansConcurrences;
		}
		else {
			this.tpsTotal = this.tpsAvecConcurrences;
		}
		if (this.tpsTotal <= this.tpsIndependant) {
			this.tpsTotal = this.tpsIndependant;
		}
		return "Le temps total de production est de "+this.tpsTotal + "h\n";
	}
	
	
	/**
	 * @return possibilite de la production si les elements non achetables sont disponibles en quantité suffisante ou non
	 */
	public boolean getEstPossible() {
		return this.estPossible;
	}
	
	/**
	 * @return Détail du démarrage des chaines indépendantes
	 */
	public String getChainesIndependants() {
		return this.chainesIndependants;
	}
	
	/**
	 * @return Détail du démarrage et des dépendances des chaines qui ne se disputent pas les memes elements mais sont dépendants.
	 */
	public String getChainesDependantsSansConcurrences() {
		return this.chainesDependantsSansConcurrences;
	}
	
	/**
	 * @return Détail du démarrage et des dépendances des chaines qui se disputent les memes elements
	 */
	public String getChainesDependantsAvecConcurrences() {
		return this.chainesDependantsAvecConcurrences;
	}
	
	/**
	 * @param list_dependances
	 * @param list_peres
	 * @param code d'une chaine
	 * @return Retourne une arraylist de chaines correspondant aux dépendances d'une chaine
	 */
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
	
	
	/**
	 * @param chaine
	 * @param elements
	 * @param niveau
	 * @param indice
	 */
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
	
	/**
	 * @param chaine
	 * @param elements
	 * @param niveau
	 * @param indice
	 */
	private void majSortie(ChaineDeProduction c, ArrayList<Element> elements, Double[] niveau, int indice) {
		for (Element elSorti : c.getSortie().keySet()) {
			for (Element elStock: elements) {
				if (elSorti.getCode() == elStock.getCode()) {
					elStock.setQuantite(elStock.getQuantite() + (c.getSortie().get(elSorti)*niveau[indice]));
				}
			}
		}
	}
	
	/**
	 * @param chaine
	 * @param elements
	 */
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
	
	/**
	 * @param chaine
	 * @param elements
	 */
	private void majSortie(ChaineDeProduction c, ArrayList<Element> elements) {
		for (Element elSorti : c.getSortie().keySet()) {
			for (Element elStock: elements) {
				if (elSorti.getCode() == elStock.getCode()) {
					elStock.setQuantite(elStock.getQuantite() + (c.getSortie().get(elSorti)));
				}
			}
		}
	}
	
	/**
	 * @param chaine
	 * @param elements
	 * @return cout de vente
	 */
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
	
	/**
	 * @return La liste des stockages impossible (en forme de String)
	 */
	public String getStockImpossible() {
		return this.stockImpossible;
	}
	
}
