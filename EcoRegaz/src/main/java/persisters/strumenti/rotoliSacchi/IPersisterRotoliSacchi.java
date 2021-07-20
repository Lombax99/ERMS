package main.java.persisters.strumenti.rotoliSacchi;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.models.strumenti.rotoloSacchi.RotoloSacchi;

/**
 * Interfaccia che definisce i metodi per l'interazione con DB circa i sacchi
 *
 */
public interface IPersisterRotoliSacchi {

	public ResultSet elencoRotoli() throws SQLException;

	public boolean aggiuntaNuovaRotolo(RotoloSacchi rotolo) throws SQLException;

	public boolean rimozioneRotolo(int id_Strumento) throws SQLException;
}
