package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WindowMainController extends InitialisationDonnees{

    @FXML
    private Button btnVisuStocks;

    @FXML
    private Button btnEssaiProd;
    
    @FXML
    private Button quitter;


    @FXML
    void openEssaisProd(ActionEvent event) throws IOException {
    	/* Alert box
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Message Here...");
    	alert.setHeaderText("Look, an Information Dialog");
    	alert.setContentText("I have a great message for you!");
    	alert.showAndWait().ifPresent(rs -> {
    	    if (rs == ButtonType.OK) {
    	        System.out.println("Pressed OK.");
    	    }
    	});
    	*/
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowSimulationProd.fxml"));     

    	Parent root = (Parent)fxmlLoader.load();          
    	WindowSimulationProdController controller = fxmlLoader.<WindowSimulationProdController>getController();
    	
    	controller.initData(InitialisationDonnees.chaines);
    	Scene scene = new Scene(root); 
    	Stage stage = new Stage();
    	stage.setScene(scene);    

    	stage.show();   
    	
    }

    @FXML
    void openStocksView(ActionEvent event) throws IOException {
    	ObservableList <Element> obElt = FXCollections.observableList(InitialisationDonnees.elements);
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowVisualisationStocks.fxml"));     

    	Parent root = (Parent)fxmlLoader.load();          
    	WindowVisualisationStocksController controller = fxmlLoader.<WindowVisualisationStocksController>getController();
    	
    	controller.initData(obElt);
    	Scene scene = new Scene(root); 
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	stage.setTitle("Stocks de production");

    	stage.show();   
    }
    
    @FXML
    void exit(ActionEvent event) {
    	Platform.exit();
    }

}
