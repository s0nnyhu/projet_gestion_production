package application;



import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Régis
 *
 */
public class Element {
	private StringProperty code = new SimpleStringProperty();
	private StringProperty nom = new SimpleStringProperty();
	private DoubleProperty quantite = new SimpleDoubleProperty();
	private StringProperty unite = new SimpleStringProperty();
	private DoubleProperty achat = new SimpleDoubleProperty();
	private DoubleProperty vente = new SimpleDoubleProperty();
	private Stockage stockage;
	private StringProperty nomStock = new SimpleStringProperty();
	private int demande;
	
	/**
	 * @param code
	 * @param nom
	 * @param quantite
	 * @param unite
	 * @param achat
	 * @param vente
	 */
	public Element(String code, String nom, double quantite, String unite, double achat, double vente, Stockage stockage, int demande) {
		super();
		this.code = new SimpleStringProperty(code);
		this.nom = new SimpleStringProperty(nom);
		this.quantite = new SimpleDoubleProperty(quantite);
		this.unite = new SimpleStringProperty(unite);
		this.achat = new SimpleDoubleProperty(achat);
		this.vente = new SimpleDoubleProperty(vente);
		this.stockage = stockage;
		this.nomStock = new SimpleStringProperty(stockage.getNom());
		this.demande = demande;
	}
	/**
	 * @param e
	 */
	public Element(Element e) {
		this(e.getCode(), e.getNom(), e.getQuantite(), e.getUnite(), e.getAchat(), e.getVente(), e.getStockage(), e.getDemande());
	}
	
	
	public String getNomStock() {
		return nomStock.getValue();
	}

	public void setNomStock(String nomStock) {
		this.nomStock.set(nomStock);
	}

	public Stockage getStockage() {
		return stockage;
	}

	public void setStockage(Stockage stockage) {
		this.stockage = stockage;
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
	
	/**
	 * @return
	 */
	public int getDemande() {
		return demande;
	}

	/**
	 * @param demande
	 */
	public void setDemande(int demande) {
		this.demande = demande;
	}

	@Override
	public String toString() {
		return "Element [code=" + code + ", nom=" + nom + ", quantite=" + quantite + ", unite=" + unite + ", achat="
				+ achat + ", vente=" + vente + ", stockage=" + stockage + ", demande=" + demande + "]";
	}
	
}
