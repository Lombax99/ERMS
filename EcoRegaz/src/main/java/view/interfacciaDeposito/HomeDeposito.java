package main.java.view.interfacciaDeposito;

import main.java.controller.strumenti.deposito.*;

public class HomeDeposito {

	private AggiuntaDepositoController aggDepController;
	private ElencoDepositiController viewDepController;
	private ModificaDepositoController modDepController;
	private RimozioneDepositoController elimDepController;
	
	public HomeDeposito(AggiuntaDepositoController aggDepController, ElencoDepositiController viewDepController,
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
