/**
 * @(#)AcesHigh.java
 *
 * AcesHigh application
 *
 * @author Michael Finnegan
 * @version 1.00 2021/2/17
 *
 * This program asks the user to enter the names of two card players.
 * It then performs the following actions in a loop that iterates 3 times:
 * It deals each player a random card.
 * It displays the names of the players in a dialog message and the card given to each player.
 * 
 */

import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextArea;
import java.util.Arrays;
import java.util.Scanner;


public class AcesHigh {
    
    // Create a string decribing a card dealt randomly from a pack of 52.
    private static String dealCard(){
    	
    	int rank = (int) (Math.random() * (14 - 2 + 1)) + 2;
    	String specialCards[] = new String[] {"Jack", "Queen", "King", "Ace"};
    	int suit = (int) (Math.random() * 4);
    	
    	return ((rank < 11)? rank+"": specialCards[rank - 11]) + " of " + (new String[]{"Hearts", "Diamonds", "Clubs", "Spades"})[suit];
    }
    
    
    public static void main(String[] args) {
    
    	JTextArea textArea = new JTextArea();
    	textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    	
    	String player1 = JOptionPane.showInputDialog("Please enter the name of player 1");
    	String player2 = JOptionPane.showInputDialog("Please enter the name of player 2");
    	
    	for(int i = 0; i < 3; i++){
    		
	    	textArea.setText(String.format("%-20s%s\n\n", "Player Name", "Card Dealt"));
	    	textArea.append(String.format("%-20s%s\n", player1, dealCard()));
	    	textArea.append(String.format("%-20s%s\n", player2, dealCard()));	    		    	
	    	JOptionPane.showMessageDialog(null, textArea, "Game Info", JOptionPane.PLAIN_MESSAGE);
    	}
    	   	
    	System.exit(0);
    }
}
