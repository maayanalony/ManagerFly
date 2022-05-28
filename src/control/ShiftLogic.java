package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import entity.LandAttendant;
import entity.Shift;
import enumeration.LandAttendantRole;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import utils.Consts;
import utils.Convert;

public class ShiftLogic {
	
	private static ShiftLogic _instance;

	private ShiftLogic() {
	}

	public static ShiftLogic getInstance() {
		if (_instance == null)
			_instance = new ShiftLogic();
		return _instance;
	}
	
	/**
	 * fetches all airports from DB file.
	 * @return ArrayList of planes.
	 */
	public ArrayList<Shift> getShifts() {
		ArrayList<Shift> results = new ArrayList<Shift>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_SHIFTS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					LocalDateTime start = Convert.getInstance().convertToLocalDateTime(rs.getTimestamp(i)); i++;
					LocalDateTime end = Convert.getInstance().convertToLocalDateTime(rs.getTimestamp(i)); i++;
					Shift newShift= new Shift(start, end);
					getLandAttendantsInShift(newShift);
					results.add(newShift);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<LandAttendant> getLandAttendantsInShift(Shift shift) {
		ArrayList<LandAttendant> results = new ArrayList<LandAttendant>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_ATTENDANT_IN_SHIFT);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					LocalDateTime start = Convert.getInstance().convertToLocalDateTime(rs.getTimestamp(i)); i++;
					LocalDateTime end = Convert.getInstance().convertToLocalDateTime(rs.getTimestamp(i)); i++;
					
					
					if(start.equals(shift.getStartTime()) && end.equals(shift.getEndTime())) {
						LandAttendant la= LandAttendantLogic.getInstance().findLandAttendant( rs.getString(i++)); 
						LandAttendantRole role= Convert.getInstance().convertRole(rs.getString(i++));
						shift.addAttendantToShift(role, la);
					}
					
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
	public boolean addShift(Shift shift) {
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_SHIFT)) {
				
				int i = 1;
				
				Timestamp startTimeStamp = Timestamp.valueOf(shift.getStartTime());
				stmt.setTimestamp(i++, startTimeStamp);
				Timestamp endTimeStamp = Timestamp.valueOf(shift.getEndTime());
				stmt.setTimestamp(i++, endTimeStamp);
				
				
				/* Executes the query */
				stmt.executeUpdate();
				Alert a = new Alert(AlertType.CONFIRMATION);
		    	 a.setContentText("The shift was successfully added");
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
	
	
	public boolean addAttendantsToShift(Shift s, LandAttendant la, LandAttendantRole role) {
		
		try {
			
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_ATTENDANT_IN_SHIFT)) {
				
				int i = 1;
				Timestamp startTimeStamp = Timestamp.valueOf(s.getStartTime());
				stmt.setTimestamp(i++, startTimeStamp); 
				Timestamp endTimeStamp = Timestamp.valueOf(s.getEndTime());
				stmt.setTimestamp(i++, endTimeStamp); 
				stmt.setString(i++, la.getEmployeeId()); 
				stmt.setString(i++, Convert.getInstance().convertRole(role));
				
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
	
	public boolean removeAttendantFromShift(Shift s, LandAttendant la, LandAttendantRole role) {
		
		try {
			
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_DEL_ATTENDANT_IN_SHIFT)) {
				
				int i = 1;
				Timestamp startTimeStamp = Timestamp.valueOf(s.getStartTime());
				stmt.setTimestamp(i++, startTimeStamp); 
				Timestamp endTimeStamp = Timestamp.valueOf(s.getEndTime());
				stmt.setTimestamp(i++, endTimeStamp); 
				stmt.setString(i++, la.getEmployeeId()); 
				stmt.setString(i++, Convert.getInstance().convertRole(role));
				
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
	
}
