package Moblima;

import java.util.ArrayList;

public class UserHandler {
	private ArrayList<User> users;
	
	public UserHandler() {
		this.users = new ArrayList<>();
		
	}
	public ArrayList<User> getUsers(){
		return users;
	}
	public int getSize() {
		return users.size()-1;
	}
}
