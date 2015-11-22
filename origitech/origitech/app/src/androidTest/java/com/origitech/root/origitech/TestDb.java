package com.origitech.root.origitech;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.origitech.root.origitech.databases.Database;
import com.origitech.root.origitech.provider.DBContract;

/**
 * Created by root on 10/17/15.
 */
public class TestDb extends AndroidTestCase {
   /* public void testCreateDb() throws Throwable{
        mContext.deleteDatabase(DBContract.DATABASE_NAME);
        SQLiteDatabase db=new Database(this.mContext).getWritableDatabase();
    assertEquals(true,db.isOpen());
        db.close();
    }*/

    public void testInsertReadDb(){
        Database dbdb=new Database(mContext);
        SQLiteDatabase db=dbdb.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(DBContract.Verification._ID, 1);
        values.put(DBContract.Verification.KEY_PRODUCT_NAME, "Ligaroba");
        values.put(DBContract.Verification.KEY_PRODUCT_MODEL, "Lappy");
        values.put(DBContract.Verification.KEY_PRODUCT_OWNER, "origicheck");
        values.put(DBContract.Verification.KEY_PRODUCT_OWNER_ID, "01245789");
        values.put(DBContract.Verification.KEY_PRODUCT_TYPE,1);
        values.put(DBContract.Verification.KEY_AVATAR, "avatar");

long id;
        id=db.insert(DBContract.Verification.VERIFY_TABLE,null, values);
        assertEquals(1,id);
        Log.e("LOG", " new id " + id);

    }

}
