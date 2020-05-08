package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context c;
    ArrayList<Uri> images;

    public CustomAdapter(Context c, ArrayList<Uri> images){
        this.c = c;
        this.images = images;

    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView temp;

        if(view == null){

            temp = new ImageView(c);
            temp.setLayoutParams(new GridView.LayoutParams(350,350));
            temp.setScaleType(ImageView.ScaleType.FIT_CENTER);
            temp.setPadding(0,10,5,10);

        }

        else{
            temp = (ImageView)view;
        }

        temp.setTag(Integer.valueOf(i));

        System.out.println("I: " + i);
        AlbumPage.imageIndexes.put(images.get(i),i);
        temp.setImageURI(images.get(i));

        temp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Integer position = (Integer)view.getTag();
                AlbumPage.selectedIndex = position;
                System.out.println(position);
                AlbumPage.focusedImage.setImageURI(AlbumPage.images.get(position));
            }
        });

        return temp;
    }
}
