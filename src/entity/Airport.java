package entity;

import java.util.Objects;

import enumeration.AirportStatus;
import utils.Convert;

public class Airport {

	private String airportCode;
	private String country;
	private String city;
	private int GMTcode;
	private AirportStatus status;
	
	public Airport(String airportCode, String country, String city, int gMTcode, String status) {
		super();
		this.airportCode = airportCode;
		this.country = country;
		this.city = city;
		GMTcode = gMTcode;
		this.setStatus(Convert.getInstance().convertAirportStatus(status));
	}
	
	

	public Airport(String airportCode) {
		super();
		this.airportCode = airportCode;
	}



	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getGMTcode() {
		return GMTcode;
	}

	public void setGMTcode(int gMTcode) {
		GMTcode = gMTcode;
	}

	@Override
	public int hashCode() {
		return Objects.hash(airportCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Airport other = (Airport) obj;
		return Objects.equals(airportCode, other.airportCode);
	}

	@Override
	public String toString() {
		return "airportCode=" + airportCode + "    status: "+ status + "\ncountry=" + country + ", city=" + city + "\nGMTcode="
				+ GMTcode ;
	}



	public AirportStatus getStatus() {
		return status;
	}



	public void setStatus(AirportStatus status) {
		this.status = status;
	}
	
	
	
}
