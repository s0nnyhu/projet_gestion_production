package Vues;

import java.io.IOException;
import java.util.ArrayList;

import application.ChaineDeProduction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class WindowChoixChaineProdController {
	
	private ArrayList<ChaineDeProduction> chaines;
	
    @FXML
    private TableView<ChaineDeProduction> tableView;

    @FXML
    private TableColumn<ChaineDeProduction, CheckBox> colChoisir;

    @FXML
    private TableColumn<ChaineDeProduction, String> colCode;
    
    @FXML
    private TableColumn<ChaineDeProduction, String> colNom;

    @FXML
    private TableColumn<ChaineDeProduction, String> colEntree;

    @FXML
    private TableColumn<ChaineDeProduction, String> colSortie;

    @FXML
    private TableColumn<ChaineDeProduction, Integer> colTemps;

    @FXML
    private Button btnSuivant;

    @FXML
    private Button btnAnnuler;
    
    void initData(ArrayList<ChaineDeProduction> c) {
		this.chaines = new ArrayList<>();
		
		for (ChaineDeProduction newChaine: c) {
			this.chaines.add(new ChaineDeProduction(newChaine));
		}

    	ObservableList <ChaineDeProduction> oChaine = FXCollections.observableList(this.chaines);
    	this.colChoisir.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, CheckBox>("ckBox"));
    	this.colCode.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("code"));
		this.colNom.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("nom"));
		this.colEntree.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("strEntree"));
		this.colSortie.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("strSorti"));
		this.colTemps.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, Integer>("temps"));
    	tableView.setItems(oChaine);
    	
    	
    }
    
    @FXML
    void actionAnnuler(ActionEvent event) {
    	Stage stage = (Stage)btnAnnuler.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void actionSuivant(ActionEvent event) {
		ArrayList<ChaineDeProduction> chainesChoisi = new ArrayList<>();
    	for (ChaineDeProduction c : this.chaines) {
    		if (c.getCkBox().isSelected()) {
    			chainesChoisi.add(c);
    		}
    	}
    	try {
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowChoixNiveauEtTemps.fxml"));     

        	Parent root = (Parent)fxmlLoader.load();          
        	WindowChoixNiveauEtTempsController controller = fxmlLoader.<WindowChoixNiveauEtTempsController>getController();
        	
        	controller.initData(chainesChoisi);
        	Scene scene = new Scene(root); 
        	Stage stage = new Stage();
        	stage.setScene(scene);
        	stage.setTitle("Temps de production");

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

}
