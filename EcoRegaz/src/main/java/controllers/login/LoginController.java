package main.java.controllers.login;

import java.time.LocalDateTime;

import main.java.persisters.logPersister.PersisterLog;

/**
 * Classe che si occupa di verificare la password e delegare a PersisterLog la scrittura del log.
 *
 */
public class LoginController {

	private PersisterLog persisterReference;		//unico persister non singleton
	
	/* 
	 * Stringa fake value per il funzionamento del prototipo
	 * Sostituisce il file in cui verrà memorizzata la password reale
	 * TODO
	 */
	private static final String password = "SuperUserPassword";

	/*
	 * Costruttore
	 */
	public LoginController() {
		persisterReference = new PersisterLog();
	}

	/* 
	 * Controlla il contenuto della stringa
	 * NB: Da cambiare quando verrà utilizzato un file crittato 
	 * 	   contentente la password reale
	 */
	public boolean verificaCredenziali(String text) {
		boolean value = text.contentEquals(password);
		this.scritturaEntry(value);
		return value;
	}

	public void scritturaEntry(boolean correttezza) {
		persisterReference.scritturaEntry(LocalDateTime.now(), correttezza);
	}
}
