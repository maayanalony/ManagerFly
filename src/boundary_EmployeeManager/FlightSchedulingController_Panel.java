package boundary_EmployeeManager;

import java.io.IOException;
import javax.swing.JOptionPane;

import javafx.beans.value.ObservableValue;
import Exceptions.NothingSelectedExceptions;
import boundary.EmployeeManagerController;
import control.FlightLogic;
import entity.Flight;
import entity.FlightAttendant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class FlightSchedulingController_Panel {

    public static Flight chosenFlight;

    private ObservableList<Flight> flightsListObs  = FXCollections.observableArrayList(FlightLogic.getInstance().getFlights()); 
    private ObservableList<FlightAttendant> flightAttendantsListObs  = FXCollections.observableArrayList(); 
    
    @FXML private ListView<Flight> listFlights;
    @FXML private ListView<FlightAttendant> listFlightAttendants;
    @FXML private TextField searchFlightID;
    @FXML private Label chiefPilot;
    @FXML private Label secPilot;
    @FXML private Label schdulingStatus;
    
    public void initialize() {	
    	listFlights.setItems(flightsListObs);
    	listFlights.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    	// Update the flight scheduling when the selected flight changes
    	listFlights.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Flight> ov, Flight old_val, Flight new_val) -> {
    		chosenFlight = listFlights.getSelectionModel().getSelectedItem();
    	     try {
				showScheduling();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	    });
    	FlightLogic.getInstance().getFlights();
	}
  
    

    @FXML
	public void searchFlightByID (ActionEvent event) {
	   	Flight flightToFind= FlightLogic.getInstance().findFlight(searchFlightID.getText());
	   	if(flightToFind!=null) {
	   		flightsListObs.clear();
	       	flightsListObs.add(flightToFind);
	       	listFlights.setItems(flightsListObs);
	       	listFlights.refresh();
	   	} else {
	   		Alert a = new Alert(AlertType.INFORMATION);
			a.setContentText("No search results found");
			a.show();
			searchFlightID.setText("");
	   	}
   }
   
     
    @FXML
    public void ClearSerchResultFlights (ActionEvent event) {
    	flightsListObs.clear();
     	flightsListObs.addAll(FlightLogic.getInstance().getFlights());
     	listFlights.setItems(flightsListObs);
     	listFlights.refresh();
    }

    @FXML
	 public void showScheduling() throws IOException {
    	//chosenFlight= listFlights.getSelectionModel().getSelectedItem();
    	flightAttendantsListObs.clear();
    	try {
	    		if(chosenFlight!=null) {
	    			if(chosenFlight.getChiefPilot()!=null)
	    				chiefPilot.setText(chosenFlight.getChiefPilot().toString());
	    			else
	    				chiefPilot.setText("null");
	    			if(chosenFlight.getSecPilot()!=null)
	    				secPilot.setText(chosenFlight.getSecPilot().toString());
	    			else
	    				secPilot.setText("null");
	    			if(!chosenFlight.getFlightAttendentsInFlight().isEmpty()) {
	    				flightAttendantsListObs.addAll(chosenFlight.getFlightAttendentsInFlight());
		    	    	listFlightAttendants.setItems(flightAttendantsListObs);
	    			}
	    			
	    			if(chosenFlight.getFlightAttendentsInFlight().size()==chosenFlight.getPlane().getNumOfFlightAttendants() && chosenFlight.getChiefPilot()!=null && chosenFlight.getSecPilot()!=null) {
	    	    		schdulingStatus.setText("Complete");
	    	    		schdulingStatus.setTextFill(Color.GREEN);
	    	    	} else {
	    	    		schdulingStatus.setText("Not completed");
	    	    		schdulingStatus.setTextFill(Color.RED);
	    	    	}
			    	
	    		} else 
	    			throw new NothingSelectedExceptions();
	    	} catch (NothingSelectedExceptions e) {
	    		JOptionPane.showMessageDialog(null, e.getMessage());
	    	} 

	    }
    
    
     @FXML
	 public void editScheduling()  {
    	 //chosenFlight= listFlights.getSelectionModel().getSelectedItem();
    	 try {
    		 if(chosenFlight!=null) 
    			 EmployeeManagerController.getInstance().openEditFlightSch();
    		 else 
	    			throw new NothingSelectedExceptions();
    		 
	    	} catch (NothingSelectedExceptions e) {
	    		JOptionPane.showMessageDialog(null, e.getMessage());
	    	}
 		
     }
    
    
    
    
    
    
}
