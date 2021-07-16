package main.java.model.deposito;

public class Deposito {

	private int id_Deposito;
	private String descrizione;
	private String strumentiExtra;
	
	public Deposito(int id_Deposito, String descrizione, String strumentiExtra) {
		this.id_Deposito = id_Deposito;
		this.descrizione = descrizione;
		this.strumentiExtra = strumentiExtra;
	}

	public int getId_Deposito() {
		return id_Deposito;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getStrumentiExtra() {
		return strumentiExtra;
	}

}
