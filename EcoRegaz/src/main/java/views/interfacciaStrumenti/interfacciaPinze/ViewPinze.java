package main.java.views.interfacciaStrumenti.interfacciaPinze;

import java.io.IOException;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.java.application.AlertPanel;
import main.java.controllers.strumenti.pinze.FiltroPinze;
import main.java.controllers.strumenti.pinze.GestionePinzeController;
import main.java.models.deposito.Deposito;
import main.java.models.iscritto.Iscritto;
import main.java.models.strumenti.pinza.Appartenenza;
import main.java.models.strumenti.pinza.Condizione;
import main.java.models.strumenti.pinza.Pinza;
import main.java.persisters.iscritti.PersisterIscritti;
import main.java.persisters.strumenti.deposito.PersisterDeposito;
import main.java.persisters.strumenti.pinze.PersisterPinze;
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
	@FXML
	private TableView<ObservableList<String>> tabella;
	@FXML
	private TableColumn<ObservableList<String>, String> Id_PinzaColumn;
	@FXML
	private TableColumn<ObservableList<String>, String> CondizioneColumn;
	@FXML
	private TableColumn<ObservableList<String>, String> AppartenenzaColumn;
	@FXML
	private TableColumn<ObservableList<String>, String> Id_DepColumn;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		/*
		 * Set up del sidePanel
		 */
		Utility_SidePanel.initialize(exitButton, sidePanel);

		controllerPinze = new GestionePinzeController();

		// setUp delle colonne
		Id_PinzaColumn.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
				// param.getValue() restituisce l'istanza ObservableList<String> di una particolare riga,
				// .get(1) prende il primo parametro della riga
				return new SimpleStringProperty(param.getValue().get(0).toString());
			}
		});

		CondizioneColumn.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
				return new SimpleStringProperty(param.getValue().get(2).toString());
			}
		});

		AppartenenzaColumn.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
				return new SimpleStringProperty(param.getValue().get(1).toString());
			}
		});

		Id_DepColumn.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
				return new SimpleStringProperty(param.getValue().get(3).toString());
			}
		});

		this.stampa();
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
				return new PopUpPinze(controllerPinze);
			});
		} catch (IOException e) {
			AlertPanel.saysError("ERRORE: nella load di Aggiunta pinze", e);
		}

		Scene popUpScene = new Scene(popUpPinze);

		inserisciDatiPinzaStage.setScene(popUpScene);
		inserisciDatiPinzaStage.initModality(Modality.APPLICATION_MODAL);
		inserisciDatiPinzaStage.setOnCloseRequest(e -> {
			this.stampa();
			inserisciDatiPinzaStage.close();
		});
		inserisciDatiPinzaStage.show();
	}


	// stampa a video della tabella
	private void stampa() {
		// prendo tutte le pinze con un filtro vuoto
		ResultSet ElencoPinze = controllerPinze.elencoStrumenti(new FiltroPinze());


		// popolo la tabella
		ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

		// Iterate Row
		try {
			while (ElencoPinze.next()) {

				ObservableList<String> row = FXCollections.observableArrayList();

				// Iterate Column
				for (int i = 1; i <= ElencoPinze.getMetaData().getColumnCount(); i++) {

					row.add(ElencoPinze.getString(i));
				}
				data.add(row);
			}

		} catch (SQLException e) {
			AlertPanel.saysError("ERRORE nella popolazione della tabella", e);
		}
		tabella.setItems(data);
	}
}
