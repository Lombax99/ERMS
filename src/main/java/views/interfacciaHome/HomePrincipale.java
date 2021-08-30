package main.java.views.interfacciaHome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.java.views.GestoreInterfacce;

/**
 * Classe che si occupa della gestione dei bottoni per le varie funzionalità. <br>
 * Ogni handler reindirizza il compito al GestoreInterfacce.
 *
 */
public class HomePrincipale {


	/**
	 * Metodo handler del bottone Interventi. <br>
	 */
	@FXML
	private void openInterventiHandler(ActionEvent event) {

		GestoreInterfacce.getInstance().initHomeInterventi();

	}


	/**
	 * Metodo handler del bottone Rendicontazione. <br>
	 */
	@FXML
	private void openRendicontazioneHandler(ActionEvent event) {

		GestoreInterfacce.getInstance().initHomeRendicontazione();

	}


	/**
	 * Metodo handler del bottone AreeVerdi. <br>
	 */
	@FXML
	private void openAnalisiAreeVerdiHandler(ActionEvent event) {

		GestoreInterfacce.getInstance().initHomeAnalisi();

	}


	/**
	 * Metodo handler del bottone Strumenti. <br>
	 */
	@FXML
	private void openStrumentiHandler(ActionEvent event) {

		GestoreInterfacce.getInstance().initHomeStrumenti();

	}


	/**
	 * Metodo handler del bottone Depositi. <br>
	 */
	@FXML
	private void openDepositiHandler(ActionEvent event) {

		GestoreInterfacce.getInstance().initHomeDepositi();
	}


	/**
	 * Metodo handler del bottone Iscritti. <br>
	 */
	@FXML
	private void openIscrittiHandler(ActionEvent event) {

		GestoreInterfacce.getInstance().initHomeIscritti();

	}

}
