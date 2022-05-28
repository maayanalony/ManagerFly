package boundary_EmployeeManager;

import boundary.EmployeeManagerController;
import control.FlightAttendantLogic;
import control.LandAttendantLogic;
import control.PilotLogic;
import entity.Employee;
import entity.FlightAttendant;
import entity.LandAttendant;
import entity.Pilot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class PanelEmployeesController {
	static Employee chosenEmployee;
	ObservableList<FlightAttendant> flightAttendantsObserve = FXCollections.observableArrayList();
	ObservableList<LandAttendant> landAttendantsObserve = FXCollections.observableArrayList();
	ObservableList<Pilot> pilotsObserve = FXCollections.observableArrayList();
	
	@FXML public ListView<FlightAttendant> flightAttendantsList;
	@FXML public ListView<LandAttendant> landAttendantsList;
	@FXML public ListView<Pilot> pilotsList;
    @FXML private TextField searchEmployeeID;
    @FXML private Button ShowAllBtn;
    @FXML private ButtonBar addButtonBar;

   
    public void initialize() {	
    	if(ShowAllBtn.isVisible())
    		ShowAllBtn.setVisible(false); // before searching is not visible
    	initializeEmployeesLists();
	}
    
    public void initializeEmployeesLists() {
    	flightAttendantsObserve.clear();
		landAttendantsObserve.clear();
		pilotsObserve.clear();
		flightAttendantsObserve.addAll(FlightAttendantLogic.getInstance().getFlightAttendants());
		landAttendantsObserve.addAll(LandAttendantLogic.getInstance().getLandAttendants());
		pilotsObserve.addAll(PilotLogic.getInstance().getPilots());
		flightAttendantsList.setItems(flightAttendantsObserve);
    	landAttendantsList.setItems(landAttendantsObserve);
    	pilotsList.setItems(pilotsObserve);
    	
    	addButtonBar.setVisible(false);
    }
    
   
    @FXML
	 public void searchEmployeeByID (ActionEvent event) {
    	String employeeToFind= searchEmployeeID.getText();
    	
    	if(employeeToFind!=null) {
    		flightAttendantsObserve.clear();
    		landAttendantsObserve.clear();
    		pilotsObserve.clear();
    		for(Pilot emp: PilotLogic.getInstance().getPilots()) {
        		if(emp.getEmployeeId().equals(employeeToFind)) {
        			pilotsObserve.add(emp);
        			pilotsList.setItems(pilotsObserve);
        			flightAttendantsList.setItems(flightAttendantsObserve);
        	    	landAttendantsList.setItems(landAttendantsObserve);
        		}
    		}
    		for(FlightAttendant emp: FlightAttendantLogic.getInstance().getFlightAttendants()) {
        		if(emp.getEmployeeId().equals(employeeToFind)) {
        			flightAttendantsObserve.add(emp);
        			pilotsList.setItems(pilotsObserve);
        			flightAttendantsList.setItems(flightAttendantsObserve);
        	    	landAttendantsList.setItems(landAttendantsObserve);
        		}
    		}
    		for(LandAttendant emp: LandAttendantLogic.getInstance().getLandAttendants()) {
        		if(emp.getEmployeeId().equals(employeeToFind)) {
        			landAttendantsObserve.add(emp);
        			pilotsList.setItems(pilotsObserve);
        			flightAttendantsList.setItems(flightAttendantsObserve);
        	    	landAttendantsList.setItems(landAttendantsObserve);
        		}
    		}
    		
        	
    	}
    	ShowAllBtn.setVisible(true);
    }
    
    
    @FXML
	public void showAllEmployees (ActionEvent event) {
    	flightAttendantsObserve.clear();
		landAttendantsObserve.clear();
		pilotsObserve.clear();
		flightAttendantsObserve.addAll(FlightAttendantLogic.getInstance().getFlightAttendants());
		landAttendantsObserve.addAll(LandAttendantLogic.getInstance().getLandAttendants());
		pilotsObserve.addAll(PilotLogic.getInstance().getPilots());
		pilotsList.setItems(pilotsObserve);
		flightAttendantsList.setItems(flightAttendantsObserve);
    	landAttendantsList.setItems(landAttendantsObserve);
       	ShowAllBtn.setVisible(false);
   	
   	
   } 

    
  
    public void openEditEmployee() {
    	if(pilotsList.getSelectionModel().getSelectedItem()!=null) {
    		chosenEmployee= pilotsList.getSelectionModel().getSelectedItem();
    		EmployeeManagerController.getInstance().openEditPilot();
    	}
    	else if(flightAttendantsList.getSelectionModel().getSelectedItem()!=null) {
    		chosenEmployee= flightAttendantsList.getSelectionModel().getSelectedItem();
    		EmployeeManagerController.getInstance().openEditFlightAttendant();
    	}
    	else if(landAttendantsList.getSelectionModel().getSelectedItem()!=null) {
    		chosenEmployee= landAttendantsList.getSelectionModel().getSelectedItem();
    		EmployeeManagerController.getInstance().openEditLandAttendant();
    	}
    	
    }
    
    public void openAddEmployee() {
    	addButtonBar.setVisible(true);
    }
    
    public void openAddPilot() {
    	EmployeeManagerController.getInstance().openAddPilot();
    }
    public void openAddFlightAttendant() {
    	EmployeeManagerController.getInstance().openAddFlightAttendant();;
    }
    public void openAddLandAttendant() {
    	EmployeeManagerController.getInstance().openAddLandAttendant();;
    }
	
}
