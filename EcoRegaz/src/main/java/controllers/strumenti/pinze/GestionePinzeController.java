package main.java.controllers.strumenti.pinze;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.application.AlertPanel;
import main.java.controllers.IFiltro;
import main.java.controllers.strumenti.IGestioneStrumentiController;
import main.java.models.strumenti.IStrumento;
import main.java.models.strumenti.pinza.Pinza;
import main.java.persisters.strumenti.pinze.PersisterPinze;

public class GestionePinzeController  implements IGestioneStrumentiController{
 
	@Override
	public boolean rimozioneStrumento(int ID_Pinza) {
		try{
			if (PersisterPinze.getInstance().rimozionePinza(ID_Pinza))
			{
				return true;
			}
		} catch(SQLException e) {
			AlertPanel.saysError("Errore nella rimozione dello strumento nel database", e);
		}
		return false;
	}

	@Override
	public ResultSet elencoStrumenti(IFiltro filtro) {
		
		ResultSet result = null;
		
		if(filtro == null)
		{
			AlertPanel.saysInfo("ERRORE", "Il filtro inserito � null");
			return result;
		}
		
		try {
			result = PersisterPinze.getInstance().elencoPinze((FiltroPinze)filtro);
		} catch (SQLException e) {
			AlertPanel.saysError("Errore nella visualizzazione DB delle pinze", e);
		}
		
		return result;
	}

	@Override
	public boolean aggiuntaNuovoStrumento(IStrumento strumento) {
		
		if(strumento == null)
		{
			AlertPanel.saysInfo("ERRORE", "Lo strumento inserito � null");
			return false;
		}
		
		Pinza pinza = (Pinza)strumento;
		
		if(pinza.getCondizione() == null)
		{
			AlertPanel.saysInfo("ERRORE", "La condizione inserita � null");
			return false;
		}
		
		if(pinza.getAppartenenza() == null)
		{
			AlertPanel.saysInfo("ERRORE", "L'appartenenza inserita � null");
			return false;
		}
		
		/*
		 * TODO
		 * cambiare per un controllo che verifichi se l'id � stato selezionato nella view o no
		 * per ora ho messo questo ipotizzando che non ci siano mai ID negativi nei depositi
		 */
		if(pinza.getId_Deposito() < 0)
		{
			AlertPanel.saysInfo("ERRORE", "Selezionare un Id_Deposito");
			return false;
		}
					
		try{
			if (PersisterPinze.getInstance().aggiuntaNuovaPinza(pinza))
			return true;
		} catch(SQLException e) {
			AlertPanel.saysError("Errore nell'inserimento della pinza nel database", e);
		}
		return false;
	}


	@Override
	public boolean modificaDatiStrumento(IStrumento strumento) {
		
		if(strumento == null)
		{
			AlertPanel.saysInfo("ERRORE", "La pinza inserita � null");
			return false;
		}
		
		Pinza pinza = (Pinza)strumento;
		
		if(pinza.getCondizione() == null)
		{
			AlertPanel.saysInfo("ERRORE", "La condizione inserita � null");
			return false;
		}
		
		if(pinza.getAppartenenza() == null)
		{
			AlertPanel.saysInfo("ERRORE", "L'appartenenza inserita � null");
			return false;
		}
		
		try{
			if (PersisterPinze.getInstance().modificaDatiPinza(pinza))
			return true;
		} catch(SQLException e) {
			AlertPanel.saysError("Errore nella modifica della pinza nel database", e);
		}
		return false;
	}
}
