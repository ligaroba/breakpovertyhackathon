package com.origitech.root.origitech.functions;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.EditText;

import com.origitech.root.origitech.constants.ConstantParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by root on 9/30/15.
 */
public class Functions {

private static final String ACTION="action";


    //actions
    private static final String USER_ACTION="useraction";
    private static final String CLAIM_ACTION ="verification";
    private static final String UNIQUECODE ="uniquecode" ;

    private static final String REPORTS="reports";
    private static final String VERIFYCODE="verifycode";
    private static final String QUERY_VERIFICATION="queryverification";
    private static final String QUERY_REPORTS="queryreports";
    private static final String USERS="users";
    private static final String QUERY_BRANDS = "querybrands";
    private static final String QUERYREPORTS ="queryreports";
    private static final String BLACKLIST ="getblacklist";
    private static final String QUERYBACKUP ="querybackup" ;
    private static final String BACKUPLIST ="getbackuplist" ;




    //useraction
    private static final String ACTION_INSERT="insert";
    private static final String ACTION_UPDATE="update";
    private static final String ACTION_DELETE="delete";
    private static final String ACTION_GETALL="all";
    private static final String ACTION_BYCAT="bycat";
    private static final String ACTION_CAT="cat";


    //user data
    private static final String FIRSTNAME="firstName";
    private static final String SECONDNAME="secondName";
    private static final String MOBILE="mobile";
    private static final String EMAIL="email";

    private static final String IDUSER="id_users";
    private static final String IDDATAFILE="id_datafile";
    private static final String PRODUCTID="id_product";
    private static final String PRODUCTNAME="productName";
    private static final String LOCATION="location";
    private static final String MODEL="model";
    private static final String DEALER="shop";
    private static final String REPORT = "report";
    private static final String  GETCLAIMS ="getclaims" ;
    private static final String  PASSWORD ="password" ;
    private static final String  SHOP ="shop" ;



    // private static final String FIRSTNAME="firstName";





    public ContentValues updateProfile(int id_user,String firstName,String secondName,String mobile,String email){
        ContentValues values =new ContentValues();
        values.put(ACTION,USERS);
        values.put(USER_ACTION,ACTION_UPDATE);
        values.put(IDUSER,id_user);
        values.put(FIRSTNAME,firstName);
        values.put(SECONDNAME,secondName);
        values.put(MOBILE,mobile);
        values.put(EMAIL,email);

        return  values;


    }
    public ContentValues checkProfile(String mobile,String password){
        ContentValues values =new ContentValues();
        values.put(ACTION,USERS);
        values.put(USER_ACTION,ACTION_INSERT);
        values.put(MOBILE,mobile);
        values.put(PASSWORD,password);

        return  values;


    }


    public ContentValues getBlacklist(){
        ContentValues values =new ContentValues();
        values.put(ACTION,QUERYREPORTS);
        values.put(USER_ACTION,BLACKLIST);
         return  values;
    }
    public ContentValues getBackuplist(int id_user){
        ContentValues values =new ContentValues();
        values.put(ACTION,QUERYREPORTS);
        values.put(USER_ACTION,BACKUPLIST);
        values.put(IDUSER,id_user);
        return  values;
    }

    public ContentValues codeClaim(int id_user,int id_product,int id_datafile){
        ContentValues values =new ContentValues();
        values.put(ACTION,CLAIM_ACTION);
        values.put(USER_ACTION,ACTION_INSERT);
        values.put(IDUSER,id_user);
        values.put(IDDATAFILE,id_datafile);
        values.put(PRODUCTID,id_product);
        return  values;
    }

    public ContentValues backup(int id_user,String tablename,String type,String avatar){

        ContentValues values =new ContentValues();
        values.put(ACTION, ConstantParams.UPLOADS);
        values.put(USER_ACTION, ConstantParams.UPLOAD_BACKUP);
        values.put(ConstantParams.UPLOAD_TABLE,tablename);
        values.put(ConstantParams.IDUSER,id_user);
        values.put(ConstantParams.AVATAR,avatar);
        values.put(ConstantParams.UPLOAD_TYPE,type);
        return  values;

    }


    public ContentValues report(int id_user,String productname,String model,String location,String dealer,String shop){

        ContentValues values =new ContentValues();
        values.put(ACTION, REPORT);
        values.put(USER_ACTION, ACTION_INSERT);
        values.put(ConstantParams.IDUSER,id_user);
        values.put(PRODUCTNAME,productname);
        values.put(LOCATION,location);
        values.put(DEALER,dealer);
        values.put(SHOP,shop);
        values.put(MODEL,model);
        return  values;

    }
    public ContentValues getVerificationHistory(int id_user){

        ContentValues values =new ContentValues();
        values.put(ACTION,QUERY_VERIFICATION);
        values.put(USER_ACTION,ConstantParams.RESULT_CLAIMED_CODES);
        values.put(IDUSER,id_user);
        return  values;

    }
    public ContentValues getreportList(int id_user){

        ContentValues values =new ContentValues();
        values.put(ACTION,QUERY_REPORTS);
        values.put(USER_ACTION,ConstantParams.RESULT_REPORT);
        values.put(IDUSER,id_user);
        return  values;

    }
    public ContentValues verifyCode(String code) {

        ContentValues values =new ContentValues();
        values.put(ACTION,QUERY_VERIFICATION);
        values.put(USER_ACTION,VERIFYCODE);
         values.put(UNIQUECODE,code);
        return  values;
    }

    public ContentValues getbrands(String action,String brandcat){
        ContentValues values =new ContentValues();
        values.put(ACTION,QUERY_BRANDS);
        values.put(USER_ACTION,QUERY_BRANDS);

        if(action.equals(ACTION_GETALL)){
            values.put(USER_ACTION,ACTION_GETALL);
        }else {
            values.put(USER_ACTION,ACTION_BYCAT);
            values.put(ACTION_CAT,brandcat);

        }
        return  values;

    }

    public String getPostString(ContentValues params){
        StringBuilder result=new StringBuilder();
        boolean first = true;

        for (Map.Entry<String,Object> entry:params.valueSet()){
            if(first)
                first=false;
            else
                result.append("&");
            try {
                result.append(URLEncoder.encode(entry.getKey() + "=" + entry.getValue().toString(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    public boolean checkNullity(String value){
        if (value !=null || value!=" "){

            return true;
        }else {
            return false;
        }

    }
    public String getText(EditText editText){
       return editText.getText().toString();

    }
    public void setERROR(EditText editText,String msg){
                 editText.setError(msg);
    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }



    public static Bitmap decodeSampledBitmapFromStream(FileInputStream fileInputStream,int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(fileInputStream, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(fileInputStream, null, options);
    }




    public File getCacheFolder(Context context) {

        File cacheDir = null;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            cacheDir = new File(Environment.getExternalStorageDirectory()+"/Origicheck");

            if(!cacheDir.isDirectory()) {

                cacheDir.mkdirs();

            }

        }



        if(!cacheDir.isDirectory()) {

            cacheDir = context.getCacheDir(); //get system cache folder

        }



        return cacheDir;

    }

    public File getDataFolder(Context context) {

        File dataDir = null;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            dataDir = new File(Environment.getExternalStorageDirectory()+"/Origicheck/backups");

            if(!dataDir.isDirectory()) {

                dataDir.mkdirs();

            }
        }



        if(!dataDir.isDirectory()) {

            dataDir = context.getFilesDir();

        }



        return dataDir;

    }
}

