package main.java.controllers.interventi.gestioneInterventi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
//import java.util.Iterator;		//serve nei controlli commentati

import main.java.application.AlertPanel;
import main.java.controllers.gestioneAreeVerdi.AggiuntaAreaVerdeController;
import main.java.models.areaVerde.AreaVerde;
import main.java.models.intervento.Intervento;
import main.java.persisters.interventi.PersisterInterventi;

public class AggiuntaInterventoController {
	
	private AggiuntaAreaVerdeController aggAreaVerdeController;
	
	public AggiuntaInterventoController()
	{
		this.aggAreaVerdeController = new AggiuntaAreaVerdeController();
	}

	public boolean aggiuntaIntervento(Intervento intervento, AreaVerde areaVerde) {
		
		aggAreaVerdeController.aggiuntaAreaVerde(areaVerde);
		
		//controlli dell'imput
		if(intervento == null)
		{
			AlertPanel.saysInfo("ERRORE", "L'intervento inserito è null");
			return false;
		}
		//controllo data non superiore al giorno corrente
		if(intervento.getDataIntervento().isAfter(LocalDate.now()) || intervento.getDataIntervento() == null)
		{
			AlertPanel.saysInfo("ERRORE", "Data non valida");
			return false;
		}
		//controllo lista CodiciFiscali (CF generati automaticamente; solo per test)
		/*
		Iterator<String> CFIterator = intervento.getElencoCFPartecipanti().iterator();
		int counter = 0;
        while (CFIterator.hasNext()) {
        	counter++;
            if(! CFIterator.next().matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$"))
    		{
    			AlertPanel.saysInfo("ERRORE", "Il Codice Fiscale numero " + counter + " non è valido");
    			return false;
    		}
        }
        */
		
		if(intervento.getDescrizioneValutativa().strip().isEmpty() || intervento.getDescrizioneValutativa().equals(null))
		{
			AlertPanel.saysInfo("ERRORE", "La descrizione inserita è vuota");
			return false;
		}
		
		/*
		 * TODO 
		 * controllo anti SQLInjection descrizioneValutativa 
		 */
		
		if(intervento.getGravità() > 5 || intervento.getGravità() < 1)
		{
			AlertPanel.saysInfo("ERRORE", "La gravità inserita è scorretta: deve essere compresa tra 1 e 5");
			return false;
		}
		
		//controllo che non ci sia un intervento già presente con stessa data e luogo
		FiltroInterventi filtro = new FiltroInterventi();
		filtro.setDataFine(intervento.getDataIntervento());
		filtro.setDataInizio(intervento.getDataIntervento());
		filtro.setIdGestionaleAreaVerde(intervento.getId_GestionaleAreaVerde());
		
		ResultSet check;
		try {
			check = PersisterInterventi.getInstance().visualizzaInterventi(filtro);
		} catch (SQLException e2) {
			AlertPanel.saysError("Errore visualizza interventi del controllo aggiunta interventi per key duplicate", e2);
			return false;
		}
		try {
			if(!check.next())
			{
				AlertPanel.saysInfo("ERRORE", "Esiste un intervento con stessa data e luogo nel DB");
				return false;
			}
		} catch (SQLException e1) {
			AlertPanel.saysError("ERRORE funzione:next aggiuntaInterventoController", e1);
			return false;
		}
		
		//evocazione persister normale
			try {
				if(PersisterInterventi.getInstance().aggiuntaIntervento(intervento))
				{
					return true;
				}
			} catch (SQLException e) {
				
				//lancio messaggio di errore
				AlertPanel.saysError("Errore nell'aggiunta DB intervento", e);
			}
			
			//se l'aggiungi restituisce false
			AlertPanel.saysInfo("ERRORE", "qualcosa è andato storto nell'aggiungi intervento");
			return false;
	}
}
