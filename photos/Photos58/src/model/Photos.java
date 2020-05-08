package model;

import data.UserData;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Photos extends Application {
	
	/**
	 * The start of the gui view, displays the login page
	 * Based on user input the scenes will change from here
	 */
	
	public static Stage cs; 
		
	/**
	 * Launches the initial login scene
	 */
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		URL url = getClass().getResource("../gui/login.fxml"); 
		cs = primaryStage; 
		
		if(url == null) {
			System.out.println("No file found"); 
			return;
		}
		
		else {
			System.out.println("Login FXML found"); 
		}
				
		Parent root = FXMLLoader.load(url); 
		Scene scene = new Scene(root,400,200); 
		cs.setTitle("Login Page");
		cs.setScene(scene); 
		cs.show(); 

	}
	
	
	public static void main(String[] args) {
		
		try {
			
		UserData.setUsers();
		System.out.println("Retrieved data: " + UserData.getUsers());
	}
	
	catch(IOException e) {
		e.printStackTrace();
	}
	
	catch(ClassNotFoundException e1) {
		e1.printStackTrace();
	}
		launch(args);
	}
}
