class MyDouble {
  public Double d;
  //Constructor
  MyDouble(double value) {
    this.d = value;
  }
  //Constructor
  MyDouble() {
    this.d = 0.0;
  }

  public static boolean tryParseDouble(String s, MyDouble value) {

    try {
      value.d = Double.parseDouble(s);
      return true;

    } catch(NumberFormatException e) {
      return false;
    }
  }

}
//-----------------------------------------------------
public class parseTest {

  /*--------------------------------------------------
    * C# has a TryParse method associated with the double number type.
    * By Java does not. Therefore in Java if you are trying to convert a number in a string into
    *a double you need to use a try catch. Alternatively, you could create your own
    *custom Double type and use it like this:
    *
    *	if(MyDouble.tryParseDouble(numS, money)){
        	message = String.format("Your pay cheque this week is: %,.2f", money.d);
        }else{
        	message = "Sorry, No pay cheque this week for you:\nthat string could not be parsed into a Double.";
        }
    *
   */

  //-----------------------------------------------
  public static void main(String[] args) {

    String numS = "747.01",
    message;
    MyDouble money = new MyDouble();

    if (MyDouble.tryParseDouble(numS, money)) {
      message = String.format("Your pay cheque this week is: %,.2f", money.d);
    } else {
      message = "Sorry, No pay cheque this week for you:\nthat string could not be parsed into a Double.";
    }
    System.out.println(message);

  }
}