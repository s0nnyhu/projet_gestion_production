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
			tmpStrEntree += "(" + e.getCode() +  "," + entree.get(e) + ")";
		}
		for (Element e: sortie.keySet()) {
			tmpStrSorti += "(" + e.getCode() +  "," + entree.get(e) + ")";
		}
		this.strEntree = (new SimpleStringProperty(tmpStrEntree));
		this.strSorti = (new SimpleStringProperty(tmpStrSorti));


	}
	
	public String getCode() {
		return code.getValue();
	}
	public void setCode(String code) {
		this.code.set(code);
	}
	public String getNom() {
		return nom.getValue();
	}
	public void setNom(String nom) {
		this.nom.set(nom);
	}
	
	public String getStrEntree() {
		return strEntree.getValue();
	}
	public void setStrEntree(String strEntree) {
		this.strEntree.set(strEntree);
	}
	
	public String getStrSorti() {
		return nom.getValue();
	}
	public void setStrSorti(String strSorti) {
		this.nom.set(strSorti);
	}
	
	public HashMap<Element, Double> getEntree() {
		return entree;
	}
	public void setEntree(HashMap<Element, Double> entree) {
		this.entree = entree;
	}
	public HashMap<Element, Double> getSortie() {
		return sortie;
	}
	public void setSortie(HashMap<Element, Double> sortie) {
		this.sortie = sortie;
	}

	@Override
	public String toString() {
		return "ChaineDeProduction [code=" + code + ", nom=" + nom + ", entree=" + entree + ", sortie=" + sortie + "]";
	}
	
	
	
	
}
