package main.java.persisters.interventi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import main.java.controllers.interventi.gestioneInterventi.FiltroInterventi;
import main.java.models.intervento.Intervento;

public class PersisterInterventi implements IPersisterInterventi{
	
	
	/*
	 * Pattern Singleton
	 */
	private static PersisterInterventi instance = null;

	/**
	 * Pattern Singleton. <br>
	 * L'eccezione viene lanciata perch� nel costruttore qualcosa pu� andare storto.
	 * 
	 * @throws SQLException
	 */
	public static PersisterInterventi getInstance() throws SQLException {
		if (instance == null) {
			instance = new PersisterInterventi();
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