package Vues;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class WindowChoixNiveauEtTempsController {

    @FXML
    private SplitPane splitPane;

    @FXML
    private TableView<?> tabView;

    @FXML
    private TableColumn<?, ?> tCode;

    @FXML
    private TableColumn<?, ?> tNom;

    @FXML
    private TableColumn<?, ?> tEntree;

    @FXML
    private TableColumn<?, ?> tSorti;

    @FXML
    private TableColumn<?, ?> tTemps;

    @FXML
    private AnchorPane anchPane;

    @FXML
    private VBox vbox;

    @FXML
    private Button btnEvaluer;

    @FXML
    private Button btnRetour;

    @FXML
    void evaluer(ActionEvent event) {

    }

    @FXML
    void retour(ActionEvent event) {

    }

}
