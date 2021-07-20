package main.java.controllers.interventi.gestioneAnalisi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

import main.java.application.AlertPanel;
import main.java.controllers.interventi.gestioneInterventi.FiltroInterventi;
import main.java.controllers.interventi.gestioneInterventi.VisualizzazioneInterventiPassatiController;
import main.java.models.areaVerde.*;

public class AnalisiAreeVerdi {

	private static VisualizzazioneInterventiPassatiController ViewIntervController;

	public AnalisiAreeVerdi(VisualizzazioneInterventiPassatiController viewIntervController) {
		super();
		ViewIntervController = viewIntervController;
	}

	public static Set<AreaVerde> elaboraAnalisi(FiltroInterventi filtro) {
		// Lancio un messaggio di avvertenza se il filtro è vuoto
		if (filtro.getDataInizio().isPresent() || filtro.getDataFine().isPresent() || filtro.getDescrizione().get().strip().isBlank() || filtro.getDescrizione().equals(null)
					|| filtro.getNomeArea().isPresent() || filtro.getQuartiere().isPresent())
			AlertPanel.saysInfo("Filtro completamente vuoto", "Ignora il messaggio se desideri ottenere i dati di tutti gli interventi, riporvare altrimenti");

		/*
		 * Trasformo il resultSet passato dal persister in un set di aree verdi e poi le riordino
		 */
		ResultSet unsorted = ViewIntervController.visualizza(filtro);
		Set<AreaVerde> areeVerdi = new TreeSet<AreaVerde>();
		int flag = 0; // serve per capire se ci sono zero aree verdi selezionate
		try {
			while (unsorted.next()) {
				int ID_Gestionale = unsorted.getInt("ID_GESTIONALE");
				Quartiere quartiere = (Quartiere) unsorted.getObject("QUARTIERE");
				String nome = unsorted.getString("NOME");
				String geoPoint = unsorted.getString("GEOPOINT");

				AreaVerde area = new AreaVerde(ID_Gestionale, nome, geoPoint, quartiere);
				areeVerdi.add(area);
				flag++;
			}
		} catch (SQLException e) {
			AlertPanel.saysError("Errore nell'analisi delle aree verdi selezionate", e);
			// Quest'errore dipende dal fatto che non ci sono aree verdi e quindi il ResulSet è vuoto
		}
		if (flag != 0)
			algoritmoDaScrivere(areeVerdi); // è l'algoritmo che ordina i parchi in ordine di necessità di pulizia
		return areeVerdi;

	}

	private static void algoritmoDaScrivere(Set<AreaVerde> areeVerdi) {
		areeVerdi.parallelStream().sorted();
		/*
		 * TODO
		 * Riordinamento fittizio, algoritmo da pensare
		 */
	}
}