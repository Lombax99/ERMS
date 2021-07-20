package main.java.controllers.strumenti.pinze;

import java.util.Optional;

import main.java.application.AlertPanel;
import main.java.models.strumenti.pinza.Appartenenza;
import main.java.models.strumenti.pinza.Condizione;

/**
 * Classe di utility che va usata quando bisogna ricercare le Pinze con certi parametri. <br>
 */
public class FiltroPinze {

	private Optional<Integer> id_Deposito;
	private Optional<Integer> id_Pinza;
	private Optional<Appartenenza> appartenenza;
	private Optional<Condizione> condizione;

	public FiltroPinze() {
		this.id_Deposito = Optional.empty();
		this.id_Pinza = Optional.empty();
		this.appartenenza = Optional.empty();
		this.condizione = Optional.empty();
	}

	public Optional<Integer> getId_Deposito() {
		return id_Deposito;
	}

	public Optional<Integer> getId_Pinza() {
		return id_Pinza;
	}

	public Optional<Appartenenza> getAppartenenza() {
		return appartenenza;
	}

	public Optional<Condizione> getCondizione() {
		return condizione;
	}

	public boolean setId_Deposito(int id_Deposito) {
		if (id_Deposito > 0) {
			this.id_Deposito = Optional.of(id_Deposito);
			return true;
		} else {
			AlertPanel.saysInfo("ERRORE", "id_Deposito deve essere positivo");
			this.id_Deposito = Optional.empty();
			return false;
		}
	}

	public boolean setId_Pinza(int id_Pinza) {

		if (id_Pinza > 0) {
			this.id_Pinza = Optional.of(id_Pinza);
			return true;
			
		} else {
			AlertPanel.saysInfo("ERRORE", "id_Pinza deve essere positivo");
			this.id_Pinza = Optional.empty();
			return false;
		}
	}

	public boolean setAppartenenza(Appartenenza appartenenza) {
		if (appartenenza != null) {
			this.appartenenza = Optional.of(appartenenza);
			return true;
		} else {
			this.appartenenza = Optional.empty();
			return false;
		}
	}

	public boolean setCondizione(Condizione condizione) {

		if (condizione != null) {
			this.condizione = Optional.of(condizione);
			return true;
		} else {
			this.condizione = Optional.empty();
			return false;
		}

	}

}
