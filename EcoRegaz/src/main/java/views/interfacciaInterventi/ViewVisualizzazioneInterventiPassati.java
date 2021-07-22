package main.java.views.interfacciaInterventi;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import com.jfoenix.controls.JFXDrawer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import main.java.application.AlertPanel;
import main.java.controllers.interventi.gestioneInterventi.FiltroInterventi;
import main.java.controllers.interventi.gestioneInterventi.VisualizzazioneInterventiPassatiController;
import main.java.models.areaVerde.Quartiere;
import main.java.views.Utility_SidePanel;

public class ViewVisualizzazioneInterventiPassati implements Initializable {


	VisualizzazioneInterventiPassatiController visualizzazioneInterventiController;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		/*
		 * Set up del sidePanel
		 */
		Utility_SidePanel.initialize(exitButton, sidePanel);

		for (Quartiere quartiere : Quartiere.values()) {
			quartieri.getItems().add(quartiere);
		}

	}


	@FXML
	void cercaInterventiHandler(ActionEvent event) {

		FiltroInterventi filtro = new FiltroInterventi();

		filtro.setDataInizio(dataPickerInizio.getValue());
		filtro.setDataFine(dataPickerFine.getValue());
		filtro.setNomeArea(nomeAreaField.getText());
		filtro.setDescrizione(descrizioneInterventiField.getText());
		filtro.setQuartiere(quartieri.getValue());


		visualizzazioneInterventiController = new VisualizzazioneInterventiPassatiController();

		// prendo i dati
		ResultSet interventiFiltrati = visualizzazioneInterventiController.visualizza(filtro);


		dataColumn.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
				// param.getValue() restituisce l'istanza ObservableList<String> di una particolare riga,
				// .get(1) prende il primo parametro della riga
				return new SimpleStringProperty(param.getValue().get(1).toString());
			}
		});

		areaVerdeColumn.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
				return new SimpleStringProperty(param.getValue().get(5).toString());
			}
		});

		descrizioneColumn.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
				return new SimpleStringProperty(param.getValue().get(3).toString());
			}
		});

		gravitaColumn.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
				return new SimpleStringProperty(param.getValue().get(2).toString());
			}
		});

		quartiereColumn.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
				return new SimpleStringProperty(param.getValue().get(7).toString());
			}
		});

		numOpColumn.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
				return new SimpleStringProperty(param.getValue().get(8).toString());
			}
		});



		// popolo la tabella
		ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

		// Iterate Row
		try {
			while (interventiFiltrati.next()) {

				ObservableList<String> row = FXCollections.observableArrayList();

				// Iterate Column
				for (int i = 1; i <= interventiFiltrati.getMetaData().getColumnCount(); i++) {

					// qui, al posto di aggiungere la colonna dell'elenco iscritti, viene aggiunto il counter degli iscritti
					if (i == 9) {
						interventiFiltrati.getString(i);
						StringTokenizer str = new StringTokenizer(interventiFiltrati.getString(i), "&&&");
						int counter = 0;
						while (str.hasMoreTokens()) {
							str.nextToken();
							counter++;
						}
						row.add(String.valueOf(counter));

					} else {

						// in tutti gli altri casi viene preso il field corrispondente e tradotto a stringa
						row.add(interventiFiltrati.getString(i));
					}

				}

				data.add(row);
			}

		} catch (SQLException e) {
			AlertPanel.saysError("ERRORE nella popolazione della tabella", e);
		}

		tabella.setItems(data);

	}


	/*
	 * Definizione dei componenti grafici
	 */
	@FXML
	private JFXDrawer sidePanel;
	@FXML
	private Button exitButton;
	@FXML
	private TextField nomeAreaField;
	@FXML
	private TextField descrizioneInterventiField;
	@FXML
	private DatePicker dataPickerInizio;
	@FXML
	private DatePicker dataPickerFine;
	@FXML
	private ChoiceBox<Quartiere> quartieri;
	@FXML
	private TableView<ObservableList<String>> tabella;
	@FXML
	private TableColumn<ObservableList<String>, String> dataColumn;
	@FXML
	private TableColumn<ObservableList<String>, String> areaVerdeColumn;
	@FXML
	private TableColumn<ObservableList<String>, String> descrizioneColumn;
	@FXML
	private TableColumn<ObservableList<String>, String> gravitaColumn;
	@FXML
	private TableColumn<ObservableList<String>, String> quartiereColumn;
	@FXML
	private TableColumn<ObservableList<String>, String> numOpColumn;


}
