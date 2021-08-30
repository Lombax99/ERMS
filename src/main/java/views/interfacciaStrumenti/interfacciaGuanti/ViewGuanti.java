package main.java.views.interfacciaStrumenti.interfacciaGuanti;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JFormattedTextField;

import com.jfoenix.controls.JFXDrawer;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.java.controllers.strumenti.deposito.ElencoDepositiController;
import main.java.controllers.strumenti.guanti.GestioneScatoleGuantiController;
import main.java.models.deposito.Deposito;
import main.java.models.strumenti.scatolaGuanti.ScatolaGuanti;
import main.java.models.strumenti.scatolaGuanti.Taglia;
import main.java.views.Utility_SidePanel;

public class ViewGuanti implements PropertyChangeListener, Initializable {

	private int numDefault = 0;
	private int ID_DepositoSelezionato;
	private Taglia taglia;
	private Deposito depSelezionato;
	private Taglia tagliaSelezionata;
	private GestioneScatoleGuantiController guantiControllerReference;
	private ElencoDepositiController depositoControllerReference;
	private JFormattedTextField quantitaField;

	@FXML
	Button aggiungi;
	@FXML
	Button rimuovi;
	@FXML
	private JFXDrawer sidePanel;
	@FXML
	private Button exitButton;


	public int getID_DepositoSelezionato() {
		return ID_DepositoSelezionato;
	}


	public void setID_DepositoSelezionato(int iD_DepositoSelezionato) {
		ID_DepositoSelezionato = iD_DepositoSelezionato;
	}


	public Taglia getTaglia() {
		return taglia;
	}


	public void setTaglia(Taglia taglia) {
		this.taglia = taglia;
	}


	public Deposito getDepSelezionato() {
		return depSelezionato;
	}


	public void setDepSelezionato(Deposito depSelezionato) {
		this.depSelezionato = depSelezionato;
	}


	public Taglia getTagliaSelezionata() {
		return tagliaSelezionata;
	}


	public void setTagliaSelezionata(Taglia tagliaSelezionata) {
		this.tagliaSelezionata = tagliaSelezionata;
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// inizializzazione
		//visualizza(); // aka initPane
		Utility_SidePanel.initialize(exitButton, sidePanel);
	}


	@SuppressWarnings("unused")//TODO togliere
	private void visualizza() {
		ObservableList<ResultSet> depositi = FXCollections.observableArrayList(depositoControllerReference.elencoDepositi());
		ListView<Deposito> depositoDaSelezionare = new ListView<Deposito>();
		depositoDaSelezionare.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Deposito> ov, Deposito old_val, Deposito new_val) -> {
			setDepSelezionato(depositoDaSelezionare.getSelectionModel().getSelectedItem());
		});


		ObservableList<Taglia> taglie = FXCollections.observableArrayList(Taglia.values());
		ListView<Taglia> tagliaDaSelezionare = new ListView<Taglia>(taglie);
		tagliaDaSelezionare.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Taglia> ov, Taglia old_val, Taglia new_val) -> {
			setTagliaSelezionata(tagliaDaSelezionare.getSelectionModel().getSelectedItem());
		});
		// TODO Va popolata la listView a partire dal resultSet;


		quantitaField = new JFormattedTextField();
		quantitaField.setValue(numDefault);
		quantitaField.addPropertyChangeListener("value", this);



	}


	@FXML
	private void tastoAggiungiScatolaGuantiHandler(ActionEvent event) {
		int numScatole = (int) quantitaField.getValue();
		setID_DepositoSelezionato(getDepSelezionato().getId_Deposito());
		setTaglia(getTagliaSelezionata());
		for (int i = 0; i < numScatole; i++)
			guantiControllerReference.aggiuntaNuovoStrumento(new ScatolaGuanti(ID_DepositoSelezionato, taglia, i)); // TODO , l'ID della scatola andrebbe dato solo una volta inserito nel DB
	}


	@FXML
	private void tastoRimuoviScatolaGuantiHandler(ActionEvent event) {
		int numScatole = (int) quantitaField.getValue();
		setID_DepositoSelezionato(getDepSelezionato().getId_Deposito());
		setTaglia(getTagliaSelezionata());
		for (int i = 0; i < numScatole; i++)
			guantiControllerReference.rimozioneStrumento(i); // TODO , l'ID della scatola andrebbe dato solo una volta inserito nel DB
	}


	public void propertyChange(PropertyChangeEvent e) {
		Object source = e.getSource();
		if (source == quantitaField)
			numDefault = ((Number) quantitaField.getValue()).intValue();
	}
}
