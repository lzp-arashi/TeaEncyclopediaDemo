package com.example.administrator.teaencyclopediademo.util.cache.impl;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.administrator.teaencyclopediademo.util.cache.ImageCache;

public class DoubleCache implements ImageCache {
    private ImageCache discCache;
    private ImageCache memoryCache;

    public DoubleCache(int memorySize,String cacheDir) {
        this.discCache = new DiskCache(cacheDir);
        this.memoryCache = new MemoryImg(memorySize);
    }

    public DoubleCache(Context context) {
        this.discCache = new DiskCache(context);
        this.memoryCache = new MemoryImg(10<<20);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        memoryCache.putBitmap(url,bitmap);
        discCache.putBitmap(url,bitmap);
    }
    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bitmap = memoryCache.getBitmap(url);
        if(bitmap!=null){
            return bitmap;
        }
        else {
            bitmap = discCache.getBitmap(url);
            if(bitmap!=null)
            {
                memoryCache.putBitmap(url,bitmap);
            }
        }
        return bitmap;
    }
}
