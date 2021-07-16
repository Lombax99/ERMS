package main.java.model.strumenti.pinza;

import main.java.model.strumenti.IStrumento;

public class Pinza implements IStrumento{
	
	private int id_Deposito;
	private int id_Pinza;
	private Appartenenza appartenenza;
	private Condizione condizione;

	public Pinza(int id_Deposito, int id_Pinza, Appartenenza appartenenza, Condizione condizione) {
		this.id_Deposito = id_Deposito;
		this.id_Pinza = id_Pinza;
		this.appartenenza = appartenenza;
		this.condizione = condizione;
	}

	@Override
	public int getId_Deposito() {
		return 0;
	}

	public int getId_Pinza() {
		return id_Pinza;
	}

	public Appartenenza getAppartenenza() {
		return appartenenza;
	}

	public Condizione getCondizione() {
		return condizione;
	}

}
