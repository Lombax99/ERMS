package main.java.controllers.interventi.gestioneInterventi;

import java.time.LocalDate;
import java.util.Optional;

import main.java.application.AlertPanel;
import main.java.models.areaVerde.Quartiere;

/**
 * Classe di utility che va usata quando bisogna ricercare gli Interventi con certi parametri. <br>
 * REGOLE: <br>
 * 1) dataInizio/dataFine: rappresentano un intervallo di tempo estremi inclusi <br>
 * 2) quartiere: ricercato univocmente, un'area verde può appartenere ad uno ed un solo quartiere <br>
 * 3) nomeArea: viene ricercato non univocamente: se nome="Arcobaleno", corrispondono "Arcobaleno" "giardino Arcobaleno" <br>
 * 4) descrizione: viene ricercata un'area verde che contiene la stringa data nella descrizione
 */

public class FiltroInterventi {

	private Optional<LocalDate> dataInizio;
	private Optional<LocalDate> dataFine;
	private Optional<Quartiere> quartiere;
	private Optional<String> nomeArea;
	private Optional<String> descrizione;
	
	public void FiltroIscritti() {
		
	}

	public Optional<LocalDate> getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDate dataInizio) {
		if(dataInizio == null)
		{
			this.dataInizio = Optional.empty();
		}
		else
		{
			this.dataInizio = Optional.of(dataInizio);
		}
	}

	public Optional<LocalDate> getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDate dataFine) {
		if(dataFine == null)
		{
			this.dataFine = Optional.empty();
		}
		else
		{
			this.dataFine = Optional.of(dataFine);
		}
	}

	public Optional<Quartiere> getQuartiere() {
		return quartiere;
	}

	public void setQuartiere(Quartiere quartiere) {
		if(quartiere == null)
		{
			this.quartiere = Optional.empty();
		}
		else
		{
			this.quartiere = Optional.of(quartiere);
		}
	}

	public Optional<String> getNomeArea() {
		return nomeArea;
	}

	public void setNomeArea(String nomeArea) {
		if(nomeArea.strip().isEmpty() || nomeArea.equals(null))
		{
			this.nomeArea = Optional.empty();
		}
		else
		{
			this.nomeArea = Optional.of(nomeArea);
		}
	}

	public Optional<String> getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		if(descrizione.strip().isEmpty() || descrizione.equals(null))
		{
			this.descrizione = Optional.empty();
		}
		else
		{
			/*
			 * TODO
			 * controllo anti SQLInjection
			 * vedi prepared statement
			 */
			
			if(descrizione.length() > 50)
			{
				AlertPanel.saysInfo("ERRORE", "Descrizione troppo lunga");
				throw new IllegalArgumentException();
			}
			this.descrizione = Optional.of(descrizione.toUpperCase());
		}
	};	
}
