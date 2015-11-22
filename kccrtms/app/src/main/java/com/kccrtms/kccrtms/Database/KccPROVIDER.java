package com.kccrtms.kccrtms.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by root on 10/12/15.
 */
public class KccPROVIDER extends ContentProvider {

    private static final int AREA = 400;
    private static final int USERS = 402;
    private static final int USERSID = 403;
    private static final int AREA_ID = 401;
    private static final int TERITORY = 200;
    private static final int TERITORY_ID = 201;
    private static final int OUTLETS = 300;
    private static final int SHOPPING_BUDGET = 302;
    private static final int OUTLETS_ID = 301;
    private static final int REGION = 100;
    private static final int REGION_ID = 101;

    private static final int CHANNEL = 500;
    private static final int CHANNEL_ID = 501;

    private static final int PRODUCTS= 600;
    private static final int PRODUCTS_ID = 601;


    private static final UriMatcher uriMatcher = new UriMatcher(-1);
    private SQLiteDatabase shopDb;
    private   SqliteDBHelper dbn;
  


    static {
        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.RegionEntry.REGION_TABLE, REGION);
        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.RegionEntry.REGION_TABLE + "/#", REGION_ID);
        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.TeritoryEntry.TERITORY_TABLE, TERITORY);
        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.TeritoryEntry.TERITORY_TABLE + "/#", TERITORY_ID);
        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.AreaEntry.AREA_TABLE, AREA);
        //  uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, ShoppingEntry.SHOPPING_TABLE+"/*/*", SHOPPING_BUDGET);

        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.AreaEntry.AREA_TABLE + "/#", AREA_ID);
        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.OutletEntry.OUTLETS_TABLE, OUTLETS);
        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.OutletEntry.OUTLETS_TABLE + "/#", OUTLETS_ID);

        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.UserEntry.USERS_TABLE, USERS);
        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.UserEntry.USERS_TABLE + "/#", USERSID);

        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.ChannelEntry.CHANNEL_TABLE, CHANNEL);
        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.ChannelEntry.CHANNEL_TABLE + "/#", CHANNEL_ID);

        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.ProductEntry.PRODUCT_TABLE, PRODUCTS);
        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.ProductEntry.PRODUCT_TABLE + "/#", PRODUCTS_ID);
    }


    public boolean onCreate()
    {
        dbn=new SqliteDBHelper(getContext());
        shopDb = dbn.getWritableDatabase();
        return shopDb != null;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int id;
        int match = uriMatcher.match(uri);
        switch (match) {

            case REGION:
                id = this.shopDb.delete(DBContract.RegionEntry.REGION_TABLE, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return id;
            case REGION_ID:
                return 0;
            case TERITORY:
                id = this.shopDb.delete(DBContract.TeritoryEntry.TERITORY_TABLE, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return id;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);

        }


    }


    public String getType(Uri paramUri)
    {
        switch (uriMatcher.match(paramUri))
        {

            case REGION:
                return DBContract.TeritoryEntry.CONTENT_TYPE;
            case REGION_ID:
                return DBContract.TeritoryEntry.CONTENT_ITEM_TYPE;


            default:
                throw new IllegalArgumentException("Unsupported URI: " + paramUri);
        }
    }

    public Uri insert(Uri uri, ContentValues values)
    {
        int match = uriMatcher.match(uri);
        Uri _uri = null;
        long rowinsertd;
        switch (match)
        {

            case REGION:
                rowinsertd = shopDb.insert(DBContract.RegionEntry.REGION_TABLE,null,values);
                _uri= ContentUris.withAppendedId(uri, rowinsertd);
                getContext().getContentResolver().notifyChange(_uri,null);
                return _uri;
            case TERITORY:
                rowinsertd =shopDb.insert(DBContract.TeritoryEntry.TERITORY_TABLE,null,values);
                _uri=ContentUris.withAppendedId(uri,rowinsertd);
                getContext().getContentResolver().notifyChange(_uri,null);
                return _uri;
            case AREA:
                rowinsertd =shopDb.insert(DBContract.AreaEntry.AREA_TABLE, null, values);
                _uri=ContentUris.withAppendedId(uri,rowinsertd);
                getContext().getContentResolver().notifyChange(_uri,null);
                return _uri;
            case OUTLETS:
                rowinsertd =shopDb.insert(DBContract.OutletEntry.OUTLETS_TABLE, null, values);
                _uri=ContentUris.withAppendedId(uri,rowinsertd);
                getContext().getContentResolver().notifyChange(_uri,null);
                return _uri;
            case USERS:
                rowinsertd =shopDb.insert(DBContract.UserEntry.USERS_TABLE, null, values);
                _uri=ContentUris.withAppendedId(uri,rowinsertd);
                getContext().getContentResolver().notifyChange(_uri,null);
                return _uri;
            case CHANNEL:
                rowinsertd =shopDb.insert(DBContract.ChannelEntry.CHANNEL_TABLE, null, values);
                _uri=ContentUris.withAppendedId(uri,rowinsertd);
                getContext().getContentResolver().notifyChange(_uri,null);
                return _uri;
            case PRODUCTS:
                rowinsertd =shopDb.insert(DBContract.ProductEntry.PRODUCT_TABLE, null, values);
                _uri=ContentUris.withAppendedId(uri,rowinsertd);
                getContext().getContentResolver().notifyChange(_uri,null);
                return _uri;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri );

        }


    }


    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String soroder)
    {
        SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
        Cursor cursor=null;
        switch (uriMatcher.match(uri))
        {
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);


            case REGION:
                sqlBuilder.setTables(DBContract.RegionEntry.REGION_TABLE);
                //sqlBuilder.appendWhere(DBContract.RegionEntry._ID + " = " + uri.getPathSegments().get(1));
                cursor = sqlBuilder.query(shopDb, projection, selection, selectionArgs, null, null, null);

                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case TERITORY:
                sqlBuilder.setTables(DBContract.TeritoryEntry.TERITORY_TABLE);
                cursor = sqlBuilder.query(shopDb, projection, selection, selectionArgs, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case AREA:
             //   cursor = query(shopDb, projection, selection, selectionArgs, null, null, null);
               // cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case OUTLETS:


       //         cursor = getBudget.query(shopDb,projection, selection, selectionArgs, null, null, null);
         //       cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case USERS:
                sqlBuilder.setTables(DBContract.UserEntry.USERS_TABLE);
                cursor = sqlBuilder.query(shopDb, projection, selection, selectionArgs, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case CHANNEL:
                sqlBuilder.setTables(DBContract.ChannelEntry.CHANNEL_TABLE);
                cursor = sqlBuilder.query(shopDb, projection, selection, selectionArgs, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case PRODUCTS:
                sqlBuilder.setTables(DBContract.ProductEntry.PRODUCT_TABLE);
                cursor = sqlBuilder.query(shopDb, projection, selection, selectionArgs, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;


        }

    }

    public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString)
    {
        return 0;
    }
}
