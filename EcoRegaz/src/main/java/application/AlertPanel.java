package main.java.application;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AlertPanel {

	private static Alert alert;
	private static TextArea textArea;

	/**
	 * Alert di ERRORE. L'eccezione è il contentText.
	 * @param headerText
	 * @param exception
	 */
	public static void saysError(String headerText, Exception exception) {
		alert = new Alert(AlertType.ERROR);
		textArea = new TextArea();
		alert.setResizable(true);
		alert.setTitle("ERMS");
		
		alert.setHeaderText(headerText);

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		textArea.setText(sw.toString());
		textArea.setWrapText(true);
		textArea.setEditable(false);
		alert.getDialogPane().setContent(textArea);

		alert.showAndWait();
	}

	/**
	 * Alert di INFORMAZIONE
	 */
	public static void saysInfo(String headerText, String contentText) {
		alert = new Alert(AlertType.INFORMATION);
		alert.setResizable(true);
		textArea = new TextArea();
		alert.setTitle("ERMS");
		
		alert.setHeaderText(headerText);

		textArea.setText(contentText);
		textArea.setWrapText(true);
		textArea.setEditable(false);
		alert.getDialogPane().setContent(textArea);

		alert.showAndWait();
	}

	/**
	 * Alert di CONFERMA
	 * @return true se è selezionato buttonType1, false altrimenti
	 */
	public static boolean saysConfirm(String headerText, String contentText, ButtonType buttonType1, ButtonType buttonType2) {
		alert = new Alert(AlertType.CONFIRMATION);
		alert.setResizable(true);
		textArea = new TextArea();
		alert.setTitle("ERMS");
		
		alert.setHeaderText(headerText);

		textArea.setText(contentText);
		textArea.setWrapText(true);
		textArea.setEditable(false);
		alert.getDialogPane().setContent(textArea);
		
		alert.getButtonTypes().setAll(buttonType1, buttonType2);
		if (alert.showAndWait().get() == buttonType1) {
			return true;
		} else {
			return false;
		}
	}

}
