package com.crazydudes.viewpagerexample.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * ContentProvider implementation for your project.
 * <p/>
 * A contentprovider is very important in the android Model - View - Controller architecture
 * that android provides. It's basically a database model that enables you to query your
 * SQLite database using the android built in content resolver.
 * <p/>
 * More information here: http://developer.android.com/guide/topics/providers/content-providers.html
 * <p/>
 * Whenever you make use of a SQLite database, make sure you implement one of these badboys!
 */
public class PagerContentProvider extends ContentProvider {

    private PagerDbHelper database;

    public static final int RECORD = 1;
    public static final int RECORD_ID = 2;

    private static final String AUTHORITY = "com.crazydudes.viewpagerexample";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/");

    private static final UriMatcher uriMatcher;

    /**
     * A uri matcher is very useful to create uris that direct to specific
     * records in the database. I usually add a Uri matcher that matches both
     * a query for the whole table and a query using the row id, that will only
     * affect that specific row.
     *
     * More on UriMatcher on the link in the class header.
     */
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, PagerDatabaseTables.TABLE_NAME, RECORD);
        uriMatcher.addURI(AUTHORITY, PagerDatabaseTables.TABLE_NAME + "/#", RECORD_ID);

    }

    @Override
    public boolean onCreate() {
        database = new PagerDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int uriType = uriMatcher.match(uri);
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (uriType) {
            case(RECORD):
                queryBuilder.setTables(PagerDatabaseTables.TABLE_NAME);
                break;
            case (RECORD_ID):
                queryBuilder.setTables(PagerDatabaseTables.TABLE_NAME);
                queryBuilder.appendWhere(PagerDatabaseTables.COLUMN_ID+"="+uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri.toString());
        }
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        //the below line will be very important
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = database.getWritableDatabase();
        int id = (int) db.insert(PagerDatabaseTables.TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(PagerDatabaseTables.TABLE_NAME + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase db = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case (RECORD):
                rowsDeleted = db.delete(PagerDatabaseTables.TABLE_NAME, selection, selectionArgs);
                break;
            case (RECORD_ID):
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                 rowsDeleted= db.delete(PagerDatabaseTables.TABLE_NAME, PagerDatabaseTables.COLUMN_ID + "=" + id, null);
                }else {
                    rowsDeleted = db.delete(PagerDatabaseTables.TABLE_NAME, PagerDatabaseTables.COLUMN_ID + "=" + id + " AND " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("unknown uri" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase db = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case(RECORD):
                rowsUpdated = db.update(PagerDatabaseTables.TABLE_NAME, values, selection, selectionArgs);
                break;
            case(RECORD_ID):
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(PagerDatabaseTables.TABLE_NAME, values, PagerDatabaseTables.COLUMN_ID + "=" + uri.getLastPathSegment(), null);
                }else {
                    rowsUpdated = db.update(PagerDatabaseTables.TABLE_NAME, values, PagerDatabaseTables.COLUMN_ID + "=" + uri.getLastPathSegment() + " AND " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("unknown uri" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }
}
