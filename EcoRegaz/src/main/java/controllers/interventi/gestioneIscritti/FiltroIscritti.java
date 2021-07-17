package main.java.controllers.interventi.gestioneIscritti;

import java.util.Optional;

import main.java.application.AlertPanel;

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
	
	
	public FiltroIscritti() {
		
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


	public boolean setNome(String nome) {
		
		if(nome.strip().isEmpty() || nome.equals(null))
		{
			this.nome = Optional.empty();
		}
		else
		{
			if(nome.contains("'"))
			{
				AlertPanel.saysInfo("ERRORE", "Il nome contiene un apice");
				return false;
			}
			if(nome.length() > 15)
			{
				AlertPanel.saysInfo("ERRORE", "Nome troppo lungo");
				return false;
			}
			this.nome = Optional.of(nome.toUpperCase());
		}
		return true;
	}


	public boolean setCognome(String cognome) {

		if(cognome.strip().isEmpty() || cognome.equals(null))
		{
			this.cognome = Optional.empty();
		}
		else
		{
			if(cognome.contains("'"))
			{
				AlertPanel.saysInfo("ERRORE", "Il cognome contiene un apice");
				return false;
			}
			if(cognome.length() > 20)
			{
				AlertPanel.saysInfo("ERRORE", "Cognome troppo lungo");
				return false;
			}
			this.cognome = Optional.of(cognome.toUpperCase());
		}
		return true;
	}


	public boolean setCodFisc(String codFisc) {

		if(codFisc.strip().isEmpty() || codFisc.equals(null))
		{
			this.codFisc = Optional.empty();
		}
		else
		{
			if(codFisc.contains("'"))
			{
				AlertPanel.saysInfo("ERRORE", "Uno dei campi inseriri contiene un apice");
				return false;
			}
			if(codFisc.length() != 16)
			{
				AlertPanel.saysInfo("ERRORE", "Codice Fiscale non valido");
				return false;
			}
			codFisc = codFisc.toUpperCase();
			if(! codFisc.matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$"))
			{
				AlertPanel.saysInfo("ERRORE", "Codice Fiscale non valido");
				return false;
			}
			this.codFisc = Optional.of(codFisc);
		}
		return true;
	}
	
	
}
