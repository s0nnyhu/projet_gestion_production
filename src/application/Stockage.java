package application;

/**
 * @author Régis
 *
 */
public class Stockage {
	private String code;
	private String nom;
	private double capacite;
	private int quantiteDispo;
	
	
	/**
	 * Constructeur
	 * @param code
	 * @param nom
	 * @param capacite
	 * @param quantiteDispo
	 */
	public Stockage(String code, String nom, double capacite, int quantiteDispo) {
		this.code = code;
		this.nom = nom;
		this.capacite = capacite;
		this.quantiteDispo = quantiteDispo;
	}
	
	
	/**
	 * Constructeur
	 * @param stockage
	 */
	public Stockage(Stockage sto) {
		this(sto.getCode(), sto.getNom(), sto.getCapacite(), sto.getQuantiteDispo());
	}


	/**
	 * @return Le code d'un mode de stockage
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @param code: Le code d'un mode de stockage
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * @return Le nom d'un stockage
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom: Le nom d'un stockage
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * @return La capacité d'un stockage
	 */
	public double getCapacite() {
		return capacite;
	}
	
	/**
	 * @param capacite: La capacité d'un stockage
	 */
	public void setCapacite(double capacite) {
		this.capacite = capacite;
	}
	
	/**
	 * @return La quantité disponible dans un stockage
	 */
	public int getQuantiteDispo() {
		return quantiteDispo;
	}
	
	/**
	 * @param quantiteDispo
	 */
	public void setQuantiteDispo(int quantiteDispo) {
		this.quantiteDispo = quantiteDispo;
	}
	
	/**
	 * @param quantite
	 */
	public void reduireQuantite(int q) {
		this.setQuantiteDispo(quantiteDispo-q);
	}


	@Override
	public String toString() {
		return "Stockage [code=" + code + ", nom=" + nom + ", capacite=" + capacite
				+ ", quantiteDispo=" + quantiteDispo + "]";
	}
	
	

}
