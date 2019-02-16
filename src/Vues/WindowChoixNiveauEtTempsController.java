package Vues;

import java.util.ArrayList;

import application.ChaineDeProduction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WindowChoixNiveauEtTempsController {
	private ArrayList<ChaineDeProduction> chaines;

    @FXML
    private SplitPane splitPane;

    @FXML
    private TableView<ChaineDeProduction> tabView;

    @FXML
    private TableColumn<ChaineDeProduction, String> tCode;

    @FXML
    private TableColumn<ChaineDeProduction, String> tNom;

    @FXML
    private TableColumn<ChaineDeProduction, String> tEntree;

    @FXML
    private TableColumn<ChaineDeProduction, String> tSorti;

    @FXML
    private TableColumn<ChaineDeProduction, Integer> tTemps;

    @FXML
    private AnchorPane anchPane;

    @FXML
    private VBox vbox;

    @FXML
    private Button btnEvaluer;

    @FXML
    private Button btnRetour;

    private TextField[] tabTxtField;
    private Double[] tabValueTxtField;
    
    @FXML
    void evaluer(ActionEvent event) {
    	for (int i=0; i<tabTxtField.length;i++) {
    		if(tabTxtField[i].getText().isEmpty()) {
    			tabTxtField[i].setText("0");
    		}
    		if (Double.parseDouble(tabTxtField[i].getText()) < 0) {
    			throw new NumberFormatException();
    		}
    		tabValueTxtField[i] = Double.parseDouble(tabTxtField[i].getText());
    	}
    	for (Double d: tabValueTxtField) {
    		System.out.println(d.toString());
    	}
    }

    @FXML
    void retour(ActionEvent event) {
    	Stage stage = (Stage)btnRetour.getScene().getWindow();
    	stage.close();
    }

    void initData(ArrayList<ChaineDeProduction> c) {
    	this.chaines = c;
		
    	ObservableList <ChaineDeProduction> oChaine = FXCollections.observableList(this.chaines);
    	this.tCode.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("code"));
		this.tNom.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("nom"));
		this.tEntree.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("strEntree"));
		this.tSorti.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("strSorti"));
		this.tTemps.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, Integer>("temps"));
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
