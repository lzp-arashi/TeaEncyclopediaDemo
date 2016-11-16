package com.example.administrator.teaencyclopediademo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLHelper extends SQLiteOpenHelper {
    // 数据库文件名
    private static final String DB_NAME = "my_database.db";
    // 数据库版本号
    private static final int DB_VERSION = 1;
    // 数据库表名
    public static final String TABLE_NAME = "collect_table";

    public MySQLHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 建表语句
        String sql = "create table if not exists " + TABLE_NAME + "(id varchar primary key, title varchar, source varchar,description varchar,wapthumb varchar,createtime varchar,nickname varchar)";
        // 执行SQL语句
        sqLiteDatabase.execSQL(sql);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
