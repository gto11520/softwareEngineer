/**
<p>Title: Online Purchasing </p>
<b>Filename:</b> UnitTest.java<br>
<b>Date Written:</b>April 14 2014 <br>
<b>Description</b>The User application class to register, forgot password, or login<br>
@author David Chan
 */
package softwareEngineer;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class UnitTest {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
        
		//***************Test the Entity class************
		//***************Test User class******************
		System.out.println("Test User Class");
		System.out.println("---------------");
		
		//Non default user class - consisting user id,password,name,address,phonenumber,payment option
		User testuser = new User("jsmith","password1","john smith","378 smith lane","5168790000","visa 0193141441");
		
		//Non default user class - consisting user id,password
		//User testuser1 = new User("jsmith","password1");
		
		//accesor method getting the user id 
		System.out.println("get user id: " + testuser.getId());
		
		//accesor method getting the user password
	    System.out.println("get user password: " + testuser.getPassword());
	    
	    //accesor method getting the user name
	    System.out.println("get user name: " + testuser.getName());
	   
	    //accesor method getting the user address
	    System.out.println("get user Phone Address: " + testuser.getAddress());
	    
	    //accesor method getting the user phonenumber
	    System.out.println("get user Phone Number: " + testuser.getPhoneNum());
     
	   //accesor method getting the payment option
	    System.out.println("get user Payment: " + testuser.getPayment());
		
	   //**************Test the AccountList.java*********
	   //**************Testing specifically the createUserAccount() *********
	   //**************First use case test***********
	    
	    AccountList registerTest = new AccountList();
	    
	    if (registerTest.createUserAccount(testuser) == true)
	    	System.out.println("Your account has been registered");
	    else
	    	System.out.println("Your account has not been registered");
	    
	    //************Test the UserApp.java***********
	    //************Testing the user interface**********
	    
	    
		
	}
	
	//-------------------------------------- 
		// Do what the menu item calls for 
		//-------------------------------------- 
		@SuppressWarnings("static-access")
		public static void dispatch(int choice) { 
			Scanner scan = new Scanner(System.in);
			switch(choice) { 
			case 1:  
				System.out.println( "\nFollow the Prompts to register your account.");
				UserInput();
				dispatch(2);
				break; 

			case 2: 
				System.out.print( "\nFollow the Prompts to Log-In to your Account  \n ");
				System.out.print( "\nPlease enter your userid : \n ");
				String userid = scan.next();
				AccountInvoiceJDBC auser = null;
				System.out.println(auser.searchUser(userid).getId());

				System.out.print( "\nPlease enter your password: \n ");
				String pass = scan.next();

				if (AccountList.loginUser(userid, pass) != null)
				{	System.out.println("Login Success!"); //holds the userid of the current login
				dispatch(4); //goto to case 4 
				}	else
				{System.out.println("Login was unsuccessfully Please try again");
				mainMenu();
				}
				break;
			case 3: 
				System.out.print( "\nFollow the Prompts user to retrieve password.. \n ");
				System.out.print( "\nPlease enter your userid : \n ");
				break;

//			case 4: //account management menu switch statement
//				int x = 0;
//				while (x != 2) { 
//					accountMgmtMenu();
//					x = scan.nextInt(); 
//					if (x != 2){
//						subAccountMgmtMenu();
//						accountSetting();
//					}
//				}//ends while 
//	            System.out.println("Just a moment...");
//				break;
			case 0: 
				scan.close();
				break; 
			default: 
				System.out.println("Sorry, invalid choice"); 
			} 
		} //ends dispatcher
	
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
	//----------------------------
		//Loops into the main menu system
		//----------------------------
		public static void mainMenu(){
			Scanner scan = new Scanner( System.in );
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

}
