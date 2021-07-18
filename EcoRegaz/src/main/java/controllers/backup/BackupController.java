package main.java.controllers.backup;

import java.sql.CallableStatement;
import java.sql.SQLException;

import main.java.persisters.MainDB;

/**
 * Classe che implementa la funzionalità di backup. <br>
 * E' Singleton.
 */
public class BackupController {

	private boolean backupDaFare = false;


	/**
	 * Pattern Singleton. <br>
	 * L'eccezione viene lanciata perché nel costruttore qualcosa può andare storto.
	 * 
	 * @throws SQLException
	 */
	private static BackupController instance = null;
	public static BackupController getInstance() {
		if (instance == null) {
			instance = new BackupController();
		}
		return instance;
	}

	/**
	 * Metodo che imposta a true la variabile backupDaFare
	 */
	public void backupDaEseguire() {
		backupDaFare = true;
	}

	/**
	 * Metodo che viene chiamato SEMPRE in chiusura al programma. <br>
	 * Se il flag è alzato, parte il backup. Altrimenti non succede nulla. <br>
	 * Il backup viene effettuato nella cartella del programma.
	 * 
	 * http://db.apache.org/derby/docs/10.0/manuals/admin/hubprnt43.html
	 * @throws SQLException 
	 */
	public void avviaBackup() throws SQLException {

		if (backupDaFare) {
			String backupDirectory = System.getProperty("user.home") + "/AppData/Local/ERMS/backups";
			CallableStatement cs = MainDB.connection.prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)");
			cs.setString(1, backupDirectory);
			cs.execute();
		}

	}

}
