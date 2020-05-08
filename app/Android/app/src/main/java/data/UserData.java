package data;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import model.Album;

public class UserData implements Serializable {

    private static final long serialVersionUID = 3004498425663499418L;
    public static HashMap<String,Album> userAlbums = new HashMap<>();
    public static ArrayList<Album> albumList = new ArrayList<>();
    private static File data = new File("/data/user/0/com.example.myapplication/files/data.dat");
    public UserData(){
    }

    public static void addAlbum(Album newAlbum) throws Exception{

        System.out.println("Attempting to add: " + newAlbum.getAlbumName());

        if(!userAlbums.containsKey(newAlbum.getAlbumName())){
            userAlbums.put(newAlbum.getAlbumName(),newAlbum);
            albumList.add(newAlbum);
        }
        displayAlbums();
    }

    public static void deleteAlbum(String target){
        if(userAlbums.containsKey(target)){
            userAlbums.remove(target);
            albumList.remove(target);
        }

        displayAlbums();
    }

    public static void renameAlbum (String target,String newName) throws Exception{
        if(userAlbums.containsKey(target)){

            Album renamedAlbum = new Album(newName);
            renamedAlbum.setPhotos(userAlbums.get(target).getPhotos());
            renamedAlbum.setPhotoList(userAlbums.get(target).getPhotoList());

            userAlbums.remove(target);
            albumList.remove(target);

            addAlbum(renamedAlbum);
        }
    }

    public static HashMap<String,Album> getUserAlbums(){
        return userAlbums;
    }

    public static void save(ArrayList<Album> userAlbums){

        try{
            FileOutputStream fos = new FileOutputStream(data);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(albumList);
            oos.close();
            fos.close();
            System.out.println("Saved");
        }

        catch(IOException a){
            a.printStackTrace();
        }

    }

    public static ArrayList<Album> load() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(data));
        albumList = (ArrayList<Album>)ois.readObject();
        ois.close();
        return albumList;
    }

    public static void setAlbums() throws FileNotFoundException, ClassNotFoundException, IOException {
        albumList = load();
        storeAlbums();
        displayAlbums();
    }

    public static boolean firstLaunch(){

        if(data.length() == 0){
            return true;
        }

        else{
            return false;
        }
    }

    public static void storeAlbums(){

        for(int i = 0; i < albumList.size(); i++){
            userAlbums.put(albumList.get(i).getAlbumName(),albumList.get(i));
            System.out.println("loaded: " + albumList.get(i).getAlbumName());
        }
    }

    public static void displayAlbums(){
        System.out.println("Current albums:");
        for(int i = 0; i < albumList.size(); i++){
            System.out.println(albumList.get(i).getAlbumName());
        }
    }
}
