package application;

import java.util.HashMap;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ChaineDeProduction {
	private StringProperty code = new SimpleStringProperty();
	private StringProperty nom = new SimpleStringProperty();
	private StringProperty strEntree = new SimpleStringProperty();
	private StringProperty strSorti = new SimpleStringProperty();
	private HashMap<Element,Double> entree;
	private HashMap<Element,Double> sortie;
	
	/**
	 * @param code
	 * @param nom
	 * @param entree
	 * @param sortie
	 */
	public ChaineDeProduction(String code, String nom, HashMap<Element, Double> entree,
		HashMap<Element, Double> sortie) {
		super();
		this.code = new SimpleStringProperty(code);
		this.nom = new SimpleStringProperty(nom);
		this.entree = entree;
		this.sortie = sortie;
		String tmpStrEntree = "";
		String tmpStrSorti = "";
		for (Element e: entree.keySet()) {
			tmpStrEntree += "(" + e.getNom() +  ", " + entree.get(e) + ") ";
		}
		for (Element e: sortie.keySet()) {
			tmpStrSorti += "(" + e.getNom() +  ", " + entree.get(e) + ") ";
		}
		this.strEntree = (new SimpleStringProperty(tmpStrEntree));
		this.strSorti = (new SimpleStringProperty(tmpStrSorti));

	}
	
	public ChaineDeProduction(ChaineDeProduction c) {
		this(c.getCode(), c.getNom(), c.getEntree(), c.getSortie());
	}
	/**
	 * @return
	 */
	public String getCode() {
		return code.getValue();
	}
	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code.set(code);
	}
	/**
	 * @return
	 */
	public String getNom() {
		return nom.getValue();
	}
	/**
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom.set(nom);
	}
	
	/**
	 * @return
	 */
	public String getStrEntree() {
		return strEntree.getValue();
	}
	/**
	 * @param strEntree
	 */
	public void setStrEntree(String strEntree) {
		this.strEntree.set(strEntree);
	}
	
	/**
	 * @return
	 */
	public String getStrSorti() {
		return nom.getValue();
	}
	/**
	 * @param strSorti
	 */
	public void setStrSorti(String strSorti) {
		this.nom.set(strSorti);
	}
	
	/**
	 * @return
	 */
	public HashMap<Element, Double> getEntree() {
		return entree;
	}
	/**
	 * @param entree
	 */
	public void setEntree(HashMap<Element, Double> entree) {
		this.entree = entree;
	}
	/**
	 * @return
	 */
	public HashMap<Element, Double> getSortie() {
		return sortie;
	}
	/**
	 * @param sortie
	 */
	public void setSortie(HashMap<Element, Double> sortie) {
		this.sortie = sortie;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ChaineDeProduction [code=" + code + ", nom=" + nom + ", entree=" + entree + ", sortie=" + sortie + "]";
	}
	
	
	
	
}
