package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	private ArrayList<ChaineDeProduction> chaines;
	
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
    private String[] tabValueTxtField;
    
    

    @FXML
    void evaluer(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowSimulationProdRes.fxml"));     

    	Parent root = (Parent)fxmlLoader.load();          
    	WindowSimulationProdResController controller = fxmlLoader.<WindowSimulationProdResController>getController();
    	
    	
    	for (int i=0; i<tabTxtField.length;i++) {
    		tabValueTxtField[i] = tabTxtField[i].getText();
    	}
    	
    	controller.initData(tabValueTxtField);
    	Scene scene = new Scene(root); 
    	Stage stage = new Stage();
    	stage.setScene(scene);    

    	stage.show();   
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

    	tabView.setItems(oChaine);
    	tabTxtField = new TextField[oChaine.size()];
    	tabValueTxtField = new String[oChaine.size()];
    	for (int i = 0; i<oChaine.size(); i++) {
    		TextField tf = new TextField();
    		tf.setPrefWidth(80);
    		tabTxtField[i] = tf;
    		Label lbl = new Label(chaines.get(i).getCode());
    		HBox h = new HBox();
    		h.setPadding(new Insets(15, 12, 20, 12));
    		h.setSpacing(20);
    		h.getChildren().addAll(lbl,tabTxtField[i]);
    		vbox.getChildren().add(h);
    		
    	}
    	
    }

}
