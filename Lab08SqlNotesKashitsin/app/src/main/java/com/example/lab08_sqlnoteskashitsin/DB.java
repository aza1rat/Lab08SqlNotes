package com.example.lab08_sqlnoteskashitsin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper{
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) { //Кашицын,393
        String sql = "CREATE TABLE Notes (id INT, txt TEXT);";
        sqlDB.execSQL(sql);
    }

    public int getMaxId()
    {
        String sql = "SELECT MAX(id) FROM  Notes;";
        SQLiteDatabase sqlDB  = getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery(sql,null);
        if (cursor.moveToFirst())
            return cursor.getInt(0);
        return 0;
    }

    public void addNote(int id, String txt)
    {
        String sql = "INSERT INTO Notes VALUES (" + String.valueOf(id) + ", '" + txt + "');";
        SQLiteDatabase sqlDB = getWritableDatabase();
        sqlDB.execSQL(sql);
    }

    public void saveNote(int idNote, String txtNote)
    {
        String sql = "UPDATE Notes SET txt = '" + txtNote + "' WHERE id ='" + String.valueOf(idNote) + "';";
        SQLiteDatabase sqlDB = getWritableDatabase();
        sqlDB.execSQL(sql);
    }

    public void deleteNote(int idNote)
    {
        String sql = "DELETE FROM Notes WHERE id= '" + String.valueOf(idNote) + "';";
        SQLiteDatabase sqlDB = getWritableDatabase();
        sqlDB.execSQL(sql);
    }

    public String getNote(int id)
    {
        String sql = "SELECT txt FROM  Notes WHERE id = " + String.valueOf(id) + ";";
        SQLiteDatabase sqlDB = getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery(sql,null);
        if (cursor.moveToFirst())
            return cursor.getString(0);
        return"";
    }

    public void getAllNotes(ArrayList<Note> list)//Кашицын,393
    {
        String sql = "SELECT id, txt FROM Notes;";
        SQLiteDatabase sqlDB = getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery(sql,null);
        if (cursor.moveToFirst() == true)
        {
            do {
                Note note = new Note();
                note.id = cursor.getInt(0);
                note.txt = cursor.getString(1);
                list.add(note);
            }while (cursor.moveToNext());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
