package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Photo implements Serializable{

	private static final long serialVersionUID = -8887713468220143815L;
	public String photoName; 
	private String caption;
	private String albumName;
	private String username; 
	public File photo; 
	private HashMap<String,Tag> tags; 
	private ArrayList<String> albumsApartOf; // all the albums the photo is apart of
	private Calendar timeStamp; 
	private Date photoDate; 
	public String path; 
	public boolean isStock; 
	
	/**
	 * When a new instance is created the photo gets time stamped 
	 * @param photoName - name of photo
	 * @param caption - caption of the photo
	 * @param albumName - name of the album which it belongs
	 * @param username - name of the user who owns the photo
	 * @param photo - the photo itself
	 */

	public Photo(String photoName, String caption, String albumName, File photo, String username) {
		this.photoName = photoName;
		this.caption = caption;
		this.albumName = albumName;
		this.photo = photo; 
		this.username = username;
		tags = new HashMap<String,Tag>();
		albumsApartOf = new ArrayList<String>();
		albumsApartOf.add(albumName); 
		timeStamp = new GregorianCalendar();
		timeStamp.set(Calendar.MILLISECOND,0); 
		photoDate = timeStamp.getTime(); 
	}
	
	public boolean isStock() {
		return isStock; 
	}
	
	public void setStock(boolean is) {
		isStock = is; 
	}
	
	public Date getDate() {
		return photoDate; 
	}
	
	public String getPhotoName() {
		return photoName;
	}
	
	public void setPhotoName(String newName) {
		photoName = newName; 
	}
	
	public String getCaption() {
		return caption; 
	}
	
	public File getPath() {
		return photo;
	}
	
	public void setCaption(String newCaption) {
		caption = newCaption; 
	}
	
	public ArrayList<String> showCurrentAlbums() {
		return albumsApartOf; 
	}
	
	public void addAlbum(String albumName) {
		albumsApartOf.add(albumName); 
	}
	
	public void deleteAlbum(String albumName) {
		albumsApartOf.remove(albumName); 
	}
	
	public String getPhotosUser() {
		return username; 
	}
	
}
