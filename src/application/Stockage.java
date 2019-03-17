package application;

/**
 * @author R�gis
 *
 */
public class Stockage {
	private String code;
	private String nom;
	private double capacite;
	private int quantiteDispo;
	
	
	/**
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
	 * @param q
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