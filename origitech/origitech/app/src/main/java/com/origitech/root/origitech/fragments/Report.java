package com.origitech.root.origitech.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.origitech.root.origitech.R;
import com.origitech.root.origitech.constants.ConstantParams;

/**
 * Created by root on 10/13/15.
 */
public class Report extends DialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText edmodel,edtproductname,edtdealername,edtshop;
    Button btnreport;
    Spinner spinreporttype;
    SharedPreferences prefFrag;
    ImageView imagreport;
    View view;
    ReportData listener;
    String choice;
    Uri uri=null;


    public interface ReportData{
        public void onReportSenData(String code);
        public void ButtonPressed(int id,int crop);
        public void onReportDataSend(String proname,String model,String dealer,String shop,Uri uri);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener = (ReportData) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement AuthenticationDialogListener");

        }



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_reportfake, container, false);

        edtdealername=(EditText) view.findViewById(R.id.edtdealer_name);
        edtproductname=(EditText) view.findViewById(R.id.edtproduct_name);
        edmodel=(EditText) view.findViewById(R.id.edtmodel);
        edtshop=(EditText) view.findViewById(R.id.edtshop);



        spinreporttype=(Spinner) view.findViewById(R.id.spinreporttype);
        btnreport= (Button) view.findViewById(R.id.btnreport);
        imagreport= (ImageView) view.findViewById(R.id.imagreport);

        btnreport.setOnClickListener(this);
        spinreporttype.setOnItemSelectedListener(this);
        imagreport.setImageDrawable(getResources().getDrawable(R.drawable.ic_history));
        imagreport.setOnClickListener(this);


        return view;


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnreport:
                String model=edmodel.getText().toString();
                String dealer=edtdealername.getText().toString();
                String prodname=edtproductname.getText().toString();
                String shop=edtshop.getText().toString();


                if(!TextUtils.isEmpty(model)&&!TextUtils.isEmpty(shop)&&!TextUtils.isEmpty(prodname)){

                    if(!choice.equalsIgnoreCase("Select report type") &&  uri!=null ) {
                        listener.onReportDataSend(prodname, model, dealer, shop,uri);
                    }else{
                        Toast.makeText(getActivity(),"Please fill empty fields",Toast.LENGTH_SHORT).show();
                    }
                    }else{
                        Toast.makeText(getActivity(),"Please fill empty fields",Toast.LENGTH_SHORT).show();
                    }


                break;
            case R.id.imagreport:

                listener.ButtonPressed(2, 0);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

        switch (parent.getId()){

            case R.id.spinreporttype:
                    String [] selection= getResources().getStringArray(R.array.reporttype);

                if(position!=0){
                    choice=selection[position];
                 }


                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onResume() {
        super.onResume();
        prefFrag=getActivity().getSharedPreferences(ConstantParams.PREF_DATA, Context.MODE_PRIVATE);
        String imageurl=prefFrag.getString(ConstantParams.IMAGEURI, "none");
        //  Toast.makeText(getActivity(),"image url "+ imageurl, Toast.LENGTH_LONG).show();
        if(imageurl!="none"){
            uri= Uri.parse(imageurl);
            imagreport.setImageURI(uri);

        }





    }
}
