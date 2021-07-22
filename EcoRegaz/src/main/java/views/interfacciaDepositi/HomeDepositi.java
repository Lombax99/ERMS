package main.java.views.interfacciaDepositi;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import main.java.application.AlertPanel;
import main.java.controllers.strumenti.deposito.*;
import main.java.models.deposito.Deposito;
import main.java.persisters.strumenti.deposito.PersisterDeposito;
import main.java.views.Utility_SidePanel;

public class HomeDepositi implements Initializable{

	private AggiuntaDepositoController aggDepController;
	private ElencoDepositiController viewDepController;
	private ModificaDepositoController modDepController;
	private RimozioneDepositoController elimDepController;
	
    @FXML
    private Button AggiungiDepositoButton;
	@FXML
	private TableView<ObservableList<String>> tabella;
	@FXML
	private TableColumn<ObservableList<String>, String> Id_DepColumn;
	@FXML
	private TableColumn<ObservableList<String>, String> DescrizioneColumn;
	@FXML
	private TableColumn<ObservableList<String>, String> NumPinzeColumn;
	@FXML
	private TableColumn<ObservableList<String>, String> QSacchiColumn;
	@FXML
	private TableColumn<ObservableList<String>, String> QGuantiColumn;
	@FXML
	private TableColumn<ObservableList<String>, String> ExtraColumn;
	@FXML
	private JFXDrawer sidePanel;
	@FXML
	private Button exitButton;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Utility_SidePanel.initialize(exitButton, sidePanel);
		
		// TODO da rimuovere
		try {
			PersisterDeposito.getInstance().aggiuntaDeposito(new Deposito(0, "descrizioneDep1", "molti strumenti"));
			PersisterDeposito.getInstance().aggiuntaDeposito(new Deposito(0, "descrizioneDep2", "pochi scrumenti"));
			PersisterDeposito.getInstance().aggiuntaDeposito(new Deposito(0, "descrizioneDep3", "POWER ALAAAAAANNNNN"));
		} catch (SQLException e1) {
			e1.printStackTrace();
		} // fin qui
		
		
		// riempio i dati della tabella
		ResultSet Depositi = viewDepController.elencoDepositi();


		Id_DepColumn.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
				// param.getValue() restituisce l'istanza ObservableList<String> di una particolare riga,
				// .get(1) prende il primo parametro della riga
				return new SimpleStringProperty(param.getValue().get(0).toString());
			}
		});

		DescrizioneColumn.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
				return new SimpleStringProperty(param.getValue().get(1).toString());
			}
		});

		NumPinzeColumn.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
				return new SimpleStringProperty(param.getValue().get(2).toString());
			}
		});

		QSacchiColumn.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
				return new SimpleStringProperty(param.getValue().get(3).toString());
			}
		});

		QGuantiColumn.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
				return new SimpleStringProperty(param.getValue().get(4).toString());
			}
		});

		ExtraColumn.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
				return new SimpleStringProperty(param.getValue().get(5).toString());
			}
		});



		// popolo la tabella
		ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

		// Iterate Row
		try {
			while (Depositi.next()) {

				ObservableList<String> row = FXCollections.observableArrayList();

				// Iterate Column
				for (int i = 1; i <= Depositi.getMetaData().getColumnCount(); i++) {

					row.add(Depositi.getString(i));
				}

				data.add(row);
			}

		} catch (SQLException e) {
			AlertPanel.saysError("ERRORE nella popolazione della tabella", e);
		}

		tabella.setItems(data);
	}
	
	public HomeDepositi() {
		this.aggDepController = new AggiuntaDepositoController();
		this.viewDepController = new ElencoDepositiController();
		this.modDepController = new ModificaDepositoController();
		this.elimDepController = new RimozioneDepositoController();
	}

	private void tastoAggiugniDepositoHandler()	{
		
	}
	
	private void rimuoviDepositoHandler()	{
		
	}
	
	private void modificaDepositoHandler() {
		
	}
	
	private void visualizza() {
		
	}
}
