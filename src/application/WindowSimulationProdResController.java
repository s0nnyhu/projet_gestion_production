package application;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WindowSimulationProdResController {

	@FXML
	private Button retour;	

	@FXML
	private Button export;	

    @FXML
    void retour(ActionEvent event) {
    	Stage stage = (Stage) retour.getScene().getWindow();
        stage.close();
    }
    
    
    @FXML
    void exporter(ActionEvent event) {
    	this.retour(event);
    	//Methode exportation
    	String nomFic = "export.csv";
    	
    	//Notification
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setHeaderText("Exportation des essais de production");
    	alert.setContentText("Les essais de production ont été exportés dans le fichier \""+nomFic+"\".");

    	alert.showAndWait();
    }
    
    void initData(String[] str) {
    	for (String s : str) {
    		System.out.println(s);
    	}
    	for (Element e : InitialisationDonnees.elements) {
    		System.out.println(e.getCode() + " - " + e.getQuantite());
    	}
    	System.out.println("-------------");
    	System.out.println("salutttt");
    	for (ChaineDeProduction c : InitialisationDonnees.chaines) {
    		for (Element e1: c.getEntree().keySet()) {
    			for (Element e2 : InitialisationDonnees.elements) {
    				if (e1.getCode() == e2.getCode()) {
    					System.out.println(e1.getQuantite() + "-" + c.getEntree().get(e1));
    					e1.setQuantite(e1.getQuantite() - c.getEntree().get(e1));
    				}
    			}
    		}
    	}
    	System.out.println("-----------------");
    	for (Element e : InitialisationDonnees.elements) {
    		System.out.println(e.getQuantite());
    	}
    }
}
