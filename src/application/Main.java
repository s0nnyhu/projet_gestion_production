package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * @author RÃ©gis & Sonny
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("WindowMain.fxml"));
	        Scene scene = new Scene(root);
	        stage.setTitle("Gestion de Production");
	        stage.setScene(scene);
	        stage.show();
		}
		catch(Exception e) {
			System.out.println("erreur");
		}
	}
	
	/**
	 * Main method
	 * Charge les données et lance l'application
	 * @param args
	 */
	public static void main(String[] args) {
		InitialisationDonnees.initialiserElements();
		InitialisationDonnees.initialiserChaines();
		launch(args);
	}
}
