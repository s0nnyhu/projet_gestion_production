package Vues;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import application.CalculesActivitesChaines;
import application.CalculesActivitesDemandesChaines;
import application.CalculesActivitesTempsChaines;
import application.ChaineDeProduction;
import application.Element;
import application.Production;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

public class WindowSimulationAvecTempsResController {
	
	private String possibiliteProd;
	
	private ArrayList<Production> production = new ArrayList<Production>();
	@FXML
	private Button retour;	

	@FXML
	private Button export;	

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

    
    private ArrayList<Element> listAchat;    

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
	
    
    /**
     * @param event
     */
	@FXML
    void exporter(ActionEvent event) {
    	String message = "Les essais de production ont été exportés Nouveau_Stock.csv, Production.csv";
    	try {
    		FileWriter fw = new FileWriter(new File("../DonneesV2/Exports/Nouveau_Stock.csv"));
    		fw.write("Code;Nom;Quantite;unite;achat;vente;Demande");
            fw.write(System.lineSeparator());
    		for (Element e : this.elements) {
                fw.write(String.format("%s;%s;%s;%s;%s,%s,%s",e.getCode(), e.getNom(), e.getQuantite(), e.getUnite(), e.getAchat(), e.getVente(), e.getDemande()));
                fw.write(System.lineSeparator());
    		}
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    	try {
    		FileWriter fw = new FileWriter(new File("../DonneesV2/Exports/Production.csv"));
    		fw.write("Chaine;coutVente;Efficacite");
            fw.write(System.lineSeparator());
    		for (Production p : this.production) {
                fw.write(String.format("%s;%s;%s", p.getNom(), p.getCoutVente(), p.getEfficacite()));
                fw.write(System.lineSeparator());
    		}
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    	
    	if(!listAchat.isEmpty()) {
	    	try {
	    		FileWriter fw = new FileWriter("../DonneesV2/Exports/Liste_Achats.csv");
	    		fw.write("Code;Nom;Quantite");
	            fw.write(System.lineSeparator());
	    		for (Element e : this.listAchat) {
	                fw.write(String.format("%s;%s;%s", e.getCode(), e.getNom(), Math.abs(e.getQuantite())));
	                fw.write(System.lineSeparator());
	    		}
	            fw.close();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
    		message += ", Liste_Achats.csv";
    	}
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.getDialogPane().setPrefWidth(700);
    	alert.getDialogPane().setPrefWidth(400);
    	alert.setHeaderText("Exportation des essais de production");
    	alert.setContentText(message);

    	alert.showAndWait();
    	
    }

    /**
     * @param p
     */
    void chargerSimulationProduction(ObservableList <Production> p) {
		this.colChaine.setCellValueFactory(
                new PropertyValueFactory<Production, String>("nom"));
		this.colCoutVente.setCellValueFactory(
                new PropertyValueFactory<Production, Double>("coutVente"));
		this.colEfficacite.setCellValueFactory(
                new PropertyValueFactory<Production, Double>("efficacite"));
		this.colDemande.setCellValueFactory(
                new PropertyValueFactory<Production, String>("satisDemande"));
		this.tabSimulationProd.setItems(p);
    }
    
    /**
     * @param e
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
     * @param e
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
     * @param event
     */
    @FXML
    void retour(ActionEvent event) {
    	Stage stage = (Stage) retour.getScene().getWindow();
        stage.close();
    }

    /**
     * @param niveau
     */
    void initData(ArrayList<Element> el, HashMap<ChaineDeProduction, TextField> mapChaineNiveau) {
		this.elements = new ArrayList<>();
		this.chaines = new ArrayList<>();
		this.listAchat = new ArrayList<>();
		Double niveau[] = new Double[mapChaineNiveau.values().size()];
		
		for (Element e : el) {
			this.elements.add(new Element(e));
		}
    	
		int i = 0;
    	Iterator it = mapChaineNiveau.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            ChaineDeProduction ch = (ChaineDeProduction) pair.getKey();
            
            this.chaines.add(new ChaineDeProduction((ChaineDeProduction)pair.getKey()));
            TextField f = (TextField) pair.getValue();
            niveau[i] = Double.parseDouble(f.getText());
            //System.out.println(ch.getCode() + " " + niveau[i]);
            i++;
        }
		
    	CalculesActivitesTempsChaines calc = new CalculesActivitesTempsChaines();
    	calc.calcul(elements, chaines, niveau);
    	//this.possibiliteProd = calc.getListeProdImpossible();
    	ObservableList<Element> listAchats = FXCollections.observableArrayList(listAchat);
    	//Affichage ou non de la liste d'achats

    	ObservableList <Element> oElement = FXCollections.observableList(this.elements);
    	ObservableList <Production> oProduction = FXCollections.observableList(production);
    	chargerTabNewStock(oElement);
    	chargerSimulationProduction(oProduction);
    	
    }
}
