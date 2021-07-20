package main.java.controllers.interventi.gestioneInterventi;

import java.sql.SQLException;
import java.time.LocalDate;
//import java.util.Iterator;	//serve per il codice commentato

import main.java.application.AlertPanel;
import main.java.models.intervento.Intervento;
import main.java.persisters.interventi.PersisterInterventi;

public class ModificaInterventoController {

	public boolean modifica(Intervento intervento) {
		
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
		 * controllo anti SQLInjection descrizione 
		 */
		
		//controllo anti SQLInjection		
		if(intervento.getDescrizioneValutativa().contains("'"))
		{
			intervento.getDescrizioneValutativa().replace("'", "`");
		}
		
		if(intervento.getGravità() > 5 || intervento.getGravità() < 1)
		{
			AlertPanel.saysInfo("ERRORE", "La gravità inserita è scorretta: deve essere compresa tra 1 e 5");
			return false;
		}
		
		//evocazione persister normale
			try {
				if(PersisterInterventi.getInstance().modificaIntervento(intervento))
				{
					return true;
				}
			} catch (SQLException e) {
				
				//lancio messaggio di errore
				AlertPanel.saysError("Errore nella modifica DB intervento", e);
			}
			
			//se l'aggiungi restituisce false
			AlertPanel.saysInfo("ERRORE", "qualcosa è andato storto nella modifica intervento");
	
		return false;
		
	}
}
