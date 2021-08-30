package main.java.controllers.strumenti.deposito;

import main.java.persisters.strumenti.deposito.PersisterDeposito;

import java.sql.SQLException;

import main.java.application.*;
import main.java.models.deposito.Deposito;
public class AggiuntaDepositoController {
	
	public boolean aggiuntaDeposito(Deposito dep) {

		IllegalArgumentException illegal = new IllegalArgumentException();
		
		if(dep.getDescrizione().equals(null) || dep.getStrumentiExtra().equals(null))
		{
			AlertPanel.saysError("Parametri null non accettabili",  illegal);
		}
		else if(dep.getDescrizione().strip().isBlank())
		{
			AlertPanel.saysError("Descrizione vuota non accettabile",  illegal);
		}
		else if(dep.getDescrizione().length() > 250 || dep.getStrumentiExtra().strip().length() > 250 )
		{
			AlertPanel.saysError("Descrizione troppo lunga",  illegal );			
		}
		else if(dep.getDescrizione().contains("'"))
		{
			dep.setDescrizione(dep.getDescrizione().replace("'", "`"));
		}
		else if(dep.getStrumentiExtra().contains("'"))
		{
			dep.setStrumentiExtra(dep.getStrumentiExtra().replace("'", "`"));
		}
		else 
			try{
				if (PersisterDeposito.getInstance().aggiuntaDeposito(dep))
				return true;
			} catch(SQLException e) {
				AlertPanel.saysError("Errore nell'inserimento del deposito nel database", e);
			}
		return false;
	}
}