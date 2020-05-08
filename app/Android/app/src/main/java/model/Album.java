package model;

import android.net.Uri;

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
    private ArrayList<String> uri;
    private String albumName;
    public int totalPhotos;


    public Album(String albumName) {
        uri = new ArrayList<String>();
        this.albumName = albumName;
        photos = new HashMap<>();
        listedPhotos = new ArrayList<>();
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String newName) {
        this.albumName = newName;
    }

    public void addPhoto(Photo newPhoto) {
        photos.put(newPhoto.getCaption(),newPhoto);
        listedPhotos.add(newPhoto);
        totalPhotos++;
    }

    public void setPhotos(HashMap<String,Photo> temp){
        this.photos = temp;
    }

    public void setPhotoList(ArrayList<Photo> temp){
        this.listedPhotos = temp;
    }

    public HashMap<String,Photo> getPhotos(){
        return photos;
    }

    public ArrayList<Photo> getPhotoList() {
        return listedPhotos;
    }

    public void addUri(Uri newUri){
        uri.add(newUri.toString());
    }

    public ArrayList<String> getAllUri(){
        return uri;
    }

    public String getUri(Uri target) {
        if (uri.contains(target.toString())) {
            for (int i = 0; i < uri.size(); i++) {
                if (uri.get(i).equals(target.toString())) {
                    return uri.get(i).toString();
                }
            }
        }
        return "";
    }


    public void deletePhoto(String photoName) {
        if(photos.containsKey(photoName)) {
            listedPhotos.remove(photos.get(photoName));
            photos.remove(photoName);
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

}
