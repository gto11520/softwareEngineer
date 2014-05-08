/**
<p>Title: Online Purchasing </p>
<b>Filename:</b> AccountList.java<br>
<b>Date Written:</b>April 17 2014 <br>
<b>Description</b>The AccountList class Manages all the storing/editing of Users or Employees information in the Mysql Database<br>
@author David Chan
 */

package softwareEngineer;


import java.util.Scanner;



public class AccountList {


	/**
    @param User object to retrieve customer info
    @return true or false if user has been connected to the db and the data has been stored
	 */
	@SuppressWarnings("static-access")
	public static boolean createUserAccount(User customer){

		AccountInvoiceJDBC connect=null;
		if (connect.createUserAccount(customer) == true)
			return true;
		else
			return false;		
	}//ends addUser

	/**
	@param String id , String pass : takes in id and password from the app class to verify the db 
	@return true or false if connection has been connected to the db and the data has been verified 
	 */
	@SuppressWarnings("static-access")
	public static User loginUser(String id,String pass){

		AccountInvoiceJDBC connect=null;
		User a = connect.loginUser(id, pass);
		if (a != null)
			return a;
		else
			return null;
	}
	/**
	 *@param String id : takes in id from the app class to verify the db 
	 *@return null or user object if database has verified info
	 */
	@SuppressWarnings("static-access")
	public static User searchUser (String id){

		AccountInvoiceJDBC connect = null;
		User a = connect.searchUser(id);
		if (a != null)
			return a;
		else
			return null;
	}

	/**
   edit the new user info through the JBDC connection to update information 
   also includes password retrieval password retrieval
   @param b user object will edited and then set.
	 */
	public static void updateAccountbyUser(User b){

	}
	/**
   this forgotPassword method will construct a object that will consit of three items userid,name,and phone.
   @return user object of forgotPassword which consist of userid , name and phone number.
	 */

	@SuppressWarnings("static-access")
	public static User resetPassword()
	{
		AccountInvoiceJDBC connect;
		connect = new AccountInvoiceJDBC();
		Scanner scan = new Scanner( System.in );
		Scanner namescan = new Scanner( System.in );

		System.out.println("What is your userid?");
		String userid = scan.nextLine();

		System.out.println("What is your name?");
		String name = namescan.nextLine();

		System.out.println("What is your phone number?");
		String phonenum = scan.nextLine();

		User b = new User ( userid, name, phonenum); //new data
		User a = new User(b); //updated
		connect.updateAccountbyUser(b, a);

		return b;
	}//ends forgotPassword

	/**
   update account base on what the userid will input
   @param customer1 and customer2 and
   @return User object .
	 */
	public static boolean updateAccountbyUser(User customer1, User customer2)
	{
		
		return true;
	}
	
	/**
	 * @param id : passed by the userapp
	 * @return true or false whether it is logged out
	 */
	
	@SuppressWarnings("static-access")
	public static boolean logoutUser(String id){
		AccountInvoiceJDBC connect= null;
		if (connect.logoutUser(id) == true)
			return true;
		else
			return false;
	}
}// end AccountList class
