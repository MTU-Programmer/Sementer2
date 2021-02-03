/*
 * Michael Finnegan, Wednesday 3 February 2021
 * 
 * This program checks a string array of ISBN numbers, and produces an output which
 * indicates the valid ones and also indicates which ones are not valid ISBN numbers. 
 */

package isbn;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextArea;

public class ISBNChecker {

	
	/*-----------------------------------------------------------------
	 * This function returns true if the string contains a valid ISBN number
	 * It also returns a cleaned version of the ISBN number without spaces 
	 * in between digits or or hyphens in between digits.
	 */
	private static boolean checkISBN(String ISBN, StringBuilder sb) {
		
		int i;
		boolean workerEnded = false;
		String isbn = ISBN.trim();
		if(sb.length() > 0){
			sb.delete(0, sb.length());
		}
		if(isbn.length() == 0) {
			return false;
		}
		char[] isbnChars = new char[10];
		//remove any white spaces between characters
		i = 0;
		for(char ch : isbn.toCharArray()) {
			
			//Are you trying to force the worker to process another character 
			//after he has already stored 10 valid ISBN characters. If so then the ISBN is not valid.
			if(workerEnded) {
				return false;
			}
			
			if(ch ==' ' || ch == '-') {
				//Skip white space and '-' characters within ISBN number.
				continue;
			}else {
				isbnChars[i++] = ch;
			}
				
			if(i < 10) {
				if(ch >= '0' && ch <='9') {
					continue; //get next character
				}else {
					//We met a non-digit char within the first 9 characters
					//Therefore it is not a valid ISBN number.
					return false;
				}
					
			}else {
				//We have just stored the 10th character.
				//See is it either a digit or X or x.
				if(!((ch >='0' && ch <='9') || ch =='X' || ch == 'x')) {
					return false;
				}else {
					//The 10th char is valid, but end the worker
					workerEnded = true;
				}
				
			}//end else of outer if
			
		}//end for
		
		if(i < 10) {
			return false;
		}
		
		int lastDigit = (((isbnChars[9] >= '0') && (isbnChars[9] <='9') )? ((int) isbnChars[9]) - 48: 10);
		//We now know that the following:
		// 1. our isbn contains 10 valid characters.
		// 2. The first 9 characters are digits.
		// 3. The 10th character is either a digit or X or x.
		// We have put the value of the last digit or the value 10 into the int lastDigit
		/*
		 * The “golden rule” works as follows: the first 9 digits of the ISBN, when multiplied
			respectively by each of the numbers 10, 9, 8, 7, 6 ….. down to 2, and then added together
			will produce some total. When this total is divided by 11, then a remainder is produced
			and subtracting this remainder from 11 must be equal to the numeric value of the last
			character, the so-called “check digit”.
			
			So, for example, the ISBN 0140124993 is a valid ISBN because it contains 10 characters,
			the first 9 of which are all digits, the last which is also a digit and
			(0 x 10) + (1 x 9) + (4 x 8) + (0 x 7) + (1 x 6) + (2 x 5) + (4 x 4) + (9 x 3) + (9 x 2) = 118
			and 118/11 = 10 remainder 8 and finally 11-8 = 3 which is equal to the numeric value of the 
			last character, the "check digit".
		 */
		double d = 0.0, total = 0.0;
		
		for(i = 10; i >= 2; i--) {
			d = Character.getNumericValue(isbnChars[10 - i]) * i;
			total += d;
		}
		
		int num = (int) total;
		
		if((11 - (num % 11)) != lastDigit) {
			return false;
		}
		
		sb.append(isbnChars);
		return true;
		
	}
	//--------------------------------------------------------------------
	
	
	
	public static void main(String[] args) {
		String isbnInput = "0140124993";
		StringBuilder sb = new StringBuilder();
		String isbnCleaned = new String();
		String[] isbnArr = new String[] {
										//Valid ISBN numbers
										"123456789x",
										"865724831X",
										"1843560283",
										"1762836491",
										"8337821027",
										//invalid isbn numbers
										"1883560283",
										"864724831X",
										"0740124993",
										//More valid isbn numbers
										"1-85868291-6",
									};
		

		JTextArea textArea = new JTextArea(20, 60);
		Font textAreaFont = new Font("Monospaced", Font.BOLD, 14);
		textArea.setFont(textAreaFont);
		textArea.setText(String.format(""));
		
		int i =0;
		for(String s: isbnArr) {
			isbnInput = s;
			i++;
			boolean isISBN = checkISBN(isbnInput, sb);
			
			if(isISBN) {
				textArea.append(String.format("%-5d%-16s is a valid ISBN number \n",i, isbnInput));
			}else {
				textArea.append(String.format("%-5d%-16s is not a valid ISBN number \n",i, isbnInput));
			}

		}
		
		JOptionPane.showMessageDialog(null, textArea, "Formatted Data", JOptionPane.PLAIN_MESSAGE);

		System.exit(0);

	}//end main

}
