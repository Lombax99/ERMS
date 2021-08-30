package main.java.controllers.strumenti;

import java.sql.ResultSet;

import main.java.controllers.IFiltro;
import main.java.models.strumenti.*;

/**
 * Interfaccia che definisce i metodi per gestire tutti gli strumenti
 *
 */

public interface IGestioneStrumentiController {

	public boolean aggiuntaNuovoStrumento(IStrumento strumento);
	
	public boolean rimozioneStrumento(int ID);
	
	public ResultSet elencoStrumenti(IFiltro filtro);
	
	public boolean modificaDatiStrumento(IStrumento strumento);
	
}
