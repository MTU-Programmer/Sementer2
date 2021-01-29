import java.util.Arrays;
import java.util.Scanner;

/*
		A program that uses a do-while loop to simulate the actions of an ATM machine.
		It begins by presenting the user with a menu of 3 transaction options as indicated in the
		screen capture below:

		****************ITT ATM****************

		Please choose from the following transaction types:

		'D' is deposit
		'W' is withdrawal
		'X' is Exit the system

		Please enter your choice: W

		Please enter the amount you wish to withdraw: 100

		Your balance is now 1300.00
		Please hit return to continue .....

		Thanks for using the system ... goodbye

		The user then enters their choice as an uppercase letter.
		Once a valid choice has been entered, the user can deposit, withdraw or exit from the
		system. They can carry out as many deposits or withdrawals as they like and each one
		updates their balance and then displays the new balance to the screen to 2 decimal places.
		You can assume that the user starts off with an initial balance of €1000 in their account.
		When the user selects the exit option then they receive a “goodbye” message.

 */
public class ATM {

    private double _balance;


    public void showBalanceMessage() {
        System.out.printf("Your balance is now %,.2f \r\n", _balance);
        return;
    }


    // Getter
    public double getBalance() {
        return _balance;
    }
    // Setter
    public void setBalance(double balance) {
        _balance = balance;
    }

    public double withdrawMoney(double money) {
        _balance -= money;
        return _balance;
    }

    public double depositMoney(double money) {
        _balance += money;
        return _balance;
    }
    /*-------------------------------------------------------
     * if k = "D" then input the amount to deposit,
     * deposit the money and show the new balance
     * else assume k = "W" therefore input the amount to withdraw,
     * withdraw the money and show the new balance.
     */
    private void doAction(String k) throws Exception {

        String s;
        double balance = 0, money;

        if (!k.equals("D") && !k.equals("W")) {

            throw new Exception(String.format("Error: %s is not D or W", k));
        }

        Scanner input = new Scanner(System.in);
        System.out.printf("Please enter the amount you wish to %s : ", k.equals("D") ? "deposit" : "withdraw");
        s = input.nextLine();
        System.out.printf("\r\n");
        money = Double.parseDouble(s);
        money = k.equals("D") ? depositMoney(money) : withdrawMoney(money);
        showBalanceMessage();

        return;

    }

    //----------------------------------------------
    private void displayMenu() {
        String menuText = "****************ITT ATM****************\r\n\r\n" +
            "Please choose from the following transaction types:\r\n\r\n" +
            "'D' is deposit\r\n" +
            "'W' is withdrawal\r\n" +
            "'X' is Exit the system\r\n\r\n" +
            "Please enter your choice: ";
        System.out.printf(menuText);
    }
    //----------------------------------------------

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String s = "";
        double money;
        ATM atm = new ATM();
        atm.setBalance(1000);

        outer: do {

            atm.displayMenu();
            s = input.nextLine();
            s = s.toUpperCase();
            try {

                switch (s) {

                    case "W":
                        atm.doAction(s);
                        break;
                    case "D":
                        atm.doAction(s);
                        break;

                    case "X":
                        break outer;
                    default:
                        System.out.println("Wrong key!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.printf("Hit return to continue \r\n");
            s = input.nextLine();
        } while (true);

        System.out.printf("\r\nThanks for using the system ... goodbye \r\n");
    }

}