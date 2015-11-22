package com.kccrtms.kccrtms.network;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.kccrtms.kccrtms.Constants.ConstantsClass;
import com.kccrtms.kccrtms.Database.DBContract;
import com.kccrtms.kccrtms.Database.SqliteDBHelper;
import com.kccrtms.kccrtms.MainActivity;
import com.kccrtms.kccrtms.UserManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by root on 10/12/15.
 */
public class BackgroundThread extends AsyncTask<ContentValues,String,String> {
    JSONObject json;
    private JsonHandler handler;
    Context context;
    String message;
    ProgressDialog pDialog;
    JSONArray jsonArray;
    JSONObject jsonregion, jsonteritory, jsonarea,jsonoutlet,jsonuser;
    String response;
   
    String TAG = "ProcessingTAsk";
    SqliteDBHelper db;
    private ConstantsClass consta;
    private static final String SUCCESS = "success";
    private static final String TYPE = "type";
    private static final String ERROR = "error";
    private static final String EXISTS = "exists";


    private int displayprogres;
    private MenuItem refreshMenuItem;
    private ContentValues values;
    private Uri uri;

    public BackgroundThread(Context context, String message
            , int show//,MenuItem item
                            //
    ) {
        handler = new JsonHandler();
        this.context = context;
        this.message = message;
        displayprogres = show;
        //  refreshMenuItem=item;

    }

    public void showProgress() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(Html.fromHtml("<b> <i> " + message + "</i> </b>"));
        pDialog.setCancelable(false);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.show();
    }

    @SuppressLint("NewApi")
    protected void onPreExecute() {
        super.onPreExecute();
        if (displayprogres == 1) {
            showProgress();
        } else {
            //  refreshMenuItem.setActionView(R.layout.custom_progressbar);

            //  refreshMenuItem.expandActionView();
        }


    }

    protected String doInBackground(ContentValues... params) {


        try {

                json = handler.JsonConnection(params[0]);

            if (Integer.parseInt(json.getString(SUCCESS)) > 0) {
                // jsonArray= json.getJSONArray("tip");
                response = json.getString(TYPE);

               if (response.equalsIgnoreCase("tbl_teritory")) {
                    db = new SqliteDBHelper(context);
                    db.resetTables(DBContract.TeritoryEntry.TERITORY_TABLE);

                    jsonArray = json.getJSONArray("data");
                    Log.e(TAG, jsonArray.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonteritory = jsonArray.getJSONObject(i);

                        ContentValues teritory = new ContentValues();
                        teritory.put(DBContract.TeritoryEntry._ID, jsonteritory.getInt("id_teritory"));
                        teritory.put(DBContract.TeritoryEntry.COLUMN_LOC_KEY, jsonteritory.getInt("id_region"));
                        teritory.put(DBContract.TeritoryEntry.KEY_TERITORYNAME, jsonteritory.getString("teritory_name"));

                           uri = context.getContentResolver().insert(DBContract.TeritoryEntry.CONTENT_URI, teritory);
                           consta.SUCCESS_RESPONSE = 2;
                           consta.URI=uri;


                    }
                   return response;


                } else if (response.equalsIgnoreCase("tbl_sku")) {
                   db = new SqliteDBHelper(context);
                   db.resetTables(DBContract.ProductEntry.PRODUCT_TABLE);

                   jsonArray = json.getJSONArray("data");
                   Log.e(TAG, jsonArray.toString());

                   for (int i = 0; i < jsonArray.length(); i++) {
                       jsonteritory = jsonArray.getJSONObject(i);

                       ContentValues teritory = new ContentValues();
                       teritory.put(DBContract.ProductEntry._ID, jsonteritory.getInt("id_sku"));
                       teritory.put(DBContract.ProductEntry.KEY_PRODUCT, jsonteritory.getString("sku_name"));


                       uri = context.getContentResolver().insert(DBContract.ProductEntry.CONTENT_URI, teritory);

                   }
                   return response;


               } else if (response.equalsIgnoreCase("tbl_channels")) {
                   db = new SqliteDBHelper(context);
                   db.resetTables(DBContract.ChannelEntry.CHANNEL_TABLE);

                   jsonArray = json.getJSONArray("data");
                   Log.e(TAG, jsonArray.toString());

                   for (int i = 0; i < jsonArray.length(); i++) {
                       jsonteritory = jsonArray.getJSONObject(i);

                       ContentValues teritory = new ContentValues();
                       teritory.put(DBContract.ChannelEntry._ID, jsonteritory.getInt("id_channel"));
                       teritory.put(DBContract.ChannelEntry.KEY_CHANNEL, jsonteritory.getString("channel_name"));
                       uri = context.getContentResolver().insert(DBContract.ChannelEntry.CONTENT_URI, teritory);

                   }
                   return response;


               }else if (response.equalsIgnoreCase("tbl_users")) {


                    db = new SqliteDBHelper(context);
                    db.resetTables(DBContract.UserEntry.USERS_TABLE);
                    jsonArray = json.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonuser = jsonArray.getJSONObject(i);
                        values = new ContentValues();
                        values.put(DBContract.UserEntry._ID, jsonuser.getInt("id_users"));
                        values.put(DBContract.UserEntry.KEY_FIRSTNAME, jsonuser.getString("firstname"));
                        values.put(DBContract.UserEntry.KEY_LASTNAME, jsonuser.getString("lastname"));
                        values.put(DBContract.UserEntry.KEY_EMAIL, jsonuser.getString("email"));
                        values.put(DBContract.UserEntry.KEY_MOBILE, jsonuser.getString("mobile"));
                        values.put(DBContract.UserEntry.KEY_USERNAME, jsonuser.getString("username"));
                        values.put(DBContract.UserEntry.KEY_PASSWORD, jsonuser.getString("userpass"));

                        uri = context.getContentResolver().insert(
                                DBContract.UserEntry.CONTENT_URI, values);
                        consta.SUCCESS_RESPONSE = 3;
                        Log.e("ShopdukaMenu", values.toString());
                        return String.valueOf(jsonuser.getInt("id_users"));

                    }


                } else if (response.equalsIgnoreCase("tbl_region")) {
                   db = new SqliteDBHelper(context);
                     db.resetTables(DBContract.RegionEntry.REGION_TABLE);
                    jsonArray = json.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonregion = jsonArray.getJSONObject(i);
                        values = new ContentValues();
                        values.put(DBContract.RegionEntry._ID, jsonregion.getInt("id_region"));
                        values.put(DBContract.RegionEntry.KEY_REGIONNAME, jsonregion.getString("region_name"));


                        uri = context.getContentResolver().insert(
                                DBContract.RegionEntry.CONTENT_URI, values);
                        Log.e("ShopdukaMenu", values.toString());
                    }

                    consta.SUCCESS_RESPONSE = 5;


                } else if (response.equalsIgnoreCase("tbl_area")) {
                   db = new SqliteDBHelper(context);
                   db.resetTables(DBContract.AreaEntry.AREA_TABLE);
                    jsonArray = json.getJSONArray("data");
                    ContentValues values = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonarea = jsonArray.getJSONObject(i);
                        values = new ContentValues();
                        values.put(DBContract.AreaEntry._ID, jsonarea.getInt("id_area"));
                        values.put(DBContract.AreaEntry.COLUMN_LOC_KEY_REGION,jsonarea.getInt("id_region"));
                        values.put(DBContract.AreaEntry.COLUMN_LOC_KEY_TERITORY, jsonarea.getInt("id_teritory"));
                        values.put(DBContract.AreaEntry.CAREA_NAME, jsonarea.getString("area_name"));


                        uri = context.getContentResolver().insert(DBContract.AreaEntry.CONTENT_URI, values);
                        Log.e("ShopdukaMenu", values.toString());
                    }
                } else if (response.equalsIgnoreCase("tbl_outlet")) {
                   db = new SqliteDBHelper(context);
                   db.resetTables(DBContract.OutletEntry.OUTLETS_TABLE);
                    jsonArray = json.getJSONArray("data");
                    ContentValues values = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonoutlet= jsonArray.getJSONObject(i);
                        values = new ContentValues();
                        values.put(DBContract.OutletEntry._ID, jsonoutlet.getInt("id_outlet"));
                        values.put(DBContract.OutletEntry.COLUMN_LOC_KEY, jsonoutlet.getInt("id_region"));
                        values.put(DBContract.OutletEntry.COLUMN_LOC_KEY_AREA, jsonoutlet.getInt("id_area"));
                        values.put(DBContract.OutletEntry.COLUMN_LOC_KEY_TERITORY, jsonoutlet.getInt("id_teritory"));
                        values.put(DBContract.OutletEntry.OUTLET_NAME, jsonoutlet.getInt("outlet_name"));
                        uri = context.getContentResolver().insert(DBContract.OutletEntry.CONTENT_URI, values);

                    }
                }

            } else if (Integer.parseInt(json.getString(EXISTS)) > 0) {
                //jsonArray= json.getJSONArray("tip");
                response = json.getString(EXISTS);
                if (Integer.parseInt(response) == 1) {
                    consta.ERROR_RESPONSE = 1;
                } else if (Integer.parseInt(response) == 2) {
                    consta.ERROR_RESPONSE = 2;
                } else if (Integer.parseInt(response) == 3) {
                    consta.ERROR_RESPONSE = 3;
                    consta.ERROR_MESSAGE = json.getString("error_msg");
                } else if (Integer.parseInt(response) == 4) {
                    consta.ERROR_RESPONSE = 4;
                } else if (Integer.parseInt(response) == 5) {
                    consta.ERROR_RESPONSE = 5;
                }


            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    protected void onPostExecute(String result) {
        if (displayprogres == 1) {
            pDialog.dismiss();
        }
        //   refreshMenuItem.collapseActionView();
        //     refreshMenuItem.setActionView(null);


        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}





