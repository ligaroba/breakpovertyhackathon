package com.kccrtms.kccrtms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.kccrtms.kccrtms.Constants.ConstantsClass;
import com.kccrtms.kccrtms.Database.DBContract;
import com.kccrtms.kccrtms.Database.SqliteDBHelper;
import com.kccrtms.kccrtms.MainActivity;
import com.kccrtms.kccrtms.R;
import com.kccrtms.kccrtms.fragments.Login;
import com.kccrtms.kccrtms.functions.Functions;
import com.kccrtms.kccrtms.network.BackgroundThread;

public class UserManager extends AppCompatActivity implements Login.LoginData{

    ConstantsClass consta;
    FragmentManager frgament = getSupportFragmentManager();
    Login login;
    SqliteDBHelper db;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermanager);
        user();
        pref=getSharedPreferences(consta.LOGINPREF, Context.MODE_PRIVATE);
        db =new SqliteDBHelper(this);

        boolean loggedin= pref.getBoolean(consta.ISLOOGED_IN,false);
        Log.e("Loggin", String.valueOf(loggedin));
        if(savedInstanceState==null){
            if(loggedin==true){

                Intent intent =new Intent(UserManager.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                login = new Login();
                frgament.beginTransaction().addToBackStack(null).add(R.id.container, login, "loginfrag").commit();
            }
        }



    }

    @Override
    public void onsendLoginData(String mobile, String password) {

        if( db.getUser(mobile,password)){
            SharedPreferences.Editor editor=pref.edit();
            editor.putBoolean(consta.ISLOOGED_IN,true);
            editor.putString(consta.USERID,mobile);
            editor.commit();

            Intent intent =new Intent(UserManager.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            new BackgroundThread(this, "please wait...", 0).execute(new Functions().userlogin(mobile, password));
            if (db.getUser(mobile, password) == true) {
                SharedPreferences.Editor editor=pref.edit();
                editor.putBoolean(consta.ISLOOGED_IN,true);
                editor.commit();

                Intent intent =new Intent(UserManager.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this, "Please sign up", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void sendLoginID(int id) {

    }


    public void user() {
        db=new SqliteDBHelper(this);
        db.resetTables(DBContract.UserEntry.USERS_TABLE);
        db.users("Ligaye", "Robert", "ligaroba@gmail.com", "014447552", "ligaroba","oti");


    }







}