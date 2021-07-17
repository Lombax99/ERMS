package main.java.controllers.strumenti.deposito;

import main.java.persisters.strumenti.deposito.PersisterDeposito;
import main.java.application.*;
import main.java.models.deposito.Deposito;
import java.lang.Error;
public class AggiuntaDepositoController {

	private PersisterDeposito peristerReference;
	
	public void aggiuntaDeposito(Deposito dep) {

		IllegalArgumentException e = new IllegalArgumentException();
		
	if(dep.getDescrizione().isBlank())
		AlertPanel.saysError("Descrizione vuota non accettabile",  e );
		
	if(dep.getDescrizione().length() > )
		AlertPanel.saysError("Descrizione vuota non accettabile",  e );
		
	}
}
