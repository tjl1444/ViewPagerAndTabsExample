package com.crazydudes.viewpagerexample.data;

import android.database.sqlite.SQLiteDatabase;

/**
 * Static class mostly for holing strings and SQL commands.
 */
public class PagerDatabaseTables {

    public static final String TABLE_NAME = "records";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GRADE = "grade";

    private static final String RECORDS_CREATE = "CREATE TABLE " +
            TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_NAME + " VARCHAR(45), " +
            COLUMN_GRADE + " INTEGER);";

    public static void createTable(SQLiteDatabase database) {
        database.execSQL(RECORDS_CREATE);
    }

    /**
     * Simple implementation just for the sake of not leaving it
     * blank, do whatever you please with it.
     * @param database
     */
    public static void upgrade(SQLiteDatabase database) {
        database.execSQL("DROP TABLE " + TABLE_NAME);
        createTable(database);
    }
}
