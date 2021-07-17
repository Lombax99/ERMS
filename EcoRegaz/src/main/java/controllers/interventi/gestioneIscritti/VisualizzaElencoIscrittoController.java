package main.java.controllers.interventi.gestioneIscritti;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.application.AlertPanel;
import main.java.persisters.iscritti.PersisterIscritti;

public class VisualizzaElencoIscrittoController {

	public ResultSet visualizza(FiltroIscritti filtro) {
		
		ResultSet result = null;
		
		//evocazione persister
		try {
			result = PersisterIscritti.getInstance().visualizzaIscritti(filtro);
		} catch (SQLException e) {
			AlertPanel.saysError("Errore nella visualizzazione DB iscritti", e);
		}
		
		return result;
	}
}
