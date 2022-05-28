package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import utils.Consts;
import utils.Convert;
import entity.Airport;
import entity.Flight;
import entity.FlightAttendant;
import entity.Pilot;
import entity.Plane;
import enumeration.FlightSaleStatus;
import enumeration.FlightStatus;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

public class FlightLogic {
	
	private static FlightLogic _instance;

	private FlightLogic() {
	}

	public static FlightLogic getInstance() {
		if (_instance == null)
			_instance = new FlightLogic();
		return _instance;
	}
	
	
	/**
	 * fetches all flights from DB file.
	 * @return ArrayList of flights.
	 */
	public ArrayList<Flight> getFlights() {
		ArrayList<Flight> results = new ArrayList<Flight>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_FLIGHTS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					String flightID = rs.getString(i++); 
					LocalDateTime depTime = Convert.getInstance().convertToLocalDateTime(rs.getTimestamp(i)); i++;
					LocalDateTime arvTime = Convert.getInstance().convertToLocalDateTime(rs.getTimestamp(i)); i++;
					Airport depAirport = new Airport(rs.getString(i++));
					Airport arvAirport = new Airport(rs.getString(i++));
					Plane plane = new Plane(rs.getString(i++));
					Pilot chiefPilot = new Pilot(rs.getString(i++));
					Pilot secondaryPilot = new Pilot(rs.getString(i++));
					FlightStatus flightStatus = Convert.getInstance().convertFlightStatus(rs.getString(i)); i++; 
					FlightSaleStatus saleStatus= Convert.getInstance().convertSaleStatus(rs.getString(i++)); 
					LocalDate updateDate = Convert.getInstance().convertToLocalDate(rs.getTimestamp(i));
					
					
					Flight newFlight= new Flight(flightID, depTime, arvTime, depAirport, arvAirport,
							plane, chiefPilot, secondaryPilot, flightStatus, saleStatus,updateDate);
					getFlightAttendantsInFlight(newFlight);
	
					results.add(newFlight);
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<Flight> getUpdatedTodayFlights() {
		ArrayList<Flight> results = new ArrayList<Flight>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_UPDATED_TODAY);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					String flightID = rs.getString(i++); 
					LocalDateTime depTime = Convert.getInstance().convertToLocalDateTime(rs.getTimestamp(i)); i++;
					LocalDateTime arvTime = Convert.getInstance().convertToLocalDateTime(rs.getTimestamp(i)); i++;
					Airport depAirport = new Airport(rs.getString(i++));
					Airport arvAirport = new Airport(rs.getString(i++));
					Plane plane = new Plane(rs.getString(i++));
					Pilot chiefPilot = new Pilot(rs.getString(i++));
					Pilot secondaryPilot = new Pilot(rs.getString(i++));
					FlightStatus flightStatus = Convert.getInstance().convertFlightStatus(rs.getString(i)); i++; 
					FlightSaleStatus saleStatus= Convert.getInstance().convertSaleStatus(rs.getString(i++)); 
					LocalDate updateDate = Convert.getInstance().convertToLocalDate(rs.getTimestamp(i));
					Flight newFlight= new Flight(flightID, depTime, arvTime, depAirport, arvAirport,
							plane, chiefPilot, secondaryPilot, flightStatus, saleStatus,updateDate);
					getFlightAttendantsInFlight(newFlight);
	
					results.add(newFlight);
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public void getFlightAttendantsInFlight(Flight flight) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_ATTENDANT_IN_FLIGHT);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					String flightId = rs.getString(i++); 
					String flightAttendant = rs.getString(i++); 
					
					if(flightId.equals(flight.getFlightId())) {
						FlightAttendant a= FlightAttendantLogic.getInstance().findFlightAttendant(flightAttendant);
						flight.addFlightAttendentsInFlight(a);
					}	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/*----------------------------------------- ADD / REMOVE / UPDATE FLIGHT METHODS --------------------------------------------*/
	
	/**
	 * Delete the selected flight in form.
	 * return true if the deletion was successful, else - return false
	 * @param flightID - the flight to delete from DB
     * @return 
	 */
	public boolean removeFlight(String flightID) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_DEL_FLIGHT)) {
				
				stmt.setString(1, flightID);
				stmt.executeUpdate();
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * Adding a new flight with the parameters received from the form.
	 * return true if the insertion was successful, else - return false
     * @return 
	 */
	public String addFlight(Flight flight) {
		
		String answer = this.noProblem(flight);
		
		if(answer.equals("flightOK")) {
			
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_FLIGHT)) {
					
					int i = 1;
					
					stmt.setString(i++, flight.getFlightId()); 
					Timestamp depTimeStamp = Timestamp.valueOf(flight.getDepTime());
					Timestamp arvTimeStamp = Timestamp.valueOf(flight.getArvTime());
					stmt.setTimestamp(i++, depTimeStamp); 
					stmt.setTimestamp(i++, arvTimeStamp); 
					stmt.setString(i++, flight.getDepAirport().getAirportCode()); 
					stmt.setString(i++, flight.getArvAirport().getAirportCode()); 
					stmt.setString(i++, flight.getPlane().getTailNumber()); 
					
					/* Executes the query */
					stmt.executeUpdate();
					return "flightOK";
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} 
		
		/* In case that the adding was fail */
		return answer; 
	
	}
	
	
	/**
	 * Editing a exist flight with the parameters received from the form.
	 * return true if the update was successful, else - return false
     * @return 
	 */
	public String editFlight (String flightId, LocalDateTime depTime, LocalDateTime arvTime, Airport depAirport, Airport arvAirport,Plane plane,
			FlightStatus status) {
		Flight checkFlight= new Flight(flightId, depTime, arvTime, depAirport, arvAirport,plane);
		String answer = this.noProblemEdit(checkFlight);
		System.out.println(answer);
		
		System.out.println(checkFlight);
		if(answer.equals("flightOK")) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_FLIGHT)) {
					
					int i = 1;
					Timestamp depTimeStamp = Timestamp.valueOf(depTime);
					Timestamp arvTimeStamp = Timestamp.valueOf(depTime);
					stmt.setTimestamp(i++, depTimeStamp); 
					stmt.setTimestamp(i++, arvTimeStamp);
					stmt.setString(i++, depAirport.getAirportCode()); 
					stmt.setString(i++, arvAirport.getAirportCode()); 
					stmt.setString(i++, plane.getTailNumber());
					stmt.setString(i++, status.toString()); 
					stmt.setString(i++, flightId); 
					
					
					stmt.executeUpdate();
					return "flightOK";
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} // if flight ok
		/* In case that the adding was fail */
		return answer; 
	}
		
	
	/*----------------------------------------- OTHER METHODS --------------------------------------------*/

	public String noProblem(Flight flight){
		
		ArrayList<Flight> flightList = this.getFlights();	
		 
		 if(flightList.contains(flight)) {
			 return "Flight is already exsist";
		 }
		 
		 for(Flight f: flightList) {
			 if(sameDay(f.getArvTime(),flight.getArvTime()) || sameDay(f.getDepTime(),flight.getDepTime())) {
				 if( f.getPlane().equals(flight.getPlane())) 
					 return ("The selected plane has a flight on the requested day");
			 }
			
			 if(f.getDepAirport().equals(flight.getDepAirport())) {  
				 if(!timeDiffInAirport(f.getDepTime(),flight.getDepTime())) 
					 return ("There is not enough time between the flights in the selected departure airport");

			} else if(f.getArvAirport().equals(flight.getArvAirport())) {
				if(!timeDiffInAirport(f.getArvTime(),flight.getArvTime())) 
					 return ("There is not enough time between the flights in the selected arrival airport");
			}
		 }
		 
		 return "flightOK";

	 }
	
	public String noProblemEdit(Flight flight){
		
		ArrayList<Flight> flightList = this.getFlights();	
		 
		 for(Flight f: flightList) {
			 if(!f.equals(flight)) {
				 if(sameDay(f.getArvTime(),flight.getArvTime()) || sameDay(f.getDepTime(),flight.getDepTime())) {
					 if( f.getPlane().equals(flight.getPlane())) 
						 return ("The selected plane has a flight on the requested day");
				 }
				
				 if(f.getDepAirport().equals(flight.getDepAirport())) {  
					 if(!timeDiffInAirport(f.getDepTime(),flight.getDepTime())) 
						 return ("There is not enough time between the flights in the selected departure airport");

				} else if(f.getArvAirport().equals(flight.getArvAirport())) {
					if(!timeDiffInAirport(f.getArvTime(),flight.getArvTime())) 
						 return ("There is not enough time between the flights in the selected arrival airport");
				}
			 } // if not same flight
			 
		 } // for
		 
		 return "flightOK";

	 }
	 
	public boolean sameDay(LocalDateTime d1, LocalDateTime d2) {
		 if(d1.toLocalDate().equals(d2.toLocalDate())) {
			 System.out.println("same day");
	    		return true;
		 }
		 return false;
	 }
   
   public boolean timeDiffInAirport(LocalDateTime d1, LocalDateTime d2) {
	   
	   long minutes = ChronoUnit.MINUTES.between(d1, d2);
	   System.out.println(minutes);	   
	   if(minutes>30 || minutes<-30)
		   return true;
	   else
		   return false;
   }
   
   /* Check Id */
   public boolean checkID(Flight flight) {
	   
	   if(this.getFlights().contains(flight))
		   return true;
	   else return false;
				   
	   
   }
	
		
	
	/*------------------------------Generate Report of major Flights-----------------------------*/
	public  JFrame exportMajorFlightsReport(int numOfTouristSeats, Date from, Date until) 
	{
		
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR))
			{
				HashMap<String, Object> params = new HashMap<>();
				
				params.put("1", numOfTouristSeats);
				params.put("2", from);
				params.put("3", until);
				
				System.out.println(params);
				
				JasperPrint print = JasperFillManager.fillReport(
						getClass().getResourceAsStream("/boundary_FlightManager/FlightsReport.jasper"),
						params, conn);
				JFrame frame = new JFrame("Report" + numOfTouristSeats + " tourist class seat");				
				frame.getContentPane().add(new JRViewer(print));
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.pack();
				return frame;
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*------------------------------Generate Report of major Flights-----------------------------*/
	public  JFrame exportPercentReport(String country) 
	{	
		try {
            Class.forName(Consts.JDBC_STR);
            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR)) {
            	HashMap<String, Object> params = new HashMap<>();
            	params.put("1", country);
            	JasperPrint print = JasperFillManager.fillReport(
            			getClass().getResourceAsStream("/boundary_FlightManager/PercentReport.jasper"),
                        params, conn);
                JFrame frame = new JFrame("Percent Report for " + country );
                frame.getContentPane().add(new JRViewer(print));
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.pack();
                return frame;
            } catch (SQLException | JRException | NullPointerException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return null;
	}
	
	public Flight findFlight(String flightId) {
		   Flight flightToFind = new Flight(flightId);
			for(Flight f : getFlights()) {
				if(f.equals(flightToFind))
					return f;
			}
			return null;
	}
	
	//////////////////////////////////
	
	public boolean editPilotsInFlight(Flight f) {
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_PILOTS_IN_FLIGHT)) {
				
				int i = 1;
				if(f.getChiefPilot()!=null)
					stmt.setString(i++, f.getChiefPilot().getEmployeeId()); 
				else 
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				
				if(f.getSecPilot()!=null)
					stmt.setString(i++, f.getSecPilot().getEmployeeId());
				else 
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				
				stmt.setString(i++, f.getFlightId()); 
				
				/* Executes the query */
				stmt.executeUpdate();
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addFlightAttendantsToFlight(Flight f, FlightAttendant fa) {
		
		try {
			
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_ATTENDANT_IN_FLIGHT)) {
				
				int i = 1;
				
				stmt.setString(i++, f.getFlightId()); 
				stmt.setString(i++, fa.getEmployeeId());
				
				/* Executes the query */
				stmt.executeUpdate();
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean removeFlightAttendantsToFlight(Flight f, FlightAttendant fa) {
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_DEL_ATTENDANT_IN_FLIGHT)) {
				
				int i = 1;
				stmt.setString(i++, f.getFlightId()); 
				stmt.setString(i++, fa.getEmployeeId());
				
				/* Executes the query */
				stmt.executeUpdate();
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean editFlightStatus (String flightId, FlightStatus status) {
		try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_FLIGHT_STATUS)) {
					
					int i = 1;
					stmt.setString(i++, status.toString());
					stmt.setString(i++, flightId); 
					
					stmt.executeUpdate();
					return true;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		/* In case that the update failed */
		return false; 
	}
	
	public boolean editFlightSaleStatus (String flightId, FlightSaleStatus saleSt) {
		try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_FLIGHT_STATUS)) {
					
					int i = 1; 
					stmt.setString(i++, saleSt.toString());
					stmt.setString(i++, flightId); 
					
					stmt.executeUpdate();
					return true;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		/* In case that the update failed */
		return false; 
	}
	
}
	

	
	

