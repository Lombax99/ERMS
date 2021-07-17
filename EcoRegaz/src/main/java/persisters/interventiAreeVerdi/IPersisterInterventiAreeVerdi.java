package main.java.persisters.interventiAreeVerdi;

import java.sql.ResultSet;
import java.time.LocalDate;

import main.java.controllers.interventi.gestioneInterventi.FiltroInterventi;
import main.java.models.areaVerde.AreaVerde;
import main.java.models.intervento.Intervento;

/**
 * Interfaccia che definisce i metodi per l'interazione col DB circa interventi e aree verdi
 */
public interface IPersisterInterventiAreeVerdi {
	
	public ResultSet visualizzaInterventi(FiltroInterventi filtroInterventi);
	
	public boolean aggiuntaIntervento(Intervento intervento);
	
	public boolean aggiuntaAreaVerde(AreaVerde areaVerde);
	
	public boolean rimuoviIntervento(LocalDate date, String nomeAreaVerde);
	
	public boolean modificaIntervento(Intervento intervento);

}
