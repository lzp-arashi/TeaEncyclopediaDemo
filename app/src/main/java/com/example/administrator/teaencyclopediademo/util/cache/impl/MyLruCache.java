package com.example.administrator.teaencyclopediademo.util.cache.impl;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Administrator on 2016/11/17.
 */
public class MyLruCache extends LruCache<String,Bitmap>{

    public MyLruCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes()*value.getHeight();
    }
}
