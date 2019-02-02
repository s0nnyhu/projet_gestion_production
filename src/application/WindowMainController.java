package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class WindowMainController {

    @FXML
    private Button btnVisuStocks;

    @FXML
    private Button btnEssaiProd;

    @FXML
    void openEssaisProd(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Message Here...");
    	alert.setHeaderText("Look, an Information Dialog");
    	alert.setContentText("I have a great message for you!");
    	alert.showAndWait().ifPresent(rs -> {
    	    if (rs == ButtonType.OK) {
    	        System.out.println("Pressed OK.");
    	    }
    	});
    }

    @FXML
    void openStocksView(ActionEvent event) throws IOException {
    	
    	ArrayList<Element> elements = new ArrayList<Element>();
		
    	try {
			FileReader elem = new FileReader("/home/sonny/eclipse-workspace/projet_gestion_production/src/DonneesV1/elements.csv");
			BufferedReader br = new BufferedReader(elem);
			String line = br.readLine(); //saut de la premiere ligne en effectuant une lecture
			while( (line = br.readLine() ) != null) {
				String[] fields = line.split(";");
				for(int i=0; i<fields.length; i++) {
					if(fields[i].equals("NA")) {
						fields[i] = "0";
					}
				}
				String code = fields[0];
				String nom = fields[1];
				double quantite = Double.parseDouble(fields[2]);
				String unite = fields[3];
				double achat = Double.parseDouble(fields[4]);
				double vente = Double.parseDouble(fields[5]);
				
				Element el = new Element(code, nom, quantite, unite, achat, vente);
				elements.add(el);
			}
			br.close();
			elem.close();
		} catch (IOException e) {
			System.err.println("Erreur dans le nom du fichier...");
			e.printStackTrace();
		}
    	ObservableList <Element> obElt = FXCollections.observableList(elements);
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowVisualisationStocks.fxml"));     

    	Parent root = (Parent)fxmlLoader.load();          
    	WindowVisualisationStocksController controller = fxmlLoader.<WindowVisualisationStocksController>getController();
    	
    	controller.initData(obElt);
    	Scene scene = new Scene(root); 
    	Stage stage = new Stage();
    	stage.setScene(scene);    

    	stage.show();   
    }

}
