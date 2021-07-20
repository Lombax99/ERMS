package main.java.controllers.interventi.gestioneInterventi;

import java.sql.SQLException;
import java.time.LocalDate;
//import java.util.Iterator;		//serve nei controlli commentati

import main.java.application.AlertPanel;
import main.java.models.areaVerde.AreaVerde;
import main.java.models.intervento.Intervento;
import main.java.persisters.areeVerdi.PersisterAreeVerdi;
import main.java.persisters.interventi.PersisterInterventi;

public class AggiuntaInterventoController {

	public boolean aggiuntaIntervento(Intervento intervento) {
		
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
	
	
	
	
	public boolean aggiuntaAreaVerde(AreaVerde areaVerde) {
		
		//controlli dell'imput
		if(areaVerde == null)
		{
			AlertPanel.saysInfo("ERRORE", "L'area verde inserita è null");
			return false;
		}
		if(areaVerde.getNome().strip().isEmpty() || areaVerde.getNome().equals(null))
		{
			AlertPanel.saysInfo("ERRORE", "L'area verde inserita è vuota");
			return false;
		}
		
		/*
		 * TODO 
		 * controllo anti SQLInjection nome areaVerde 
		 */
		
		if(areaVerde.getGeoPoint().strip().isEmpty() || areaVerde.getGeoPoint().equals(null))
		{
			AlertPanel.saysInfo("ERRORE", "Le coordinate dell'area verde sono vuote");
			return false;
		}
		//controllo anti SQLInjection
		if(areaVerde.getGeoPoint().contains("'"))
		{
			AlertPanel.saysInfo("ERRORE", "Le coordinate dell'area verde non possono contenere apici");
			return false;
		}
		
		if(areaVerde.getQuartiere() == null)
		{
			AlertPanel.saysInfo("ERRORE", "Il quartiere è null");
			return false;
		}
		
		
		//evocazione persister normale
		try {
			if(PersisterAreeVerdi.getInstance().aggiuntaAreaVerde(areaVerde))
			{
				return true;
			}
		} catch (SQLException e) {
			
			//lancio messaggio di errore
			AlertPanel.saysError("Errore nell'aggiunta DB area verde", e);
		}
		
		//se l'aggiungi restituisce false
		AlertPanel.saysInfo("ERRORE", "qualcosa è andato storto nell'aggiungi area verde");
		return false;
	}
}
