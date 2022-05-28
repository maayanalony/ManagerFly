package entity;

import java.time.LocalDate;

public class FlightAttendant extends Employee {

	public FlightAttendant(String employeeId, String firstName, String lastName, LocalDate joinDate, LocalDate endDate) {
		super(employeeId, firstName, lastName, joinDate, endDate);
		// TODO Auto-generated constructor stub
	}
	
	public FlightAttendant(String employeeID) {
		super(employeeID);
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
	

}
