package Algo;

import application.person;

public class Client {
	
	public static person onlinePerson;
	
	public static boolean checkUser(String username,String password)
	{
		int hashPassword=password.hashCode();
		return server.checkUser(username,hashPassword);
	}

}
