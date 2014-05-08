/**
<p>Title: Online Purchasing </p>
<b>Filename:</b> UserApp.java<br>
<b>Date Written:</b>April 14 2014 <br>
<b>Description</b>The User application class to register, forgot password, or login<br>
@author David Chan
 */
package softwareEngineer;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class UserApp {

	static Scanner scan = new Scanner(System.in);
	static User customer = null;

	//------------------------------------------------------- 
	// Create a list, then repeatedly print the menu and do what the 
	// user asks until they quit 
	//------------------------------------------------------- 
	public static void main(String[] args) { 
		//mainMenu system
		mainMenu();
		System.out.println("\t\t GoodBye!\n   Thank you for trying out our program. \n \t\t by Team #4");
		System.exit(0);
	}//ends main

	//-------------------------------------- 
	// Do what the menu item calls for 
	//-------------------------------------- 
	@SuppressWarnings("static-access")
	public static void dispatch(int choice) { 
		switch(choice) { 
		case 1:  
			System.out.println( "\nFollow the Prompts to register your account.");
			UserInput();
			dispatch(2);

			try {
				TimeUnit.MILLISECONDS.sleep(2650);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break; 

		case 2: 
			loginInput();
			if (customer != null){
				System.out.println(" Login Success!");
				dispatch(4); //goto to case 4 
			} //holds the userid of the current login
			else
				{System.out.println("Login was unsuccessfully Please try again");
				mainMenu();}
			break;

		case 3: 
			System.out.print( "\nFollow the Prompts user to retrieve password.. \n ");
			System.out.print( "\nPlease enter your userid : \n ");

			break;

		case 4: //account management menu switch statement
			int x = 0;
			while (x != 2) { 
				accountMgmtMenu();
				x = scan.nextInt(); 
				if (x != 2){
					subAccountMgmtMenu();
					accountSetting();
				}
			}//ends while 
			if (AccountList.logoutUser(customer.getId()) == true )
				{
				mainMenu();
				}
			System.out.println("Just a moment...");
			break;
		case 0: 
			scan.close();
			break; 
		default: 
			System.out.println("Sorry, invalid choice"); 
		} 
	} //ends dispatcher

	//----------------------------
	//Loops into the main menu system
	//----------------------------
	public static void mainMenu(){
		printMenu(); 
		int choice = scan.nextInt(); 
		while (choice != 0) { 
			dispatch(choice); 
			printMenu(); 
			choice = scan.nextInt(); 
		}//ends while 
	}

	//---------------------------- 
	// Print the user's choices 
	//---------------------------- 
	public static void printMenu() { 
		System.out.println("\t\t The User Login System "); 
		System.out.println("\t\t ======================"); 

		System.out.println("The Menu Options:"); 
		System.out.println("1: Register an Account");
		System.out.println("2: Login to your Account");
		System.out.println("3: Reset Password");
		System.out.println("0: Quit/Exit ");

		System.out.println("Please enter your selection > "); 
	} //ends printMenu  
	@SuppressWarnings("static-access")
	public static User UserInput(){ // user input

		Scanner scan = new Scanner( System.in );
		Scanner namescan = new Scanner(System.in);//used for name due to blank spaces
		Scanner in = new Scanner(System.in); // used for address scan due to blank spaces 
		Scanner scan2 = new Scanner(System.in); // used for payment scan due to blank spaces 
		System.out.println("What is the user Id?(spaces are not allowed)");
		String userid = scan.next();

		System.out.println("Enter a new password to the new account: ");
		String password = scan.next();

		System.out.println("What is the name?");
		String name = namescan.nextLine();

		System.out.println("What is the address?");
		String address =in.nextLine();

		System.out.println("What is the Phone Number?(only numbers please)");
		String phoneNumber = scan.next();
		if( (phoneNumber.length()) > 10 || !(phoneNumber.matches("[0-9]+"))  )
		{
			System.out.println("Your Phone number is too long or incorrect Try again");
			dispatch(1);
		}
		else{

			System.out.println("Enter your Payment Info?");
			String paymentinfo = scan2.nextLine();

			User b = new User ( userid, password,name,address,phoneNumber,paymentinfo); 

			if (AccountList.createUserAccount(b) == true){
				System.out.println( "Congratulation "+name +" your account has been created.");
				System.out.println( "-------------------------------------------------------------------");}
			else
				System.out.println("you account was not created, please check your input again");

			//scan.close();
			return b;
		}
		return null;
	}//ends userinput method
	/** 
	 *Account management menu system to print the menu
	 */
	public static void accountMgmtMenu() { 
		System.out.println("\t\t The Account Management System "); 
		System.out.println("\t\t =============================="); 

		System.out.println("The Menu Options:"); 
		System.out.println("1: Manage Account settings");
		System.out.println("2: Logout");

		System.out.println("Please enter your selection > "); 
	} //ends printMenu 
	/** 
	 * submenu of account management of the menu
	 */
	public static void subAccountMgmtMenu(){ 
		System.out.println("Welcome to Account Settings:"); 
		System.out.println("1: View your userId");
		System.out.println("2: Update your Account");
		System.out.println("3: Delete user Account");
		System.out.println("4: Reset your password");

		System.out.println("5: Go back");
		System.out.println("Please enter your selection > ");  
	}//ends subAccountmgmtmenu
	/** 
	 * submenu of account management switch statement
	 */
	public static void accountSetting(){
		int choice = scan.nextInt(); 
		while (choice != 5) { 

			switch(choice){
			case 1:
						System.out.println( "Your UserId: " +customer.ToString() +"\n");
						System.out.println("payment info " + customer.getPayment());
				break;
			case 2:
				System.out.println("update your account");
				break;
			case 3:
				System.out.println("delete user account");
				break;
			case 4: 
				System.out.println("reset your password");
				break;
			case 5:
				accountMgmtMenu();
				break;
			default: 
				System.out.println("Sorry, invalid choice"); 

			}//ends switch
			subAccountMgmtMenu();
			choice = scan.nextInt(); 
		}//ends while
	}//ends account settings
	/** 
	 * Case 2 login input
	 * @return return the object of the user to maintain through the program
	 */

	public static User loginInput(){
		
		System.out.print( "\nFollow the Prompts to Log-In to your Account  \n ");
		System.out.print( "\nPlease enter your userid : \n ");
		String userid = scan.next();
		System.out.println(AccountList.searchUser(userid).getId());

		System.out.print( "\nPlease enter your password: \n ");
		String pass = scan.next();
		
		customer = AccountList.loginUser(userid, pass);

		if (customer != null)
			{	 
				return customer;
			}
		return null;
	}//ends loginInput
}//ends class
