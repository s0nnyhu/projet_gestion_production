package donnees;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import application.ChaineDeProduction;
import application.Element;

public class DonneesCSV extends GestionDonneesFichiers{
	
	boolean elementsLoaded;
	
	public DonneesCSV(String path_elements, String path_chaines) {
		this.elements = new ArrayList<>();
		this.chaines = new ArrayList<>();
		this.chargerDonnees(path_elements, path_chaines);
	}
	
	public ArrayList<Element> getElements() {
		return this.elements;
	}
	
	public ArrayList<ChaineDeProduction> getChaines() {
		return this.chaines;
	}
	
	@Override
	public void chargerDonnees(String path_elements, String path_chaines) {
		this.chargerElements(path_elements);
		this.chargerChaines(path_chaines);
	}
	
	private void chargerElements(String path_elements) {
		try {
			FileReader elem = new FileReader(path_elements);
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
				this.elements.add(el);
			}
			br.close();
			elem.close();
			this.elementsLoaded = true;
		} catch (IOException e) {
			System.err.println("Erreur dans le nom du fichier...");
			e.printStackTrace();
		}
	}

	private void chargerChaines(String path_chaines) {
		try {
			FileReader ch = new FileReader(path_chaines);
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
					for(Element elem : this.elements) {
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
					for(Element elem : this.elements) {
						if(elem.getCode().equals(codeElem)) {
							sortie.put(elem, quantiteElem);
						}
					}
				}		
				
				ChaineDeProduction cp = new ChaineDeProduction(code, nom, entree, sortie);
				this.chaines.add(cp);
			}
			br.close();
			ch.close();
			if(this.elementsLoaded) {
				this.loaded = true;
			}
		} catch (IOException e) {
			System.err.println("Erreur dans le nom du fichier...");
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void chargerDonnees() {
		
	}
}
