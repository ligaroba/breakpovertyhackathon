package com.origitech.root.origitech.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.origitech.root.origitech.constants.ConstantParams;
import com.origitech.root.origitech.constants.SetterGetter;
import com.origitech.root.origitech.provider.DBContract;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/1/15.
 */
public class Database extends SQLiteOpenHelper{
    public Database(Context context) {
        super(context, DBContract.DATABASE_NAME, null, DBContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String profileSql="CREATE TABLE " + DBContract.Profile.PROFILE_TABLE + "  ( "+
                DBContract.Profile._ID + " INTEGER PRIMARY KEY ," +
                DBContract.Profile.KEY_FIRSTNAME + " varchar(250) NULL," +
                DBContract.Profile.KEY_SECONDNAME + " varchar(250) NULL," +
                DBContract.Profile.KEY_MOBILE + " varchar(250) NULL," +
                DBContract.Profile.KEY_EMAIL + " varchar(250) NULL," +
                DBContract.Profile.KEY_AVATAR + " varchar(250) NULL" +
                ");";
        String verifySql="CREATE TABLE " + DBContract.Verification.VERIFY_TABLE + "  ( "+
                DBContract.Verification._ID + " INTEGER  PRIMARY KEY ," +
                DBContract.Verification.KEY_PRODUCT_NAME + " varchar(250) NULL," +
                DBContract.Verification.KEY_PRODUCT_MODEL + " varchar(250) NULL," +
                DBContract.Verification.KEY_PRODUCT_OWNER + " varchar(250) NULL," +
                DBContract.Verification.KEY_PRODUCT_OWNER_ID + " varchar(250) NULL," +
                DBContract.Verification.KEY_PRODUCT_SERIAL + " varchar(250) NULL," +
                DBContract.Verification.KEY_PRODUCT_TYPE + " varchar(250) NULL," +
                DBContract.Verification.KEY_VERIFICATION_CODE + " varchar(250) NULL," +
                DBContract.Report.KEY_TIME_ADDED + " varchar(250) NULL," +
                DBContract.Verification.KEY_AVATAR + " varchar(250) NULL" +

                ");";
        String reportSql="CREATE TABLE " + DBContract.Report.REPORT_TABLE + "  ( "+
                DBContract.Report._ID + " INTEGER  PRIMARY KEY," +
                DBContract.Report.KEY_PRODUCT_NAME + " varchar(250) NULL," +
                DBContract.Report.KEY_PRODUCT_MODEL + " varchar(250) NULL," +
                DBContract.Report.KEY_PRODUCT_SHOP + " varchar(250) NULL," +
                DBContract.Report.KEY_LOCATION + " varchar(250) NULL," +
                DBContract.Report.KEY_DEALERNAME + " varchar(250) NULL," +
                DBContract.Report.KEY_TIME_ADDED + " varchar(250) NULL," +
                DBContract.Report.KEY_STATUS + " varchar(5) NULL," +
                DBContract.Report.KEY_CASESTATUS + " varchar(5) NULL"+


                ");";
        String blacklistSql="CREATE TABLE " + DBContract.Blacklist.BLACKLIST_TABLE + "  ( "+
                DBContract.Blacklist._ID + " INTEGER  PRIMARY KEY," +
                DBContract.Blacklist.KEY_PRODUCT_NAME + " varchar(250) NULL," +
                DBContract.Blacklist.KEY_PRODUCT_MODEL + " varchar(250) NULL," +
                DBContract.Blacklist.KEY_PRODUCT_SHOP + " varchar(250) NULL," +
                DBContract.Blacklist.KEY_LOCATION + " varchar(250) NULL," +
                DBContract.Blacklist.KEY_DEALERNAME + " varchar(250) NULL," +
                DBContract.Blacklist.KEY_TIME_ADDED + " varchar(250) NULL," +
                DBContract.Blacklist.KEY_STATUS + " varchar(5) NULL," +
                DBContract.Blacklist.KEY_CASESTATUS + " varchar(5) NULL"+


                ");";

        String backupSql="CREATE TABLE " + DBContract.Backup.BACKUP_TABLE + "  ( "+
                DBContract.Report._ID + " INTEGER  PRIMARY KEY," +
                DBContract.Backup.KEY_TYPE + " varchar(250) NULL," +
                DBContract.Backup.KEY_AVATAR + " varchar(250) NULL," +
                DBContract.Backup.KEY_TIME_ADDED + " varchar(250) NULL" +
                ");";

            Log.e("SQLITEDB" , profileSql);
                    db.execSQL(profileSql);
                    db.execSQL(verifySql);
                    db.execSQL(reportSql);
                    db.execSQL(backupSql);
                     db.execSQL(blacklistSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE " + DBContract.Profile.PROFILE_TABLE);
        db.execSQL("DROP TABLE " + DBContract.Report.REPORT_TABLE);
        db.execSQL("DROP TABLE " + DBContract.Verification.VERIFY_TABLE);
        db.execSQL("DROP TABLE " + DBContract.Blacklist.BLACKLIST_TABLE);

        onCreate(db);



    }

    public void resetTables(String tablename) {
        // TODO Auto-generated method stub
        SQLiteDatabase db =this.getWritableDatabase();
        try {
            db.delete(tablename, null,null);
           // db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + tablename + "'");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        db.close();

    }




    public int getUserId() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT "+DBContract.Profile._ID+" FROM "+ DBContract.Profile.PROFILE_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            return cursor.getInt(cursor.getColumnIndex(DBContract.Profile._ID));
        else
            return -1; // not found
    }

    public List<SetterGetter> getBlacklist() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor=null;
        List<SetterGetter> list=new ArrayList<SetterGetter>();


        String query = "SELECT * FROM "+ DBContract.Blacklist.BLACKLIST_TABLE;
        cursor= db.rawQuery(query, null);
        if(cursor.moveToFirst()){
       while (cursor.isAfterLast()==false){
           SetterGetter setta =new SetterGetter();
           setta.setDealername(cursor.getString(cursor.getColumnIndex(DBContract.Report.KEY_DEALERNAME)));
          // setta.setOffencetype(cursor.getString(cursor.getColumnIndex(DBContract.Report.KEY_STATUS)));

           if(cursor.getInt(cursor.getColumnIndex(DBContract.Report.KEY_STATUS))==1) {
               setta.setOffencetype(ConstantParams.FAKE_PRODUCT_OFFENSE);
           }else{
               setta.setOffencetype(ConstantParams.FAKE_STICKER_OFFENSE);
           }
           if(cursor.getInt(cursor.getColumnIndex(DBContract.Report.KEY_CASESTATUS))==4) {
               setta.setCasestatus(ConstantParams.STATUS_JAIL);
           }else{
               setta.setCasestatus(ConstantParams.STATUS_FINED);
           }
           setta.setLocation(cursor.getString(cursor.getColumnIndex(DBContract.Report.KEY_LOCATION)));
           setta.setShopname(cursor.getString(cursor.getColumnIndex(DBContract.Report.KEY_PRODUCT_SHOP)));
           list.add(setta);
           cursor.moveToNext();

       }
        }
        return list;

    }

    public List<SetterGetter> getVerificationList() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor=null;
        List<SetterGetter> list=new ArrayList<SetterGetter>();


        String query = "SELECT * FROM "+ DBContract.Verification.VERIFY_TABLE;
        cursor= db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            while (cursor.isAfterLast()==false){
                SetterGetter setta =new SetterGetter();
                setta.setProductName(cursor.getString(cursor.getColumnIndex(DBContract.Verification.KEY_PRODUCT_NAME)));
                setta.setModel(cursor.getString(cursor.getColumnIndex(DBContract.Verification.KEY_PRODUCT_MODEL)));
                setta.setCompanyname(cursor.getString(cursor.getColumnIndex(DBContract.Verification.KEY_PRODUCT_OWNER)));
                setta.setProductSerial(cursor.getString(cursor.getColumnIndex(DBContract.Verification.KEY_PRODUCT_SERIAL)));
                setta.setProductType(cursor.getString(cursor.getColumnIndex(DBContract.Verification.KEY_PRODUCT_TYPE)));
                setta.setUniquecode(cursor.getString(cursor.getColumnIndex(DBContract.Verification.KEY_VERIFICATION_CODE)));
                setta.setTimeAdded(cursor.getString(cursor.getColumnIndex(DBContract.Verification.KEY_TIME_ADDED)));
                list.add(setta);
                cursor.moveToNext();

            }
        }
        return list;

    }

    public List<SetterGetter> getProfile() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor=null;
        List<SetterGetter> list=new ArrayList<SetterGetter>();


        String query = "SELECT * FROM "+ DBContract.Profile.PROFILE_TABLE;
        cursor= db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            while (cursor.isAfterLast()==false){
                SetterGetter setta =new SetterGetter();
                setta.setEmail(cursor.getString(cursor.getColumnIndex(DBContract.Profile.KEY_EMAIL)));
                setta.setFirstName(cursor.getString(cursor.getColumnIndex(DBContract.Profile.KEY_FIRSTNAME)));
                setta.setSecondName(cursor.getString(cursor.getColumnIndex(DBContract.Profile.KEY_SECONDNAME)));
                setta.setMobile(cursor.getString(cursor.getColumnIndex(DBContract.Profile.KEY_MOBILE)));

                list.add(setta);
                cursor.moveToNext();

            }
        }
        return list;

    }
    public List<SetterGetter> getBackup() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor=null;
        List<SetterGetter> list=new ArrayList<SetterGetter>();


        String query = "SELECT * FROM "+ DBContract.Backup.BACKUP_TABLE;
        cursor= db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            while (cursor.isAfterLast()==false){
                SetterGetter setta =new SetterGetter();
                setta.setAvatar(cursor.getString(cursor.getColumnIndex(DBContract.Backup.KEY_AVATAR)));
                setta.setBacktype(cursor.getString(cursor.getColumnIndex(DBContract.Backup.KEY_TYPE)));
                setta.setTimeAdded(cursor.getString(cursor.getColumnIndex(DBContract.Backup.KEY_TIME_ADDED)));
                list.add(setta);
                cursor.moveToNext();

            }
        }
        return list;

    }

   /* public void addContact(int id, String username, String contact){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.Profile._ID,id);
        values.put(DBContract.Profile.KEY_USERNAME,username);
        values.put(DBContract.Profile.,contact);

        db.insertOrThrow(CONTACTS_TABLE,null,values);
        db.close();
    }*/
}
