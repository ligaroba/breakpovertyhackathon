package com.origitech.root.origitech.network;

/**
 * Created by root on 10/7/15.
 */


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.origitech.root.origitech.constants.ConstantParams;
import com.origitech.root.origitech.databases.Database;
import com.origitech.root.origitech.fragments.CheckUserprofile;
import com.origitech.root.origitech.fragments.DashboardFragment;
import com.origitech.root.origitech.functions.Functions;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;




/**
 * Created by root on 9/23/14.
 */
public class FileTransfer extends AsyncTask<String,Void,Integer>{
    HttpURLConnection connection = null;
    DataOutputStream outputStream = null;
    DataInputStream inputStream = null;
    String lineEnd="\r\n";
    String twoHyphens="--";
    String boundary="*****";
    int bytesRead,bytesAvailable,bufferSize;
    byte[] buffer;
    int maxBufferSize=1*1024*1024;
    String responseFromServer="";
   // String urlString="http://192.168.43.247/origitech/file_upload.php";
     private String urlString="http://www.origicheck.com/includes/file_upload.php";
    int serverResponseCode;
    String serverResponseMessage="";
    String TAG="FileTransfer";
    String name;
    String pmessage;
    int userid;
    DashboardFragment dashboard;

    Database db;
    String message;
    ProgressDialog pDialog;
    Context context;
    String json;
    int response;
    JSONObject jsonObject;
    String tablename;
    String[] extradata;
    ContentValues postData=new ContentValues();
    public FileTransfer(String name,String pmessage,String tablename,String[] extradata,Context context){

        this.name=name;
        this.tablename=tablename;
        this.extradata=extradata;
        this.pmessage=pmessage;
        this.context=context;
        this.db=new Database(context);


    }

    protected void onPreExecute(){
        super.onPreExecute();
        pDialog=new ProgressDialog(context);
        pDialog.setMessage(Html.fromHtml("<b> <i> " + pmessage + "</i> </b>"));
        pDialog.setCancelable(false);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.show();


    }

    protected Integer doInBackground(String... data) {
        String selectedPath= data[0];
        String action;
        String table;
        String iduser;
        String type;


//    Toast.makeText(context," before:  "+ new Functions().getPostString(data[0])+ "  "+ selectedPath,Toast.LENGTH_LONG).show();
        Log.e(TAG, "seleted " + selectedPath);
        File sourcefile = new File(selectedPath);
        if (!sourcefile.isFile()) {
            Log.e(TAG, "source file does not exist: " + selectedPath);
            return 0;
        } else {

            try {
               // Toast.makeText(context,"After:  "+ new Functions().getPostString(data[0])+ "  "+ selectedPath,Toast.LENGTH_LONG).show();
                FileInputStream fileInputStream = new FileInputStream(new File(selectedPath));
           //   iduser= data[0].getAsString(ConstantParams.IDUSER);
           //     table= data[0].getAsString(ConstantParams.UPLOAD_TABLE);
            //    type= data[0].getAsString(ConstantParams.UPLOAD_TYPE);
          //   action= data[0].getAsString("action");
          //      postData=data[0];
                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();

                // Allow Inputs &amp; Outputs.
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);

                // Set HTTP method to POST.
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty(name, selectedPath);


                outputStream = new DataOutputStream(connection.getOutputStream());

                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data;name="+name+";filename=" + sourcefile + lineEnd);
                //writer(new Functions().getPostString(data[0]));
                outputStream.writeUTF(data[0]);
                outputStream.writeBytes(lineEnd);



                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // Read file
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    outputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                outputStream.writeBytes(lineEnd);
                outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = connection.getResponseCode();
                serverResponseMessage = connection.getResponseMessage();

                Log.e("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);


                fileInputStream.close();
                outputStream.flush();
                outputStream.close();
            } catch (MalformedURLException ex) {
                Log.e("Debug", "error: " + ex.getMessage(), ex);
            } catch (IOException ioe) {
                Log.e("Debug", "error: " + ioe.getMessage(), ioe);
            }
            //------------------ read the SERVER RESPONSE
            try {
                inputStream = new DataInputStream(connection.getInputStream());
                String str;
                StringBuilder stringBuilder = new StringBuilder();

                while ((str = inputStream.readLine()) != null) {
                    Log.e("Debug", "Server Response " + str);
                    stringBuilder.append(str);

                }
                json=stringBuilder.toString();
                jsonObject = new JSONObject(json);
                response=Integer.parseInt(jsonObject.getString("success"));
                Log.e(TAG,jsonObject.toString());
                inputStream.close();

            } catch (IOException ioex) {
                Log.e("Debug", "error: " + ioex.getMessage(), ioex);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e(TAG, "Code: " + serverResponseCode + " msg" + serverResponseMessage + "data  " + json.toString());
            return serverResponseCode;

        }
    }
    protected void onPostExecute(Integer result){
        pDialog.dismiss();
        if(db.getUserId()!=-1) {
            userid = db.getUserId();

        }
        Log.e(TAG,"extradata"+extradata.toString());

        if(result==200 && response==1) {
            ConstantParams.SUCCESS_RESPONSE=response;
           // message="File Uploaded successfully";
                try {
                if(tablename.equalsIgnoreCase("tbl_backup")){

                    new BackgroundThread(context, "finishing up", 1).execute(new Functions().backup(userid, tablename, extradata[0], jsonObject.getString("filename")));


                }else if(tablename.equalsIgnoreCase("tbl_reports")){

                        new BackgroundThread(context, "Saving report", 1).execute(new Functions().report(userid,extradata[0],extradata[1],extradata[2],extradata[3],jsonObject.getString("filename")));


                }else{
                    new BackgroundThread(context,"updating profile",1).execute(new Functions().updateProfile(userid,extradata[0],extradata[1],extradata[2],extradata[3]));
                }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


        }else{

            message="Upload Failed Try again" ;
        }

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
