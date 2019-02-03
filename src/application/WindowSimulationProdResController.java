package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class WindowSimulationProdResController {
	
	private ArrayList<Production> production = new ArrayList<Production>();
	private ArrayList <Element> listeAchat = new ArrayList<Element>();

	@FXML
	private Button retour;	

	@FXML
	private Button export;	

    @FXML
    private TableView<Production> tabSimulationProd;

    @FXML
    private TableColumn<Production, String> colChaine;

    @FXML
    private TableColumn<Production, Double> colCoutVente;

    @FXML
    private TableColumn<Production, Double> colEfficacite;
    
    @FXML
    private TableView<Element> tabNewStock;

    @FXML
    private TableColumn<Element, String> colCode;

    @FXML
    private TableColumn<Element, String> colNom;

    @FXML
    private TableColumn<Element, Double> colQuantite;

    @FXML
    private TableColumn<Element, String> colUnite;

    @FXML
    private TableColumn<Element, Double> colPA;

    @FXML
    private TableColumn<Element, Double> colPV;

    @FXML
    private TextArea txtListeAchat;
    
    @FXML
    private TextArea txtProdImpossible;
    
    /**
     * @param event
     */
    @FXML
    void exporter(ActionEvent event) {
    	try {
    		FileWriter fw = new FileWriter(new File("Nouveau_Stock.csv"));
    		fw.write("Code;Nom;Quantite;unite;achat;vente");
            fw.write(System.lineSeparator());
    		for (Element e : InitialisationDonnees.elements) {
                fw.write(String.format("%s;%s;%s;%s;%s,%s",e.getCode(), e.getNom(), e.getQuantite(), e.getUnite(), e.getAchat(), e.getVente()));
                fw.write(System.lineSeparator());
    		}
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    	try {
    		FileWriter fw = new FileWriter(new File("Production.csv"));
    		fw.write("Chaine,coutVente,Efficacite");
            fw.write(System.lineSeparator());
    		for (Production p : this.production) {
                fw.write(String.format("%s;%s;%s", p.getNom(), p.getCoutVente(), p.getEfficacite()));
                fw.write(System.lineSeparator());
    		}
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    	
    	try {
    		FileWriter fw = new FileWriter(new File("Liste_Achats.csv"));
    		fw.write("Code,Nom,Quantite");
            fw.write(System.lineSeparator());
    		for (Element e : this.listeAchat) {
                fw.write(String.format("%s;%s;%s", e.getCode(), e.getNom(), Math.abs(e.getQuantite())));
                fw.write(System.lineSeparator());
    		}
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.getDialogPane().setPrefWidth(700);
    	alert.getDialogPane().setPrefWidth(400);
    	alert.setHeaderText("Exportation des essais de production");
    	alert.setContentText("Les essais de production ont été exportés Nouveau_Stock.csv, Production.csv, Liste_Achats.csv!");

    	alert.showAndWait();
    	
    }

    /**
     * @param p
     */
    void chargerSimulationProduction(ObservableList <Production> p) {
		this.colChaine.setCellValueFactory(
                new PropertyValueFactory<Production, String>("nom"));
		this.colCoutVente.setCellValueFactory(
                new PropertyValueFactory<Production, Double>("coutVente"));
		this.colEfficacite.setCellValueFactory(
                new PropertyValueFactory<Production, Double>("efficacite"));
		this.tabSimulationProd.setItems(p);
    }
    
    /**
     * @param e
     */
    void chargerTabNewStock(ObservableList <Element> e) {
    		this.colCode.setCellValueFactory(
                    new PropertyValueFactory<Element, String>("code"));
    		this.colNom.setCellValueFactory(
                    new PropertyValueFactory<Element, String>("nom"));
    		this.colQuantite.setCellValueFactory(
                    new PropertyValueFactory<Element, Double>("quantite"));
    		this.colUnite.setCellValueFactory(
                    new PropertyValueFactory<Element, String>("unite"));
    		this.colPA.setCellValueFactory(
                    new PropertyValueFactory<Element, Double>("achat"));
    		this.colPV.setCellValueFactory(
                    new PropertyValueFactory<Element, Double>("vente"));
    		this.tabNewStock.setItems(e);
    }
    
    /**
     * @param event
     */
    @FXML
    void retour(ActionEvent event) {
    	Stage stage = (Stage) retour.getScene().getWindow();
        stage.close();
    }

    /**
     * @param niveau
     */
    void initData(Double[] niveau) {
    	
    	int indexListeAchat = 0;

    	int i = 0;
    	boolean estPossible = true;
    	double coutVente = 0;
    	double efficacite = 0;
    	double totalAchatChaine = 0;
    	
    	for (ChaineDeProduction c : InitialisationDonnees.chaines) {
        	coutVente = 0;
        	efficacite = 0;
        	totalAchatChaine = 0;
			estPossible = true;
			
			System.out.println("niveau: " + niveau[i]);
    		if (niveau[i] == 0) {
    			c.setSortie(null);
    			estPossible = false;
    		}
    		else {
    			/*
    			 * Pour chaque élement en entrée dans la chaine de production, on récupère la quantité nécessaire et
    			 * s'il correspond à l'élement de notre tableau des éléments, on met à jour la quantité 
    			 * dans notre tableau des élements.
    			 */
    			for (Element elEntree: c.getEntree().keySet()) {
        			for (Element elStock : InitialisationDonnees.elements) {
        				if (elEntree.getCode() == elStock.getCode()) {
        					elStock.setQuantite(elStock.getQuantite() - (c.getEntree().get(elEntree)*niveau[i]));
        				}
        			}
        		}
    			/*
    			 * Pour chaque élement en sortie de la chaine de production, on récupère la quantité sortie et
    			 * s'il correspond à l'élement de notre tableau des éléments, on met à jour la quantité 
    			 * dans notre tableau des élements.
    			 */
    			for (Element elSorti : c.getSortie().keySet()) {
    				for (Element elStock: InitialisationDonnees.elements) {
        				if (elSorti.getCode() == elStock.getCode()) {
        					elStock.setQuantite(elStock.getQuantite() + (c.getSortie().get(elSorti)*niveau[i]));
        					if (elStock.getVente() != 0) {
            					coutVente += elStock.getQuantite() * elStock.getVente();
        					}
        				}
    				}
    			}
    			
    			/*
    			 * Pour chaque élement en entrée dans la chaine de production,
    			 * s'il correspond à l' un des élements de notre tableau d'éléments et qu'il une quantité négative,
    			 * on remplie notre tableau de liste d'élements à acheter en fonction et on fait la somme du prix d'achat
    			 * de chaque élement à acheter
    			 */
    			for (Element elEntree: c.getEntree().keySet()) {
        			for (Element e : InitialisationDonnees.elements) {
        				if (e.getCode() == elEntree.getCode()) {
        					if (e.getQuantite() < 0) {
        						if (e.getAchat() == 0) {
        							estPossible = false;
        						}
        						else
        						{
	        						listeAchat.add(e);
	        						this.txtListeAchat.appendText(listeAchat.get(indexListeAchat).getCode() + ": " + Math.abs(listeAchat.get(indexListeAchat).getQuantite()) + "\n");
	        						totalAchatChaine += e.getQuantite() * e.getAchat();
	        						System.out.println(totalAchatChaine + ":" + e.getQuantite() + "*" + e.getAchat());
	        						indexListeAchat++;
	        					}
	        				}
        				}
        				
        			}
    			}
				efficacite = coutVente-totalAchatChaine;
    		}
			if (estPossible == false) {
				this.txtProdImpossible.appendText(c.getCode() + ": PRODUCTION IMPOSSIBLE\n");
    			production.add(new Production(c, 0, 0));
    		}
    		else {
    			production.add(new Production(c,coutVente, efficacite));
    		}
    	}
    	
    	ObservableList <Element> oElement = FXCollections.observableList(InitialisationDonnees.elements);
    	ObservableList <Production> oProduction = FXCollections.observableList(production);
    	chargerTabNewStock(oElement);
    	chargerSimulationProduction(oProduction);
    	
    }
}
