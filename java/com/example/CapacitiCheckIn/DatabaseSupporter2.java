package com.example.CapacitiCheckIn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseSupporter2 extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="checkinuser.db";
    public static final String TABLE_NAME ="checkinuser";
    public static final String COL_1 ="ID";
    public static final String COL_2 ="name";
    public static final String COL_3 ="surname";
    public static final String COL_4 ="attendance";
    public static final String COL_5 ="programme";


    public DatabaseSupporter2(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +" (ID INTEGER PRIMARY  KEY AUTOINCREMENT,  NAME TEXT, SURNAME TEXT, ATTENDANCE TEXT, PROGRAMME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String surname, String attendance, String programme){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, attendance);
        contentValues.put(COL_5, programme);
        long res = db.insert(TABLE_NAME,null,contentValues);
        if (res == -1)
            return false;
        else
            return  true;
    }

    public Cursor getAttendance(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }

    public Cursor getSkills(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from checkinuser where programme = 'Skills' or programme = 'skills' ",null);
        return res; }

    public Cursor getLearnership(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from checkinuser where programme = 'Learnership' or programme = 'learnership'",null);
        return res; }

    public void delete(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME,null,null);
        database.execSQL("delete from "+ TABLE_NAME);
        database.close();
    }
}
