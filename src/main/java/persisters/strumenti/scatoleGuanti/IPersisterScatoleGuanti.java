package main.java.persisters.strumenti.scatoleGuanti;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.models.strumenti.scatolaGuanti.ScatolaGuanti;

/**
 * Interfaccia che definisce i metodi per l'interazione con DB circa i guanti
 *
 */
public interface IPersisterScatoleGuanti {

	public boolean aggiuntaNuovaScatola(ScatolaGuanti scatola) throws SQLException;

	public boolean rimozioneScatola(int id_ScatolaGuanti) throws SQLException;

	public ResultSet elencoScatole() throws SQLException;

	//public boolean modificaDatiScatola(ScatolaGuanti scatola) throws SQLException;

	//public boolean rimozioneScatolaTagliaSpecifica(int id_Deposito, Taglia taglia) throws SQLException;

}
