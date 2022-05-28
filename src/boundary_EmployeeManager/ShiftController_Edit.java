package boundary_EmployeeManager;

import javax.swing.JOptionPane;

import Exceptions.NothingSelectedExceptions;
import control.LandAttendantLogic;
import control.ShiftLogic;
import entity.LandAttendant;
import entity.Shift;
import enumeration.LandAttendantRole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;

public class ShiftController_Edit {

    private Shift chosenShift= ShiftController_Panel.chosenShift;

    private ObservableList<LandAttendant> landAttendantObs  = FXCollections.observableArrayList(); 
    private ObservableList<LandAttendant> checkInObs  = FXCollections.observableArrayList(); 
    private ObservableList<LandAttendant> luggageObs  = FXCollections.observableArrayList(); 
    private ObservableList<LandAttendant> allocationObs  = FXCollections.observableArrayList(); 
    
    @FXML private Label shiftDetails;
    @FXML private ListView<LandAttendant> listLandAttendants;
    @FXML private ListView<LandAttendant> listCheckIn;
    @FXML private ListView<LandAttendant> listLuggage;
    @FXML private ListView<LandAttendant> listAllocation;
    
    public void initialize() {	
    	shiftDetails.setText(chosenShift.toString());
    	showScheduling();
    	landAttendantObs.addAll(LandAttendantLogic.getInstance().getLandAttendants());
    	landAttendantObs.removeAll(chosenShift.getCheckIn());
    	landAttendantObs.removeAll(chosenShift.getLuggage());
    	landAttendantObs.removeAll(chosenShift.getAllocation());
    	listLandAttendants.setItems(landAttendantObs);
	}

    @FXML
	 public void showScheduling() {
    	checkInObs.clear();
    	luggageObs.clear();
    	allocationObs.clear();
    	if(chosenShift!=null) {
			if(chosenShift.getCheckIn()!=null) {
				checkInObs.addAll(chosenShift.getCheckIn());
				listCheckIn.setItems(checkInObs);
			}
			if(chosenShift.getLuggage()!=null) {
				luggageObs.addAll(chosenShift.getLuggage());
				listLuggage.setItems(luggageObs);
			}
			if(chosenShift.getAllocation()!=null) {
				allocationObs.addAll(chosenShift.getAllocation());
				listAllocation.setItems(allocationObs);
			}
    	}
	 }
    
    
     @FXML
     public void addAttToCheckIn() {
	    	LandAttendant chosenAttendant= listLandAttendants.getSelectionModel().getSelectedItem();
	    	 try {
	    		 if(chosenAttendant!=null) {
	    			 checkInObs.add(chosenAttendant);
	    			 landAttendantObs.remove(chosenAttendant);
	    			 listCheckIn.setItems(checkInObs);
	    			 listLandAttendants.setItems(landAttendantObs);
	    			 listCheckIn.refresh();
	    			 listLandAttendants.refresh();
	    		     ShiftLogic.getInstance().addAttendantsToShift(chosenShift, chosenAttendant, LandAttendantRole.CHECKING_TICKETS);	    			 
	    		 } else 
		    			throw new NothingSelectedExceptions();
	    		 
		    	} catch (NothingSelectedExceptions e) {
		    		JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
	    }
     @FXML
     public void removeAttFromCheckIn() {
	    	LandAttendant chosenAttendant= listCheckIn.getSelectionModel().getSelectedItem();
	    	 try {
	    		 if(chosenAttendant!=null) {
	    			 checkInObs.remove(chosenAttendant);
	    			 landAttendantObs.add(chosenAttendant);
	    			 listCheckIn.setItems(checkInObs);
	    			 listLandAttendants.setItems(landAttendantObs);
	    			 listCheckIn.refresh();
	    			 listLandAttendants.refresh();
	    		     ShiftLogic.getInstance().removeAttendantFromShift(chosenShift, chosenAttendant, LandAttendantRole.CHECKING_TICKETS);	    			 
	    		 } else 
		    			throw new NothingSelectedExceptions();
	    		 
		    	} catch (NothingSelectedExceptions e) {
		    		JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
	    }
     @FXML
     public void addAttToLuggage() {
	    	LandAttendant chosenAttendant= listLandAttendants.getSelectionModel().getSelectedItem();
	    	 try {
	    		 if(chosenAttendant!=null) {
	    			 luggageObs.add(chosenAttendant);
	    			 landAttendantObs.remove(chosenAttendant);
	    			 listLuggage.setItems(luggageObs);
	    			 listLandAttendants.setItems(landAttendantObs);
	    			 listLuggage.refresh();
	    			 listLandAttendants.refresh();
	    		     ShiftLogic.getInstance().addAttendantsToShift(chosenShift, chosenAttendant, LandAttendantRole.LABALING_AND_SENDING_LUGGAGE);	    			 
	    		 } else 
		    			throw new NothingSelectedExceptions();
	    		 
		    	} catch (NothingSelectedExceptions e) {
		    		JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
	    }
     @FXML
     public void removeAttFromLuggage() {
	    	LandAttendant chosenAttendant= listLuggage.getSelectionModel().getSelectedItem();
	    	 try {
	    		 if(chosenAttendant!=null) {
	    			 luggageObs.remove(chosenAttendant);
	    			 landAttendantObs.add(chosenAttendant);
	    			 listLuggage.setItems(luggageObs);
	    			 listLandAttendants.setItems(landAttendantObs);
	    			 listLuggage.refresh();
	    			 listLandAttendants.refresh();
	    		     ShiftLogic.getInstance().removeAttendantFromShift(chosenShift, chosenAttendant, LandAttendantRole.LABALING_AND_SENDING_LUGGAGE);	    			 
	    		 } else 
		    			throw new NothingSelectedExceptions();
	    		 
		    	} catch (NothingSelectedExceptions e) {
		    		JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
	    }
     @FXML
     public void addAttToAllocation() {
	    	LandAttendant chosenAttendant= listLandAttendants.getSelectionModel().getSelectedItem();
	    	 try {
	    		 if(chosenAttendant!=null) {
	    			 allocationObs.add(chosenAttendant);
	    			 landAttendantObs.remove(chosenAttendant);
	    			 listAllocation.setItems(allocationObs);
	    			 listLandAttendants.setItems(landAttendantObs);
	    			 listAllocation.refresh();
	    			 listLandAttendants.refresh();
	    		     ShiftLogic.getInstance().addAttendantsToShift(chosenShift, chosenAttendant, LandAttendantRole.ALLOCATION_PLACES);	    			 
	    		 } else 
		    			throw new NothingSelectedExceptions();
	    		 
		    	} catch (NothingSelectedExceptions e) {
		    		JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
	    }
     @FXML
     public void removeAttFromAllocation() {
	    	LandAttendant chosenAttendant= listAllocation.getSelectionModel().getSelectedItem();
	    	 try {
	    		 if(chosenAttendant!=null) {
	    			 allocationObs.remove(chosenAttendant);
	    			 landAttendantObs.add(chosenAttendant);
	    			 listAllocation.setItems(allocationObs);
	    			 listLandAttendants.setItems(landAttendantObs);
	    			 listAllocation.refresh();
	    			 listLandAttendants.refresh();
	    		     ShiftLogic.getInstance().removeAttendantFromShift(chosenShift, chosenAttendant, LandAttendantRole.ALLOCATION_PLACES);	    			 
	    		 } else 
		    			throw new NothingSelectedExceptions();
	    		 
		    	} catch (NothingSelectedExceptions e) {
		    		JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
	    }
     
     @FXML
     public void saveShift() {
    	 Alert a = new Alert(AlertType.CONFIRMATION);
	    	a.setContentText("The schduling was successfully updated./nA message has been sent to the relevant employees.");
	    	a.show();
     }
    
    
    
}
