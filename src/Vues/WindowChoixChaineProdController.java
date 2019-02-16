package Vues;

import java.util.ArrayList;

import application.ChaineDeProduction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class WindowChoixChaineProdController {
	
	private ArrayList<ChaineDeProduction> chaines;

    @FXML
    private TableColumn<?, ?> colChoisir;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colEntree;

    @FXML
    private TableColumn<?, ?> colSortie;

    @FXML
    private TableColumn<?, ?> colTemps;

    @FXML
    private Button btnSuivant;

    @FXML
    private Button btnAnnuler;
    
    void initData(ArrayList<ChaineDeProduction> c) {
    	System.out.println("hello");
		this.chaines = new ArrayList<>();

		this.chaines = c;
		
    	ObservableList <ChaineDeProduction> oChaine = FXCollections.observableList(this.chaines);
    	this.colCode.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("code"));
		this.colNom.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("nom"));
		this.colEntree.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("strEntree"));
		this.colSorti.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("strSorti"));

    	tabView.setItems(oChaine);
    	tabTxtField = new TextField[oChaine.size()];
    	tabValueTxtField = new Double[oChaine.size()];
    	for (int i = 0; i<oChaine.size(); i++) {
    		TextField tf = new TextField();
    		tf.setPrefWidth(80);
    		tabTxtField[i] = tf;
    		tf.setPromptText("0");
    		Label lbl = new Label(c.get(i).getCode());
    		HBox h = new HBox();
    		h.setPadding(new Insets(15, 12, 20, 12));
    		h.setSpacing(20);
    		h.getChildren().addAll(lbl,tabTxtField[i]);
    		vbox.getChildren().add(h);
    		
    	}
    	
    }

}
