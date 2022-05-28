package control;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;


import entity.Airport;
import entity.Flight;
import enumeration.AirportStatus;
import enumeration.FlightSaleStatus;
import enumeration.FlightStatus;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import utils.Consts;

public class AirportLogic {
	
	private static AirportLogic _instance;

	private AirportLogic() {
	}

	public static AirportLogic getInstance() {
		if (_instance == null)
			_instance = new AirportLogic();
		return _instance;
	}
	
	/**
	 * fetches all airports from DB file.
	 * @return ArrayList of planes.
	 */
	public ArrayList<Airport> getAirport() {
		ArrayList<Airport> results = new ArrayList<Airport>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_AIRPORTS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new Airport(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getInt(i++), rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	

	/**
	 * Adding a new plane with the parameters received from the form.
	 * return true if the insertion was successful, else - return false
     * @return 
	 */
	public boolean addAirport(Airport airport) {
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_AIRPORT)) {
				
				int i = 1;
				
				stmt.setString(i++, airport.getAirportCode()); // can't be null
				stmt.setString(i++, airport.getCountry()); // can't be null
				stmt.setString(i++, airport.getCity()); // can't be null
				stmt.setInt(i++, airport.getGMTcode()); // can't be null
				
				/* Executes the query */
				stmt.executeUpdate();
				Alert a = new Alert(AlertType.CONFIRMATION);
		    	 a.setContentText("The airport was successfully added");
		    	 a.show();
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean editAirport(Airport airport) {
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_AIRPORT)) {
				
				int i = 1;
				stmt.setString(i++, airport.getCountry()); 
				stmt.setString(i++, airport.getCity()); 
				stmt.setInt(i++, airport.getGMTcode()); 
				stmt.setString(i++, airport.getStatus().toString());
				stmt.setString(i++, airport.getAirportCode());
				if(airport.getStatus().equals(AirportStatus.CLOSE)){
					for(Flight f: FlightLogic.getInstance().getFlights()) {
						if(f.getDepTime().isAfter(LocalDateTime.now()) && !f.getStatus().equals(FlightStatus.CANCELLED)){
							FlightLogic.getInstance().editFlightStatus(f.getFlightId(), FlightStatus.FROZEN);
							FlightLogic.getInstance().editFlightSaleStatus(f.getFlightId(), FlightSaleStatus.FROZEN);
							
						}
								
					}
				}else {
					for(Flight f: FlightLogic.getInstance().getFlights()) {
						if(f.getDepTime().isAfter( LocalDateTime.now()) && !f.getStatus().equals(FlightStatus.CANCELLED)){
							if(ChronoUnit.DAYS.between(LocalDateTime.now(), f.getDepTime())>=14) { // alert for finish scheduling in time
					    		if(f.getChiefPilot()!=null && f.getSecPilot()!=null && f.getPlane().getNumOfFlightAttendants()==f.getFlightAttendentsInFlight().size()) {
					    			FlightLogic.getInstance().editFlightSaleStatus(f.getFlightId(), FlightSaleStatus.REGULAR_SALE);
					    		}else  //  for uncompleted scheduling
					    			FlightLogic.getInstance().editFlightSaleStatus(f.getFlightId(), FlightSaleStatus.PRE_SALE);							    	
					    		
					    	} 
						}
					} //for
				}
				
				/* Executes the query */
				stmt.executeUpdate();
				Alert a = new Alert(AlertType.CONFIRMATION);
		    	 a.setContentText("The airport was successfully updated");
		    	 a.show();
				return true;
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Airport findAirport(String code) {
		Airport toFind = new Airport(code);
		for(Airport a : getAirport()) {
			if(a.equals(toFind))
				return a;
		}
		return null;
	}
}
