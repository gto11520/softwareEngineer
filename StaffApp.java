package softwareEngineer;
import java.util.Scanner;

public class StaffApp {
	static Staff currentStaff = null;
	//------------------------------------------------------- 
	// Create a list, then repeatedly print the menu and do what the 
	// staff asks until they quit 
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
					System.out.println("What is the Staff Id?(spaces are not allowed)");
					String userid = scan.nextLine();
					if( userid.contains(" "))
					{
						System.out.println("Your userid has a space please try again");
						choice = "jfhfj"; //return back to the beginning of the choice
					}
					else{
						System.out.println("Enter a new password to the new account: ");
						String password = scan.nextLine();

						System.out.println("What is the name?");
						String name = scan.nextLine();

						System.out.println("What is your Job title?");
						String title =scan.nextLine();

						System.out.println("What is the Phone Number?(only numbers please)");
						String phoneNumber = scan.nextLine();
						if( (phoneNumber.length()) > 10 || !(phoneNumber.matches("[0-9]+"))  )
						{
							System.out.println("Your Phone number is too long or incorrect Try again");
							choice = "jfhfj";
						}
						else{

							Staff b = new Staff ( userid, password,name,title,phoneNumber); 
							if (AccountList.createStaffAccount(b) == true){
								System.out.println( "Congratulation "+name +" your account has been created.");
								System.out.println( "-------------------------------------------------------------------");
							}//ends if
							else
								System.out.println("you account was not created, please check your input again");
						}//ends else
					}//ends userid-else 
					break;

				case "2": 
					if (loginInput(scan) != null) {//current Staff loggin in. and passing in a scan object because i need maintain the instance of the current scanner

						System.out.println("You have successfully login");

						accountMgmtMenu(); //proceed to account management
						String subchoice = scan.nextLine(); 
						while (!subchoice.equals("3")){	
							switch(subchoice) {

							case "1": 
								subAccountMgmtMenu(); //just menu
								accountSetting(scan); //this will lead to input from submenu
								break;
							case "2":
							    InvoiceInterface.InvoiceDispaly();
								break;
							default:
								System.out.println("Sorry, invalid choice");
								break;
							}//ends switch 
							accountMgmtMenu(); //print menu system from another function
							subchoice = scan.nextLine(); //reads an input
						} //ends while
						AccountList.logoutStaff(currentStaff.getId());
						System.out.println("Just a moment...");

					}//ends if
					else
					{System.out.println("Login was unsuccessfully Please try again");
					choice="adfsd";
					}
					break;

				case "3": 

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
	 *  Print the Staff's choices 
	 */

	public static void printMenu() { 
		System.out.println("\t\t Welcome to the Staff Menu "); 
		System.out.println("\t\t ======================"); 
		System.out.println("The Menu Options:"); 
		System.out.println("1: Register new staff member");
		System.out.println("2: Login to your Account");
		System.out.println("0: Quit/Exit ");

		System.out.println("Please enter your selection > "); 
	} //ends printMenu 
	/** 
	 * Case 2 login input
	 * @param in - For instance, by passing an already open Scanner to your core business logic functions, 
	 * you can mock a real Staff's behavior and create a test to ensure your code remains stable, by constructing a 
	 * Scanner that reads from a hard coded String, and passing that into your method, without 
	 * needing to run the whole class and type in the behavior your testing manually again and again.
	 * @return return the object of the Staff to maintain through the program
	 */
	public static Staff loginInput(Scanner in){
		System.out.print( "\nFollow the Prompts to Log-In to your Account  \n ");
		System.out.print( "\nPlease enter your userid : \n ");
		String userid = in.nextLine().trim();
		System.out.print( "\nPlease enter your password: \n ");
		String pass = in.nextLine().trim();
		currentStaff = AccountList.loginStaff(userid, pass);
		if (currentStaff != null)
		{	 
			return currentStaff;
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
		System.out.println("2: Manage Invoice");
		System.out.println("3: Logout");

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
		System.out.println("3: Delete Staff Account");
		System.out.println("4: Go back");
		System.out.println("Please enter your selection > ");  
	}//ends subAccountmgmtmenu

	/** 
	 * submenu of account management switch statement
	 */
	public static void accountSetting(Scanner in){
		String choice = in.nextLine().trim(); 
		while (!choice.equalsIgnoreCase("4")) { 

			switch(choice){
			case "1":
				System.out.println( "View your account: " +currentStaff.toString() +"\n");
				break;
			case "2":
				System.out.println("update your account");
				Staff newCustomer = editUser(in);
				if(newCustomer==null)
				{
					System.out.println("Staff was not modified cancelling update");
				}
				else 
					if (AccountList.updateAccountbyStaff(newCustomer) != false)

						System.out.println("Proceeding back to SubMenu");
					else
						System.out.println("Your account is not updated");
				break;
			case "3":
				System.out.println("Do you want to delete your Staff account? Type yes to proceed or press any key to goback");
				String deletex = in.nextLine().trim(); 
				if (deletex.equals("yes") == true)
				{AccountList.deleteStaffAccount(currentStaff.getId());
				System.out.println("Account has been deleted. The program will exit");
				System.exit(0);}
				else
					System.out.println("Account was not deleted");
					choice = "dasfsd";
				break;
			default: 
				System.out.println("Sorry, invalid choice"); 
				break;
			}//ends switch
			subAccountMgmtMenu();
			choice = in.nextLine().trim(); 
		}//ends while
	}//ends accountsettings method

	public static Staff editUser(Scanner in){
		@SuppressWarnings("unused")
		boolean changed = false;
		int count = 0; 

		System.out.println("Please enter your NEW name or 'No' not to change your name");
		String name = in.nextLine();
		if (!(name.equalsIgnoreCase("no")))
		{
			currentStaff.setName(name);
			changed =true;
			count++;
		}
		System.out.println("Please enter your NEW Position of the company or 'No' not to change your title");
		String title = in.nextLine();
		if (!(title.equalsIgnoreCase("no")))
		{
			changed =true;
			currentStaff.setTitle(title);
			count++;
		}

		System.out.println("Please enter your NEW Phone Number or 'No' not to change your phone number");
		String phoneNumber = in.nextLine();
		if (!(phoneNumber.equalsIgnoreCase("no")))
		{
			changed =true;
			currentStaff.setPhoneNumber(phoneNumber);
			count++;
		}


		if(count == 0)
			return null;
		else
			System.out.println(count+" attributes has been updated in your account");
		return currentStaff;
	}


}//ends class
