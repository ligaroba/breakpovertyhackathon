package com.kccrtms.kccrtms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.kccrtms.kccrtms.functions.Functions;
import com.kccrtms.kccrtms.network.BackgroundThread;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by root on 10/14/15.
 */

public class Splash extends Activity {
    long Delay=8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        regions();
        Timer timer=new Timer();
        TimerTask showsplash=new TimerTask(){
            public void run(){
                finish();

                Intent dashboard=new Intent(Splash.this,UserManager.class);
                startActivity(dashboard);
            }
        };
        timer.schedule(showsplash, Delay);

    }
    public void regions() {
       /* db=new SqliteDBHelper(this);
        db.resetTables(DBContract.RegionEntry.REGION_TABLE);
        db.regions(1,"Nairobi");
        db.regions(2,"Mombasa");
        db.regions(3,"Kisumu");
        db.regions(4,"Nakuru");
        db.regions(5, "Kakamega");
*/
        new BackgroundThread(this,"loading..",0).execute(new Functions().getAll("tbl_region"));
        new BackgroundThread(this,"loading..",0).execute(new Functions().getAll("tbl_sku"));
        new BackgroundThread(this,"loading..",0).execute(new Functions().getAll("tbl_teritory"));
        new BackgroundThread(this,"loading..",0).execute(new Functions().getAll("tbl_area"));
        new BackgroundThread(this,"loading..",0).execute(new Functions().getAll("tbl_channels"));

        // return uri;

    }

}
