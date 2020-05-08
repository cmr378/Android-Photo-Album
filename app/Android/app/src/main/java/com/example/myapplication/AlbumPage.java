package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import data.UserData;
import model.Photo;

public class AlbumPage extends AppCompatActivity {

    public Uri imageURI;
    private static final int PICK_IMAGE_REQUEST = 1;
    public static HashMap<Uri,Integer> imageIndexes;
    public static ImageView focusedImage;
    public static ArrayList<Uri> images;
    private ArrayList<Uri> albumPhotos;
    public boolean imagesExist;
    public static List <BitPhoto> savedImages;
    private CustomAdapter customAdapter;

    public static Uri transfer;

    public static int selectedIndex;
    private Button upload,remove,display;
    private Toolbar toolbar;
    private GridView gallery;
    private EditText searchTransfer;
    public Bitmap added_photo;

    public String [] predefinedSearches = new String[]{
            "New",
            "St.",
            "Place",
            "St"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_page);

        imageIndexes = new HashMap<>();
        images = new ArrayList<>();
        focusedImage = new ImageView(this);


        upload = findViewById(R.id.upload);
        remove = findViewById(R.id.deleteLocation);
        display = findViewById(R.id.display);
        toolbar = findViewById(R.id.toolbar);
        searchTransfer = findViewById(R.id.searchTransfer);


        albumPhotos = new ArrayList<>();
        gallery = findViewById(R.id.gallery);


        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadClicked();
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(HomePage.albumOpened.getAlbumName());

    }

    public void transferClicked(View V){

        transfer = images.get(selectedIndex);

        if(searchTransfer.getText().toString().isEmpty()){
            searchTransfer.setText("enter destination album");
        }
        else{
            if(UserData.albumList.contains(transfer)){
                for(int i = 0; i < UserData.albumList.size(); i++){
                    if(UserData.albumList.get(i).equals(transfer.toString())){
                        UserData.albumList.get(i).addUri(transfer);
                    }
                }
            }
        }
    }

    private void uploadClicked(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    public void displayClicked(View V){

        System.out.println("Displaying slideshow with centered image");
        System.out.println("URI: " + images.size() + " mainArray: " + albumPhotos.size());
        //UserData.save(UserData.albumList);
        Intent intent = new Intent(this,SlideShow.class);
        startActivity(intent);
    }

    public void searchClicked(View V){

        System.out.println("Test");

        int check = 0;

        if(searchTransfer.getText().toString().isEmpty()){
            searchTransfer.setText("enter destination album");
        }

        else {
            for (int i = 0; i < HomePage.albumOpened.getPhotoList().size(); i++) {
                for (int j = 0; j < predefinedSearches.length; j++) {
                    if (!HomePage.albumOpened.getPhotoList().get(i).getPhotoLocation().isEmpty() && HomePage.albumOpened.getPhotoList().get(i).getPeople().size() != 0) {

                        if (searchTransfer.getText().toString().equals(predefinedSearches[j])) {
                            if (HomePage.albumOpened.getPhotoList().get(i).getPhotoLocation().startsWith(predefinedSearches[i])) {
                                if (check == 0) {
                                    albumPhotos.clear();
                                    check++;
                                }
                                albumPhotos.add(images.get(i));
                                customAdapter.notifyDataSetChanged();
                            }
                        } else if (searchTransfer.getText().toString().equals(HomePage.albumOpened.getPhotoList().get(i).getPhotoLocation())) {
                            if (check == 0) {
                                albumPhotos.clear();
                                check++;
                            }
                            albumPhotos.add(images.get(i));
                            customAdapter.notifyDataSetChanged();
                        } else if (searchTransfer.getText().toString().equals(HomePage.albumOpened.getPhotoList().get(i).getPeople().get(i))) {
                            if (check == 0) {
                                albumPhotos.clear();
                                check++;
                            }
                            albumPhotos.add(images.get(i));
                            customAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
    }

    /**
     * Sets the uploaded image into the imageview and stores it into the gridview
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){

            imageURI = data.getData();
            HomePage.albumOpened.addUri(imageURI);

            if(albumPhotos.isEmpty()){
                customAdapter = new CustomAdapter(this,albumPhotos);
                albumPhotos.add(imageURI);
                images.add(imageURI);
                gallery.setAdapter(customAdapter);
            }

            else{
                albumPhotos.add(imageURI);
                images.add(imageURI);
                gallery.setAdapter(customAdapter);
                customAdapter.notifyDataSetChanged();
            }

            storePhoto(imageURI);
            System.out.println("Array Size: " + albumPhotos.size());
        }
    }

    public void storePhoto(Uri currentUri){

        try{
            added_photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(),currentUri);
        }

        catch(IOException a){
            a.printStackTrace();
        }

        BitPhoto temp = new BitPhoto(added_photo);

        Photo newPhoto = new Photo(currentUri.toString(),temp);

        HomePage.albumOpened.addPhoto(newPhoto);
        UserData.save(UserData.albumList);
        System.out.println("Added: " + newPhoto.getPhoto());

    }

    public void loadImages(){
        for(int i = 0; i < HomePage.albumOpened.getPhotoList().size(); i++){
            savedImages.add(HomePage.albumOpened.getPhotoList().get(i).getPhoto());
        }
    }

}


