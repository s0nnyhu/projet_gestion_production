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
	
	/**
	 * @return Elements
	 */
	public ArrayList<Element> getElements() {
		return elements;
	}
	/**
	 * @param elements
	 */
	public void setElements(ArrayList<Element> elements) {
		this.elements = elements;
	}
	public boolean getLoaded() {
		return this.loaded;
	}
	/**
	 *Permet de charger les donnees de l'usine 
	 */
	public abstract void chargerDonnees();
	/**
	 * Permet de charger les donnees de l'usine 
	 * @param path_elements
	 * @param path_chaines
	 */
	public abstract void chargerDonnees(String path_elements, String path_chaines);
	/**
	 * Permet de charger les donnees de l'usine 
	 * @param path_elements
	 * @param path_chaines
	 * @param path_stockage
	 */
	public abstract void chargerDonnees(String path_elements, String path_chaines, String path_stockage);
}
