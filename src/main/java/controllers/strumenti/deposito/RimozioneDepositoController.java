package main.java.controllers.strumenti.deposito;

import main.java.persisters.strumenti.deposito.PersisterDeposito;
import java.sql.SQLException;
import main.java.application.*;

public class RimozioneDepositoController {
	
	public boolean rimuoviDeposito(int Id_Deposito) {
		
		try{
				if (PersisterDeposito.getInstance().rimuoviDeposito(Id_Deposito))
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
