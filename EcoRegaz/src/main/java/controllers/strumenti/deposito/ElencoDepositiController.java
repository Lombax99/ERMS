package main.java.controllers.strumenti.deposito;

import main.java.persisters.strumenti.deposito.PersisterDeposito;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.application.*;
import main.java.models.deposito.Deposito;

public class ElencoDepositiController {
	
	public ResultSet elencoDepositi() {

		ResultSet result;
		try{
		result = PersisterDeposito.getInstance().elencoDepositi());
	} catch(SQLException e) {
		AlertPanel.saysError("Errore nell'apertura del deposito", e);
	}
		return result;
	}
}

