package main.java.persisters.areeVerdi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import main.java.application.AlertPanel;
import main.java.models.areaVerde.AreaVerde;
import main.java.models.areaVerde.Quartiere;
import main.java.persisters.MainDB;

/**
 * Classe che implementa i metodi per l'interazione col DB circa le aree verdi
 *
 */
public class PersisterAreeVerdi implements IPersisterAreeVerdi {

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
	 */
	private static String createTable = "CREATE TABLE AREEVERDI_TABLE (ID_GESTIONALEAREAVERDE INTEGER NOT NULL PRIMARY KEY, " + " NOME VARCHAR(100) NOT NULL, " + " COORDINATE VARCHAR(50) NOT NULL, "
				+ " QUARTIERE VARCHAR(30) NOT NULL CHECK(QUARTIERE = 'San Donato - San Vitale' " + " OR QUARTIERE = 'Borgo Panigale - Reno' " + " OR QUARTIERE = 'Navile' "
				+ " OR QUARTIERE = 'Savena' " + " OR QUARTIERE = 'Porto - Saragozza' " + " OR QUARTIERE = 'Santo Stefano' " + " ))";

	/**
	 * Pattern Singleton. <br>
	 * L'eccezione viene lanciata perché nel costruttore qualcosa può andare storto.
	 * 
	 * @throws SQLException
	 */
	public static PersisterAreeVerdi getInstance() throws SQLException {
		if (instance == null) {
			instance = new PersisterAreeVerdi();
		}
		return instance;
	}

	private static PersisterAreeVerdi instance = null;

	/**
	 * Costruttore: <br>
	 * 1) Preleva la connessione da MainDB <br>
	 * 2) Crea lo Statement <br>
	 * 3) Verifica l'esistenza della tabella <br>
	 * 
	 * @throws SQLException
	 */
	private PersisterAreeVerdi() throws SQLException {

		/*
		 * Prelevo la connessione da MainDB, il quale sicuramente è già stato inizializzato
		 */
		connection = MainDB.connection;

		if (connection == null) {
			throw new SQLException("Costruttore PersisterAreeVerdi: Connessione con DB non stabilita");
		}

		/*
		 * Creazione dello statement che permette di eseguire query SQL
		 */
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

		/*
		 * Verifica dell'esistenza della tabella degli Iscritti.
		 * Se non esiste, viene creata
		 */
		if (!MainDB.tableExists(connection, "UPDATE AREEVERDI_TABLE SET NOME = 'test' WHERE ID_GESTIONALEAREAVERDE = -100 ")) {
			statement.execute(createTable);
		}

	}

	/**
	 * Metodo che prima cerca l'area verde all'interno del database locale <br>
	 * altrimenti cerca nel database online.
	 * 
	 * @throws SQLException
	 */
	@Override
	public List<AreaVerde> visualizzaAreeVerdi(String nomeAreaVerde) throws SQLException {

		// ricerca del nome nel database locale
		String selectString = "SELECT *		FROM AREEVERDI_TABLE	WHERE NOME LIKE '%" + nomeAreaVerde + "%'";

		// esecuzione effettiva della SELECT
		ResultSet resultSet = statement.executeQuery(selectString);

		List<AreaVerde> lista = new ArrayList<>();

		while (resultSet.next()) {
			lista.add(new AreaVerde(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), Quartiere.valueOf(resultSet.getString(4))));
		}

		// se la lista è vuota, effettua la richiesta ad database online
		if (lista.isEmpty()) {
			try {
				lista = richiestaOnline(nomeAreaVerde);
			} catch (IOException e) {
				AlertPanel.saysError("ERRORE nella ricerca online delle AreeVerdi", e);
			}
		}

		return lista;

	}

	/**
	 * Metodo che richiede le aree verdi al database online data una stringa. <br>
	 * 1) Crea un URL con la richiesta adattata alle sole colonne interessanti. <br>
	 * 2) Apre la connessione (si chiude poi da sola) <br>
	 * 3) Legge con un semplice bufferedReader le entry e le salva in una stringa in formato JSON <br>
	 * 4) Crea un oggetto JSON con all'interno la stringa <br>
	 * 5) Preleva l'array delle entry contenuto nel JSONObject <br>
	 * 6) Scorre l'array e per ogni entry salva i dati nella lista finale. <br>
	 * 
	 * Documentazione sui field dell'URL: https://help.opendatasoft.com/apis/ods-search-v1/#query-language
	 * 
	 * q="nomeAreaVerde" If a given word, or compounds, is surrounded with double quotes, only exact matches are returned (modulo an accent and case insensitive match)
	 * 
	 * rows=9999 Massimo numero di righe
	 * 
	 * sort=-cod_ug Sort delle entry. In ordine ascendente per codice gestionale
	 * 
	 * fields=cod_ug,nome,quartiere,geo_point_2d Colonne da estrapolare
	 * 
	 * @param nomeAreaVerde
	 * @return
	 * @throws IOException
	 */
	private List<AreaVerde> richiestaOnline(String nomeAreaVerde) throws IOException {

		/*
		 * 1) Crea un URL con la richiesta adattata alle sole colonne interessanti.
		 */
		URL url = new URL("https://opendata.comune.bologna.it/api/records/1.0/search/?dataset=aree-verdi&q=" + nomeAreaVerde + "&rows=9999&sort=-cod_ug&fields=cod_ug,nome,quartiere,geo_point_2d");

		/*
		 * 2) Apre la connessione (si chiude poi da sola)
		 */
		URLConnection urlConnection = url.openConnection();

		/*
		 * 3) Legge con un semplice bufferedReader le entry e le salva in una stringa
		 */
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		StringBuilder content = new StringBuilder();
		String line = "";
		while ((line = bufferedReader.readLine()) != null) {
			content.append(line + "\n");
		}
		bufferedReader.close();

		/*
		 * 4) Crea un oggetto JSON con all'interno la stringa 
		 */
		JSONObject obj = new JSONObject(content.toString());

		/*
		 * 5) Preleva l'array delle entry contenuto nel JSONObject
		 */
		JSONArray array = new JSONArray(obj.getJSONArray("records"));

		/*
		 * 6) Scorre l'array e per ogni entry salva i dati nella lista finale.
		 */
		List<AreaVerde> lista = new ArrayList<>();
		array.forEach(area_verde -> {
			JSONObject fields = (JSONObject) ((JSONObject) area_verde).get("fields");
			lista.add(new AreaVerde((int) fields.get("cod_ug"), String.valueOf(fields.get("nome")), String.valueOf(fields.get("geo_point_2d")),
						Quartiere.toEnum(String.valueOf(fields.get("quartiere")))));
		});

		return lista;
	}

	
	/**
	 * Metodo che aggiunge una Area Verde al DB usando direttamente una query INSERT.
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean aggiuntaAreaVerde(AreaVerde areaVerde) throws SQLException {
		PreparedStatement psInsert = connection.prepareStatement("INSERT INTO AREEVERDI_TABLE (ID_GESTIONALEAREAVERDE, NOME, COORDINATE, QUARTIERE) VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

		// setto tutti i "?"
		psInsert.setInt(1, areaVerde.getId_GestionaleAreaVerde());
		psInsert.setString(2, areaVerde.getNome());
		psInsert.setString(3, areaVerde.getGeoPoint());
		psInsert.setString(4, areaVerde.getQuartiere().toString());

		// esecuzione effettiva dell'INSERT
		if (psInsert.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psInsert.executeUpdate() non è andata a buon fine");
		}

		// rilascio delle risorse
		psInsert.close();

		return true;
	}
}
