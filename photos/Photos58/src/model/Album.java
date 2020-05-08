package model;

import java.util.Collections;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Comparator;

public class Album implements Serializable{
	
	private static final long serialVersionUID = -5470434596276717927L;
	private HashMap<String,Photo> photos; 
	private ArrayList<Photo> listedPhotos; 
	private String albumName; 
	public int totalPhotos;
	public Date earliestPhoto; 
	public Date latestPhoto; 
	
	
	public Album(String albumName) {
		this.albumName = albumName; 
		photos = new HashMap<String,Photo>(); 
		listedPhotos = new ArrayList<Photo>(); 
	}
	
	public String getAlbumName() {
		return albumName; 
	}
	
	public void setAlbumName(String newName) {
		albumName = newName; 
	}
	
	public void addPhoto(Photo newPhoto) {
		sortPhotosByDate(); 
		photos.put(newPhoto.getPhotoName(),newPhoto); 
		listedPhotos.add(newPhoto);
		totalPhotos++; 
	}
	
	public ArrayList<Photo> getPhotoList() {
		return listedPhotos; 
	}
	
	public void deletePhoto(String photoName) {
		if(photos.containsKey(photoName)) {
			listedPhotos.remove(photos.get(photoName));
			photos.remove(photoName);
			sortPhotosByDate(); 
			totalPhotos--; 
		}
		else {
			System.out.println("Photo doesn't exist");
		}
	}
	
	public boolean checkPhoto(String photoName) {
		if(photos.containsKey(photoName)) {
			return true;
		}
		
		else {
			return false; 
		}
	}
	
	public Photo getPhoto(String photoName) {
		return photos.get(photoName);
	}
	
	public int getPhotoCount() {
		return totalPhotos;
	}
	
	
	public void sortPhotosByDate() {
		
		Collections.sort(listedPhotos,new Comparator<Photo>() {
			public int compare(Photo p1, Photo p2) {
				return Long.valueOf(p2.getDate().getTime()).compareTo(p1.getDate().getTime()); 
			}
		}); 
		
	}

}
