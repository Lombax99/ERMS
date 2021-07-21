package main.java.views.interfacciaStrumenti.interfacciaPinze;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFormattedTextField;

import com.jfoenix.controls.JFXDrawer;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import main.java.controllers.strumenti.pinze.FiltroPinze;
import main.java.controllers.strumenti.pinze.GestionePinzeController;
import main.java.models.strumenti.pinza.Appartenenza;
import main.java.models.strumenti.pinza.Condizione;
import main.java.models.strumenti.pinza.Pinza;
import main.java.views.Utility_SidePanel;

public class ViewPinze implements Initializable {

	private GestionePinzeController controllerPinze;
	final Popup popup = new Popup();

	/*
	 * Definizione dei componenti grafici
	 */

	@FXML
	private JFXDrawer sidePanel;
	@FXML
	private Button exitButton;
	@FXML
	private ListView<String> listViewDepositi;

	@FXML
	ChoiceBox<Integer> ID_DepositoField;
	@FXML
	ChoiceBox<Appartenenza> AppartenenzaField;
	@FXML
	ChoiceBox<Condizione> CondizioneField;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		/*
		 * Set up del sidePanel
		 */
		Utility_SidePanel.initialize(exitButton, sidePanel);

		controllerPinze = new GestionePinzeController();
		ID_DepositoField = new ChoiceBox();
		AppartenenzaField = new ChoiceBox();
		for (Appartenenza appartenenza : Appartenenza.values()) {
			AppartenenzaField.getItems().add(appartenenza);
		}
		CondizioneField = new ChoiceBox();
		for (Condizione condizione : Condizione.values()) {
			AppartenenzaField.getItems().add(condizione);
		}


		popup.setX(300);
		popup.setY(200);
		popup.getContent().add(e);
		
		
		/*
		 * Prelevamento depositi
		 */
		GestionePinzeController gp = new GestionePinzeController();
		ResultSet result = gp.elencoStrumenti(new FiltroPinze());
		
		List<String> listaDepositi = new ArrayList<>();
		while(result.next()) {
			listaDepositi.add(result.getString(4));
		}
		
		listViewDepositi.setItems(FXCollections.observableArrayList(listaDepositi));
	}



	@FXML
	private void AggiungiPinzaHandler() {
		popup.show();
	}


	@FXML
	private void PopUpAggiungiPinzaHandler() {
		Pinza pinza = new Pinza(Integer.valueOf((String) ID_DepositoField.getValue()), 0, Appartenenza.valueOf((String) AppartenenzaField.getValue()),
					Condizione.valueOf((String) CondizioneField.getValue()));

		controllerPinze.aggiuntaNuovoStrumento(pinza);
	}
}
