package boundary;
// external border pane controller
public class EmployeeManagerController {
	
	public static EmployeeManagerController _instance;
	
	public EmployeeManagerController () {
	}
	
	public static EmployeeManagerController getInstance() {
		if (_instance == null)
			_instance = new EmployeeManagerController();
		return _instance;
	}
	
	public void changeUser() {
		Main.changeUser();
	}
	
	 public void openEmployeePanel() {
	    	Main.setGeneralLayout(Main.mainLayout, "/boundary_EmployeeManager/PanelEmployees.fxml");
	 }
	 	 
	 public void openAddFlightAttendant() {
	    	Main.setGeneralLayout(Main.mainLayout, "/boundary_EmployeeManager/FlightAttendant_Add.fxml");
	 }
	 public void openEditFlightAttendant() {
	    	Main.setGeneralLayout(Main.mainLayout, "/boundary_EmployeeManager/FlightAttendant_Edit.fxml");
	 }
	 
	 
	 public void openAddLandAttendant() {
	    	Main.setGeneralLayout(Main.mainLayout, "/boundary_EmployeeManager/LandAttendant_Add.fxml");
	 }
	 public void openEditLandAttendant() {
	    	Main.setGeneralLayout(Main.mainLayout, "/boundary_EmployeeManager/LandAttendant_Edit.fxml");
	 }
	 
	 
	 public void openAddPilot() {
	    	Main.setGeneralLayout(Main.mainLayout, "/boundary_EmployeeManager/Pilot_Add.fxml");
	 }
	 public void openEditPilot() {
	    	Main.setGeneralLayout(Main.mainLayout, "/boundary_EmployeeManager/Pilot_Edit.fxml");
	 }
	 
	 
	 public void openFlightSch() {
	    	Main.setGeneralLayout(Main.mainLayout, "/boundary_EmployeeManager/FlightScheduling.fxml");
	 }
	 public void openEditFlightSch() {
	    	Main.setGeneralLayout(Main.mainLayout, "/boundary_EmployeeManager/FlightScheduling_Edit.fxml");
	 }
	 public void openShifts() {
	    	Main.setGeneralLayout(Main.mainLayout, "/boundary_EmployeeManager/ShiftPanel.fxml");
	 }
	 public void openEditShiftSch() {
	    	Main.setGeneralLayout(Main.mainLayout, "/boundary_EmployeeManager/ShiftScheduling_Edit.fxml");
	 }

}
