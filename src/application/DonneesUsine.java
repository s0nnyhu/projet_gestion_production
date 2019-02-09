package application;

import java.util.ArrayList;

public abstract class DonneesUsine {
	protected ArrayList<Element> elements;
	public ArrayList<Element> getElements() {
		return elements;
	}
	public void setElements(ArrayList<Element> elements) {
		this.elements = elements;
	}
	protected ArrayList<ChaineDeProduction> chaines;
	public abstract void chargerDonnees();
	public abstract void chargerDonnees(String path_elements, String path_chaines);
}
