package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class InitialisationDonnees {
	private static ArrayList<Element> elements;
	private static ArrayList<ChaineDeProduction> chaines;

	private static final String ficElements = "../DonneesV1/FichiersV1/elements.csv";
	private static final String ficChaines = "../DonneesV1/FichiersV1/chaines.csv";
	
	/**
	 * 
	 */
	public static void initialiserElements() {
		InitialisationDonnees.setElements(new ArrayList<Element>());
		try {
			FileReader elem = new FileReader(ficElements);
			BufferedReader br = new BufferedReader(elem);
			String line = br.readLine(); //saut de la premiere ligne en effectuant une lecture
			while( (line = br.readLine() ) != null) {
				String[] fields = line.split(";");
				for(int i=0; i<fields.length; i++) {
					if(fields[i].equals("NA")) {
						fields[i] = "0";
					}
				}
				String code = fields[0];
				String nom = fields[1];
				double quantite = Double.parseDouble(fields[2]);
				String unite = fields[3];
				double achat = Double.parseDouble(fields[4]);
				double vente = Double.parseDouble(fields[5]);
				
				Element el = new Element(code, nom, quantite, unite, achat, vente);
				InitialisationDonnees.getElements().add(el);
			}
			br.close();
			elem.close();
		} catch (IOException e) {
			System.err.println("Erreur dans le nom du fichier...");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public static void initialiserChaines() {
		InitialisationDonnees.setChaines(new ArrayList<ChaineDeProduction>());
		try {
			FileReader ch = new FileReader(ficChaines);
			BufferedReader br = new BufferedReader(ch);
			String line = br.readLine(); //saut de la premiere ligne en effectuant une lerture
			while( (line = br.readLine() ) != null) {
				String[] fields = line.split(";");
				for(int i=0; i<fields.length; i++) {
					if(fields[i].equals("NA")) { //Gestion des champs NaN
						fields[i] = "0";
					}
				}
				String code = fields[0];
				String nom = fields[1];
				HashMap<Element, Double> entree = new HashMap<Element, Double>();
				String[] in = fields[2].replace(" ", "").split("\\),\\(");
				for(int i=0; i<in.length; i++) {
					String str[] = in[i].replace("(", "").replace(")", "").split(",");
					String codeElem = str[0];
					double quantiteElem = Double.parseDouble(str[1]);
					//Récupération des objets Elements pour remplir la HashMap "entree"
					for(Element elem : InitialisationDonnees.getElements()) {
						if(elem.getCode().equals(codeElem)) {
							entree.put(elem, quantiteElem);
						}
					}
				}
				
				HashMap<Element, Double> sortie = new HashMap<Element, Double>();
				String[] out = fields[3].replace(" ", "").split("\\),\\(");
				for(int i=0; i<out.length; i++) {
					String str[] = out[i].replace("(", "").replace(")", "").split(",");
					String codeElem = str[0];
					double quantiteElem = Double.parseDouble(str[1]);
					
					//Récupération des objets Elements pour remplir la HashMap "sortie" 
					for(Element elem : InitialisationDonnees.getElements()) {
						if(elem.getCode().equals(codeElem)) {
							sortie.put(elem, quantiteElem);
						}
					}
				}		
				
				ChaineDeProduction cp = new ChaineDeProduction(code, nom, entree, sortie);
				getChaines().add(cp);
			}
			br.close();
			ch.close();
		} catch (IOException e) {
			System.err.println("Erreur dans le nom du fichier...");
			e.printStackTrace();
		}
	}

	public static ArrayList<ChaineDeProduction> getChaines() {
		return chaines;
	}

	public static void setChaines(ArrayList<ChaineDeProduction> chaines) {
		InitialisationDonnees.chaines = chaines;
	}

	public static ArrayList<Element> getElements() {
		return elements;
	}

	public static void setElements(ArrayList<Element> elements) {
		InitialisationDonnees.elements = elements;
	}
}
