package main.java.controllers.strumenti.deposito;

import main.java.persisters.strumenti.deposito.PersisterDeposito;

import java.sql.SQLException;

import main.java.application.*;
import main.java.models.deposito.Deposito;

public class ModificaDepositoController {

	public boolean modificaDeposito(Deposito dep) {

		IllegalArgumentException illegal = new IllegalArgumentException();

		if(dep.getDescrizione().equals(null) || dep.getStrumentiExtra().equals(null))
			AlertPanel.saysError("Parametri null non accettabili",  illegal);
		
		else if (dep.getDescrizione().strip().isBlank())
			AlertPanel.saysError("Descrizione vuota non accettabile", illegal);

		else if (dep.getDescrizione().length() > 250 || dep.getStrumentiExtra().length() > 250)
			AlertPanel.saysError("Descrizione o StrumentiEntra troppo lunghi", illegal);

		else
			try {
				dep.getStrumentiExtra().strip();
				if (PersisterDeposito.getInstance().modificaDeposito(dep))
					return true;
			} catch (SQLException e) {
				AlertPanel.saysError("Errore nell'inserimento del deposito nel database", e);
			}
		return false;
	}
}
