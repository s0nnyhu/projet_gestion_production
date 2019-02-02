package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WindowSimulationProdResController {

    @FXML
    private Label lbl;
    
    void initData(String[] str) {
    	this.lbl.setText(str[1]);
    	for (String s : str) {
    		System.out.println(s);
    	}
    }
}
