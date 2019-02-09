package Vues;

import java.io.IOException;
import java.util.ArrayList;
import application.ChaineDeProduction;
import application.DonneesCSV;
import application.Element;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class WindowMainController{

    @FXML
    private Button btnVisuStocks;

    @FXML
    private Button btnEssaiProd;
    
    @FXML
    private Button quitter;

	protected ArrayList<Element> elements;
	
	protected ArrayList<ChaineDeProduction> chaines;

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
        	
        	controller.initData(this.elements);
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
    void chargerDonnees() {
		this.elements = new ArrayList<>();
		this.chaines = new ArrayList<>();
    	DonneesCSV data = new DonneesCSV("/home/sonny/eclipse-workspace/projet_gestion_production/src/DonneesV1/elements.csv", "/home/sonny/eclipse-workspace/projet_gestion_production/src/DonneesV1/chaines.csv");
		this.elements = data.getElements();
		this.chaines = data.getChaines();
    	btnVisuStocks.setDisable(false);
		btnEssaiProd.setDisable(false);
    }
    
    /**
     * @param event
     */
    @FXML
    void exit(ActionEvent event) {
    	Platform.exit();
    }

}
