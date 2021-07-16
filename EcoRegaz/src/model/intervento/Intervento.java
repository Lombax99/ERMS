package model.intervento;

import java.time.LocalDate;
import java.util.List;

public class Intervento {
	
	private int id_Intervento;
	private int id_GestionaleAreaVerde;
	private LocalDate dataIntervento;
	private List<String> elencoCFPartecipanti;
	private String descrizioneValutativa;
	private int gravità;
	
	public Intervento(int id_GestionaleAreaVerde, LocalDate dataIntervento, List<String> elencoCFPartecipanti,
			String descrizioneValutativa, int gravità) {
		this.id_GestionaleAreaVerde = id_GestionaleAreaVerde;
		this.dataIntervento = dataIntervento;
		this.elencoCFPartecipanti = elencoCFPartecipanti;
		this.descrizioneValutativa = descrizioneValutativa;
		this.gravità = gravità;
	}

	public int getId_Intervento() {
		return id_Intervento;
	}
	
	public void setId_Intervento(int ID)
	{
		this.id_Intervento = ID;
	}

	public int getId_GestionaleAreaVerde() {
		return id_GestionaleAreaVerde;
	}

	public LocalDate getDataIntervento() {
		return dataIntervento;
	}

	public List<String> getElencoCFPartecipanti() {
		return elencoCFPartecipanti;
	}

	public String getDescrizioneValutativa() {
		return descrizioneValutativa;
	}

	public int getGravità() {
		return gravità;
	}

}
