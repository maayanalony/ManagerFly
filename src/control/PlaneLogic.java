package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import entity.Plane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import utils.Consts;

public class PlaneLogic {
	
		
		private static PlaneLogic _instance;

		private PlaneLogic() {
		}

		public static PlaneLogic getInstance() {
			if (_instance == null)
				_instance = new PlaneLogic();
			return _instance;
		}
		

		public ArrayList<Plane> getPlanes() {
			ArrayList<Plane> results = new ArrayList<Plane>();
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_PLANES);
						ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						int i = 1;
						results.add(new Plane(rs.getString(i++), rs.getInt(i++)));
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
		 * fetches all available planes from DB file.
		 * @return ArrayList of planes.
		 */
		public ArrayList<Plane> getUnavailablePlanes(Timestamp depTimeStamp, Timestamp arvTimeStamp) {
			ArrayList<Plane> results = new ArrayList<Plane>();
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_SEL_UNAVAILABLE_PLANS)){
					stmt.setTimestamp(1,depTimeStamp);
					stmt.setTimestamp(2,arvTimeStamp);
					ResultSet rs = stmt.executeQuery();	{	
					while (rs.next()) {
						int i = 1;
						results.add(new Plane(rs.getString(i++), rs.getInt(i++)));
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
		
		
		
		/*----------------------------------------- ADD / REMOVE / UPDATE PLANES METHODS --------------------------------------------*/
	
		
		/**
		 * Adding a new plane with the parameters received from the form.
		 * return true if the insertion was successful, else - return false
	     * @return 
		 */
		public boolean addPlane(Plane plane,int Fcol, int Frow, int Bcol, int Brow, int Tcol, int Trow ) {
			
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_PLANE)) {
					
					int i = 1;
					
					stmt.setString(i++, plane.getTailNumber()); // can't be null
					stmt.setInt(i++, plane.getNumOfFlightAttendants()); // can't be null
					
					/* Executes the query */
					stmt.executeUpdate();

					
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return false;
			}
			

			// Add seats to the new plane
			
			for(Integer i=1; i<=Frow;i++) {
				for(Integer j=0;j<Fcol;j++) {					
					SeatLogic.getInstance().addSeatToPlane(i.toString(), String.valueOf((char)(j+'A')) , plane.getTailNumber(), "FIRST_CLASS");
				}
			}
			for(Integer i=Frow+1; i<=Frow+Brow;i++) {
				for(Integer j=0;j<Bcol;j++) {					
					SeatLogic.getInstance().addSeatToPlane(i.toString(), String.valueOf((char)(j+'A')) , plane.getTailNumber(), "BUISNESS_CLASS");
				}
				
			}
			for(Integer i=Frow+Brow+1; i<=Frow+Brow+Trow;i++) {
				for(Integer j=0;j<Tcol;j++) {
					SeatLogic.getInstance().addSeatToPlane(i.toString(), String.valueOf((char)(j+'A')) , plane.getTailNumber(), "TOURIST_CLASS");
				}
			}
			
			//Finally shows confimeration masagge
			Alert a = new Alert(AlertType.CONFIRMATION);
	    	a.setContentText("The plane was successfully added");
	    	a.show();
			return true;
	
		}
		
		public Plane findPlane(String num) {
			Plane toFind = new Plane(num);
			for(Plane p : getPlanes()) {
				if(p.equals(toFind))
					return p;
			}
			return null;
		}
}
