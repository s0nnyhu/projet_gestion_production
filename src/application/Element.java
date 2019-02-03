package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Element {
	private StringProperty code = new SimpleStringProperty();
	private StringProperty nom = new SimpleStringProperty();
	private DoubleProperty quantite = new SimpleDoubleProperty();
	private StringProperty unite = new SimpleStringProperty();
	private DoubleProperty achat = new SimpleDoubleProperty();
	private DoubleProperty vente = new SimpleDoubleProperty();
	
	/**
	 * @param code
	 * @param nom
	 * @param quantite
	 * @param unite
	 * @param achat
	 * @param vente
	 */
	public Element(String code, String nom, double quantite, String unite, double achat, double vente) {
		super();
		this.code = new SimpleStringProperty(code);
		this.nom = new SimpleStringProperty(nom);
		this.quantite = new SimpleDoubleProperty(quantite);
		this.unite = new SimpleStringProperty(unite);
		this.achat = new SimpleDoubleProperty(achat);
		this.vente = new SimpleDoubleProperty(vente);
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
	public double getQuantite() {
		return quantite.getValue();
	}
	/**
	 * @param quantite
	 */
	public void setQuantite(double quantite) {
		this.quantite.set(quantite);
	}
	/**
	 * @return
	 */
	public String getUnite() {
		return unite.getValue();
	}
	/**
	 * @param unite
	 */
	public void setUnite(String unite) {
		this.unite.set(unite);
	}
	/**
	 * @return
	 */
	public double getAchat() {
		return achat.getValue();
	}
	/**
	 * @param achat
	 */
	public void setAchat(double achat) {
		this.achat.set(achat);
	}
	/**
	 * @return
	 */
	public double getVente() {
		return vente.getValue();
	}
	/**
	 * @param vente
	 */
	public void setVente(double vente) {
		this.vente.set(vente);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Element [code=" + code + "]";
	}
	
}
