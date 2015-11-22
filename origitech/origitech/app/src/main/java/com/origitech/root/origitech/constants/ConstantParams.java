package com.origitech.root.origitech.constants;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by root on 9/30/15.
 */
public class ConstantParams {

    public static final String ACTION="action";
    public static final String INTERNET_MSG ="Connection Failed" ;
    public static final String IMAGEURI ="IMAGEURI" ;
    public static final String PREF_USERID ="USERCREDENTIAL" ;
    public static final String FRAGVAL = "fragval";
    public static final String RESELT_VERIFYCODE = "verifycode";
    public static final String RESULT_CLAIMED_CODES = "getclaims";
    public static final String RESELT_VERIFYCODE_FAILD = "verifycodefailed";
    public static final String USERNEW ="newuser" ;
    public static final String EMAILEXIST ="Email Exists" ;
    public static final String FAILEDINSERT = "Failed Insert";
    public static final String MOBILEEXIST =" Mobile Exists" ;
    public static final String CODE ="verifiedcode" ;

    public static final String DATAFILEID = "id_datafile";
    public static final String PRODUCTID =" id_product" ;
    public static final String RESULT_REPORT = "getreports" ;

    public static final String RESULT_BACKUP = "uploads" ;
    public static final String STATUS_JAIL ="Jailed";
    public static final String STATUS_FINED ="Fined" ;
    public static final String GETVERIFICATION = "getverification";


    public static final String BROADCAST_ACTION = "com.origitech.root.origitech.BROADCAST";
    public static final String EXTENDED_DATA_STATUS = "com.origitech.root.origitech.STATUS";
    public static final String EXTENDED_STATUS_LOG = "com.origitech.root.origitech.LOG";


    // The download is starting
    public static final int STATE_ACTION_STARTED = 0;

    // The background thread is connecting
    public static final int STATE_ACTION_CONNECTING = 1;

    // The background thread is parsing
    public static final int STATE_ACTION_PARSING = 2;

    // The background thread is writing data to the content provider
    public static final int STATE_ACTION_WRITING = 3;

    // The background thread is done
    public static final int STATE_ACTION_COMPLETE = 4;

    // The background thread is doing logging
    public static final int STATE_LOG = -1;
    public static final String RESULT_BLACKLIST = "getblacklist";
    public static final String GOTUSERCLAIM ="saving..." ;
    public static final String GOTOBACKUP = "launching backup";
    public static final String GOTOBACKUPLIST ="updating backuplist" ;


    public static int ID_DATAFILE=0;
    public static int ID_PRODUCT=0;
    public static int ID_USER=0;

    public static int SUCCESS_RESPONSE = 0;
    public static int ERROR_RESPONSE=0;
    public static  String ERROR_MESSAGE;
    public static Uri IMAGE=null;
    public static final String PREF_DATA="Activity_Data";
    public static final String AVATAR="avatar";
    public static final String UPLOAD_TYPE="type";
    public static final String UPLOAD_TABLE="table";
    public static final String BACKUP="backup";
    public static final String UPLOADS="uploads";
    public static final String IDUSER="id_users";
    public static final String UPLOAD_BACKUP="upload_backup";

    public static final String USERID="id_users";



    public static final String FAKE_STICKER_OFFENSE="Fake Sticker";
    public static final String FAKE_PRODUCT_OFFENSE="Fake Product";

    public static final String GOTOCLAIMLIST="Updating ClaimList";
    public static final String GOTOCLAIMCODE="gotoclaimcode";
    public static final String GOTINTENT="gotToIntent";
    public static final String GOTOREPORTLIST="updating reportList";
    public static final String GOTOUSERPROFILE="updating user profile";
            ;
    public static final String CONNECTION_FAILED="No internet connection";
    public static final String REFRESH_FAILED="Refresh failed";

    public static final int LAUNCH_USERPROFILE = 0;
    public static String CLAIMCODE="";

    public static String ConnectionStatus="";
    public static String status="";
}
