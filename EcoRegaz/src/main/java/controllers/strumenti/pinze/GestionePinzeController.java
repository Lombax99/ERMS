package main.java.controllers.strumenti.pinze;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.application.AlertPanel;
import main.java.controllers.strumenti.IGestioneStrumentiController;
import main.java.models.strumenti.IStrumento;
import main.java.models.strumenti.pinza.Pinza;
import main.java.persisters.iscritti.PersisterIscritti;
import main.java.persisters.strumenti.pinze.PersisterPinze;

public class GestionePinzeController  implements IGestioneStrumentiController{



public boolean aggiuntaNuovoStrumento(Pinza strumento) {

try{
	if (PersisterPinze.getInstance().aggiuntaNuovaPinza(strumento))
	return true;
} catch(SQLException e) {
	AlertPanel.saysError("Errore nell'inserimento dello strumento nel database", e);
}
return false;
}


public boolean rimozioneStrumento(int ID_Pinza) {
	try{
		if (PersisterPinze.getInstance().rimozionePinza(ID_Pinza))
		return true;
	} catch(SQLException e) {
		AlertPanel.saysError("Errore nella rimozione dello strumento nel database", e);
	}
	return false;
	}


public ResultSet elencoStrumenti() {
	
	ResultSet result = null;
	
	try {
		result = PersisterPinze.getInstance().elencoPinze();
	} catch (SQLException e) {
		AlertPanel.saysError("Errore nella visualizzazione DB delle pinze", e);
	}
	
	return result;
}


public boolean modificaDatiStrumento(Pinza strumento) {
	try{
		if (PersisterPinze.getInstance().modificaDatiPinza(strumento))
		return true;
	} catch(SQLException e) {
		AlertPanel.saysError("Errore nella modifica dello strumento nel database", e);
	}
	return false;
	}

}
