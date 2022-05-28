package boundary_FlightManager;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import boundary.EnterController;
import boundary.Main;
import control.AirportLogic;
import control.FlightLogic;
import control.PlaneLogic;
import entity.Airport;
import entity.Flight;
import entity.Plane;
import enumeration.FlightStatus;
import enumeration.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Convert;

public class FlightController_Edit {
	
	private Flight flight= FlightController_Panel.chosenFlight;
	
	private ObservableList<Plane> planeList = FXCollections.observableArrayList(PlaneLogic.getInstance().getPlanes());
	private ObservableList<Airport> airportList = FXCollections.observableArrayList(AirportLogic.getInstance().getAirport());
	private ObservableList<FlightStatus> flightStatusObs = FXCollections.observableArrayList(FlightStatus.values());
	 @FXML private Label flightID;
	 @FXML private ComboBox<Plane> selectPlaneCombo;
	 @FXML private ComboBox<Airport> selectDepAirportCombo;
	 @FXML private ComboBox<Airport> selectArvAirportCombo;
	 @FXML private DatePicker SelectDepDate;
	 @FXML private DatePicker SelectArvDate;
	 @FXML private ComboBox<Integer> depHour;
	 @FXML private ComboBox<Integer> depMin;
	 @FXML private ComboBox<Integer> arvHour;
	 @FXML private ComboBox<Integer> arvMin;
	 @FXML private ComboBox<FlightStatus> selectStatus;
	 
	 @FXML private VBox addAirportBtn;
	 
	 private Timestamp depTime=null;
	 LocalDateTime inputDepDateTime=null;
	 private Timestamp arvTime= null;
	 LocalDateTime inputArvDateTime=null;
	 
	// invalids:
	 static ArrayList<Label> invalidLabels= new ArrayList<>(); // array of invalid	
	 @FXML private Label invalidDepTime;
	 @FXML private Label invalidArvTime;
	 @FXML private Label invalidDiffTimes;
	 @FXML private Label invalidDepAirport;
	 @FXML private Label invalidArvAirport;
	 @FXML private Label invalidPlane;
	 @FXML private Label invalidStatus;
	 
	/*----------------------------------------------------------------------------------------------------------*/
	/*---------------------------------------Initialize Methods---------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------------*/
	
	 public void initialize() {
		 initializeFlightsAirport();
		 initializeFlightsDateTime();
		 selectPlaneCombo.setItems(planeList); // initialize planes
		 selectStatus.setItems(flightStatusObs);
		 		 
		 if(EnterController.user.equals(User.FLIGHT_MANAGER))
			 addAirportBtn.setVisible(false);
		 else
			 addAirportBtn.setVisible(true);
		 
		 flightID.setText(flight.getFlightId());
		 selectPlaneCombo.setValue(flight.getPlane());
		 selectDepAirportCombo.setValue(flight.getDepAirport());
		 selectArvAirportCombo.setValue(flight.getArvAirport());
		 SelectDepDate.setValue(flight.getDepTime().toLocalDate());
		 SelectArvDate.setValue(flight.getArvTime().toLocalDate());
		 depHour.setValue(flight.getDepTime().getHour());
		 depMin.setValue(flight.getDepTime().getMinute());
		 arvHour.setValue(flight.getArvTime().getHour());
		 arvMin.setValue(flight.getArvTime().getMinute());
		 selectStatus.setValue(flight.getStatus());

	}
	 
	 public void initializeFlightsDateTime() {
		    ArrayList<Integer> hours =  new ArrayList<>();
			ArrayList<Integer> minets =  new ArrayList<>();
			for(int i=0; i<24;i++) {
				hours.add(i);
			}
			for(int i=0; i<60;i++) {
				minets.add(i);
			}
			depHour.setItems(FXCollections.observableArrayList(hours));
			depMin.setItems(FXCollections.observableArrayList(minets));
			arvHour.setItems(FXCollections.observableArrayList(hours));
			arvMin.setItems(FXCollections.observableArrayList(minets));
			
			// Flight can not be booked less then 2 months in advance
			SelectDepDate.setDayCellFactory(picker -> new DateCell() {
				public void updateItem(LocalDate date, boolean empty) {
					super.updateItem(date, empty);
					LocalDate today = LocalDate.now();
					LocalDate twoMonthLater = today.plus(2,ChronoUnit.MONTHS);
		            LocalDate maxDate = LocalDate.of(2025, 1, 1);
		            setDisable(date.compareTo(maxDate) > 0 || date.compareTo(twoMonthLater) < 0 );
				}
			});
			SelectDepDate.setEditable(false);
			
			SelectArvDate.setDayCellFactory(picker -> new DateCell() {
				public void updateItem(LocalDate date, boolean empty) {
					super.updateItem(date, empty);
					LocalDate today = LocalDate.now();
					LocalDate twoMoreMonth = today.plus(2,ChronoUnit.MONTHS);
		            LocalDate maxDate = LocalDate.of(2025, 1, 1);
		            setDisable(date.compareTo(maxDate) > 0 || date.compareTo(twoMoreMonth) < 0 );
				}
			});
			SelectArvDate.setEditable(false);
	 }
	 
	 public void initializeFlightsAirport() {
		 
			selectDepAirportCombo.setItems(airportList);
			selectArvAirportCombo.setItems(airportList);
		    selectDepAirportCombo.setOnAction(event -> {
	    		if (selectDepAirportCombo.getValue() != null) {
						ObservableList<Airport> airportList1 = FXCollections.observableArrayList(AirportLogic.getInstance().getAirport());
				    	airportList1.remove(selectDepAirportCombo.getValue());
				    	selectArvAirportCombo.setItems(airportList1);
					}
	    	});
	 }
	 
	 /*----------------------------------------------------------------------------------------------------------*/
	/*---------------------------------------------Edit Flight---------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------------*/
	 
	 
	@SuppressWarnings("deprecation")
	@FXML
	 public void addNewFlight (ActionEvent event) {
		 
		 for(Label l: invalidLabels) {
	     	   l.setVisible(false);
	        }
		   invalidLabels.clear();
		   
		   String inputID= flightID.getText();		 
	       
		   if(SelectDepDate.getValue()!=null && depHour.getValue()!=null && depMin.getValue()!=null) {
			   Date depD= Date.valueOf(SelectDepDate.getValue());
			   depTime = new Timestamp(depD.getTime());
			   depTime.setHours(depHour.getValue());
			   depTime.setMinutes(depMin.getValue());	
			   inputDepDateTime = Convert.getInstance().convertToLocalDateTime(depTime);
		   } else
			   invalidLabels.add(invalidDepTime);
		   
		   
		   if(SelectArvDate.getValue()!=null && arvHour.getValue()!=null && arvMin.getValue()!=null) {
			   Date arvD= Date.valueOf(SelectArvDate.getValue());
			   arvTime = new Timestamp(arvD.getTime());
			   arvTime.setHours(arvHour.getValue());
			   arvTime.setMinutes(arvMin.getValue());
			   inputArvDateTime = Convert.getInstance().convertToLocalDateTime(arvTime);
		   } else
			   invalidLabels.add(invalidArvTime);
		   
		   if(inputDepDateTime!=null && inputArvDateTime!=null) { // if arrival time is before departure time
			   if(!inputArvDateTime.isAfter(inputDepDateTime)) 
				   invalidLabels.add(invalidDiffTimes);
		   }

		   Airport inputDepAirport= selectDepAirportCombo.getValue();
		   if(inputDepAirport==null)
			   invalidLabels.add(invalidDepAirport);
		   
		   Airport inputArvAirport= selectArvAirportCombo.getValue();
		   if(inputArvAirport==null)
			   invalidLabels.add(invalidArvAirport);
		   	
		   Plane inputPlane= selectPlaneCombo.getValue();
	       if(inputPlane == null )
	    	   invalidLabels.add(invalidPlane);
	       
	       FlightStatus inputStatus= selectStatus.getValue();
	       if(inputStatus == null )
	    	   invalidLabels.add(invalidStatus);	
	       
	       if(!invalidLabels.isEmpty()) { // if there are invalid fields
	    	   for(Label l: invalidLabels) {
	        	   l.setVisible(true);
	        	   l.setText("X");
	        	   if(l.equals(invalidDiffTimes))
	        		   l.setText("arrival time is before departure time");
	           }
	       } else {/*----------------------------Creates new Flight to add to DB--------------------------------------------*/ 
	    	   
	    	   String s = FlightLogic.getInstance().editFlight(inputID, inputDepDateTime, inputArvDateTime, inputDepAirport, inputArvAirport, inputPlane, inputStatus);
	    	   
	    	   System.out.println(s);
	    	   
	    	   if(s.equals("flightOK")) {
	    		   Alert a = new Alert(AlertType.CONFIRMATION);
	    		   a.setContentText("The flight was successfully added");
	    		   a.show();
 
	    	   } else if(s.equals("The selected plane has a flight on the requested day")) {
	    		   Alert a = new Alert(AlertType.WARNING);
	    		   a.setContentText(s);
	    		   a.show();
	    	   } else if(s.equals("There is not enough time between the flights in the selected departure airport")) {
	    		   Alert a = new Alert(AlertType.WARNING);
	    		   a.setContentText(s);
	    		   a.show();
	    	   } else if(s.equals("There is not enough time between the flights in the selected arrival airport")) {
	    		   Alert a = new Alert(AlertType.WARNING);
	    		   a.setContentText(s);
	    		   a.show();
	    	   }
	    		   
	    		   
	       }
 		   

	 }
	 

	 
	 
	 
	 /*----------------------------------------------------------------------------------------------------------*/
		/*---------------------------------------Add Plane Pop-Up---------------------------------------------------*/
		/*----------------------------------------------------------------------------------------------------------*/	 
	    
	    
	    @FXML
	    public void openAddPlane (ActionEvent event) {
	    	Stage PlanePopStage = new Stage();
	    	Main.openPopScreen(PlanePopStage, "/boundary/frmAddPlane.fxml");
	    	PlanePopStage.onCloseRequestProperty();
	    	if(!PlanePopStage.isShowing())
	    		closeAddPlane();
	    	
	    	PlanePopStage.setOnCloseRequest(e -> closeAddPlane());
	    }
	    
	    @FXML
	    public <CloseEvent> void closeAddPlane() {
	    	planeList.clear();
	    	planeList.addAll(PlaneLogic.getInstance().getPlanes());
	    	selectPlaneCombo.setItems(planeList);
	    }
	    

		    /*----------------------------------------------------------------------------------------------------------*/
			/*---------------------------------------Add Airport Pop-Up---------------------------------------------------*/
			/*----------------------------------------------------------------------------------------------------------*/	

		    
		    
		    @FXML
		    public void openAddAirport (ActionEvent event) {
		    	Stage AirportPopStage = new Stage();
		    	Main.openPopScreen(AirportPopStage, "/boundary/frmAddAirport.fxml");
		    	AirportPopStage.onCloseRequestProperty();
		    	if(!AirportPopStage.isShowing())
		    		closeAddAirport();
		    	
		    	AirportPopStage.setOnCloseRequest(e -> closeAddAirport());
		    }
		    
		    @FXML
		    public <CloseEvent> void closeAddAirport() {
		    	airportList.clear();
		    	airportList.addAll(AirportLogic.getInstance().getAirport());
		    	initializeFlightsAirport();
		    }
			
}
