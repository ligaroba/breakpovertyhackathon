package com.kccrtms.kccrtms.functions;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;

import com.kccrtms.kccrtms.Database.DBContract;

import java.util.List;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

public class Functions {

    private static final String USERID = "id_users";
    private static final String TAG_USERSLOGIN = "queryusers";
    private static final String TAG_LOGIN = "loginwithid";
    private static final String TAG_INSERT= "insert";
    private static final String INSERTOUTLETS = "tbl_outlet";
    private static final String INSERTSALES = "tbl_sales";
    private static final String INSERTORDERS = "tbl_orders";


    private static final String UNITPRICE = "unitprice";
    private static final String REMARKS = "remarks";
    private static final String LOCATION = "location";

    //name
    private static final String TAG = "action";
    private static final String IDUSERS = "id_users";
    private static final String PRODUCTID = "id_product";
    private static final String IDREGION = "id_region";
    private static final String USERNAME = "username";
    private static final String IDTERITORY= "id_teritory";
    private static final String IDAREA = "id_area";
    private static final String USERPASS = "password";
    private static final String IDOUTLET = "id_outlet";
    private static final String ORDERQTY = "order_qty";
    private static final String USERACTTION = "useraction";
    private static final String SALESQTY = "salesqty";
    private static final String OUTLATENAME = "outletname";
    private static final String CONTACTPERSON = "contactperson";
    private static final String PHONE = "phone";
    private static final String CHANNEL = "channel";


    private static final String QUERYALL = "queryall";



    public ContentValues userlogin(String username, String pass) {
        ContentValues values = new ContentValues();
        values.put(TAG, TAG_USERSLOGIN);
        values.put(USERACTTION, TAG_LOGIN);
        values.put(USERNAME, username);
        values.put(USERPASS, pass);

        return values;
    }


    public ContentValues newoutlets(String iduser,int rid,int tid,int aid,String outletsname,String cname ,String location,String mobile,String channel){


        ContentValues values = new ContentValues();
        values.put(TAG, TAG_INSERT);
        values.put(USERACTTION, INSERTOUTLETS);
        values.put(IDUSERS, rid);
        values.put(IDREGION, rid);
        values.put(IDTERITORY, tid);
        values.put(IDAREA, aid);
        values.put(OUTLATENAME, outletsname);
        values.put(CONTACTPERSON,cname);
        values.put(PHONE, mobile);
        values.put(CHANNEL, channel);

        return  values;
    }
    public ContentValues newrecord(String iduser,int rid,int tid,int aid,int oid,String idprod ,int price,int qty,String remarks,String type){

        ContentValues values = new ContentValues();
        values.put(TAG, TAG_INSERT);


        if(type=="orders"){
            values.put(ORDERQTY, qty);
            values.put(USERACTTION, INSERTORDERS);
        }else {
            values.put(SALESQTY, qty);
            values.put(USERACTTION, INSERTSALES);
        }
        values.put(IDUSERS, rid);
        values.put(IDREGION, rid);
        values.put(IDTERITORY, tid);
        values.put(IDAREA, aid);
        values.put(IDOUTLET, oid);
        values.put(PRODUCTID,idprod);
        values.put(UNITPRICE,qty);
        values.put(UNITPRICE, price);
        values.put(REMARKS, remarks);

          return values;
    }



    public ContentValues getAll(String tblename) {
        ContentValues values = new ContentValues();
        values.put(TAG, QUERYALL);
        values.put(USERACTTION, tblename);
        return values;
    }

    public Location getLocation(Boolean enabledProviderOnly, Context context) {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location utilLocation = null;
        List<String> providers = locationManager.getProviders(enabledProviderOnly);


        for (String provider : providers) {
            if (checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                utilLocation = locationManager.getLastKnownLocation(provider);
                if(utilLocation!=null){
                    return utilLocation;
                }
                return null;

            }

        }
        return null;
    }
}