package main.java.controllers.strumenti.guanti;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.application.AlertPanel;
import main.java.controllers.IFiltro;
import main.java.controllers.strumenti.IGestioneStrumentiController;
import main.java.models.strumenti.IStrumento;
import main.java.models.strumenti.scatolaGuanti.ScatolaGuanti;
import main.java.persisters.strumenti.scatoleGuanti.PersisterScatoleGuanti;

public class GestioneScatoleGuantiController implements IGestioneStrumentiController {

	@Override
	public boolean aggiuntaNuovoStrumento(IStrumento strumento) {
		
		if(strumento == null)
		{
			AlertPanel.saysInfo("ERRORE", "Lo strumento inserito è null");
			return false;
		}
		
		ScatolaGuanti scatola = (ScatolaGuanti)strumento;
		
		if(scatola.getTaglia() == null)
		{
			AlertPanel.saysInfo("ERRORE", "La taglia inserita è null");
			return false;
		}
		
		try {
			if (PersisterScatoleGuanti.getInstance().aggiuntaNuovaScatola(scatola))
				return true;
		} catch (SQLException e) {
			AlertPanel.saysError("Errore nell'inserimento dello strumento nel database", e);
		}
		return false;
	}

	@Override
	public boolean rimozioneStrumento(int ID_ScatolaGuanti) {
		
		try {
			if (PersisterScatoleGuanti.getInstance().rimozioneScatola(ID_ScatolaGuanti))
				return true;
		} catch (SQLException e) {
			AlertPanel.saysError("Errore nella rimozione della scatola guanti nel database", e);
		}
		return false;
	}

	@Override
	public ResultSet elencoStrumenti(IFiltro filtro) {
		
		ResultSet result = null;
		
		try {
			result = PersisterScatoleGuanti.getInstance().elencoScatole();
		} catch (SQLException e) {
			AlertPanel.saysError("Errore nella visualizzazione delle scatole guanti dal database", e);
		}
		
		return result;
	}

	@Override
	public boolean modificaDatiStrumento(IStrumento strumento) {
		// non deve essere implementata
		return false;
	}
 
}
