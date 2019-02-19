package application;

import java.util.HashMap;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

/**
 * @author Régis
 *
 */
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
	

	public CheckBox getCkBox() {
		return ckBox;
	}

	public void setCkBox(CheckBox ckBox) {
		this.ckBox = ckBox;
	}

	public ChaineDeProduction(ChaineDeProduction c) {
		this(c.getCode(), c.getNom(), c.getEntree(), c.getSortie(), c.getTemps());
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
	public String getSatisDemande() {
		return satisDemande.getValue();
	}

	/**
	 * @param satisDemande
	 */
	public void setSatisDemande(String satisDemande) {
		this.satisDemande.set(satisDemande);
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
		return strSorti.getValue();
	}
	/**
	 * @param strSorti
	 */
	public void setStrSorti(String strSorti) {
		this.strSorti.set(strSorti);
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
	

	/**
	 * @return
	 */
	public int getDemande() {
		return demande;
	}


	/**
	 * @return
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
