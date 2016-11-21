package com.example.administrator.teaencyclopediademo.ui;

import android.app.Application;
import android.graphics.Bitmap;

import com.example.administrator.teaencyclopediademo.R;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //对UIL进行初始化
        ImageLoaderConfiguration.Builder builder =
                new ImageLoaderConfiguration.Builder(this);
        //设置
        builder.threadPoolSize(4);//线程池中线程的数量

        DiskCache diskCache = null;
        try {
            diskCache = new LruDiskCache(this.getExternalCacheDir(),new Md5FileNameGenerator(),1024*1024*256);
        } catch (IOException e) {
            e.printStackTrace();
        }
        builder.diskCache(diskCache);//磁盘缓存

        int maxSize = (int) (Runtime.getRuntime().maxMemory()/8);
        MemoryCache memoryCache = new LruMemoryCache(maxSize);
        builder.memoryCache(memoryCache);//内存缓存

        DisplayImageOptions.Builder optionsBuilder = new DisplayImageOptions.Builder();

        //设置
        optionsBuilder.bitmapConfig(Bitmap.Config.RGB_565);//内存占用少一些

        optionsBuilder.cacheInMemory(true);
        optionsBuilder.cacheOnDisk(true);//设置是否进行磁盘缓存
        optionsBuilder.showImageOnFail(R.drawable.defaultcovers);

        optionsBuilder.showImageOnLoading(android.R.drawable.btn_star);

        optionsBuilder.showImageForEmptyUri(android.R.drawable.btn_plus);

        DisplayImageOptions options = optionsBuilder.build();
        builder.defaultDisplayImageOptions(options);//设置图片展示的参数


        ImageLoaderConfiguration configuration = builder.build();
        ImageLoader.getInstance().init(configuration);
    }
}
