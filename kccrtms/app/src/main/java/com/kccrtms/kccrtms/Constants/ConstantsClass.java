package com.kccrtms.kccrtms.Constants;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 10/12/15.
 */
public class ConstantsClass {

    public static final String DATAPREF ="datapref" ;
  
    public static final String SALESPRICE ="saleprice" ;
   
    public static final String SALES ="sales" ;
    public static int ITEMCLICKED;
    public static int SUCCESS_RESPONSE;
    public static int ERROR_RESPONSE;
    public static String ERROR_MESSAGE;
    public static String JSON;
    public static String LOGINPREF="loginpref";
    public static String ISLOOGED_IN="loggedin";
    public static String USERID="userid";
    public static boolean PROCESDONE;
    public static Uri URI = null;
    public static List<HashMap<String, Object>> PERSONS;
    public static String INTERNET_MSG = "Check Your Intenet connection";
    public static final String GOOGLE_PROJECTID = "1016364469029";

    public static final String SEND_BUNDLE_OUTLETS="Outlet_bundle";
    public static final String SEND_BUNDLE_RECORDS="Records_bundle";

    public static final String REGIONID="regionId";
    public static final String TERITORYID="teritoriId";
    public static final String AREAID="areaid";
    public static final String OUTLETID="outletid";

    public static final String UNITPRICE = "unitprice";
    public static final String REMARKS = "remarks";
    public static final String LOCATION = "location";

    //name
    public static final String TAG = "action";
    public static final String IDUSERS = "id_users";
    public static final String PRODUCTID = "id_product";
    public static final String IDREGION = "id_region";
    public static final String USERNAME = "username";
    public static final String IDTERITORY= "id_teritory";
    public static final String IDAREA = "id_area";
    public static final String USERPASS = "password";
    public static final String IDOUTLET = "id_outlet";
    public static final String ORDERQTY = "order_qty";
    public static final String USERACTTION = "useraction";
    public static final String SALESQTY = "salesqty";
    public static final String OUTLATENAME = "outletname";
    public static final String CONTACTPERSON = "contactperson";
    public static final String PHONE = "phone";
    public static final String CHANNEL = "channel";
    public static final String INSERTSALES = "tbl_sales";
    public static final String INSERTORDERS = "tbl_orders";



    public static boolean isFieldEmpty(EditText[] editTexts) {

        for (int i = 0; i < editTexts.length; i++) {
            if (TextUtils.isEmpty(editTexts[i].getText().toString()) && editTexts[i].getText().toString().length() < 8) {
                editTexts[i].setError("Field empty");
                return true;
            }

        }
        return false;

    }

}
