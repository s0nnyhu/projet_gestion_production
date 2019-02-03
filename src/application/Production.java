package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Production {
	private StringProperty nom;
	private ChaineDeProduction chaine;
	private DoubleProperty coutVente;
	private DoubleProperty efficacite;
	
	/**
	 * @param chaine
	 * @param coutVente
	 * @param efficacite
	 */
	public Production(ChaineDeProduction chaine, double coutVente, double efficacite) {
		super();
		this.nom = new SimpleStringProperty(chaine.getCode());
		this.chaine = chaine;
		this.coutVente = new SimpleDoubleProperty(coutVente);
		this.efficacite = new SimpleDoubleProperty(efficacite);
	}

	/**
	 * @return
	 */
	public ChaineDeProduction getChaine() {
		return chaine;
	}

	/**
	 * @param chaine
	 */
	public void setChaine(ChaineDeProduction chaine) {
		this.chaine = chaine;
	}


	/**
	 * @return
	 */
	public double getCoutVente() {
		return coutVente.getValue();
	}

	/**
	 * @param coutVente
	 */
	public void setCoutVente(double coutVente) {
		this.coutVente.set(coutVente);
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
	public void setnom(String nom) {
		this.nom.set(nom);
	}

	/**
	 * @return
	 */
	public double getEfficacite() {
		return efficacite.getValue();
	}

	/**
	 * @param efficacite
	 */
	public void setEfficacite(double efficacite) {
		this.efficacite.set(efficacite);
	}
}
