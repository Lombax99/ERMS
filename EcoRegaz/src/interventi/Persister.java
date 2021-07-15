package interventi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Persister {

	public static void main(String[] args) throws SQLException {

		/*
		 * Accensione del database
		 */
		String dbName = "ERMS_DB";
		Connection connection = DriverManager.getConnection("jdbc:derby:" + dbName + ";create=true");

		System.out.println("Connected to and created database " + dbName);// TODO print inutile

		/*
		 * We want to control transactions manually. Autocommit is on by default in JDBC.
		 */
		connection.setAutoCommit(false);

		/*
		 * Creo lo statement
		 */
		Statement statement = connection.createStatement();

		statement.execute("CREATE TABLE INTERVENTI(ID_INTERVENTO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY," + "		AREAVERDE VARCHAR(254) NOT NULL)");
	}

}
