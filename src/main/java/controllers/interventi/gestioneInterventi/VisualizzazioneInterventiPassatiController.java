package main.java.controllers.interventi.gestioneInterventi;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.application.AlertPanel;
import main.java.persisters.interventi.PersisterInterventi;

public class VisualizzazioneInterventiPassatiController {
	
	public ResultSet visualizza(FiltroInterventi filtro) {
		
		ResultSet result = null;
		
		//evocazione persister
		try {
			result = PersisterInterventi.getInstance().visualizzaInterventi((filtro));
		} catch (SQLException e) {
			AlertPanel.saysError("Errore nella visualizzazione DB Interventi Passati", e);
		}
		
		return result;
	}

}
