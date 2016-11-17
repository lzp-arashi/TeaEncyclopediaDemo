package com.example.administrator.teaencyclopediademo.util.cache.impl;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.example.administrator.teaencyclopediademo.util.cache.ImageCache;

public class MemoryImg implements ImageCache {
    private LruCache<String,Bitmap> lruCache;

    public MemoryImg(int memorySize) {
        this.lruCache=new LruCache<String,Bitmap>(memorySize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {

        lruCache.put(url,bitmap);
    }

    @Override
    public Bitmap getBitmap(String url) {
        return lruCache.get(url);
    }
}
