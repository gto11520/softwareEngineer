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
	    //setter method of the name,password phone number, payment 
	    
	    System.out.println("\n");
	    testuser.setName("test name");
	    testuser.setAddress("test address");
	    testuser.setPassword("test password");
	    testuser.setPhoneNumber("5167232322");
	    testuser.setPayment("visatest 141241212");
	    System.out.println("print info: " + testuser);
		
	   //**************Test the AccountList.java*********
	   //**************Testing specifically the createUserAccount() *********
	   //**************First use case test***********
	    
	    AccountList accountTest = new AccountList();
//	    
//	    if (accountTest.createUserAccount(testuser) == true)
//	    	System.out.println("Your account has been registered");
//	    else
//	    	System.out.println("Your account has not been registered");
	    
	    //**************Test the AccountList.java*********
	    //**************Testing specifically the loginUser() *********
	    //************Second use cases***************
	    if (accountTest.loginUser(testuser.getId(), testuser.getPassword()) != null)
	    	System.out.println("Your account is logged in");
	    else
	    	System.out.println("Your account is not logged in");
	   
	   System.out.println();
	    //**************Test the AccountList.java*********
	    //**************Testing specifically the UpdateaccountbyUser *********
	    //************third use cases***************
	    if (accountTest.updateAccountbyStaff(testuser) == true )
	    {testuser.setName("new Name by use case");
	    	System.out.println("Your account has been updated");
	     System.out.println(testuser);	
	    }
	    System.out.println();
	  //**************Test the AccountList.java*********
	    //**************Testing specifically the resetpassword*********
	    //************third use cases***************
	    
	  testuser.setPassword("newpassword");
	    if (accountTest.resetPassword(testuser.getPassword(), testuser.getId()) == true )
	    {
	    	System.out.println("password has been changed to 'newpassword'");
	     System.out.println(testuser);	
	    }
	    
	    
	    
	    
	    
	    
	    //**************Test the AccountList.java*********
	    //**************Testing specifically the DeleteUser account *********
	    //************third use cases***************
	    if (accountTest.deleteUserAccount(testuser.getId()) ==true )
	    	System.out.println("Your account has been deleted");
	    else
	    	System.out.println("Your account is not deleted");
	     
		
	}
}//ends unit test class