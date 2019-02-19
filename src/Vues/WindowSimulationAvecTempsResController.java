package Vues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import application.CalculesActivitesTempsChaines;
import application.ChaineDeProduction;
import application.Element;
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
    private TableView<ChaineDeProduction> tabSimulationProd;

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
	
    
   

    /**
     * @param p
     */
    void chargerSimulationNouvelleChaine(ObservableList <ChaineDeProduction> c) {
		this.tCode.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("code"));
		this.tNom.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("nom"));
		this.tEntree.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("strEntree"));
		this.tSortie.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("strSorti"));
		this.tTemps.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, Integer>("temps"));
		this.tSDemande.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("satisDemande"));
		this.tabSimulationProd.setItems(c);
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
    /*void chargerListeAchats(ObservableList <Element> o) {
		this.codeAchat.setCellValueFactory(
                new PropertyValueFactory<Element, String>("code"));
		this.nomAchat.setCellValueFactory(
                new PropertyValueFactory<Element, String>("nom"));
		this.quantiteAchat.setCellValueFactory(
                new PropertyValueFactory<Element, Double>("quantite"));
		this.tableauAchats.setItems(o);
    }*/
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
    void initData(ArrayList<Element> el, HashMap<ChaineDeProduction, TextField> mapChaineNiveau, ArrayList<ChaineDeProduction> listChainesUsines) {
		this.elements = new ArrayList<>();
		this.chaines = new ArrayList<>();

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
    	Set<ChaineDeProduction> impossible = new LinkedHashSet<ChaineDeProduction> (calc.getListChaineImpossible());
    	Set<ChaineDeProduction> dependante = new LinkedHashSet<ChaineDeProduction> (calc.getListChaineDependante());
    	Set<ChaineDeProduction> independante = new LinkedHashSet<ChaineDeProduction> (calc.getListChaineIndependante());
    	
    	ArrayList <ChaineDeProduction> nouvelleListeChaine = new ArrayList<>();
    	nouvelleListeChaine = calc.getNouvelleListProduction();
    	if (!impossible.isEmpty()) {
    		Label labelInfo = new Label("La production pour les chaines suivantes est impossible!");
    		vboxRes.getChildren().add(labelInfo);
        	for (ChaineDeProduction c : impossible) {
        		Label labelDetail = new Label(c.getCode() + " - Quantité en entrée insuffisante!");
                vboxRes.getChildren().add(labelDetail); 
        	}
        	Label space = new Label("");
        	vboxRes.getChildren().add(space);
    	}

    	if (calc.getTempsMis() > calc.getTempsActivitesUsines() || calc.getTempsMis() == 0) {
    		Label labelTemps = new Label ("Le temps mis pour la production choisi est: " + calc.getTempsMis());
            Label labelInfo = new Label("La production n'est pas possible dans les délais!");
            vboxRes.getChildren().addAll(labelTemps, labelInfo); 
    	}
    	else {
    		Label labelTemps = new Label ("Le temps mis pour la production choisi est: " + calc.getTempsMis());
            Label labelInfo = new Label("La production est possible dans les délais!");
            vboxRes.getChildren().addAll(labelTemps, labelInfo); 
    	}
    	
    	
    	
    	ObservableList <Element> oElement = FXCollections.observableList(this.elements);
    	ObservableList <ChaineDeProduction> oChaineProduction = FXCollections.observableList(nouvelleListeChaine);
    	chargerTabNewStock(oElement);
    	chargerSimulationNouvelleChaine(oChaineProduction);
    	
    }
}
