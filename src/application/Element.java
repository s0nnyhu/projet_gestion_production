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
	
	public Element(String code, String nom, double quantite, String unite, double achat, double vente) {
		super();
		this.code = new SimpleStringProperty(code);
		this.nom = new SimpleStringProperty(nom);
		this.quantite = new SimpleDoubleProperty(quantite);
		this.unite = new SimpleStringProperty(unite);
		this.achat = new SimpleDoubleProperty(achat);
		this.vente = new SimpleDoubleProperty(vente);
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
	public double getQuantite() {
		return quantite.getValue();
	}
	public void setQuantite(double quantite) {
		this.quantite.set(quantite);
	}
	public String getUnite() {
		return unite.getValue();
	}
	public void setUnite(String unite) {
		this.unite.set(unite);
	}
	public double getAchat() {
		return achat.getValue();
	}
	public void setAchat(double achat) {
		this.achat.set(achat);
	}
	public double getVente() {
		return vente.getValue();
	}
	public void setVente(double vente) {
		this.vente.set(vente);
	}

	@Override
	public String toString() {
		return "Element [code=" + code + ", nom=" + nom + ", quantite=" + quantite + ", unite=" + unite + ", achat="
				+ achat + ", vente=" + vente + "]";
	}
	
}
