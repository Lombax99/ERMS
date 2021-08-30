package main.java.views.interfacciaStrumenti;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.java.views.GestoreInterfacce;

public class HomeStrumenti {

	/**
	 * Metodo handler del bottone Guanti. <br>
	 */
	@FXML
	void openGuantiHandler(ActionEvent event) {
		
		GestoreInterfacce.getInstance().initViewGuanti();
	}


	/**
	 * Metodo handler del bottone Pinze. <br>
	 */
	@FXML
	void openPinzeHandler(ActionEvent event) {
		
		GestoreInterfacce.getInstance().initViewPinze();
	}


	/**
	 * Metodo handler del bottone Sacchi. <br>
	 */
	@FXML
	void openSacchiHandler(ActionEvent event) {

		GestoreInterfacce.getInstance().initViewSacchi();

	}

}
