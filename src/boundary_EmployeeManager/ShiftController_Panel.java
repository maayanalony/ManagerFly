package boundary_EmployeeManager;

import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import Exceptions.NothingSelectedExceptions;
import boundary.EmployeeManagerController;
import boundary.Main;
import control.ShiftLogic;
import entity.LandAttendant;
import entity.Shift;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

public class ShiftController_Panel {

    public static Shift chosenShift;

    private ObservableList<Shift> shiftsObs  = FXCollections.observableArrayList(ShiftLogic.getInstance().getShifts()); 
    private ObservableList<LandAttendant> checkInObs  = FXCollections.observableArrayList(); 
    private ObservableList<LandAttendant> luggageObs  = FXCollections.observableArrayList(); 
    private ObservableList<LandAttendant> allocationObs  = FXCollections.observableArrayList(); 
    
    @FXML private ListView<Shift> listShifts;
    @FXML private ListView<LandAttendant> listCheckIn;
    @FXML private ListView<LandAttendant> listLuggage;
    @FXML private ListView<LandAttendant> listAllocation;
    @FXML private DatePicker searchShift;
    @FXML private VBox schedulingBox;
    
    public void initialize() {	
    	listShifts.setItems(shiftsObs);
    	listShifts.getSelectionModel().setSelectionMode(null);
    	schedulingBox.setVisible(false);
    	ShiftLogic.getInstance().getShifts();
    	for(Shift s: ShiftLogic.getInstance().getShifts()) {
    		if(s.getCheckIn()!=null)
    			checkInObs.addAll(s.getCheckIn());
    		if(s.getLuggage()!=null)
    			luggageObs.addAll(s.getLuggage());
    		if(s.getAllocation()!=null)
    			allocationObs.addAll(s.getAllocation());    			
    	}
	}
  
    

    @FXML
	public void searchShiftByDate (ActionEvent event) {
	   	LocalDate shiftToFind= searchShift.getValue();
	   	if(shiftToFind!=null) {
	   		shiftsObs.clear();
	   		for(Shift s: ShiftLogic.getInstance().getShifts()) {
	   			if(s.getStartTime().toLocalDate().isEqual(shiftToFind)) 
	   				shiftsObs.add(s);
	   		}
	       	listShifts.setItems(shiftsObs);
	       	listShifts.refresh();
	   	} else {
	   		Alert a = new Alert(AlertType.INFORMATION);
			a.setContentText("No search results found");
			a.show();
			searchShift.setValue(null);
	   	}
   }
   
     
    @FXML
    public void ClearSerchResultFlights (ActionEvent event) {
    	shiftsObs.clear();
     	shiftsObs.addAll(ShiftLogic.getInstance().getShifts());
     	listShifts.setItems(shiftsObs);
     	listShifts.refresh();
    }

    @FXML
	 public void showScheduling() throws IOException {
    	chosenShift= listShifts.getSelectionModel().getSelectedItem();
    	checkInObs.clear();
    	luggageObs.clear();
    	allocationObs.clear();
    	schedulingBox.setVisible(true);
    	try {
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
	    				for(LandAttendant l: chosenShift.getAllocation())
	    					System.out.println(l);
	    				allocationObs.addAll(chosenShift.getAllocation());
	    				listAllocation.setItems(allocationObs);
	    			}
	    			
			    	
	    		} else 
	    			throw new NothingSelectedExceptions();
	    	} catch (NothingSelectedExceptions e) {
	    		JOptionPane.showMessageDialog(null, e.getMessage());
	    	} 

	    }
    
    
     @FXML
	 public void editScheduling()  {
    	 chosenShift= listShifts.getSelectionModel().getSelectedItem();
    	 try {
    		 if(chosenShift!=null) 
    			 EmployeeManagerController.getInstance().openEditShiftSch();
    		 else 
	    			throw new NothingSelectedExceptions();
    		 
	    	} catch (NothingSelectedExceptions e) {
	    		JOptionPane.showMessageDialog(null, e.getMessage());
	    	}
 		
     }
    
     @FXML
	    public void openAddShift (ActionEvent event) {
	    	Stage ShiftPopStage = new Stage();
	    	Main.openPopScreen(ShiftPopStage, "/boundary_EmployeeManager/Shift_Add.fxml");
	    	ShiftPopStage.onCloseRequestProperty();
	    	if(!ShiftPopStage.isShowing())
	    		closeAddShift();
	    	
	    	ShiftPopStage.setOnCloseRequest(e -> closeAddShift());
	    }
	    
	    @FXML
	    public <CloseEvent> void closeAddShift() {
	    	shiftsObs.clear();
	    	shiftsObs.addAll(ShiftLogic.getInstance().getShifts());
	    	listShifts.setItems(shiftsObs);
	     	listShifts.refresh();
	    }
    
    
    
    
}
