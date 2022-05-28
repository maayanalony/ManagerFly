package boundary_FlightManager;

import java.text.ParseException;
import java.util.ArrayList;

import boundary.TextFormats;
import control.AirportLogic;
import entity.Airport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class AirportController_Add {
	
	
	@FXML private TextField airportCode;
    @FXML private TextField country;
    @FXML private TextField city;
    @FXML private ComboBox<Integer> selectGMT;
    
    // invalids:
    static ArrayList<Label> invalidLabels= new ArrayList<>(); // array of invalid
 	@FXML private Label invalidCode;
 	@FXML private Label invalidCountry;
 	@FXML private Label invalidCity;
 	@FXML private Label invalidGMT;
 	@FXML private Label invalidExist;
    
    public void initialize() throws ParseException {
    	
    	ArrayList<Integer> gmtList = new ArrayList<>(25);
    	for(int i=-12; i<=12; i++) 
    		gmtList.add(i);
    	ObservableList<Integer> gmtObs = FXCollections.observableArrayList(gmtList);
    	selectGMT.setItems(gmtObs);  
    	
    	airportCode.setTextFormatter(new TextFormatter<String>(TextFormats.getInstance().onlyUpperCase()));
    	country.setTextFormatter(new TextFormatter<String>(TextFormats.getInstance().notNumbers()));
    	city.setTextFormatter(new TextFormatter<String>(TextFormats.getInstance().notNumbers()));
	 }
	   
	public void addNewAirport (ActionEvent event) {
		
		if(invalidLabels!=null) {
			 for(Label l: invalidLabels) {
		     	   l.setVisible(false);
		        }
			   invalidLabels.clear();
		}
	
		
	   String inputCode= airportCode.getText();
	   if(inputCode == null || inputCode.isEmpty() ) 
		   invalidLabels.add(invalidCode);
	   if(inputCode.length()!=3) 
	       invalidLabels.add(invalidCode);
	   else if( AirportLogic.getInstance().getAirport().contains(new Airport(inputCode)))
		   invalidLabels.add(invalidExist); // if Airport code id is already Exists
	 

	   String inputCountry= country.getText();
	   if(inputCountry == null || inputCountry.isEmpty()) 
		   invalidLabels.add(invalidCountry);
	   
	   String inputCity= city.getText();
	   if(inputCity == null || inputCity.isEmpty()) 
		   invalidLabels.add(invalidCity);
	   
	   Integer inputGMT= selectGMT.getValue();
	   if(inputGMT == null )
		   invalidLabels.add(invalidGMT);
		   
	   if(invalidLabels.isEmpty()) {
		   Airport newAirport= new Airport(inputCode, inputCountry, inputCity, inputGMT, "OPEN");
		   AirportLogic.getInstance().addAirport(newAirport);
		   
	   } else {
		   for(Label l: invalidLabels) {
        	   l.setVisible(true);
        	   l.setText("X");
        	   if(l.equals(invalidCode))
        		   invalidCode.setText("Only 3 upper Letters");
        	   if(l.equals(invalidExist))
        		   invalidExist.setText("This airport code is already exists");
           }
		   
	   }
		   
		  
	}

}
