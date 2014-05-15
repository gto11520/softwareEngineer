/**
<p>Title: Online Purchasing </p>
<b>Filename:</b> AccountList.java<br>
<b>Date Written:</b>April 17 2014 <br>
<b>Description</b>The AccountList class Manages all the storing/editing of Users or Employees information in the Mysql Database<br>
@author David Chan
 */

package softwareEngineer;

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
	

	@SuppressWarnings("static-access")

	/**
	 * resetPassword method will require you to verfiy your userid, name, and phonenumber 
	 * @param customer user object will be stored and id
	 * @return the retreival of database password to user 
	 */
	public static boolean resetPassword(String password, String id)
	{
		if (AccountInvoiceJDBC.resetPassword(password, id) == true)
			return true;

		return false;
	}//ends forgotPassword

	/**
	 * update account base on what the userid 
	 * @param a: user object that will take in and jdbc will updateinfo 
	 * @return boolean true if account has been updated or not .
	 */
	public static boolean updateAccountbyUser(User a)
	{
		
		if (AccountInvoiceJDBC.updateAccountbyUser(a) == true)
			return true;
		else
			return false;
		
	}
	/**
	 * 
	 * @param userid : passed by app class to checks userid and then jdbc query will remove
	 * @return boolean true if account removal has been done
	 */
	@SuppressWarnings("static-access")
	public static boolean deleteUserAccount (String userid) 
	{
		AccountInvoiceJDBC connect = null;
		if (connect.deleteUserAccount(userid) == true)
			return true;
		
		return false;
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
	
	/**
    @param Staff object to retrieve employee info
    @return true or false if staff has been connected to the db and the data has been stored
	 */
	@SuppressWarnings("static-access")
	public static boolean createStaffAccount(Staff employee){

		AccountInvoiceJDBC connect=null;
		if (connect.createStaffAccount(employee) == true)
			return true;
		else
			return false;		
	}//ends addStaff

	/**
	@param String id , String pass : takes in id and password from the app class to verify the db 
	@return true or false if connection has been connected to the db and the data has been verified 
	 */
	@SuppressWarnings("static-access")
	public static Staff loginStaff(String id,String pass){

		AccountInvoiceJDBC connect=null;
		Staff a = connect.loginStaff(id, pass);
		if (a != null)
			return a;
		else
			return null;
	}
	

	/**
	 * update account base on what the userid 
	 * @param a: Staff object that will take in and jdbc will updateinfo 
	 * @return boolean true if account has been updated or not .
	 */
	public static boolean updateAccountbyStaff(Staff a)
	{
		
		if (AccountInvoiceJDBC.updateAccountbyStaff(a) == true)
			return true;
		else
			return false;
		
	}
	/**
	 * @param userid : passed by app class to checks userid and then jdbc query will remove
	 * @return boolean true if account removal has been done
	 */
	@SuppressWarnings("static-access")
	public static boolean deleteStaffAccount (String userid) 
	{
		AccountInvoiceJDBC connect = null;
		if (connect.deleteStaffAccount(userid) == true)
			return true;
		
		return false;
	}
	/**
	 * @param id : passed by the userapp
	 * @return true or false whether it is logged out
	 */
	
	@SuppressWarnings("static-access")
	public static boolean logoutStaff(String id){
		AccountInvoiceJDBC connect= null;
		if (connect.logoutStaff(id) == true)
			return true;
		else
			return false;
	}
}// end AccountList class
