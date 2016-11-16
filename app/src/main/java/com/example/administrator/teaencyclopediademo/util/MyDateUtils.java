package com.example.administrator.teaencyclopediademo.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.example.administrator.teaencyclopediademo.bean.PageEntity;

import java.util.ArrayList;
import java.util.List;


public class MyDateUtils {
    private static MyDateUtils myDateUtils;
    private static HistorySQL historySQL;
    private Handler handler;
    public MyDateUtils(Context context) {
        historySQL=new HistorySQL(context);
        handler=new Handler();
    }
    public static MyDateUtils getInerence(Context context){
        if(myDateUtils==null){
            myDateUtils=new MyDateUtils(context);
        }
        return myDateUtils;
    }
    public void insertData(PageEntity.PageDetial detial){
        SQLiteDatabase db=historySQL.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put("id",detial.getId());
        values.put("title",detial.getTitle());
        values.put("source",detial.getSource());
        values.put("description",detial.getDescription());
        values.put("wapthumb",detial.getWap_thumb());
        values.put("createtime",detial.getCreate_time());
        values.put("nickname",detial.getNickname());
        db.insert(HistorySQL.HISTORY_TABLE_NAME,null,values);
        db.close();
    }
    public void getDetial(final ListenerDB listenerDB){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db=historySQL.getReadableDatabase();
                Cursor cursor=db.query(HistorySQL.HISTORY_TABLE_NAME,null,null,null,null,null,null);
                int idIndex=cursor.getColumnIndex("id");
                int titleIndex=cursor.getColumnIndex("title");
                int sourceIndex=cursor.getColumnIndex("source");
                int descriptionIndex=cursor.getColumnIndex("description");
                int wapthumbIndex=cursor.getColumnIndex("wapthumb");
                int createtimeIndex=cursor.getColumnIndex("createtime");
                int nicknameIndex=cursor.getColumnIndex("nickname");
                final List<PageEntity.PageDetial> detials=new ArrayList<PageEntity.PageDetial>();
                while (cursor.moveToNext()){
                    PageEntity.PageDetial detial=new PageEntity().getPage();
                    detial.setId(cursor.getString(idIndex));
                    detial.setTitle(cursor.getString(titleIndex));
                    detial.setSource(cursor.getString(sourceIndex));
                    detial.setDescription(cursor.getString(descriptionIndex));
                    detial.setWap_thumb(cursor.getString(wapthumbIndex));
                    detial.setCreate_time(cursor.getString(createtimeIndex));
                    detial.setNickname(cursor.getString(nicknameIndex));
                    detials.add(detial);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listenerDB.onresponse(detials);
                    }
                });
            }
        }).start();
    }
    public interface ListenerDB{
        public void onresponse(List<PageEntity.PageDetial> detials);
    }
    public static  List<PageEntity.PageDetial> getShuju2(){
        List<PageEntity.PageDetial> myData = new ArrayList<>();
        SQLiteDatabase db=historySQL.getReadableDatabase();
        Cursor cursor=db.query(HistorySQL.HISTORY_TABLE_NAME,null,null,null,null,null,null);
        int idIndex=cursor.getColumnIndex("id");
        int titleIndex=cursor.getColumnIndex("title");
        int sourceIndex=cursor.getColumnIndex("source");
        int descriptionIndex=cursor.getColumnIndex("description");
        int wapthumbIndex=cursor.getColumnIndex("wapthumb");
        int createtimeIndex=cursor.getColumnIndex("createtime");
        int nicknameIndex=cursor.getColumnIndex("nickname");

        while (cursor.moveToNext()){
            PageEntity.PageDetial detial=new PageEntity().getPage();
            detial.setId(cursor.getString(idIndex));
            detial.setTitle(cursor.getString(titleIndex));
            detial.setSource(cursor.getString(sourceIndex));
            detial.setDescription(cursor.getString(descriptionIndex));
            detial.setWap_thumb(cursor.getString(wapthumbIndex));
            detial.setCreate_time(cursor.getString(createtimeIndex));
            detial.setNickname(cursor.getString(nicknameIndex));
            myData.add(detial);
        }
        return  myData;

    }
}