package boundary_FlightManager;

import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JFrame;
import control.AirportLogic;
import control.FlightLogic;
import entity.Airport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;

public class DataForPercentRepController {
	private ObservableList<String> countryList = FXCollections.observableArrayList();
	 @FXML private ComboBox<String> selectCountry;
    
    public void initialize() throws ParseException {
    	ArrayList<String> countries= new ArrayList<>();
    	for(Airport a: AirportLogic.getInstance().getAirport()) {
    		if(countries.isEmpty())
    			countries.add(a.getCountry());
    		else if(!countries.contains(a.getCountry())) {
    			countries.add(a.getCountry());
    		}
    	}
    	
    	
    	countryList.addAll(countries);
    	selectCountry.setItems(countryList);
	 }
    
    @FXML
    public void exportReport() {
    	if(selectCountry.getValue()!=null) {
    		JFrame report =FlightLogic.getInstance().exportPercentReport(selectCountry.getValue());
    		report.setVisible(true);
    	} else {
    		Alert a = new Alert(AlertType.ERROR);
    	    a.setContentText("You need to choose a country.");
    	    a.show();
    	}
   }

}
