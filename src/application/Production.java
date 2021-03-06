package application;

import java.util.Comparator;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Production{
	private StringProperty nom;
	private ChaineDeProduction chaine;
	private DoubleProperty coutVente;
	private DoubleProperty efficacite;
	private IntegerProperty temps;
	private int demande;
	private StringProperty satisDemande;
	
	/**
	 * Constructeur
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

	/**
	 * Constructeur
	 * @param chaine
	 * @param coutVente
	 * @param efficacite
	 * @param temps
	 */
	public Production(ChaineDeProduction chaine, double coutVente, double efficacite, int temps) {
		super();
		this.nom = new SimpleStringProperty(chaine.getCode()+" : "+chaine.getNom());
		this.chaine = chaine;
		this.coutVente = new SimpleDoubleProperty(coutVente);
		this.efficacite = new SimpleDoubleProperty(efficacite);
		this.temps = new SimpleIntegerProperty(temps);
		//Calcul de la valeur de la demande totale de la chaine de production
		for(Map.Entry<Element, Double> e : chaine.getSortie().entrySet()) {
			this.demande += e.getKey().getDemande();
		}
		this.satisDemande  = new SimpleStringProperty();
	}
	
	/**
	 * @return La demande d'une chaine de production
	 */
	public double getDemande() {
		return demande;
	}

	/**
	 * @return La chaine correspondant à la production
	 */
	public ChaineDeProduction getChaine() {
		return chaine;
	}

	/**
	 * @param chaine: La chaine correspondant à la production
	 */
	public void setChaine(ChaineDeProduction chaine) {
		this.chaine = chaine;
	}


	/**
	 * @return Le cout de vente d'une production
	 */
	public double getCoutVente() {
		return coutVente.getValue();
	}

	/**
	 * @param coutVente: Le cout de vente d'une production
	 */
	public void setCoutVente(double coutVente) {
		this.coutVente.set(coutVente);
	}
	
	/**
	 * @return Le nom d'une production (qui est une chaine)
	 */
	public String getNom() {
		return nom.getValue();
	}

	/**
	 * @param nom: Le nom d'une production (qui est une chaine)
	 */
	public void setnom(String nom) {
		this.nom.set(nom);
	}

	/**
	 * @return L'éfficacité d'une production
	 */
	public double getEfficacite() {
		return efficacite.getValue();
	}

	/**
	 * @param efficacite: L'efficacité d'une production
	 */
	public void setEfficacite(double efficacite) {
		this.efficacite.set(efficacite);
	}

	/**
	 * @return Le temps d'une production
	 */
	public IntegerProperty getTemps() {
		return temps;
	}

	/**
	 * @param temps: Le temps d'une production
	 */
	public void setTemps(int temps) {
		this.temps.set(temps);
	}
	
	/**
	 * @param satisDemande: La satisfaction de la demande (String)
	 */
	public void setSatisDemande(String satisDemande) {
		this.satisDemande.set(satisDemande);
	}

	/**
	 * @return La satisfaction de la demande
	 */
	public String getSatisDemande() {
		return satisDemande.getValue();
	}

	//Comparateur de Production en fonction du temps
	public class ComparateurTempsProd implements Comparator<Production>{	
		@Override
		public int compare(Production p1, Production p2) {
			return p1.getTemps().getValue().compareTo(p2.getTemps().getValue());
		}
		
	}
	
}
