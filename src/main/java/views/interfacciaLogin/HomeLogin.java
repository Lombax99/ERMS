package main.java.views.interfacciaLogin;

import java.util.concurrent.TimeUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.java.application.AlertPanel;
import main.java.controllers.login.LoginController;
import main.java.views.GestoreInterfacce;

public class HomeLogin {

	LoginController loginController;

	/**
	 * Costruttore che inizializza LoginController.
	 */
	public HomeLogin() {
		loginController = new LoginController();
	}

	/**
	 * Handler del pulsante "Ok". <br>
	 * La password viene verificata via il loginController. <br>
	 * Se è falsa, appare un AlertPanel e il campo viene liberato. <br>
	 * Se è vera, viene eseguito il metodo initHomePrincipale(). <br>
	 * In entrambi i casi, viene scritto un log via logPersister.
	 */
	@FXML
	private void verificaCredenzialeHandler(ActionEvent event) throws InterruptedException {
		if (loginController.verificaCredenziali(passwordField.getText())) {
			GestoreInterfacce.getInstance().initHomePrincipale();
		} else {
			passwordField.clear();
			AlertPanel.saysInfo("PASSWORD ERRATA", "Riprovare tra 3 secondi");
			TimeUnit.SECONDS.sleep(3);
		}
	}

	/**
	 * Metodo che viene invocato quando viene cliccato il pulsante ENTER.
	 */
	@FXML
	void verificaCredenzialeENTER(KeyEvent event) throws InterruptedException {
		if (event.getCode() == KeyCode.ENTER) {
			verificaCredenzialeHandler(new ActionEvent());
		}
	}

	/*
	 * Dichiarazione dei componenti grafici
	 */
	@FXML
	PasswordField passwordField;

}
