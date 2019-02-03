package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class WindowMainController extends InitialisationDonnees{

    @FXML
    private Button btnVisuStocks;

    @FXML
    private Button btnEssaiProd;
    
    @FXML
    private Button quitter;


    /**
     * @param event
     */
    @FXML
    void openEssaisProd(ActionEvent event){
    	try {
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowSimulationProd.fxml"));     

        	Parent root = (Parent)fxmlLoader.load();          
        	WindowSimulationProdController controller = fxmlLoader.<WindowSimulationProdController>getController();
        	
        	controller.initData();
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
        	
        	controller.initData();
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
    
    /**
     * @param event
     */
    @FXML
    void exit(ActionEvent event) {
    	Platform.exit();
    }

}
