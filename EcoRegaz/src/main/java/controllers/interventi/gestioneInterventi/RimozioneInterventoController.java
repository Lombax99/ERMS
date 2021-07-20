package main.java.controllers.interventi.gestioneInterventi;

import java.sql.SQLException;
import java.time.LocalDate;

import main.java.application.AlertPanel;
import main.java.persisters.interventi.PersisterInterventi;

public class RimozioneInterventoController {

	public boolean rimuovi(LocalDate data, String nomeAreaVerde) {
		
		//controllo data non superiore al giorno corrente
		if(data.isAfter(LocalDate.now()) || data == null)
		{
			AlertPanel.saysInfo("ERRORE", "Data non valida");
			return false;
		}
		//controllo nomeAreaVerde non vuota e pulizia spazi vuoti
		if(nomeAreaVerde.strip().isEmpty() || nomeAreaVerde.equals(null))
		{
			AlertPanel.saysInfo("ERRORE", "Il nome dell'area verde inserita è vuota");
			return false;
		}
		
		/*
		 * TODO 
		 * controllo anti SQLInjection nomeAreaVerde 
		 */
		
		//evocazione persister normale
		try {
			if(PersisterInterventi.getInstance().rimuoviIntervento(data, nomeAreaVerde))
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
