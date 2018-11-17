package com.example.softex.barcopdesample.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.softex.barcopdesample.dataClass.Student;
import com.example.softex.barcopdesample.dataClass.StudentRepo;

public class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 4;
    public Context context;
    // Database Name
    private static final String DATABASE_NAME = "crud.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String q="CREATE TABLE barcode(ID INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT,count INTEGER , format TEXT,date TEXT,time TEXT,vehicleNo TEXT,refNo TEXT)";
        db.execSQL(q);
        String q1="CREATE TABLE admin(ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)";
        db.execSQL(q1);
        String q2="CREATE TABLE user(ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)";
        db.execSQL(q2);
        String q3="CREATE TABLE vehicle(ID INTEGER PRIMARY KEY AUTOINCREMENT, vehicleNo TEXT)";
        db.execSQL(q3);
        String q4="CREATE TABLE email(ID INTEGER PRIMARY KEY AUTOINCREMENT, emailId TEXT)";
        db.execSQL(q4);
        String q5="CREATE TABLE manualCount(ID INTEGER PRIMARY KEY AUTOINCREMENT, count TEXT, vehNo TEXT, refNo TEXT,date TEXT,time TEXT)";
        db.execSQL(q5);

        String q6="CREATE TABLE tbl_delay(ID INTEGER PRIMARY KEY AUTOINCREMENT, delay TEXT)";
        db.execSQL(q6);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE);
        // Create tables again
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE_vehicle);
        // Create tables again
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE_user);
        // Create tables again
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS manualCount");
        // Create tables again
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE_admin);
        // Create tables again
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS email");
        // Create tables again
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS tbl_delay");
        // Create tables again
        onCreate(db);
    }
}