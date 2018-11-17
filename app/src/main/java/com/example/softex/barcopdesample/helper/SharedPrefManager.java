package com.example.softex.barcopdesample.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;


/**
 * Created by softex on 04-Nov-17.
 */

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "qrcode";
    private static final String KEY_Start_Time = "keystarttime";
    private static final String KEY_End_Time = "keyendtime";
    private static final String KEY_Path = "path";
    private static final String KEY_Pass = "pass";
    private static final String KEY_ID = "id";
    private static final String KEY_COUNT = "count";
    private static final String KEY_Ref = "ref";

    private static final String KEY_Scan = "scan";

    private static final String KEY_Flash = "flash";


    public SharedPrefManager() {
    }

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }


    public boolean setStartTime(String time) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Start_Time, time);
        editor.commit();
        return true;
    }

    public String getStartTime() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String time= sharedPreferences.getString(KEY_Start_Time, null);
        return  time;
    }

    public boolean setCount(int count) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_COUNT, count);
        editor.apply();
        return true;
    }

    public int getCountt() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int count= sharedPreferences.getInt(KEY_COUNT, 0);
        return  count;
    }
    public boolean setRef(String ref) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Ref, ref);
        editor.apply();
        return true;
    }

    public String getRef() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String ref= sharedPreferences.getString(KEY_Ref,null);
        return  ref;
    }
    public boolean setPath(String path) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Path, path);
        editor.apply();
        return true;
    }

    public String getPath() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String path= sharedPreferences.getString(KEY_Path, null);
        return  path;
    }

    public boolean setPass(String pass) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Pass, pass);
        editor.apply();
        return true;
    }

    public String getPass() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String pass= sharedPreferences.getString(KEY_Pass, null);
        return  pass;
    }

    public boolean setId(int id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID ,id);
        editor.apply();
        return true;
    }
    public int getId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int id= sharedPreferences.getInt(KEY_ID, 0);
        return  id;
    }
    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }


    public boolean setScan(String scan) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Scan, scan);
        editor.apply();
        return true;
    }

    public String getScan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String scan= sharedPreferences.getString(KEY_Scan, null);
        return  scan;
    }
    public boolean setFlash(String flash) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Flash, flash);
        editor.apply();
        return true;
    }

    public String getFlash() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String flash= sharedPreferences.getString(KEY_Flash, null);
        return  flash;
    }
}
