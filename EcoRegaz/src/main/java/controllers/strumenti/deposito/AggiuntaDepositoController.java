package main.java.controllers.strumenti.deposito;

import main.java.persisters.strumenti.deposito.PersisterDeposito;

import java.sql.SQLException;

import main.java.application.*;
import main.java.models.deposito.Deposito;
public class AggiuntaDepositoController {
	
	public boolean aggiuntaDeposito(Deposito dep) {

		IllegalArgumentException illegal = new IllegalArgumentException();
		
	if(dep.getDescrizione().isBlank())
		AlertPanel.saysError("Descrizione vuota non accettabile",  illegal );
		
	else if(dep.getDescrizione().length() > 250 || dep.getStrumentiExtra().length() > 250 )
		AlertPanel.saysError("Descrizione troppo lunga",  illegal );
		
	else try{
		if ( PersisterDeposito.getInstance().aggiuntaDeposito(dep))
			return true;
	} catch(SQLException e) {
		AlertPanel.saysError("Errore nell'inserimento del deposito nel database", e);
	}
	return false;
	}
}
