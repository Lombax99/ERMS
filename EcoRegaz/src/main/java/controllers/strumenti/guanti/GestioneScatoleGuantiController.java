package main.java.controllers.strumenti.guanti;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.application.AlertPanel;
import main.java.controllers.strumenti.IGestioneStrumentiController;
import main.java.models.strumenti.IStrumento;
import main.java.models.strumenti.scatolaGuanti.ScatolaGuanti;
import main.java.persisters.strumenti.scatoleGuanti.PersisterScatoleGuanti;

public class GestioneScatoleGuantiController implements IGestioneStrumentiController {

	@Override
	public boolean aggiuntaNuovoStrumento(IStrumento strumento) {

		try {
			if (PersisterScatoleGuanti.getInstance().aggiuntaNuovaScatola((ScatolaGuanti) strumento))
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
			AlertPanel.saysError("Errore nella rimozione dello strumento nel database", e);
		}
		return false;
	}

	@Override
	public ResultSet elencoStrumenti() {
		return null;
	}

	@Override
	public boolean modificaDatiStrumento(IStrumento strumento) {
		// non deve essere implementata 
		return false;
	}

}
