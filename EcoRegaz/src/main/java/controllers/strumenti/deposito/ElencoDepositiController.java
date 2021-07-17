package main.java.controllers.strumenti.deposito;

import main.java.persisters.strumenti.deposito.PersisterDeposito;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.application.*;

public class ElencoDepositiController {

	public ResultSet elencoDepositi() {

		ResultSet result = null;
		try {
			result = PersisterDeposito.getInstance().visualizzaDepositi();
		} catch (SQLException e) {
			AlertPanel.saysError("Errore nella visualizzazione dei depositi", e);
		}
		return result;
	}
}
