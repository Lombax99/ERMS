package main.java.controllers.strumenti.guanti;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.application.AlertPanel;
import main.java.controllers.strumenti.IGestioneStrumentiController;
import main.java.models.strumenti.scatolaGuanti.ScatolaGuanti;
import main.java.persisters.strumenti.deposito.PersisterDeposito;

public class GestioneScatoleGuantiController  implements IGestioneStrumentiController{

			
	public boolean aggiuntaNuovoStrumento(ScatolaGuanti strumento) {
		
		
		
		try{
			if (PersisterScatoleGuanti.getInstance().aggiuntaNuovaScatola(strumento))
			return true;
		} catch(SQLException e) {
			AlertPanel.saysError("Errore nell'inserimento dello strumento nel database", e);
		}
	return false;
}

	
	public boolean rimozioneStrumento(int ID_ScatolaGuanti) {
		try{
			if (PersisterScatoleGuanti.getInstance().rimozioneScatola(ID_ScatolaGuanti))
			return true;
		} catch(SQLException e) {
			AlertPanel.saysError("Errore nella rimozione dello strumento nel database", e);
		}
	return false;
}

	
	public ResultSet elencoStrumenti() {
		
	}
	
	public boolean modificaDatiStrumento(ScatolaGuanti strumento) {
		try{
			if (PersisterSscatoleGuanti.getInstance().modificaDatiScatola(strumento))
			return true;
		} catch(SQLException e) {
			AlertPanel.saysError("Errore nella modifica dello strumento nel database", e);
		}
	return false;
}
		
}

