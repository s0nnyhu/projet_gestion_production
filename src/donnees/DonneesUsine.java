package donnees;

import java.util.ArrayList;

import application.ChaineDeProduction;
import application.Element;
import application.Stockage;

public abstract class DonneesUsine {
	protected ArrayList<Element> elements;
	protected ArrayList<ChaineDeProduction> chaines;
	protected ArrayList<Stockage> stockages;
	protected boolean loaded;
	
	public ArrayList<Element> getElements() {
		return elements;
	}
	public void setElements(ArrayList<Element> elements) {
		this.elements = elements;
	}
	public boolean getLoaded() {
		return this.loaded;
	}
	public abstract void chargerDonnees();
	public abstract void chargerDonnees(String path_elements, String path_chaines);
	public abstract void chargerDonnees(String path_elements, String path_chaines, String path_stockage);
}
