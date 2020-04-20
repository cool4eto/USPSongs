package com.example.uspsongs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Song.db";
    public static final String TABLE_NAME="Song_table";
    public static final String COL1="Id";
    public static final String COL2="name";
    public static final String COL3="author";
    public static final String COL4="duration";
    public static final String COL5="year";
    public static final String COL6="genre";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (Id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, AUTHOR TEXT, DURATION INTEGER, YEAR INTEGER, GENRE TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME );
        onCreate(db);
    }
    public boolean insertSong(String name, String author, String duration, String year, String genre)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL2,name);
        contentValues.put(COL3,author);
        contentValues.put(COL4,duration);
        contentValues.put(COL5,year);
        contentValues.put(COL6,genre);
        long res = db.insert(TABLE_NAME,null,contentValues);
        if(res==-1)
        {
            return false;
        }
        else return true;
    }
    public Cursor getAllSongs()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT* from "+TABLE_NAME,null);
        return res;
    }
    public boolean updateSong(String id, String name,String author, String duration, String year, String genre)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL1,id);
        contentValues.put(COL2,name);
        contentValues.put(COL3,author);
        contentValues.put(COL4,duration);
        contentValues.put(COL5,year);
        contentValues.put(COL6,genre);
        db.update(TABLE_NAME,contentValues,"Id="+id,null);
        return true;
    }
    public boolean deleteSong(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,"Id="+id,null);
        return true;
    }
}
