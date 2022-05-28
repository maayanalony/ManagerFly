package utils;

import java.net.URLDecoder;

public class Consts {
	
	private Consts() {
		throw new AssertionError();
	}
	
	protected static final String DB_FILEPATH = getDBPath();
	public static final String CONN_STR = "jdbc:ucanaccess://" + DB_FILEPATH;
	public static final String JDBC_STR = "net.ucanaccess.jdbc.UcanaccessDriver";
	
	/*----------------------------------------- CONSTANTS -----------------------------------------*/
	
	public static final int MIN_FLIGHT_ATTENDANTS = 2;
	
	/*----------------------------------------- FLIGHT QUERIES -----------------------------------------*/
	public static final String SQL_SEL_FLIGHTS = "SELECT * FROM TblFlights";
	public static final String SQL_DEL_FLIGHT = "{ call qryDelFlight(?) }";
	public static final String SQL_INS_FLIGHT = "{ call addFlight(?,?,?,?,?,?) }";
	public static final String SQL_UPD_FLIGHT = "{ call qryUpdateFlight(?,?,?,?,?,?,?,?) }";
	public static final String SQL_UPD_FLIGHT_STATUS = "{ call qryUpdateFlightStatus (?,?) }";
	public static final String SQL_UPD_FLIGHT_SALE_STATUS = "{ call qryUpdateFlightSaleStatus(?,?) }";
	public static final String SQL_SEL_UPDATED_TODAY = "SELECT * FROM qryUpdatedTodayFlights";
	// for scheduling
	public static final String SQL_SEL_ATTENDANT_IN_FLIGHT = "SELECT * FROM  TblFlightAttendantsInFlight";
	public static final String SQL_UPD_PILOTS_IN_FLIGHT = "{ call qryUdatePilotsInFlight(?,?,?) }";
	public static final String SQL_ADD_ATTENDANT_IN_FLIGHT ="{ call qryAddAttToFlight(?,?) }";
	public static final String SQL_DEL_ATTENDANT_IN_FLIGHT = "{ call qryDelAttFromFlight(?,?) }";
	
	
	
	/*----------------------------------------- AIRPORTS QUERIES ----------------------------------------------*/
	public static final String SQL_SEL_AIRPORTS = "SELECT * FROM TblAirports";
	public static final String SQL_INS_AIRPORT = "{ call qryInsAirport(?,?,?,?) }";
	public static final String SQL_UPD_AIRPORT = "{ call updateAirport(?,?,?,?,?) }";
	
	
	/*----------------------------------------- PLANES QUERIES ----------------------------------------------*/
	public static final String SQL_SEL_PLANES = "SELECT * FROM TblPlanes";
	public static final String SQL_SEL_UNAVAILABLE_PLANS = "{ call qryGetUnavailablePlanes(?,?) }";
	public static final String SQL_INS_PLANE = "{ call qryInsPlane(?,?) }";
	
	
	/*----------------------------------------- SEATS QUERIES ----------------------------------------------*/
	public static final String SQL_SEL_SEATS = "SELECT * FROM TblSeats";
	public static final String SQL_INS_SEAT_TO_PLANE = "{ call qryInsSeatInPlnae(?,?,?,?) }";
	public static final String SQL_SEAT_BY_PLANE = "{ call qrySeatsIByPlane(?) }";
	
	
	/*----------------------------------------- PILOTS QUERIES ----------------------------------------------*/
	public static final String SQL_SEL_PILOTS = "SELECT * FROM TblPilots";
	public static final String SQL_INS_PILOT = "{ call qryInsPilot(?,?,?,?,?,?,?) }";;
	public static final String SQL_UPD_PILOT = "{ call updatePilot (?,?,?,?,?,?,?) }";
	
	/*----------------------------------------- FLIGHT ATTENDANTS QUERIES ----------------------------------------------*/
	public static final String SQL_SEL_FLIGHT_AT = "SELECT * FROM TblFlightAttendants";
	public static final String SQL_INS_FLIGHT_AT = "{ call qryInsFlightAttendant(?,?,?,?,?) }";
	public static final String SQL_UPD_FLIGHT_AT = "{ call updateFlightAttendant(?,?,?,?,?) }";
	
	
	/*----------------------------------------- LAND ATTENDANTS QUERIES ----------------------------------------------*/
	public static final String SQL_SEL_LAND_AT = "SELECT * FROM TblLandAttendants";
	public static final String SQL_INS_LAND_AT = "{ call qryInsLandAttendant(?,?,?,?,?) }";
	public static final String SQL_UPD_LAND_AT = "{ call updateLandAttendant(?,?,?,?,?) }";
	
	/*----------------------------------------- SHIFTS QUERIES ----------------------------------------------*/
	public static final String SQL_SEL_SHIFTS = "SELECT * FROM TblShifts";
	public static final String SQL_INS_SHIFT = "{ call qryInsShift(?,?) }";
	public static final String SQL_SEL_ATTENDANT_IN_SHIFT = "SELECT * FROM TblLandAttendantInShift";
	public static final String SQL_UPD_ATTENDANT_IN_SHIFT = "{ call qryInsAttToShift(?,?,?,?) }";
	public static final String SQL_DEL_ATTENDANT_IN_SHIFT = "{ call qryDelAttFromShift(?,?,?,?) }";
	
	/*----------------------------*/
	
	public static final String SQL_EXPORT_FLIGHTS_DATA= "SELECT TblFlights.filghtID, TblFlights.depDate, TblFlights.arvDate, TblFlights.depAirportID, TblFlights.arvAirportID, TblFlights.tailNumber, TblFlights.chiefPilotID, TblFlights.secondaryPilotID, TblFlights.flightStatus, TblFlights.saleStatus, TblFlights.updateDate\r\n"
			+ "FROM TblFlights\r\n"
			+ "WHERE (((TblFlights.updateDate)=Date()));\r\n"
			+ "";
	public static final String SQL_EXPORT_SEATS_DATA="SELECT TblPlanes.tailNumber, TblSeatsInPlane.lineNumber, TblSeatsInPlane.seatNumber, TblSeatsInPlane.seatClass\r\n"
			+ "FROM (qryExportFlightsData INNER JOIN TblPlanes ON qryExportFlightsData.tailNumber = TblPlanes.tailNumber) INNER JOIN TblSeatsInPlane ON TblPlanes.tailNumber = TblSeatsInPlane.tailNumber;\r\n"
			+ "";
	
	public static final String SQL_EXPORT_AIRPORTS_DATA="SELECT TblAirports.airportCode, TblAirports.country, TblAirports.city, TblAirports.GMTCode\r\n"
			+ "FROM TblAirports;\r\n"
			+ "";
	
	
	
	
	/**
	 * find the correct path of the DB file
     * @return the path of the DB file (from eclipse or with runnable file)
	 */
	private static String getDBPath() {
		try {
			String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decoded = URLDecoder.decode(path, "UTF-8");
			// System.out.println(decoded) - Can help to check the returned path
			if (decoded.contains(".jar")) {
				decoded = decoded.substring(0, decoded.lastIndexOf('/'));
				return decoded + "/src/entity/MANAGER_FLY_DB.accdb";
			} else {
				decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
				System.out.println(decoded);
				return decoded + "/src/entity/MANAGER_FLY_DB.accdb";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
