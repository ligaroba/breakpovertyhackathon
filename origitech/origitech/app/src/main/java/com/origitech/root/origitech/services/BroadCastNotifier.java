package com.origitech.root.origitech.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.origitech.root.origitech.constants.ConstantParams;

/**
 * Created by root on 10/22/15.
 */
public class BroadCastNotifier {
    Context context;
    private LocalBroadcastManager mBroadcast;


    public BroadCastNotifier(Context context) {
        this.context=context;
        mBroadcast=LocalBroadcastManager.getInstance(context);
    }

   public void BroadCastIntentWithState(int status){
        Intent localIntent= new Intent();
       localIntent.setAction(ConstantParams.BROADCAST_ACTION);
       localIntent.putExtra(ConstantParams.EXTENDED_DATA_STATUS, status);
       localIntent.addCategory(Intent.CATEGORY_DEFAULT);
       mBroadcast.sendBroadcast(localIntent);

   }
    public void notifyProgress(String logData){
        Intent localIntent =new Intent();
        localIntent.putExtra(ConstantParams.EXTENDED_DATA_STATUS, -1);
        localIntent.putExtra(ConstantParams.EXTENDED_STATUS_LOG ,logData);
        localIntent.addCategory(Intent.CATEGORY_DEFAULT);
        mBroadcast.sendBroadcast(localIntent);

    }
}
