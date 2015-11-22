package com.origitech.root.origitech.network;


        import android.app.ProgressDialog;
        import android.content.ContentValues;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.support.v7.app.AlertDialog;
        import android.text.Html;
        import android.util.Log;
        import android.view.MenuItem;
        import android.widget.ListView;
        import android.widget.Toast;

        import com.origitech.root.origitech.BackupList;
        import com.origitech.root.origitech.ClaimList;
        import com.origitech.root.origitech.MainActivity;
        import com.origitech.root.origitech.fragments.Userprofile;
        import com.origitech.root.origitech.constants.ConstantParams;
        import com.origitech.root.origitech.databases.Database;
        import com.origitech.root.origitech.Blacklisted;
        import com.origitech.root.origitech.provider.DBContract;


        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;


public class BackgroundThread extends AsyncTask<ContentValues,String,String> {
    JSONObject json;
    private JsonHandler handler;
    Context context;
    String message;
    ProgressDialog pDialog;
    JSONArray jsonArray;
    JSONObject jsonproduct,jsonshoppin,jsonuser;
    String response;
    ListView mListview;
    String TAG="ProcessingTAsk";
    Database db;
    private ConstantParams consta;
    private static final String SUCCESS="success";
    private static final String USERACTION="type";
    private static final String EXISTS="exists";
    private static final String ERROR="error";

    private static String id_users="";

    private static final String DATA="data";

    private int displayprogres;
    private MenuItem refreshMenuItem;
    private ContentValues values;
    private Uri uri;


    private String companyName;
    private String model;
    private String productName;
    private String uniquecode;
    private int iddatafile;
    private  int idproduct;
    Uri _uri;

    SharedPreferences userPref;
    public BackgroundThread(Context context,String message
            ,int show//,MenuItem item
                            //
    ) {
        handler = new JsonHandler();
        this.context=context;
        userPref= context.getSharedPreferences(ConstantParams.PREF_USERID, Context.MODE_PRIVATE);
        this.message=message;
        displayprogres=show;
        //  refreshMenuItem=item;

    }



    public void showProgress(){
        pDialog=new ProgressDialog(context);
        pDialog.setMessage(Html.fromHtml("<b> <i> " + message + "</i> </b>"));
        pDialog.setCancelable(false);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.show();
    }


    protected void onPreExecute(){
        super.onPreExecute();
        if(displayprogres==1){
            showProgress();
        }else{
            //  refreshMenuItem.setActionView(R.layout.custom_progressbar);

            //  refreshMenuItem.expandActionView();
        }



    }
    protected  String doInBackground(ContentValues... params){


        try {
            try {

                json = handler.JsonConnection(params[0]);


            } catch (IOException e) {
                e.printStackTrace();
            }

            if(json!=null){

            if (Integer.parseInt(json.getString(SUCCESS)) > 0) {
                db=new Database(context);
                response = json.getString(USERACTION);
                if (response.equalsIgnoreCase(ConstantParams.USERNEW)) {
                    JSONObject profile=null;
                    db.resetTables(DBContract.Profile.PROFILE_TABLE);
                    jsonArray = json.getJSONArray(DATA);
                    ContentValues users = new ContentValues();
                    Log.e(TAG, "  " +  jsonArray.toString()+ "  count " + jsonArray.length());


                  for (int i = 0; i < jsonArray.length(); i++) {
                        profile = jsonArray.getJSONObject(i);


                        users.put(DBContract.Profile._ID, profile.getInt("id_users"));
                        users.put(DBContract.Profile.KEY_FIRSTNAME, profile.getString("firstName"));
                        users.put(DBContract.Profile.KEY_SECONDNAME, profile.getString("secondName"));
                        users.put(DBContract.Profile.KEY_MOBILE, profile.getString("mobile"));
                        users.put(DBContract.Profile.KEY_EMAIL, profile.getString("email"));

                        _uri = context.getContentResolver().insert(DBContract.Profile.CONTENT_URI, users);

                  }
                    context.getContentResolver().notifyChange(_uri, null);

                    if(message.equalsIgnoreCase(ConstantParams.GOTOCLAIMLIST)){
                        response=ConstantParams.GOTOCLAIMLIST;
                    }else if(message.equalsIgnoreCase(ConstantParams.GOTOREPORTLIST)){
                        response=ConstantParams.GOTOREPORTLIST;
                    }else if(message.equalsIgnoreCase(ConstantParams.GOTOUSERPROFILE)){
                        response=ConstantParams.GOTOUSERPROFILE;
                    }else if(message.equalsIgnoreCase(ConstantParams.GOTOBACKUPLIST)){
                        response=ConstantParams.GOTOBACKUPLIST;
                    }else {
                        response = ConstantParams.GOTUSERCLAIM;
                    }

                    //consta.SUCCESS_RESPONSE = 1;
                } else if (response.equalsIgnoreCase(ConstantParams.RESULT_CLAIMED_CODES)) {
                    ContentValues values = new ContentValues();
                    db.resetTables(DBContract.Verification.VERIFY_TABLE);
                    jsonArray = json.getJSONArray(DATA);
                    JSONObject Verification = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Verification = jsonArray.getJSONObject(i);


                        values.put(DBContract.Verification._ID, Verification.getInt("id_verify"));
                        values.put(DBContract.Verification.KEY_PRODUCT_NAME, Verification.getString("name"));
                        values.put(DBContract.Verification.KEY_PRODUCT_MODEL, Verification.getString("model"));
                        values.put(DBContract.Verification.KEY_PRODUCT_TYPE, Verification.getString("type"));
                        values.put(DBContract.Verification.KEY_VERIFICATION_CODE, Verification.getString("unique_code"));
                        values.put(DBContract.Verification.KEY_AVATAR, Verification.getString("avatar"));
                        values.put(DBContract.Verification.KEY_PRODUCT_OWNER, Verification.getString("companyName"));
                        values.put(DBContract.Verification.KEY_TIME_ADDED, Verification.getString("timeadded"));
                       // Log.e(TAG,jsonArray.toString());
                        _uri = context.getContentResolver().insert(DBContract.Verification.CONTENT_URI, values);
                    }
                    context.getContentResolver().notifyChange(_uri, null);
                    response = ConstantParams.RESULT_CLAIMED_CODES;



                } else if (response.equalsIgnoreCase(ConstantParams.RESELT_VERIFYCODE)) {



                    response = ConstantParams.RESELT_VERIFYCODE;

                    jsonArray = json.getJSONArray(DATA);
                    companyName = jsonArray.getJSONObject(0).getString("companyName").toString();
                    model = jsonArray.getJSONObject(0).getString("model").toString();
                    productName = jsonArray.getJSONObject(0).getString("name");
                    uniquecode = jsonArray.getJSONObject(0).getString("unique_code");
                    ConstantParams.ID_DATAFILE = jsonArray.getJSONObject(0).getInt("id_datafile");
                    ConstantParams.ID_PRODUCT = jsonArray.getJSONObject(0).getInt("id_product");




                } else if (response.equalsIgnoreCase(ConstantParams.RESULT_REPORT)) {


                    db.resetTables(DBContract.Report.REPORT_TABLE);
                    jsonArray = json.getJSONArray(DATA);
                    Log.e(TAG, "  " +  jsonArray.toString()+ "  count " + jsonArray.length());
                    JSONObject areport = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        areport = jsonArray.getJSONObject(i);
                        Log.e(TAG, "  object number " + i + " " + areport.toString());
                        ContentValues reportval = new ContentValues();

                        reportval.put(DBContract.Report._ID, areport.getInt("id_report"));
                        reportval.put(DBContract.Report.KEY_PRODUCT_NAME, areport.getString("productName"));
                        reportval.put(DBContract.Report.KEY_PRODUCT_MODEL, areport.getString("model"));
                        reportval.put(DBContract.Report.KEY_LOCATION, areport.getString("location"));
                        reportval.put(DBContract.Report.KEY_PRODUCT_SHOP, areport.getString("shop"));
                        reportval.put(DBContract.Report.KEY_DEALERNAME, areport.getString("dealer_name"));
                        reportval.put(DBContract.Report.KEY_STATUS, areport.getString("status"));
                        reportval.put(DBContract.Report.KEY_CASESTATUS, areport.getString("case_state"));

                        _uri = context.getContentResolver().insert(DBContract.Report.CONTENT_URI, reportval);
                    }
                    context.getContentResolver().notifyChange(_uri, null);
                return ConstantParams.RESULT_REPORT;

                } else if (response.equalsIgnoreCase(ConstantParams.RESULT_BLACKLIST)) {


                    db.resetTables(DBContract.Blacklist.BLACKLIST_TABLE);
                    jsonArray = json.getJSONArray(DATA);
                    Log.e(TAG, "  " +  jsonArray.toString()+ "  count " + jsonArray.length());
                    JSONObject areport = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        areport = jsonArray.getJSONObject(i);
                        Log.e(TAG, "  object number " + i + " " + areport.toString());
                        ContentValues reportval = new ContentValues();

                        reportval.put(DBContract.Report._ID, areport.getInt("id_report"));
                        reportval.put(DBContract.Report.KEY_PRODUCT_NAME, areport.getString("productName"));
                        reportval.put(DBContract.Report.KEY_PRODUCT_MODEL, areport.getString("model"));
                        reportval.put(DBContract.Report.KEY_LOCATION, areport.getString("location"));
                        reportval.put(DBContract.Report.KEY_PRODUCT_SHOP, areport.getString("shop"));
                        reportval.put(DBContract.Report.KEY_DEALERNAME, areport.getString("dealer_name"));
                        reportval.put(DBContract.Report.KEY_STATUS, areport.getString("status"));
                        reportval.put(DBContract.Report.KEY_CASESTATUS, areport.getString("case_state"));

                        _uri = context.getContentResolver().insert(DBContract.Blacklist.CONTENT_URI, reportval);
                    }
                    context.getContentResolver().notifyChange(_uri, null);
                    return ConstantParams.RESULT_BLACKLIST;

                }else if (response.equalsIgnoreCase(ConstantParams.UPLOAD_BACKUP)) {

                    db.resetTables(DBContract.Backup.BACKUP_TABLE);
                    jsonArray = json.getJSONArray(DATA);
                    Log.e(TAG, "  " +  jsonArray.toString()+ "  count " + jsonArray.length());
                    JSONObject areport = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        areport = jsonArray.getJSONObject(i);
                        Log.e(TAG, "  object number " + i + " " + areport.toString());
                        ContentValues reportval = new ContentValues();

                        reportval.put(DBContract.Backup._ID, areport.getInt("id_backup"));
                        reportval.put(DBContract.Backup.KEY_TYPE, areport.getString("type"));
                        reportval.put(DBContract.Backup.KEY_AVATAR, areport.getString("avatar"));
                        reportval.put(DBContract.Backup.KEY_TIME_ADDED, areport.getString("timeadded"));

                        _uri = context.getContentResolver().insert(DBContract.Backup.CONTENT_URI, reportval);
                    }
                    context.getContentResolver().notifyChange(_uri, null);
                    return ConstantParams.UPLOAD_BACKUP;


                } else if (response == "") {
//

                    consta.SUCCESS_RESPONSE = 5;


                } else if (response == "") {

                } else if (response == "") {

                }

            } else if (Integer.parseInt(json.getString(ERROR)) > 0) {
                //jsonArray= json.getJSONArray("tip");
                response = json.getString(USERACTION);


                if (response.equalsIgnoreCase(ConstantParams.RESELT_VERIFYCODE)) {
                    response = ConstantParams.RESELT_VERIFYCODE_FAILD;

                    Log.e("negative  result", response);
                } else if (response.equalsIgnoreCase(ConstantParams.USERNEW)) {




                            if(Integer.parseInt(json.getString("exists_email")) > 0){

                                response = ConstantParams.EMAILEXIST;


                            }else if(Integer.parseInt(json.getString("failed_insert")) > 0){

                                response = ConstantParams.FAILEDINSERT;


                            }else if(Integer.parseInt(json.getString("exists_mobile")) > 0){
                                response = ConstantParams.MOBILEEXIST;


                            }


                } else if (response == "") {
                    consta.ERROR_RESPONSE = 3;
                    consta.ERROR_MESSAGE = json.getString("error_msg");
                } else if (response == "") {
                    consta.ERROR_RESPONSE = 4;
                } else if (response == "") {
                    consta.ERROR_RESPONSE = 5;
                }


            }

        }else{
             message="Error occurred, Try again";
        }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }


    protected void onPostExecute(final String result) {
        if (displayprogres == 1) {
               pDialog.dismiss();
        }
        if (result.equalsIgnoreCase("null")||result==null) {
            // Toast.makeText(context,"result" + result, Toast.LENGTH_LONG).show();
            Log.e(TAG, "result" + result);
        } else {
            if (result.equalsIgnoreCase(ConstantParams.RESULT_CLAIMED_CODES)) {
                Toast.makeText(context, "List updated pull to refresh", Toast.LENGTH_SHORT).show();
            } else if (result.equalsIgnoreCase(ConstantParams.EMAILEXIST)) {
                message = "Email Exists";
            } else if (result.equalsIgnoreCase(ConstantParams.FAILEDINSERT)) {
                message = "Failed Try again";
            } else if (result.equalsIgnoreCase(ConstantParams.MOBILEEXIST)) {
                message = "Mobile Exists";
            } else if (result == ConstantParams.RESELT_VERIFYCODE) {


                final AlertDialog.Builder sbuilder = new AlertDialog.Builder(context);
                sbuilder.setTitle("Code Verification Message");
                sbuilder.setMessage(Html.fromHtml("This is an Original Code for <b><i> " + productName + " </b>from <b>" + companyName + "</b> " +
                        "</i><br>Do you want to claim <b> <i> " + uniquecode + "  </i> </b>"));
                sbuilder.setPositiveButton("Claim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                  /* //int userid = userPref.getInt(ConstantParams.USERID, 0);
                    if (userid == 0 ) {
                    /*
                        userPref.edit().putString(ConstantParams.CODE,uniquecode).commit();

                         ConstantParams.CLAIMCODE="Claimcode";
                        Intent intentMain= new Intent(context, MainActivity.class);
                        context.startActivity(intentMain);

                     //   dialogInterface.cancel();

                    } else {
                    */
                        alertDialog(result, dialogInterface);


                        //   }

                        dialogInterface.cancel();


                    }
                });
                sbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // selectedChoice(ConstantParams.RESELT_VERIFYCODE, 1);
                        dialogInterface.cancel();

                    }
                });
                sbuilder.show();


            } else if (result == ConstantParams.RESELT_VERIFYCODE_FAILD) {

                Log.e("verifycode  result", result);
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Code Verification Message");
                builder.setMessage(Html.fromHtml("This code is fake </i><br>Do you want to Report?</b>"));
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        ConstantParams.CLAIMCODE = "Report";
                        LauchMain();
                        dialogInterface.cancel();

                    }
                });
                builder.show();

            } else if (result == ConstantParams.RESELT_VERIFYCODE_FAILD) {

                ConstantParams.CLAIMCODE = "ClaimList";
            } else if (result.equalsIgnoreCase(ConstantParams.RESULT_REPORT)) {


                ConstantParams.CLAIMCODE = "viewreport";
                Intent intentMain = new Intent(context, MainActivity.class);
                context.startActivity(intentMain);


            } else if (consta.ERROR_RESPONSE == 5) {


            } else if (consta.SUCCESS_RESPONSE == 5) {


            } else if (result.equalsIgnoreCase(ConstantParams.UPLOAD_BACKUP)) {

                ConstantParams.CLAIMCODE = ConstantParams.UPLOAD_BACKUP;
                LauchMain();

                //   Toast.makeText(context,"done uploading",Toast.LENGTH_LONG).show();


            } else if (result.equalsIgnoreCase(ConstantParams.RESULT_BLACKLIST)) {
                Intent intentMain = new Intent(context, Blacklisted.class);
                context.startActivity(intentMain);

            } else if (result.equalsIgnoreCase(ConstantParams.GOTOCLAIMLIST)) {

                ConstantParams.CLAIMCODE = ConstantParams.GOTOCLAIMLIST;
                Intent intentMain = new Intent(context, ClaimList.class);
                context.startActivity(intentMain);

            } else if (result.equalsIgnoreCase(ConstantParams.GOTOREPORTLIST)) {

                ConstantParams.CLAIMCODE = ConstantParams.GOTOREPORTLIST;
                LauchMain();
            } else if (result.equalsIgnoreCase(ConstantParams.GOTOUSERPROFILE)) {
                ConstantParams.CLAIMCODE = ConstantParams.GOTOUSERPROFILE;
                LauchMain();
            } else if (result.equalsIgnoreCase(ConstantParams.GOTOBACKUPLIST)) {
                //ConstantParams.CLAIMCODE=ConstantParams.GOTOBACKUPLIST;
                Intent intentMain = new Intent(context, BackupList.class);
                context.startActivity(intentMain);
            } else if (result.equalsIgnoreCase(ConstantParams.GOTUSERCLAIM)) {

                alertDialog(result, null);


            } else if (result.equalsIgnoreCase(ConstantParams.UPLOAD_BACKUP)) {
                Toast.makeText(context, "No backuped Items", Toast.LENGTH_SHORT).show();
            }


        }
    }

    public void alertDialog(String result ,DialogInterface dialog){

        final  String sresult=result;

        if(dialog!=null){
            dialog.cancel();
        }

        uniquecode=userPref.getString(ConstantParams.CODE,"");
        final AlertDialog.Builder bbbuilder = new AlertDialog.Builder(context);
        bbbuilder.setTitle("Claim Code" );
        bbbuilder.setMessage(Html.fromHtml("This code: <b>"+ uniquecode + " </b><b><i> Will be associated with your account "));
        bbbuilder.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //String userid = userPref.getString(ConstantParams.IDUSER, "none");
                ConstantParams.CLAIMCODE="saveClaim";
                SharedPreferences.Editor edit =userPref.edit();
                edit.putInt(ConstantParams.DATAFILEID, ConstantParams.ID_DATAFILE);
                edit.putInt(ConstantParams.PRODUCTID, ConstantParams.ID_PRODUCT);
                edit.commit();
                LauchMain();
                dialogInterface.cancel();


            }
        });
        bbbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // selectedChoice(ConstantParams.RESELT_VERIFYCODE, 1);
                dialogInterface.cancel();

            }
        });
        bbbuilder.show();


    }


    public void LauchMain(){
        Intent intentMain= new Intent(context, MainActivity.class);
        context.startActivity(intentMain);
    }





}
