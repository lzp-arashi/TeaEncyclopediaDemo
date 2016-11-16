package com.example.administrator.teaencyclopediademo.util.cache;

import android.graphics.Bitmap;

public interface ImageCache {
    public void putBitmap(String url, Bitmap bitmap);
    public Bitmap getBitmap(String url);
}
