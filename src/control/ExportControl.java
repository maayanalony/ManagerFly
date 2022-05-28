package control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import utils.Consts;

public class ExportControl {
	
	private static ExportControl instance;

	public static ExportControl getInstance() {
		if (instance == null)
			instance = new ExportControl();
		return instance;
	}


	public void exportToJSON() 
	{
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(
							Consts.SQL_EXPORT_FLIGHTS_DATA)){
					ResultSet rs = stmt.executeQuery(); {
				JsonArray updatedFlights = new JsonArray();
				while (rs.next()) {
					JsonObject updatedFlight = new JsonObject();
					for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
						updatedFlight.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					updatedFlights.add(updatedFlight);
				}
				JsonObject doc = new JsonObject();
				exportAirports(doc);
				exportSeats(doc);
				doc.put("Flights", updatedFlights);
				File file = new File("json/flights.json");
				file.getParentFile().mkdir();
				try (FileWriter writer = new FileWriter(file)) {
					writer.write(Jsoner.prettyPrint(doc.toJson()));
					writer.flush();
					 Alert alert = new Alert(AlertType.INFORMATION, "Flights data exported successfully!");
					 alert.setHeaderText("Success");
					 alert.setTitle("Success Data Export");
					 alert.showAndWait();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	private void exportAirports(JsonObject doc) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(
							Consts.SQL_EXPORT_AIRPORTS_DATA)){
					ResultSet rs = stmt.executeQuery(); {
				JsonArray updatedAirports = new JsonArray();
				while (rs.next()) {
					JsonObject updatedAirport = new JsonObject();
					for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
						updatedAirport.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					updatedAirports.add(updatedAirport);
				}
				doc.put("Airports", updatedAirports);
			}
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	private void exportSeats(JsonObject doc) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(
							Consts.SQL_EXPORT_SEATS_DATA)){
					ResultSet rs = stmt.executeQuery(); {
				JsonArray updatedSeats = new JsonArray();
				while (rs.next()) {
					JsonObject updatedSeat = new JsonObject();
					for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
						updatedSeat.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					updatedSeats.add(updatedSeat);
				}
				doc.put("Seats", updatedSeats);
			}
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}

	 
} 

