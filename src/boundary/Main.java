package boundary;
	
import java.io.IOException;

import enumeration.User;
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	
	private static Stage primaryStage;
	public static BorderPane mainLayout;
	public static BorderPane secondLayout;
	private static Scene mainView;
	
	@Override
	public void start(Stage s) {
		
		try {
			primaryStage= s;
			setMainLayout("/boundary/MainLayout.fxml");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// external border pane
	public static void userLayout() {
		if(EnterController.user.equals(User.EMPLOYEE_MANAGER))
			setMainLayout("/boundary/LayoutEmployeeManager.fxml");
		else
			setMainLayout("/boundary/LayoutFlightManager.fxml");
		
	}
	public static void setMainLayout(String newMainLayout) {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(newMainLayout));
			mainLayout = loader.load();
			mainView= new Scene (mainLayout, 1100,700);  // create new scene that runs the resource
			primaryStage.setScene(mainView); // create the scene on the stage
			primaryStage.show(); // showing the scene
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	// set internal border pane
	public static void setGeneralLayout(BorderPane bpOut, String bpInPath) {	
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(bpInPath));
			BorderPane bpIn = loader.load();
			bpOut.setCenter(bpIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	// create pop up screen
	public static Stage popStage = new Stage();
	public static void openPopScreen(Stage popStage, String popBpPath) {
		
		popStage.initModality(Modality.APPLICATION_MODAL);
		popStage.initOwner(primaryStage);
		BorderPane popBp = new BorderPane ();
		setGeneralLayout(popBp, popBpPath);
		Scene popScene = new Scene (popBp, 600,500);
		popStage.setScene(popScene);
		popStage.show();
	}
	

	public static void changeUser() {
	     Main.setMainLayout("/boundary/MainLayout.fxml");
	 }
	
	public static void main(String[] args) {
		launch(args); 
	}
}
