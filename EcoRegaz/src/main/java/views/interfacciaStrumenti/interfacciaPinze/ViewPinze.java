package main.java.views.interfacciaStrumenti.interfacciaPinze;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import com.jfoenix.controls.JFXDrawer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.application.AlertPanel;
import main.java.controllers.strumenti.pinze.GestionePinzeController;
import main.java.models.strumenti.pinza.Appartenenza;
import main.java.models.strumenti.pinza.Condizione;
import main.java.views.Utility_SidePanel;
import main.java.views.interfacciaLogin.HomeLogin;

public class ViewPinze implements Initializable {

	public final static String popUpURL = "/main/java/views/interfacciaStrumenti/interfacciaPinze/PopUpPinze.fxml";

	private GestionePinzeController controllerPinze;

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
    private Button fineButton;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		/*
		 * Set up del sidePanel
		 */
		Utility_SidePanel.initialize(exitButton, sidePanel);

		controllerPinze = new GestionePinzeController();



		/*
		 * Prelevamento depositi
		 */
		// ResultSet result = gp.elencoStrumenti(new FiltroPinze());

		// List<String> listaDepositi = new ArrayList<>();
		/*while(result.next()) {
			listaDepositi.add(result.getString(4));
		}
		*/
		// listViewDepositi.setItems(FXCollections.observableArrayList(listaDepositi));
	}



	@FXML
	private void AggiungiPinzaHandler() {
		/*
		 * Creo un nuovo stage in cui viene visualizzata una listview con la lista delle aree verdi
		 */

		Stage inserisciDatiPinzaStage = new Stage();
		AnchorPane popUpPinze = null;
		try {
			popUpPinze = FXMLLoader.<AnchorPane>load(getClass().getResource(popUpURL), null, null, e -> {
				return new PopUpPinze(inserisciDatiPinzaStage);
			});
		} catch (IOException e) {
			AlertPanel.saysError("ERRORE: nella load di Aggiunta pinze", e);
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
}
