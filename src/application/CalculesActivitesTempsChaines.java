
package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class CalculesActivitesTempsChaines {
	private int tempsChaineIndependante;
	private int tempsChaineDependante;
	private ArrayList<ChaineDeProduction> listChaineImpossible;
	final int tempsActivitesUsines = 60;
	
	private ArrayList<ChaineDeProduction> nouvelleListProduction;
	
	private ArrayList<ChaineDeProduction> listChaineProduisantElement;
	//Liste des chaines dont les quantités demandées en entrée dépendent d'autres chaines temporaire
	ArrayList<ChaineDeProduction> listChaineDependantTmp;
	//Liste des chaines dont les quantités demandées en entrée dépendent d'autres chaines
	ArrayList<ChaineDeProduction> listChaineDependant;
	//Liste des chaines dont les quantités demandées en entrée sont disponibles en stocks temporaire
	ArrayList<ChaineDeProduction> listChaineIndependantTmp;
	//Liste des chaines dont les quantités demandées en entrée sont disponibles en stocks
	ArrayList<ChaineDeProduction> listChaineIndependant;
	
	public CalculesActivitesTempsChaines() {
		this.tempsChaineDependante = 0;
		this.tempsChaineIndependante = 0;
		this.listChaineImpossible = new ArrayList<>();
		this.listChaineProduisantElement = new ArrayList<>();
		this.listChaineDependantTmp = new ArrayList<>();
		this.listChaineIndependantTmp = new ArrayList<>();
		this.listChaineDependant = new ArrayList<>();
		this.listChaineIndependant = new ArrayList<>();
		this.nouvelleListProduction = new ArrayList<>();
	}
	
	/*
	 * rajouter un champs de priorité
	 * séquencement
	 * 
	 */
	public void calcul(ArrayList<Element> elements, ArrayList<ChaineDeProduction> chaines,  Double[] niveau) {
		boolean stockNegatif;
		int i = 0;
		for (ChaineDeProduction c : chaines) {
			 stockNegatif = false;
			//Mise à jour du temps de la chaine en fonction du niveau d'activité
			c.setTemps((int) (niveau[i]*c.getTemps()));
			
			//Mise à jour des stocks en entrée et sortie de la chaine en fonction du niveau d'activité
			for (Element elementChaine : c.getEntree().keySet()) {
				c.getEntree().put(elementChaine, (double) c.getEntree().get(elementChaine) * niveau[i]);
			}
			for (Element elementChaine : c.getSortie().keySet()) {
				c.getSortie().put(elementChaine, (double) c.getSortie().get(elementChaine) * niveau[i]);
			}
			
			//Pour chaque élement en entrée d'une chaine
			for (Element elementChaine : c.getEntree().keySet()) {
				//Si la quantité en stock est insuffisante
				for (Element elementStocks: elements ) {
					if (elementChaine.getCode() == elementStocks.getCode()) {
						if (c.getEntree().get(elementChaine)> elementStocks.getQuantite()) {
							stockNegatif = true;
							rechercheChainesProduisantElementDansChainesUser(elementChaine, chaines);
						}
					}
				}
			}

			//Si la liste des élemenent dans listChaineProduisantElement est vide et que le stock n'est pas négatif alors les élements 
			//en entrée de la chaine sont en quantité suffisantes dans le stock, dans ce cas, on ajoute la chaine courante dans la 
			//liste des chaines independantes.
			if (listChaineProduisantElement.isEmpty() && stockNegatif == false) {
				//System.out.println("INDEPENDANT");
				//System.out.println(c.getCode());
				listChaineIndependantTmp.add(c);
			}
			//Si la liste de chaines qu'il a choisi ne produit aucun element demandé par une chaine et que le stock est insuffisant alors
			//Production impossible
			else if (listChaineProduisantElement.isEmpty() && stockNegatif == true) {
				//System.out.println("IMPOSSIBLE");
				//System.out.println(c.getCode());
				listChaineImpossible.add(c);
			}
			//Sinon on l'ajoute dans la liste des chaines dépendantes.
			else if (!listChaineProduisantElement.isEmpty() && stockNegatif == true){
				//System.out.println("DEPENDANT");
				//System.out.println(c.getCode());
				listChaineDependantTmp.add(c);
			}
			listChaineProduisantElement.clear();

			ChaineDeProduction newC = new ChaineDeProduction(c);
			//Calcul de la satisfaction de la demande
			double quantiteProduite = 0;
			for(double val : c.getSortie().values()) {
				quantiteProduite += val;
			}
			if(!listChaineImpossible.contains(c)) {
				if(c.getDemande() <= quantiteProduite) {
    				newC.setSatisDemande("Satisfaite");
    			}
    			else {
    				double percent = (quantiteProduite * 100)/c.getDemande();
    				newC.setSatisDemande(String.format("%.2f", percent)+"% satisfait(s)");	
    			}
			}
			else { //production impossible
				newC.setSatisDemande("0% satisfait");
			}
			this.nouvelleListProduction.add(newC);
			i++;
		}
		
		//Trie par temps croissant des chaines independantes
		
		Collections.sort(this.listChaineIndependantTmp);
		//On parcours chaque chaine independante triée et on les démarre
		for (ChaineDeProduction c : this.listChaineIndependantTmp) {
			for (Element elementEntree : c.getEntree().keySet()) {
				for (Element elementStocks: elements ) {
					if (elementEntree.getCode() == elementStocks.getCode()) {
						double newQuantite = elementStocks.getQuantite() - (c.getEntree().get(elementEntree));
						if (newQuantite < 0) {
							this.listChaineImpossible.add(c);
						}
						else {
							this.listChaineIndependant.add(c);
							majEntree(c, elements);
							majSortie(c, elements);
						}
					}
				}
			}
		}
		
		for (ChaineDeProduction cImpossible : this.listChaineImpossible) {
			for (ChaineDeProduction cNew : this.nouvelleListProduction) {
				if (cNew.getCode() == cImpossible.getCode()) {
					cNew.setSatisDemande("Non satisfaite");
				}
			}
		}

		
		for (ChaineDeProduction c: this.listChaineDependantTmp) {
			rechercherDependance(c, chaines);
		}
		
		//Trie par temps croissant des chaines dependantes
		Collections.sort(this.listChaineDependant);
		
		//On recupere le temps total sur la liste des chaines dependantes les uns des autres
		for (ChaineDeProduction c : listChaineDependant) {
			this.tempsChaineDependante += c.getTemps();
			majSortie(c, elements);
		}
		
		//On recupere le temps maximum de la liste des chaines independantes vu qu'elles démarrent en parallèle
		for (ChaineDeProduction c : listChaineIndependant) {
			if (this.tempsChaineIndependante <= c.getTemps()) {
				this.tempsChaineIndependante = c.getTemps();
			}
		}
		
	}


	public void calcul2(ArrayList<Element> elements, ArrayList<ChaineDeProduction> chaines,  Double[] niveau) {
		boolean achetable = true;
		boolean stockNegatif;
		int i = 0;
		for (ChaineDeProduction c : chaines) {
			 stockNegatif = false;
			//Mise à jour du temps de la chaine en fonction du niveau d'activité
			c.setTemps((int) (niveau[i]*c.getTemps()));
			
			//Mise à jour des stocks en entrée et sortie de la chaine en fonction du niveau d'activité
			for (Element elementChaine : c.getEntree().keySet()) {
				c.getEntree().put(elementChaine, (double) c.getEntree().get(elementChaine) * niveau[i]);
			}
			for (Element elementChaine : c.getSortie().keySet()) {
				c.getSortie().put(elementChaine, (double) c.getSortie().get(elementChaine) * niveau[i]);
			}
			
			//Pour chaque élement en entrée d'une chaine
			for (Element elementChaine : c.getEntree().keySet()) {
				//Si la quantité en stock est insuffisante
				for (Element elementStocks: elements ) {
					if (elementChaine.getCode() == elementStocks.getCode()) {
						if (c.getEntree().get(elementChaine)> elementStocks.getQuantite()) {
							stockNegatif = true;
							rechercheChainesProduisantElementDansChainesUser(elementChaine, chaines);
						}
					}
				}
				//On repertorie les �l�ments ayant un prix d'achat
				//et on determinera si la production est possible
				//m�me quand le stock est n�gatif gr�ce au bool�en "achetable"
				if(elementChaine.getAchat() == 0) {
					achetable = false;
				}
			}

			//Si la liste des élemenent dans listChaineProduisantElement est vide et que le stock n'est pas négatif alors les élements 
			//en entrée de la chaine sont en quantité suffisantes dans le stock, dans ce cas, on ajoute la chaine courante dans la 
			//liste des chaines independantes.
			if ( (listChaineProduisantElement.isEmpty() && stockNegatif == false) || achetable) {
				//System.out.println("INDEPENDANT");
				//System.out.println(c.getCode());
				System.out.println(achetable);
				listChaineIndependantTmp.add(c);
			}
			//Si la liste de chaines qu'il a choisi ne produit aucun element demandé par une chaine et que le stock est insuffisant alors
			//Production impossible
			else if (listChaineProduisantElement.isEmpty() && stockNegatif == true) {
				//System.out.println("IMPOSSIBLE");
				//System.out.println(c.getCode());
				listChaineImpossible.add(c);
			}
			//Sinon on l'ajoute dans la liste des chaines dépendantes.
			else if (!listChaineProduisantElement.isEmpty() && stockNegatif == true){
				//System.out.println("DEPENDANT");
				//System.out.println(c.getCode());
				listChaineDependantTmp.add(c);
			}
			listChaineProduisantElement.clear();

			ChaineDeProduction newC = new ChaineDeProduction(c);
			//Calcul de la satisfaction de la demande
			double quantiteProduite = 0;
			for(double val : c.getSortie().values()) {
				quantiteProduite += val;
			}
			if(!listChaineImpossible.contains(c)) {
				if(c.getDemande() <= quantiteProduite) {
    				newC.setSatisDemande("Satisfaite");
    			}
    			else {
    				double percent = (quantiteProduite * 100)/c.getDemande();
    				newC.setSatisDemande(String.format("%.2f", percent)+"% satisfait(s)");	
    			}
			}
			else { //production impossible
				newC.setSatisDemande("0% satisfait");
			}
			this.nouvelleListProduction.add(newC);
			i++;
		}
		
		//Trie par temps croissant des chaines independantes
		
		Collections.sort(this.listChaineIndependantTmp);
		//On parcours chaque chaine independante triée et on les démarre
		for (ChaineDeProduction c : this.listChaineIndependantTmp) {
			for (Element elementEntree : c.getEntree().keySet()) {
				for (Element elementStocks: elements ) {
					if (elementEntree.getCode() == elementStocks.getCode()) {
						double newQuantite = elementStocks.getQuantite() - (c.getEntree().get(elementEntree));
						if (newQuantite < 0 && !achetable) {
							this.listChaineImpossible.add(c);
						}
						else {
							this.listChaineIndependant.add(c);
							majEntree(c, elements);
							majSortie(c, elements);
						}
					}
				}
			}
		}
		
		for (ChaineDeProduction cImpossible : this.listChaineImpossible) {
			for (ChaineDeProduction cNew : this.nouvelleListProduction) {
				if (cNew.getCode() == cImpossible.getCode()) {
					cNew.setSatisDemande("Non satisfaite");
				}
			}
		}

		
		for (ChaineDeProduction c: this.listChaineDependantTmp) {
			rechercherDependance(c, chaines);
		}
		
		//Trie par temps croissant des chaines dependantes
		Collections.sort(this.listChaineDependant);
		
		//On recupere le temps total sur la liste des chaines dependantes les uns des autres
		for (ChaineDeProduction c : listChaineDependant) {
			this.tempsChaineDependante += c.getTemps();
			majSortie(c, elements);
		}
		
		//On recupere le temps maximum de la liste des chaines independantes vu qu'elles démarrent en parallèle
		for (ChaineDeProduction c : listChaineIndependant) {
			if (this.tempsChaineIndependante <= c.getTemps()) {
				this.tempsChaineIndependante = c.getTemps();
			}
		}
		
	}
	
	private void rechercheChainesProduisantElementDansChainesUser(Element element, ArrayList<ChaineDeProduction> listChaines) {
		for (ChaineDeProduction c: listChaines) {
			for (Element e : c.getSortie().keySet()) {
				if (e.getCode() == element.getCode()) {
					//System.out.println("Trouvé, element en entrée de la chaine: " + e.getCode());
					this.listChaineProduisantElement.add(new ChaineDeProduction(c));
				}
			}
		}
	}
	
	private void rechercherDependance(ChaineDeProduction c, ArrayList<ChaineDeProduction> listChaines) {
		for (ChaineDeProduction ch : listChaines) {
			for (Element elSortie : ch.getSortie().keySet()) {
				if (elSortie.getCode() == c.getCode()) {
					this.listChaineDependant.add(new ChaineDeProduction(ch));
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
	
	
	public ArrayList<ChaineDeProduction> getListeProdImpossible() {
		return this.listChaineImpossible;
	}
	
	public Set<ChaineDeProduction> getListChaineIndependante() {
		return new LinkedHashSet<ChaineDeProduction>(this.listChaineIndependant);
	}
	
	public Set<ChaineDeProduction> getListChaineDependante() {
		return new LinkedHashSet<ChaineDeProduction>(this.listChaineDependant);
	}
	
	public Set<ChaineDeProduction> getListChaineImpossible() {
		return new LinkedHashSet<ChaineDeProduction>(this.listChaineImpossible);
	}
	
	public int getTempsChaineIndependante() {
		return this.tempsChaineIndependante;
	}

	public int getTempsChaineDependante() {
		return this.tempsChaineDependante;
	}

	public ArrayList<ChaineDeProduction> getNouvelleListProduction() {
		return this.nouvelleListProduction;
	}

	public int getTempsActivitesUsines() {
		return this.tempsActivitesUsines;
	}
	
	public int getTempsMis() {
		if (this.tempsChaineDependante >= this.tempsChaineIndependante) {
			return this.tempsChaineDependante;
		}
		else {
			return this.tempsChaineIndependante;
		}
	}
}
