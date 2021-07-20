package main.java.views.interfacciaDepositi;

import javafx.stage.Stage;
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
	
	public HomeDepositi(Stage primaryStage) {
		// TODO Auto-generated constructor stub
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
