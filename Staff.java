/**
<p>Title: Online Purchasing </p>
<b>Filename:</b> User.java<br>
<b>Date Written:</b>April 17 2014 <br>
<b>Description</b>The Staff class Manages all the staff information and creates a single staff object<br>
@author David Chan
*/

package softwareEngineer;

public class Staff {//base class
        protected String name;
        protected boolean state;
        protected String title;
        protected String phoneNum;
        protected String id;
        protected String password;
	

	/**
	 * Staff object Non-Def constructor
	 * @param name The employee's name
	 * @param id the employee's id
	 * @param password The employee's password
	 */
	public Staff(String id, String password,String name,String title,String phoneNum) 
	{
		this.id = id;
		this.password = password;
	    this.name = name;
	    this.title =title;
	    this.phoneNum =phoneNum;
	}
	/**
	 * Staff object Non-Def constructor
	 * @param name The employee's name
	 * @param id the employee's id
	 * @param password The employee's password
	 * @param title the employee's title
	 */
	public Staff(String id, String password,String name,String phoneNum) 
	{
		this.id = id;
		this.password = password;
	    this.name = name;
	    this.phoneNum =phoneNum;
	}
	
	/**
	 * Staff object Non-Def constructor
	 * @param name The employee's id
	 * @param password The employee's password
	 */
	public Staff(String id, String password) 
	{
		this.id = id;
		this.password = password;
	}
	
	/**
	 * Default Constructor
	 */
	public Staff() 
	{
		super();
		name = "";
		password = "";
	
	}
	
	/**
	 * Returns the name of the employee.
	 * @return name
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * Returns the title of the employee.
	 * @return title
	 */
	public String getTitle() 
	{
		return title;
	}
	

	/**
	 * Returns the Phone number of the employee.
	 * @return phoneNum
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
	 * Changes the Name of the employee.
	 * @param name Added new Name .
	 */
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * Changes the title of the user.
	 * @param num new Number to be added.
	 */
	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	/**
	 * Returns the Password of the employee.
	 * @return password
	 */
	public String getPassword() 
	{
		return password;
	}
        
        /**
	 * Returns the employee Id.
	 * @return empId
	 */
	public String getId() 
	{
		return id;
	}
	
        /**
	 * Changes the Password of the user.
	 * @param password added new Password.
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	/**
	 * Checks and Returns the login status, TRUE is logged in and FALSE otherwise.
	 * @return state either true or false
	 */
	public boolean getStatus() 
	{
		return state;
	}

	/**
	 * Changes the login State.
	 * @param status The new condition base on whether state is logged in or out.
	 */
	public void setStatus(boolean status) 
	{
		this.state= status;
	}
	/**
	 * Sets the Staff Id Number of the user.
         * @param id takes in a new id and sets it
	 */
	public void setId(String id) 
	{
		this.id = id;
	}

        public String toString() {
        	String msg = "";
    		if (getStatus() == true)
    			msg = "UserId: "+this.id +"\n"+ "Name: "+name+"\n"+"Title: "+this.title +"\n"+"Phone Number: "+this.phoneNum +"\n"
    					 +"\n"+ name +" is currently logged in\n";

    		else
    			msg = "UserId: "+this.id +"\n"+ "Name: "+name+"\n"+"Title: "+this.title +"\n"+"Phone Number: "+this.phoneNum +"\n"
   					 +"\n"+ name +" is not currently logged in\n";
            return msg;
        }

}

