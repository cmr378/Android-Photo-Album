package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable{

	private static final long serialVersionUID = -6232403788131821496L;
	private String username;
	private HashMap<String,Album> albumList;
	private ArrayList<Album> listedAlbums; 
	public int albumCount; 

	
	/**
	 * Initiates a new instance of a User, a user name is needed
	 * The user then gets its own list of all albums
	 * @param username
	 */
	
	public User(String username) {
		this.username = username;
		albumList = new HashMap<String,Album>();
		listedAlbums = new ArrayList<Album>(); 
		albumCount = 0; 
	}
	
	public String getUsername() {
		return username; 
	}
	
	public void decrementAlbumCount() {
		albumCount--; 
	}
	
	/**
	 * Returns all albums the user owns
	 * @return
	 */
	
	public HashMap<String,Album> getUserAlbums(){
		return albumList; 
	}
	
	/**
	 * Adds a new album to the list of user owned albums
	 * @param newAlbum - the new Album created by the user
	 */
	
	public void addAlbum(Album newAlbum) {
		albumList.put(newAlbum.getAlbumName(),newAlbum); 
		listedAlbums.add(newAlbum); 
		albumCount++; 
	}
	
	public int getAlbumCount() {
		return albumCount;
	}
	
	public ArrayList<Album> getAlbumListed() {
		return listedAlbums; 
	}

}
