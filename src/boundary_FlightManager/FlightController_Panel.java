package boundary_FlightManager;


import java.io.IOException;

import javax.swing.JOptionPane;

import Exceptions.NothingSelectedExceptions;
import boundary.EnterController;
import boundary.FlightManagerController;
import boundary.Main;
import control.FlightLogic;
import entity.Flight;
import enumeration.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class FlightController_Panel {

    public static Flight chosenFlight;

    private ObservableList<Flight> flightsListObs  = FXCollections.observableArrayList(FlightLogic.getInstance().getFlights()); 
    @FXML private ListView<Flight> ListFlights;
    @FXML private TextField searchFlightID;
    @FXML private Button percentRepBtn;
    
    public void initialize() {	
    	if(EnterController.user.equals(User.FLIGHT_MANAGER))
			 percentRepBtn.setVisible(false);
		 else
			 percentRepBtn.setVisible(true);
    	
    	ListFlights.setItems(flightsListObs);
    	ListFlights.getSelectionModel().setSelectionMode(null);
	}
    
   
    @FXML
    public void addFlightBottun (ActionEvent event) {
    	FlightManagerController.getInstance().openAddFlight();
    }
  
    

    @FXML
	public void searchFlightByID (ActionEvent event) {
	   	Flight flightToFind= FlightLogic.getInstance().findFlight(searchFlightID.getText());
	   	if(flightToFind!=null) {
	   		flightsListObs.clear();
	       	flightsListObs.add(flightToFind);
	       	ListFlights.setItems(flightsListObs);
	       	ListFlights.refresh();
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
     	ListFlights.setItems(flightsListObs);
     	ListFlights.refresh();
    }
	
    /*---------------------Export Report Pop-UP------------------------*/
    
    @FXML
    public void openDataRep (ActionEvent event) {
    	Stage DataRepPopStage = new Stage();
    	Main.openPopScreen(DataRepPopStage, "/boundary_FlightManager/DataForMajorFlightsRep.fxml");
    	DataRepPopStage.onCloseRequestProperty();
    }
    
    @FXML
	 public void openEditFlight() throws IOException {
    	chosenFlight= ListFlights.getSelectionModel().getSelectedItem();	
    	try {
	    		if(chosenFlight!=null) {
	    			FlightManagerController.getInstance().openEditFlight();
			    	
	    		} else 
	    			throw new NothingSelectedExceptions();
	    	} catch (NothingSelectedExceptions e) {
	    		JOptionPane.showMessageDialog(null, e.getMessage());
	    	} 

	    } 
    
    @FXML
    public void openChooseCountry(ActionEvent event) {
    	Stage DataRepPopStage = new Stage();
    	Main.openPopScreen(DataRepPopStage, "/boundary_FlightManager/DataForPercentRep.fxml");
    	DataRepPopStage.onCloseRequestProperty();
    }
    
    
    
    /*-----------------------------------*/
    
  
    
    
    
    
    
    
    
}
