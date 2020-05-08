package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class SlideShow extends AppCompatActivity {

    private ImageView slideImage;
    private EditText personTag;
    private EditText locationTag;

    int currentIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        slideImage = findViewById(R.id.slideImage);
        personTag = findViewById(R.id.personTag);
        locationTag = findViewById(R.id.locationTag);

        currentIndex = 0;
        slideImage.setImageURI(AlbumPage.images.get(AlbumPage.selectedIndex));
        setTags();
    }

    public void setTags(){

        String allPeople = "";

        if(HomePage.albumOpened.getPhotoList().get(currentIndex).returnTag().checkIfLocationEmpty() && HomePage.albumOpened.getPhotoList().get(currentIndex).returnTag().checkIfPeopleEmpty()){
            return;
        }

        else{

            for(int i = 0; i < HomePage.albumOpened.getPhotoList().get(currentIndex).getPeople().size(); i++){

                if(i == 0){
                    allPeople +=  HomePage.albumOpened.getPhotoList().get(currentIndex).getPeople().get(i);
                }
                else{
                    allPeople += "," + HomePage.albumOpened.getPhotoList().get(currentIndex).getPeople().get(i);
                }
            }

            personTag.setText("People: " + allPeople);
            locationTag.setText("Location: " + HomePage.albumOpened.getPhotoList().get(currentIndex).getPhotoLocation());

        }
    }

    public void addPersonClicked(View V){

        if(personTag.getText().toString().isEmpty()){
            personTag.setText("Must input tag value");
        }

        else{
            HomePage.albumOpened.getPhotoList().get(currentIndex).addPersonTag(personTag.getText().toString());
            setTags();
        }
    }

    public void deletePersonClicked(View V){

        if(!personTag.getText().toString().isEmpty()){
            if(HomePage.albumOpened.getPhotoList().get(currentIndex).getPeople().contains(personTag.getText().toString())){
                HomePage.albumOpened.getPhotoList().get(currentIndex).getPeople().remove(personTag.getText().toString());
                setTags();
            }
        }

        else{
            personTag.setText("Must input tag value; ");
        }
    }

    public void addLocationClicked(View V){

        if(locationTag.getText().toString().isEmpty()){
            locationTag.setText("Must input tag value");
        }

        else{

            if(HomePage.albumOpened.getPhotoList().get(currentIndex).getPhotoLocation().isEmpty() || HomePage.albumOpened.getPhotoList().get(currentIndex).getPhotoLocation() == null){
                HomePage.albumOpened.getPhotoList().get(currentIndex).addLocation(locationTag.getText().toString());
                setTags();
            }

            else{
                locationTag.setText("Photo has location");
            }
        }
    }

    public void deleteLocationClicked(View V){
        if(!locationTag.getText().toString().isEmpty()){
            HomePage.albumOpened.getPhotoList().get(currentIndex).deleteLocation();
            locationTag.setText("Location: ");
        }
        else{
            locationTag.setText("Must enter tag value");
        }
    }

    public void nextClicked(View V){

        if(currentIndex + 1 < AlbumPage.images.size()){
            currentIndex++;
        }

        else{
            currentIndex = 0;
        }
        slideImage.setImageURI(AlbumPage.images.get(currentIndex));
    }

    public void previousClicked(View V){

        if(currentIndex - 1 >= 0){
            currentIndex--;
        }
        else {
            currentIndex = AlbumPage.images.size() - 1;
        }
        slideImage.setImageURI(AlbumPage.images.get(currentIndex));
    }

    public void clearText(View V){
        locationTag.getText().clear();
        personTag.getText().clear();
    }
}
