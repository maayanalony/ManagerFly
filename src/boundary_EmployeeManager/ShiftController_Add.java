package boundary_EmployeeManager;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import control.ShiftLogic;
import entity.Shift;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import utils.Convert;

public class ShiftController_Add {

	@FXML private DatePicker SelectStartDate;
	 @FXML private Label labelEndDate;
	 @FXML private ComboBox<Integer> startHour;
	 @FXML private ComboBox<Integer> startMin;
	 @FXML private ComboBox<Integer> durationHour;
	 @FXML private ComboBox<Integer> durationMin;
	 
	 private Timestamp startTime=null;
	 LocalDateTime inputStartDateTime=null;
	 private Timestamp endTime= null;
	 LocalDateTime inputEndDateTime=null;
	 
	 private boolean empty=false;
	@FXML private Label invalidEmpty;
	 
	 public void initialize() {
		 ArrayList<Integer> hours =  new ArrayList<>();
			ArrayList<Integer> minets =  new ArrayList<>();
			for(int i=0; i<24;i++) {
				hours.add(i);
			}
			for(int i=0; i<60;i++) {
				minets.add(i);
			}
			startHour.setItems(FXCollections.observableArrayList(hours));
			startMin.setItems(FXCollections.observableArrayList(minets));
			durationMin.setItems(FXCollections.observableArrayList(minets));
			hours.clear();
			for(int i=0; i<10;i++) {
				hours.add(i);
			}
			durationHour.setItems(FXCollections.observableArrayList(hours));
			
			startHour.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Integer> ov, Integer old_val, Integer new_val) -> {
	    		setEndTime();
	    	    });
			startMin.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Integer> ov, Integer old_val, Integer new_val) -> {
	    		setEndTime();
	    	    });
			durationHour.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Integer> ov, Integer old_val, Integer new_val) -> {
	    		setEndTime();
	    	    });
			durationMin.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Integer> ov, Integer old_val, Integer new_val) -> {
	    		setEndTime();
	    	    });
	 }
	 
	 @SuppressWarnings("deprecation")
	private void setEndTime() {
		 if(SelectStartDate.getValue()!=null && startHour.getValue()!=null && startMin.getValue()!=null) {
			   Date startD= Date.valueOf(SelectStartDate.getValue());
			   @SuppressWarnings("unused")
			Date endD;
			   startTime = new Timestamp(startD.getTime());
			   startTime.setHours(startHour.getValue());
			   startTime.setMinutes(startMin.getValue());	
			   inputStartDateTime = Convert.getInstance().convertToLocalDateTime(startTime);
			   if(durationHour.getValue()!=null && durationMin.getValue()!=null) {
					   endD= Date.valueOf(SelectStartDate.getValue());
					   endTime = new Timestamp(startD.getTime());
					   endTime.setHours(startHour.getValue()+durationHour.getValue());
					   endTime.setMinutes(startMin.getValue()+durationMin.getValue());	
					   inputEndDateTime = Convert.getInstance().convertToLocalDateTime(endTime);
					   labelEndDate.setText(Convert.getInstance().LocalDateTimeToString(inputEndDateTime));
				}
		 }
	 }
	 
	 @SuppressWarnings("deprecation")
	@FXML
	 public void addShift() {
		 if(SelectStartDate.getValue()!=null && startHour.getValue()!=null && startMin.getValue()!=null) {
			   Date startD= Date.valueOf(SelectStartDate.getValue());
			   @SuppressWarnings("unused")
			Date endD;
			   startTime = new Timestamp(startD.getTime());
			   startTime.setHours(startHour.getValue());
			   startTime.setMinutes(startMin.getValue());	
			   inputStartDateTime = Convert.getInstance().convertToLocalDateTime(startTime);
			   if(durationHour.getValue()!=null && durationMin.getValue()!=null) {
					   endD= Date.valueOf(SelectStartDate.getValue());
					   endTime = new Timestamp(startD.getTime());
					   endTime.setHours(startHour.getValue()+durationHour.getValue());
					   endTime.setMinutes(startMin.getValue()+durationMin.getValue());	
					   inputEndDateTime = Convert.getInstance().convertToLocalDateTime(endTime);
				}
			   else
				   empty= true;
		   } else
			   empty=true;
		  if(empty) 
			  invalidEmpty.setText("There are Empty Fields");
		  else {
			  Shift newShift= new Shift(inputStartDateTime, inputEndDateTime);
			  System.out.println();
			  if(ShiftLogic.getInstance().addShift(newShift)) {
				  Alert a = new Alert(AlertType.CONFIRMATION);
	    		   a.setContentText("The Shift was successfully added");
	    		   a.show();
			  }
		  }
		  
	 }
}
