package entity;

import java.time.LocalDate;

public class LandAttendant extends Employee {

	public LandAttendant(String employeeId, String firstName, String lastName, LocalDate joinDate, LocalDate endDate) {
		super(employeeId, firstName, lastName, joinDate, endDate);
		// TODO Auto-generated constructor stub
	}
	
	public LandAttendant(String employeeID) {
		super(employeeID);
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
	

}
