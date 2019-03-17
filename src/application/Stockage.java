package application;

/**
 * @author Régis
 *
 */
public class Stockage {
	private String code;
	private String nom;
	private double capacite;
	private double quantiteDispo;
	
	
	/**
	 * @param code
	 * @param nom
	 * @param capacite
	 * @param quantiteDispo
	 */
	public Stockage(String code, String nom, double capacite, double quantiteDispo) {
		this.code = code;
		this.nom = nom;
		this.capacite = capacite;
		this.quantiteDispo = quantiteDispo;
	}
	
	
	/**
	 * @param sto
	 */
	public Stockage(Stockage sto) {
		this(sto.getCode(), sto.getNom(), sto.getCapacite(), sto.getQuantiteDispo());
	}


	/**
	 * @return
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * @return
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * @return
	 */
	public double getCapacite() {
		return capacite;
	}
	
	/**
	 * @param capacite
	 */
	public void setCapacite(double capacite) {
		this.capacite = capacite;
	}
	
	/**
	 * @return
	 */
	public double getQuantiteDispo() {
		return quantiteDispo;
	}
	
	/**
	 * @param quantiteDispo
	 */
	public void setQuantiteDispo(double quantiteDispo) {
		this.quantiteDispo = quantiteDispo;
	}


	@Override
	public String toString() {
		return "Stockage [code=" + code + ", nom=" + nom + ", capacite=" + capacite
				+ ", quantiteDispo=" + quantiteDispo + "]";
	}
	
	

}
