//Guessing game
//Michael Finnegan 12 May 2021
//User 1 Enters a secret word while user 2 looks away.
//User 2 then takes over and tries to guess the word by entering one letter at a time.

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
public class MichaelFinnegan{
 	//array(s) creation and other global references go here
	//-----------------------------------------------------
	JTextField textField;
                   	             
	JLabel label1 = new JLabel("Enter word");
	JLabel label2 = new JLabel("             ");
	String matchesStr = "";
	String secretWord;
	char[] matchesArr;
	int inputStage = 1;
	int guessCounter =0;
	ActionListener eventHandler;
	//--------------------------------------------------
	public MichaelFinnegan()
	{
		Font font = new Font("monospaced", Font.PLAIN, 12);
		JFrame frame = new JFrame("Word Game");
        frame.setLayout(new FlowLayout());
        frame.setSize(300, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       	label1.setFont(font);
        frame.add(label1);
        textField = new JTextField(10);
        textField.setFont((font));
        eventHandler = new EventHandler();
        textField.addActionListener(eventHandler);	    
        frame.add(textField);   
        label2.setFont(new Font("monospaced", Font.PLAIN, 18));       
        frame.add(label2);
        
		frame.setVisible(true);
	}
	//---------------------------------------------------
	private class EventHandler implements ActionListener{
		
		//-----------------------------------------------
		private String validateSecretWord(String secret){
			String word;
			String[] errorMessage = new String[]	{"", "Sorry, the word must contain at least 4 letters!",
			 										"Sorry, the word must only contain letters of the English alphabet!!"};

			
			if(secret == null || secret.length() < 4){
				return errorMessage[1];
			}
			
			//See if all of the characters in word are only letters.
			word = secret.toLowerCase();
			for(int i = 0; i < word.length(); i++){
				
				if(!Character.isLetter(word.charAt(i))){
					return errorMessage[2];
				}
			}
			
			return "";	//No error. Word validates ok.
		}
		//-----------------------------------------------
		private boolean isGuessLetterInSecret(String guessStr){
			boolean result = false;
			int i;
			if(guessStr == null || guessStr == ""){
				return false;				
			}
			char ch = guessStr.toLowerCase().charAt(0);
			
			for(i = 0; i < secretWord.length(); i++){
				
				if(ch == secretWord.charAt(i)){
					result = true;
					matchesArr[i] = ch;
				}
			}
			return result;
		}
		//-----------------------------------------------
		private char minLetter(){
			char minCh = secretWord.charAt(0);
			for(int i = 0; i < secretWord.length(); i++){
				if(minCh > secretWord.charAt(i)){
					minCh = secretWord.charAt(i);
				}
			}
			
			return minCh;
		}
		//-----------------------------------------------
		private char maxLetter(){
			char maxCh = secretWord.charAt(0);
			for(int i = 0; i < secretWord.length(); i++){
				if(maxCh < secretWord.charAt(i)){
					maxCh = secretWord.charAt(i);
				}
			}
			
			return maxCh;
		}

		//-----------------------------------------------
		public void actionPerformed(ActionEvent e)
		{
			String msg;
			switch(inputStage){
				case 1:										//The secret word was entered and the user just pressed ENTER
					secretWord = textField.getText();		
					msg = validateSecretWord(secretWord); 		//It must be at least 4 characters and all letters.
					if(msg!= ""){
						JOptionPane.showMessageDialog(null, msg, "Error!", JOptionPane.ERROR_MESSAGE);
					}else{
						//The secret work is validated ok. Move to input stage two. The second user must guess it.
						secretWord = secretWord.toLowerCase();
						label1.setText("Guess a letter");
						textField.setText(" ");
						inputStage = 2; 					//switch will jump to case 2 next time the user presses ENTER
						matchesArr = new char[secretWord.length()];
						for(int i = 0; i < secretWord.length(); i++ ){
							matchesArr[i] = '-';
						}
						label2.setText("    " + new String(matchesArr) +"    "); //Display all the dashes representing no matches yet.
						label2.setFont(new Font("monospaced", Font.PLAIN, 18));
						guessCounter = 0;
						matchesStr = "";
						textField.setText("");
						
					}					
					break;
					
				case 2://We are now in input stage 2. The second user entered a letter 
					guessCounter++;
					if(!isGuessLetterInSecret(textField.getText())){
						if((guessCounter % 8) == 0){
							 String[] options = new String[] {"Yes", "No", "Cancel"};
							int x = JOptionPane.showOptionDialog(null, "Would you like to simplify the game?", "Select an Option",
                					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, "Yes");
                			if(x == 0){
                				//Display a dialog: The lowest letter in the word is: 
                				//					The highest letter in the word is: 
                				msg = "Lowest letter in word is: "+ minLetter() + "\n";
                				msg += "Highest letter in word is: " + maxLetter() + "\n";
                				JOptionPane.showMessageDialog(null, msg, "Game Simplify Information", JOptionPane.INFORMATION_MESSAGE);
                				
                			}
						}//end of if(guessCounter == 8)
						
					}else{
						//The guess letter is in the secret word therefore:
						//1. Show the positions the guess letter occupies in the matches string
						//2. Compare the matches string to the secret word to see if they match. 
						//if they do match then:
						//	Display message "Congratulations - you got the word. It took you 12 guesses."
						//	Then End the game.
						//Otherwise the don't match therefore continue on with the game.
						matchesStr = new String(matchesArr);						
						label2.setText("    " + matchesStr +"    "); 							//Display the matches
						label2.setFont(new Font("monospaced", Font.PLAIN, 18));

						if(secretWord.compareTo(matchesStr) == 0 ){
							msg = "Congratulations - you got the word. It took you " + guessCounter + " guesses.";
							JOptionPane.showMessageDialog(null, msg, "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
							System.exit(0);
						}
					}
					
					textField.setText("");
					break;
			
			}//end of switch
						
		}//End of actionPerformed
	}//End of inner class EventHandler
	//---------------------------------------------------
	//Code for any user-defined methods required can go here

	public static void main(String args[])
	{
		MichaelFinnegan guiApp = new MichaelFinnegan();
	}

}
