package main.java.controllers.strumenti.deposito;
import main.java.persisters.strumenti.deposito.PersisterDeposito;

import java.sql.SQLException;

import main.java.application.*;
import main.java.models.deposito.Deposito;
public class RimozioneDepositoController {
	
	public boolean rimuoviDeposito(Deposito dep) {
		
		IllegalArgumentException illegal = new IllegalArgumentException();
	
		if(dep.getDescrizione().equals(null) || dep.getStrumentiExtra().equals(null))
			AlertPanel.saysError("Parametri null non accettabili",  illegal);
		
		else if(dep.getDescrizione().strip().isBlank())
			AlertPanel.saysError("Descrizione vuota non accettabile",  illegal);
		
		else if(dep.getDescrizione().length() > 250 || dep.getStrumentiExtra().strip().length() > 250 )
			AlertPanel.saysError("Descrizione troppo lunga",  illegal );
		
		else try{
				if (PersisterDeposito.getInstance().rimuoviDeposito(dep.getId_Deposito()))
				{
					AlertPanel.saysInfo("Rimozione avvenuta con successo", "");
					return true;
				}
			} catch(SQLException e) {
				AlertPanel.saysError("Errore nella rimozione del deposito nel database", e);
			}
		return false;
	}
}
