package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import enumeration.LandAttendantRole;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import utils.Convert;

public class Shift {
	
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private ArrayList<LandAttendant> checkIn;
	private ArrayList<LandAttendant> luggage;
	private ArrayList<LandAttendant> allocation;
	
	public Shift(LocalDateTime startTime, LocalDateTime endTime) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.checkIn= new ArrayList<>();
		this.luggage= new ArrayList<>();
		this.allocation= new ArrayList<>();
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public ArrayList<LandAttendant> getCheckIn() {
		return checkIn;
	}

	public ArrayList<LandAttendant> getLuggage() {
		return luggage;
	}

	public ArrayList<LandAttendant> getAllocation() {
		return allocation;
	}
	
	public boolean addAttendantToShift(LandAttendantRole role, LandAttendant la) {
		if(role.equals(LandAttendantRole.CHECKING_TICKETS))
			return addToCheckIn(la);
		if(role.equals(LandAttendantRole.LABALING_AND_SENDING_LUGGAGE))
			return addToLuggage(la);
		return addToAllocation(la);
	}
	
	public boolean addToCheckIn(LandAttendant la) {
		if(!luggage.contains(la)) {
			if(!allocation.contains(la)) {
				checkIn.add(la);
				return true;
			} else {
				Alert a = new Alert(AlertType.ERROR);
		    	a.setContentText("This land attendant is scheduled to allocatin seats");
		    	a.show();
			}	
		} else {
			Alert a = new Alert(AlertType.ERROR);
	    	a.setContentText("This land attendant is scheduled to labeling luggage");
	    	a.show();
		}
		return false;
	}

	public boolean addToLuggage(LandAttendant la) {
		if(!checkIn.contains(la)) {
			if(!allocation.contains(la)) {
				luggage.add(la);
				return true;
			} else {
				Alert a = new Alert(AlertType.ERROR);
		    	a.setContentText("This land attendant is scheduled to allocatin seats");
		    	a.show();
			}	
		} else {
			Alert a = new Alert(AlertType.ERROR);
	    	a.setContentText("This land attendant is scheduled to check in");
	    	a.show();
		}
		return false;
	}

	public boolean addToAllocation(LandAttendant la) {
		if(!checkIn.contains(la)) {
			if(!luggage.contains(la)) {
				allocation.add(la);
				return true;
			} else {
				Alert a = new Alert(AlertType.ERROR);
		    	a.setContentText("This land attendant is scheduled to labeling luggage");
		    	a.show();
			}	
		} else {
			Alert a = new Alert(AlertType.ERROR);
	    	a.setContentText("This land attendant is scheduled to check in");
	    	a.show();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(endTime, startTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shift other = (Shift) obj;
		return Objects.equals(endTime, other.endTime) && Objects.equals(startTime, other.startTime);
	}

	@Override
	public String toString() { 
		return "From: " + Convert.getInstance().LocalDateTimeToString(startTime) + "\nTo: " + Convert.getInstance().LocalDateTimeToString(endTime);
	}
	
	
	
	
}
