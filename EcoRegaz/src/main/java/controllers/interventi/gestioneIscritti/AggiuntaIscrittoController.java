package main.java.controllers.interventi.gestioneIscritti;

import java.sql.SQLException;

import main.java.application.AlertPanel;
import main.java.models.iscritto.Iscritto;
import main.java.persisters.iscritti.*;

public class AggiuntaIscrittoController {
	
	public boolean aggiungi(Iscritto iscritto) {
		
		//controllo parametri null
		if(iscritto.getNome().equals(null) || iscritto.getCognome().equals(null) || iscritto.getCodFisc().equalsIgnoreCase(null))
		{
			AlertPanel.saysInfo("ERRORE", "Uno dei campi inseriti è null");
			return false;
		}
		
		
		//rimozione spazi inizio e fine stringa e controllo delle stringhe vuote
		if(iscritto.getNome().strip().isEmpty() || iscritto.getCognome().strip().isEmpty() || iscritto.getCodFisc().strip().isEmpty())
		{
			AlertPanel.saysInfo("ERRORE", "Uno dei campi inseriti è vuoto");
			return false;
		}
		
		
		//controllo anti SQLinjection
		if(iscritto.getNome().contains("'") || iscritto.getCognome().contains("'") || iscritto.getCodFisc().contains("'"))
		{
			AlertPanel.saysInfo("ERRORE", "Uno dei campi inseriri contiene un apice");
			return false;
		}
		
		
		//controllo lunghezza stringhe
		if(iscritto.getNome().length() > 15)
		{
			AlertPanel.saysInfo("ERRORE", "Nome troppo lungo");
			return false;
		}
		if(iscritto.getCognome().length() > 20)
		{
			AlertPanel.saysInfo("ERRORE", "Cognome troppo lungo");
			return false;
		}
		if(iscritto.getCodFisc().length() != 16)
		{
			AlertPanel.saysInfo("ERRORE", "Codice Fiscale non valido");
			return false;
		}

		
		//Conversione ad UpperCase nome e cognome
		iscritto.setNome(iscritto.getNome().toUpperCase());
		iscritto.setCognome(iscritto.getCognome().toUpperCase());
		iscritto.setCodFisc(iscritto.getCodFisc().toUpperCase());
		
		if(! iscritto.getCodFisc().matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$"))
		{
			AlertPanel.saysInfo("ERRORE", "Codice Fiscale non valido");
			return false;
		}
		
		//evocazione persister normale
		try {
			if(PersisterIscritti.getInstance().aggiuntaIscritto(iscritto))
			{
				return true;
			}
		} catch (SQLException e) {
			
			//lancio messaggio di errore
			AlertPanel.saysError("Errore nell'aggiunta DB iscritti", e);
		}
		
		//non dovrei mai arrivare qua
		AlertPanel.saysInfo("ERRORE", "qualcosa è andato storto nell'aggiungi");
		return false;
	}
}
