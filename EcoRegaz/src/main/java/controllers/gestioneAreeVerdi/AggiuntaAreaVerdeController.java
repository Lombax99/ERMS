package main.java.controllers.gestioneAreeVerdi;

import java.sql.SQLException;
import java.util.List;

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
			areaVerde.setNome(areaVerde.getNome().replace("'", "`"));
		}
		
		//conversione nome ad uppercase
		areaVerde.getNome().toUpperCase();
		
		//controllo esistenza AreaVerde DB locale
		List<AreaVerde> check = null;
		try {
			check = PersisterAreeVerdi.getInstance().checkEsistenzaAreaVerde(areaVerde.getNome());
		} catch (SQLException e1) {
			//lancio messaggio di errore
			AlertPanel.saysError("Errore nella visualizza per check DB area verde", e1);
		}
		if(!check.isEmpty())
		{
			AlertPanel.saysInfo("ERRORE", "AreaVerde già presente nel DB");
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
