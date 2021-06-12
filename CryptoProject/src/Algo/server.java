package Algo;

import java.util.HashMap;

import application.person;

public class server {
	
	public static HashMap<String, String> hmap;
	public static person Bob = new  person("Bob",  "Bob@gmail.com","123");
	public  static person Alice = new  person("Alice",  "Alice@gmail.com","123");
	
		  

		public static boolean checkUser(String username , int hashpassword) {
			if(Bob.getPwd().hashCode()==hashpassword && Bob.getUsername().equals(username))
				return true;
			if(Alice.getPwd().hashCode()==hashpassword && Alice.getUsername().equals(username))
				return true;
	    return false;

	    }
	  
	  
	
}
