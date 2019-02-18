
package application;

import java.util.ArrayList;

public class CalculesActivitesTempsChaines {
	private ArrayList<ChaineDeProduction> listChaineImpossible;
	private int tempsChaine;
	private int tempsTotalChaines;
	final int tempsActivitesUsines = 60;
	
	public CalculesActivitesTempsChaines() {
		this.listChaineImpossible = new ArrayList<>();
		this.tempsChaine = 0;
		this.tempsTotalChaines = 0;
	}

	public void calcul(ArrayList<Element> elements, ArrayList<ChaineDeProduction> chaines, Double[] niveau) {
		boolean estPossible;
		ArrayList<ChaineDeProduction> listChaineProduisantElement = new ArrayList<>();
		int i = 0;
		
		for (ChaineDeProduction c : chaines) {
			//Mise à jour du temps de la chaine en fonction du niveau d'activité
			c.setTemps((int) (niveau[i]*c.getTemps()));
			
			//Mise à jour des stocks en entrée et sortie de la chaine en fonction du niveau d'activité
			for (Element elementChaine : c.getEntree().keySet()) {
				c.getEntree().put(elementChaine, (double) c.getEntree().get(elementChaine) * niveau[i]);
			}
			for (Element elementChaine : c.getSortie().keySet()) {
				c.getSortie().put(elementChaine, (double) c.getSortie().get(elementChaine) * niveau[i]);
			}

			for (Element elementChaine : c.getEntree().keySet()) {
				//Si la quantité en stock est insuffisante
				for (Element elementStocks: elements ) {
					if (elementChaine.getCode() == elementStocks.getCode()) {
						if (c.getEntree().get(elementChaine)> elementStocks.getQuantite()) {
							listChaineProduisantElement = rechercheChainesProduisantElement(elementChaine, chaines);
							/*if (listChaineProduisantElement.isEmpty()) {
								System.out.println("Production impossible,");
							}
							System.out.println("element demandé supérieurs à element en stocks, demandées:" + c.getEntree().get(elementChaine) + " - stocks:" + elementStocks.getQuantite());
						*/
						}
					}
				}
				//si l'élément ne se trouve pas en stocks, équivaut à dire qu'il est insuffisant
				listChaineProduisantElement = rechercheChainesProduisantElement(elementChaine, chaines);
			}
			i++;
		}

		
	}
		
	private ArrayList<ChaineDeProduction> rechercheChainesProduisantElement(Element element, ArrayList<ChaineDeProduction> listChaines) {
		ArrayList<ChaineDeProduction> listChaineProduisantElement = new ArrayList<>();
		for (ChaineDeProduction c: listChaines) {
			for (Element e : c.getSortie().keySet()) {
				if (e.getCode() == element.getCode()) {
					System.out.println("Trouvé, element en entrée de la chaine: " + e.getCode());
					listChaineProduisantElement.add(new ChaineDeProduction(c));
				}
			}
		}
		return listChaineProduisantElement;
	}
	
	private void majEntree(ChaineDeProduction c, ArrayList<Element> elements, Double[] niveau, int indice, ArrayList<ChaineDeProduction> listChaines) {
		for (Element elEntree: c.getEntree().keySet()) {
			for (Element elStock : elements) {
				if (elEntree.getCode() == elStock.getCode()) {
					if ((elStock.getQuantite()- (c.getEntree().get(elEntree)*niveau[indice])) < 0) {
						rechercheChainesProduisantElement(elEntree, listChaines);
						System.out.println("stock insuffisant, demandée par " + elEntree.getCode() +": " + c.getEntree().get(elEntree)*niveau[indice] + " - en stock: " + elStock.getQuantite());
					}
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
	
	
	public ArrayList<ChaineDeProduction> getListeProdImpossible() {
		return this.listChaineImpossible;
	}
}
