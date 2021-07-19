package main.java.controllers.interventi.gestioneRendicontazione;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import main.java.application.AlertPanel;
import main.java.controllers.interventi.gestioneInterventi.FiltroInterventi;
import main.java.controllers.interventi.gestioneInterventi.VisualizzazioneInterventiPassatiController;
import main.java.models.intervento.Intervento;

public class Rendicontazione {
	
	private static VisualizzazioneInterventiPassatiController ViewIntervController;

	public Rendicontazione(VisualizzazioneInterventiPassatiController viewIntervController) {
		super();
		ViewIntervController = viewIntervController;
	
	}
	
	/*
	 *La seguente funzione chiama il controller degli interventi che a sua volta chiamerà il persister.
	 *In questo modo può gestire il resultSet che gli viene tornato
	 * */
	public static void produzioneRendicontazione(FiltroInterventi filtro) {
		
		ResultSet unsorted = ViewIntervController.visualizza(filtro);
		Set<Intervento> interventi = new TreeSet<Intervento>();
		int flag = 0;
		try {
			while(unsorted.next())
			{
			int ID_Gestionale = unsorted.getInt("ID_GESTIONALE");
			String DescrizioneValutativa = unsorted.getString("DESCRIZIONE");
			int gravità = unsorted.getInt("GRAVITA");
			@SuppressWarnings("unchecked")
			List<String> elencoCF = (List<String>) unsorted.getNCharacterStream("ELENCOCF");
			LocalDate giorno = (LocalDate) unsorted.getObject("DATA");

			
			Intervento intervento = new Intervento(ID_Gestionale,giorno, elencoCF,
					DescrizioneValutativa,gravità);
			interventi.add(intervento);
			flag++;
			}
		} catch (SQLException e) {
			AlertPanel.saysError("Errore nell'analisi delle aree verdi selezionate", e);
			//Quest'errore dipende dal fatto che non ci sono aree verdi e quindi il ResulSet è vuoto
		}
		if (flag!= 0) produzioneRendicontazioneVuota();
		/*
		 * else stampa la rendicontazoine nel formato stabilito
		 * TODO
		 */
}
	
	private static void produzioneRendicontazioneVuota()
	{
		//Qui viene prodotta la rendicontazione di default, senza alcun intervento da rendicontare
		//TODO
	}
	
}
