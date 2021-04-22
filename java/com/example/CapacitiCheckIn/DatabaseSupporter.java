package com.example.CapacitiCheckIn;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.os.Build.ID;


public class DatabaseSupporter extends SQLiteOpenHelper{
    public static final String DATABASE_NAME ="register.db";
    public static final String TABLE_NAME ="registeruser";
    public static final String COL_1 ="ID";
    public static final String COL_2 ="email";
    public static final String COL_3 ="password";



    public DatabaseSupporter(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME +" (ID INTEGER PRIMARY  KEY AUTOINCREMENT, EMAIL TEXT, PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }



    public long addUser(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
//
        long res = db.insert("registeruser",null,contentValues);
        db.close();
        return  res;
    }

    public boolean checkUser(String email, String password){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = { email, password };
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }
    public boolean checkEmail(String email){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?";
        String[] selectionArgs = { email };
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }


    public Cursor getEmail(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from registeruser",null);
        return res;
    }

    public void deleteEmails(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME,null,null);
        database.execSQL("delete from "+ TABLE_NAME);
        database.close();
    }
    public void deleteSpecificEmails(String email){

        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, COL_2 + "=? ", new String[] {email});

    }
}

