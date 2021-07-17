package main.java.persisters.areeVerdi;

import java.util.List;

import main.java.models.areaVerde.AreaVerde;

/**
 * Interfaccia che definisce i metodi per l'interazione col DB circa le aree verdi
 *
 */
public interface IPersisterAreeVerdi {

	public List<AreaVerde> visualizzaAreeVerdi(String nomeAreaVerde);

}
