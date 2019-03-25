package Vues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import application.CalculesActivitesTempsChaines;
import application.ChaineDeProduction;
import application.Element;
import application.Production;
import application.Stockage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

public class WindowSimulationAvecTempsResController {
	
	@FXML
	private TableView<Production> tabSimulationProd;
	
    @FXML
    private TableColumn<Production, String> colChaine;

    @FXML
    private TableColumn<Production, Double> colCoutVente;

    @FXML
    private TableColumn<Production, Double> colEfficacite;

    @FXML
    private TableColumn<Production, String> colDemande;
    
    @FXML
    private TableColumn<Production, String> colSatisfaction;
    
    @FXML
    private TableColumn<ChaineDeProduction, String> tCode;

    @FXML
    private TableColumn<ChaineDeProduction, String> tNom;

    @FXML
    private TableColumn<ChaineDeProduction, String> tEntree;

    @FXML
    private TableColumn<ChaineDeProduction, String> tSortie;

    @FXML
    private TableColumn<ChaineDeProduction, Integer> tTemps;

    @FXML
    private TableColumn<ChaineDeProduction, String> tSDemande;

    @FXML
    private Button retour;

    @FXML
    private VBox vboxRes;
    
    @FXML
    private TableView<Element> tabNewStock;

    @FXML
    private TableColumn<Element, String> colCode;

    @FXML
    private TableColumn<Element, String> colNom;

    @FXML
    private TableColumn<Element, Double> colQuantite;

    @FXML
    private TableColumn<Element, String> colUnite;

    @FXML
    private TableColumn<Element, Double> colPA;

    @FXML
    private TableColumn<Element, Double> colPV;
  

    @FXML
    private TitledPane paneListeAchat;
	
    @FXML
    private TableView<Element> tableauAchats;

    @FXML
    private TableColumn<Element, String> codeAchat;

    @FXML
    private TableColumn<Element, String> nomAchat;

    @FXML
    private TableColumn<Element, Double> quantiteAchat;
        
    @FXML
    private TextArea txtProdImpossible;
    
	protected ArrayList<Element> elements;
	
	protected ArrayList<ChaineDeProduction> chaines;
	
	protected ArrayList<Stockage> stockages;
	
    
    /**
     * Permet de charger le tableau Production
     * @param ObservableList <Production> p
     */
    void chargerSimulationProduction(ObservableList <Production> p) {
		this.colChaine.setCellValueFactory(
                new PropertyValueFactory<Production, String>("nom"));
		this.colCoutVente.setCellValueFactory(
                new PropertyValueFactory<Production, Double>("coutVente"));
		this.colEfficacite.setCellValueFactory(
                new PropertyValueFactory<Production, Double>("efficacite"));
		this.colDemande.setCellValueFactory(
                new PropertyValueFactory<Production, String>("demande"));
		this.colSatisfaction.setCellValueFactory(
                new PropertyValueFactory<Production, String>("satisDemande"));
		this.tabSimulationProd.setItems(p);
    }
   

    /**
     * Permet de charger la bouvelle liste de stocks (tableau)
     * @param ObservableList <Element> e
     */
    void chargerTabNewStock(ObservableList <Element> e) {
    		this.colCode.setCellValueFactory(
                    new PropertyValueFactory<Element, String>("code"));
    		this.colNom.setCellValueFactory(
                    new PropertyValueFactory<Element, String>("nom"));
    		this.colQuantite.setCellValueFactory(
                    new PropertyValueFactory<Element, Double>("quantite"));
    		this.colUnite.setCellValueFactory(
                    new PropertyValueFactory<Element, String>("unite"));
    		this.colPA.setCellValueFactory(
                    new PropertyValueFactory<Element, Double>("achat"));
    		this.colPV.setCellValueFactory(
                    new PropertyValueFactory<Element, Double>("vente"));
    		this.tabNewStock.setItems(e);
    }
    

    /**
     * Permet de charger la liste d'achats (tableau)
     * @param ObservableList <Element> o
     */
    void chargerListeAchats(ObservableList <Element> o) {
		this.codeAchat.setCellValueFactory(
                new PropertyValueFactory<Element, String>("code"));
		this.nomAchat.setCellValueFactory(
                new PropertyValueFactory<Element, String>("nom"));
		this.quantiteAchat.setCellValueFactory(
                new PropertyValueFactory<Element, Double>("quantite"));
		this.tableauAchats.setItems(o);
    }
    /**
     * Permet le retour (bouton retour)
     * @param event
     */
    
    @FXML
    void retour(ActionEvent event) {
    	Stage stage = (Stage) retour.getScene().getWindow();
        stage.close();
    }

    /**
     * Permet d'initialiser les données de la fenetre
     * @param sto 
     * @param niveau
     */
    void initData(ArrayList<Element> el, HashMap<ChaineDeProduction, TextField> mapChaineNiveau, ArrayList<ChaineDeProduction> listChainesUsines, ArrayList<Stockage> sto) {
		/*
		 * Copie profonde pour évaluation de la liste d'achats et des nouveaux stocks
		 */
    	this.elements = new ArrayList<>();
		this.chaines = new ArrayList<>();
		this.stockages = new ArrayList<>();
		ArrayList<Element> listAchat = new ArrayList<>();
		ArrayList<Production> production = new ArrayList<>();
		Double niveau[] = new Double[mapChaineNiveau.values().size()];
		
		for (Element e : el) {
			this.elements.add(new Element(e));
		}

		for (Stockage s : sto) {
			this.stockages.add(new Stockage(s));
		}
		
		int i = 0;
    	Iterator it = mapChaineNiveau.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            ChaineDeProduction ch = (ChaineDeProduction) pair.getKey();
            
            this.chaines.add(new ChaineDeProduction((ChaineDeProduction)pair.getKey()));
            TextField f = (TextField) pair.getValue();
            niveau[i] = Double.parseDouble(f.getText());
            i++;
        }
        
        /*
         * Copie profond pour évaluation de le calcul du temps d'activités des chaines
         */
    	ArrayList<Element> tpsElement = new ArrayList<>();
		ArrayList<ChaineDeProduction> tpsChaines = new ArrayList<>();
		ArrayList<Stockage> tpsSto = new ArrayList<>();
		Double tpsNiveau[] = new Double[mapChaineNiveau.values().size()];
		
		for (Element e : el) {
			tpsElement.add(new Element(e));
		}

		for (Stockage s : sto) {
			tpsSto.add(new Stockage(s));
		}
		
		int j = 0;
    	Iterator tpsIt = mapChaineNiveau.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            ChaineDeProduction ch = (ChaineDeProduction) pair.getKey();
            
            tpsChaines.add(new ChaineDeProduction((ChaineDeProduction)pair.getKey()));
            TextField f = (TextField) pair.getValue();
            tpsNiveau[i] = Double.parseDouble(f.getText());
            j++;
        }
        
        
        /*
         * Evaluation de la liste d'achats, des nouveaux stocks et de l'efficacité
         */
    	CalculesActivitesTempsChaines calc = new CalculesActivitesTempsChaines();
    	calc.calcul(elements, chaines, niveau, listAchat, production);
    	ObservableList<Element> listAchats = FXCollections.observableArrayList(listAchat);
    	ObservableList <Element> oElement = FXCollections.observableList(this.elements);
    	ObservableList <Production> oProduction = FXCollections.observableList(production);
    	chargerTabNewStock(oElement);
    	chargerSimulationProduction(oProduction);
    	chargerListeAchats(listAchats);
    	
    	/*
    	 * Evaluation du temps d'activités des chaines.
    	 */
		
    	CalculesActivitesTempsChaines tpsCalc = new CalculesActivitesTempsChaines();
    	tpsCalc.calculTemps(this.elements, listChainesUsines, this.chaines, niveau);
    	Label chaineIndependants = new Label("Les chaines suivantes sont indépendantes et peuvent démarrer en parallèles");
    	chaineIndependants.setUnderline(true);
    	Label chaineSansConcurrence = new Label("Les chaines suivantes dépendantes d'autres chaines mais ne sont pas en concurrences\n");
    	chaineSansConcurrence.setUnderline(true);
    	Label chaineAvecConcurrence = new Label("Les chaines suivantes dépendantes d'autres chaines et sont en concurrences");
    	chaineAvecConcurrence.setUnderline(true);
    	Label tempsTotal = new Label(tpsCalc.getStrTpsTotal());
    	Label estPossible;
    	if (tpsCalc.getEstPossible() && tpsCalc.getTpsTotal() <= 60 && tpsCalc.getStockImpossible() == "") {
    		estPossible = new Label("La production est possible!");
    		estPossible.setStyle("-fx-background-color: green;");
    	}
    	else {
    		estPossible = new Label("La production ne pourra aboutir!");
    		estPossible.setStyle("-fx-background-color: red;");
    	}
    	
    	
    	tempsTotal.setStyle("-fx-font-size:14px;");
    	
    	this.vboxRes.getChildren().add(chaineIndependants);
    	this.vboxRes.getChildren().add(new Label(tpsCalc.getChainesIndependants()));
    	
    	this.vboxRes.getChildren().add(chaineSansConcurrence);
    	this.vboxRes.getChildren().add(new Label(tpsCalc.getChainesDependantsSansConcurrences()));
    	
    	this.vboxRes.getChildren().add(chaineAvecConcurrence);
    	this.vboxRes.getChildren().add(new Label(tpsCalc.getChainesDependantsAvecConcurrences()));
    	
    	this.vboxRes.getChildren().add(tempsTotal);
    
    	if (tpsCalc.getStockImpossible() != "") {
    		this.vboxRes.getChildren().add(new Label(tpsCalc.getStockImpossible()));
    	}
    	
    	this.vboxRes.getChildren().add(estPossible);
    	
    }
}
