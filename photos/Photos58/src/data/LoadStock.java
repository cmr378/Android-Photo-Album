package data;

import java.io.File;

import controllers.Login;
import model.Album;
import model.Photo;

public class LoadStock {

	public LoadStock() {
	}
	
	public static Photo s1,s2,s3,s4,s5; 
	public static int check = 0;
	public static final int alreadyLoggedOn = 1; 
	public static Album stockAlbum = new Album("stock"); 
	
	public static void loadPhotos() {
		Login.newUser.addAlbum(stockAlbum);
		s1 = new Photo("Random","A random guy","stock",new File("/stock/random.jpeg"),Login.newUser.getUsername()); 
		s1.setStock(true);
		Login.newUser.getUserAlbums().get("stock").addPhoto(s1);
		s2 = new Photo("Popcorn","Reverse popcorn?","stock",new File("/stock/popcorn.jpeg"),Login.newUser.getUsername());
		s2.setStock(true);
		Login.newUser.getUserAlbums().get("stock").addPhoto(s2);
		s3 = new Photo("Horse","Funny horse","stock",new File("/stock/horse.jpeg"),Login.newUser.getUsername()); 
		s3.setStock(true);
		Login.newUser.getUserAlbums().get("stock").addPhoto(s3);
		s4 = new Photo("Duck","Funny duck","stock",new File("/stock/duck.jpeg"),Login.newUser.getUsername()); 
		s4.setStock(true);
		Login.newUser.getUserAlbums().get("stock").addPhoto(s4);
		s5 = new Photo("Cat","Floating cat","stock", new File("/stock/cat.jpeg"),Login.newUser.getUsername());
		s5.setStock(true);
		Login.newUser.getUserAlbums().get("stock").addPhoto(s5);
		Login.newUser.getUserAlbums().get("stock").totalPhotos = 5; 
	}
	
	public static boolean checkLogin() {
		if(check == alreadyLoggedOn) {
			return true;
		}
		else {
			return false; 
		}
	}
}
