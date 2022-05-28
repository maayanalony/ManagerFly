package boundary_FlightManager;

import java.sql.Date;

import javax.swing.JFrame;

import control.FlightLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class DataForMajorFlightsRepController {

    @FXML private TextField repNumSeats;
    @FXML private DatePicker repFrom;
    @FXML private DatePicker repUntil;
    @FXML private Button repExport;
    
    @FXML
    public void exportReport(ActionEvent event) {
    	boolean missing=false;
    	if(repNumSeats.getText().isEmpty() || repFrom.getValue()==null|| repUntil.getValue()==null) {
    		missing=true;
    	    Alert a = new Alert(AlertType.ERROR);
    	    a.setContentText("There are missing fields.");
    	    a.show();
    	}
    	if(!missing) {
    	    int numTourSeats = Integer.parseInt(repNumSeats.getText()); 
    	    Date from = Date.valueOf(repFrom.getValue());
    		Date until = Date.valueOf(repUntil.getValue());
    		JFrame report =FlightLogic.getInstance().exportMajorFlightsReport(numTourSeats, from, until);
    		report.setVisible(true);
    	}
   }

}
