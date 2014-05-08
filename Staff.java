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
        protected String id;
        protected String password;
	

	/**
	 * Staff object Non-Def constructor
	 * @param name The employee's name
	 * @param id the employee's id
	 * @param password The employee's password
	 */
	public Staff(String id, String password,String name) 
	{
		this.id = id;
		this.password = password;
	    this.name = name;
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
	 * Changes the Name of the employee.
	 * @param name Added new Name .
	 */
	public void setName(String name) 
	{
		this.name = name;
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

        public String ToString() {
            String msg = "";
           msg = "Name: "+name;
            return msg;
        }

}

