package com.example.administrator.teaencyclopediademo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HistorySQL extends SQLiteOpenHelper {
    // 数据库文件名
    private static final String H_NAME = "my_data.db";
    // 数据库版本号
    private static final int H_VERSION = 2;
    // 数据库表名
    public static final String HISTORY_TABLE_NAME = "history_table";

    public HistorySQL(Context context) {
        super(context, H_NAME, null, H_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 建表语句
        String sql = "create table if not exists " + HISTORY_TABLE_NAME + "(id varchar primary key, title varchar, source varchar,description varchar,wapthumb varchar,createtime varchar,nickname varchar)";
        // 执行SQL语句
        sqLiteDatabase.execSQL(sql);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
