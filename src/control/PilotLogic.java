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

import entity.Pilot;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import utils.Consts;
import utils.Convert;

public class PilotLogic {
	
	private static PilotLogic _instance;

	private PilotLogic() {
	}

	public static PilotLogic getInstance() {
		if (_instance == null)
			_instance = new PilotLogic();
		return _instance;
	}
	
	/**
	 * fetches all airports from DB file.
	 * @return ArrayList of planes.
	 */
	public ArrayList<Pilot> getPilots() {
		ArrayList<Pilot> results = new ArrayList<Pilot>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_PILOTS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					String id = rs.getString(i++);
					String fname = rs.getString(i++); 
					String lname = rs.getString(i++); 
					LocalDate join = Convert.getInstance().convertToLocalDate(rs.getTimestamp(i)); i++;
					LocalDate end = Convert.getInstance().convertToLocalDate(rs.getTimestamp(i)); i++;
					String licenseNum = rs.getString(i++); 
					LocalDate dateIssueLicense = Convert.getInstance().convertToLocalDate(rs.getTimestamp(i)); i++;
					results.add(new Pilot(id, fname, lname, join, end, licenseNum, dateIssueLicense));
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
	public boolean addPilot(Pilot emp) {
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_PILOT)) {
				
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
				stmt.setString(i++, emp.getLicenseNumber()); 
				Timestamp issueTimeStamp = Timestamp.valueOf(emp.getDateIssueLicense().atTime(0, 0, 0, 0));
				stmt.setTimestamp(i++, issueTimeStamp); 
				
				/* Executes the query */
				stmt.executeUpdate();
				Alert a = new Alert(AlertType.CONFIRMATION);
		    	 a.setContentText("The Pilot was successfully added");
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
	
	
public boolean editPilot(String employeeId, String firstName, String lastName, LocalDate joinDate, LocalDate endDate,
		String licenseNumber, LocalDate dateIssueLicense) {
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_PILOT)) {
				
				int i = 1;
				
				stmt.setString(i++, firstName);
				stmt.setString(i++, lastName); 
				Timestamp joinTimeStamp = Timestamp.valueOf(joinDate.atTime(0, 0, 0, 0));
				stmt.setTimestamp(i++, joinTimeStamp);
				Timestamp endTimeStamp=null;
				if(endDate!=null)
					endTimeStamp = Timestamp.valueOf(endDate.atTime(0, 0, 0, 0));
				stmt.setTimestamp(i++, endTimeStamp); 
				stmt.setString(i++, licenseNumber);
				Timestamp issueTimeStamp = Timestamp.valueOf(dateIssueLicense.atTime(0, 0, 0, 0));
				stmt.setTimestamp(i++, issueTimeStamp);
				stmt.setString(i++, employeeId); 
				
				/* Executes the query */
				stmt.executeUpdate();
				Alert a = new Alert(AlertType.CONFIRMATION);
		    	 a.setContentText("The pilot was successfully updated");
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

	public Pilot findPilot(String id) {
		Pilot toFind = new Pilot(id);
		for(Pilot a : getPilots()) {
			if(a.equals(toFind))
				return a;
		}
		return null;
	}
}
