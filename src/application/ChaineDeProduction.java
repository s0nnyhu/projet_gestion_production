package application;

import java.util.HashMap;

public class ChaineDeProduction {
	private String code;
	private String nom;
	private HashMap<Element,Double> entree;
	private HashMap<Element,Double> sortie;
	
	public ChaineDeProduction(String code, String nom, HashMap<Element, Double> entree,
			HashMap<Element, Double> sortie) {
		super();
		this.code = code;
		this.nom = nom;
		this.entree = entree;
		this.sortie = sortie;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
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
