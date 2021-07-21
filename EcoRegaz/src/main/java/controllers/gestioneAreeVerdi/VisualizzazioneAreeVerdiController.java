package main.java.controllers.gestioneAreeVerdi;

import java.sql.SQLException;
import java.util.List;

import main.java.application.AlertPanel;
import main.java.models.areaVerde.AreaVerde;
import main.java.persisters.areeVerdi.PersisterAreeVerdi;

public class VisualizzazioneAreeVerdiController {

	public List<AreaVerde> visualizza(String nomeAreaVerde) {

		/*
		 * Controlli di null
		 */
		if (nomeAreaVerde.strip().isEmpty() || nomeAreaVerde.equals(null)) {
			AlertPanel.saysInfo("ERRORE", "L'area verde inserita è vuota");
			return null;
		}

		/*
		 * Controllo anti SQLInjection
		 */
		if (nomeAreaVerde.contains("'")) {
			nomeAreaVerde = nomeAreaVerde.replace("'", "`");
		}
		
		/*
		 * Conversione nome ad uppercase
		 */
		nomeAreaVerde.toUpperCase();

		List<AreaVerde> result = null;

		// evocazione persister
		try {
			result = PersisterAreeVerdi.getInstance().visualizzaAreeVerdi(nomeAreaVerde);
		} catch (SQLException e) {
			AlertPanel.saysError("Errore nella visualizzazione DB Aree Verdi", e);
		}

		return result;
	}

}
