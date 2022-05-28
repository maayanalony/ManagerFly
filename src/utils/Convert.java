package utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import enumeration.AirportStatus;
import enumeration.FlightSaleStatus;
import enumeration.FlightStatus;
import enumeration.LandAttendantRole;
import enumeration.SeatClass;
import enumeration.User;

public class Convert {
	
	private static Convert _instance;

	private Convert() {
	}

	public static Convert getInstance() {
		if (_instance == null)
			_instance = new Convert();
		return _instance;
	}
	
	public FlightStatus convertFlightStatus (String s) {
		if(s.equals("ON_TIME"))
			return FlightStatus.ON_TIME; 
		if(s.equals("DELAYED"))
			return FlightStatus.DELAYED;
		if(s.equals("FROZEN"))
			return FlightStatus.FROZEN;
		return FlightStatus.CANCELLED;
	}
	
	public SeatClass convertSeatClass (String s) {
		if(s.equals("FIRST_CLASS"))
			return SeatClass.FIRST_CLASS; 
		if(s.equals("BUISNESS_CLASS"))
			return SeatClass.BUISNESS_CLASS;
		return SeatClass.TOURIST_CLASS;
	}
	
	public FlightSaleStatus convertSaleStatus (String s) {
		if(s.equals("INITIALZE"))
			return FlightSaleStatus.INITIALZE; 
		if(s.equals("PRE_SALE"))
			return FlightSaleStatus.PRE_SALE;
		return FlightSaleStatus.REGULAR_SALE;
	}
	
	public LandAttendantRole convertRole (String s) {
		if(s.equals("Checking tickets"))
			return LandAttendantRole.CHECKING_TICKETS; 
		if(s.equals("Labeling and sending luggage"))
			return LandAttendantRole.LABALING_AND_SENDING_LUGGAGE;
		return LandAttendantRole.ALLOCATION_PLACES;
	}
	
	public String convertRole (LandAttendantRole s) {
		if(s.equals(LandAttendantRole.CHECKING_TICKETS))
			return "Checking tickets"; 
		if(s.equals(LandAttendantRole.LABALING_AND_SENDING_LUGGAGE))
			return "Labeling and sending luggage";
		return "Allocation places";
	}
	
	
	public LocalDateTime convertDate (String date) {
		String year="";
		String month="";
		String day="";
		String hour="";
		String minute="";
		
		char[] myStr = date.toCharArray();
		
		for(int i=0; i<4; i++)
			year += myStr[i];
		
		for(int i=5; i<7; i++)
			month += myStr[i];
		
		for(int i=8; i<10; i++)
			day += myStr[i];
		
		for(int i=11; i<13; i++)
			hour += myStr[i];
		
		for(int i=14; i<16; i++)
			minute += myStr[i];
		
		
		int hourTime = Integer.parseInt(hour);
		int minuteTime = Integer.parseInt(minute);		
		int yearDate = Integer.parseInt(year);
		int monthDate = Integer.parseInt(month);
		int dayDate = Integer.parseInt(day);
		return LocalDateTime.of(yearDate, monthDate, dayDate, hourTime, minuteTime, 0);
	}
	

	public LocalDateTime convertToLocalDateTime (Timestamp t) {
		LocalDateTime time = null;
		if(t!=null) {
			time = t.toInstant()
					.atZone(ZoneId.systemDefault())
					.toLocalDateTime();
		}
		return time;
	}
	
	public LocalDate convertToLocalDate (Timestamp t) {
		LocalDate time = null;
		if(t!=null) {
			time = t.toInstant()
					.atZone(ZoneId.systemDefault())
					.toLocalDate();
		}
		return time;
	}
	
	public User convertUser (String s) {
		if(s.equals("flight manager"))
			return User.FLIGHT_MANAGER; 
		if(s.equals("chief flight manager"))
			return User.CHIEF_FLIGHT_MANAGER;
		return User.EMPLOYEE_MANAGER;
	}
	
	public String convertUserToString (User s) {
		if(s.equals(User.FLIGHT_MANAGER))
			return "flight manager"; 
		if(s.equals(User.CHIEF_FLIGHT_MANAGER))
			return "chief flight manager";
		return "employee manager";
	}
	
	public String LocalDateTimeToString(LocalDateTime t) {
		return t.toLocalDate()+" "+t.toLocalTime();
	}
	
	public AirportStatus convertAirportStatus (String s) {
		if(s.equals("OPEN"))
			return AirportStatus.OPEN; 
		return AirportStatus.CLOSE;
	}

}
