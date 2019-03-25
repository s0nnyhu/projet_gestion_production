package application;

import java.util.HashMap;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class ChaineDeProduction implements Comparable<ChaineDeProduction> {
	private CheckBox ckBox;
	private StringProperty code = new SimpleStringProperty();
	private StringProperty nom = new SimpleStringProperty();
	private StringProperty strEntree = new SimpleStringProperty();
	private StringProperty strSorti = new SimpleStringProperty();
	private HashMap<Element,Double> entree;
	private HashMap<Element,Double> sortie;
	private int temps;
	private int demande;
	private StringProperty satisDemande = new SimpleStringProperty();
	
	
	/**
	 * @param code
	 * @param nom
	 * @param entree
	 * @param sortie
	 * @param temps
	 */
	public ChaineDeProduction(String code, String nom, HashMap<Element, Double> entree,
		HashMap<Element, Double> sortie, int temps) {
		super();
		this.ckBox = new CheckBox();
		this.code = new SimpleStringProperty(code);
		this.nom = new SimpleStringProperty(nom);
		this.entree = entree;
		this.sortie = sortie;
		this.temps = temps;
		String tmpStrEntree = "";
		String tmpStrSorti = "";
		for (Element e: entree.keySet()) {
			tmpStrEntree += "(" + e.getNom() +  ", " + entree.get(e) + ") ";
		}
		for (Element e: sortie.keySet()) {
			tmpStrSorti += "(" + e.getNom() +  ", " + sortie.get(e) + ") ";
			this.demande += e.getDemande();
		}
		this.strEntree = (new SimpleStringProperty(tmpStrEntree));
		this.strSorti = (new SimpleStringProperty(tmpStrSorti));
	}
	

	/**
	 * @return checkbox
	 */
	public CheckBox getCkBox() {
		return ckBox;
	}

	/**
	 * @param checkbox
	 */
	public void setCkBox(CheckBox ckBox) {
		this.ckBox = ckBox;
	}

	public ChaineDeProduction(ChaineDeProduction c) {
		this(c.getCode(), c.getNom(), c.getEntree(), c.getSortie(), c.getTemps());
	}
	/**
	 * @return Le code d'une chaine
	 */
	public String getCode() {
		return code.getValue();
	}
	/**
	 * @param code: Le code d'une chaine
	 */
	public void setCode(String code) {
		this.code.set(code);
	}
	/**
	 * @return Le nom d'une chaine
	 */
	public String getNom() {
		return nom.getValue();
	}
	/**
	 * @param nom: Le nom d'une chaine
	 */
	public void setNom(String nom) {
		this.nom.set(nom);
	}
	
	/**
	 * @return la satisfaction de la demande
	 */
	public String getSatisDemande() {
		return satisDemande.getValue();
	}

	/**
	 * @param La satisfaction de la demande (String)
	 */
	public void setSatisDemande(String satisDemande) {
		this.satisDemande.set(satisDemande);
	}

	/**
	 * @return Les élements en entrée d'une chaine en String
	 */
	public String getStrEntree() {
		return strEntree.getValue();
	}
	/**
	 * @param strEntree (Elements en entrée sous formes de String)
	 */
	public void setStrEntree(String strEntree) {
		this.strEntree.set(strEntree);
	}
	
	/**
	 * @return strSorti (Elements en sortie sous formes de String)
	 */
	public String getStrSorti() {
		return strSorti.getValue();
	}
	/**
	 * @param Les élements en sortie d'une chaine en String
	 */
	public void setStrSorti(String strSorti) {
		this.strSorti.set(strSorti);
	}
	
	/**
	 * @return Les élements en sortie d'une chaine, HashMap<Element, Double>
	 */
	public HashMap<Element, Double> getEntree() {
		return entree;
	}
	/**
	 * @param Les élements en entrée d'une chaine, HashMap<Element, Double>
	 */
	public void setEntree(HashMap<Element, Double> entree) {
		this.entree = entree;
	}
	
	/**
	 * @return Les élements en sortie d'une chaine, HashMap<Element, Double>
	 */
	public HashMap<Element, Double> getSortie() {
		return sortie;
	}
	/**
	 * @param sortie (HashMap<Element, Double> sortie)
	 */
	public void setSortie(HashMap<Element, Double> sortie) {
		this.sortie = sortie;
	}
	

	/**
	 * @return Demande de la chaine de production
	 */
	public int getDemande() {
		return demande;
	}


	/**
	 * @return Temps de la chaine de production
	 */
	public int getTemps() {
		return temps;
	}

	/**
	 * @param temps
	 */
	public void setTemps(int temps) {
		this.temps = temps;
	}

	@Override
	public String toString() {
		return "ChaineDeProduction [ckBox=" + ckBox + ", code=" + code + ", nom=" + nom + ", entree=" + entree + ", sortie=" + sortie + ", temps=" + temps
				+ ", demande=" + demande + ", satisDemande=" + satisDemande + "]";
	}
	
	@Override
    public int compareTo(ChaineDeProduction comparestu) {
        int compareTemps=((ChaineDeProduction)comparestu).getTemps();
        return this.getTemps()-compareTemps;
    }
	
	
	
}
