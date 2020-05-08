package controllers;

import model.User;
import data.UserData;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;


public class Admin implements Initializable{
	
	@FXML
	ListView<String> allUsers = new ListView<String>();
		
	@FXML
	TextField adminInput; 
	
	@FXML 
	Button addUser; 
	
	@FXML 
	Button deleteUser; 
	
	@FXML
	Button listUsers; 
	
		
	private User addedUser; 
	
	private ArrayList<String> temp = new ArrayList<String>();
	
	
	public Admin() {
		
	}
	
	@FXML
	public void logoutClicked() throws Exception {
		UserData.save(UserData.getUsers());
		ChangeScene ns = new ChangeScene("../gui/login.fxml",400,200);
		ns.setTitle("Login Page");
		ns.currentScene();
	}
	
	@FXML 
	public void listUsersClicked() {
		
		if(allUsers.getItems().size() == 0) {
			adminInput.setText("No users to list");
			fillListView(); 
		}
		
		else {
			return; 
		}
	}
	
	@FXML 
	public void addButtonClicked() {
				
		if(adminInput.getText().trim().isEmpty() || adminInput.getText() == null) {
			System.out.println("Error: no user to add"); 
			return; 
		}
		
		else if(!UserData.searchUser(adminInput.getText())) {
			addedUser = new User(adminInput.getText()); 
			System.out.println("User admin added: " + adminInput.getText()); 
			UserData.addUser(addedUser);
			allUsers.getItems().add(adminInput.getText()); 
			adminInput.clear(); 
		}
		
		else if(UserData.searchUser(adminInput.getText())) {
			adminInput.setText("User already exists");
		}
 	}
	
	@FXML
	public void deleteButtonClicked() throws Exception {
		
		if(adminInput.getText().trim().isEmpty() || adminInput.getText() == null) {
			adminInput.setText("No user to delete");
			return; 
		}
		
		else if(UserData.searchUser(adminInput.getText())) {
			temp.remove(adminInput.getText()); 
			UserData.getUsers().remove(adminInput.getText()); 
			allUsers.getItems().remove(adminInput.getText()); 
			adminInput.clear(); 
		}
		
		else {
			adminInput.setText("User does not exist"); 
		}
	}
	
	/**
	 * Transfers set elements and stores the name of all users in an ArrayList
	 * The array list then populates the ListView
	 */
	
	public void fillListView() {
		temp.addAll(UserData.getUsers().keySet());

		for(int i = 0; i < temp.size();i++) {
			allUsers.getItems().add(temp.get(i)); 
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		if(UserData.currentUsers.isEmpty()) {
			System.out.println("No users exist yet"); 
		}
		
		else {
			System.out.println("Users currently exist");
			fillListView(); 
		}
		
	}
}
