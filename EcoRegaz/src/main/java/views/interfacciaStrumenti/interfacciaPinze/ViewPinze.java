package main.java.views.interfacciaStrumenti.interfacciaPinze;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFormattedTextField;

import com.jfoenix.controls.JFXDrawer;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import main.java.application.AlertPanel;
import main.java.controllers.strumenti.pinze.FiltroPinze;
import main.java.controllers.strumenti.pinze.GestionePinzeController;
import main.java.models.areaVerde.AreaVerde;
import main.java.models.strumenti.pinza.Appartenenza;
import main.java.models.strumenti.pinza.Condizione;
import main.java.models.strumenti.pinza.Pinza;
import main.java.views.Utility_SidePanel;
import main.java.views.interfacciaLogin.HomeLogin;

public class ViewPinze implements Initializable {
	
	public final static String popUpURL = "/main/java/views/interfacciaStrumenti/interfacciaPinze/PopUpPinze.fxml";

	private GestionePinzeController controllerPinze;
	private Stage inserisciDatiPinzaStage = new Stage();

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
			CondizioneField.getItems().add(condizione);
		}

		/*
		 * Prelevamento depositi
		 */
		GestionePinzeController gp = new GestionePinzeController();
		//ResultSet result = gp.elencoStrumenti(new FiltroPinze());
		
		//List<String> listaDepositi = new ArrayList<>();
		/*while(result.next()) {
			listaDepositi.add(result.getString(4));
		}
		*/
		//listViewDepositi.setItems(FXCollections.observableArrayList(listaDepositi));
	}



	@FXML
	private void AggiungiPinzaHandler() {
		/*
		 * Creo un nuovo stage in cui viene visualizzata una listview con la lista delle aree verdi
		 */
		
		AnchorPane popUpPinze = null;
		try {
			popUpPinze = FXMLLoader.<AnchorPane>load(getClass().getResource(popUpURL), null, null, e -> {
				return new HomeLogin();
			});
		} catch (IOException e) {
			AlertPanel.saysError("ERRORE: nella load di HomeLogin", e);
		}
		
		Scene popUpScene = new Scene(popUpPinze);
		
		inserisciDatiPinzaStage.setScene(popUpScene);
		inserisciDatiPinzaStage.initModality(Modality.APPLICATION_MODAL);
		inserisciDatiPinzaStage.show();
		/*
		 * Popolamento della lista 
		 */
		/*List<AreaVerde> listaAreeVerdi = visualizzazioneController.visualizza(areaVerdeCerca.getText());
		List<String> listaNomi = new ArrayList<>();

		for (AreaVerde areaVerde : listaAreeVerdi) {
			listaNomi.add(areaVerde.getNome() + "\n" + areaVerde.getQuartiere());
		}

		ListView<String> listView = new ListView<>(FXCollections.observableArrayList(listaNomi));

		/*
		 * Lo stage si chiude quando viene cliccato un elemento della listview.
		 * Inoltre vengono riempiti i field nella ViewAggiuntaIntervento.
		 */
		/*listView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {

			selezionaAreaVerdeStage.close();*/
	}


	@FXML
	private void PopUpAggiungiPinzaHandler() {
		inserisciDatiPinzaStage.close();
		/*Pinza pinza = new Pinza(ID_DepositoField.getValue(), 0, AppartenenzaField.getValue(), CondizioneField.getValue());

		controllerPinze.aggiuntaNuovoStrumento(pinza);*/
	}
}
