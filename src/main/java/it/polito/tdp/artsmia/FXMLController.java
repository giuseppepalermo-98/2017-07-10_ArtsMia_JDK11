package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Integer> boxLUN;

    @FXML
    private Button btnCalcolaComponenteConnessa;

    @FXML
    private Button btnCercaOggetti;

    @FXML
    private Button btnAnalizzaOggetti;

    @FXML
    private TextField txtObjectId;

    @FXML
    private TextArea txtResult;

    @FXML
    void doAnalizzaOggetti(ActionEvent event) {
    	this.txtResult.clear();
    	
    	this.model.creaGrafo();
    	
    	this.txtResult.appendText("Grafo creato!");
    }

    @FXML
    void doCalcolaComponenteConnessa(ActionEvent event) {
    	this.txtResult.clear();
    	Integer ido;
    	
    	try {
    	 ido = Integer.parseInt(this.txtObjectId.getText());
    	}
    	catch(NumberFormatException e) {
    		this.txtResult.appendText("ID NON VALIDO");
    		return;
    	}
    	
    	if(model.isPresente(ido))
    	 this.txtResult.appendText("Le componenti connesse a questo vertice sono: "+model.getConnessi(ido));
    	else
    		this.txtResult.appendText("Non esiste un oggeto con l'ID inserito");
    	
    	
    	
    	for(int i=2; i<model.getConnessi(ido); i++)
    		this.boxLUN.getItems().add(i);
    }

    @FXML
    void doCercaOggetti(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Integer ido;
    	Integer LUN;
    	try {
    	 ido = Integer.parseInt(this.txtObjectId.getText());
    	 LUN = (this.boxLUN.getValue());
    	 if(model.isPresente(ido)) {
    		List<ArtObject>result =  this.model.getPercorso(LUN, ido);
    		
    		this.txtResult.appendText("Il percorso ha peso pari a "+model.calcolaPeso(result)+" i vertici sono: \n");
    		for(ArtObject a: result)
    			this.txtResult.appendText(a.getId()+"\n");
    		
    	 }
    	}
    	catch(NumberFormatException e) {
    		this.txtResult.appendText("ID NON VALIDO");
    		return;
    	}
    }

    @FXML
    void initialize() {
        assert boxLUN != null : "fx:id=\"boxLUN\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaComponenteConnessa != null : "fx:id=\"btnCalcolaComponenteConnessa\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCercaOggetti != null : "fx:id=\"btnCercaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnAnalizzaOggetti != null : "fx:id=\"btnAnalizzaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtObjectId != null : "fx:id=\"txtObjectId\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
