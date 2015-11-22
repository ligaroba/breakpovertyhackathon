package com.origitech.root.origitech.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.origitech.root.origitech.R;
import com.origitech.root.origitech.constants.ConstantParams;
import com.origitech.root.origitech.functions.Functions;

import java.io.IOException;

/**
 * Created by root on 10/7/15.
 */
public class CheckUserprofile extends DialogFragment implements View.OnClickListener {

    SharedPreferences prefFrag;
    private CheckUserProfileListener listener;
    private View view;


    private Button btncreateprof;
    private EditText editphone,editpassword,editcpassword;
    private String intentAction="";


    Functions funs = new Functions();

    Uri uri=null;
    private SharedPreferences userPref;


    public interface CheckUserProfileListener{
        // public void onBackupButtonClicked(int backuptype);
        //public void ButtonPressed(int id,int crop);
        public void checkprofiledata(String intentAction,String phone,String password);


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener = (CheckUserProfileListener) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement AuthenticationDialogListener");

        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.check_userprofile, container, false);

        userPref=getActivity().getSharedPreferences(ConstantParams.PREF_USERID, Context.MODE_PRIVATE);

        btncreateprof=(Button) view.findViewById(R.id.btncheckCreate);
        editphone=(EditText) view.findViewById(R.id.editphone);
        editpassword=(EditText) view.findViewById(R.id.editpassword);
        editcpassword=(EditText) view.findViewById(R.id.editcpassword);


        btncreateprof.setOnClickListener(this);
       String gointetnt= userPref.getString(ConstantParams.GOTINTENT, "none");
        Log.e("INTENT ACTION INT ", userPref.getString(ConstantParams.GOTINTENT, "none"));
        if(!gointetnt.equalsIgnoreCase("none")){
            intentAction= userPref.getString(ConstantParams.GOTINTENT,"none");
            Log.e("INTENT ACTION", intentAction);
        }

        return view;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){


            case R.id.btncheckCreate:


                String phone=funs.getText(editphone);
                String cpassword=funs.getText(editcpassword);
                String password=funs.getText(editpassword);


                if(funs.checkNullity(phone)==false){
                    funs.setERROR(editphone,"Empty field");

                }
                if(funs.checkNullity(cpassword)==false){
                    funs.setERROR(editcpassword,"Empty field");

                }
                if(funs.checkNullity(cpassword)==false){
                    funs.setERROR(editcpassword,"Empty field");

                }
                if(cpassword.equalsIgnoreCase(password)){
                    listener.checkprofiledata(intentAction,phone,password);
                }
               else{
                    Toast.makeText(getActivity(),"Password Not Matching",Toast.LENGTH_SHORT).show();

                }

                break;

        }

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }



}
