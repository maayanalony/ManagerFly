package boundary_FlightManager;

import java.util.ArrayList;

import boundary.TextFormats;
import control.PlaneLogic;
import entity.Plane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class PlaneController_Add {
	
	  @FXML private TextField tailNumber;
	  @FXML private TextField numFlightAttendant;
	  @FXML private TextField Fcol;
	  @FXML private TextField Frow;
	  @FXML private TextField Bcol;
	  @FXML private TextField Brow;
	  @FXML private TextField Tcol;
	  @FXML private TextField Trow;
	  
	  // invalids:
	  static ArrayList<Label> invalidLabels= new ArrayList<>(); // array of invalid
	  @FXML private Label invalidTailNumber;
	  @FXML private Label invalidExist;
	  @FXML private Label invalidNumFlightAttn;
	  @FXML private Label invalidSeatsInPlane;
	   
	   public void initialize() {
		   tailNumber.setTextFormatter(new TextFormatter<Integer>(TextFormats.getInstance().onlyNumbers()));
		   numFlightAttendant.setTextFormatter(new TextFormatter<Integer>(TextFormats.getInstance().onlyNumbers()));
		   tailNumber.setTextFormatter(new TextFormatter<String>(TextFormats.getInstance().onlyNumbers()));
		   Fcol.setTextFormatter(new TextFormatter<String>(TextFormats.getInstance().onlyNumbers()));
		   Frow.setTextFormatter(new TextFormatter<String>(TextFormats.getInstance().onlyNumbers()));
		   Bcol.setTextFormatter(new TextFormatter<String>(TextFormats.getInstance().onlyNumbers()));
		   Brow.setTextFormatter(new TextFormatter<String>(TextFormats.getInstance().onlyNumbers()));
		   Tcol.setTextFormatter(new TextFormatter<String>(TextFormats.getInstance().onlyNumbers()));
		   Trow.setTextFormatter(new TextFormatter<String>(TextFormats.getInstance().onlyNumbers()));
	   }
	   
	   public void addNewPlane (ActionEvent event) {
		   
		   if(invalidLabels!=null) {
				 for(Label l: invalidLabels) {
			     	   l.setVisible(false);
			        }
				   invalidLabels.clear();
			}
		   
	    	String inputTailNumber =  tailNumber.getText();
	 	   if(inputTailNumber == null || inputTailNumber.isEmpty() ) 
	 		   invalidLabels.add(invalidTailNumber);
	 	   if(inputTailNumber.length()!=4) 
	 	       invalidLabels.add(invalidTailNumber);
	 	   else if( PlaneLogic.getInstance().getPlanes().contains(new Plane(inputTailNumber)))
	 		   invalidLabels.add(invalidExist); // if Airport code id is already Exists
			
	 	  String inputNumFlightAttn=numFlightAttendant.getText();
	 	  if(inputNumFlightAttn == null || inputNumFlightAttn.isEmpty() ) 
			   invalidLabels.add(invalidNumFlightAttn);
	 	  
			String FirstCol = Fcol.getText();
			String FirstRow = Frow.getText();
			String BuisnessCol = Bcol.getText();
			String BuisnessRow = Brow.getText();
			String TouristCol = Tcol.getText();
			String TouristRow = Trow.getText();
			if(FirstCol.isEmpty() ||	FirstRow.isEmpty() ||
					BuisnessCol.isEmpty() || BuisnessRow.isEmpty() ||
					TouristCol.isEmpty() || TouristRow.isEmpty()) 
				   invalidLabels.add(invalidSeatsInPlane);
			
			if(invalidLabels.isEmpty()) {
				Plane plane = new Plane(inputTailNumber,Integer.parseInt(inputNumFlightAttn));
				PlaneLogic.getInstance().addPlane(plane, Integer.parseInt(FirstCol), Integer.parseInt(FirstRow), Integer.parseInt(BuisnessCol), Integer.parseInt(BuisnessRow), Integer.parseInt(TouristCol), Integer.parseInt(TouristRow) ); 
				   
			   } else {
				   for(Label l: invalidLabels) {
		        	   l.setVisible(true);
		        	   l.setText("X");
		        	   if(l.equals(invalidExist))
		        		   invalidExist.setText("This tail number is already exists");
		        	   if(l.equals(invalidTailNumber))
		        		   invalidTailNumber.setText("Only 4 numbers");
		           }
				   
			   }
			
		   
		  
	   }

}
