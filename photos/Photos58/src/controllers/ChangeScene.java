package controllers;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChangeScene {
	
	private String filePath;
	private Stage currentStage = model.Photos.cs; 
	public int width;
	public int height; 
	
	
	public ChangeScene(String filePath,int width, int height) {
		this.filePath = filePath;
		this.width = width;
		this.height = height; 
	}
	
	
	/**
	 * Changes the current scene to that which the user wants
	 * Helpful so that we don't rewrite the same code over and over
	 * @throws Exception
	 */
	
	
	public void currentScene() throws Exception {
		
		URL url = getClass().getResource(filePath);
		
		if(url == null) {
			System.out.println("Cannot find: " + filePath); 
		}
		
		else {
			System.out.println("w: " + width + "h: " + height);
			System.out.println("Found the file " + filePath); 
			Parent root = FXMLLoader.load(url);
	        Scene scene =  new Scene(root, width ,height);
	        currentStage.setScene(scene);
	        currentStage.show();
		}
	}
	
	public void setTitle(String title) {
		currentStage.setTitle(title);
	}

}
