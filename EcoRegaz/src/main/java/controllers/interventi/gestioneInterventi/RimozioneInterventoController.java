package main.java.controllers.interventi.gestioneInterventi;

import java.sql.SQLException;

import main.java.application.AlertPanel;
import main.java.persisters.interventi.PersisterInterventi;

public class RimozioneInterventoController {

	public boolean rimuovi(int idIntervento) {
		
		//evocazione persister normale
		try {
			if(PersisterInterventi.getInstance().rimuoviIntervento(idIntervento))
			{
				return true;
			}
		} catch (SQLException e) {
			
			//lancio messaggio di errore
			AlertPanel.saysError("Errore nella rimozione intervento DB", e);
		}
		
		//se l'aggiungi restituisce false
		AlertPanel.saysInfo("ERRORE", "qualcosa è andato storto nella rimozione intervento");

		return false;
	}
}