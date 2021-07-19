package main.java.persisters.areeVerdi;

import java.sql.SQLException;
import java.util.List;

import main.java.models.areaVerde.AreaVerde;

/**
 * Classe che implementa i metodi per l'interazione col DB circa le aree verdi
 *
 */
public class PersisterAreeVerdi implements IPersisterAreeVerdi {

	/*
	 * Pattern Singleton
	 */
	private static PersisterAreeVerdi instance = null;

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
	
	@Override
	public List<AreaVerde> visualizzaAreeVerdi(String nomeAreaVerde) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean aggiuntaAreaVerde(AreaVerde areaVerde) {
		// TODO Auto-generated method stub
		return false;
	}
}
