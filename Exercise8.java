/**
 * @(#)Exercise8.java
 *
 * Exercise8 application
 *
 * @author Michael Finnegan
 * @version 1.00 2021/2/16

	The user is asked how mny times he/she wants the computer to toss a coin.
	Then the computer tosses the coin that number of times and displays the statistics to the user.
	Sample output:
		How many times do you want the computer to toss the coin?
		6
		Coin toss 1: heads
		Coin toss 2: tails
		Coin toss 3: tails
		Coin toss 4: heads
		Coin toss 5: heads
		Coin toss 6: heads
		=========Overall Results=========
		Percentage of heads: 67 
		Percentage of tails: 33 

 */
import java.util.Scanner;
public class Exercise8 {
    
    
    private static int tossCoin(){
    		return (int) (Math.random() *2) ;
    }
    
    
    public static void main(String[] args) {
    	int i, tossTimes, tossResult;
    	System.out.println("How many times do you want the computer to toss the coin?");
    	Scanner input = new Scanner(System.in);
    	tossTimes = input.nextInt();
    	int totalHeads =0, totalTails  =0;
    	int[] tossArr = new int[tossTimes];
    	
    	for(i = 0; i < tossTimes; i +=1){
    		
    		tossResult = tossCoin();
    		if( tossResult == 0 ){
    			totalHeads +=1;
    		}else{
    			totalTails +=1;
    		}
    		tossArr[i] = tossResult;
    	}
    	//-------------------------------------------
    	for(i = 0; i < tossTimes; i +=1){
    		if(tossArr[i] == 0){
    			System.out.printf("Coin toss %d: heads\n", i + 1);
    		}else{
    			System.out.printf("Coin toss %d: tails\n", i + 1);
    		}
    	}
    	
    	System.out.println("=========Overall Results=========");
    	
    	double totalHeadsD = totalHeads, tossTimesD = tossTimes, totalTailsD = totalTails;
    	double headsPercent = 100 * (totalHeadsD / tossTimesD);
    	double tailsPercent = 100 * (totalTailsD / tossTimesD);
    	System.out.printf("Percentage of heads: %.0f \n", headsPercent);
    	System.out.printf("Percentage of tails: %.0f \n", tailsPercent);
    }
}
