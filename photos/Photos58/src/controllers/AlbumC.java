package controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import data.UserData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import model.Photo;
import model.User;

public class AlbumC implements Initializable{
	
	@FXML
	public MenuItem returnToAlbums;
	
	@FXML 
	public MenuItem logout; 
	
	@FXML 
	public TilePane preview; 
	
	@FXML
	public Pagination singleView; 
	
	@FXML 
	public ListView<String> photoAttributes;
	
	@FXML 
	public TextField popup; 
	
	int globalIndex; 
	User currentUser = Login.currentUser;
	ArrayList <ImageView> allImages; 
	ArrayList <Image> singleImages; 
	Photo currentPhoto;  

	public AlbumC() {
		
	}
	
	@FXML
	public void transfer() {
		if(currentUser.getUserAlbums().containsKey(popup.getText())) {
			currentUser.getUserAlbums().get(popup.getText()).addPhoto(currentPhoto);
			popup.setText("Photo added to " + popup.getText());
		}
		else {
			popup.setText("No such album to move to");
		}
	}
	
	@FXML
	public void recaption() {
		currentPhoto.setCaption(popup.getText());
		photoAttributes.getItems().clear(); 
		photoAttributes.getItems().add("Photo name: " + currentPhoto.getPhotoName()); 
		photoAttributes.getItems().add("Caption: " + currentPhoto.getCaption());
		photoAttributes.getItems().add("Date Taken: " + currentPhoto.getDate().toString());
	}
	
	@FXML 
	public void addPhotoClicked() {
		
		FileChooser fc = new FileChooser(); 
		File selectedFile = fc.showOpenDialog(null); 
		Image temp;
		ImageView tempI; 
		
		
		if(selectedFile != null) {
			System.out.println(selectedFile.getAbsolutePath());
			if(popup.getText() == null || popup.getText().trim().isEmpty()) {
				popup.setText("Photo must have name");
				return; 
			}
			
			else {
				Photo newPhoto = new Photo(popup.getText(),null,UserC.albumOpened,selectedFile,currentUser.getUsername()); 
				currentUser.getUserAlbums().get(UserC.albumOpened).addPhoto(newPhoto);
				temp = new Image(newPhoto.getPath().toURI().toString()); 
				tempI = new ImageView(temp); 
				allImages.add(tempI); 
				preview.getChildren().add(storeImage(tempI,currentUser.getUserAlbums().get(UserC.albumOpened).getPhotoList().size() - 1)); 
 			}
		}
		else {
			popup.setText("Error loading file");
		}
		
	}
	
	@FXML
	public void logoutClicked() {
		try {
			System.out.println("saved"); 
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
	public void returnToAlbumPage() throws Exception {
		UserData.save(UserData.getUsers());
		ChangeScene nextScene = new ChangeScene("../gui/user.fxml",600,400);
		nextScene.setTitle(currentUser.getUsername() + "'s Albums");
		nextScene.currentScene();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		allImages = new ArrayList<ImageView>();
		singleImages = new ArrayList<Image>(); 
		
		
		System.out.println("album: " + UserC.albumOpened);
		
		if(currentUser.getUserAlbums().get(UserC.albumOpened).getPhotoList().size() != 0) {
			
			System.out.println("Album contains photos"); 
			
			loadImages();
			singleView.setPageCount(currentUser.getUserAlbums().get(UserC.albumOpened).getPhotoList().size());
			singleView.setPageFactory(new Callback<Integer,Node>(){
				@Override
				public Node call(Integer I) {
					return loadPageImages(singleImages.get(I),I); 
				}
			});
			
		}
		
		System.out.println("Error"); 
		
	}
	
	public ImageView loadPageImages(Image p, int index) {
		ImageView temp = new ImageView(p); 
		temp.setFitHeight(120);
		temp.setFitWidth(120); 
		return temp; 
	}
	
	public void loadImages() {
		
		Image temp; 
		ImageView tempI;
		
		System.out.println("Error"); 
		
		System.out.println("Last photo: " + currentUser.getUserAlbums().get(UserC.albumOpened).checkPhoto("yer"));
		
		System.out.println("PC: " + currentUser.getUserAlbums().get(UserC.albumOpened).getPhotoCount());
		System.out.println("PC: " + currentUser.getUserAlbums().get(UserC.albumOpened).getPhotoList().get(0).isStock());

		
		for(int i = 0; i < currentUser.getUserAlbums().get(UserC.albumOpened).getPhotoCount(); i++) {
			
			if(currentUser.getUserAlbums().get(UserC.albumOpened).getPhotoList().get(i).isStock()) {
				System.out.println("Stock");
				temp = new Image(currentUser.getUserAlbums().get(UserC.albumOpened).getPhotoList().get(i).getPath().toString()); 

			}
			
			else {
				temp = new Image(currentUser.getUserAlbums().get(UserC.albumOpened).getPhotoList().get(i).getPath().toURI().toString()); 
			}
			
			System.out.println("path: " + currentUser.getUserAlbums().get(UserC.albumOpened).getPhotoList().get(i).getPath().toString());
			
			singleImages.add(temp);
			tempI = new ImageView(temp); 
			tempI.setFitHeight(120);
			tempI.setFitWidth(120);
			
			allImages.add(new ImageView(temp)); 
			
			
		}
		
		//Load the images to preview
		BorderPane thumbnail; 
		
		for(int i = 0; i < allImages.size();i++) {

			thumbnail = storeImage(allImages.get(i), i); 
			preview.getChildren().add(thumbnail); 
		}
	}
	
	public BorderPane storeImage(ImageView p, int index) {
		globalIndex = index; 
		BorderPane thumbnail; 
		VBox photoData;
		TextField photoName = new TextField(currentUser.getUserAlbums().get(UserC.albumOpened).getPhotoList().get(index).getPhotoName());
		
		photoName.setOnAction(event -> {TextField textField = (TextField) event.getSource();
		String newName = textField.getText().trim(); 
		if(newName.length() != 0) {
			currentUser.getUserAlbums().get(UserC.albumOpened).getPhotoList().get(index).setPhotoName(newName);
		}
		else {
			textField.setText(currentUser.getUserAlbums().get(UserC.albumOpened).getPhotoList().get(index).photoName);
		}
		}); 
		
		photoData = new VBox(4); 
		photoData.getChildren().addAll(p,photoName,new Label(currentUser.getUserAlbums().get(UserC.albumOpened).getPhotoList().get(index).getDate().toString())); 
		thumbnail = new BorderPane(photoData); 
		
		thumbnail.setOnMouseClicked((MouseEvent e) -> {
			currentPhoto = currentUser.getUserAlbums().get(UserC.albumOpened).getPhotoList().get(index); 
			photoAttributes.getItems().clear();
			photoAttributes.getItems().add("Photo name: " + currentUser.getUserAlbums().get(UserC.albumOpened).getPhotoList().get(index).getPhotoName()); 
			photoAttributes.getItems().add("Caption: " + currentUser.getUserAlbums().get(UserC.albumOpened).getPhotoList().get(index).getCaption());
			photoAttributes.getItems().add("Date Taken: " + currentUser.getUserAlbums().get(UserC.albumOpened).getPhotoList().get(index).getDate().toString()); 
		}); 
		
		return thumbnail;

	}
}
