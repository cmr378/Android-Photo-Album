package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BitPhoto {

    Bitmap bitmap;

    public BitPhoto(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        ByteArrayOutputStream stream = null;
        stream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream);

        byte bytes[] = stream.toByteArray();

        bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
        ByteArrayOutputStream stream  = new ByteArrayOutputStream();
        int b;
        while((b = in.read()) != -1){
            stream.write(b);
        }
        byte bytes[] = stream.toByteArray();
        bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

}
