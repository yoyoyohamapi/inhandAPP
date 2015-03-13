package com.inhand.milk.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * DataBaseHelper
 * Desc:SQLite数据库帮助类
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-03
 * Time: 15:55
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    //数据库名
    private static final String DB_NAME = "inhand_milk.db";
    //版本维护
    private static final int version = 1;
    //每张表只包含UUID及JSON列
    private static final String COLUMN_JSON = "json";
    private static final String COLUMN_UUID = "uuid";
    //数据表列表
    private static final String[] TB_NAMES = new String[]{
            "album"
    };

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        创建数据表
         */
        for (int i = 0; i < TB_NAMES.length; i++) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TB_NAMES[i]
                    + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_JSON + " TEXT," + COLUMN_UUID + " VARCHAR)");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 插入方法
     *
     * @param table 表名
     * @param obj   待插入对象
     * @param uuid  uuid
     */
    public void insert(String table, String obj, String uuid) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("INSERT INTO " + table + " VALUES (NULL, ?, ?)", new Object[]{
                obj, uuid
        });
        db.close();
    }


    /**
     * 获得所有行
     *
     * @param table 表名
     * @return 所有项目列表
     */
    public ArrayList<String> findAll(String table) {
        ArrayList<String> list = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table, null, null, null, null, null, null);
        if (cursor != null) {
            list = new ArrayList<>();
            while (cursor.moveToNext()) {
                String json = cursor.getString(
                        cursor.getColumnIndex(COLUMN_JSON)
                );
                list.add(json);
            }
        }
        return list;
    }
}
