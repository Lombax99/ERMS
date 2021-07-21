package main.java.controllers.strumenti.sacchi;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.application.AlertPanel;
import main.java.controllers.IFiltro;
import main.java.controllers.strumenti.IGestioneStrumentiController;
import main.java.models.strumenti.IStrumento;
import main.java.models.strumenti.rotoloSacchi.RotoloSacchi;
import main.java.persisters.strumenti.rotoliSacchi.PersisterRotoliSacchi;

public class GestioneRotoliSacchiController implements IGestioneStrumentiController {
	
	
	public boolean rimozioneStrumento(int ID_RotoloSacchi) {
			try{
				if (PersisterRotoliSacchi.getInstance().rimozioneRotolo(ID_RotoloSacchi))
				return true;
			} catch(SQLException e) {
				AlertPanel.saysError("Errore nella rimozione del rotolo sacchi dal database", e);
			}
			return false;
		}
	
	
	public ResultSet elencoStrumenti(IFiltro filtro) {
		
		ResultSet result = null;
		
		try {
			result = PersisterRotoliSacchi.getInstance().elencoRotoli();
		} catch (SQLException e) {
			AlertPanel.saysError("Errore nella visualizzazione DB dei rotoli sacchi", e);
		}
		
		return result;
	}
	
	
	@Override
	public boolean aggiuntaNuovoStrumento(IStrumento strumento) {
		
		if(strumento == null)
		{
			AlertPanel.saysInfo("ERRORE", "Lo strumento inserito è null");
			return false;
		}
		
		try{
			if (PersisterRotoliSacchi.getInstance().aggiuntaNuovaRotolo((RotoloSacchi)strumento))
			{
				return true;
			}
		} catch(SQLException e) {
			AlertPanel.saysError("Errore nell'inserimento del rotolo sacchi nel database", e);
		}
		return false;
	}
	
	
	@Override
	public boolean modificaDatiStrumento(IStrumento strumento) {
		// non deve essere implementata
		return false;
	}

}

