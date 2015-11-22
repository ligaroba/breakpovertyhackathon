package root.com.jiranimmicrocredit.utils;

/**
 * Created by root on 11/5/15.
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.net.ConnectivityManagerCompat;



/**
 * Created by root on 10/24/15.
 */
public class NetworkUtils {
    public static int TYPE_MOBILE=1;
    public static int TYPE_WIFI=2;
    public static int TYPE_NOT_CONNECTED=0;

    public static int getConectivityStatus(Context context){
        ConnectivityManager cm= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork!=null){
            if(activeNetwork.getType()==ConnectivityManager.TYPE_WIFI){
                return TYPE_WIFI;
            }else if(activeNetwork.getType()==ConnectivityManager.TYPE_MOBILE){
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;


    }

    public static String getConnectivityStatusString(Context context){
        int con=NetworkUtils.getConectivityStatus(context);
        String status;

        if(con==NetworkUtils.TYPE_WIFI){
            status ="Wifi Enabled";
        }else if(con==NetworkUtils.TYPE_MOBILE){
            status ="mobile data Enabled";

        }else{
            status="Not Connected";
        }
        return status;

    }
}

