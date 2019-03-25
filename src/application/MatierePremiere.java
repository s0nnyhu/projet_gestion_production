package application;

public class MatierePremiere extends Element {

	/**
	 * Cosntructeur
	 * @param code
	 * @param nom
	 * @param quantite
	 * @param unite
	 * @param achat
	 * @param vente
	 * @param stockage
	 * @param demande
	 */
	public MatierePremiere(String code, String nom, double quantite, String unite, double achat, double vente, Stockage stockage, int demande) {
		super(code, nom, quantite, unite, achat, vente, stockage, demande);
	}

}
