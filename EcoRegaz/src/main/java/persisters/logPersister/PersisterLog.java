package main.java.persisters.logPersister;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import main.java.application.AlertPanel;

/**
 * Classe che si occupa della scrittura del log su file apposito
 */
public class PersisterLog {

	/**
	 * Istanza di File che identifica il logFile, salvato allo stesso livello del programma
	 */
	private static final File logFile = new File("./log");

	/**
	 * Metodo che scrive la entry nel formato: LocalDateTime successo/fallimento <br>
	 * sul file di log posizionato in "./log"
	 */
	public void scritturaEntry(LocalDateTime dataOra, boolean correttezza) {

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(logFile));

			String entry = dataOra.toString() + "\t";
			if (correttezza) {
				entry.concat("successo");
			} else {
				entry.concat("fallimento");
			}

			pw.append(entry);

		} catch (IOException e) {

			AlertPanel.saysError("ERRORE: nella scrittura del log", e);

		} finally {
			pw.close();
		}

	}

}
