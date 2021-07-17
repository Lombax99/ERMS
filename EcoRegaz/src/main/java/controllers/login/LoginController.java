package main.java.controllers.login;

import java.time.LocalDateTime;

import main.java.persisters.logPersister.PersisterLog;

public class LoginController {

	private PersisterLog persisterReference;
	/* Stringa fake value per il funzionamento del prototipo
	 * Sostituisce il file in cui verrà memorizzata la password reale*/
	private final String password="SuperUserPassword";
	
	
	/* Controlla il contenuto della stringa
	 * NB: Da cambiare quando verrà utilizzato un file crittato 
	 * 	   contentente la password reale*/
	public boolean verificaCredenziali(String text)
	{
		boolean value = text.contentEquals(password);
		this.scritturaEntry(value);
		return value;
	}

	
	public void scritturaEntry(boolean correttezza)
	{
		persisterReference.scritturaEntry(LocalDateTime.now(), correttezza);
	}
}


