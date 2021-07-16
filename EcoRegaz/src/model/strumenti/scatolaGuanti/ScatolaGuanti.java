package model.strumenti.scatolaGuanti;

import model.strumenti.IStrumento;

public class ScatolaGuanti implements IStrumento{

	private int id_Deposito;
	private Taglia taglia;
	private int id_ScatolaGuanti;
	
	public ScatolaGuanti(int id_Deposito, Taglia taglia, int id_ScatolaGuanti) {
		this.id_Deposito = id_Deposito;
		this.taglia = taglia;
		this.id_ScatolaGuanti = id_ScatolaGuanti;
	}

	@Override
	public int getId_Deposito() {
		return id_Deposito;
	}

	public Taglia getTaglia() {
		return taglia;
	}

	public int getId_ScatolaGuanti() {
		return id_ScatolaGuanti;
	}

}
