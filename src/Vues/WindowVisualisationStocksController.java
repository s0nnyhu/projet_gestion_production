package Vues;

import java.util.ArrayList;

import application.Element;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class WindowVisualisationStocksController {

	@FXML
	private Label lbl;
	
    @FXML
    private TableView<Element> tabElement;

    @FXML
    private TableColumn<Element, String> tNom;

    @FXML
    private TableColumn<Element, Double> tQuantite;

    @FXML
    private TableColumn<Element, String> tUnite;

    @FXML
    private TableColumn<Element, Double> tPA;

    @FXML
    private TableColumn<Element, Double> tPV;

    @FXML
    private TableColumn<Element, Double> tDemande;
    
    @FXML
    private Button btnRetour;

    /**
     * @param event
     */
    @FXML
    void retour(ActionEvent event) {
    	Stage stage = (Stage)btnRetour.getScene().getWindow();
    	stage.close();
    }

    
	/**
	 * Remplie le tableau de visualisation des stocks
	 */
	void initData(ArrayList<Element> e) {
    	ObservableList <Element> oElt = FXCollections.observableList(e);
		this.tNom.setCellValueFactory(
                new PropertyValueFactory<Element, String>("nom"));
		this.tQuantite.setCellValueFactory(
                new PropertyValueFactory<Element, Double>("quantite"));
		this.tUnite.setCellValueFactory(
                new PropertyValueFactory<Element, String>("unite"));
		this.tPA.setCellValueFactory(
                new PropertyValueFactory<Element, Double>("achat"));
		this.tPV.setCellValueFactory(
                new PropertyValueFactory<Element, Double>("vente"));
		this.tDemande.setCellValueFactory(
                new PropertyValueFactory<Element, Double>("demande"));
		this.tabElement.setItems(oElt);
    }

}
