package main.java.views.interfacciaInterventi;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.controllers.gestioneAreeVerdi.VisualizzazioneAreeVerdiController;
import main.java.models.areaVerde.AreaVerde;
import main.java.views.Utility_SidePanel;

public class ViewAggiuntaIntervento implements Initializable {

	VisualizzazioneAreeVerdiController visualizzazioneController;
	AreaVerde areaVerdeSelezionata;


	@FXML
	void tastoAggiungiHandler(ActionEvent event) {

	}


	@FXML
	void tastoCerceAreaVerdeHandler(ActionEvent event) {
		/*
		 * Creo un nuovo stage in cui viene visualizzata una listview con la lista delle aree verdi
		 */
		Stage selezionaAreaVerdeStage = new Stage();

		/*
		 * Popolamento della lista 
		 */
		List<AreaVerde> listaAreeVerdi = visualizzazioneController.visualizza(areaVerdeCerca.getText());
		List<String> listaNomi = new ArrayList<>();

		for (AreaVerde areaVerde : listaAreeVerdi) {
			listaNomi.add(areaVerde.getNome() + "\n" + areaVerde.getQuartiere());
		}

		ListView<String> listView = new ListView<>(FXCollections.observableArrayList(listaNomi));

		/*
		 * Lo stage si chiude quando viene cliccato un elemento della listview.
		 * Inoltre vengono riempiti i field nella ViewAggiuntaIntervento.
		 */
		listView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {

			selezionaAreaVerdeStage.close();

			int indexSelezionato = listView.getSelectionModel().getSelectedIndex();
			areaVerdeSelezionata = listaAreeVerdi.get(indexSelezionato);

			nomeField.setText(areaVerdeSelezionata.getNome());
			quartiereField.setText(areaVerdeSelezionata.getQuartiere().toString());
		});

		Scene selezionaAreaVerdeScene = new Scene(listView);
		selezionaAreaVerdeStage.setScene(selezionaAreaVerdeScene);

		/*
		 * Opzione che permette di non poter cliccare al di fuori di questo stage
		 */
		selezionaAreaVerdeStage.initModality(Modality.APPLICATION_MODAL);

		selezionaAreaVerdeStage.show();
	}


	/**
	 * Metodo che viene invocato quando viene cliccato il tasto ENTER mentre si digita l'area verde.
	 */
	@FXML
	void cercaAreaVerdeENTER(KeyEvent event) throws InterruptedException {
		if (event.getCode() == KeyCode.ENTER) {
			tastoCerceAreaVerdeHandler(new ActionEvent());
		}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		/*
		 * Inizializzazione controller per la visualizza
		 */
		visualizzazioneController = new VisualizzazioneAreeVerdiController();

		/*
		 * Set up del sidePanel
		 */
		Utility_SidePanel.initialize(exitButton, sidePanel);


	}


	/*
	 * Definizione componenti grafici
	 */
	@FXML
	private TextField areaVerdeCerca;
	@FXML
	private TextArea descrizioneArea;
	@FXML
	private Spinner<Integer> gravitaSpinner;
	@FXML
	private TextField nomeField;
	@FXML
	private DatePicker dataPicker;
	@FXML
	private ListView<String> listaOperatori;
	@FXML
	private Button exitButton;
	@FXML
	private JFXDrawer sidePanel;
	@FXML
	private TextField quartiereField;


}
