package entity;

import java.time.LocalDate;

public class Pilot extends Employee {
	
	private String licenseNumber;
	private LocalDate dateIssueLicense;
	
	public Pilot(String employeeId, String firstName, String lastName, LocalDate joinDate, LocalDate endDate,
			String licenseNumber, LocalDate dateIssueLicense) {
		super(employeeId, firstName, lastName, joinDate, endDate);
		this.licenseNumber = licenseNumber;
		this.dateIssueLicense = dateIssueLicense;
	}
	

	public Pilot(String empolyeeId) {
		super( empolyeeId);
	}
	
	public String getLicenseNumber() {
		return licenseNumber;
	}
	
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	
	public LocalDate getDateIssueLicense() {
		return dateIssueLicense;
	}
	
	public void setDateIssueLicense(LocalDate dateIssueLicense) {
		this.dateIssueLicense = dateIssueLicense;
	}
	
	@Override
	public String toString() {
		return super.toString()+"\nlicense Number: " + licenseNumber + ", date Issue License: " + dateIssueLicense;
	}
	
	
	
	
}