package controllers;


import data.LoadStock;
import data.UserData;
import model.User; 

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Login{
	

	private String username; 
		
	private ChangeScene nextScene; 
		
	public static User newUser;
	
	public static User currentUser; 
		
	@FXML
	TextField userInput;   

	@FXML 
	Button loginButton; 
	
	/**
	 * Handles the button click action
	 * If the user clicks login they are either the admin or a new/existing user
	 * @throws Exception
	 */
	
	@FXML
	private void loginClicked() throws Exception{
				
		if(userInput.getText().trim().isEmpty() || userInput.getText() == null) {
			System.out.println("empty"); 
			userInput.setText("username cannot be empty");
			return; 
		}
		
		else {
			
			username = userInput.getText().trim();
			
			if(username.equals("admin")) {
				
				System.out.println("Admin is logging in"); 
				nextScene = new ChangeScene("../gui/admin.fxml",600,400);
				nextScene.setTitle("Admin");
				nextScene.currentScene();
			}
			
			else if(username.equals("stock")) {
				
				if(!UserData.searchUser("stock")) {
					
					newUser = new User(username);
					currentUser = newUser; 
					UserData.addUser(newUser);
					System.out.println("New user: " + username);
					System.out.println("CU: " + username);
					nextScene = new ChangeScene("../gui/user.fxml",600,400);
					nextScene.currentScene();
					nextScene.setTitle(username +"'s " + "Albums"); 
					
				}
				
				else {
					System.out.println("Old user");
					currentUser = UserData.getUser(username); 
					nextScene = new ChangeScene("../gui/user.fxml",600,400);
					nextScene.currentScene();
					nextScene.setTitle(username +"'s " + "Albums"); 
				}
				
			}
			
			else if(UserData.searchUser(username)) {
				currentUser = UserData.getUser(username); 
				nextScene = new ChangeScene("../gui/user.fxml",600,400);
				nextScene.currentScene();
				nextScene.setTitle(username +"'s " + "Albums"); 
			}		
			
			else {
				
				newUser = new User(username); 
				currentUser = newUser; 
				UserData.addUser(newUser);
				System.out.println("New user: " + username);
				System.out.println("CU: " + username);
				nextScene = new ChangeScene("../gui/user.fxml",600,400);
				nextScene.currentScene();
				nextScene.setTitle(username +"'s " + "Albums");

			}
		}	
		userInput.clear(); 
	}
}
