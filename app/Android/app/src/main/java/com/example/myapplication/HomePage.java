package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import data.UserData;
import model.Album;

public class HomePage extends AppCompatActivity {

    private Button create,rename,delete;
    private ListView albumList;
    private EditText userInput;
    private ArrayList<String> currentAlbums;
    private ArrayAdapter<String> albumAdapter;

    int selectedIndex;
    public static Album albumOpened;
    String selectedAlbum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentAlbums = new ArrayList<String>();

        userInput = (EditText) findViewById(R.id.userInput);
        albumList = (ListView) findViewById(R.id.albumList);

        albumList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selectedIndex = position;
                selectedAlbum = currentAlbums.get(selectedIndex);

            }

        });

        create = (Button) findViewById(R.id.create);
        rename = (Button) findViewById(R.id.rename);
        delete = (Button) findViewById(R.id.deleteLocation);

        albumAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, currentAlbums);


        try{
            if(UserData.firstLaunch()){
                System.out.println("No albums");
            }

            else{
                System.out.println("Albums exist!");
                UserData.setAlbums();
                loadAlbums();
                albumList.setSelection(0);
                selectedAlbum = currentAlbums.get(0);
            }
        }

        catch(Exception a){
            a.printStackTrace();
        }

        albumList.setAdapter(albumAdapter);
    }

    public void openClicked(View V){

        if(!selectedAlbum.isEmpty()){
            System.out.println("Opening " + selectedAlbum);
            albumOpened = UserData.getUserAlbums().get(selectedAlbum);
            Intent intent = new Intent(this,AlbumPage.class);
            startActivity(intent);
        }

        else{
            userInput.setText("Must select an album");
        }
    }

    public void createClicked(View V) throws Exception{

        System.out.println("Clicked!");

        if(!TextUtils.isEmpty(userInput.getText())){

            if(UserData.userAlbums.containsKey(userInput.getText().toString())){
                userInput.setText("Album exists!");
            }

            else {

                Album newAlbum = new Album(userInput.getText().toString());
                UserData.addAlbum(newAlbum);
                currentAlbums.add(newAlbum.getAlbumName());
                albumAdapter.notifyDataSetChanged();
                userInput.setText("Album created!");
                System.out.println(getFilesDir());
                UserData.save(UserData.albumList);

            }
        }

    }

    public void renameClicked(View V) throws Exception{

        if(!TextUtils.isEmpty(userInput.getText())){
            String oldName = currentAlbums.get(selectedIndex);
            currentAlbums.set(selectedIndex,userInput.getText().toString());
            albumAdapter.notifyDataSetChanged();
            UserData.renameAlbum(oldName,userInput.getText().toString());
            //UserData.save(UserData.userAlbums);
            userInput.setText("Album renamed!");

        }

        else{
            userInput.setText("Click on the album and rename it!");
        }
    }

    public void deleteClicked(View V) throws Exception{

        System.out.println("Clicked");
        System.out.println("");

        if(UserData.userAlbums.containsKey(userInput.getText().toString().trim())){
            UserData.deleteAlbum(userInput.getText().toString());
            currentAlbums.remove(userInput.getText().toString());
            albumAdapter.notifyDataSetChanged();
            UserData.save(UserData.albumList);
            userInput.setText("Album deleted!");
        }

        else{
            userInput.setText("Album DNE!");
        }
    }

    public void userInputClicked(View V){
        userInput.getText().clear();
    }

    public void loadAlbums() {

        for(int i = 0; i < UserData.albumList.size(); i++){
            currentAlbums.add(UserData.albumList.get(i).getAlbumName());
            albumAdapter.notifyDataSetChanged();
        }
    }
}
