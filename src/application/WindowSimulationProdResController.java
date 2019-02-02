package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WindowSimulationProdResController {

	@FXML
	private Button retour;	

	@FXML
	private Button export;	

    @FXML
    void retour(ActionEvent event) {
    	Stage stage = (Stage) retour.getScene().getWindow();
        stage.close();
    }
    
    
    @FXML
    void exporter(ActionEvent event) {
    	this.retour(event);
    	//Methode exportation
    	String nomFic = "export.csv";
    	
    	//Notification
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setHeaderText("Exportation des essais de production");
    	alert.setContentText("Les essais de production ont été exportés dans le fichier \""+nomFic+"\".");

    	alert.showAndWait();
    }
    
    void initData(String[] str) {
    	/*this.lbl.setText(str[1]);
    	for (String s : str) {
    		System.out.println(s);
    	}*/
    }
}
