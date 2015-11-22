package com.origitech.root.origitech.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.origitech.root.origitech.R;
import com.origitech.root.origitech.constants.ConstantParams;

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by root on 10/7/15.
 */
public class BackupFiles extends DialogFragment implements View.OnClickListener {

    SharedPreferences prefFrag;
    private BackUpListener listener;
    private View view;

    private Spinner spinBackuptype;
    private Button btnUpload;
    private ImageView imagview;
    private String statusChoice="";
    Uri uri=null;


    public interface BackUpListener{
       // public void onBackupButtonClicked(int backuptype);
        public void ButtonPressed(int id,int crop);
        public void DataPassed(String backuptype);


    }


    @Override
    public void onAttach(Activity activity) {
             super.onAttach(activity);
        try{
            listener = (BackUpListener) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement AuthenticationDialogListener");

        }



        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.backup_fragment, container, false);

                spinBackuptype= (Spinner) view.findViewById(R.id.spinuploadtype);
                imagview=(ImageView) view.findViewById(R.id.imagpreview);
               btnUpload=(Button) view.findViewById(R.id.btnuploadtoserver);

        imagview.setOnClickListener(this);
        btnUpload.setOnClickListener(this);

        spinBackuptype.setOnItemSelectedListener(new CustomSpinnerListener());
        imagview.setImageDrawable(getResources().getDrawable(R.drawable.ic_history));

        return view;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.imagpreview:

                listener.ButtonPressed(2, 0);

                break;

            case R.id.btnuploadtoserver:
                    if(!statusChoice.equalsIgnoreCase("Select backup type") &&  uri!=null ){

                        listener.DataPassed(statusChoice);
                    }else{
                        Toast.makeText(getActivity(),"Please select item to backup and image",Toast.LENGTH_LONG).show();
                    }

                break;

        }

    }
    public Bitmap uriBitmap(Uri imageUri){
        Bitmap bitmap=null;

        try {
           bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bitmap!=null) {
            return bitmap;
        }else{
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        prefFrag=getActivity().getSharedPreferences(ConstantParams.PREF_DATA,Context.MODE_PRIVATE);




            String imageurl=prefFrag.getString(ConstantParams.IMAGEURI, "none");
      //  Toast.makeText(getActivity(),"image url "+ imageurl, Toast.LENGTH_LONG).show();
            if(imageurl!="none"){
                    uri=Uri.parse(imageurl);
                imagview.setImageURI(uri);

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


    private class CustomSpinnerListener implements  AdapterView.OnItemSelectedListener {


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
            switch (parent.getId()){

                case R.id.spinuploadtype:
                    String[] choice= getResources().getStringArray(R.array.backuptype);




                        if(choice[position]!=null && choice[position]!="" &&choice[position]!="Select backup type"){
                            statusChoice=choice[position];

                        }else{
                            Toast.makeText(getActivity(),"Please select item to backup",Toast.LENGTH_LONG).show();
                        }


                    break;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
