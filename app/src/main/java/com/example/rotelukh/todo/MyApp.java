package com.example.rotelukh.todo;

import android.app.Application;
import android.util.Log;

/*Extends by Application - instantiate earlier
 * Here we create DB
 * Singleton
 * */
public class MyApp extends Application {

    /* Static file for store a link to the data base object */
    private static DBToDo mDBToDo = null;

    /* Private field for store a LOG tag */
    public static final String LOG_TAG = "MyApp";

    @Override
    public void onCreate() {

        /* Invoke a parent method */
        super.onCreate();

        /* Create a data base object, pass context */
        mDBToDo = new DBToDo(getApplicationContext());

        /* Write a log */
        Log.d(LOG_TAG, "Application onCreate");
    }

    /**
     * Get a link to the data base object
     */
    public static DBToDo getDB() {
        return mDBToDo;
    }

    /**
     * Add any string to Log
     */
    public static void Log(String log) {
        Log.d(LOG_TAG, log);
    }

}
