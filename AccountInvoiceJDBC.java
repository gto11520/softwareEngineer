/**
<p>Title: Online Purchasing </p>
<b>Filename:</b> JDBCAccounts.java<br>
<b>Date Written:</b>April 14 2014 <br>
<b>Description</b>The JDBCAccounts class uses a JDBC connection to Users or Employees information in the Mysql Database<br>
@author David chan
 */

package softwareEngineer;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AccountInvoiceJDBC {
	// Oracle 11g (11.1) driver with Java 1.6, you don't need to call Class.forName. 
	// Otherwise, you need to call it to initialise the driver.
	private static final String USERNAME="root";
	private static final String PASSWORD="HKiwx64T";
	private static final String CONNECT_URL ="jdbc:mysql://localhost:3306/dchan";
	private static Connection conn = null;
	private static PreparedStatement preparedStatement = null; //prevent sql inject attack and performance is much better to do queries update search delete

	/**
	 * Get the connection from the private connection then it creates a connection using the driver manager 
	 */

	public static void getConnection()
	{
		if(conn != null) return;

		try
		{
			conn = DriverManager.getConnection(CONNECT_URL, USERNAME, PASSWORD);
			System.out.println("Connected: " + conn.toString()); //Debug Connection
		} 
		catch (SQLException e) 
		{
			System.err.println(e);
		}

	}
	/**
	 * disconnect the from the private connection 
	 */
	public static void prepareDisconnect()
	{
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			} //ends if
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//ends if preparedStatement

	}

	/**
	 * disconnect the from the private connection 
	 */
	public static void disConnect()
	{	
			
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			} //ends if
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//ends if preparedStatement

		try
		{	
			if(conn != null)
			{
				conn.close();
				//System.out.println("Connected: " + conn); //Debug
			} 
		}//end try
		catch(SQLException e)
		{
			System.err.println(e);
			e.printStackTrace();
		}

	}

	/**
	 * registerUser method will store user information into DB 
	 * @param customer  a User object to be store
	 * @return true or false if the account has been created
	 */
	public static boolean  createUserAccount(User customer){
		String selectSQL = "INSERT INTO dchan.user (userid, password, name, address,phone,paymentinfo)"
				+ "VALUES ('"+customer.getId()+"','"+customer.getPassword()+"','"+customer.getName()+"','"+customer.getAddress()+"','"+customer.getPhoneNum()+"','"+customer.getPayment()+"')";
		try {

			getConnection();
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.executeUpdate(selectSQL);
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		prepareDisconnect();
		return true;
	}//ends CreateUserAccount


	/**
	 *loginUser method will create a connection with jdbc and verfiy user information
	 *@param customer a user object will be stored
	 *@return boolean  will return true or false if the password has been confirmed
	 */
	public static User loginUser(String id, String pass){  
		String selectSQL = "SELECT userid,password,name,address,phone,paymentinfo FROM dchan.user WHERE userid = ?";

		try {

			getConnection(); //get current connection
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, id);
			//preparedStatement.setString(2, pass);

			// execute select SQL stetement
			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {

				//this will mean both user id an password has been verified 
				String userid = results.getString("userid");
				String password = results.getString("password");
				String name = results.getString("name");
				String address = results.getString("address");
				String phone = results.getString("phone");
				String paymentinfo = results.getString("paymentinfo");

				User customer = new User(userid,password,name,address,phone,paymentinfo);
				if(pass.equals(password)){
					System.out.println("inside while");

					System.out.println("correct password");
					String query2 = "UPDATE dchan.user SET loginState='1' WHERE userid = '"+userid+"' ";
					preparedStatement.executeUpdate(query2);
					customer.setStatus(true);
					return customer;
				}//inner if
				else
					return null;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		prepareDisconnect();	
		return null;

	}//ends loginUser

	/**
	 * logoutUser method will  close connection of jdbc 
	 * @param customer a user object 
	 * @return boolean , true if all field values in customer object are correct otherwise false
	 */
	public static boolean logoutUser(String id){
		String selectSQL = "UPDATE dchan.user SET loginState='0' WHERE userid = '"+id+"' ";
		try {
			getConnection(); //get the connection
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.executeUpdate(selectSQL);

		}//ends try
		catch (Exception e) {
			e.printStackTrace();
		}
		prepareDisconnect();
		
		return true;
	}//ends logoutUser


	/**
	 * deleteUserAccount method will remove user object account
	 * @param customer a user object will be stored
	 * @return User object
	 */
	public static User deleteUserAccount(User customer){
		return customer;
	}//ends deleteUserAccount


	/**
	 * resetPassword method will require you to verfiy your userid, name, and phonenumber 
	 * @param customer a user object will be stored
	 * @return the retreival of database password to user 
	 */
	public static String resetPassword(User customer){
		return null ;
	} //ends forgotpassword

	/**
	 * updateUserAccount method will update user object account
	 * @param customer1 a user object will retrieved and customer2 a user object will updated and verified 
	 */
	public static boolean updateAccountbyUser(User customer1, User customer2){

				try {
					getConnection();
					Statement stmt = conn.createStatement();
					String query = "UPDATE dchan.user SET userid = '"+customer2.getId()+"',password = '"+customer2.getPassword()+
							"' name='"+customer2.getName()+"', address = '"+customer2.getAddress()+"',"
							+ "phone = '"+customer2.getPhoneNum()+"', paymentinfo = '"+customer2.getPhoneNum()+"' WHERE userid = '"+customer1.getId()+"' ";
					stmt.executeUpdate(query);
					stmt.close();
					conn.close();
				}
				catch (Exception e) {
					e.printStackTrace();
		
				}
		return false;
	} //ends updateUserAccount


	/** 
	 * searchUser method will search for User object account
	 * @param String customer Id
	 * @return User object
	 */ 
	public static User searchUser (String id) { //,int search

		String userid = "";
		String name ="";
		String adr = "";
		String phone = "";
		String pass = "";
		String paymentinfo ="";
		boolean status =false;
		User a = new User(userid,pass,name,adr,phone,paymentinfo);
		String selectSQL = "Select userid,password,name,address,phone,paymentinfo,loginstate From user WHERE userid Like '%"+id+"%' "; //the % sign is used to find specified field of this userid cannot be anything else

		try {
			getConnection();
			preparedStatement = conn.prepareStatement(selectSQL);
			//		preparedStatement.setString(1, id);

			// execute select SQL stetement
			ResultSet results = preparedStatement.executeQuery();
			//System.out.println("Before while loop");
			int count = 0;
			while (results.next() || count == 0 ) { 
				if (count == 0)
					results.absolute(1);

				phone= results.getString(5);
				//System.out.println(phone+" this is test in jdbc");
				name = results.getString(3);
				//System.out.println(name+" this is test in jdbc");
				adr = results.getString("address");
				userid = results.getString("userid");
				pass = results.getString("password");
				paymentinfo = results.getString("paymentinfo");
				int stat = results.getInt("loginstate");
				if (stat == 0) {
					status = false;
				}
				else {
					status = true;
				}
				count++;
			} 
			// System.out.println("After while loop");
			a.setName(name);
			a.setAddress(adr);
			a.setPhoneNumber(phone);
			a.setId(userid);
			a.setPassword(pass);
			a.setStatus(status);
			a.setPayment(paymentinfo);
			//create object & print 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
		prepareDisconnect();

		return a;

	}//ends loginUser

	//-----------------------------------------------------------------------------------------------------------------------------
	// 												INVOICE SECTION
	//-----------------------------------------------------------------------------------------------------------------------------
	public static int createInvoiceFromOrderList(String newSQL)
	{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fDate = dateFormat.format(cal.getTime());
		try 
		{
			preparedStatement = conn.prepareStatement(newSQL);
			preparedStatement.setString(1, fDate);
			int rs = preparedStatement.executeUpdate();
			return rs;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		prepareDisconnect();

		return -1;

	}

	/**
	 * Author Ernesto Thermidor
	 * @return newInvoice It returns an ArrayList of Invoices.
	 */
	public ResultSet getInvoiceList(String newSQL, int placeHolder, String newStartDate, String newEndDate)
	{
		Statement stmt;
		ResultSet rs = null;
		try 
		{
			if(placeHolder > 0)
			{
				PreparedStatement stmt1 = conn.prepareStatement(newSQL);
				stmt1.setInt(1, placeHolder);
				rs = stmt1.executeQuery();
			}
			else if(placeHolder < 0)
			{
				PreparedStatement stmt2 = conn.prepareStatement(newSQL);
				stmt2.setString(1, newStartDate);
				stmt2.setString(2, newEndDate);
				rs= stmt2.executeQuery();
			}
			else 
			{
				stmt = conn.createStatement();
				rs = stmt.executeQuery(newSQL);
			}
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}

	/**
	 * Author Kahliik Burrell
	 * @param Invocie Pass by Reference of Arraylist of Invoices. 
	 * @return success Returns boolean value depending on the success of the connection. 
	 */
	public int setInvoieList(String sql,int newInvoiceNum ,double newPrice, String newCustomerInfo, 
			String newPaidDate, String newStatus, int newQuantity)
	{
		int affected = 0;
		try 
		{
			PreparedStatement upStmt = conn.prepareStatement(sql);
			upStmt.setDouble(1, newPrice);
			upStmt.setString(2, newCustomerInfo);
			upStmt.setInt(3, newQuantity);
			upStmt.setString(4, newPaidDate);
			upStmt.setString(5, newStatus);
			upStmt.setInt(6, newInvoiceNum);

			affected = upStmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return  affected;
	}

	public int deleteInvoiceList(String sql, String StartDate, String EndDate)
	{
		int affected = 0;
		try 
		{
			PreparedStatement deStmt = conn.prepareStatement(sql);
			deStmt.setString(1, StartDate);
			deStmt.setString(2, EndDate);
			affected = deStmt.executeUpdate();

		} 
		catch (SQLException e) 
		{

			e.printStackTrace();
		}
		return affected;
	}

	/**
	 * Author Cyril Thomas
	 * @return newInvoice It returns ArrayList of Invoices. 
	 */
	/*public ArrayList<Invoice> getOrderList()
	{
		ArrayList<Invoice> newInvoice = null;;
		return newInvoice;
	}*/


}//ends AccountInvoiceJDBC 



