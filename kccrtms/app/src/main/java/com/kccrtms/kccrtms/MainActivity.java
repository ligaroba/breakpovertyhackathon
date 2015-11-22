package com.kccrtms.kccrtms;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kccrtms.kccrtms.Constants.ConstantsClass;
import com.kccrtms.kccrtms.Database.DBContract;
import com.kccrtms.kccrtms.Database.SqliteDBHelper;
import com.kccrtms.kccrtms.fragments.NewOutlet;
import com.kccrtms.kccrtms.fragments.Outlets;
import com.kccrtms.kccrtms.fragments.RecordOrder;
import com.kccrtms.kccrtms.fragments.RecordSales;
import com.kccrtms.kccrtms.fragments.Records;
import com.kccrtms.kccrtms.fragments.Regions;
import com.kccrtms.kccrtms.functions.Functions;
import com.kccrtms.kccrtms.network.BackgroundThread;

import static com.kccrtms.kccrtms.fragments.NewOutlet.*;

public class MainActivity extends AppCompatActivity implements Regions.RegionsData,Outlets.OutletData ,RecordOrder.RecordOrderData,RecordSales.RecordSalesData,NewOutletData,Records.NewRecordData {
    FragmentManager frgament = getSupportFragmentManager();
    Regions region;
    Outlets outlets;
    NewOutlet newoutlet;
    RecordOrder recordOrder;
    Records records;
    RecordSales recordSales;

    SqliteDBHelper db;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    int mTwoPane=0;




    ConstantsClass consta;
    private SharedPreferences pref;
    private String userid="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db=new SqliteDBHelper(this);
           // regions();
        teritory();
           area();
       // LocationManager.
      //  new Functions().getLocation()
        pref=getSharedPreferences(consta.LOGINPREF, Context.MODE_PRIVATE);
        userid= pref.getString(consta.USERID, "");

        region = new Regions();
        frgament.beginTransaction().add(R.id.container, region).addToBackStack("region").commit();







    }
    //
//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void regions() {
       /* db=new SqliteDBHelper(this);
        db.resetTables(DBContract.RegionEntry.REGION_TABLE);
        db.regions(1,"Nairobi");
        db.regions(2,"Mombasa");
        db.regions(3,"Kisumu");
        db.regions(4,"Nakuru");
        db.regions(5, "Kakamega");
*/
        new BackgroundThread(this,"loading..",0).execute(new Functions().getAll("tbl_region"));
        new BackgroundThread(this,"loading..",0).execute(new Functions().getAll("tbl_sku"));
        new BackgroundThread(this,"loading..",0).execute(new Functions().getAll("tbl_teritory"));
        new BackgroundThread(this,"loading..",0).execute(new Functions().getAll("tbl_area"));
        new BackgroundThread(this,"loading..",0).execute(new Functions().getAll("tbl_channels"));
        // return uri;

    }

    public void teritory() {
     /*   db=new SqliteDBHelper(this);
        db.resetTables(DBContract.TeritoryEntry.TERITORY_TABLE);
        db.Teritory(1, 1, "Embakasi");
        db.Teritory(2, 1,"Westlands");

        db.Teritory(3,2,"Shanzu");
        db.Teritory(4, 2,"Mariakani");

        db.Teritory(5,3,"Kombewa");
        db.Teritory(6,3,"Dunga");

        db.Teritory(7, 4,"Section 58");
        db.Teritory(8,4,"Langas");

        db.Teritory(9,5,"Lurambi");
        db.Teritory(10,5,"Jua kali");

*/

        // return uri;

    }


    public void area() {

     /*   db=new SqliteDBHelper(this);
         db.resetTables(DBContract.AreaEntry.AREA_TABLE);
        db.area(1, 1, 1, "tassia");
        db.area(2, 1, 2, "Lavington");

        db.area(3, 2, 1, "mtwapa");
        db.area(4, 2,2, "bamburi");

        db.area(5, 3, 1, "kolenyo");
        db.area(6, 3,2, "akala");

        db.area(7, 4, 1, "railways");
        db.area(8, 4,2, "cbd");

        db.area(9, 5, 1, "kericho ndogo");
        db.area(10, 5,2, "kefinco");
*/

        // return uri;

    }




    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause", "OnPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume","OnResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop","OnStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy", "OnDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onstart", "OnStart");
    }


    @Override
    public void onDataShare(Bundle data) {

        outlets = new Outlets();
        outlets.setArguments(data);
        frgament.beginTransaction().replace(R.id.container, outlets).addToBackStack("outlets").commit();
    }



    @Override
    public void onOutletDataDataShare(Bundle bundle) {
        records = new Records();
        records.setArguments(bundle);
        frgament.beginTransaction().replace(R.id.container, records).addToBackStack("records").commit();


    }


    @Override
    public void onNewOutletDataDataShare(Bundle bundle) {

    }



    @Override
    public void OnButtonClicked(int back) {
        switch (back){

            case 1 :

                region = new Regions();
                boolean fragmentPOpped=frgament.popBackStackImmediate("regions", 0);

                if (!fragmentPOpped){
                    region = new Regions();
                    frgament.beginTransaction().replace(R.id.container, region).addToBackStack("region").commit();
                }

             break;
            case 2 :
                newoutlet=new NewOutlet();
                newoutlet.show(frgament,"newOutlet");
                break;


            case 3 :
                break;
            case 4 :
                outlets = new Outlets();
                frgament.beginTransaction().replace(R.id.container, outlets).addToBackStack("outlets").commit();

                break;
            case 5 :
                recordOrder=new RecordOrder();
                recordOrder.show(frgament, "recordOrder");
                break;
            case 6:
                recordSales=new RecordSales();
                recordSales.show(frgament,"recordSales");
                break;

            case 7:
                records = new Records();
                frgament.beginTransaction().replace(R.id.container, records).addToBackStack("records").commit();
                break;

            case 8:
                Cursor cursor =db.getAll(DBContract.RecordsEntry.RECORDS_TABLE);
                ContentValues values=new ContentValues();
                while (!cursor.isAfterLast()){
                    cursor.moveToFirst();
                   String type= cursor.getString(cursor.getColumnIndex(DBContract.RecordsEntry.TYPE));

                    if(type=="orders"){
                        values.put(consta.ORDERQTY,cursor.getInt(cursor.getColumnIndex(DBContract.RecordsEntry.QUANTITY)));
                        values.put(consta.USERACTTION, consta.INSERTORDERS);
                    }else {
                        values.put(consta.SALESQTY, cursor.getColumnIndex(DBContract.RecordsEntry.QUANTITY));
                        values.put(consta.USERACTTION, consta.INSERTSALES);
                    }
                    values.put(consta.IDUSERS,  userid);
                    values.put(consta.IDREGION,  cursor.getColumnIndex(DBContract.RecordsEntry.COLUMN_LOC_KEY));
                    values.put(consta.IDTERITORY,  cursor.getColumnIndex(DBContract.RecordsEntry.COLUMN_LOC_KEY_TERITORY));
                    values.put(consta.IDAREA,  cursor.getColumnIndex(DBContract.RecordsEntry.COLUMN_LOC_KEY_AREA));
                    values.put(consta.IDOUTLET,  cursor.getColumnIndex(DBContract.RecordsEntry.COLUMN_LOC_KEY_OID));
                    values.put(consta.PRODUCTID, cursor.getColumnIndex(DBContract.RecordsEntry.PRODUCTNAME));
                    values.put(consta.UNITPRICE, cursor.getColumnIndex(DBContract.RecordsEntry.QUANTITY));
                    values.put(consta.UNITPRICE,  cursor.getColumnIndex(DBContract.RecordsEntry.PRICE));
                    values.put(consta.LOCATION, "032012201, 1245652");
                 cursor.moveToLast();
                }


                new BackgroundThread(this,"saving records..",0).execute(values);
                break;
        }

    }

    @Override
    public void ordersData(int rid, int tid, int aid, int oid, String idprod, Integer price, Integer qty, String type) {

       /* new BackgroundThread(this,"saving order..",0).execute(
                new Functions().newrecord(userid,rid,tid,aid,oid,idprod,price,qty,type));*/

    }

    @Override
    public void salesData(int rid, int tid, int aid, int oid, String idprod, Integer price, Integer qty, String type) {
       /* new BackgroundThread(this,"saving sale..",0).execute(
                new Functions().newrecord(userid,rid,tid,aid,oid,idprod,price,qty,type));*/
    }

    @Override
    public void newoutletsData(int rid, int tid, int aid, String outletname, String cname, String location, String mobile, String channel) {
        new BackgroundThread(this,"saving outlet..",0).execute(
                new Functions().newoutlets(userid,rid,tid,aid,outletname,cname,location,mobile,channel));
    }
}
