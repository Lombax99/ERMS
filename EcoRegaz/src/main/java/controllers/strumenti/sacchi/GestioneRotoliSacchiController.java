package main.java.controllers.strumenti.sacchi;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.application.AlertPanel;
import main.java.controllers.strumenti.IGestioneStrumentiController;
import main.java.models.strumenti.rotoloSacchi.RotoloSacchi;
import main.java.persisters.iscritti.PersisterIscritti;

public class GestioneRotoliSacchiController implements IGestioneStrumentiController {

	
public boolean aggiuntaNuovoStrumento(RotoloSacchi strumento) {

try{
	if (PersisterRotoloSacchi.getInstance().aggiuntaNuovoRotolo(strumento))
	return true;
} catch(SQLException e) {
	AlertPanel.saysError("Errore nell'inserimento dello strumento nel database", e);
}
return false;
}


public boolean rimozioneStrumento(int ID_RotoloSacchi) {
try{
	if (PersisterRotoloSacchi.getInstance().rimozioneRotolo(ID_RotoloSacchi))
	return true;
} catch(SQLException e) {
	AlertPanel.saysError("Errore nella rimozione dello strumento nel database", e);
}
return false;
}


public ResultSet elencoStrumenti() {
	
	ResultSet result = null;
	
	try {
		result = PersisterRotoloSacchi.getInstance().elencoRotoli();
	} catch (SQLException e) {
		AlertPanel.saysError("Errore nella visualizzazione DB dei rotoli", e);
	}
	
	return result;
}

}

