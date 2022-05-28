package boundary;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextFormatter;

public class TextFormats {
	
	private static TextFormats _instance;

	private TextFormats() {
	}

	public static TextFormats getInstance() {
		if (_instance == null)
			_instance = new TextFormats();
		return _instance;
	}
	
	public UnaryOperator<TextFormatter.Change> onlyUpperCase(){
    	UnaryOperator<TextFormatter.Change> filter = change -> {
    	       String text = change.getText();
    	       if (text.matches("[A-Z]?")) 
    	    		   return change;
    	       
    	       return null;
    	   };
		return filter;
	}
	
	
	public UnaryOperator<TextFormatter.Change> onlyLetters(){
    	UnaryOperator<TextFormatter.Change> filter = change -> {
    	       String text = change.getText();
    	       if (text.matches("[A-Za-z]?")) 
    	    		   return change;
    	       
    	       return null;
    	   };
		return filter;
	}
	
	public UnaryOperator<TextFormatter.Change> notNumbers(){
    	UnaryOperator<TextFormatter.Change> filter = change -> {
    	       String text = change.getText();
    	       if (text.matches("[^0-9]?")) 
    	    		   return change;
    	       
    	       return null;
    	   };
		return filter;
	}
	
	public UnaryOperator<TextFormatter.Change> onlyNumbers(){
    	UnaryOperator<TextFormatter.Change> filter = change -> {
    	       String text = change.getText();
    	       if (text.matches("[0-9]?")) 
    	    		   return change;
    	       
    	       return null;
    	   };
		return filter;
	}

}
