package main.java.application;

import java.awt.Toolkit;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.views.GestoreInterfacce;

public class Main extends Application {

	/**
	 * 1.0.0.0: Prima versione
	 */
	public final static String VERSION = "1.0.0.0";

	public final static Integer MIN_WIDTH = 720;
	public final static Integer MIN_HIGHT = 480;

	@Override
	public void start(Stage primaryStage) {

		/*
		 * Impostazioni dello stage primario (primaryStage). Tale stage appare al centro
		 * dello schermo, non massimizzato e con dimensione massima pari alla dimensione
		 * massima dello schermo dell'user.
		 */
		primaryStage.setTitle("ERMS");

		// TODO: aggiungere l'icona del programma
		// primaryStage.getIcons().add(new Image("path"));

		primaryStage.setMinWidth(MIN_WIDTH);
		primaryStage.setMinHeight(MIN_HIGHT);
		primaryStage.setMaxHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		primaryStage.setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
		primaryStage.setMaximized(false);

		primaryStage.centerOnScreen();

		/*
		 * Passaggio del primaryStage al GestoreInterfacce
		 */
		GestoreInterfacce.getInstance().setPrimaryStage(primaryStage);

		/*
		 * Chiedo al GestoreInterfacce di visualizzare la HomeLogin
		 */
		GestoreInterfacce.getInstance().initHomeLogin();

		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
