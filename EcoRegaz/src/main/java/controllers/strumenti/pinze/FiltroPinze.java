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

	public void setId_Deposito(int id_Deposito) {
		if (id_Deposito > 0) {
			this.id_Deposito = Optional.of(id_Deposito);
		} else {
			AlertPanel.saysInfo("ERRORE", "id_Deposito deve essere positivo");
			this.id_Deposito = Optional.empty();
		}
	}

	public void setId_Pinza(int id_Pinza) {

		if (id_Pinza > 0) {
			this.id_Pinza = Optional.of(id_Pinza);
		} else {
			AlertPanel.saysInfo("ERRORE", "id_Pinza deve essere positivo");
			this.id_Pinza = Optional.empty();
		}
	}

	public void setAppartenenza(Appartenenza appartenenza) {
		if (appartenenza != null) {
			this.appartenenza = Optional.of(appartenenza);
		} else {
			this.appartenenza = Optional.empty();
		}
	}

	public void setCondizione(Condizione condizione) {

		if (condizione != null) {
			this.condizione = Optional.of(condizione);
		} else {
			this.condizione = Optional.empty();
		}

	}

}
