package application;

public class Element {
	private String code;
	private String nom;
	private double quantite;
	private String unite;
	private double achat;
	private double vente;
	
	public Element(String code, String nom, double quantite, String unite, double achat, double vente) {
		super();
		this.code = code;
		this.nom = nom;
		this.quantite = quantite;
		this.unite = unite;
		this.achat = achat;
		this.vente = vente;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	public double getQuantite() {
		return quantite;
	}
	public void setQuantite(double quantite) {
		this.quantite = quantite;
	}
	public String getUnite() {
		return unite;
	}
	public void setUnite(String unite) {
		this.unite = unite;
	}
	public double getAchat() {
		return achat;
	}
	public void setAchat(double achat) {
		this.achat = achat;
	}
	public double getVente() {
		return vente;
	}
	public void setVente(double vente) {
		this.vente = vente;
	}

	@Override
	public String toString() {
		return "Element [code=" + code + ", nom=" + nom + ", quantite=" + quantite + ", unite=" + unite + ", achat="
				+ achat + ", vente=" + vente + "]";
	}
	
}
