package model.strumenti.scatolaGuanti;

public class ScatolaGuanti {

	private int id_Deposito;
	private Taglia taglia;
	private int id_ScatolaGuanti;
	
	public ScatolaGuanti(int id_Deposito, Taglia taglia, int id_ScatolaGuanti) {
		super();
		this.id_Deposito = id_Deposito;
		this.taglia = taglia;
		this.id_ScatolaGuanti = id_ScatolaGuanti;
	}

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
