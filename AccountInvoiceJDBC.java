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
import java.util.ArrayList;
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
	 * @author David chan
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
	 * @author David chan
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
	 * @author David chan
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
	 * @author David chan
	 * registerUser method will store User information into DB 
	 * @param customer  a User object to be store a single object 
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
	 * @author David chan
	 *loginUser method will create a connection with jdbc and verfiy user information
	 *@param String id and password when logging in the this will take in two arguements and first veries the user of the db then the rest of the attributes
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
					String query2 = "UPDATE dchan.user SET loginState='1' WHERE userid = '"+userid+"' ";
					preparedStatement.executeUpdate(query2);
					customer.setStatus(true);
					return customer;
				}//inner if
				else{
					System.out.println("Password is incorrect! ");
					return null;}
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
	 *@author David chan
	 * @param String id - the id of the User when they logout immediately then the user will update and set login state to 0 
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
	 * @author David chan
	 * deleteUserAccount method will remove user object account
	 * @param String id : This will take in a id from user object.userid and then remove base on update query
	 * @return Boolean true if user has been removed
	 */
	public static boolean deleteUserAccount(String id){
		String deleteSQL = "DELETE FROM dchan.user WHERE userid=? ";

		try {
			getConnection();
			preparedStatement = conn.prepareStatement(deleteSQL);
			preparedStatement.setString(1, id);;
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}//ends deleteUserAccount


	/**
	 * resetPassword method will require you to verify your userid, name, and phonenumber 
	 * @param password and id so that the user
	 * @return boolean when userid is confirmed and password has beeen changed return true 
	 */
	@SuppressWarnings("null")
	public static boolean resetPassword(String newpassword,String id){
		try {
			getConnection();
			String updateSQL =  "UPDATE dchan.user SET password= ? WHERE userid = ?";
			preparedStatement = conn.prepareStatement(updateSQL);
			preparedStatement.setString(1,newpassword);
			preparedStatement.setString(2,id);
			preparedStatement.executeUpdate();

			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		prepareDisconnect();
		return false;
	} //ends resetpassword

	
	public static boolean updateAccountbyUser(User a){
		getConnection();
		String updateSQL =  "UPDATE dchan.user SET name=?,address=?,phone =?,paymentinfo=? WHERE userid = ?";

		try {
			preparedStatement = conn.prepareStatement(updateSQL);
			preparedStatement.setString(1, a.getName());
			preparedStatement.setString(2, a.getAddress());
			preparedStatement.setString(3, a.getPhoneNum());
			preparedStatement.setString(4, a.getPayment());
			preparedStatement.setString(5, a.getId());
		    preparedStatement.executeUpdate();
		    
		    return true;
		}	catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		prepareDisconnect();
		return false;
	} //ends updateUserAccount


		/** 
		 * @author david chan
		 * searchUser method will search for User object account
		 * @param String customer Id
		 * @return User object
		 */ 
		public static User searchUser (String id) { 

			String userid = "";
			String name ="";
			String adr = "";
			String phone = "";
			String pass = "";
			String paymentinfo ="";
			boolean status =false;
			User a = new User(userid,pass,name,adr,phone,paymentinfo);
			String selectSQL = "Select * From user WHERE userid Like ? "; //the % sign is used to find specified field of this userid cannot be anything else

			try {
				getConnection();
				preparedStatement = conn.prepareStatement(selectSQL);
				preparedStatement.setString(1, id);
				ResultSet results = preparedStatement.executeQuery();
				int count = 0;
				while (results.next() || count == 0 ) { 
					if (count == 0)
						results.absolute(1);
					phone= results.getString("phone");
					//System.out.println(phone+" this is test in jdbc");
					name = results.getString("name");
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
				} //ends while
				a.setName(name);
				a.setAddress(adr);
				a.setPhoneNumber(phone);
				a.setId(userid);
				a.setPassword(pass);
				a.setStatus(status);
				a.setPayment(paymentinfo);
				//create object & print 
			} catch (SQLException e) {
				
		//		System.out.println(e.getMessage());
			} 
			prepareDisconnect();

			return a;

		}//ends loginUser
		
//***************************************Staff****************************************************
//***************************************Staff****************************************************
	
		/**
		 * @author Carlos
		 * register employee method will store employee information into DB 
		 * @param employee  a User object to be store
		 * @return true or false if the account has been created
		 */
		public static boolean  createStaffAccount(Staff employee){
			String selectSQL = "INSERT INTO dchan.staff (userid, password, name, title, phone)"
					+ "VALUES ('"+employee.getId()+"','"+employee.getPassword()+"','"+employee.getName()+"','"+employee.getTitle()+"','"+employee.getPhoneNum()+"')";
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
		 * @author Carlos
		 *loginStaff method will create a connection with jdbc and verfiy user information
		 *@param customer a user object will be stored
		 *@return boolean  will return true or false if the password has been confirmed
		 */
		public static Staff loginStaff(String id, String pass){  
			String selectSQL = "SELECT userid,password,name,title,phone FROM dchan.staff WHERE userid = ?";

			try {

				getConnection(); //get current connection
				preparedStatement = conn.prepareStatement(selectSQL);
				preparedStatement.setString(1, id);
				//preparedStatement.setString(2, pass);

				// execute select SQL statement
				ResultSet results = preparedStatement.executeQuery();

				while (results.next()) {

					//this will mean both user id an password has been verified 
					String userid = results.getString("userid");
					String password = results.getString("password");
					String name = results.getString("name");
					String title = results.getString("title");
					String phone = results.getString("phone");

					Staff employee = new Staff(userid,password,name,title,phone);
					if(pass.equals(password)){
						String query2 = "UPDATE dchan.staff SET loginState='1' WHERE userid = '"+userid+"' ";
						preparedStatement.executeUpdate(query2);
						employee.setStatus(true);
						return employee;
					}//inner if
					else{
						System.out.println("Password is incorrect! ");
						return null;}
				}

			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			prepareDisconnect();	
			return null;

		}//ends loginStaff
		

		/**
		 * @author Carlos
		 * logoutUser method will  close connection of jdbc 
		 * @param String id - the id of the employee will logout immediately when staff logs off 
		 * @return boolean , true if all field values in customer object are correct otherwise false
		 */
		public static boolean logoutStaff(String id){
			String selectSQL = "UPDATE dchan.staff SET loginState='0' WHERE userid = '"+id+"' ";
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
		 * @author francious
		 * deleteUserAccount method will remove staff object account
		 * @param employee id 
		 * @return staff object
		 */
		public static boolean deleteStaffAccount(String id){
			String deleteSQL = "DELETE FROM dchan.staff WHERE userid=? ";

			try {
				getConnection();
				preparedStatement = conn.prepareStatement(deleteSQL);
				preparedStatement.setString(1, id);;
				preparedStatement.executeUpdate();
				return true;
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			return false;
		}//ends deleteUserAccount
		/**
		 * @author francious
		 * update staff method will query datatbase and update
		 * @param a which is a object of staff
		 * @return staff object
		 */
		
		public static boolean updateAccountbyStaff(Staff a){
			getConnection();
			String updateSQL =  "UPDATE dchan.staff SET name=?,title=?,phone =?WHERE userid = ?";

			try {
				preparedStatement = conn.prepareStatement(updateSQL);
				preparedStatement.setString(1, a.getName());
				preparedStatement.setString(2, a.getTitle());
				preparedStatement.setString(3, a.getPhoneNum());
				preparedStatement.setString(4, a.getId());
			    preparedStatement.executeUpdate();
			    
			    return true;
			}	catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			prepareDisconnect();
			return false;
		} //ends updateUserAccount
//********************************INVOICE*****************************************
//********************************************************************************
		/**
		 * This method retrieves a single Invoice from the Database based on the Invoice ID Num. 
		 * Author : Ernesto Thermidor
		 * @param newInvoiceID : Accepts Invoice ID, that needs to be in SQL parameter
		 * @return invoiceData : Returns a single invoice object.
		 */
		public Invoice getSingleInvoiceFromJDBC(int newInvoiceID)
		{
			
			Invoice invoiceData = new Invoice();
			try 
			{	String gsISQL= "SELECT * FROM InvoiceTable WHERE invoiceNUM = ?"; //SQL Statement
				PreparedStatement s = conn.prepareStatement(gsISQL); //Passing SQL statement to PreparedStatement
				s.setInt(1, newInvoiceID); //Passing the Invoice ID to the SQL using the set method. 
				ResultSet rs = s.executeQuery(); //passing the result to ResultSet which are rows. 
				
				while(rs.next()) //Creates an Invoice object 
				{
					invoiceData.setInvoiceNum(rs.getInt("invoiceNum"));
					invoiceData.setDateOfOrder(rs.getString("DateOfOrder"));
					invoiceData.setPrice(rs.getDouble("Price"));
					invoiceData.setCustomerInfo(rs.getString("CustomerInfo"));
//					invoiceData.setQuantity(rs.getInt("Quantity"));
					invoiceData.setPaidDate(rs.getString("paidDate"));
					invoiceData.setStatus(rs.getString("Status"));
					invoiceData.setCompanyCode(rs.getString("CompanyCode"));
				}
			} 
			catch (Exception e) 
			{
				System.err.println(e);
			}
			return invoiceData;
		}
		/**
		 * This method returns multiple invoice list based on the select parameter. 
		 * Author: Ernesto Thermidor
		 * @param select : An integer parameter to used in switch statement. 
		 * 				   Allowed Values For Select are: 
		 * 				   (1) select = 1 For Returning All The invoices in database.
		 * 				   (2) select = 2 For Returning Pending Invoices
		 * 				   (3) select = 3 For Returning Paid Invoices.
		 * 				   (4) select = 4 For Returning paid Invoices Older than the Current Days. Used in combination with the Parameter Days.
		 * @param Days : An integer parameter to hold days used in combination when Parameter select = 4
		 * @return invoiceData : Returns an Array List of invoices 
		 */
		public ArrayList<Invoice> getInvoiceListFromJDBC(int select, int Days)
		{
			ArrayList<Invoice> invoiceData = new ArrayList<Invoice>();
			Statement stmt ;
			ResultSet rs = null;
			try
			{
				stmt = conn.createStatement();
				
				switch (select) 
				{
					case 1:
						String disAllSql = "SELECT * FROM InvoiceTable ORDER BY invoiceNum DESC"; //SQL Statement
						rs = stmt.executeQuery(disAllSql);
						break;
					case 2:
						String disSPenSql = "SELECT * FROM InvoiceTable WHERE STATUS ='Pending' ORDER BY 'invoiceNUM' ASC";//SQL Statement
						rs = stmt.executeQuery(disSPenSql);
						break;
					case 3:
						String disSPaSql = "SELECT * FROM InvoiceTable WHERE STATUS ='Paid' ORDER BY 'invoiceNUM' ASC";//SQL Statement
						rs = stmt.executeQuery(disSPaSql);
						break;
					case 4:
						String disPDSql ="SELECT * FROM InvoiceTable WHERE paidDate <= (CURDATE() - INTERVAL ? DAY)";//SQL Statement
						PreparedStatement prepStmt = conn.prepareStatement(disPDSql);
						prepStmt.setInt(1, Days);
						rs = prepStmt.executeQuery();
						break;
					default:
						break;
				}
				
				while(rs.next()) //Creates The invoice object and adds it to ArrayList as long as there is rows in the resultset rs. 
				{
					Invoice newInvoice = new Invoice();
					newInvoice.setInvoiceNum(rs.getInt("invoiceNum"));
					newInvoice.setDateOfOrder(rs.getString("DateOfOrder"));
					newInvoice.setPrice(rs.getDouble("Price"));
					newInvoice.setCustomerInfo(rs.getString("CustomerInfo"));
//					newInvoice.setQuantity(rs.getInt("Quantity"));
					newInvoice.setPaidDate(rs.getString("paidDate"));
					newInvoice.setStatus(rs.getString("Status"));
					newInvoice.setCompanyCode(rs.getString("CompanyCode"));
					
					invoiceData.add(newInvoice);
				}
			}
			catch(SQLException e)
			{
				System.err.println(e);
			}
			return invoiceData;
			
		}
		
		/**
		 * This methods creates Invoices from Orders Database
		 * Author : Cyril Thomas
		 * @param newDate : Accept String Date in format of "yyyy-MM-dd" and pass that value to the SQL parameter. 
		 * @return -1 if failed or rsInt the # of row created. 
		 */
		public int createInvoiceFromOrderList(String newDate)
		{
			try 
			{
				String createSQL = "INSERT INTO InvoiceTable( invoiceNum, DateOfOrder, Price, CustomerInfo,CompanyCode )"
									+ 	" SELECT orderId, orderdate, totalPrice, shippinginfo,payment"
									+	" FROM createorder"
									+	" WHERE createorder.payment LIKE 'C%'"
									+	" AND createorder.OrderDate >= ?"
									+	" AND NOT EXISTS" 
									+ 	" (SELECT InvoiceNum FROM InvoiceTable WHERE InvoiceNum = OrderID)";
				PreparedStatement s = conn.prepareStatement(createSQL);
				s.setString(1, newDate);
				int rsInt = s.executeUpdate();
				return rsInt;
			} 
			catch (SQLException e)
			{
				System.err.println(e);
			}
			return -1;
		}
		/**
		 * This method updates an existing invoice attribute in database. 
		 * Author: Cyril Thomas
		 * @param newInvoice : Accept an Invoice Object that hold current or new Invoice values for each attributes. 
		 * @return affected : Returns the # of rows affected. 
		 */
		public int UpdateInvoiceJDBC(Invoice newInvoice)
		{
			int affected = 0;
			String updateSQL =  "UPDATE InvoiceTable SET " +
								 " InvoiceTable.Price = ?, InvoiceTable.CustomerInfo = ?, InvoiceTable.paidDate = ?, InvoiceTable.Status = ? " +
								 " WHERE InvoiceTable.invoiceNum = ? "	;
			try 
			{
				PreparedStatement upStmt = conn.prepareStatement(updateSQL);
				//Extracting the new Invoice data and passing that to the SQL statement. 
				upStmt.setDouble(1, newInvoice.getPrice());
				upStmt.setString(2, newInvoice.getCustomerInfo());
//				upStmt.setInt(3, newInvoice.getQuantity());
				upStmt.setString(3, newInvoice.getPaidDate());
				upStmt.setString(4, newInvoice.getStatus());
				upStmt.setInt(5, newInvoice.getInvoiceNum());
				
				affected = upStmt.executeUpdate();
			} 
			catch (SQLException e) 
			{
				System.err.println(e);
			}
			return affected;
		}
		/**
		 * This method deletes Older Invoices determined by how old the paid dates of the Invoices are. 
		 * Author : Kahliik Burrell
		 * @param Days : Integer Days that is used to determine old invoices and pass the value to SQL Parameter. 
		 * @return rowsaffected: Returns how many Invoices where deleted
		 */
		public int deleteInvoiceJDBC(int Days)
		{
			int rowsaffected = 0;
			String deleteSQL = "DELETE FROM InvoiceTable WHERE paidDate <= (CURDATE() - INTERVAL ? DAY)";
			
			try 
			{
				PreparedStatement deStmt = conn.prepareStatement(deleteSQL);
				deStmt.setInt(1, Days);
				rowsaffected = deStmt.executeUpdate();
			} 
			catch (SQLException e) 
			{
				System.err.println(e);
			}
			return rowsaffected;
		}

	}//ends AccountInvoiceJDBC 



