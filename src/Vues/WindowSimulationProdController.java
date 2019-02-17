package Vues;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import application.ChaineDeProduction;
import application.Element;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

public class WindowSimulationProdController {
	
    @FXML
    private TableView<ChaineDeProduction> tabView;

    @FXML
    private TableColumn<ChaineDeProduction, CheckBox> tChoisir;
    
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
    
    private HashMap<ChaineDeProduction, TextField> mapChaineNiveau;
    
	protected ArrayList<Element> elements;
	
	protected ArrayList<ChaineDeProduction> chaines;
	

    /**
     * @param event
     */
    @FXML
    void evaluer(ActionEvent event) {
    	try {
    		
    		for (TextField s : this.mapChaineNiveau.values()) {
    			if(s.getText().isEmpty()) {
    				s.setText("0");
    			}
    			if (Double.parseDouble(s.getText()) < 0) {
    				throw new NumberFormatException();
    			}
        	}
        	

    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowSimulationProdRes.fxml"));     

        	Parent root = (Parent)fxmlLoader.load();          
        	WindowSimulationProdResController controller = fxmlLoader.<WindowSimulationProdResController>getController();
        	controller.initData(this.elements, this.mapChaineNiveau);
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
        	alert.setContentText("Les données rentrées doivent être des chiffres supérieurs ou égales à 0!");
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
    		System.out.println(e.getMessage());
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
    	this.mapChaineNiveau = new HashMap<>();
		this.elements = new ArrayList<>();
		this.chaines = new ArrayList<>();
		
		for (ChaineDeProduction newChaine: c) {
			this.chaines.add(new ChaineDeProduction(newChaine));
		}
		this.elements = e;
		
    	ObservableList <ChaineDeProduction> oChaine = FXCollections.observableList(this.chaines);
    	this.tChoisir.setCellValueFactory(
    			new PropertyValueFactory<ChaineDeProduction, CheckBox>("ckBox"));
    	this.tCode.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("code"));
		this.tNom.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("nom"));
		this.tEntree.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("strEntree"));
		this.tSorti.setCellValueFactory(
                new PropertyValueFactory<ChaineDeProduction, String>("strSorti"));
		
		for (ChaineDeProduction cha : this.chaines) {
			
            TextField tf = new TextField();
    		tf.setPrefWidth(80);
    		tf.setPromptText("0");
    		Label lbl = new Label(cha.getCode());
    		HBox h = new HBox();
    		h.setPadding(new Insets(15, 12, 20, 12));
    		h.setSpacing(20);
    		
			cha.getCkBox().setOnAction(new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent event) {
		            CheckBox chk = (CheckBox) event.getSource();
			        if (event.getSource() instanceof CheckBox) {
			            if (chk.isSelected()) {
			        		mapChaineNiveau.put(cha, tf);
			        		h.getChildren().addAll(lbl,tf);
			        		vbox.getChildren().add(h);
			            }
			            else {
			            	mapChaineNiveau.remove(cha);
			            	h.getChildren().clear();
			            	vbox.getChildren().remove(h);
			            }
			        }
			    }
			});
		}
		
    	tabView.setItems(oChaine);
    	tabTxtField = new TextField[oChaine.size()];
    	tabValueTxtField = new Double[oChaine.size()];    	
    }

}
