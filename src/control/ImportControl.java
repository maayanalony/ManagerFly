package control;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import enumeration.FlightStatus;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import utils.Convert;

public class ImportControl {
	
	private static ImportControl instance;

	public static ImportControl getInstance() {
		if (instance == null)
			instance = new ImportControl();
		return instance;
	}
	
	public void importFromXML(String path) {
    	try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(path));
			doc.getDocumentElement().normalize();
			NodeList nl = doc.getElementsByTagName("flight");
			for (int i = 0; i < nl.getLength(); i++) {
				if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element el = (Element) nl.item(i);
					String f = el.getElementsByTagName("flightID").item(0).getTextContent();
					String s = el.getElementsByTagName("flightStatus").item(0).getTextContent();
					FlightStatus status=Convert.getInstance().convertFlightStatus(s);
					FlightLogic.getInstance().editFlightStatus(f, status);
				}
			}
			
			Alert alert = new Alert(AlertType.INFORMATION, "Data imported to data bases");
			alert.setHeaderText("Success");
			alert.setTitle("imported");
			alert.showAndWait();
			
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
    }


}
