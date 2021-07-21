package main.java.controllers.interventi.gestioneInterventi;

import java.time.LocalDate;
import java.util.Optional;

import main.java.application.AlertPanel;
import main.java.controllers.IFiltro;
import main.java.models.areaVerde.Quartiere;

/**
 * Classe di utility che va usata quando bisogna ricercare gli Interventi con certi parametri. <br>
 * REGOLE: <br>
 * 1) dataInizio/dataFine: rappresentano un intervallo di tempo estremi inclusi <br>
 * 2) quartiere: ricercato univocmente, un'area verde può appartenere ad uno ed un solo quartiere <br>
 * 3) nomeArea: viene ricercato non univocamente: se nome="Arcobaleno", corrispondono "Arcobaleno" "giardino Arcobaleno" <br>
 * 4) descrizione: viene ricercata un'area verde che contiene la stringa data nella descrizione
 */

public class FiltroInterventi implements IFiltro{

	private Optional<LocalDate> dataInizio;
	private Optional<LocalDate> dataFine;
	private Optional<Quartiere> quartiere;
	private Optional<String> nomeArea;
	private Optional<String> descrizione;
	private Optional<Integer> idGestionaleAreaVerde;
	
	public void FiltroIscritti() {
		this.dataInizio = Optional.empty();
		this.dataFine = Optional.empty();
		this.quartiere = Optional.empty();
		this.nomeArea = Optional.empty();
		this.descrizione = Optional.empty();
		this.idGestionaleAreaVerde = Optional.empty();
	}

	public Optional<LocalDate> getDataInizio() {
		return dataInizio;
	}

	public Optional<LocalDate> getDataFine() {
		return dataFine;
	}

	public Optional<Quartiere> getQuartiere() {
		return quartiere;
	}

	public Optional<String> getNomeArea() {
		return nomeArea;
	}

	public Optional<String> getDescrizione() {
		return descrizione;
	}
	
	/*
	 * Le seguenti funzioni ritornano:
	 * un valore vero se l'attributo selezionato una volta modificato contiene qualcosa
	 * ritorna invece un valore falso se è vuoto 
	 * errore in alertPanel in caso di errori (ad esempio per una descirzione troppo lunga)
	 */
	
	public Optional<Integer> getIdGestionaleAreaVerde() {
		return idGestionaleAreaVerde;
	}

	public boolean setDescrizione(String descrizione) {
		if(descrizione.strip().isEmpty() || descrizione.equals(null))
		{
			this.descrizione = Optional.empty();
			return false;
		}
		else
		{
			if(descrizione.contains("'"))
			{
				descrizione.replace("'", "`");
			}
			
			if(descrizione.length() > 2000)
			{
				AlertPanel.saysInfo("ERRORE", "Descrizione troppo lunga");
				throw new IllegalArgumentException();
			}
			this.descrizione = Optional.of(descrizione.toUpperCase());
			return true;
		}
	}
	

	public boolean setQuartiere(Quartiere quartiere) {
		if(quartiere == null)
		{
			this.quartiere = Optional.empty();
			return false;
		}
		else
		{
			this.quartiere = Optional.of(quartiere);
			return true;
		}
	}
	
	public boolean setNomeArea(String nomeArea) {
		if(nomeArea.strip().isEmpty() || nomeArea.equals(null))
		{
			this.nomeArea = Optional.empty();
			return false;
		}
		else
		{
			this.nomeArea = Optional.of(nomeArea);
			return true;
		}
	}
	
	public boolean setDataInizio(LocalDate dataInizio) {
		if(dataInizio == null)
		{
			this.dataInizio = Optional.empty();
			return false;
		}
		else
		{
			this.dataInizio = Optional.of(dataInizio);
			return true;
		}
	}

	public boolean setDataFine(LocalDate dataFine) {
		if(dataFine == null)
		{
			this.dataFine = Optional.empty();
			return false;
		}
		else
		{
			this.dataFine = Optional.of(dataFine);
			return true;
		}
	}

	public boolean setIdGestionaleAreaVerde(Integer idGestionaleAreaVerde) {
		if(idGestionaleAreaVerde == null)
		{
			this.idGestionaleAreaVerde = Optional.empty();
			return false;
		}
		else
		{
			this.idGestionaleAreaVerde = Optional.of(idGestionaleAreaVerde);
			return true;
		}
	}
}