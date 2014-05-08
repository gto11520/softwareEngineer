/**
<p>Title: Online Purchasing </p>
<b>Filename:</b> User.java<br>
<b>Date Written:</b>April 17 2014 <br>
<b>Description</b>The User class Manages all the Users information creates a single user object<br>
@author David Chan
*/

package softwareEngineer;


public class User extends Staff{
    
    private String address;
    private String phoneNum;
    private String paymentInfo;
        
    /**
        User Default Constructor
     */
	public User()
	{
		name = "";
		address = "";
		phoneNum = "";
		password = "";
		id = " ";
		
	}
        
    /**
	 * User Non-Default Constructor
	 * @param name The User's name
	 * @param address The User's address
	 * @param phoneNum The User's phone number
	 * @param userid The User ID
	 * @param password The User's password
	 */
	public User(String id, String password, String name, String address, String phoneNum,String paymentInfo) 
	{
		super(id,password,name);
		this.address = address;
		this.phoneNum = phoneNum;
        this.paymentInfo=paymentInfo;
        
	}
	
	/**
	 * user Non-default constructor 
	 * @param userid the user's password
	 * To retrieve user id only
	 */
	public User(String id,String name,String phoneNum) 
	{
		this.id = id;
		this.name =name;
		this.phoneNum = phoneNum;
		
	}
	/**
	 * User Non-Default Constructor
	 * @param aUser A User object that has been returned from JDBC.
	 */
	public User(User aUser)
	{
		this.name = aUser.getName();
		this.address = aUser.getAddress();
		this.phoneNum = aUser.getPhoneNum();
		this.id = aUser.getId();
		this.password = aUser.getPassword();
		this.paymentInfo=aUser.getPayment();
		this.state = aUser.getStatus();
	}

	
	/**
	 * Returns the address of the User.
	 * @return address
	 */
	public String getAddress() 
	{
		return address;
	}

	/**
	 * Changes the Address of the user.
	 * @param address The new Address added.
	 */
	public void setAddress(String address) 
	{
		this.address = address;
	}

	/**
	 * Returns the phone number of the user.
	 * @return phoneNumber
	 */
	public String getPhoneNum() 
	{
		return phoneNum;
	}

	/**
	 * Changes the Phone Number of the user.
	 * @param num new Number to be added.
	 */
	public void setPhoneNumber(String num) 
	{
		this.phoneNum = num;
	}

	/**
	 * Returns the Payment Info of the User.
	 * @return address
	 */
	public String getPayment() 
	{
		return paymentInfo;
	}

	/**
	 * Changes the Payment Info of the user.
	 * @param address The new Address added.
	 */
	public void setPayment(String paymentInfo) 
	{
		this.paymentInfo = paymentInfo;
	}
	/**
	 * toString method
	 * return a id,name,address,phonenumber,paymentinfo
	 */
	public String ToString() {
		String msg = "";
		if (getStatus() == true)
			msg = "UserId: "+this.id +"\n"+ "Name: "+name+"\n"+"Address: "+this.address +"\n"+"Phone Number: "+this.phoneNum +"\n"
					+"Payment Info: "+this.paymentInfo +"\n"+ name +" is currently logged in\n";

		else
			msg = "UserId: "+this.id +"\n"+ "Name: "+name+"\n"+"Address: "+this.address +"\n"+"Phone Number: "+this.phoneNum +"\n"
					+"Payment Info: "+this.paymentInfo +"\n"+ name +" is currently not logged in\n";

		return msg;
	}//end toString
}//ends user class
    
