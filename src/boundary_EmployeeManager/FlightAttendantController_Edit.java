package boundary_EmployeeManager;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import boundary.TextFormats;
import control.FlightAttendantLogic;
import entity.FlightAttendant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class FlightAttendantController_Edit {
	
	FlightAttendant chosen = (FlightAttendant) PanelEmployeesController.chosenEmployee;
	@FXML private Label employeeID;
    @FXML private TextField fname;
    @FXML private TextField lname;
    @FXML private DatePicker selectJoinDate;
    @FXML private DatePicker selectEndDate;
    
    // invalids:
    static ArrayList<Label> invalidLabels= new ArrayList<>(); // array of invalid
 	@FXML private Label invalidFname;
 	@FXML private Label invalidLname;
 	@FXML private Label invalidJoinDate;
    
    public void initialize() throws ParseException {  	
    	employeeID.setText(chosen.getEmployeeId());
    	fname.setText(chosen.getFirstName());
    	lname.setText(chosen.getLastName());
    	selectJoinDate.setValue(chosen.getJoinDate());
    	selectEndDate.setValue(chosen.getEndDate());
    	
    	fname.setTextFormatter(new TextFormatter<String>(TextFormats.getInstance().notNumbers()));
    	lname.setTextFormatter(new TextFormatter<String>(TextFormats.getInstance().notNumbers()));
	 }
	   
	public void editFlightAttendant (ActionEvent event) {
		
		if(invalidLabels!=null) {
			 for(Label l: invalidLabels) {
		     	   l.setVisible(false);
		        }
			   invalidLabels.clear();
		}
	
		
	   String inputID= employeeID.getText();
	   
	   String inputfname= fname.getText();
	   if(inputfname == null || inputfname.isEmpty()) 
		   invalidLabels.add(invalidFname);
	   
	   String inputlname= lname.getText();
	   if(inputlname == null || inputlname.isEmpty()) 
		   invalidLabels.add(invalidLname);
	   
	   LocalDate inputJoinDate = null;
	   if(selectJoinDate.getValue()!=null) {
		   Date joinDate= Date.valueOf(selectJoinDate.getValue());
		   inputJoinDate = joinDate.toLocalDate();
	   } else
		   invalidLabels.add(invalidJoinDate);
	   
	   LocalDate inputEndDate = null;
	   if(selectEndDate.getValue()!=null) {
		   Date endDate= Date.valueOf(selectEndDate.getValue());
		   inputEndDate = endDate.toLocalDate();
	   } else
		   inputEndDate = null;
		   
	   if(invalidLabels.isEmpty()) {
		   FlightAttendantLogic.getInstance().editFlightAttendant(inputID, inputfname, inputlname, inputJoinDate, inputEndDate);
		   
	   } else {
		   for(Label l: invalidLabels) {
        	   l.setVisible(true);
        	   l.setText("X");
           }
		   
	   }
		   
		  
	}

}
