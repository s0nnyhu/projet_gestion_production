package Vues;

import java.io.IOException;
import java.util.ArrayList;

import application.ChaineDeProduction;
import application.Element;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

public class WindowSimulationProdController {
	
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
    private SplitPane splitPane;
    
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
    
	protected ArrayList<Element> elements;
	
	protected ArrayList<ChaineDeProduction> chaines;

    /**
     * @param event
     */
    @FXML
    void evaluer(ActionEvent event) {
    	
    	try {

        	for (int i=0; i<tabTxtField.length;i++) {
        		if(tabTxtField[i].getText().isEmpty()) {
        			tabTxtField[i].setText("0");
        		}
        		if (Double.parseDouble(tabTxtField[i].getText()) < 0) {
        			throw new NumberFormatException();
        		}
        		tabValueTxtField[i] = Double.parseDouble(tabTxtField[i].getText());
        	}

    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowSimulationProdRes.fxml"));     

        	Parent root = (Parent)fxmlLoader.load();          
        	WindowSimulationProdResController controller = fxmlLoader.<WindowSimulationProdResController>getController();
        	controller.initData(this.elements, this.chaines, tabValueTxtField);
        	Scene scene = new Scene(root); 
        	Stage stage = new Stage();
        	stage.setTitle("Resultats: Evaluation");
        	stage.setScene(scene);    

        	stage.show(); 
    	}
    	catch(NumberFormatException e) {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.getDialogPane().setMinWidth(500);
        	alert.setTitle("Erreur");
        	alert.setHeaderText("Une erreur est survenue!");
        	alert.setContentText("Les donnees rentrees doivent etre des chiffres superieurs a 0!");
        	alert.showAndWait().ifPresent(rs -> {
        	    if (rs == ButtonType.OK) {
        	        System.out.println("Pressed OK.");
        	    }
        	});
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
    	catch(Exception e) {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Erreur");
        	alert.setHeaderText("Une erreur est survenue!");
        	alert.setContentText("Une erreur est survenue!");
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
    void retour(ActionEvent event) {
    	Stage stage = (Stage)btnRetour.getScene().getWindow();
    	stage.close();
    }
    
    /**
     * 
     */
    void initData(ArrayList<Element> e, ArrayList<ChaineDeProduction> c) {
		this.elements = new ArrayList<>();
		this.chaines = new ArrayList<>();
		
		this.elements = e;
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
