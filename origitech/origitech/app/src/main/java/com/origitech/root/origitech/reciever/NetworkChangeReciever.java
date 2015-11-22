package com.origitech.root.origitech.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.origitech.root.origitech.constants.ConstantParams;
import com.origitech.root.origitech.utils.NetworkUtils;

/**
 * Created by root on 10/24/15.
 */
public class NetworkChangeReciever  extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        ConstantParams.ConnectionStatus= NetworkUtils.getConnectivityStatusString(context);
       //

    }
}
