package main.java.controllers.gestioneAreeVerdi;

import java.sql.SQLException;

import main.java.application.AlertPanel;
import main.java.models.areaVerde.AreaVerde;
import main.java.persisters.areeVerdi.PersisterAreeVerdi;

public class AggiuntaAreaVerdeController {

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
				
		if(areaVerde.getGeoPoint().strip().isEmpty() || areaVerde.getGeoPoint().equals(null))
		{
			AlertPanel.saysInfo("ERRORE", "Le coordinate dell'area verde sono vuote");
			return false;
		}

		if(areaVerde.getQuartiere() == null)
		{
			AlertPanel.saysInfo("ERRORE", "Il quartiere è null");
			return false;
		}
		
		//controllo anti SQLInjection
		if(areaVerde.getGeoPoint().contains("'"))
		{
			AlertPanel.saysInfo("ERRORE", "Le coordinate dell'area verde non possono contenere apici");
			return false;
		}

		//controllo anti SQLInjection		
		if(areaVerde.getNome().contains("'"))
		{
			areaVerde.getNome().replace("'", "`");
		}
		
		//conversione nome ad uppercase
		areaVerde.getNome().toUpperCase();
		
		
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
