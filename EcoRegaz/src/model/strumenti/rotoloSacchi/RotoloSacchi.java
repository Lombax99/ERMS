package model.strumenti.rotoloSacchi;

import model.strumenti.IStrumento;

public class RotoloSacchi implements IStrumento{

	private int id_Deposito;
	private int id_RotoloSacchi;
	
	public RotoloSacchi(int id_Deposito, int id_RotoloSacchi) {
		this.id_Deposito = id_Deposito;
		this.id_RotoloSacchi = id_RotoloSacchi;
	}

	@Override
	public int getId_Deposito() {
		return id_Deposito;
	}

	public int getId_RotoloSacchi() {
		return id_RotoloSacchi;
	}
		
}
