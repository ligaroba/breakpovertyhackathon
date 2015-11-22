package com.origitech.root.origitech.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.origitech.root.origitech.R;

/**
 * Created by root on 10/10/15.
 */
public class Verification extends Fragment implements View.OnClickListener {
    View view;
    Button btnVerify;
    EditText tvtInputCode;

    VerificationData listener;

public interface VerificationData{
    public void onVerifySenData(String code);

}


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener = (VerificationData) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement AuthenticationDialogListener");

        }



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.verification, container, false);
               tvtInputCode= (EditText) view.findViewById(R.id.tvtverifycode);
                btnVerify= (Button) view.findViewById(R.id.btnverify);


        //diabledEdittext(tvtInputCode,false);
        btnVerify.setOnClickListener(this);
        tvtInputCode.setOnClickListener(this);


        //getActivity().onBackPressed();
        return view;


    }

    public void diabledEdittext(EditText editText ,boolean toogle){
        editText.setFocusable(toogle);
        // editText.setEnabled(toogle);
        editText.setCursorVisible(toogle);
      //  editText.setKeyListener(null);


    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnverify:
                String usercode = tvtInputCode.getText().toString();

                if (TextUtils.isEmpty(usercode)) {

                    Toast.makeText(getActivity(), "Please Enter Code", Toast.LENGTH_LONG).show();
                } else {

                      listener.onVerifySenData(usercode);
                 }

                break;
            case R.id.tvtverifycode:
                   //  diabledEdittext(tvtInputCode,true);

                break;

        }

    }
}
