package com.example.administrator.teaencyclopediademo.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.administrator.teaencyclopediademo.R;
import com.example.administrator.teaencyclopediademo.util.cache.ImageCache;
import com.example.administrator.teaencyclopediademo.util.cache.impl.MemoryImg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BitmapUtils {
    private ExecutorService executorService;
    private static BitmapUtils bitmapUtils;
    private ImageCache imageCache;

    public void setImageCache(MemoryImg imageCache) {
        this.imageCache = imageCache;
    }

    public BitmapUtils() {
        executorService = Executors.newFixedThreadPool(4);
    }
    public static BitmapUtils getInstene(){
        if(bitmapUtils==null){
            bitmapUtils=new BitmapUtils();
        }
        return  bitmapUtils;
    }
    public void loadImg(final String url, final ImageView imageView){
        imageView.setTag(url);
        if(imageCache!=null){
            Bitmap bitmap=imageCache.getBitmap(url);
            if(bitmap!=null){
                    imageView.setImageBitmap(bitmap);
                    return;
            }
        }
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap=RequestUtils.getBitmap(url);
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        String tag= (String) imageView.getTag();
                        if(tag.equals(url)&&url!=null&&bitmap!=null){
                            imageView.setImageBitmap(bitmap);
                            imageCache.putBitmap(url, bitmap);
                        }else{
                            imageView.setImageResource(R.drawable.defaultcovers);
                        }
                    }
                });
            }
        });
    }

}
