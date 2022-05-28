package boundary_EmployeeManager;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import boundary.TextFormats;
import control.FlightAttendantLogic;
import control.LandAttendantLogic;
import control.PilotLogic;
import entity.Employee;
import entity.LandAttendant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class LandAttendantController_Add {
	
	
	@FXML private TextField employeeID;
    @FXML private TextField fname;
    @FXML private TextField lname;
    @FXML private DatePicker selectJoinDate;
    @FXML private DatePicker selectEndDate;
    
    // invalids:
    static ArrayList<Label> invalidLabels= new ArrayList<>(); // array of invalid
 	@FXML private Label invalidID;
 	@FXML private Label invalidFname;
 	@FXML private Label invalidLname;
 	@FXML private Label invalidJoinDate;
 	@FXML private Label invalidExist;
    
    public void initialize() throws ParseException {  	
    	employeeID.setTextFormatter(new TextFormatter<String>(TextFormats.getInstance().onlyNumbers()));
    	fname.setTextFormatter(new TextFormatter<String>(TextFormats.getInstance().notNumbers()));
    	lname.setTextFormatter(new TextFormatter<String>(TextFormats.getInstance().notNumbers()));
	 }
	   
	public void addNewLandAttendant (ActionEvent event) {
		
		if(invalidLabels!=null) {
			 for(Label l: invalidLabels) {
		     	   l.setVisible(false);
		        }
			   invalidLabels.clear();
		}
	
		
	   String inputID= employeeID.getText();
	   if(inputID == null || inputID.isEmpty() ) 
		   invalidLabels.add(invalidID);
	   else {
		   Employee newEmp= new Employee(inputID);
		   if(FlightAttendantLogic.getInstance().getFlightAttendants().contains(newEmp)) {
			   invalidLabels.add(invalidExist);
			   invalidExist.setText("This flight attendant is already exist");
		   } else if(LandAttendantLogic.getInstance().getLandAttendants().contains(newEmp)) {
			   invalidLabels.add(invalidExist);
			   invalidExist.setText("This employee is already exist as a land attendant.\nAn employee can be hierd in only one role");
		   }  else if(PilotLogic.getInstance().getPilots().contains(newEmp)) {
			   invalidLabels.add(invalidExist);
			   invalidExist.setText("This employee is already exist as a pilot.\nAn employee can be hierd in only one role");
		   }
	   }
	   
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
		   LandAttendant newLandAttendant= new LandAttendant(inputID, inputfname, inputlname, inputJoinDate, inputEndDate);
		   LandAttendantLogic.getInstance().addLandAttendant(newLandAttendant);
		   
	   } else {
		   for(Label l: invalidLabels) {
        	   l.setVisible(true);
        	   if(!l.equals(invalidExist))
        		   l.setText("X");
           }
		   
	   }
		   
		  
	}

}
