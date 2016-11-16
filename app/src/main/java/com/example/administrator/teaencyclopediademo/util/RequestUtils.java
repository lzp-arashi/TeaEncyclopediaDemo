package com.example.administrator.teaencyclopediademo.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestUtils {
    public static String get(String url){
       InputStream inputStream= getInputStream(url);
        if (inputStream!=null) {
            byte b[]=new byte[10 * 1024];
            int len=-1;
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            try {
                while (-1!=(len=inputStream.read(b))){
                    baos.write(b,0,len);
                }
                return baos.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static InputStream getInputStream(String url) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            if (httpURLConnection.getResponseCode()==200) {
                return httpURLConnection.getInputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getBitmap(String url){
        InputStream inputStream = getInputStream(url);
        if (inputStream!=null) {
            return BitmapFactory.decodeStream(inputStream);
        }
        return null;
    }
}
