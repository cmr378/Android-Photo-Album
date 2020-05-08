package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import data.LoadStock;
import data.UserData;
import model.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class UserC implements Initializable {
	
	@FXML
	public Text albumName;
	
	@FXML
	public Text totalPhotos; 
	
	@FXML
	public Text earliestPhoto;
	
	@FXML
	public Text latestPhoto; 
	
	@FXML
	public ListView<String> albumList; 
	
	@FXML 
	public TextField popup;
	
	@FXML
	public TextField userInput; 
	
	@FXML
	public Button createAlbum;
	
	@FXML 
	public Button deleteAlbum;
	
	@FXML
	public Button renameAlbum; 
	
	@FXML
	public Button openAlbum; 
	
	@FXML
	public Button logout; 
	
	User currentUser = Login.currentUser;
	
	private ChangeScene nextScene; 
	
	public static String albumOpened; 


	public UserC() {
		
	}
	
	/**
	 * On launch of the user subsystem creates the following:
	 * A stock album
	 * Allows the list to be edited
	 * Welcome's the current user
	 * Clicking on list identifies album name
	 */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		onLaunch(); 
	}
	
	@FXML
	public void deleteClicked() {
		deleteAlbum(userInput.getText()); 
	}
	
	
	@FXML 
	public void renameClicked() {
		
		if(albumList.getSelectionModel().isEmpty()) {
			popup.setText("No album selected"); 
			userInput.clear(); 
		}
		
		else {
			renameAlbum(); 
			userInput.clear(); 
		}
	}
	
	@FXML
	public void openClicked() throws Exception {
		
		if(albumList.getSelectionModel().isEmpty()) {
			popup.setText("No album selected");
		}
		else {
			albumOpened = albumList.getSelectionModel().getSelectedItem(); 
			System.out.println("Opened: " + albumOpened); 
			nextScene = new ChangeScene("../gui/album.fxml",800,400); 
			nextScene.setTitle(albumList.getSelectionModel().getSelectedItem() + " album");
			nextScene.currentScene();
		}
		
	}
	
	@FXML
	public void logoutClicked() {
		
		try {
			System.out.println("saved"); 
			UserData.updateUser(currentUser);
			UserData.save(UserData.getUsers());
			ChangeScene ns = new ChangeScene("../gui/login.fxml",400,200);
			ns.setTitle("Login Page");
			ns.currentScene();
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void newClicked() {
		Album newAlbum = new Album(userInput.getText());
		currentUser.addAlbum(newAlbum);
		albumList.getItems().add(newAlbum.getAlbumName());
	}
	
	/**
	 * Selected listView item relates to user owned albums
	 */
	
	public void setFxText() {
		
		if(currentUser.getUserAlbums().containsKey(albumList.getSelectionModel().getSelectedItem()) || currentUser.getAlbumCount() != 0) {
			System.out.println("Selected album belongs to user");
			albumName.setText(albumList.getSelectionModel().getSelectedItem()); 
		}
		
		else {
			System.out.println("Album doesn't belong to user");
		}
	}
	
	/**
	 * Updates the listview by replacing the old name with the new
	 * Replaces the old album name in hashmap 
	 */
	
	public void renameAlbum() {

		Album temp = currentUser.getUserAlbums().get(albumList.getSelectionModel().getSelectedItem()); 
		temp.setAlbumName(userInput.getText());
		currentUser.getUserAlbums().remove(albumList.getSelectionModel().getSelectedItem());
		currentUser.getUserAlbums().put(userInput.getText(),temp); 
		albumList.getItems().set(albumList.getSelectionModel().getSelectedIndex(), userInput.getText());
		
	}

	
	public void onLaunch() {
		
		
		if(currentUser.getUsername().equals("stock") && currentUser.getAlbumCount() == 0) {
			LoadStock.loadPhotos(); // load stock photos for Stock user on first launch
			albumList.getItems().add("stock"); 
		}
		
		
		loadAlbums(); 
		
		popup.setText("Welcome " + currentUser.getUsername() + "!");
		albumList.setEditable(true);
		setFxText(); 
		
		if(currentUser.getAlbumCount() != 0) {
			albumList.getSelectionModel().select(0);
			setAlbumDetails(albumList.getItems().get(0));
		}
		
		
		System.out.println("Error?"); 

		albumList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			 @Override
		        public void handle(MouseEvent event) {
		            System.out.println("Listview item clicked: " + albumList.getSelectionModel().getSelectedItem());
		            popup.setText("Album: " + albumList.getSelectionModel().getSelectedItem());
		            setAlbumDetails(albumList.getSelectionModel().getSelectedItem());
		        }
		}); 
		
		System.out.println(currentUser.getUserAlbums());
	}
	
	public void loadAlbums() {
		if(currentUser.getAlbumCount() != 0) {
			for(int i = 0; i < currentUser.getAlbumCount();i++) {
				
				if(albumList.getItems().contains(currentUser.getAlbumListed().get(i).getAlbumName())) {
					continue;
				}
				
				else {
					albumList.getItems().add(currentUser.getAlbumListed().get(i).getAlbumName()); 

				}
			}
		}
	}
	
	public void setAlbumDetails(String name) {
		
		albumName.setText(name);
		totalPhotos.setText(Integer.toString(currentUser.getUserAlbums().get(name).totalPhotos));
		earliestPhoto.setText(currentUser.getUserAlbums().get(name).getPhotoList().get(currentUser.getUserAlbums().get(name).getPhotoList().size() - 1).getDate().toString());
		latestPhoto.setText(currentUser.getUserAlbums().get(name).getPhotoList().get(0).getDate().toString());
		
	}
	
	public void deleteAlbum(String albumName) {
		if(currentUser.getUserAlbums().containsKey(albumName)) {
			currentUser.getUserAlbums().remove(albumName); 
			albumList.getItems().remove(albumName); 
			currentUser.decrementAlbumCount();
			popup.setText("Deleted: " + albumName + " successfully!" );
		}
		
		else {
			popup.setText("Album doesn't exist!"); 
		}
	}
}
