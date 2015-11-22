package com.origitech.root.origitech.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

import com.origitech.root.origitech.constants.ConstantParams;
import com.origitech.root.origitech.constants.SetterGetter;
import com.origitech.root.origitech.functions.Functions;
import com.origitech.root.origitech.network.BackgroundThread;
import com.origitech.root.origitech.network.JsonHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by root on 10/16/15.
 */
public class Backgroundservice extends IntentService {
    String id_user;

    private BroadCastNotifier broadcastNotifier = new BroadCastNotifier(this);

    public Backgroundservice() {
        super("Backgroundservice");


    }

    @Override
    protected void onHandleIntent(Intent intent) {
       Bundle data= intent.getExtras();


         id_user=data.getString(ConstantParams.IDUSER);
        String action=data.getString(ConstantParams.ACTION);
        ContentValues postData=new ContentValues();

        if(action.equalsIgnoreCase(ConstantParams.GETVERIFICATION)){

             postData=new Functions().getVerificationHistory(Integer.valueOf(id_user));

        }else{
            postData=new Functions().getBlacklist();
        }


        try {
            broadcastNotifier.BroadCastIntentWithState(ConstantParams.STATE_ACTION_CONNECTING);

            if(new SetterGetter().getResponseCode()== HttpURLConnection.HTTP_OK){
                broadcastNotifier.BroadCastIntentWithState(ConstantParams.STATE_ACTION_PARSING);
              //  new BackgroundThread(getApplicationContext(),"",0).execute(postData);
                new JsonHandler().JsonConnection(postData);
                broadcastNotifier.BroadCastIntentWithState(ConstantParams.STATE_ACTION_WRITING);
            }else{

            }
            broadcastNotifier.BroadCastIntentWithState(ConstantParams.STATE_ACTION_COMPLETE);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
