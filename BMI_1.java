/**
	Michael Finnegan Tuesday 26 January 2021

 	This program asks the user for his weight in kilograms and his height in metres.
	It then calculates his Body Mass Index based on the following formula: BMI = weight / (height * height)
	The user's BMI value is displayed together with an appropriate message frm this table:

	BMI Weight Status
	Below 18.5		Underweight
	18.5 – 24.99	Normal
	25.0 – 29.99	Overweight
	30.0 and Above	Obese  
 */
import java.util.Scanner;

public class BMI_1 {
        
   
    public BMI_1() {
    }
    
   
     
    public static void main(String[] args) {
    	
    	double weight = 0.0;
    	double height = 0.0;
    	double bmi = 0.0;
    	String message = "";
    	
        System.out.println("Please enter your weight in Kilograms: ");
       	Scanner input = new Scanner(System.in);
        weight = input.nextDouble();
        
        //System.out.printf("%f", weight);
        if(weight < 0.0){
        	System.out.println("Invalid weight. Quitting program now...");
        	System.exit(0);
        }
        System.out.println("Please enter your height in metres: ");
        height = input.nextDouble();
        if(height < 0){
        	System.out.println("Invalid height. Quitting program now...");
        	System.exit(0);
        }
        bmi = weight / (height * height);
    	message = (bmi < 18.5)? "underweight":((bmi >= 18.5 && bmi <= 24.99)? "normal weight":((bmi >=24.99 && bmi <= 29.99)? "over weight": "obese"));
    	System.out.printf("Your BMI is %.2f so you are %s \n", bmi, message);

        
    }
}
