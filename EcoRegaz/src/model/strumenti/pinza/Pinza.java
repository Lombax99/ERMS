package model.strumenti.pinza;

import model.strumenti.IStrumento;

public class Pinza implements IStrumento{
	
	private int id_Deposito;
	private int id_Pinza;
	private Appartenenza appartenenza;
	private Condizione condizione;

	@Override
	public int getId_Deposito() {
		return 0;
	}

}
