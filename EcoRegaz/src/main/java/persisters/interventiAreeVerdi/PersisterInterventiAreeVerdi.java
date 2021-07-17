package main.java.persisters.interventiAreeVerdi;

import java.sql.ResultSet;
import java.time.LocalDate;

import main.java.controllers.interventi.gestioneInterventi.FiltroInterventi;
import main.java.models.areaVerde.AreaVerde;
import main.java.models.intervento.Intervento;

public class PersisterInterventiAreeVerdi implements IPersisterInterventiAreeVerdi{
	
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
	public boolean aggiuntaAreaVerde(AreaVerde areaVerde) {
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
