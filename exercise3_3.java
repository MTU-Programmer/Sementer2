/*----------------------------------------------
 * Michael Finnegan, Tuesday 2 February 2021
 * 
 * This is a solution to exercise 2, Lab 3
 * on pages 17 and 18 of the pdf.
 */

import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextArea;

class StudentGrades {
  String _name;
  String _tnumber;
  String _courseName;
  float _avgMark;
  public static int count = 0; // record total # of StudentGrades objects created
  
  //Constructor
  public StudentGrades() {
    count += 1;
  }
  
  public String getName() {
    return _name;
  }

  public void setName(String newName) {
    this._name = newName;
  }

  public String getTNumber() {
    return _tnumber;
  }
  public void setTNumber(String value) {
    _tnumber = value;
  }
  public String getCourseName() {
    return _courseName;
  }
  public void setCourseName(String value) {
    _courseName = value;
  }
  public float getAvgMark() {
    return _avgMark;
  }
  public void setAvgMark(float value) {
    _avgMark = value;
  }
  public String toString() {
    //Name, T-Number, Course, Average mark
    return String.format("%-25s%-16s%-40s%-10.2f\n", _name, _tnumber, _courseName, _avgMark);
  }
}
public class exercise3_3 {

  public static void main(String[] args) {

    JTextArea textArea = new JTextArea(20, 90);
    Font textAreaFont = new Font("Monospaced", Font.BOLD, 14);
    textArea.setFont(textAreaFont);
    //textArea.setText("Hello");
    /*
		 * Student information required:
		 * 1 Name
		 * 2 T-Number
		 * 3 Name of Course
		 * 4 Average mark
		 */
    StudentGrades[] grades = new StudentGrades[5];
    for (int i = 0; i < 5; i += 1) {
      grades[i] = new StudentGrades();
      grades[i].setName(JOptionPane.showInputDialog("Please enter the name of student " + (i + 1) + ": "));
      grades[i].setTNumber(JOptionPane.showInputDialog("Please enter the T-Numner of student " + (i + 1) + ": "));
      grades[i].setCourseName(JOptionPane.showInputDialog("Please enter the course of student" + (i + 1) + ": "));
      grades[i].setAvgMark(Float.parseFloat(JOptionPane.showInputDialog("Please enter the average mark of student" + (i + 1) + ": ")));

    }
    textArea.setText(String.format("%-25s%-16s%-40s%-10s\n", "Name", "T-Number", "Course", "Avg Mark"));
    textArea.append(String.format("%-25s%-16s%-40s%-10s\n", "====", "========", "======", "========"));

    for (int i = 0; i < StudentGrades.count; i += 1) {

      textArea.append(grades[i].toString());
    }

    JOptionPane.showMessageDialog(null, textArea, "Formatted Data", JOptionPane.PLAIN_MESSAGE);

    System.exit(0);
  }

}