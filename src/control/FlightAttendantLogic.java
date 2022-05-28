package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.FlightAttendant;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import utils.Consts;
import utils.Convert;

public class FlightAttendantLogic {
	
	private static FlightAttendantLogic _instance;

	private FlightAttendantLogic() {
	}

	public static FlightAttendantLogic getInstance() {
		if (_instance == null)
			_instance = new FlightAttendantLogic();
		return _instance;
	}
	
	/**
	 * fetches all airports from DB file.
	 * @return ArrayList of planes.
	 */
	public ArrayList<FlightAttendant> getFlightAttendants() {
		ArrayList<FlightAttendant> results = new ArrayList<FlightAttendant>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_FLIGHT_AT);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					String id = rs.getString(i++); 
					String fname = rs.getString(i++); 
					String lname = rs.getString(i++); 
					LocalDate join = Convert.getInstance().convertToLocalDate(rs.getTimestamp(i)); i++;
					LocalDate end = Convert.getInstance().convertToLocalDate(rs.getTimestamp(i)); i++;
					results.add(new FlightAttendant(id, fname, lname, join, end));
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
	public boolean addFlightAttendant(FlightAttendant emp) {
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_FLIGHT_AT)) {
				
				int i = 1;
				stmt.setString(i++, emp.getEmployeeId()); 
				stmt.setString(i++, emp.getFirstName()); 
				stmt.setString(i++, emp.getLastName()); 
				Timestamp joinTimeStamp = Timestamp.valueOf(emp.getJoinDate().atTime(0, 0, 0, 0));
				stmt.setTimestamp(i++, joinTimeStamp); 
				Timestamp endTimeStamp=null;
				if(emp.getEndDate()!=null)
					endTimeStamp = Timestamp.valueOf(emp.getEndDate().atTime(0, 0, 0, 0));
				stmt.setTimestamp(i++, endTimeStamp);  
				
				/* Executes the query */
				stmt.executeUpdate();
				Alert a = new Alert(AlertType.CONFIRMATION);
		    	 a.setContentText("The flight attendant was successfully added");
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
	
	
	public boolean editFlightAttendant(String employeeId, String firstName, String lastName, LocalDate joinDate, LocalDate endDate) {
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_FLIGHT_AT)) {
				
				int i = 1;
				stmt.setString(i++, firstName);
				stmt.setString(i++, lastName); 
				Timestamp joinTimeStamp = Timestamp.valueOf(joinDate.atTime(0, 0, 0, 0));
				stmt.setTimestamp(i++, joinTimeStamp);
				Timestamp endTimeStamp=null;
				if(endDate!=null)
					endTimeStamp = Timestamp.valueOf(endDate.atTime(0, 0, 0, 0));
				stmt.setTimestamp(i++, endTimeStamp);
				stmt.setString(i++, employeeId); 
				
				/* Executes the query */
				stmt.executeUpdate();
				Alert a = new Alert(AlertType.CONFIRMATION);
		    	 a.setContentText("The flight attendant was successfully updated");
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
	
	public FlightAttendant findFlightAttendant(String id) {
			FlightAttendant toFind = new FlightAttendant(id);
			for(FlightAttendant a : getFlightAttendants()) {
				if(a.equals(toFind))
					return a;
			}
			return null;
	}
	
	
}
