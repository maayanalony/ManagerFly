package boundary_FlightManager;

import java.util.ArrayList;

import boundary.TextFormats;
import control.AirportLogic;
import entity.Airport;
import enumeration.AirportStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class AirportController_Edit {
	
	Airport chosen=AirportsController_Panel.chosenAirport;
	@FXML private Label airportCode;
    @FXML private TextField country;
    @FXML private TextField city;
    @FXML private ComboBox<Integer> selectGMT;
    @FXML private ComboBox<AirportStatus> selectStatus;
    
    // invalids:
    static ArrayList<Label> invalidLabels= new ArrayList<>(); // array of invalid
 	@FXML private Label invalidCode;
 	@FXML private Label invalidCountry;
 	@FXML private Label invalidCity;
 	@FXML private Label invalidGMT;
    
    public void initialize() {
    	
    	ArrayList<Integer> gmtList = new ArrayList<>(25);
    	for(int i=-12; i<=12; i++) 
    		gmtList.add(i);
    	ObservableList<Integer> gmtObs = FXCollections.observableArrayList(gmtList);
    	selectGMT.setItems(gmtObs);  
    	ArrayList<AirportStatus> statusList= new ArrayList<>(2);
    	statusList.add(AirportStatus.OPEN);
    	statusList.add(AirportStatus.CLOSE);
    	ObservableList<AirportStatus> statusObs = FXCollections.observableArrayList(statusList);
    	selectStatus.setItems(statusObs);
    	
    	airportCode.setText(chosen.getAirportCode());
    	country.setText(chosen.getCountry());
    	city.setText(chosen.getCity());
    	selectGMT.setValue(chosen.getGMTcode());
    	selectStatus.setValue(chosen.getStatus());
    	
    	country.setTextFormatter(new TextFormatter<String>(TextFormats.getInstance().notNumbers()));
    	city.setTextFormatter(new TextFormatter<String>(TextFormats.getInstance().notNumbers()));
    	
	 }
	   
	public void saveAirport (ActionEvent event) {
		
		if(invalidLabels!=null) {
			 for(Label l: invalidLabels) {
		     	   l.setVisible(false);
		        }
			   invalidLabels.clear();
		}
	
		
	   
	   String inputCode= airportCode.getText();

	   String inputCountry= country.getText();
	   if(inputCountry == null || inputCountry.isEmpty()) 
		   invalidLabels.add(invalidCountry);
	   
	   String inputCity= city.getText();
	   if(inputCity == null || inputCity.isEmpty()) 
		   invalidLabels.add(invalidCity);
	   
	   Integer inputGMT= selectGMT.getValue();
	   if(inputGMT == null )
		   invalidLabels.add(invalidGMT);
	   
	   AirportStatus inputStatus= selectStatus.getValue();
		   
	   if(invalidLabels.isEmpty()) {
		   Airport newAirport= new Airport(inputCode, inputCountry, inputCity, inputGMT, inputStatus.toString());
		   System.out.println(newAirport);
		   AirportLogic.getInstance().editAirport(newAirport);
		   
	   } else {
		   for(Label l: invalidLabels) {
        	   l.setVisible(true);
        	   l.setText("X");
           }
		   
	   }
		   
		  
	}

}
