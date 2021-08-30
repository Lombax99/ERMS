package main.java.controllers.interventi.gestioneIscritti;

import java.sql.SQLException;

import main.java.application.AlertPanel;
import main.java.persisters.iscritti.PersisterIscritti;

public class RimozioneIscrittoController {

	public boolean rimuovi(String codFisc)
	{
		
		//controllo parametri null
		if(codFisc.equalsIgnoreCase(null))
		{
			AlertPanel.saysInfo("ERRORE", "Il codice fiscale è null");
			return false;
		}
		
		
		//rimozione spazi inizio e fine stringa e controllo delle stringhe vuote
		if(codFisc.strip().isEmpty())
		{
			AlertPanel.saysInfo("ERRORE", "codFisc è vuoto");
			return false;
		}
		
		
		//controllo anti SQLinjection
		if(codFisc.contains("'"))
		{
			AlertPanel.saysInfo("ERRORE", "Il codice fiscale contiene un apice");
			return false;
		}
		
		//controllo lunghezza
		if(codFisc.length() != 16)
		{
			AlertPanel.saysInfo("ERRORE", "Codice Fiscale non valido");
			return false;
		}

		//controllo codFiscale ben formato
		codFisc = codFisc.toUpperCase();
		
		if(! codFisc.matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$"))
		{
			AlertPanel.saysInfo("ERRORE", "Codice Fiscale non valido");
			return false;
		}
		
		//evocazione persister normale
		try {
			if(PersisterIscritti.getInstance().rimuoviIscritto(codFisc))
			{
				return true;
			}
		} catch (SQLException e) {
			
			//lancio messaggio di errore
			AlertPanel.saysError("Errore nell'aggiunta DB iscritti", e);
		}


		//non dovrei mai arrivare qua
		AlertPanel.saysInfo("ERRORE", "qualcosa è andato storto nella rimuovi");
		return false;
	}
}
