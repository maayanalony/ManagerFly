package entity;

import java.time.LocalDate;
import java.util.Objects;

public class Employee {

	private String employeeId;
	private String firstName;
	private String lastName;
	private LocalDate joinDate;
	private LocalDate endDate;
	
	public Employee(String employeeId, String firstName, String lastName, LocalDate joinDate, LocalDate endDate) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.joinDate = joinDate;
		this.endDate = endDate;
	}
	
	public Employee(String employeeId) {
		super();
		this.employeeId = employeeId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(employeeId, other.employeeId);
	}

	@Override
	public String toString() {
		return employeeId + " " + firstName + " " + lastName
				+ "\njoin Date: " + joinDate + ", end Date: " + endDate;
	}
	
	
	
}
