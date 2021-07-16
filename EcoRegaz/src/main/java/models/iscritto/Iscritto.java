package main.java.models.iscritto;

public class Iscritto {

	private String nome;
	private String cognome;
	private String codFisc;
	
	public Iscritto(String nome, String cognome, String codFisc) {
		this.nome = nome;
		this.cognome = cognome;
		this.codFisc = codFisc;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getCodFisc() {
		return codFisc;
	}
	
}
