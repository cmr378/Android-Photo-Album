package model;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.myapplication.BitPhoto;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Photo implements Serializable{

    private static final long serialVersionUID = -8887713468220143815L;
    transient BitPhoto image;
    private Tag photoTag;
    BitPhoto bmp;
    private String caption;

    /**
     * When a new instance is created the photo gets time stamped
     */

    public Photo(String caption, BitPhoto image) {

        photoTag = new Tag();
        System.out.println("caption: " + caption);
        this.image = image;
        this.caption = caption;

    }

    public void addPersonTag(String personName){
        photoTag.addPerson(personName);
    }

    public void addLocation(String locationName){
        photoTag.setLocation(locationName);
    }

    public String getPhotoLocation(){
        return photoTag.getLocation();
    }

    public void deleteLocation(){
        photoTag.deleteLocation();
    }

    public BitPhoto getPhoto(){
        return image;
    }

    public String getCaption(){
        return caption;
    }

    public List<String> getPeople(){
        return photoTag.getPeople();
    }

    public String getPerson(String name){
        return photoTag.getPerson(name);
    }

    public Tag returnTag(){
        return photoTag;
    }


}