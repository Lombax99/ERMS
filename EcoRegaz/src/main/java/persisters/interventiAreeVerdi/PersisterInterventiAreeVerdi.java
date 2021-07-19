package main.java.persisters.interventiAreeVerdi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import main.java.controllers.interventi.gestioneInterventi.FiltroInterventi;
import main.java.models.intervento.Intervento;

public class PersisterInterventiAreeVerdi implements IPersisterInterventiAreeVerdi{
	
	
	/*
	 * Pattern Singleton
	 */
	private static PersisterInterventiAreeVerdi instance = null;

	/**
	 * Pattern Singleton. <br>
	 * L'eccezione viene lanciata perché nel costruttore qualcosa può andare storto.
	 * 
	 * @throws SQLException
	 */
	public static PersisterInterventiAreeVerdi getInstance() throws SQLException {
		if (instance == null) {
			instance = new PersisterInterventiAreeVerdi();
		}
		return instance;
	}
	
	
	@Override
	public ResultSet visualizzaInterventi(FiltroInterventi filtroInterventi) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean aggiuntaIntervento(Intervento intervento) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rimuoviIntervento(LocalDate date, String nomeAreaVerde) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modificaIntervento(Intervento intervento) {
		// TODO Auto-generated method stub
		return false;
	}

}
