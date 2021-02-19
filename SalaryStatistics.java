/**
 * @(#)SalaryStatistics.java
 *
 * SalaryStatistics application
 *
 * @author Michael Finnegan
 * @version 1.00 2021/2/19
 *
 * This is my solution from working on exercise PROG61002, Structured Programming 2.
 *
 * It demonstrates how to display losts of salary data in a textArea inside a Panel inside a JFrame.
 * It also demonstrates how to analyse and display statistical information from that data.
 * 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SalaryStatistics extends JPanel implements ActionListener {
    // Name-constants to define the various dimensions
    public static int WINDOW_WIDTH = 300;
    public static int WINDOW_HEIGHT = 150;
    // ......

    // private variables of UI components
    JFrame frame; //The parent frame containing the panel. 
    JOptionPane optionPane;
    JButton salariesBtn, statsBtn;
    int[] salaries = new int[100];
    JTextArea textArea;
    Font textAreaFont;

    //-------------------------------------------------------------------
    // Constructor
    SalaryStatistics() {
        for (int i = 0; i < 100; i++) {
            salaries[i] = 0;
        }

        //Instantiate some objects.
        textArea = new JTextArea();
        textAreaFont = new Font("Monospaced", Font.BOLD, 20);
        textArea.setFont(textAreaFont);
        //the parent frame has to be instantiated next outside the constructor and in method setupPanel()
        // in order for the panel and its components to appear on the frame.


    }
    //-------------------------------------------------------------------
    private void setupRandomSalaries() {
        for (int i = 0; i < 100; i++) {
            int aSalary = (int)(Math.random() * (60000 - 20000) + 1) + 20000;
            salaries[i] = aSalary;
        }

    }
    //-------------------------------------------------------------------
    // Get a row of 10 salaries and format them into a string.
    //row is between 0 and 9
    private String getSalaryRow(int row) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < 10; i++) {
            sb.append(String.format("%-10s", salaries[row * 10 + i]));
        }
        return sb.toString() + "\n";
    }
    //-------------------------------------------------------------------
    // Use the Selection Sort algorithm.
    private void doSort() {

        int i, j, index, smallestValue, len = salaries.length;

        for (i = 0; i < len - 1; i++) {

            index = i;
            for (j = i + 1; j < len; j++) {
                if (salaries[j] < salaries[index]) {
                    index = j; // find the index of the lowest value
                }
            }
            smallestValue = salaries[index];
            salaries[index] = salaries[i];
            salaries[i] = smallestValue;

        }

    }
    //-------------------------------------------------------------------
    //Find the largest salary, the smallest salary, the average salary,
    // the Median salary and the percentage of salaries in the range 35000 to 45000
    private String getStats() {
        StringBuffer sb = new StringBuffer("");

        double total = 0.0, average = 0.0, percentInRange = 0.0;
        int len = salaries.length;
        int largest = salaries[len - 1], smallest = salaries[0], inRange = 0;
        int median = salaries[len / 2];

        for (int i = 0; i < salaries.length; i++) {

            if ((salaries[i] >= 35000) && (salaries[i] <= 45000)) {
                inRange++;
            }
            total += ((double) salaries[i]);

        }
        average = total / ((double) len);
        percentInRange = (((double) inRange) / ((double) len));

        sb.append(String.format("Largest salary in the array: €%,d\n", largest));
        sb.append(String.format("Smallest salary in the array: €%,d\n", smallest));
        sb.append(String.format("Average salary: €%,.0f\n", average));
        sb.append(String.format("Median salary: €%,d\n", median));
        sb.append(String.format("%% Salaries in the range €35000-45000: %.0f%%", percentInRange * 100.0));

        return sb.toString();
    }
    //-------------------------------------------------------------------

    public void viewSalaries() {

        setupRandomSalaries();
        textArea.setText("");

        for (int r = 0; r < 10; r++) {
            textArea.append(getSalaryRow(r));
        }

        optionPane.showMessageDialog(null, textArea, "Salary Data", JOptionPane.PLAIN_MESSAGE);

    }
    //-------------------------------------------------------------------
    public void viewStats() {
        String msg = "The array has not yet been populated with random salary values!";
        JLabel label = new JLabel(msg);
        label.setFont(new Font("Serif", Font.PLAIN, 24));
        doSort();
        textArea.setText("");
        for (int r = 0; r < 10; r++) {
            textArea.append(getSalaryRow(r));
        }

        if (salaries[0] == 0) {
            optionPane.showMessageDialog(null, label, "No Salary Data", JOptionPane.ERROR_MESSAGE);
        } else {
            textArea.append("\n\n" + getStats());
            //System.out.println(getStats());
            optionPane.showMessageDialog(null, textArea, "Salary Stats", JOptionPane.INFORMATION_MESSAGE);
        }

    }
    //-------------------------------------------------------------------
    private void setupPanel() {

        frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setTitle("Salaries");
        this.setBackground(new Color(200, 220, 220));
        this.setLayout(new FlowLayout());

        salariesBtn = new JButton("View Salaries");
        salariesBtn.setFont(new Font("Arial", Font.PLAIN, 20));
        statsBtn = new JButton("Salary Stats");
        statsBtn.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(salariesBtn);
        this.add(statsBtn);
        salariesBtn.addActionListener(this);
        statsBtn.addActionListener(this);



    }
    //-------------------------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == salariesBtn) {
            // 1. Fill salaries array with randomly generated whole number salary values 
            //    between 20,000 and 60,000 inclusive.
            // 2. Display a Dialog box showing the unsorted data.
            viewSalaries();

        } else if (e.getSource() == statsBtn) {
            viewStats();
        }

    }

    //-------------------------------------------------------------------

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SalaryStatistics ss = new SalaryStatistics();
                JFrame frame = new JFrame();
                frame.setContentPane(ss); //add panel to frame
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                //Set the size of the frame and center the frame on the screen
                GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
                int width = (int)((1 - 0.25) * env.getDefaultScreenDevice().getDisplayMode().getWidth());
                int height = (int)((1 - 0.25) * env.getDefaultScreenDevice().getDisplayMode().getHeight());
                frame.setSize(width, height); // or pack()            
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);

                ss.setupPanel();
            }
        });
               
    }//End main

}