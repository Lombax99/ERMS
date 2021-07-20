package main.java.views.interfacciaDepositi;

import main.java.controllers.strumenti.deposito.*;

public class HomeDepositi {

	private AggiuntaDepositoController aggDepController;
	private ElencoDepositiController viewDepController;
	private ModificaDepositoController modDepController;
	private RimozioneDepositoController elimDepController;
	
	public HomeDepositi(AggiuntaDepositoController aggDepController, ElencoDepositiController viewDepController,
			ModificaDepositoController modDepController, RimozioneDepositoController elimDepController) {
		this.aggDepController = aggDepController;
		this.viewDepController = viewDepController;
		this.modDepController = modDepController;
		this.elimDepController = elimDepController;
	}
	
	private void tastoAggiugniDepositoHandler()	{
		
	}
	
	private void rimuoviDepositoHandler()	{
		
	}
	
	private void modificaDepositoHandler() {
		
	}
	
	private void visualizza() {
		
	}
}
