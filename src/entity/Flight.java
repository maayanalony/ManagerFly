package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import boundary.EnterController;
import control.AirportLogic;
import control.PilotLogic;
import control.PlaneLogic;
import enumeration.FlightSaleStatus;
import enumeration.FlightStatus;
import enumeration.User;
import utils.Convert;

public class Flight {
	
	private String flightId;
	private LocalDateTime depTime;
	private LocalDateTime arvTime;
	private Airport depAirport;
	private Airport arvAirport;
	private Plane plane;
	private Pilot chiefPilot;
	private Pilot secPilot;
	private ArrayList <FlightAttendant> flightAttendentsInFlight;
	private FlightStatus status;
	private FlightSaleStatus saleStatus;
	private LocalDate updateDate;
	
	/* constructor for Add*/
	public Flight(String flightId, LocalDateTime depTime, LocalDateTime arvTime, Airport depAirport, Airport arvAirport, Plane plane) {
		super();
		this.flightId = flightId;
		this.depTime = depTime;
		this.arvTime = arvTime;
		this.depAirport = depAirport;
		this.arvAirport = arvAirport;
		this.plane = plane;
		flightAttendentsInFlight= new ArrayList<>();
	}
	 /*constructor for getFlights*/
	public Flight(String flightId, LocalDateTime depTime, LocalDateTime arvTime, Airport depAirport, Airport arvAirport,
			Plane plane, Pilot chiefPilot, Pilot secPilot, FlightStatus status, FlightSaleStatus saleStatus, LocalDate updateDate) {
		super();
		this.flightId = flightId;
		this.depTime = depTime;
		this.arvTime = arvTime;
		this.depAirport = AirportLogic.getInstance().findAirport(depAirport.getAirportCode());
		this.arvAirport = AirportLogic.getInstance().findAirport(arvAirport.getAirportCode());
		this.plane = PlaneLogic.getInstance().findPlane(plane.getTailNumber());
		this.chiefPilot = PilotLogic.getInstance().findPilot(chiefPilot.getEmployeeId());
		this.secPilot = PilotLogic.getInstance().findPilot(secPilot.getEmployeeId());
		this.status = status;
		this.saleStatus = saleStatus;
		this.updateDate=updateDate;
		flightAttendentsInFlight= new ArrayList<>();
	}

	/* Partial constructor */
	public Flight(String flightId) {
		super();
		this.flightId = flightId;
	}
	
	

	public ArrayList<FlightAttendant> getFlightAttendentsInFlight() {
		return flightAttendentsInFlight;
	}

	public void addFlightAttendentsInFlight(FlightAttendant flightAttendent) {
		if(flightAttendent!=null)
			flightAttendentsInFlight.add(flightAttendent);
	}
	public void removeFlightAttendentsInFlight(FlightAttendant flightAttendent) {
		flightAttendentsInFlight.remove(flightAttendent);
	}


	public String getFlightId() {
		return flightId;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}

	public LocalDateTime getDepTime() {
		return depTime;
	}

	public void setDepTime(LocalDateTime depTime) {
		this.depTime = depTime;
	}

	public LocalDateTime getArvTime() {
		return arvTime;
	}

	public void setArvTime(LocalDateTime arvTime) {
		this.arvTime = arvTime;
	}

	public Airport getDepAirport() {
		return depAirport;
	}

	public void setDepAirport(Airport depAirport) {
		this.depAirport = depAirport;
	}

	public Airport getArvAirport() {
		return arvAirport;
	}

	public void setArvAirport(Airport arvAirport) {
		this.arvAirport = arvAirport;
	}

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	public Pilot getChiefPilot() {
		return chiefPilot;
	}

	public void setChiefPilot(Pilot chiefPilot) {
		this.chiefPilot = chiefPilot;
	}

	public Pilot getSecPilot() {
		return secPilot;
	}

	public void setSecPilot(Pilot secPilot) {
		this.secPilot = secPilot;
	}

	public FlightStatus getStatus() {
		return status;
	}

	public void setStatus(FlightStatus status) {
		this.status = status;
	}

	public FlightSaleStatus getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(FlightSaleStatus saleStatus) {
		this.saleStatus = saleStatus;
	}

	public LocalDate getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(flightId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		return Objects.equals(flightId, other.flightId);
	}

	@Override
	public String toString() {
		if(EnterController.user.equals(User.EMPLOYEE_MANAGER)) {
			return "Flight numbr: " + flightId + "	Plane number " + plane.getTailNumber() + 
			"	Status: " + status.toString() + "\nDeparture airport: "+ depAirport.getAirportCode() + "	at: " + Convert.getInstance().LocalDateTimeToString(depTime) +
			"\nDestination airport: " + arvAirport.getAirportCode() + "	at " + Convert.getInstance().LocalDateTimeToString(arvTime) + "\nupdateDate: "+ updateDate;
		}
		return "Flight numbr: " + flightId + "	Plane number " + plane.getTailNumber() + 
				"	Status: " + status.toString() + "\nDeparture airport: "+ depAirport.getAirportCode() + "	at: " + Convert.getInstance().LocalDateTimeToString(depTime) +
				"\nDestination airport: " + arvAirport.getAirportCode() + "	at " + Convert.getInstance().LocalDateTimeToString(arvTime) + "\nsaleStatus: " 
				+ saleStatus + " Scheduling: "+schedulingStatus()+"\nupdateDate: "+ updateDate;

	
	}
	
	private String schedulingStatus() {
		if(flightAttendentsInFlight==null)
			return "Not completed";
		if(flightAttendentsInFlight.size()==plane.getNumOfFlightAttendants() && chiefPilot!=null && secPilot!=null) 
    		return "Complete";
    	else 
    		return "Not completed";
    	
	}
	
	
}
