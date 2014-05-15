package softwareEngineer;

import java.util.Scanner;


public class UserApp {
	static User currentCustomer = null;
	//------------------------------------------------------- 
	// Create a list, then repeatedly print the menu and do what the 
	// user asks until they quit 
	//------------------------------------------------------- 
	public static void main(String[] args) {

		try(Scanner scan = new Scanner(System.in);){//scanner to avoid resource leak
			printMenu(); //print menu system from another function
			String choice = scan.nextLine().trim(); //reads an input
			final String EXIT_now = "0";
			//		final String BACK = "back";
			while (!(choice.equalsIgnoreCase(EXIT_now)) && !(choice.equalsIgnoreCase("quit"))){
				switch(choice) {
				case "1":  
					System.out.println( "\nFollow the Prompts to register your account.");
					System.out.println("What is the user Id?(spaces are not allowed)");
					String userid = scan.nextLine();
					if( userid.contains(" "))
					{
						System.out.println("Your userid has a space please try again");
						choice = "jfhfj"; //return back to the begining of the choice
					}
					else{
						System.out.println("Enter a new password to the new account: ");
						String password = scan.nextLine();

						System.out.println("What is the name?");
						String name = scan.nextLine();

						System.out.println("What is the address?");
						String address =scan.nextLine();

						System.out.println("What is the Phone Number?(only numbers please)");
						String phoneNumber = scan.nextLine();
						if( (phoneNumber.length()) > 10 || !(phoneNumber.matches("[0-9]+"))  )
						{
							System.out.println("Your Phone number is too long or incorrect Try again");
							choice = "jfhfj";
						}
						else{
							System.out.println("Enter your Payment Info?");
							String paymentinfo = scan.nextLine();
							User b = new User ( userid, password,name,address,phoneNumber,paymentinfo); 
							if (AccountList.createUserAccount(b) == true){
								System.out.println( "Congratulation "+name +" your account has been created.");
								System.out.println( "-------------------------------------------------------------------");
							}//ends if
							else
								System.out.println("you account was not created, please check your input again");
						}//ends else
					}//ends userid-else 
					break;

				case "2": 
					if (loginInput(scan) != null) {//current Customer loggin in. and passing in a scan object because i need maintain the instance of the current scanner

						System.out.println("Your password has been verified. You have successfully login");

						accountMgmtMenu(); //proceed to account management
						String subchoice = scan.nextLine(); 
						while (!subchoice.equals("2")){	
							switch(subchoice) {

							case "1": 
								subAccountMgmtMenu(); //just menu
								accountSetting(scan); //this will lead to input from submenu
								break;

							default:
								System.out.println("Sorry, invalid choice");
								break;
							}//ends switch 
							accountMgmtMenu(); //print menu system from another function
							subchoice = scan.nextLine(); //reads an input
						} //ends while
						AccountList.logoutUser(currentCustomer.getId());
						System.out.println("Just a moment...");

					}//ends if
					else
					{System.out.println("Login was unsuccessfully Please try again");
					choice="adfsd";
					}
					break;

				case "3": 
					System.out.print( "\nFollow the Prompts user to reset your password.. \n ");

					System.out.print( "\nPlease enter your userid to verify your account : \n ");
					String userid1 = scan.nextLine().trim();
					if (AccountList.searchUser(userid1).getId().equals(userid1)){
						System.out.println("User id has been verified");
						String newpassword = "";
						String newpassword2= "";
						boolean passwordchecked = false ; 
						System.out.println("Enter your new password or 'No' not to change your password");
						newpassword =scan.nextLine();

						while ( (!passwordchecked) && (!newpassword.equalsIgnoreCase("no"))) // passwordchecked is true once it is false it leaves the while loop
							// when it pass is not false keep going and when it not "no" keep going
							//once one of them is false it quits out of the while. 
						{
							System.out.println("Please enter your password again ");
							newpassword2 =scan.nextLine();

							if (newpassword.equals(newpassword2) == true)
							{passwordchecked = true; //once its false then the while will stop
							System.out.println("password matched please wait...");
							}
							else
								passwordchecked = false;//else it keeps going never met condition
						}
						if (newpassword.equalsIgnoreCase("no") == false && passwordchecked == true)
						{ 
							//special case for above because there is no object created only during login
							AccountInvoiceJDBC.resetPassword(newpassword, userid1); //there is no object for current customer
							System.out.println("your password has been successfully resetted");
						}
						else
							System.out.println("Your password has not been changed");
					}	
					else
					{System.out.println("Wrong user ID ");
					}
					break;

				default: 
					System.out.println("Sorry, invalid choice"); 
					break;
				} //ends switch  
				printMenu(); //print menu system from another function
				choice = scan.nextLine(); //reads an input
			}//ends while 
		}//close try 
		System.out.println("\t\t GoodBye!\n   Thank you for trying out our program. \n \t\t by Team #4");
		System.exit(0);
	}//ends main
	/**
	 *  Print the user's choices 
	 */

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
	/** 
	 * Case 2 login input
	 * @param in - For instance, by passing an already open Scanner to your core business logic functions, 
	 * you can mock a real user's behavior and create a test to ensure your code remains stable, by constructing a 
	 * Scanner that reads from a hard coded String, and passing that into your method, without 
	 * needing to run the whole class and type in the behavior your testing manually again and again.
	 * @return return the object of the user to maintain through the program
	 */
	public static User loginInput(Scanner in){
		System.out.print( "\nFollow the Prompts to Log-In to your Account  \n ");
		System.out.print( "\nPlease enter your userid : \n ");
		String userid = in.nextLine().trim();
		if (AccountList.searchUser(userid).getId().equals(userid)){
			System.out.println("User id has been verified");
		}	
		else
		{System.out.println("Wrong user Information please try again.");
		return null;}
		System.out.print( "\nPlease enter your password: \n ");
		String pass = in.nextLine().trim();
		currentCustomer = AccountList.loginUser(userid, pass);

		if (currentCustomer != null)
		{	 
			return currentCustomer;
		}
		return null;

	}//ends loginInput
	/** 
	 * After successfully login the case 2 will proceed to this menu
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
		System.out.println("----------------------------");

		System.out.println("1: View your Account");
		System.out.println("2: Update your Account");
		System.out.println("3: Delete user Account");
		System.out.println("4: Reset Password ");
		System.out.println("5: Go back");
		System.out.println("Please enter your selection > ");  
	}//ends subAccountmgmtmenu

	/** 
	 * submenu of account management switch statement
	 */
	public static void accountSetting(Scanner in){
		String choice = in.nextLine().trim(); 
		while (!choice.equalsIgnoreCase("5")) { 

			switch(choice){
			case "1":
				System.out.println( "View your account: " +currentCustomer.toString()+"\n");
				break;
			case "2":
				System.out.println("update your account");
				User newCustomer = editUser(in);
				if(newCustomer==null)
				{
					System.out.println("User was not modified cancelling update");
				}
				else 
					if (AccountList.updateAccountbyUser(newCustomer) != false)

						System.out.println("Proceeding back to SubMenu");
					else
						System.out.println("Your account is not updated");
				break;
			case "3":
				System.out.println("Do you want to delete your user account? Type yes to proceed or press any key to goback");
				String deletex = in.nextLine().trim(); 
				if (deletex.equals("yes") == true)
				{AccountList.deleteUserAccount(currentCustomer.getId());
				System.out.println("Account has been deleted. The program will exit");
				System.exit(0);}
				else
					choice = "dasfsd";
				break;
			case "4":
				passwordReset(in);
				break;
			default: 
				System.out.println("Sorry, invalid choice"); 
				break;
			}//ends switch
			subAccountMgmtMenu();
			choice = in.nextLine().trim(); 
		}//ends while
	}//ends accountsettings method

	public static User editUser(Scanner in){
		@SuppressWarnings("unused")
		boolean changed = false;
		int count = 0; 

		System.out.println("Please enter your NEW name or 'No' not to change your name");
		String name = in.nextLine();
		if (!(name.equalsIgnoreCase("no")))
		{
			currentCustomer.setName(name);
			changed =true;
			count++;
		}
		System.out.println("Please enter your NEW Address or 'No' not to change your address");
		String address = in.nextLine();
		if (!(address.equalsIgnoreCase("no")))
		{
			changed =true;
			currentCustomer.setAddress(address);
			count++;
		}


		System.out.println("Please enter your NEW Phone Number or 'No' not to change your phone number");
		String phoneNumber = in.nextLine();
		if (!(phoneNumber.equalsIgnoreCase("no")))
		{
			changed =true;
			currentCustomer.setPhoneNumber(phoneNumber);
			count++;
		}

		System.out.println("Please enter your NEW payment Info or 'No' not to change your payment Info");
		String paymentinfo = in.nextLine();
		if (!(paymentinfo.equalsIgnoreCase("no")))
		{
			changed =true;
			currentCustomer.setPayment(paymentinfo);
			count++;
		}


		if(count == 0)
			return null;
		else
			System.out.println(count+" attributes has been updated in your account");
		return currentCustomer;
	}

	/* reset giveen password
	 * @param in takes in the scanner instance
	 * @return no return
	 */
	public static void passwordReset(Scanner in){
		String newpassword = "";
		String newpassword2= "";
		boolean passwordchecked = false ; 
		System.out.println("Enter your new password or 'No' not to change your password");
		newpassword =in.nextLine();

		while ( (!passwordchecked) && (!newpassword.equalsIgnoreCase("no"))) // passwordchecked is true once it is false it leaves the while loop
			// when it pass is not false keep going and when it not "no" keep going
			//once one of them is false it quits out of the while. 
		{
			System.out.println("Please enter your password again ");
			newpassword2 =in.nextLine();

			if (newpassword.equals(newpassword2) == true)
			{passwordchecked = true; //once its false then the while will stop
			System.out.println("password matched please wait...");
			}
			else
				passwordchecked = false;//else it keeps going never met condition
		}
		if (newpassword.equalsIgnoreCase("no") == false && passwordchecked == true)
		{ 
			currentCustomer.setPassword(newpassword);
			//special case for above because there is no object created only during login
			AccountInvoiceJDBC.resetPassword(newpassword, currentCustomer.getId()); //there is no object for current customer
			System.out.println("your password has been successfully resetted");
		}
		else
			System.out.println("Your password has not been changed");
	}//ends passwordReset

}//ends class