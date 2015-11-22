package root.com.jiranimmicrocredit.BackgroudThread;

/**
 * Created by root on 11/1/15.
 */

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import root.com.jiranimmicrocredit.network.JsonHandler;
import root.com.jiranimmicrocredit.providers.DBContract;
import root.com.jiranimmicrocredit.storage.SqlDBHandler;
import root.com.jiranimmicrocredit.utils.Constants;
import root.com.jiranimmicrocredit.utils.SetterGetters;


public class BackGroundThread extends AsyncTask<ContentValues,String,String> {
    JSONObject json;
    Context context;
    String message;
    ProgressDialog pDialog;
    JSONArray jsonArray;
    JSONObject jsonproduct,jsonshoppin,jsonuser;
    String response;
    ListView mListview;
    String TAG="ProcessingTAsk";
    SqlDBHandler db;

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
    private JsonHandler handler;


    private String companyName;
    private String model;
    private String productName;
    private String uniquecode;
    private int iddatafile;
    private  int idproduct;
    Uri _uri;
    SetterGetters setter;

    SharedPreferences userPref;
    private String errormsg;

    public BackGroundThread(Context context,String message,int show) {
        //,MenuItem item
        handler=new JsonHandler();
        setter=new SetterGetters();
        this.context=context;
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
                    db=new SqlDBHandler(context);
                    response = json.getString(USERACTION);
                    if (response.equalsIgnoreCase(Constants.NEWUSER)) {
                        JSONObject profile=null;
                        db.resetTables(DBContract.Profile.PROFILE_TABLE);
                        jsonArray = json.getJSONArray(DATA);
                        ContentValues users = new ContentValues();
                        Log.e(TAG, "  " + jsonArray.toString() + "  count " + jsonArray.length());


                        for (int i = 0; i < jsonArray.length(); i++) {
                            profile = jsonArray.getJSONObject(i);
                            users.put(DBContract.Profile._ID, profile.getInt(Constants.PARAM_ID_APPLICANT));
                            users.put(DBContract.Profile.KEY_FIRSTNAME, profile.getString(Constants.PARAM_FIRSTNAME));
                            users.put(DBContract.Profile.KEY_SECONDNAME, profile.getString(Constants.PARAM_SECONDNAME));
                            users.put(DBContract.Profile.KEY_LASTNAME, profile.getString(Constants.PARAM_LASTNAME));
                            users.put(DBContract.Profile.KEY_IDNUMBER, profile.getString(Constants.PARAM_IDNUMBER));
                            users.put(DBContract.Profile.KEY_STATUS, profile.getInt(Constants.PARAM_STATUS));
                            users.put(DBContract.Profile.KEY_MOBILE, profile.getString(Constants.PARAM_PHONE));
                            users.put(DBContract.Profile.KEY_EMAIL, profile.getString(Constants.PARAM_EMAIL));
                            users.put(DBContract.Profile.KEY_TIMEADDED, profile.getString(Constants.PARAM_TIMEADDED));

                            _uri = context.getContentResolver().insert(DBContract.Profile.CONTENT_URI, users);

                        }
                        context.getContentResolver().notifyChange(_uri, null);
                        setter.setResponse(response);
                        return response;


                    } else if (response.equalsIgnoreCase(Constants.ACTION_BUSINESS)) {
                        ContentValues values = new ContentValues();
                        db.resetTables(DBContract.Business.BUSINESS_TABLE);
                        jsonArray = json.getJSONArray(DATA);
                        JSONObject Business = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Business = jsonArray.getJSONObject(i);
                            values.put(DBContract.Business._ID, Business.getInt(Constants.PARAM_ID_BUSINESS));
                            values.put(DBContract.Business.KEY_BUSINESSNAME, Business.getString(Constants.PARAM_BUSINESS_NAME));
                            values.put(DBContract.Business.KEY_BUSINESSTYPE, Business.getString(Constants.PARAM_ID_BUSINES_TYPE));
                            values.put(DBContract.Business.KEY_IDAPPLICANT, Business.getString(Constants.PARAM_ID_APPLICANT));
                            values.put(DBContract.Business.KEY_INCOME, Business.getInt(Constants.PARAM_INCOME));
                            values.put(DBContract.Business.KEY_AVATAR, Business.getString(Constants.PARAM_AVATAR));
                            values.put(DBContract.Business.KEY_LOCATION, Business.getString(Constants.PARAM_LOCATION));
                            values.put(DBContract.Business.KEY_TURNOVER, Business.getInt(Constants.PARAM_TURNOVER));
                            values.put(DBContract.Business.KEY_YEAR_OF_BUSINESS, Business.getString(Constants.PARAM_YEAR_OF_BUSINESS));
                            values.put(DBContract.Business.KEY_TIMEADDED, Business.getString(Constants.PARAM_TIMEADDED));

                            _uri = context.getContentResolver().insert(DBContract.Business.CONTENT_URI, values);
                        }
                        context.getContentResolver().notifyChange(_uri, null);
                        setter.setResponse(response);
                        return response;



                    } else if (response.equalsIgnoreCase(Constants.ACTION_EMPLOYMENT)) {


                        db.resetTables(DBContract.Employment.EMPLOYMENT_TABLE);
                        jsonArray = json.getJSONArray(DATA);
                        Log.e(TAG, "  " +  jsonArray.toString()+ "  count " + jsonArray.length());
                        JSONObject areport = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            areport = jsonArray.getJSONObject(i);
                            Log.e(TAG, "  object number " + i + " " + areport.toString());
                            ContentValues employment = new ContentValues();

                            employment.put(DBContract.Employment._ID, areport.getInt(Constants.PARAM_ID_EMPLOYMENT));
                            employment.put(DBContract.Employment.KEY_EMPLOYERNAME, areport.getString(Constants.PARAM_EMPLOYER_NAME));
                            employment.put(DBContract.Employment.KEY_IDAPPLICANT, areport.getString(Constants.PARAM_ID_APPLICANT));
                            employment.put(DBContract.Employment.KEY_LOCATION, areport.getString(Constants.PARAM_LOCATION));
                            employment.put(DBContract.Employment.KEY_DEPARTMENT, areport.getString(Constants.PARAM_DEPARTMENT));
                            employment.put(DBContract.Employment.KEY_AVATAR, areport.getString(Constants.PARAM_AVATAR));
                            employment.put(DBContract.Employment.KEY_PARAM_TERMS_OF_EMPLOYMENT, areport.getString(Constants.PARAM_TERMS_OF_EMPLOYMENT));
                            employment.put(DBContract.Employment.KEY_INCOME, areport.getInt(Constants.PARAM_INCOME));
                            employment.put(DBContract.Employment.KEY_TIMEADDED, areport.getString(Constants.PARAM_TIMEADDED));


                            _uri = context.getContentResolver().insert(DBContract.Employment.CONTENT_URI, employment);
                        }
                        context.getContentResolver().notifyChange(_uri, null);
                        setter.setResponse(response);
                        return response;


                    } else if (response.equalsIgnoreCase(Constants.ACTION_GURANTORS)) {


                        db.resetTables(DBContract.Gurantors.GURANTORS_TABLE);
                        jsonArray = json.getJSONArray(DATA);
                        Log.e(TAG, "  " +  jsonArray.toString()+ "  count " + jsonArray.length());
                        JSONObject areport = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            areport = jsonArray.getJSONObject(i);
                            Log.e(TAG, "  object number " + i + " " + areport.toString());
                            ContentValues gurantors = new ContentValues();

                            gurantors.put(DBContract.Gurantors._ID, areport.getInt(Constants.PARAM_ID_GURANTORS));
                            gurantors.put(DBContract.Gurantors.KEY_FIRSTNAME, areport.getString(Constants.PARAM_FIRSTNAME));
                            gurantors.put(DBContract.Gurantors.KEY_SECONDNAME, areport.getString(Constants.PARAM_SECONDNAME));
                            gurantors.put(DBContract.Gurantors.KEY_IDNUMBER, areport.getString(Constants.PARAM_IDNUMBER));
                            gurantors.put(DBContract.Gurantors.KEY_EMAIL, areport.getString(Constants.PARAM_EMAIL));
                            gurantors.put(DBContract.Gurantors.KEY_RESIDENCE, areport.getString(Constants.PARAM_RESIDENCE));
                            gurantors.put(DBContract.Gurantors.KEY_MOBILE, areport.getString(Constants.PARAM_PHONE));
                            gurantors.put(DBContract.Gurantors.KEY_TIMEADDED, areport.getString(Constants.PARAM_TIMEADDED));
                            gurantors.put(DBContract.Gurantors.KEY_LASTNAME, areport.getString(Constants.PARAM_LASTNAME));
                            gurantors.put(DBContract.Gurantors.KEY_AVATAR, areport.getString(Constants.PARAM_AVATAR));
                            gurantors.put(DBContract.Gurantors.KEY_GUARANTORTYPE, areport.getString(Constants.PARAM_GUARANTORTYPE));

                            _uri = context.getContentResolver().insert(DBContract.Gurantors.CONTENT_URI, gurantors);
                        }
                        context.getContentResolver().notifyChange(_uri, null);
                       setter.setResponse(response);
                        return response;

                    }else if (response.equalsIgnoreCase(Constants.USERACTION_LOANCATEGORY)) {

                        db.resetTables(DBContract.LoanCluster.LOAN_CLUSTER_TABLE);
                        jsonArray = json.getJSONArray(DATA);
                        Log.e(TAG, "  " +  jsonArray.toString()+ "  count " + jsonArray.length());
                        JSONObject areport = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            areport = jsonArray.getJSONObject(i);
                            Log.e(TAG, "  object number " + i + " " + areport.toString());
                            ContentValues loanCluster = new ContentValues();


                            loanCluster.put(DBContract.Loans._ID, areport.getInt(Constants.PARAM_ID_LOAN));
                            loanCluster.put(DBContract.Loans.KEY_LOANAME, areport.getString(Constants.PARAM_LOANNAME));
                            loanCluster.put(DBContract.Loans.KEY_INSTALMENTAMOUNT, areport.getInt(Constants.PARAM_INSTALMENTAMOUNT));
                            loanCluster.put(DBContract.Loans.KEY_LOANAMOUNT, areport.getInt(Constants.PARAM_LOANAMOUNT));
                            loanCluster.put(DBContract.Loans.KEY_LOANTYPE, areport.getString(Constants.PARAM_LOANTYPE));
                            loanCluster.put(DBContract.Loans.KEY_REPAYMENTPERIOD, areport.getString(Constants.PARAM_PAYMENTPERIOD));
                            loanCluster.put(DBContract.Loans.KEY_STATUS, areport.getInt(Constants.PARAM_STATUS));
                            loanCluster.put(DBContract.Loans.KEY_TIMEADDED, areport.getString(Constants.PARAM_TIMEADDED));



                            _uri = context.getContentResolver().insert(DBContract.LoanCluster.CONTENT_URI, loanCluster);
                        }
                        context.getContentResolver().notifyChange(_uri, null);
                        setter.setResponse(response);
                        return response;


                    } else if (response.equalsIgnoreCase(Constants.USERACTION_LOANS)) {

                        db.resetTables(DBContract.Loans.LOAN_TABLE);
                        jsonArray = json.getJSONArray(DATA);
                        Log.e(TAG, "  " +  jsonArray.toString()+ "  count " + jsonArray.length());
                        JSONObject areport = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            areport = jsonArray.getJSONObject(i);
                            Log.e(TAG, "  object number " + i + " " + areport.toString());
                            ContentValues loan = new ContentValues();


                            loan.put(DBContract.Loans._ID, areport.getInt(Constants.PARAM_ID_LOAN));
                            loan.put(DBContract.Loans.KEY_LOANAME, areport.getString(Constants.PARAM_LOANNAME));
                            loan.put(DBContract.Loans.KEY_INSTALMENTAMOUNT, areport.getInt(Constants.PARAM_INSTALMENTAMOUNT));
                            loan.put(DBContract.Loans.KEY_LOANAMOUNT, areport.getInt(Constants.PARAM_LOANAMOUNT));
                            loan.put(DBContract.Loans.KEY_LOANTYPE, areport.getString(Constants.PARAM_LOANTYPE));
                            loan.put(DBContract.Loans.KEY_REPAYMENTPERIOD, areport.getString(Constants.PARAM_PAYMENTPERIOD));
                            loan.put(DBContract.Loans.KEY_STATUS, areport.getInt(Constants.PARAM_STATUS));
                            loan.put(DBContract.Loans.KEY_TIMEADDED, areport.getString(Constants.PARAM_TIMEADDED));



                            _uri = context.getContentResolver().insert(DBContract.Loans.CONTENT_URI, loan);
                        }
                        context.getContentResolver().notifyChange(_uri, null);
                        setter.setResponse(response);
                        return response;


                    } else if (response.equalsIgnoreCase(Constants.USERACTION_LOANAPPLICATION)) {
                   db.resetTables(DBContract.LoanApplication.LOANAPPLICATION_TABLE);
                        jsonArray = json.getJSONArray(DATA);
                        Log.e(TAG, "  " +  jsonArray.toString()+ "  count " + jsonArray.length());
                        JSONObject areport = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            areport = jsonArray.getJSONObject(i);
                            Log.e(TAG, "  object number " + i + " " + areport.toString());
                            ContentValues loanApplication = new ContentValues();


                            loanApplication.put(DBContract.Loans._ID, areport.getInt(Constants.PARAM_ID_LOANAPPLICATION));
                            loanApplication.put(DBContract.LoanApplication.KEY_LOANAME, areport.getString(Constants.PARAM_LOANNAME));
                            loanApplication.put(DBContract.LoanApplication.KEY_INSTALMENTAMOUNT, areport.getInt(Constants.PARAM_INSTALMENTAMOUNT));
                            loanApplication.put(DBContract.LoanApplication.KEY_LOANAMOUNT, areport.getInt(Constants.PARAM_LOANAMOUNT));
                            loanApplication.put(DBContract.LoanApplication.KEY_LOANTYPE, areport.getString(Constants.PARAM_LOANTYPE));
                            loanApplication.put(DBContract.LoanApplication.KEY_REPAYMENTPERIOD, areport.getString(Constants.PARAM_LOAN_REPAYMENTDATE));
                            loanApplication.put(DBContract.LoanApplication.KEY_STATUS, areport.getInt(Constants.PARAM_STATUS));
                            loanApplication.put(DBContract.LoanApplication.KEY_PURPOSE, areport.getString(Constants.PARAM_LOAN_PURPOSE));
                            loanApplication.put(DBContract.LoanApplication.KEY_TIMEADDED, areport.getString(Constants.PARAM_TIMEADDED));

                            _uri = context.getContentResolver().insert(DBContract.LoanApplication.CONTENT_URI, loanApplication);


                        }
                        context.getContentResolver().notifyChange(_uri, null);
                        setter.setResponse(response);
                        return response;


                    } else if (response.equalsIgnoreCase(Constants.ACTION_BUSINESSTYPE)) {
                        db.resetTables(DBContract.BusinessType.BUSINESSTYPE_TABLE);
                        jsonArray = json.getJSONArray(DATA);
                        Log.e(TAG, "  " +  jsonArray.toString()+ "  count " + jsonArray.length());
                        JSONObject areport = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            areport = jsonArray.getJSONObject(i);
                            Log.e(TAG, "  object number " + i + " " + areport.toString());
                            ContentValues businessType = new ContentValues();


                            businessType.put(DBContract.BusinessType._ID, areport.getInt(Constants.PARAM_ID_BUSINES_TYPE));
                            businessType.put(DBContract.BusinessType.KEY_NATUREOFBUSINESS, areport.getString(Constants.PARAM_NATURE_OF_BUSINESS));
                            businessType.put(DBContract.BusinessType.KEY_TIMEADDED, areport.getString(Constants.PARAM_TIMEADDED));

                            _uri = context.getContentResolver().insert(DBContract.Gurantors.CONTENT_URI, businessType);


                        }
                        context.getContentResolver().notifyChange(_uri, null);
                        setter.setResponse(response);
                        return response;


                    } else if (response.equalsIgnoreCase(Constants.ACTION_PROPERTY)) {
                        db.resetTables(DBContract.Property.PROPERTY_TABLE);
                        jsonArray = json.getJSONArray(DATA);
                        Log.e(TAG, "  " +  jsonArray.toString()+ "  count " + jsonArray.length());
                        JSONObject areport = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            areport = jsonArray.getJSONObject(i);
                            Log.e(TAG, "  object number " + i + " " + areport.toString());
                            ContentValues property = new ContentValues();


                            property.put(DBContract.Property._ID, areport.getInt(Constants.PARAM_ID_PROPERTY));
                            property.put(DBContract.Property.KEY_PROPERTYNAME, areport.getString(Constants.PARAM_PROPERTYNAME));
                            property.put(DBContract.Property.KEY_PLOTNUMBER, areport.getString(Constants.PARAM_PLOTNUMBER));
                            property.put(DBContract.Property.KEY_INCOME, areport.getInt(Constants.PARAM_INCOME));
                            property.put(DBContract.Property.KEY_LOCATION, areport.getString(Constants.PARAM_LOCATION));
                            property.put(DBContract.Property.KEY_SPECIFYOWNERSHIP, areport.getString(Constants.PARAM_SPECIFYOWNERSHIP));
                            property.put(DBContract.Property.KEY_PARAM_OCCUPANCY, areport.getInt(Constants.PARAM_OCCUPANCY));
                            property.put(DBContract.Property.KEY_TIMEADDED, areport.getString(Constants.PARAM_TIMEADDED));

                            _uri = context.getContentResolver().insert(DBContract.Gurantors.CONTENT_URI, property);


                        }
                        context.getContentResolver().notifyChange(_uri, null);
                        setter.setResponse(response);
                        return response;

                    } else if (response.equalsIgnoreCase(Constants.USERACTION_OHTEROCUPATION)) {
                        db.resetTables(DBContract.OtherOccupations.OTHEROCCUPATION_TABLE);
                        jsonArray = json.getJSONArray(DATA);
                        Log.e(TAG, "  " +  jsonArray.toString()+ "  count " + jsonArray.length());
                        JSONObject areport = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            areport = jsonArray.getJSONObject(i);
                            Log.e(TAG, "  object number " + i + " " + areport.toString());
                            ContentValues otherOccupations = new ContentValues();


                            otherOccupations.put(DBContract.OtherOccupations._ID, areport.getInt(Constants.PARAM_ID_PROPERTY));
                            otherOccupations.put(DBContract.OtherOccupations.KEY_OCCUPATIONAME, areport.getString(Constants.PARAM_PROPERTYNAME));
                            otherOccupations.put(DBContract.OtherOccupations.KEY_INCOME, areport.getInt(Constants.PARAM_INCOME));
                            otherOccupations.put(DBContract.OtherOccupations.KEY_LOCATION, areport.getString(Constants.PARAM_LOCATION));
                            otherOccupations.put(DBContract.OtherOccupations.KEY_TIMEADDED, areport.getString(Constants.PARAM_TIMEADDED));

                            _uri = context.getContentResolver().insert(DBContract.OtherOccupations.CONTENT_URI, otherOccupations);


                        }
                        context.getContentResolver().notifyChange(_uri, null);
                        setter.setResponse(response);
                        return response;
                    }

                } else if (Integer.parseInt(json.getString(Constants.PARAM_ERROR)) > 0) {
                    //jsonArray= json.getJSONArray("tip");
                     response = json.getString(Constants.PARAM_TYPE);
                     errormsg=json.getString(Constants.PARAM_ERROR);

                    if (response.equalsIgnoreCase(Constants.ACTION_BUSINESS)) {
                             setErrorMsg();
                        return Constants.PARAM_ERROR;

                    } else if (response.equalsIgnoreCase(Constants.ACTION_BUSINESS)) {

                        setErrorMsg();
                        return Constants.PARAM_ERROR;
                    }else if (response.equalsIgnoreCase(Constants.ACTION_GURANTORS)) {

                        setErrorMsg();
                        return Constants.PARAM_ERROR;

                    }else if (response.equalsIgnoreCase(Constants.ACTION_OTHEROCCUPATION)) {

                        setErrorMsg();
                        return Constants.PARAM_ERROR;

                    }else if (response.equalsIgnoreCase(Constants.ACTION_LOANAPPLICATION)) {

                        setErrorMsg();
                        return Constants.PARAM_ERROR;

                    }else if (response.equalsIgnoreCase(Constants.ACTION_PROPERTY)) {

                        setErrorMsg();
                        return Constants.PARAM_ERROR;

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


    protected void onPostExecute(final String result){
        if(displayprogres==1) {
            pDialog.dismiss();
        }
if(setter.getResponse()!=""||setter.getResponse()!=null) {
    if (result.equalsIgnoreCase(Constants.ACTION_BUSINESS)) {

    } else if (result.equalsIgnoreCase(Constants.USERACTION_LOANAPPLICATION)) {
        Toast.makeText(context,Constants.SUCCESS,Toast.LENGTH_SHORT).show();

    } else if (result.equalsIgnoreCase(Constants.USERACTION_OHTEROCUPATION)) {
        Toast.makeText(context,Constants.SUCCESS,Toast.LENGTH_SHORT).show();

    } else if (result.equalsIgnoreCase(Constants.ACTION_GURANTORS)) {
        Toast.makeText(context,Constants.SUCCESS,Toast.LENGTH_SHORT).show();

    } else if (result.equalsIgnoreCase(Constants.ACTION_EMPLOYMENT)) {
        Toast.makeText(context,Constants.SUCCESS,Toast.LENGTH_SHORT).show();

    } else if (result.equalsIgnoreCase(Constants.ACTION_PROPERTY)) {
        Toast.makeText(context,Constants.SUCCESS,Toast.LENGTH_SHORT).show();

    } else if (result.equalsIgnoreCase(Constants.USERACTION_LOANS)) {

    } else if (result.equalsIgnoreCase(Constants.USERACTION_LOANCATEGORY)) {

    } else if(result.equalsIgnoreCase(Constants.PARAM_ERROR)) {

        if(setter.getResponse().equalsIgnoreCase(Constants.PARAM_FAILED_INSERT)){
            Toast.makeText(context,Constants.MSG_FAILED_INSERT,Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,setter.getResponse() + "Exists",Toast.LENGTH_SHORT).show();
        }

    }

            }else{
                Toast.makeText(context,"Error occured" , Toast.LENGTH_SHORT).show();
}


    }






    public void setErrorMsg() throws JSONException {

        if(json.getInt(Constants.PARAM_FAILED_INSERT)>0){
            setter.setResponse(Constants.PARAM_FAILED_INSERT);
        }else{
            setter.setResponse(json.getString(Constants.PARAM_EXISTS));
        }

    }





}

