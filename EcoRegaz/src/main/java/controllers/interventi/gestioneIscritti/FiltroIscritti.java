package main.java.controllers.interventi.gestioneIscritti;

import java.util.Optional;

/**
 * Classe di utility che va usata quando bisogna ricercare gli Iscritti con certi parametri. <br>
 * REGOLE: <br>
 * 1) nome: viene ricercato non univocamente: se nome="Luca", corrispondono "Luca" "Luca Marco" <br>
 * 2) cognome: uguale a nome <br>
 * 3) codFisc: cercato univocamente: se codFisc="VNGRCR00A26A944A", corrisponde esattamente quello
 */
public class FiltroIscritti {
	
	private Optional<String> nome;
	private Optional<String> cognome;
	private Optional<String> codFisc;
	
	
	public FiltroIscritti(Optional<String> nome, Optional<String> cognome, Optional<String> codFisc) {
		this.nome = nome;
		this.cognome = cognome;
		this.codFisc = codFisc;
	}


	public Optional<String> getNome() {
		return nome;
	}


	public Optional<String> getCognome() {
		return cognome;
	}


	public Optional<String> getCodFisc() {
		return codFisc;
	}
	

}
