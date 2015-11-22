package root.com.jiranimmicrocredit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SmsActivity extends AppCompatActivity {

    private Button Buttonsend;

    private CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
         Buttonsend  = (Button) findViewById(R.id.buttonSend);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorLayout);

        Buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    Intent sendIntent= new Intent(Intent.ACTION_VIEW);

                    sendIntent.putExtra("sms_body","default content");
                    sendIntent.setType("vnd.android-dir/mms-sms");

                    startActivity(sendIntent);
                    Snackbar.make(coordinatorLayout,"Sms send!",Snackbar.LENGTH_SHORT)
                            .setAction("Dismiss",new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .setActionTextColor(Color.GREEN)
                                    //.setDuration(4000)
                            .show();

                }catch (Exception e){


                    Snackbar.make(coordinatorLayout,"SMS failed, please try again later!",Snackbar.LENGTH_SHORT)
                            .setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .setActionTextColor(Color.RED)
                                    //.setDuration(4000)
                            .show();

                }

            }
       });

    }




}
