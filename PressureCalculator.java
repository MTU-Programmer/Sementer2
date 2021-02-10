/**
 * @(#)PressureCalculator.java
 *
 * PressureCalculator application
 *
 * @author Michael Finnegan
 * @version 1.00 2021/2/10
 */
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Font;

public class PressureCalculator {
    
    private static Double pressureCalculator(Double density, Double depth){
    	return density * 9.8 * depth;
    }
    
    public static void main(String[] args) {
    	String densityAsString, depthAsString, pressureAsString;
    	Double depth, density, pressure;
    	
    	densityAsString = JOptionPane.showInputDialog("Please enter the density of the fluid");
    	density = Double.parseDouble(densityAsString);
    	depthAsString = JOptionPane.showInputDialog("Please enter the depth of the object in the fluid");
    	
    	depth = Double.parseDouble(depthAsString);
    	
    	pressure = pressureCalculator(density, depth);
    	pressureAsString = String.format("%,.3f", pressure);
    	JOptionPane.showMessageDialog(null, "The pressure exerted on the body is " + pressureAsString, "Pressure", 
    										JOptionPane.INFORMATION_MESSAGE);
    										
    	
    System.exit(0);
    }
}
