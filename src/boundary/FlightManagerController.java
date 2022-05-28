package boundary;

import control.ExportControl;
import control.ImportControl;
import enumeration.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

public class FlightManagerController {
	
	public static FlightManagerController _instance;
	
	public FlightManagerController () {
	}
	
	public static FlightManagerController getInstance() {
		if (_instance == null)
			_instance = new FlightManagerController();
		return _instance;
	}
	
	@FXML private MenuItem addAirportItem;
	@FXML private Label managerLab;
	
	public void initialize() {
		if(EnterController.user.equals(User.CHIEF_FLIGHT_MANAGER)) {
			addAirportItem.setVisible(true);
			managerLab.setText("Chief Flight Manager");
		}else {
			addAirportItem.setVisible(false);
			managerLab.setText("Flight Manager");
		}
	}
	
	public void changeUser() {
		Main.changeUser();
	}
	
	public void openFlightPanel() {
	    Main.setGeneralLayout(Main.mainLayout, "/boundary_FlightManager/PanelFlights.fxml");
	 }
	 public void openAddFlight() {
	    Main.setGeneralLayout(Main.mainLayout, "/boundary_FlightManager/Flight_Add.fxml");
	 }
	 public void openEditFlight() {
		    Main.setGeneralLayout(Main.mainLayout, "/boundary_FlightManager/Flight_Edit.fxml");
	}
	 
	 public void openAirpoertPanel() {
	    Main.setGeneralLayout(Main.mainLayout, "/boundary_FlightManager/PanelAirports.fxml");
	 }
	 public void openAddAirport() {
		 Main.setGeneralLayout(Main.mainLayout, "/boundary_FlightManager/Airport_Add.fxml");
	 }
	 public void openEditAirport() {
		 Main.setGeneralLayout(Main.mainLayout, "/boundary_FlightManager/Airport_Edit.fxml");
	 }
	 
	 public void openPlanePanel() {
	    Main.setGeneralLayout(Main.mainLayout, "/boundary_FlightManager/PanelPlanes.fxml");
	 }
	 public void openAddPlane() {
	    Main.setGeneralLayout(Main.mainLayout, "/boundary_FlightManager/Plane_Add.fxml");
	 }
	 
	public void exportToJason() {
		ExportControl.getInstance().exportToJSON();
	}
	
	public void importXml() throws Exception {
		ImportControl.getInstance().importFromXML("resources/flightStatus.xml");
	}
	
	

}
