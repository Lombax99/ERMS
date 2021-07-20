package main.java.persisters.interventi;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.java.controllers.interventi.gestioneInterventi.FiltroInterventi;
import main.java.models.intervento.Intervento;
import main.java.persisters.MainDB;

public class PersisterInterventi implements IPersisterInterventi {

	/**
	 * Variabile che identifica la connessione con il DB.
	 */
	Connection connection;

	/**
	 * Statement unico per tutta la classe, al fine di mantenere vivi i ResultSet
	 */
	Statement statement;

	/**
	 * Stringa che contiene la creazione della tabella. <br>
	 * ATTENZIONE che ID_GESTIONALEAREAVERDE non fa reference della tabella sugli interventi, per evitare problemi in versioni future. <br>
	 * Ci possono stare al massimo 30 partecipanti. <br>
	 */
	private static String createTable = "CREATE TABLE INTERVENTI_TABLE (ID_INTERVENTO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " + " ID_GESTIONALEAREAVERDE INTEGER NOT NULL, "
				+ " DATA_INTERVENTO DATE NOT NULL, " + " GRAVITA INTEGER NOT NULL CHECK(GRAVITA > 0 AND GRAVITA <= 5), " + " DESCRIZIONE VARCHAR(2000) NOT NULL) "
				+ " ELENCO_PARTECIPANTI VARCHAR(550) NOT NULL, " + " UNIQUE (ID_GESTIONALEAREAVERDE, DATA_INTERVENTO) ";

	/**
	 * Pattern Singleton. <br>
	 * L'eccezione viene lanciata perché nel costruttore qualcosa può andare storto.
	 * 
	 * @throws SQLException
	 */
	public static PersisterInterventi getInstance() throws SQLException {
		if (instance == null) {
			instance = new PersisterInterventi();
		}
		return instance;
	}

	private static PersisterInterventi instance = null;

	/**
	 * Costruttore: <br>
	 * 1) Preleva la connessione da MainDB <br>
	 * 2) Crea lo Statement <br>
	 * 3) Verifica l'esistenza della tabella <br>
	 * 
	 * @throws SQLException
	 */
	private PersisterInterventi() throws SQLException {

		/*
		 * Prelevo la connessione da MainDB, il quale sicuramente è già stato inizializzato
		 */
		connection = MainDB.connection;

		if (connection == null) {
			throw new SQLException("Costruttore PersisterInterventi: Connessione con DB non stabilita");
		}

		/*
		 * Creazione dello statement che permette di eseguire query SQL
		 */
		statement = connection.createStatement();

		/*
		 * Verifica dell'esistenza della tabella degli Iscritti.
		 * Se non esiste, viene creata
		 */
		if (!MainDB.tableExists(connection, "UPDATE INTERVENTI_TABLE SET DESCRIZIONE = 'test' WHERE ID_INTERVENTO = -100 ")) {
			statement.execute(createTable);
		}

	}

	/**
	 * Metodo che restituisce un ResultSet in base al FiltroInterventi. <br>
	 * Il ResultSet è formato da: <br>
	 * I.ID_INTERVENTO, I.DATA_INTERVENTO, I.GRAVITA, I.DESCRIZIONE, A.ID_GESTIONALEAREAVERDE, A.NOME, A.COORDINATE, A.QUARTIERE <br>
	 * <br>
	 * Costruisce man mano la Stringa che costituisce la query SELECT. <br>
	 * La prima Stringa è un "WHERE ..." (che seleziona tutto) e le stringhe successive sono un "AND ..." <br>
	 * oppure una stringa vuota "", in base al FiltroInterventi. <br>
	 * 
	 * @throws SQLException
	 */
	@Override
	public ResultSet visualizzaInterventi(FiltroInterventi filtro) throws SQLException {
		// Stringa iniziale: è l'unico where, tutti gli altri sono o AND o stringa vuota
		String initial_String = "SELECT 	I.ID_INTERVENTO, I.DATA_INTERVENTO, I.GRAVITA, I.DESCRIZIONE, A.* " + "FROM 	INTERVENTI_TABLE I, AREEVERDI_TABLE A "
					+ "WHERE	I.ID_GESTIONALEAREAVERDE = A.ID_GESTIONALEAREAVERDE ";

		// data inizio: AND I:DATA_INTERVENTO >= '2021-05-09'
		String dataInizio_String = "";
		if (filtro.getDataInizio().isPresent()) {
			dataInizio_String = "AND I.DATA_INTERVENTO >= '" + filtro.getDataInizio().get().toString() + "'";
		}

		// data fine: AND I:DATA_INTERVENTO <= '2021-05-09'
		String dataFine_String = "";
		if (filtro.getDataFine().isPresent()) {
			dataFine_String = "AND I.DATA_INTERVENTO <= '" + filtro.getDataFine().get().toString() + "'";
		}

		// nome area verde: AND A.NOME LIKE '%ARCOBALENO%'
		String areaVerde_String = "";
		if (filtro.getNomeArea().isPresent()) {
			areaVerde_String = "AND A.NOME LIKE '%" + filtro.getNomeArea().get().toUpperCase() + "%'";
		}

		// quartiere: AND A.QUARTIERE = 'Navile'
		String quartiere_String = "";
		if (filtro.getQuartiere().isPresent()) {
			quartiere_String = "AND A.QUARTIERE = '" + filtro.getQuartiere().get().toString() + "'";
		}

		// descrizione: AND I.DESCRIZIONE LIKE '%tappi%'
		String descrizione_String = "";
		if (filtro.getDescrizione().isPresent()) {
			descrizione_String = "AND I.DESCRIZIONE LIKE '%" + filtro.getDescrizione().get() + "%'";
		}

		// idGestionale: AND A.ID_GESTIONALEAREAVERDE = 3
		String idGestionaleAreaVerde = "";
		if (filtro.getIdGestionaleAreaVerde().isPresent()) {
			idGestionaleAreaVerde = "AND A.ID_GESTIONALEAREAVERDE = " + filtro.getIdGestionaleAreaVerde().get().toString();
		}

		String selectString = initial_String + " " + dataInizio_String + " " + dataFine_String + " " + areaVerde_String + " " + quartiere_String + " " + descrizione_String + " "
					+ idGestionaleAreaVerde;

		// esecuzione effettiva della SELECT
		ResultSet resultSet = statement.executeQuery(selectString);

		return resultSet;
	}

	/**
	 * Metodo che aggiunge un Intervento al DB usando direttamente una query INSERT.
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean aggiuntaIntervento(Intervento intervento) throws SQLException {

		PreparedStatement psInsert = connection.prepareStatement("INSERT INTO INTERVENTI_TABLE (ID_GESTIONALEAREAVERDE, DATA_INTERVENTO, GRAVITA, DESCRIZIONE, ELENCO_PARTECIPANTI) VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

		// setto tutti i "?"
		psInsert.setInt(1, intervento.getId_GestionaleAreaVerde());
		psInsert.setDate(2, Date.valueOf(intervento.getDataIntervento()));
		psInsert.setInt(3, intervento.getGravità());
		psInsert.setString(4, intervento.getDescrizioneValutativa());
		psInsert.setString(5, intervento.getPartecipantiString());

		// esecuzione effettiva dell'INSERT
		if (psInsert.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psInsert.executeUpdate() non è andata a buon fine");
		}

		// rilascio delle risorse
		psInsert.close();

		return true;

	}

	/**
	 * Metodo che rimuove un Intervento dal DB usando direttamente una query DELETE
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean rimuoviIntervento(int id_Intervento) throws SQLException {

		PreparedStatement psDelete = connection.prepareStatement("DELETE FROM INTERVENTI_TABLE WHERE ID_INTERVENTO = ?");

		// setto tutti i "?"
		psDelete.setInt(1, id_Intervento);

		// esecuzione effettiva del DELETE
		if (psDelete.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psDelete.executeUpdate() non è andata a buon fine");
		}

		// rilascio delle risorse
		psDelete.close();

		return true;
	}

	/**
	 * Metodo che modifica un Intervento al DB usando direttamente una query UPDATE. <br>
	 * Va a sovrascrivere tutti i paramentri.
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean modificaIntervento(Intervento intervento) throws SQLException {
		PreparedStatement psUpdate = connection.prepareStatement("UPDATE INTERVENTI_TABLE SET GRAVITA = ?, DESCRIZIONE = ? WHERE ID_INTERVENTO = ?");

		// setto tutti i "?"
		psUpdate.setInt(1, intervento.getGravità());
		psUpdate.setString(2, intervento.getDescrizioneValutativa());

		// esecuzione effettiva dell'UPDATE
		if (psUpdate.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psUpdate.executeUpdate() non è andata a buon fine");
		}

		// rilascio delle risorse
		psUpdate.close();

		return true;
	}

}
