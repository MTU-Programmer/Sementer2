/**
 * @(#)HexToDec2.java
 *
 * HexToDec2 application
 *
 * @author Michael Finnegan
 * @version 1.00 2021/2/17
 * This is a more succinct version of the hexadecimal to decimal converter,
 * with negates the need for the Math floating-point calculations.
 */
 
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextArea;
import java.util.Arrays;
import java.util.Scanner;
  
public class HexToDec2 {
    
    
    
    //------------------------------------------------------------
    //The string must be exactly 6 charcters in length and only
    //contain hexadecimal characters.
    private static boolean isValidHexadecimal(String hexStr){
    	
    	int ch;
    	if(hexStr == null || hexStr.trim().isEmpty() || hexStr.trim().length() > 6){
    		return false;
    	}  			
    	hexStr = hexStr.trim().toUpperCase();
    	
    	for(int i = 0; i < 6; i++) {
    		
    		ch = (int) hexStr.charAt(i); 
    		if(!(ch > 47 && ch < 58? true: (ch > 64 && ch < 71? true: false)))
    			return false;
    		
    	}
    	return true;
    }
    //------------------------------------------------------------
    //Conver a Hexadecimal string to decimal number in a string.
    private static String hexToDecimal(String inp){
    	String input = inp.trim().toUpperCase();
    	int ch;
    	long acc = 0;
    	int weight = 1048576;

    	for(int i = 0; i < 6; i++) {
    		ch = (int) input.charAt(i); 					//ch = ASCII code
    		acc += weight * (( ch< 58)? ch - 48: ch - 55);
    		weight = weight >> 4;  		
    	}
    	
    	return acc + "";
    }
    //------------------------------------------------------------
    
    public static void main(String[] args) {
    	
    	String decStr, msg, userHexStr = "",  badMsg = "The value entered is not a 6-character hexadecimal number";
    	
    	userHexStr = JOptionPane.showInputDialog("Please enter your 6-character hexadecimal value");
    	
    	if(isValidHexadecimal(userHexStr) == false){
    		JOptionPane.showMessageDialog(null, badMsg, "Program Results", JOptionPane.INFORMATION_MESSAGE);
    	}else{
    		decStr = hexToDecimal(userHexStr);
    		msg = "The decimal equivalent of " + userHexStr.toUpperCase() + " is " + decStr;
    		JOptionPane.showMessageDialog(null, msg, "Program Results", JOptionPane.INFORMATION_MESSAGE);
    	}
    	
    	System.exit(0);
    }
}
