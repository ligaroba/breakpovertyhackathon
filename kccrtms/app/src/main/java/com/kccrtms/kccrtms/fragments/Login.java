package com.kccrtms.kccrtms.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kccrtms.kccrtms.Constants.ConstantsClass;
import com.kccrtms.kccrtms.Database.SqliteDBHelper;
import com.kccrtms.kccrtms.R;

/**
 * Created by root on 10/12/15.
 */
public class Login extends Fragment implements View.OnClickListener {


    EditText edtusername,edtpass;
    Button btngosignup,btnlogin;
    ConstantsClass consta;
    View view;
    SqliteDBHelper db;
    private LoginData listener;
    public interface LoginData{
        public void onsendLoginData(String mobile,String password);
        public void sendLoginID(int id);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (LoginData) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AuthenticationDialogListener");

        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.login_fragment, container, false);

        edtusername = (EditText) view.findViewById(R.id.editusername);
        edtpass = (EditText) view.findViewById(R.id.edtpassword);
        btnlogin= (Button) view.findViewById(R.id.btnlogin);


        btnlogin.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnlogin:
                EditText[] fields={edtusername,edtpass};


                if(consta.isFieldEmpty(fields)==false){
                    String pass=edtpass.getText().toString();
                    String mobile=edtusername.getText().toString();
                    listener.onsendLoginData(mobile, pass);



                }

                break;
        }
    }

}
