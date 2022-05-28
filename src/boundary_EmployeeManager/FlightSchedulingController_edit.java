package boundary_EmployeeManager;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.swing.JOptionPane;

import Exceptions.NothingSelectedExceptions;
import control.FlightAttendantLogic;
import control.FlightLogic;
import control.PilotLogic;
import entity.Flight;
import entity.FlightAttendant;
import entity.Pilot;
import enumeration.FlightSaleStatus;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.AlertType;

public class FlightSchedulingController_edit {
		
		private ObservableList<Pilot> pilotObserve = FXCollections.observableArrayList(PilotLogic.getInstance().getPilots());
		private ObservableList<FlightAttendant> attendantsObserve = FXCollections.observableArrayList();
		private ObservableList<FlightAttendant> attendantsInFlightObserve = FXCollections.observableArrayList();
	    @FXML private Label flightLab;
	    @FXML private ListView<FlightAttendant> attendantsList;
	    @FXML private ListView<FlightAttendant> attendantstInFlightList;
	    @FXML private ComboBox<Pilot> selectChiefPilot;
	    @FXML private ComboBox<Pilot> selectSecPilot;
	    @FXML private Label numOfNeededAttendants;
	    @FXML private Label currentNumOfAttendants;
	    @FXML private Label schdulingStatus;
	    
	    private Flight flight = FlightSchedulingController_Panel.chosenFlight;
	    private int numOfNeededAtt= flight.getPlane().getNumOfFlightAttendants();
	    private int currentnumOfAtt=flight.getFlightAttendentsInFlight().size();
	    		
	    public void initialize() {	
	    	selectChiefPilot.setItems(pilotObserve);
	    	selectSecPilot.setItems(pilotObserve);
	    	flightLab.setText(flight.toString());
	    	
	    	// setting current flight scheduling details
	    	if(flight.getChiefPilot()!=null) 
	    		selectChiefPilot.setValue(flight.getChiefPilot());
	    	
	    	if(flight.getSecPilot()!=null) 
	    		selectSecPilot.setValue(flight.getSecPilot());
	    	
	    	attendantsInFlightObserve.addAll(flight.getFlightAttendentsInFlight());
	    	attendantsObserve.addAll(FlightAttendantLogic.getInstance().getFlightAttendants());
	    	attendantsObserve.removeAll(attendantsInFlightObserve);
	    	attendantsList.setItems(attendantsObserve);
	    	attendantstInFlightList.setItems(attendantsInFlightObserve);
	    	
	    	numOfNeededAttendants.setText(""+numOfNeededAtt);
	    	currentNumOfAttendants.setText(""+currentnumOfAtt);
	    	
	    	// Listener to pilot combo boxes for updating schedule status in real time
	    	selectChiefPilot.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Pilot> ov, Pilot old_val, Pilot new_val) -> {
	    		setSchdulingStatus();
	    	    });
	    	selectSecPilot.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Pilot> ov, Pilot old_val, Pilot new_val) -> {
	    		setSchdulingStatus();
	    	    });
	    	
	    	setSchdulingStatus(); // set current schedule status
	    	
		}
	    
	    // real time feedback on scheduling status
	    private void setSchdulingStatus() {
	    	if(numOfNeededAtt==currentnumOfAtt && selectChiefPilot.getValue()!=null && selectSecPilot.getValue()!=null) {
	    		schdulingStatus.setText("Complete");
	    		schdulingStatus.setTextFill(Color.GREEN);
	    	} else {
	    		schdulingStatus.setText("Not completed");
	    		schdulingStatus.setTextFill(Color.RED);
	    	}
	    	
	     }
	    
	    // adding flight attendant to the flight
	    public void addFlightAttedant() {
	    	FlightAttendant chosenAttendant= attendantsList.getSelectionModel().getSelectedItem();
	    	 try {
	    		 if(chosenAttendant!=null) {
	    			 attendantsInFlightObserve.add(chosenAttendant);
	    			 attendantsObserve.remove(chosenAttendant);
	    			 attendantsList.setItems(attendantsObserve);
	    		     attendantstInFlightList.setItems(attendantsInFlightObserve);
	    		     attendantsList.refresh();
	    		     attendantstInFlightList.refresh();
	    		     FlightLogic.getInstance().addFlightAttendantsToFlight(flight, chosenAttendant);	
	    		     currentnumOfAtt++;
	    		     currentNumOfAttendants.setText(""+currentnumOfAtt);
	    		     setSchdulingStatus(); 
	    		 } else 
		    			throw new NothingSelectedExceptions();
	    		 
		    	} catch (NothingSelectedExceptions e) {
		    		JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
	    }
	    
	    // removing flight attendant from the flight
	    public void removeFlightAttedant() {
	    	FlightAttendant chosenAttendant= attendantstInFlightList.getSelectionModel().getSelectedItem();
	    	 try {
	    		 if(chosenAttendant!=null) {
	    			 attendantsInFlightObserve.remove(chosenAttendant);
	    			 attendantsObserve.add(chosenAttendant);
	    			 attendantsList.setItems(attendantsObserve);
	    		     attendantstInFlightList.setItems(attendantsInFlightObserve);
	    		     attendantsList.refresh();
	    		     attendantstInFlightList.refresh();
	    		     FlightLogic.getInstance().removeFlightAttendantsToFlight(flight, chosenAttendant);
	    		     currentnumOfAtt--;
	    		     currentNumOfAttendants.setText(""+currentnumOfAtt);
	    		     setSchdulingStatus(); 
	    		 } else 
		    			throw new NothingSelectedExceptions();
	    		 
		    	} catch (NothingSelectedExceptions e) {
		    		JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
	    }
	    
	    public void saveScheduling() {
	    	// add or update pilots to the flight 
	    	if(selectChiefPilot.getValue()!=null)  
	    		flight.setChiefPilot(selectChiefPilot.getValue());
	    	if(selectSecPilot.getValue()!=null)
	    		flight.setSecPilot(selectSecPilot.getValue());	    	
	    	FlightLogic.getInstance().editPilotsInFlight(flight);

	    	if(ChronoUnit.DAYS.between(LocalDateTime.now(), flight.getDepTime())>=14) { // alert for finish scheduling in time
	    		if(flight.getChiefPilot()!=null && flight.getSecPilot()!=null && currentnumOfAtt==numOfNeededAtt) {
	    			FlightLogic.getInstance().editFlightSaleStatus(flight.getFlightId(), FlightSaleStatus.REGULAR_SALE);
	    			Alert a = new Alert(AlertType.CONFIRMATION);
			    	a.setContentText("The schduling was successfully updated.\nThe Scheduling for this flight is complete!");
			    	a.show();
	    		}else { // alert for uncompleted scheduling
	    			FlightLogic.getInstance().editFlightSaleStatus(flight.getFlightId(), FlightSaleStatus.PRE_SALE);
			    	Alert a2 = new Alert(AlertType.WARNING);
			    	a2.setContentText("The Scheduling for this flight is not complete");
			    	a2.show();
			    	Alert a = new Alert(AlertType.CONFIRMATION);
			    	a.setContentText("The schduling was successfully updated./nA message has been sent to the relevant employees.");
			    	a.show();
	    		}
	    	} else { // alert for uncompleted scheduling 14 days or less before the departure time of the flight
	    		if(flight.getChiefPilot()==null || flight.getSecPilot()==null || currentnumOfAtt<numOfNeededAtt) {
	    			FlightLogic.getInstance().editFlightSaleStatus(flight.getFlightId(), FlightSaleStatus.PRE_SALE);
	    			Alert a = new Alert(AlertType.WARNING);
			    	a.setContentText("There are "+ChronoUnit.DAYS.between(LocalDateTime.now(), flight.getDepTime())+" days left until the departure time of this flight."
			    			+ "\nNote: The flight may be canceled due to a shortage of pilots or flight attendants");
			    	a.show();
	    		}
	    			
	    	}
	    	
	    }


}
