package boundary;


import java.util.ArrayList;

import enumeration.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import utils.Convert;

public class EnterController {
	public static User user;
	@FXML private ComboBox<String> selectUserCombo;
	@FXML private Label empty;
	
	public void initialize() {
		ArrayList<String> userList = new ArrayList<>();
		User [] users= User.values();
    	for(int i=0; i<users.length; i++) 
    		userList.add(Convert.getInstance().convertUserToString(users[i]));
    	ObservableList<String> usersObservList = FXCollections.observableArrayList(userList);
    	selectUserCombo.setItems(usersObservList);  
    	
	}
	
	// setting external border pane by user and welcome-Panel as internal border pane
	public void enter() {
		String inputUser= selectUserCombo.getValue();
		if(inputUser==null) 
			empty.setVisible(true);
		else {
			EnterController.user= Convert.getInstance().convertUser(inputUser);
			Main.userLayout(); 
		}
		Main.setGeneralLayout(Main.mainLayout, "/boundary/welcomePanel.fxml");
			
	}

}
