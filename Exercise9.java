/**
 * @(#)Exercise9.java
 *
 * Exercise9 application
 *
 * @author Michael Finnegan
 * @version 1.00 2021/2/23
 *
 *	This program calculates the slope of a line passing through user-supplied
 *  x1, y1 cordinates and coordinates (0, 0).
 *
 *  In Visual C# there is a variety of TryParse methods for converting strings into numbers.
 *  Unfortunately there is no built-in tryParse in Java, but I have created my own for this
 *  demonstration.
 */
import java.awt.*;
import javafx.util.*;
import javax.swing.*;

public class Exercise9 {
    
    //----------------------------------------------------------------
    private static Pair<Boolean, Integer> tryParseInt(String numStr){
    	Integer number = null;
    	Boolean isValid = true;
    	
    	try{
    		number = Integer.parseInt(numStr);
    	} catch(NumberFormatException e){
    		isValid = false;
    		// number is null
    	}	   	
    	return new Pair<Boolean, Integer>(isValid, number);
    } 
    //----------------------------------------------------------------
    private static int getCoordinate(String msg) {
    	
    	JLabel label = new JLabel(msg);
        label.setFont(new Font("Serif", Font.PLAIN, 20));
    	Pair<Boolean, Integer> kv;
	    
	    do{	    
	    	String numStr = JOptionPane.showInputDialog(label);
	    	
	    	if(!(kv = tryParseInt(numStr)).getKey()){
	    		label = new JLabel(String.format("Not a valid integer\n"));	    			    
	    		JOptionPane.showMessageDialog(null, label, "Error", JOptionPane.ERROR_MESSAGE );
	    	}
	    }while(!kv.getKey());	
		
		return kv.getValue();
    }
    //----------------------------------------------------------------
    private static String slope(int x1, int y1) {
    	
    	double numerator =  (0 - y1);
		double denumerator =  (0 - x1);
		
		return (x1 == 0)? "undefined": String.format("%.3f", numerator / denumerator);
    }
    //----------------------------------------------------------------
    public static void main(String[] args) {
    
    	int x1 = getCoordinate("Enter the X1 coordinate");	
    	int y1 = getCoordinate("Enter the Y1 coordinate");	
    	
    	String msg = String.format("The slope of the line passing through the point (%d, %d) and (0, 0) is %s ", 
    		x1, y1, slope(x1, y1));
    	JLabel label = new JLabel(msg);
        label.setFont(new Font("Serif", Font.PLAIN, 20));
 
     
     	JOptionPane.showMessageDialog(null, label, "Information", JOptionPane.INFORMATION_MESSAGE);
 
    	System.exit(0);
    }
}
