package main.java.controllers.interventi.gestioneAnalisi;

import java.util.List;

import main.java.controllers.interventi.gestioneInterventi.FiltroInterventi;
import main.java.controllers.interventi.gestioneInterventi.VisualizzazioneInterventiPassatiController;
import main.java.models.areaVerde.AreaVerde;

public class AnalisiAreeVerdi {

	private VisualizzazioneInterventiPassatiController ViewIntervController;

	public AnalisiAreeVerdi(VisualizzazioneInterventiPassatiController viewIntervController) {
		super();
		ViewIntervController = viewIntervController;
	}
	
	public static List<AreaVerde> elaboraAnalisi(FiltroInterventi filtro) {
		
	}
}