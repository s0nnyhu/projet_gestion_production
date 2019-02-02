package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class WindowVisualisationStocksController {

	@FXML
	private Label lbl;
	
    @FXML
    private TableView<Element> tabElement;

    @FXML
    private TableColumn<Element, String> tCode;

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
    
    void initialize() {
    	
    }
    
	void initData(ObservableList <Element> e) {
		this.tCode.setCellValueFactory(
                new PropertyValueFactory<Element, String>("code"));
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
		this.tabElement.setItems(e);
    }

}
