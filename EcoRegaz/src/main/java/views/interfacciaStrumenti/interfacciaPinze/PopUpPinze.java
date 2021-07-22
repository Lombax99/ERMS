package main.java.views.interfacciaStrumenti.interfacciaPinze;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import main.java.application.AlertPanel;
import main.java.controllers.strumenti.deposito.ElencoDepositiController;
import main.java.models.strumenti.pinza.Appartenenza;
import main.java.models.strumenti.pinza.Condizione;

public class PopUpPinze implements Initializable {

	private Stage inserisciDatiPinzaStage;
	private ElencoDepositiController controllerDepositi;
	
	@FXML
	private ListView<Integer> ID_DepositoField;
	@FXML
	private ChoiceBox<Appartenenza> AppartenenzaField;
	@FXML
	private ChoiceBox<Condizione> CondizioneField;
	
	public PopUpPinze(Stage inserisciDatiPinze) {
		this.inserisciDatiPinzaStage = inserisciDatiPinze;
		this.controllerDepositi = new ElencoDepositiController();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for (Appartenenza appartenenza : Appartenenza.values()) {
			AppartenenzaField.getItems().add(appartenenza);
		}
		for (Condizione condizione : Condizione.values()) {
			CondizioneField.getItems().add(condizione);
		}
		
		List<Integer> listaId_Depositi = new ArrayList<>();
		ResultSet Depositi = controllerDepositi.elencoDepositi();
		try {
			while (Depositi.next()) {
				listaId_Depositi.add(Depositi.getInt(1));
			}
		} catch (SQLException e) {
			AlertPanel.saysError("ERRORE", e);
		}
		ID_DepositoField.getItems().addAll(listaId_Depositi);
		
		/*ID_DepositoField.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Integer> ov, String old_val, String new_val) -> {

			int indexSelezionato = listView.getSelectionModel().getSelectedIndex();
			areaVerdeSelezionata = listaAreeVerdi.get(indexSelezionato);

			nomeField.setText(areaVerdeSelezionata.getNome());
			quartiereField.setText(areaVerdeSelezionata.getQuartiere().toString());
		});*/
	}
	
	@FXML
	private void PopUpAggiungiPinzaHandler() {
		inserisciDatiPinzaStage.close();
		/*Pinza pinza = new Pinza(ID_DepositoField.getValue(), 0, AppartenenzaField.getValue(), CondizioneField.getValue());

		controllerPinze.aggiuntaNuovoStrumento(pinza);*/
	}
}