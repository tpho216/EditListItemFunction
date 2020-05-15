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
    static final String TABLE_NAME = "contact";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_NUMBER = "number";
    static final String COLUMN_ID = "id";
    static final String CREATE_QUERY = "create table "+TABLE_NAME+"("
                                        + COLUMN_ID+" integer primary key,"
                                        + COLUMN_NAME+" text,"
                                        + COLUMN_NUMBER+" text)";
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

    public long insertContact(ListItem newListItem){
        try{
            SQLiteDatabase database = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, newListItem.getName());
            contentValues.put(COLUMN_NUMBER, newListItem.getNumber());
            long row = database.insert(TABLE_NAME,null,contentValues);
            return row;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<ListItem> getAllContacts(){
        ArrayList<ListItem> list = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String number = cursor.getString(cursor.getColumnIndex(COLUMN_NUMBER));
            long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
            ListItem listItem = new ListItem(id,name,number);
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
            contentValues.put(COLUMN_NUMBER, listItem.getNumber());
            int rows = database.update(TABLE_NAME,contentValues,COLUMN_ID+" =? ",new String[]{listItem.getId()+""});
            return rows;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
}