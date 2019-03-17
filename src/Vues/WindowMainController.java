package Vues;

import java.io.IOException;
import java.util.ArrayList;
import application.ChaineDeProduction;
import application.Element;
import application.Stockage;
import donnees.DonneesLibraryCSV;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class WindowMainController{

    @FXML
    private Button btnVisuStocks;

    @FXML
    private Button btnEssaiProd;
    
    @FXML
    private Button btnSimulationTemps;
    
    @FXML
    private Button quitter;
    
    @FXML
    private Label loadedLabel;

	protected ArrayList<Element> elements;
	
	protected ArrayList<ChaineDeProduction> chaines;

	protected ArrayList<Stockage> stockages;
	
    /**
     * @param event
     */
    @FXML
    void openEssaisProd(ActionEvent event){
    	try {
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowSimulationProd.fxml"));     

        	Parent root = (Parent)fxmlLoader.load();          
        	WindowSimulationProdController controller = fxmlLoader.<WindowSimulationProdController>getController();
        	
        	controller.initData(this.elements, this.chaines);
        	Scene scene = new Scene(root); 
        	Stage stage = new Stage();
        	stage.setTitle("Evaluation de la production");
        	stage.setScene(scene);    

        	stage.show();   
    	}
    	catch(IOException e1) {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.getDialogPane().setMinWidth(500);
        	alert.setTitle("Erreur");
        	alert.setHeaderText("Une erreur est survenue!");
        	alert.setContentText("Une erreur IOException est survenue!");
        	alert.showAndWait().ifPresent(rs -> {
        	    if (rs == ButtonType.OK) {
        	        System.out.println("Pressed OK.");
        	    }
        	});
    	}
    	
    	
    }

    /**
     * @param event
     */
    @FXML
    void openStocksView(ActionEvent event) {
    	try {
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowVisualisationStocks.fxml"));     

        	Parent root = (Parent)fxmlLoader.load();          
        	WindowVisualisationStocksController controller = fxmlLoader.<WindowVisualisationStocksController>getController();
        	
        	controller.initData(this.elements, this.stockages);
        	Scene scene = new Scene(root); 
        	Stage stage = new Stage();
        	stage.setScene(scene);
        	stage.setTitle("Stocks de production");

        	stage.show();   
    	}
    	catch(IOException e1) {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.getDialogPane().setMinWidth(500);
        	alert.setTitle("Erreur");
        	alert.setHeaderText("Une erreur est survenue!");
        	alert.setContentText("Une erreur IOException est survenue!");
        	alert.showAndWait().ifPresent(rs -> {
        	    if (rs == ButtonType.OK) {
        	        System.out.println("Pressed OK.");
        	    }
        	});
    	}
    	
    }
    
    @FXML
    void openSimulationTemps(ActionEvent event){
    	try {
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowSimulationAvecTemps.fxml"));     

        	Parent root = (Parent)fxmlLoader.load();          
        	WindowSimulationAvecTempsController controller = fxmlLoader.<WindowSimulationAvecTempsController>getController();
        	
        	controller.initData(this.elements, this.chaines);
        	Scene scene = new Scene(root); 
        	Stage stage = new Stage();
        	stage.setScene(scene);
        	stage.setTitle("Simulation avec prise en compte du temps");

        	stage.show();   
    	}
    	catch(IOException e1) {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.getDialogPane().setMinWidth(500);
        	alert.setTitle("Erreur");
        	alert.setHeaderText("Une erreur est survenue!");
        	alert.setContentText("Une erreur IOException est survenue!");
        	alert.showAndWait().ifPresent(rs -> {
        	    if (rs == ButtonType.OK) {
        	        System.out.println("Pressed OK.");
        	    }
        	});
    	}
    }
    
    @FXML
    void chargerDonnees() {
		this.elements = new ArrayList<>();
		this.chaines = new ArrayList<>();
		this.stockages = new ArrayList<>();
		String cheminElements = "/home/sonny/eclipse-workspace/projet_gestion_production/src/DonneesV2/elements.csv";
		String cheminChaines = "/home/sonny/eclipse-workspace/projet_gestion_production/src/DonneesV2/chaines.csv";
		String cheminStockages = "/home/sonny/eclipse-workspace/projet_gestion_production/src/DonneesV2/stockage.csv";
    	DonneesLibraryCSV data = new DonneesLibraryCSV(cheminElements, cheminChaines, cheminStockages);
		this.elements = data.getElements();
		this.chaines = data.getChaines();
		this.stockages = data.getStockages();
		if(data.getLoaded()) {
	    	btnVisuStocks.setDisable(false);
			btnEssaiProd.setDisable(false);	
			btnSimulationTemps.setDisable(false);
		}
		else {
			loadedLabel.setText("Echec du chargement des données");
		}
		loadedLabel.setVisible(true);
    }
    
    /**
     * @param event
     */
    @FXML
    void exit(ActionEvent event) {
    	Platform.exit();
    }

}
