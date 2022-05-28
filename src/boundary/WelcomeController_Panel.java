package boundary;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.swing.JOptionPane;

import Exceptions.NothingSelectedExceptions;
import control.FlightLogic;
import entity.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class WelcomeController_Panel {

    public static Flight chosenFlight;

    private ObservableList<Flight> updatedTodayFlightsObs  = FXCollections.observableArrayList(FlightLogic.getInstance().getUpdatedTodayFlights()); 
    private ObservableList<Flight> needToScheduleListObs  = FXCollections.observableArrayList(); 
    private ObservableList<Flight> newFlightsListObs  = FXCollections.observableArrayList(); 
    @FXML private ListView<Flight> listUpdatedToday;
    @FXML private ListView<Flight> listNeedToSchedule;
    @FXML private ListView<Flight> listNewFlights;
    
   
	public void initialize() {	
		
    	for(Flight f: FlightLogic.getInstance().getFlights()) {
    		if(f.getChiefPilot()==null && f.getSecPilot()==null && f.getFlightAttendentsInFlight().isEmpty()) {
    			newFlightsListObs.add(f);
    			updatedTodayFlightsObs.remove(f);
    		}
    		if(ChronoUnit.DAYS.between(LocalDateTime.now(), f.getDepTime())<=14) {
    			if(f.getFlightAttendentsInFlight()==null)
    				needToScheduleListObs.add(f);
    			else if(f.getFlightAttendentsInFlight().size()!=f.getPlane().getNumOfFlightAttendants() || f.getChiefPilot()==null || f.getSecPilot()==null)
    				needToScheduleListObs.add(f);
    		}
    	}
    	listUpdatedToday.setItems(updatedTodayFlightsObs);
    	listNeedToSchedule.setItems(needToScheduleListObs);
    	listNewFlights.setItems(newFlightsListObs);
    	listUpdatedToday.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    	listNeedToSchedule.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    	listNewFlights.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}
    
    
     @FXML
	 public void openEdit()  {
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
