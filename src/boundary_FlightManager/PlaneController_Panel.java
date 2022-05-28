package boundary_FlightManager;

import boundary.FlightManagerController;
import control.PlaneLogic;
import control.SeatLogic;
import entity.Plane;
import entity.SeatInPlane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;


public class PlaneController_Panel {
	
	ObservableList<Plane> planesObserve = FXCollections.observableArrayList(PlaneLogic.getInstance().getPlanes());
	
	
	@FXML public ListView<Plane> planesList;
	@FXML public ListView<SeatInPlane> seatsInPlane;
    @FXML private TextField searchPlaneID;
    @FXML private Button ShowAllBtn;
    
   
    public void initialize() {	
    	ShowAllBtn.setVisible(false); // before searching is not visible
    	planesList.setItems(planesObserve);
    	
    	
	}
    
    public void showSeats(ActionEvent event) {
    	Plane selectedPlane= planesList.getSelectionModel().getSelectedItem();
    	if(selectedPlane==null) {
    		Alert a = new Alert(AlertType.WARNING);
    		a.setContentText("For watching the seats in the plane you have to choose a plane");
 	    	a.show();
    	} else {
	    	ObservableList<SeatInPlane> seatsObserve = 
	    			FXCollections.observableArrayList(SeatLogic.getInstance().getSeatsInPlane(selectedPlane.getTailNumber()));
	    	seatsInPlane.setItems(seatsObserve);
    	}
    }
    
   
    @FXML
	 public void searchPlaneByID (ActionEvent event) {
    	Plane planeToFind= findPlane(searchPlaneID.getText());
    	if(planeToFind!=null) {
    		planesObserve.clear();
        	planesObserve.add(planeToFind);
        	planesList.setItems(planesObserve);
        	planesList.refresh();
    	}
    	ShowAllBtn.setVisible(true);
    }
    
    @FXML
	public void showAllPlanes (ActionEvent event) {
    	planesObserve.clear();
       	planesObserve.addAll(PlaneLogic.getInstance().getPlanes());
       	planesList.setItems(planesObserve);
       	planesList.refresh();
       	ShowAllBtn.setVisible(false);
   	
   	
   }
    
    public Plane findPlane(String PlaneId) {
		Plane planesToFind = new Plane(PlaneId);
		for(Plane p : PlaneLogic.getInstance().getPlanes()) {
			if(p.equals(planesToFind))
				return p;
		}
		return null;
		
	}
    
    public void openAddPlane() {
    	FlightManagerController.getInstance().openAddPlane();
    }
	
}
