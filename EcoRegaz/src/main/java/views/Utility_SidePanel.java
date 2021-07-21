package main.java.views;

import com.jfoenix.controls.JFXDrawer;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import main.java.application.AlertPanel;

/**
 * Classe che implementa i metodi per l'inizializzazione del side panel per passare da una schermata all'altra.
 *
 */
public class Utility_SidePanel {


	/**
	 * 1) Imposta l'handler di il exitButton: gli fa attivare e disattivare il sidePanel. <br>
	 * 2) Imposta l'elenco delle view dentro il sidePanel. <br>
	 * 
	 * @param exitButton
	 * @param sidePanel
	 */
	public static void initialize(Button exitButton, JFXDrawer sidePanel) {

		exitButton.setOnAction(e -> {
			if (sidePanel.isOpened()) {
				sidePanel.close();
			} else
				sidePanel.open();
		});

		ListView<String> listViewSidePanel = new ListView<String>();
		listViewSidePanel.setItems(FXCollections.observableArrayList("Home", "Interventi", "Rendicontazione", "Analisi", "Strumenti", "Depositi", "Iscritti"));
		sidePanel.setSidePane(listViewSidePanel);

		listViewSidePanel.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
			String selectedItem = listViewSidePanel.getSelectionModel().getSelectedItem();

			switch (selectedItem) {
			case "Home":
				GestoreInterfacce.getInstance().initHomePrincipale();
				break;
			case "Interventi":
				GestoreInterfacce.getInstance().initHomeInterventi();
				break;
			case "Rendicontazione":
				GestoreInterfacce.getInstance().initHomeRendicontazione();
				break;
			case "Analisi":
				GestoreInterfacce.getInstance().initHomeAnalisi();
				break;
			case "Strumenti":
				GestoreInterfacce.getInstance().initHomeStrumenti();
				break;
			case "Depositi":
				GestoreInterfacce.getInstance().initHomeDepositi();
				break;
			case "Iscritti":
				GestoreInterfacce.getInstance().initHomeIscritti();
				break;
			default:
				AlertPanel.saysError("ERRORE nel sidePanel", new IllegalArgumentException());
			}

		});

	}

}
