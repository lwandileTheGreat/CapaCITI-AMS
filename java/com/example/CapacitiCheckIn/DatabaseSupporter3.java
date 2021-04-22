package com.example.CapacitiCheckIn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseSupporter3 extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="addemailuser.db";
    public static final String TABLE_NAME ="addemail";
    public static final String COL_1 ="ID";
    public static final String COL_2 ="email";


    public DatabaseSupporter3(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + TABLE_NAME +" (ID INTEGER PRIMARY  KEY AUTOINCREMENT,  EMAIL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
    public long addEmail(String email){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
//
        long result = database.insert("addemail",null,contentValues);
        database.close();
        return  result;
    }

    public boolean validateEmail(String email){
        String[] columns = { COL_1 };
        SQLiteDatabase database = getReadableDatabase();
        String selection = COL_2 + "=?";
        String[] selectionArgs = { email };
        Cursor cursor = database.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        database.close();

        if(count>0)
            return  true;
        else
            return  false;
    }
    public void deleteEmails(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME,null,null);
        database.execSQL("delete from "+ TABLE_NAME);
        database.close();
    }
    public void deleteSpecificEmails(String email) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, COL_2 + "=? ", new String[] {email});
    }
    public Cursor getEmail(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from addemail",null);
        return res;
    }

}
