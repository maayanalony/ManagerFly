package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.SeatInPlane;
import enumeration.SeatClass;
import utils.Consts;

public class SeatLogic {
	private static SeatLogic _instance;

	private SeatLogic() {
	}

	public static SeatLogic getInstance() {
		if (_instance == null)
			_instance = new SeatLogic();
		return _instance;
	}
	

	/**
	 * fetches all airports from DB file.
	 * @return ArrayList of planes.
	 */
	public ArrayList<SeatInPlane> getSeatsInPlane(String tailNumber) {
		ArrayList<SeatInPlane> results = new ArrayList<SeatInPlane>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_SEAT_BY_PLANE)){
				stmt.setString(1, tailNumber); 
				ResultSet rs = stmt.executeQuery(); {
					while (rs.next()) {
						int i = 1;
						results.add(new SeatInPlane(rs.getInt(i++), rs.getString(i++), rs.getString(i++), convertClass(rs.getString(i++))));
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
	
	public SeatClass convertClass(String classInPlane) {
		if(classInPlane.equals("FIRST_CLASS"))
			return SeatClass.FIRST_CLASS;
		else if(classInPlane.equals("BUISNESS_CLASS"))
				return SeatClass.BUISNESS_CLASS;
		return SeatClass.TOURIST_CLASS;
		
	}
	
	
	/************************** Query to Add seat to the DB ***************************/ 
	public boolean addSeatToPlane(String lineNumber, String seatNumber , String tailNumber, String seatClass) {
			
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_SEAT_TO_PLANE)) {
					
					int i = 1;
					
					
					stmt.setString(i++, lineNumber); // can't be null
					stmt.setString(i++, seatNumber); // can't be null
					stmt.setString(i++, tailNumber); // can't be null
					stmt.setString(i++, seatClass); // can't be null
					
					/* Executes the query */
					stmt.executeUpdate();
	//				Alert a = new Alert(AlertType.CONFIRMATION);
	//		    	 a.setContentText("The plane was successfully added");
	//		    	 a.show();
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
