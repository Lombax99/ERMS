package main.java.models.areaVerde;

public class AreaVerde {
	
	private int id_GestionaleAreaVerde;
	private Quartiere quartiere;
	private String nome;
	private String geoPoint;
	
	public AreaVerde(int id_GestionaleAreaVerde, String nome, String geoPoint, Quartiere quartiere) {
		this.id_GestionaleAreaVerde = id_GestionaleAreaVerde;
		this.quartiere = quartiere;
		this.nome = nome;
		this.geoPoint = geoPoint;
	}

	public int getId_GestionaleAreaVerde() {
		return id_GestionaleAreaVerde;
	}

	public Quartiere getQuartiere() {
		return quartiere;
	}

	public String getNome() {
		return nome;
	}

	public String getGeoPoint() {
		return geoPoint;
	}

	public void setNome(String nome) {
		this.nome = nome;
		
	}

}
