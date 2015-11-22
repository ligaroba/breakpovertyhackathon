package com.origitech.root.origitech.network;

/**
 * Created by root on 9/30/15.
 */


        import android.content.ContentValues;
        import android.util.Log;


        import com.origitech.root.origitech.constants.SetterGetter;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.OutputStreamWriter;
        import java.io.UnsupportedEncodingException;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.net.URLEncoder;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

/**
 * Created by root on 5/10/15.
 */
public class JsonHandler {


    //  private String url="http://10.0.2.2/shopdukaapi/index.php";
    //private String requestUrl="http://192.168.43.247/origitech/implementation.php";
    private String requestUrl=" http://www.origicheck.com/includes/implementation.php";

   // 192.168.43.247
    StringBuilder builder =new StringBuilder();
    String response="";
    InputStream placeContent;
    BufferedReader bReader;
    BufferedWriter writer;
    JSONObject jsonobject;
    int responsCode;
    String json;
    String TAG="JSON HANDLER";
    URL url;



    public JSONObject JsonConnection(ContentValues postDataParams) throws IOException {
        url=new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

        Log.e("URL STRING",getPostString(postDataParams));
        writer.write(getPostString(postDataParams));
        writer.flush();
        writer.close();
        os.close();
        responsCode = conn.getResponseCode();
        new SetterGetter().setResponseCode(responsCode);

        if (responsCode == HttpURLConnection.HTTP_OK) {
            String line;
            bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((line = bReader.readLine()) != null) {

                builder.append(line);

            }
            json = builder.toString();
            Log.e(TAG, json.toString());

            if (json != null) {
                try {
                    jsonobject = new JSONObject(json);
                    Log.e(TAG, jsonobject.toString());

                } catch (JSONException jsonX) {

                    jsonX.printStackTrace();
                }
            } else {

                jsonobject = null;
            }
            // Log.e(TAG,jsonobject.toString());
            return jsonobject;

        } else {
            return null;
        }


/*





        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        if(params!=null) {
            try{
                httppost.setEntity(new UrlEncodedFormEntity(params));

                Log.e(TAG, params.toString());

            } catch (UnsupportedEncodingException ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }

            try{
                HttpResponse response = httpclient.execute(httppost);
                if(response.getStatusLine().getStatusCode()== 200){

                    placeContent = null;

                    placeContent = response.getEntity().getContent();

                    BufferedReader breader = new BufferedReader(new InputStreamReader(placeContent,"UTF-8"),8) ;

                    String linein="";

                    while((linein=breader.readLine())!=null) builder.append(linein);


                }

                placeContent.close();



            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            json=  builder.toString();
            Log.e(TAG,json.toString());

        }

        if(json!=null) {
            try {
                jsonobject = new JSONObject(json);
                Log.e(TAG,jsonobject.toString());

            }catch(JSONException jsonX){

                jsonX.printStackTrace();
            }
        }else {

            jsonobject=null;
        }
        // Log.e(TAG,jsonobject.toString());
        return jsonobject;
    }*/
    }
    public String getPostString(ContentValues params){
        StringBuilder result=new StringBuilder();
        boolean first = true;

        for (Map.Entry<String,Object> entry:params.valueSet()){
            if(first)
                first=false;
            else
                result.append("&");


                result.append(entry.getKey()+"="+entry.getValue().toString());


        }
        return result.toString();
    }

}
