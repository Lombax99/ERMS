package main.java.views.interfacciaInterventi;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.application.AlertPanel;
import main.java.controllers.gestioneAreeVerdi.VisualizzazioneAreeVerdiController;
import main.java.controllers.interventi.gestioneIscritti.FiltroIscritti;
import main.java.controllers.interventi.gestioneIscritti.VisualizzaElencoIscrittoController;
import main.java.models.areaVerde.AreaVerde;
import main.java.views.Utility_SidePanel;

public class ViewAggiuntaIntervento implements Initializable {

	VisualizzazioneAreeVerdiController visualizzazioneAreeVerdiController;
	VisualizzaElencoIscrittoController visualizzazioneIscrittiController;
	AreaVerde areaVerdeSelezionata;
	List<String> listaCV;


	@FXML
	void tastoAggiungiHandler(ActionEvent event) {

		/*
		 * Controllo: dataPicker
		 */
		if (dataPicker.getValue() == null) {
			AlertPanel.saysInfo("ERRORE", "Data non inserita");
			return;
		}

		/*
		 * Controllo: descrizione
		 */
		if (descrizioneArea.getText() == null) {
			AlertPanel.saysInfo("ERRORE", "Descrizione non inserita");
			return;
		}

		/*
		 * Controllo: descrizione
		 */
		if (gravitaSpinner.getValue() == 0) {
			AlertPanel.saysInfo("ERRORE", "Gravità non inserita");
			return;
		}

		/*
		 * Controllo: descrizione
		 */
		if (listaCV.isEmpty()) {
			AlertPanel.saysInfo("ERRORE", "Nessun operatore selezionato");
			return;
		}

	}


	@FXML
	void tastoCerceAreaVerdeHandler(ActionEvent event) {
		/*
		 * Creo un nuovo stage in cui viene visualizzata una listview con la lista delle aree verdi
		 */
		Stage selezionaAreaVerdeStage = new Stage();
		/*
		 * Opzione che permette di non poter cliccare al di fuori di questo stage
		 */
		selezionaAreaVerdeStage.initModality(Modality.APPLICATION_MODAL);

		selezionaAreaVerdeStage.setWidth(500);

		/*
		 * Popolamento della lista 
		 */
		List<AreaVerde> listaAreeVerdi = visualizzazioneAreeVerdiController.visualizza(areaVerdeCerca.getText());
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
		 * Inizializzazione controller per la visualizza delle aree verdi
		 */
		visualizzazioneAreeVerdiController = new VisualizzazioneAreeVerdiController();

		/*
		 * Inizializzazione controller per la visualizza degli iscritti
		 */
		visualizzazioneIscrittiController = new VisualizzaElencoIscrittoController();

		/*
		 * Set up del dataPiker
		 */
		dataPicker.setValue(LocalDate.now());

		/*
		 * Set up di gravitaSpinner: va da 1 a 5, e parte da 0
		 */
		gravitaSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, 0));


		/*
		 * Set up di listaOperatori:
		 * 1) viene prelevato il ResultSet con tutti gli iscritti
		 * 2) 
		 */
		ResultSet tuttiIscritti = visualizzazioneIscrittiController.visualizza(new FiltroIscritti());
		try {
			while (tuttiIscritti.next()) {
				listaOperatori.getItems().add(tuttiIscritti.getString(2) + tuttiIscritti.getString(3));
			}
		} catch (SQLException e) {
			AlertPanel.saysError("ERRORE nella visualizza degli iscritti", e);
		}


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
