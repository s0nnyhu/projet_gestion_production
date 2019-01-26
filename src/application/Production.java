package application;

public class Production {
	private ChaineDeProduction chaine;
	private double prixDAchats;
	private double prixDeVente;
	
	public Production(ChaineDeProduction chaine, double prixDAchats, double prixDeVente) {
		super();
		this.chaine = chaine;
		this.prixDAchats = prixDAchats;
		this.prixDeVente = prixDeVente;
	}
	
	public double getBenefice() {
		return 0;
	}
}
