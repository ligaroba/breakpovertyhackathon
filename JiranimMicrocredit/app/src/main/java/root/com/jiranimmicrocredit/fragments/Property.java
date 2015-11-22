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
public class Property extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private String selected;
    Functions fn=new Functions();



    public interface CallBackInterface {
        public void ButtonPressed(int id, int crop,int activity);
        public void passdData(String propertyname, String location, String plotnumber, String Occupancy, String income,String ownership);
    }

    public  CallBackInterface listener;
    EditText edtpropertyname, edtlocation, edtplotnumber, edtocuppancy, edtincome;
    Spinner spinownwership;
    Button btnsubmit;
    ImageView imgUploadFile;
    View view;

    SetterGetters setter =new SetterGetters();

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (CallBackInterface) activity;
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
        view = inflater.inflate(R.layout.property, container, false);
        edtpropertyname = (EditText) view.findViewById(R.id.ediproperty_name);
        edtplotnumber = (EditText) view.findViewById(R.id.edtplot_number);
        edtlocation = (EditText) view.findViewById(R.id.property_location);
        edtocuppancy = (EditText) view.findViewById(R.id.edtnumber_occupancy);
        edtincome = (EditText) view.findViewById(R.id.editincome);
        spinownwership = (Spinner) view.findViewById(R.id.spinproperty_spinner);
        imgUploadFile = (ImageView) view.findViewById(R.id.img_property);

        btnsubmit = (Button) view.findViewById(R.id.btnsubmit);

        spinownwership.setOnItemSelectedListener(this);
        imgUploadFile.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);
        edtlocation.setOnClickListener(this);

        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        String [] choice = getResources().getStringArray(R.array.property_ownership);
        selected = choice[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnsubmit:

                String propertyname=fn.getText(edtpropertyname);
                String location=fn.getText(edtlocation);
                String plotnumber=fn.getText(edtplotnumber);
                String ocupancy=fn.getText(edtocuppancy);
                String income=fn.getText(edtocuppancy);

                if(fn.checkNullity(propertyname)==true){
                    fn.setERROR(edtpropertyname,"Empty Field");
                }
                if(fn.checkNullity(location)==true){
                    fn.setERROR(edtlocation,"Empty Field");
                }
                if(fn.checkNullity(plotnumber)==true){
                    fn.setERROR(edtplotnumber,"Empty Field");
                }
                if(fn.checkNullity(income)==true){
                    fn.setERROR(edtincome,"Empty Field");
                }
                if(fn.checkNullity(ocupancy)==true){
                    fn.setERROR(edtocuppancy,"Empty Field");
                }
                else{
                    listener.passdData(propertyname, location, plotnumber, ocupancy, income, selected);



                    //new BackGroundThread(getActivity(),"Adding Property",1).execute(new Functions().property(0,location,plotnumber,selected,ocupancy,income,userid,))
                }

                break;



            case R.id.img_property:
                listener.ButtonPressed(2,0,1);
                break;
            case R.id.property_location:
                listener.ButtonPressed(4,0,-1);
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences pref=getActivity().getSharedPreferences(Constants.STORAGE_PREF, Context.MODE_PRIVATE);
        String image=pref.getString(Constants.PROPERTY_IMAGE, "none");
        String location=pref.getString(Constants.PROPERTY_LOCATION,"none");

        if(!image.equalsIgnoreCase("none")){

            imgUploadFile.setImageURI(Uri.parse(image));
        }

        if(!location.equalsIgnoreCase("none")){

            edtlocation.setText(location);
        }


    }
}