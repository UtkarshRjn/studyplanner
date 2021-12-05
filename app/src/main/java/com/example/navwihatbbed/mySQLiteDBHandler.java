package com.example.navwihatbbed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class mySQLiteDBHandler  extends SQLiteOpenHelper {

    public static final String EVENT_TABLE = "EVENT_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_EVENT_TITLE = "EVENT_TITLE";
    public static final String COLUMN_EVENT_DATE = "EVENT_DATE";
    public static final String COLUMN_EVENT_TIME = "EVENT_TIME";
    public static final String COLUMN_EVENT_TYPE = "EVENT_TYPE";
    public static final String COLUMN_EVENT_DESCRIPTION = "EVENT_DESCRIPTION";

    public mySQLiteDBHandler(@Nullable Context context) {
        super(context, "event.db", null, 1);
    }

    // this is called the first time a database is accessed. There should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + EVENT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EVENT_TITLE + " TEXT, " + COLUMN_EVENT_DATE + " TEXT, " + COLUMN_EVENT_TIME + " TEXT, " + COLUMN_EVENT_TYPE + " TEXT, " + COLUMN_EVENT_DESCRIPTION + " TEXT)";

        db.execSQL(createTableStatement);
    }

    // this ia called if the database version number changes. It prevents previous users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(EventModel eventModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EVENT_TITLE, eventModel.getTitle());
        cv.put(COLUMN_EVENT_TYPE, eventModel.getType());
        cv.put(COLUMN_EVENT_TIME, eventModel.getTime());
        cv.put(COLUMN_EVENT_DATE, eventModel.getDate());
        cv.put(COLUMN_EVENT_DESCRIPTION, eventModel.getDescription());

        long insert = db.insert(EVENT_TABLE, null, cv);
        return insert != -1;
    }

    public List<EventModel> getType(String type) {
        List<EventModel> returnList = new ArrayList<>();

//        get data from the database

        String queryString = "SELECT * FROM " + EVENT_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if(cursor.moveToFirst()){
           // loop through the cursor (result set) and create new event Objects. Put them into the return list
            do{
                int eventID = cursor.getInt(0);
                String eventTitle = cursor.getString(1);
                String eventDate = cursor.getString(2);
                String eventTime = cursor.getString(3);
                String eventType = cursor.getString(4);
                String eventDescription = cursor.getString(5);

                EventModel eventModel = new EventModel(eventID,eventTitle,eventDate,eventTime,eventType,eventDescription);
                if(eventType.equals(type)) returnList.add(eventModel);

            }while (cursor.moveToNext());
        }
        else{
            // failure. do not add anything to the list
        }

        // close both the cursor and the db when done.
        cursor.close();
        db.close();

        return returnList;
    }
}
