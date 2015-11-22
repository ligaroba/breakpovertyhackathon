package root.com.jiranimmicrocredit.Recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import root.com.jiranimmicrocredit.utils.Constants;
import root.com.jiranimmicrocredit.utils.NetworkUtils;

/**
 * Created by root on 11/1/15.
 */
public class ConnectivityReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
            Constants.ConnectionStatus= NetworkUtils.getConnectivityStatusString(context);
            //

    }
}
