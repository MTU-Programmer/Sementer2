/**
 * @(#)HexToDec.java
 *
 * HexToDec application
 *
 * @author Michael Finnegan
 * @version 1.00 2021/2/16
 */
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextArea;
import java.util.Arrays;
import java.util.Scanner;



public class HexToDec {
    
    
    //------------------------------------------------------------------
    //Return true only if the string is a valid 6 character hexadecimal number.
    private static boolean isValidHexadecimal(String inp){
    	int i, len = inp.length(), validChars = 0;
    	char ch;
    	boolean result = false;
    	String input = inp.toUpperCase();
    	
    	if( len < 6)
    		return result;
    	
    	for(i = 0; i < 6; i++) {    		
    		ch = input.charAt(i);
    		if(Character.isDigit(ch) || (ch >= 'A' && ch <= 'F')){
    			validChars++;
    		}else{
    			break;
    		}   			   			
    	}
    	if(validChars == 6)
    		result = true;
    		
    	return result;
    }
    
    //-----------------------------------------------------------------
    //Convert a six digit hexadecimal number to its decimal equivalent.
    //Return the result in a string.
    private static String hexToDecimal(String inp){
    	int i = 0, ch, chv =0;
    	Long decNumber;
    	String input = inp.toUpperCase();
    	Double[] decArr = new Double[] {	Math.pow(16, 5),Math.pow(16, 4),Math.pow(16, 3),
    									Math.pow(16, 2), Math.pow(16, 1), Math.pow(16, 0) };
    
    	Double acc =0d;
    	for(i = 0; i < 6; i++){
    		ch = (int) input.charAt(i); 					//ch = ASCII code
    		chv = (((ch - 48) < 10)? ch - 48: ch - 55);
    		acc += (decArr[i] * chv);
    	}
    	decNumber = acc.longValue();
    	return decNumber.toString();
    }
    //-----------------------------------------------------------------
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
