package application;

import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Production {
	private StringProperty nom;
	private ChaineDeProduction chaine;
	private DoubleProperty coutVente;
	private DoubleProperty efficacite;
	private IntegerProperty temps;
	private int demande;
	private StringProperty satisDemande;
	
	/**
	 * @param chaine
	 * @param coutVente
	 * @param efficacite
	 */
	public Production(ChaineDeProduction chaine, double coutVente, double efficacite) {
		super();
		this.nom = new SimpleStringProperty(chaine.getCode()+" : "+chaine.getNom());
		this.chaine = chaine;
		this.coutVente = new SimpleDoubleProperty(coutVente);
		this.efficacite = new SimpleDoubleProperty(efficacite);
		this.temps = new SimpleIntegerProperty(chaine.getTemps());
		//Calcul de la valeur de la demande totale de la chaine de production
		for(Map.Entry<Element, Double> e : chaine.getSortie().entrySet()) {
			this.demande += e.getKey().getDemande();
		}
		this.satisDemande  = new SimpleStringProperty();
	}

	public double getDemande() {
		return demande;
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

	/**
	 * @return
	 */
	public IntegerProperty getTemps() {
		return temps;
	}

	/**
	 * @param temps
	 */
	public void setTemps(int temps) {
		this.temps.set(temps);
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
	public String getSatisDemande() {
		return satisDemande.getValue();
	}

}
