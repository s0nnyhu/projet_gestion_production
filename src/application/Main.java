package application;
	
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//ArrayList contenant les elements lus depuis le fichier csv
		ArrayList<Element> elements = new ArrayList<Element>();
		
		//ArrayList contenant les chaines lues depuis le fichier csv
		ArrayList<ChaineDeProduction> chaines = new ArrayList<ChaineDeProduction>();
		
		//Lecture du fichier contenant les éléments
		try {
			FileReader elem = new FileReader("../DonneesV1/FichiersV1/elements.csv");
			BufferedReader br = new BufferedReader(elem);
			String line = br.readLine(); //saut de la premiere ligne en effectuant une lerture
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
				elements.add(el);
			}
			br.close();
			elem.close();
		} catch (IOException e) {
			System.err.println("Erreur dans le nom du fichier...");
			e.printStackTrace();
		}
		
		//Lecture du fichier contenant les chaines de production
		try {
			FileReader ch = new FileReader("../DonneesV1/FichiersV1/chaines.csv");
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
					for(Element elem : elements) {
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
					for(Element elem : elements) {
						if(elem.getCode().equals(codeElem)) {
							sortie.put(elem, quantiteElem);
						}
					}
				}		
				
				ChaineDeProduction cp = new ChaineDeProduction(code, nom, entree, sortie);
				chaines.add(cp);
			}
			br.close();
			ch.close();
		} catch (IOException e) {
			System.err.println("Erreur dans le nom du fichier...");
			e.printStackTrace();
		}
		
		
		for(Element e : elements) {
			System.out.println(e);
		}
		for(ChaineDeProduction ch : chaines) {
			System.out.println(ch);
		}
		
		//launch(args);
	}
}
