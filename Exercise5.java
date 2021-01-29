/*
		Exercise 5
		Write a Java program that reads in exactly 5 words using a for loop. The program
		should determine the average number of vowels per word along with the average number
		of consonants per word and display these to 2 decimal places (note that you’ll need
		another for loop within the main for loop for this processing). You can take it that all
		the words entered will be valid and will only contain letters. It should also determine the
		longest word entered. Note that your program should run as indicated in the following
		sample screenshot.
	*/
import java.util.Scanner;

	class WordAnalysis
	{
			public static double totalVowels = 0.0;
			public static double totalConsonants = 0.0 ;
			public static String longestWord = "";
			static double wordsProcessed = 0.0;


		//----------------------------------------------------------------------
		public static void ProcessWord(String aWord){


			int vc = 0, cc = 0;			//vc = vowel count. cc = consonant count
			char ch;

			char chArr[]= aWord.toLowerCase().toCharArray();

			for( int j = 0; j < chArr.length; j +=1){
				ch = chArr[j];
				if( ch =='a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'){
					vc++;
				}else if( (ch >='b') && (ch <= 'z')){
					cc++;
				}

			}
			totalVowels += vc;
			totalConsonants += cc;

			// If this is the longest word received so far then record that fact.
			if(aWord.length() > longestWord.length()){
				longestWord = aWord;
			}
			wordsProcessed += 1;
		}
		//---------------------------------------------------------------
		public String[] displayStatistics(){

			String[] lines = new String[3];
			double avgVowels = totalVowels / wordsProcessed;
			double avgConsonants = totalConsonants / wordsProcessed;
			lines[0] = String.format("The average number of vowels per word: %.2f", avgVowels);
			lines[1] = String.format("The average number of consonants per word: %.2f", avgConsonants);
			lines[2] = String.format("The longest word: %s", longestWord);

			return lines;
		}
	} // End of class WordAnalysis


	public class Exercise5 {


	//--------------------------------------------------------
    public static void main(String[] args) {

		WordAnalysis WA = new WordAnalysis();
		String s;
    	Scanner input = new Scanner(System.in);
    	System.out.println("Enter 5 words. Press ENTER key after each word.\n");

		for(int i = 0; i < 5; i +=1){

			System.out.printf("Enter word %d: ", i+1);
			s = input.nextLine();
			WA.ProcessWord(s);
		}
		for(String l :WA.displayStatistics()){
			System.out.println(l);
		};

    }
}
