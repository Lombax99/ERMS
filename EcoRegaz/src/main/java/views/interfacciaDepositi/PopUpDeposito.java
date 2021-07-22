package main.java.views.interfacciaDepositi;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import main.java.controllers.strumenti.deposito.AggiuntaDepositoController;
import main.java.models.deposito.Deposito;

public class PopUpDeposito implements Initializable {

	private AggiuntaDepositoController controllerAggDep;
	
	@FXML
	private TextField DescrizioneField;
	@FXML
	private TextField StrumentiExtraField;
	
	public PopUpDeposito(AggiuntaDepositoController controllerAggDep) {
		this.controllerAggDep = controllerAggDep;
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
				
	}
	
	@FXML
	private void PopUpAggiungiDepositoHandler() {
		
		controllerAggDep.aggiuntaDeposito(new Deposito(-1, DescrizioneField.getText(), StrumentiExtraField.getText()));
	}
}