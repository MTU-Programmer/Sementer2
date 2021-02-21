/**
 * @(#)CalendarApp.java
 *
 * CalendarApp application
 *
 * @author Michael Finnegan
 * @version 1.00 2021/2/21
 */
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
  

/*----------------------------------------------------------------
 * Calendar is not the main class. 
 * Calendar is a non-GUI class. It is only concerned with
 * calculating a new calendar and storing the results (all the rows)
 * in a string which can be plugged into a textArea by the main class.
 * The main class is CalendarApp, which is below this class.
*/
class Calendar
{
        private String[] sMonthArr = new String[] 
        {
            "January","February","March","April","May","June",
            "July","August","September","October","November","December"
        };
        
        private String[] sDayArr = new String[] { "Mon","Tue","Wed","Thu","Fri","Sat","Sun" };
        public int ErrNum;      			 //In constructor initialize to 0.
        public String[] arrErr;
        private int[] bigDaysArr;	        //total # of days in all the previous months of all previous years.
        private int year;
     	private int[] daysInPrevMonths;	    //total # of days in all the previous months of chosen year.
        private int[] chosenYearMonths;	    //The # of days in each month of the chosen year
        public int[][][] matrix;				//Array of months 0 .. 11 then within each month array matrix of 7 cols by 6 rows.
                                            //to access a cols use $col = $this->matrix[0][0][0]
        public java.util.List<String> notepadTable;       //table made up in the form of an array of strings.


		public int getYear() {
			return year;
		}
    	//----------------------------------------
    	public boolean tryParseInt(String value) {  
		     try {  
		         Integer.parseInt(value);  
		         return true;  
		      } catch (NumberFormatException e) {  
		         return false;  
		      }  
		}   
        /*-------------------------------------------
	        Get current year from the computer's system clock.   
        */
         public static int getCurrentYear() {
         	LocalDateTime now = LocalDateTime.now();
         	DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy"); 
         	return Integer.parseInt(now.format(format)); 	
         }
        

    /*-------------------------------------------
        Constructor
        Receive the year as a string. Check if it is valid. If not default 
        to accepting the computer clock's year.
        
    */
        public Calendar(String sYear)
        {
            
            if (tryParseInt(sYear)) {  
   				this.year = Integer.parseInt(sYear);  // We now know that it's safe to parse
			}else
        	{
                //No valid year was chosen, therefore choose the current year
                this.year = getCurrentYear();
            }
            
            if (this.year < 1583 || this.year > 2099)
            {
                this.ErrNum = 1;
                this.arrErr = new String[] { "Year out of range (1583 - 2099)" };

            }
            else
            {
                //There was a valid year chosen and it is in the range 1583..2099.
                SetupDaysInPrevMonths();
		        SetupBigDaysArr();
		        CalendarToMatrix();
		        CalendarToNotepadTable();		//There are 38 rows in the table including opening & closing table tags.

            }
            
        } //End constructor
        
               
        
      	/*-------------------------------------------
	        This Easter date calculation function came from 
	        https://www.drupal.org/node/1180480
        */
        public Map<String, Integer> EasterSunday(int year) 
        {
		    
            int a = year % 19;
            int b = year / 100;
	        int c = year % 100;
	        int d = b /4;
	        int e = b % 4;
	        int f = (b + 8)/25;
	        int g = ((b - f + 1)/3);
	        int h = (19 * a + b - d - g + 15) % 30;
	        int i = c / 4;
	        int k = c % 4;
	        int l = (32 + 2 * e + 2 * i - h - k) % 7;
	        int m = ((a + 11 * h + 22 * l ) / 451);
	        int easterMonth = ((h + l - 7 * m + 114) / 31);//  [3=March, 4=April]
	        int p = (h + l - 7* m + 114) % 31;
	        int easterDate = p + 1; //     (date in Easter Month)
			
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("year", year);
			map.put("month", easterMonth);
			map.put("date", easterDate);
            return map;
        }

        /*----------------------------------------------------
         * Centre text within a given field with, returning a string of field width.
         */
        private String CenterText(String text, int fieldWidth)
        {
            if(text == null || text == "" || fieldWidth < 2)
            {
                return "Error in string or fieldWidth";
            }
            
            int lSpaces;
            String spaces = "                                                                      ";
            String output = "";
            if(text.length() >= fieldWidth)
            {
                
                output = text.substring(0, fieldWidth);
            }
            else
            {
                lSpaces = (fieldWidth - text.length()) /2;
                output = spaces.substring(0,lSpaces) + text + spaces.substring(0,lSpaces);
                if(output.length() < fieldWidth)
                {
                    output = " " + output;
                }
            }
            return output;
        }

        /*----------------------------------------------------
         * Return 0 if not a leap year, else return 1.
        */
        private int IsLeapYear(int y)
        {
            
            return (y % 400 == 0)? 1:((y % 100 == 0)? 0: (y % 4 == 0)? 1: 0);
        }
     	
        /*---------------------------------------------------
	        Return the total number of days in all the previous years,
	        including leap years. 
        */
        private int DaysInPreviousYears()
        {

	        int pY = this.year -1;
	        int leapDays = - (pY / 100) + (pY / 400) + (pY / 4);
	        int prevDays = pY * 365 + leapDays;		
	        return prevDays;
        }

        /*---------------------------------------------------
           Set up an array containing the number of days in each month of the chosen year.
       */
        private void SetupChosenYearMonths()
        {
            //									0	1	2	3	4	5	6	7	8	9	10	11
	        //								    Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec
	        this.chosenYearMonths = new int[]{  31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	        this.chosenYearMonths[1] = 28 + IsLeapYear(this.year);

        }
               
     /*----------------------------------------------------
        Setup the accumulated days in all of the months prior to a given month for the chosen year.
    */
    private void SetupDaysInPrevMonths()
    {
        if(this.ErrNum > 0)
        {
		    return ;
	    }
	    SetupChosenYearMonths();
        this.daysInPrevMonths = new int[12];
	    int acc = 0;
	    for(int i = 0; i < 12; i += 1) 
        {
		    this.daysInPrevMonths[i] = acc;
		    acc += this.chosenYearMonths[i];
	    }

    }
    /*-------------------------------------------
	    Setup the accumulated days in all of the months prior to a given month 
	    plus all the years previous to the chosen year.
	    function SetupDaysInPrevMonths() must have already been called before calling this function.
    */
    private int SetupBigDaysArr()
    {		
		
	    if(this.ErrNum > 0){
		    return this.ErrNum;
	    }
        this.bigDaysArr = new int[12];
	    int prevDays = DaysInPreviousYears();
	    for(int i = 0; i < 12; i += 1) {
		    this.bigDaysArr[i] = prevDays + this.daysInPrevMonths[i] ;
		
	    }
        return 0;
    }

    /*-------------------------------------------
	    Receive a day number 1..31 and a month number 1..12
	    then return the column 0..6 that this day is on.
	    function this.SetupBigDaysArr() must have already been called before calling this function.
	    This is done by the constructor.
    */
    private int GetDayCol(int d, int m)
    {
	
	    int x = (this.bigDaysArr[m -1] + d -1);
	    int col = x % 7;
	
	    return col;
    }

    /*-------------------------------------------
	    Receive a day number 1..31 and a month number 1..12
	    then return the row 0..5 that this day is on.
	    function setupBigDaysArr() must have already been called before calling this function.
	    This is done by the constructor.

    */
    private int GetDayRow(int d, int m)
    {
	
	    int row = ( (GetDayCol(1, m) + (d -1)) / 7);
	
	    return row;
    }
    /*-------------------------------------------
	    Receive a day number 1..31 and a month number 1..12.
	    Return an associative array containing the col (0..6) and
	    the row (0..5) that $d, $m is on.
	    The days of the week are arranged as
	    0   1   2   3   4   5   6
	    Mon Tue Wed Thu Fri Sat Sun
    */
    private HashMap<String, Integer> GetDayColRow(int d, int m)
    {
	
	    HashMap<String, Integer> colRow = new HashMap<String, Integer>();
        colRow.put("col", this.GetDayCol(d, m));
        colRow.put("row", this.GetDayRow(d, m));

	    return colRow;
    }

    /*-------------------------------------------
	    Initialize the matrix for all of the 12 months. 
	    1. Setting the default values to 0.
	    2. put the date values in the matrix into their appropriate rows and columns.
    */
    private void InitMatrix(){
	
	    this.matrix = new int[12][6][7];
	    //The above line automtically initialises the array.
	    //This code shows how to initialise the arrays manually
        //for(int m = 0; m < 12; m +=1) {
		//	    for(int r = 0; r < 6; r += 1) {
		//	    for(int c = 0; c < 7; c += 1) {
		//		    this.matrix[m][r][c] = 0;
		//	    }
		//    }
	    //}

    }

    /*-------------------------------------------
	    Store a month in the matrix.
	    $mm = 1..12
	    The matrix must have already been setup with 0 values.
    */
    private void MonthToMatrix(int mm)
    {
	
	    int m = mm -1;
	    int lastDay = this.chosenYearMonths[m];			//$lastDay = total # of days in the month
	    for(int d = 1; d <= lastDay; d += 1){
		    HashMap<String, Integer> colRow = this.GetDayColRow(d, mm);
		    int c = colRow.get("col");
		    int r = colRow.get("row");
		    this.matrix[m][r][c] = d;
	    }	
    }

    /*-------------------------------------------
	    Store all the dates for each of the 12 months in the matrix.
    */
    private void CalendarToMatrix()
    {

	    this.InitMatrix();
	    for(int m = 1; m <= 12; m +=1 ){
		    MonthToMatrix(m);
	    }
    }


    /*-------------------------------------------
	    Receive a month (0..11) and a row (0..5) represents a row of dates to fetch for that month.
	    Return a string with the values formatted within a field width of 5.
    */
    private String MonthRowDatesToString(int m, int r)
    {
	
	    StringBuilder sb = new StringBuilder();
	    
	    for(int c = 0; c < 7; c += 1)
        {
		    int d = this.matrix[m][r][c];
		    if(d > 0)
            {
			    sb.append(String.format("%5d", d)); // right justify
		    }else
            {
			    sb.append(String.format("%5s", " "));
		    }
	    }

	    return sb.toString();
    }

    /*-------------------------------------------
	    Put the days of the week into a string, each day occupies a field width of 5.
    */
    private String DaysOfTheWeekToString()
    {
	    StringBuilder sb = new StringBuilder();
	    for(int i = 0; i < 7; i += 1)
        {
		    sb.append(String.format("%5s", this.sDayArr[i]));
	    }

	    return sb.toString();
    }

    /*------------------------------------------
	    Based on the value of $row create and return an appropriate string
	    containg a calendar row .
    */
    private String CreateNotepadCalendarRow(int row)
    {
	
	    String s = "";
	    int r = row % 9;
	    int lm = 3 * (row / 9);
	    String leftMonth = this.sMonthArr[lm] + " " + this.year;
	    String middleMonth = this.sMonthArr[lm + 1] + " " + this.year;
	    String rightMonth = this.sMonthArr[lm + 2] + " " + this.year;
	    switch(r)
        {

		    case 0: // Month name on each side
			    s = CenterText(leftMonth, 35) + "  " + CenterText(middleMonth, 35) + "  " + CenterText(rightMonth, 35);
			    s += "\n";
                break;

		    case 1:	// Days of the week on each side: left, middle, right.
			    s = DaysOfTheWeekToString() + "  " + DaysOfTheWeekToString() + "  " + DaysOfTheWeekToString();				
			    s += "\n";
                break;
		
		    case 8: // A clear line below the month dates.
			   s = "\n";
			    break;

		    default: //Assume its one of the six date rows they occur at row positions 2..7 in the calendar
			    int datesRow = r -2;
			    s = MonthRowDatesToString(lm, datesRow) + "  ";
                s += MonthRowDatesToString(lm + 1, datesRow) + "  ";
                s += MonthRowDatesToString(lm + 2, datesRow) + "\n";
			    break;
	    }

	    return s;
    }

    /*------------------------------------------
        A calendar of 12 months date values has already been setup in the 3 dimensional matrix array.
        Create a List<string> object calendar based on the data in matrix array and store the calendar
        as 36 rows of strings.

        Months are displayed in triples i.e., January & February, March, then April, May, June below them.
        Each month has a pattern of 9 rows. The pattern repeats in the months below.
        The 9 rows are:
        0		The month title
        1		The days of the week
        2..7	Date rows 0..5
        8		A blank row before the next pair of two months below.
        Therefore there are 4 * 9 = 36 rows.

    */
     private java.util.List<String> CalendarToNotepadTable()
     {
         this.notepadTable = new ArrayList<String>();
         String s = "";
         for (int i = 0; i < 36; i += 1 )
         {
             s = CreateNotepadCalendarRow(i);
             this.notepadTable.add(s);
         }


         // Add on an extra line displaying the date of Easter.
         Map<String, Integer> easterInfo = EasterSunday(this.year);
         s = "  Easter Sunday falls on " + easterInfo.get("date"); 
         String month = (easterInfo.get("month") == 3)? " March": " April";
         s += month + " " + easterInfo.get("year") + "\n";
         this.notepadTable.add(s);
         
         return this.notepadTable;

     }
     //-----------------------------------------
     // convert List<string> notePadTable into a big long string for the real notepad control.
     //-----------------------------------------
     public String GetNotepadText()
     {
         StringBuilder sb = new StringBuilder("\n");
        
         for(String s : this.notepadTable)
         {
             sb.append(s);
         }
         return sb.toString();
     }

}//End class Calendar	
 	
//--------------------------------------------------------------------------------
// This is the main class.

public class CalendarApp extends JFrame implements ActionListener {
    
    // Name-constants to define the various dimensions
    public static int WINDOW_WIDTH = 300;
    public static int WINDOW_HEIGHT = 150;
    // ......

    // private variables of UI components
    JPanel panel;
    JOptionPane optionPane;
    JButton btn1, btn2;
    JScrollPane scrollPane;
    JTextArea textArea;
    Font textAreaFont;
    JTextField textField1;
    Calendar calendar;

    //-------------------------------------------------------------------
    // Constructor
    CalendarApp(){

        //Instantiate some objects.
        textArea = new JTextArea();
        textAreaFont = new Font("Monospaced", Font.BOLD, 20);
        textArea.setFont(textAreaFont);
        //the parent frame has to be instantiated next outside the constructor and in method setupPanel()
        // in order for the panel and its components to appear on the frame.

    	
    }
    //------------------------------------------------------------------
   private void setupPanel() {
    	
		//We are inside a JFrame.
        //We would use this from inside a panel to access the parent frame: frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        
        this.setTitle("Calendar"); //set the frame's title.
        panel = new JPanel();
        panel.setBackground(new Color(200, 220, 220));
        panel.setLayout(new FlowLayout());
        textArea = new JTextArea(26, 100);
        textArea.setFont(new Font("Lucida Console", Font.BOLD, 18));
        textArea.setText("");
        
        scrollPane= new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);
        panel.add(scrollPane);
        panel.setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));
        
        JPanel btnPanel = new JPanel();
       	btnPanel.setLayout(new FlowLayout());
        
        textField1 = new JTextField(Calendar.getCurrentYear() + "",10);
        textField1.setFont(new Font("Lucida Console", Font.BOLD, 20));
		JLabel label = new JLabel("Enter year: ");
		label.setFont(new Font("Lucida Console", Font.BOLD, 20));
		
		btnPanel.add(label);
		btnPanel.add(textField1);
		
		
        btn1 = new JButton("Calculate");
        btn1.setFont(new Font("Arial", Font.PLAIN, 20));
        btn2 = new JButton("Print");
        btn2.setFont(new Font("Arial", Font.PLAIN, 20));
        btnPanel.add(btn1);
        btnPanel.add(btn2);
        panel.add(btnPanel);
        
        
        
        btn1.addActionListener(this);
        btn2.addActionListener(this);
		this.setContentPane(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		 //Set the size of the frame and center the frame on the screen
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int width = (int)((1 - 0.25) * env.getDefaultScreenDevice().getDisplayMode().getWidth());
        int height = (int)((1 - 0.25) * env.getDefaultScreenDevice().getDisplayMode().getHeight());
        this.setSize(width, height); // or pack()            
        this.setVisible(true);
        this.setLocationRelativeTo(null);


    }
    //-------------------------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btn1) {
          
            // call a method
            String yearS = textField1.getText();
            calendar = new Calendar(yearS);
            textArea.setText(calendar.GetNotepadText());
            this.setTitle("Calendar " + calendar.getYear());

        } else if (e.getSource() == btn2) {
            // call another method
            JLabel label = new JLabel("This feature is not developed in this version.");         
			label.setFont(new Font("Arial", Font.BOLD, 20));
            JOptionPane.showMessageDialog(null, label, "Information", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    
    //----------------------------------------------------------------------------
    public static void main(String[] args) {
    	   
    	//Calendar cal = new Calendar( "2021");
    	//String text = cal.GetNotepadText();
    	//System.out.println(text);
    	SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                //runs code in constructor then sets up the panel               
                new CalendarApp().setupPanel();
            }
        });

    
    }
    
} // end class Main