package boundary_FlightManager;

import boundary.EnterController;
import boundary.FlightManagerController;
import control.AirportLogic;
import entity.Airport;
import enumeration.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class AirportsController_Panel {
	static Airport chosenAirport;
	ObservableList<Airport> airportsObserve = FXCollections.observableArrayList(AirportLogic.getInstance().getAirport());
	
	@FXML public ListView<Airport> airportsList;
    @FXML private TextField searchAirportID;
    @FXML private Button ShowAllBtn;
    
    @FXML private Button addAirportBtn;
    @FXML private Button delAirportBtn;
    @FXML private Button editAirportBtn;
   
    public void initialize() {	
    	ShowAllBtn.setVisible(false); // before searching is not visible
    	airportsList.setItems(airportsObserve);
    	
    	if(EnterController.user.equals(User.FLIGHT_MANAGER)) {
			 addAirportBtn.setVisible(false);
    		 delAirportBtn.setVisible(false);
    		 editAirportBtn.setVisible(false);
    	}else {
			 addAirportBtn.setVisible(true);
			 delAirportBtn.setVisible(true);
			 editAirportBtn.setVisible(true);
    	}
    	
	}
    
   
    @FXML
	 public void searchAirportByCode (ActionEvent event) {
    	Airport airportToFind= findAirport(searchAirportID.getText());
    	if(airportToFind!=null) {
    		airportsObserve.clear();
        	airportsObserve.add(airportToFind);
        	airportsList.setItems(airportsObserve);
        	airportsList.refresh();
    	}
    	ShowAllBtn.setVisible(true);
    }
    
    @FXML
	public void showAllAirports (ActionEvent event) {
    	airportsObserve.clear();
       	airportsObserve.addAll(AirportLogic.getInstance().getAirport());
       	airportsList.setItems(airportsObserve);
       	airportsList.refresh();
       	ShowAllBtn.setVisible(false);
   	
   	
   }
    
    public Airport findAirport(String AirportId) {
		Airport airportsToFind = new Airport(AirportId);
		for(Airport f : AirportLogic.getInstance().getAirport()) {
			if(f.equals(airportsToFind))
				return f;
		}
		return null;
		
	}
    
    public void openAddAirport() {
    	FlightManagerController.getInstance().openAddAirport();
    }
    
  
    public void openEditAirport() {
    	if(airportsList.getSelectionModel().getSelectedItem()!=null) {
    		chosenAirport= airportsList.getSelectionModel().getSelectedItem();
    		FlightManagerController.getInstance().openEditAirport();
    	}
    	
    }
	
}
