package donnees;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import application.ChaineDeProduction;
import application.Element;
import application.Stockage;

public class DonneesLibraryCSV extends GestionDonneesFichiers{
	boolean elementsLoaded;
	boolean stockageLoaded;

	public DonneesLibraryCSV(String cheminElements, String cheminChaines) {
		// TODO Auto-generated constructor stub
		this.elements = new ArrayList<>();
		this.chaines = new ArrayList<>();
		this.chargerDonnees(cheminElements, cheminChaines);
	}

	public DonneesLibraryCSV(String cheminElements, String cheminChaines, String cheminStockages) {
		this.stockages = new ArrayList<>();
		this.elements = new ArrayList<>();
		this.chaines = new ArrayList<>();
		this.chargerDonnees(cheminElements, cheminChaines, cheminStockages);
	}

	public ArrayList<Element> getElements() {
		return this.elements;
	}
	
	public ArrayList<ChaineDeProduction> getChaines() {
		return this.chaines;
	}
	
	public ArrayList<Stockage> getStockages(){
		return this.stockages;
	}
	
	@Override
	public void chargerDonnees() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chargerDonnees(String path_elements, String path_chaines) {
		this.chargerElements(path_elements);
		this.chargerChaines(path_chaines);
	}
	
	@Override
	public void chargerDonnees(String path_elements, String path_chaines, String path_stockage) {
		this.chargerStockage(path_stockage);
		this.chargerElements(path_elements);
		this.chargerChaines(path_chaines);
	}
	
	private void chargerStockage(String path_stockage) {
		try {
			FileReader sto = new FileReader(path_stockage);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').withFirstRecordAsHeader().parse(sto);
			for (CSVRecord record : records) {
			    String code = record.get("Code");
			    String nom = record.get("Nom");
			    
		    	double capacite = 0.0;
			    if(record.get("Capacite") != null) {
			    	capacite = Double.parseDouble(record.get("Capacite"));
			    }
			    
			    double qteDispo = 0.0;
			    if(record.get("Quantite disponible") != null){
			    	qteDispo = Double.parseDouble(record.get("Quantite disponible"));
			    }
				
				Stockage st = new Stockage(code, nom, capacite, qteDispo);
				this.stockages.add(st);
			}
			sto.close();
			this.stockageLoaded = true;
		} catch (IOException e) {
			System.err.println("Erreur dans le nom du fichier stockages...");
			e.printStackTrace();
		}
	}
	
	private void chargerElements(String path_elements) {
		try {
			FileReader elem = new FileReader(path_elements);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withNullString("NA").withDelimiter(';').withFirstRecordAsHeader().parse(elem);
			for (CSVRecord record : records) {
			    String code = record.get("Code");
			    String nom = record.get("Nom");
			    
		    	double quantite = 0.0;
			    if(record.get("Quantite") != null) {
				    quantite = Double.parseDouble(record.get("Quantite"));
			    }
			    
			    String unite = record.get("unite");
			    
			    double achat = 0.0;
			    if(record.get("achat") != null) {
			    	achat = Double.parseDouble(record.get("achat"));
			    }
			    
			    String stockage = record.get("stockage");
			    if(this.stockageLoaded) {
			    	for(Stockage sto : this.stockages) {
			    		if(sto.getCode().equals(stockage)) {
			    			stockage = sto.getNom();
			    		}
			    	}
			    }
			    
			    double vente = 0.0;
			    if(record.get("vente") != null){
			    	vente = Double.parseDouble(record.get("vente"));
			    }
			    
			    int demande = Integer.parseInt(record.get("Demande"));
				
				Element el = new Element(code, nom, quantite, unite, achat, vente, stockage, demande);
				this.elements.add(el);
			}
			elem.close();
			this.elementsLoaded = true;
		} catch (IOException e) {
			System.err.println("Erreur dans le nom du fichier elements...");
			e.printStackTrace();
		}
	}

	private void chargerChaines(String path_chaines) {
		try {
			FileReader ch = new FileReader(path_chaines);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').withFirstRecordAsHeader().parse(ch);
			for (CSVRecord record : records) {
			    String code = record.get("Code");
			    String nom = record.get("Nom");
			    int temps = Integer.parseInt(record.get("Temps"));
				
			    HashMap<Element, Double> entree = new HashMap<Element, Double>();
				String[] in = record.get("Entree (code,qte)").replace(" ", "").split("\\),\\(");
				for(int i=0; i<in.length; i++) {
					String str[] = in[i].replace("(", "").replace(")", "").split(",");
					String codeElem = str[0];

					double quantiteElem = 0.0;
					if(str.length == 2) {
						quantiteElem = Double.parseDouble(str[1]);
					}
					
					//Recuperation des objets Elements pour remplir la HashMap "entree"
					for(Element elem : this.elements) {
						if(elem.getCode().equals(codeElem)) {
							entree.put(elem, quantiteElem);
						}
					}
				}
				
				HashMap<Element, Double> sortie = new HashMap<Element, Double>();
				String[] out = record.get("Sortie (code,qte)").replace(" ", "").split("\\),\\(");
				for(int i=0; i<out.length; i++) {
					String str[] = out[i].replace("(", "").replace(")", "").split(",");
					String codeElem = str[0];
					double quantiteElem = Double.parseDouble(str[1]);
					
					//Recuperation des objets Elements pour remplir la HashMap "sortie" 
					for(Element elem : this.elements) {
						if(elem.getCode().equals(codeElem)) {
							sortie.put(elem, quantiteElem);
						}
					}
				}		
					
				ChaineDeProduction cp = new ChaineDeProduction(code, nom, entree, sortie, temps);
				this.chaines.add(cp);
			}
			
			ch.close();			
			
			if(this.elementsLoaded) {
				this.loaded = true;
			}
			
		} catch (IOException e) {
			System.err.println("Erreur dans le nom du fichier...");
			e.printStackTrace();
		}
	}
}
