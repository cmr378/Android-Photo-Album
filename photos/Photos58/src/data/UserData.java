package data;

import model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap; 

public class UserData implements Serializable{
	

	private static final long serialVersionUID = 3004498425663499418L;
	public static HashMap<String,User> currentUsers = new HashMap<String,User>(); 
	public final String storeDir = "data";
	public static final String storeFile = "data.dat"; 
	
	public UserData() {
	}
		
	
	/**
	 * Adds any new user to the Set of users
	 * Same username won't work
	 * Still have to check for duplicate manually
	 * @param newUser - the new user object
	 */
	
	public static void addUser(User newUser) {
		
		if(searchUser(newUser.getUsername())) {
			System.out.println("User already exists"); 
		}
		
		else {
			currentUsers.put(newUser.getUsername(), newUser); 
		}
	}
	
	public static User getUser(String username) {
		if(currentUsers.containsKey(username)) {
			return currentUsers.get(username);
		}
		else {
			return null; 
		}
	}
	
	public static void updateUser(User updated) {
		if(currentUsers.containsKey(updated.getUsername())) {
			currentUsers.remove(updated.getUsername());
			currentUsers.put(updated.getUsername(), updated); 
		}
	}
	
	/**
	 * Returns all registered users 
	 * @return
	 */
	
	public static HashMap<String,User> getUsers(){
		return currentUsers; 
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	
	public static boolean searchUser(String username) {
		
		if(currentUsers.containsKey(username)) {
			return true;
		}
		
		else {
			return false; 
		}
	}	
	
	public static void save(HashMap<String,User> currentUsers) throws Exception {
		@SuppressWarnings("resource")
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(".." + File.separator + storeFile));
		oos.writeObject(currentUsers);
	}
	
	public static HashMap<String,User> load() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(".." + File.separator + storeFile));
		return (HashMap<String,User>)ois.readObject();
	}
	
	public static void setUsers() throws FileNotFoundException, ClassNotFoundException, IOException {
		currentUsers = load(); 
	}
	
 }
