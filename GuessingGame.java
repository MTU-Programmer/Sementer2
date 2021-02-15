/**
 * @(#)GuessingGame.java
 *
 * GuessingGame application
 *
 * @author Michael Finnegan
 * @version 1.00 2021/2/15
 *
 *
 * A random number between 1 and 100 is generated. There are two players: a human and the computer.
 * The object of the game is to guess the number. The human enters his/her guess. The computer takes a guess too.
 * whoever guesses closest to the original number wins the game. The game is played in a loop five times.
 */
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextArea;
import java.util.Arrays;
import java.util.Scanner;


public class GuessingGame {
    
    
    private static int generateRandomNumber(int low, int high){
    	
    	return (int) (Math.random() * (high - low + 1))+ low;
    }
    //---------------------------------------------------------------
    //The user entry must be:
    //1. a number
    //2. within the range 1 to 100
    private static boolean isGuessValid(String userNumStr){
    	boolean isValid = true;
    	int userNum;
    	try{
    		userNum = Integer.parseInt(userNumStr);
    		if(!(userNum >= 1 && userNum <= 100)) {
    			isValid = false;
    		}
    	}catch(Exception e){
    		isValid = false;
    	}
    	return isValid;
    }
    
    //----------------------------------------------------------------
    //Decide who won and then
    //return a message to the caller concerning the winner.
    private static String whoIsCloser(int userNum, int computerNum, int rNum){
    	
    		String intro = "The generated number was " + rNum + ", the computer number was " + computerNum + ", ";
    		intro += ", the user number was " + userNum + ", ";   	
    		String[] tagOn = new String[3];
    		tagOn[0] = "and neither player wins.";
    		tagOn[1] = "and the winner was the user.";
    		tagOn[2] = "and the winner was the computer.";
    		int userDiff = ((rNum - userNum) < 0)? userNum - rNum: rNum - userNum;
    		int computerDiff = ((rNum - computerNum) < 0)? computerNum - rNum: rNum - computerNum;
    		int winner = (userDiff == computerDiff)? 0:((userDiff < computerDiff)? 1: 2);
    		
    		return intro + tagOn[winner];
    		
    }
    //----------------------------------------------------------------
    public static void main(String[] args) {
    	int i, rNum, userNum, computerNum;
    	String userNumStr, winnerMsg;
    	
    	for( i = 1; i<=5; i++ ){
    		
    		rNum = generateRandomNumber(1, 100);
    		userNumStr = JOptionPane.showInputDialog("User, please enter your guess (a value between 1 and 100)");
    		if(isGuessValid(userNumStr) == false){
    			JOptionPane.showMessageDialog(null, "Invalid guess value entered - this game is void", "Error", JOptionPane.INFORMATION_MESSAGE);
    			System.exit(0);
    		}
    		userNum = Integer.parseInt(userNumStr);
    		computerNum = generateRandomNumber(1, 100);
    		winnerMsg = whoIsCloser(userNum, computerNum, rNum);
    		JOptionPane.showMessageDialog(null, winnerMsg, "The Result", JOptionPane.INFORMATION_MESSAGE);
    	}
    	  	
    	System.exit(0);
    }
}
