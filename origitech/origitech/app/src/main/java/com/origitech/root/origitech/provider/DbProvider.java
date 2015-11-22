package com.origitech.root.origitech.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.origitech.root.origitech.databases.Database;



/**
 * Created by root on 9/30/15.
 */
public class DbProvider extends ContentProvider{
    Database sdb;
    private SQLiteDatabase db;

    private static final int PROFILE=500;
    private static final int PROFILEID=501;
    private static final int VERIFICATION=400;
    private static final int VERIFICATIONID=401;
    private static final int REPORT=300;
    private static final int REPORTID=301;
    private static final int BACKUP=600;
    private static final int BACKUPID=601;
    private static final int BLACKLIST=700;
    private static final int BLACKLISTID=701;

    private static final UriMatcher uriMatcher = new UriMatcher(-1);


    static {

        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.Profile.PROFILE_TABLE,PROFILE);
        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.Profile.PROFILE_TABLE+"/#",PROFILEID);

        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.Verification.VERIFY_TABLE,VERIFICATION);
        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.Verification.VERIFY_TABLE+"/#",VERIFICATIONID);

        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.Report.REPORT_TABLE,REPORT);
        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.Report.REPORT_TABLE+"/#",REPORTID);

        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.Backup.BACKUP_TABLE,BACKUP);
        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.Backup.BACKUP_TABLE+"/#",BACKUPID);

        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.Blacklist.BLACKLIST_TABLE,BLACKLIST);
        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.Blacklist.BLACKLIST_TABLE+"/#",BLACKLISTID);

    }



    @Override
    public boolean onCreate() {
        sdb =new Database(getContext());
        db=sdb.getWritableDatabase();

        return db !=null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();

        Cursor cursor=null;
        int match = uriMatcher.match(uri);
        switch (match){

            case PROFILE:
                sqlBuilder.setTables(DBContract.Profile.PROFILE_TABLE);
                cursor=sqlBuilder.query(db,projection,selection,selectionArgs,sortOrder,null,null,null);
                cursor.setNotificationUri(getContext().getContentResolver(),uri);

               return cursor;
            case PROFILEID:

                sqlBuilder.setTables(DBContract.Profile.PROFILE_TABLE);
                sqlBuilder.appendWhere(DBContract.Profile._ID + " = " + uri.getPathSegments().get(1));
                cursor=sqlBuilder.query(db,projection,selection,selectionArgs,sortOrder,null,null,null);
                cursor.setNotificationUri(getContext().getContentResolver(),uri);


                return cursor;
            case VERIFICATION:
                sqlBuilder.setTables(DBContract.Verification.VERIFY_TABLE);
                cursor=sqlBuilder.query(db,projection,selection,selectionArgs,sortOrder,null,null,null);
                cursor.setNotificationUri(getContext().getContentResolver(),uri);
                return cursor;

            case VERIFICATIONID:

                sqlBuilder.setTables(DBContract.Verification.VERIFY_TABLE);
                sqlBuilder.appendWhere(DBContract.Verification._ID + " = " + uri.getPathSegments().get(1));
                cursor=sqlBuilder.query(db,projection,selection,selectionArgs,sortOrder,null,null,null);
                cursor.setNotificationUri(getContext().getContentResolver(),uri);


                return cursor;


            case REPORT:
                sqlBuilder.setTables(DBContract.Report.REPORT_TABLE);
                sqlBuilder.appendWhere(DBContract.Report._ID + " = " + uri.getPathSegments().get(1));
                cursor=sqlBuilder.query(db,projection,selection,selectionArgs,sortOrder,null,null,null);
                cursor.setNotificationUri(getContext().getContentResolver(),uri);

                break;

            case REPORTID:

                sqlBuilder.setTables(DBContract.Report.REPORT_TABLE);
                sqlBuilder.appendWhere(DBContract.Report._ID + " = " + uri.getPathSegments().get(1));
                cursor=sqlBuilder.query(db,projection,selection,selectionArgs,sortOrder,null,null,null);
                cursor.setNotificationUri(getContext().getContentResolver(),uri);


                return cursor;

            case BLACKLIST:
                sqlBuilder.setTables(DBContract.Blacklist.BLACKLIST_TABLE);
                sqlBuilder.appendWhere(DBContract.Report._ID + " = " + uri.getPathSegments().get(1));
                cursor=sqlBuilder.query(db,projection,selection,selectionArgs,sortOrder,null,null,null);
                cursor.setNotificationUri(getContext().getContentResolver(),uri);

                break;

            case BLACKLISTID:

                sqlBuilder.setTables(DBContract.Blacklist.BLACKLIST_TABLE);
                sqlBuilder.appendWhere(DBContract.Report._ID + " = " + uri.getPathSegments().get(1));
                cursor=sqlBuilder.query(db,projection,selection,selectionArgs,sortOrder,null,null,null);
                cursor.setNotificationUri(getContext().getContentResolver(),uri);


                return cursor;

            case BACKUP:
                sqlBuilder.setTables(DBContract.Backup.BACKUP_TABLE);
                sqlBuilder.appendWhere(DBContract.Backup._ID + " = " + uri.getPathSegments().get(1));
                cursor=sqlBuilder.query(db,projection,selection,selectionArgs,sortOrder,null,null,null);
                cursor.setNotificationUri(getContext().getContentResolver(),uri);

                break;

            case BACKUPID:

                sqlBuilder.setTables(DBContract.Backup.BACKUP_TABLE);
                sqlBuilder.appendWhere(DBContract.Backup._ID + " = " + uri.getPathSegments().get(1));
                cursor=sqlBuilder.query(db,projection,selection,selectionArgs,sortOrder,null,null,null);
                cursor.setNotificationUri(getContext().getContentResolver(),uri);


                return cursor;

            default: throw new IllegalArgumentException("Uri not supported: " + uri);




        }




        return null;
    }

    @Override
    public String getType(Uri uri) {


        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Uri _uri = null;
        long rowinserted;
        int match = uriMatcher.match(uri);

            switch (match){
                case PROFILE:
                    rowinserted=db.insert(DBContract.Profile.PROFILE_TABLE,null,values);
                    _uri= ContentUris.withAppendedId(DBContract.Profile.CONTENT_URI, rowinserted);
                    getContext().getContentResolver().notifyChange(_uri,null);


                    return _uri;
                case VERIFICATION:
                    rowinserted=db.insert(DBContract.Verification.VERIFY_TABLE,null,values);
                    _uri= ContentUris.withAppendedId(DBContract.Verification.CONTENT_URI, rowinserted);
                    getContext().getContentResolver().notifyChange(_uri,null);

                    return _uri;
                case REPORT:
                    rowinserted=db.insert(DBContract.Report.REPORT_TABLE,null,values);
                    _uri= ContentUris.withAppendedId(DBContract.Report.CONTENT_URI, rowinserted);
                    getContext().getContentResolver().notifyChange(_uri,null);

                    return _uri;
                case BACKUP:
                    rowinserted=db.insert(DBContract.Backup.BACKUP_TABLE,null,values);
                    _uri= ContentUris.withAppendedId(DBContract.Backup.CONTENT_URI, rowinserted);
                    getContext().getContentResolver().notifyChange(_uri,null);
                    return _uri;
                case BLACKLIST:
                    rowinserted=db.insert(DBContract.Blacklist.BLACKLIST_TABLE,null,values);
                    _uri= ContentUris.withAppendedId(DBContract.Backup.CONTENT_URI, rowinserted);
                    getContext().getContentResolver().notifyChange(_uri,null);
                    Log.e("BLACKLIST","Blaclist");
                    return _uri;
                default:
                    throw  new  IllegalArgumentException("Not supported: " + uri);
            }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
