package root.com.jiranimmicrocredit.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import root.com.jiranimmicrocredit.BackgroudThread.BackGroundThread;
import root.com.jiranimmicrocredit.R;
import root.com.jiranimmicrocredit.utils.Constants;
import root.com.jiranimmicrocredit.utils.Functions;
import root.com.jiranimmicrocredit.utils.SetterGetters;

/**
 * Created by root on 11/5/15.
 */
public class Employment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private String selected;
    Functions fn=new Functions();



    public interface CallEmpBackInterface {
        public void ButtonPressed(int id, int crop,int activity);
        public void passdEmpData(String employeename, String department, String period, String email, String income, String selected);
    }

    public  CallEmpBackInterface listener;
    EditText edtemployeename, edtdepartment, edtperiod, edtemail, edtincome;
    Spinner spinemployementtype;
    Button btnsubmit;
    ImageView imgUploadFile;
    View view;

    SetterGetters setter =new SetterGetters();

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (CallEmpBackInterface) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement AuthenticationDialogListener");

        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.employment, container, false);
        edtemployeename = (EditText) view.findViewById(R.id.editemployer_name);
        edtdepartment = (EditText) view.findViewById(R.id.editdepartment);
        edtperiod = (EditText) view.findViewById(R.id.editemploymentyears);
        edtemail = (EditText) view.findViewById(R.id.editemail);
        edtincome = (EditText) view.findViewById(R.id.editmonthlyincome);
        spinemployementtype = (Spinner) view.findViewById(R.id.spinemploymenttype);
        imgUploadFile = (ImageView) view.findViewById(R.id.imguploadpayslip);

        btnsubmit = (Button) view.findViewById(R.id.btnsubmitemployee);

        spinemployementtype.setOnItemSelectedListener(this);
        imgUploadFile.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);

        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        String [] choice = getResources().getStringArray(R.array.employment_terms);
        selected = choice[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnsubmitemployee:

                String employeename=fn.getText(edtemployeename);
                String department=fn.getText(edtdepartment);
                String period=fn.getText(edtperiod);
                String email=fn.getText(edtemail);
                String income=fn.getText(edtincome);

                if(fn.checkNullity(employeename)==true){
                    fn.setERROR(edtemployeename,"Empty Field");
                }
                if(fn.checkNullity(department)==true){
                    fn.setERROR(edtdepartment,"Empty Field");
                }
                if(fn.checkNullity(period)==true){
                    fn.setERROR(edtperiod,"Empty Field");
                }
                if(fn.checkNullity(income)==true){
                    fn.setERROR(edtincome,"Empty Field");
                }
                if(fn.checkNullity(email)==true){
                    fn.setERROR(edtemail,"Empty Field");
                }
                else{
                    listener.passdEmpData(employeename, department, period, email, income, selected);



                    //new BackGroundThread(getActivity(),"Adding Property",1).execute(new Functions().property(0,location,plotnumber,selected,ocupancy,income,userid,))
                }

                break;



            case R.id.imguploadpayslip:
                listener.ButtonPressed(2,0,2);
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences pref=getActivity().getSharedPreferences(Constants.STORAGE_PREF, Context.MODE_PRIVATE);
        String image=pref.getString(Constants.EMPLOYMENT_IMAGE,"none");

        if(!image.equalsIgnoreCase("none")){

            imgUploadFile.setImageURI(Uri.parse(image));
        }


    }
}