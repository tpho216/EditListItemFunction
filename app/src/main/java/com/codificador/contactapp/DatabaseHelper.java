package com.codificador.contactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Seng on 12/7/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "listItems.db";
    static final String TABLE_NAME = "list_items";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_VALUE = "number";
    static final String COLUMN_SEEKBAR_MIN = "seekbar_min";
    static final String COLUMN_SEEKBAR_MAX = "seekbar_max";
    static final String COLUMN_ID = "id";
    static final String CREATE_QUERY = "create table "+TABLE_NAME+"("
                                        + COLUMN_ID+" integer primary key,"
                                        + COLUMN_NAME+" text,"
                                        + COLUMN_VALUE +" text,"
                                        + COLUMN_SEEKBAR_MIN +" integer,"
                                        + COLUMN_SEEKBAR_MAX +" integer" + ")";
    static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertListItem(ListItem newListItem){
        try{
            SQLiteDatabase database = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, newListItem.getName());
            contentValues.put(COLUMN_VALUE, newListItem.getValue());
            contentValues.put(COLUMN_SEEKBAR_MAX, newListItem.getSeekbar_max());
            contentValues.put(COLUMN_SEEKBAR_MIN, newListItem.getSeekbar_min());
            long row = database.insert(TABLE_NAME,null,contentValues);
            return row;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<ListItem> getAllListItems(){
        ArrayList<ListItem> list = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            int value = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_VALUE)));
            long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
            int seekbar_max = Integer.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_SEEKBAR_MAX)));
            int seekbar_min = Integer.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_SEEKBAR_MIN)));
            ListItem listItem = new ListItem(id,name,value,seekbar_max, seekbar_min);
            list.add(listItem);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public int delete(long id){
        SQLiteDatabase database = getWritableDatabase();
        String whereArgs[] = {id+""};
        int rows = database.delete(TABLE_NAME,COLUMN_ID+" = ?",whereArgs);
        return rows;
    }


    public int update(ListItem listItem){
        try{
            SQLiteDatabase database = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, listItem.getName());
            contentValues.put(COLUMN_VALUE, listItem.getValue());
            contentValues.put(COLUMN_SEEKBAR_MAX, listItem.getSeekbar_max());
            contentValues.put(COLUMN_SEEKBAR_MIN, listItem.getSeekbar_min());
            int rows = database.update(TABLE_NAME,contentValues,COLUMN_ID+" =? ",new String[]{listItem.getId()+""});
            return rows;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
}