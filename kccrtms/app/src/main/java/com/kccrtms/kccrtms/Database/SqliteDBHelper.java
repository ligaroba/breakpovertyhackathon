package com.kccrtms.kccrtms.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by root on 10/12/15.
 */
public class SqliteDBHelper extends SQLiteOpenHelper{



    private static final int DATABASE_VERSION = 1;

    public SqliteDBHelper(Context context)
    {
        super(context, DBContract.DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        String  psql ="CREATE TABLE IF NOT EXISTS " + DBContract.RegionEntry.REGION_TABLE + " (" +
                DBContract.RegionEntry._ID+ " integer primary key," +
                DBContract.RegionEntry.KEY_REGIONNAME + " varchar(100) not null);";
        Log.e("Product sql", psql);
        db.execSQL(psql);


        String sSql= "CREATE TABLE IF NOT EXISTS " +
                DBContract.TeritoryEntry.TERITORY_TABLE + " (" +
                DBContract.TeritoryEntry._ID + "  integer primary key," +
                DBContract.TeritoryEntry.COLUMN_LOC_KEY + "  int(5) not null," +
                DBContract.TeritoryEntry.KEY_TERITORYNAME + " varchar(100) not null)";
        db.execSQL(sSql);

        String prSql="CREATE TABLE  " +
                DBContract.AreaEntry.AREA_TABLE +" ( "+
                DBContract.AreaEntry._ID + " INTEGER PRIMARY KEY," +
                DBContract.AreaEntry.COLUMN_LOC_KEY_REGION+ "  int(5) not null," +
                DBContract.AreaEntry.COLUMN_LOC_KEY_TERITORY+ "  int(5) not null," +
                DBContract.AreaEntry.CAREA_NAME+ " varchar(100) not null )" ;
        db.execSQL(prSql);

        String usersql="CREATE TABLE IF NOT EXISTS " +
                DBContract.UserEntry.USERS_TABLE +" ( "+
                DBContract.UserEntry._ID + " INTEGER PRIMARY KEY ," +
                DBContract.UserEntry.KEY_FIRSTNAME + " varchar(200) not null ," +
                DBContract.UserEntry.KEY_LASTNAME +" varchar(200) not null  ," +
                DBContract.UserEntry.KEY_EMAIL +" varchar(200) not null  ," +
                DBContract.UserEntry.KEY_MOBILE +" varchar(200) not null  ," +
                DBContract.UserEntry.KEY_USERNAME +" varchar(200) not null  ," +
                DBContract.UserEntry.KEY_PASSWORD +" varchar(200) not null  )";
        db.execSQL(usersql);


        String shSql="CREATE TABLE IF NOT EXISTS " +
                DBContract.OutletEntry.OUTLETS_TABLE+ "( "+
                DBContract.OutletEntry._ID +  " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                DBContract.OutletEntry.COLUMN_LOC_KEY + " int(5) not null ," +
                DBContract.OutletEntry.COLUMN_LOC_KEY_TERITORY + " int(5) not null  ," +
                DBContract.OutletEntry.COLUMN_LOC_KEY_AREA + " int(5) not null  ," +
                DBContract.OutletEntry.OUTLET_NAME +" varchar(200) not null,"+
                DBContract.OutletEntry.CONTACTPERSON +" varchar(200) not null,"+
                DBContract.OutletEntry.CONTACTS +" varchar(200) not null," +
                DBContract.OutletEntry.CHANNEL +" varchar(200) not null" +
                ")";


        db.execSQL(shSql);
        String recordsSql="CREATE TABLE IF NOT EXISTS " +
                DBContract.RecordsEntry.RECORDS_TABLE+ "( "+
                DBContract.RecordsEntry._ID +  " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                DBContract.RecordsEntry.COLUMN_LOC_KEY + " int(5) not null ," +
                DBContract.RecordsEntry.COLUMN_LOC_KEY_TERITORY + " int(5) not null  ," +
                DBContract.RecordsEntry.COLUMN_LOC_KEY_AREA + " int(5) not null  ," +
                DBContract.RecordsEntry.COLUMN_LOC_KEY_OID + " int(5) not null  ," +
                DBContract.RecordsEntry.PRODUCTNAME+" varchar(255) not null,"+
                DBContract.RecordsEntry.QUANTITY +" int(5) not null," +
                DBContract.RecordsEntry.PRICE +" int(5) not null," +
                DBContract.RecordsEntry.TYPE +" int(5) not null" +
                ")";


        db.execSQL(recordsSql);


        String channelSql="CREATE TABLE IF NOT EXISTS " +
                DBContract.ChannelEntry.CHANNEL_TABLE+ "( "+
                DBContract.ChannelEntry._ID +  " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                DBContract.ChannelEntry.KEY_CHANNEL+" varchar(255) not null"+
                ")";
        db.execSQL(channelSql);

        String productSql="CREATE TABLE IF NOT EXISTS " +
                DBContract.ProductEntry.PRODUCT_TABLE+ "( "+
                DBContract.ProductEntry._ID +  " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                DBContract.ProductEntry.KEY_PRODUCT+" varchar(255) not null"+
                ")";
        db.execSQL(productSql);

    }






    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.RegionEntry.REGION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.AreaEntry.AREA_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.OutletEntry.OUTLETS_TABLE);
        db.execSQL("DROP TABLE IF EXITS " + DBContract.TeritoryEntry.TERITORY_TABLE);
        db.execSQL("DROP TABLE IF EXITS " + DBContract.UserEntry.USERS_TABLE);
        onCreate(db);
    }




    public void resetTables(String tablename) {
        // TODO Auto-generated method stub
        SQLiteDatabase db =this.getWritableDatabase();
        try {
            db.delete(tablename, null,null);
            db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + tablename + "'");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        db.close();

    }




    public void users(String fnme,String lname ,String email,String mobile,String username,String pass){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_PRID,prid);
        values.put(DBContract.UserEntry.KEY_FIRSTNAME,fnme);
        values.put(DBContract.UserEntry.KEY_LASTNAME,lname);
        values.put(DBContract.UserEntry.KEY_EMAIL,email);
        values.put(DBContract.UserEntry.KEY_MOBILE,mobile);
        values.put(DBContract.UserEntry.KEY_USERNAME,username);
        values.put(DBContract.UserEntry.KEY_PASSWORD,pass);





        db.insertOrThrow(DBContract.UserEntry.USERS_TABLE,null,values);
        db.close();
    }


    public long newoutlets(int rid,int tid,int aid,String outletsname,String cname ,String location,String mobile,String channel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_PRID,prid);

        values.put(DBContract.OutletEntry.COLUMN_LOC_KEY, rid);
        values.put(DBContract.OutletEntry.COLUMN_LOC_KEY_TERITORY, tid);
        values.put(DBContract.OutletEntry.COLUMN_LOC_KEY_AREA, aid);
        values.put(DBContract.OutletEntry.OUTLET_NAME, outletsname);
        values.put(DBContract.OutletEntry.CONTACTPERSON,cname);
        values.put(DBContract.OutletEntry.CONTACTS,mobile);
       // values.put(DBContract.OutletEntry.LOCATION,location);

        values.put(DBContract.OutletEntry.CHANNEL, channel);






      long row=  db.insertOrThrow(DBContract.OutletEntry.OUTLETS_TABLE,null,values);
        db.close();
        return  row;
    }
    public long newrecord(int rid,int tid,int aid,int oid,String prodname ,int price,int qty,String type){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_PRID,prid);

        values.put(DBContract.RecordsEntry.COLUMN_LOC_KEY, rid);
        values.put(DBContract.RecordsEntry.COLUMN_LOC_KEY_TERITORY, tid);
        values.put(DBContract.RecordsEntry.COLUMN_LOC_KEY_AREA, aid);
        values.put(DBContract.RecordsEntry.COLUMN_LOC_KEY_OID, oid);
        values.put(DBContract.RecordsEntry.PRODUCTNAME,prodname);
        values.put(DBContract.RecordsEntry.QUANTITY,qty);
        values.put(DBContract.RecordsEntry.PRICE,price);
        values.put(DBContract.RecordsEntry.TYPE, type);

       long row= db.insertOrThrow(DBContract.RecordsEntry.RECORDS_TABLE,null,values);
        db.close();
        return row;
    }





    public void regions(int rid,String pname){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.RegionEntry._ID,rid);
        values.put(DBContract.RegionEntry.KEY_REGIONNAME, pname);

        db.insertOrThrow(DBContract.RegionEntry.REGION_TABLE, null, values);
        db.close();
    }

    public void Teritory(int tid,int rid ,String teritoryname){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.TeritoryEntry._ID,tid);
        values.put(DBContract.TeritoryEntry.COLUMN_LOC_KEY,rid);
        values.put(DBContract.TeritoryEntry.KEY_TERITORYNAME,teritoryname);

        db.insertOrThrow(DBContract.TeritoryEntry.TERITORY_TABLE,null,values);
        db.close();
    }

    public void area(int aid,int rid,int tid,String area){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_PRID,prid);
        values.put(DBContract.AreaEntry._ID, aid);
        values.put(DBContract.AreaEntry.COLUMN_LOC_KEY_REGION, rid);
        values.put(DBContract.AreaEntry.COLUMN_LOC_KEY_TERITORY, tid);
        values.put(DBContract.AreaEntry.CAREA_NAME, area);

        db.insertOrThrow(DBContract.AreaEntry.AREA_TABLE, null, values);
        db.close();
    }

    public long outlet(int oid,int rid,int tid,int aid,String outletname){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_PRID,prid);
        values.put(DBContract.OutletEntry._ID, aid);
        values.put(DBContract.OutletEntry.COLUMN_LOC_KEY, rid);
        values.put(DBContract.OutletEntry.COLUMN_LOC_KEY_TERITORY, tid);
        values.put(DBContract.OutletEntry.COLUMN_LOC_KEY_AREA, aid);
        values.put(DBContract.OutletEntry.OUTLET_NAME, outletname);

       long rowid= db.insertOrThrow(DBContract.OutletEntry.OUTLETS_TABLE, null, values);
        db.close();
        return rowid;
    }

    public Cursor getRegions(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DBContract.RegionEntry.REGION_TABLE, null,
                null, null, null, null, null);

        return cursor;


    }

    public Cursor getTeritory(int regionID){

        SQLiteDatabase db = this.getReadableDatabase();

        String selection =  DBContract.TeritoryEntry.TERITORY_TABLE + "." +DBContract.TeritoryEntry.COLUMN_LOC_KEY + " = " + regionID;
        Cursor cursor = db.query(DBContract.TeritoryEntry.TERITORY_TABLE,null,
                selection, null, null, null, null);

        return cursor;


    }
    public Cursor getArea(int regionID,int teritoryID){

        SQLiteDatabase db = this.getReadableDatabase();

        String selectiond =  DBContract.AreaEntry.AREA_TABLE + "." +DBContract.AreaEntry.COLUMN_LOC_KEY_REGION+ " = " + regionID +" AND "+DBContract.AreaEntry.AREA_TABLE + "." +DBContract.AreaEntry.COLUMN_LOC_KEY_TERITORY+ " = " + teritoryID;
        Cursor cursor = db.query(DBContract.AreaEntry.AREA_TABLE,null,
                selectiond, null, null, null, null);

        return cursor;


    }
    public Cursor getOutlets(int regionID,int teritoryID,int areaID){

        SQLiteDatabase db = this.getReadableDatabase();

        String selectiond = DBContract.OutletEntry.OUTLETS_TABLE+"."+ DBContract.OutletEntry.COLUMN_LOC_KEY_AREA + " =" + areaID + " AND " + DBContract.OutletEntry.OUTLETS_TABLE + "." +DBContract.OutletEntry.COLUMN_LOC_KEY+ " = " + regionID + " AND "+DBContract.OutletEntry.OUTLETS_TABLE + "." +DBContract.OutletEntry.COLUMN_LOC_KEY_TERITORY+ " = " + teritoryID;
        Cursor cursor = db.query(DBContract.OutletEntry.OUTLETS_TABLE,null,
                selectiond, null, null, null, null);

        return cursor;


    }
    public Boolean getUser(String username,String password){

        SQLiteDatabase db = this.getReadableDatabase();

        String selectiond = DBContract.UserEntry.KEY_USERNAME + " ='" + username + "' AND " + DBContract.UserEntry.KEY_PASSWORD +"='"+ password+"'";
        Cursor cursor = db.query(DBContract.UserEntry.USERS_TABLE,null,
                selectiond, null, null, null, null);
        if(cursor!=null){

            return true;
        }
        return false;

    }

    public Cursor getAll(String tablename){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tablename,null,
                null, null, null, null, null);
    return cursor;

    }

}
