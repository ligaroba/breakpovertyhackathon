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
public class OtherOccupations extends Fragment implements View.OnClickListener {

    private String selected;
    Functions fn=new Functions();



    public interface CallOtherBackInterface {
        public void ButtonPressed(int id, int crop,int activity);
        public void passdOtherData(String occupationname, String description, String income);
    }

    public  CallOtherBackInterface listener;
    EditText edtoccupationname, edtdescription,edtincome;

    Button btnsubmit;
    ImageView imgUploadFile;
    View view;

    SetterGetters setter =new SetterGetters();

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (CallOtherBackInterface) activity;
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
        view = inflater.inflate(R.layout.other_occupation, container, false);
        edtoccupationname = (EditText) view.findViewById(R.id.editoccupation_name);
        edtdescription = (EditText) view.findViewById(R.id.editoccupation_description);
        edtincome = (EditText) view.findViewById(R.id.editincome);

        imgUploadFile = (ImageView) view.findViewById(R.id.imgotheroccupation);

        btnsubmit = (Button) view.findViewById(R.id.btnsubmitother);


        imgUploadFile.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);

        return view;
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnsubmitother:

                String occupationname=fn.getText(edtoccupationname);
                String description=fn.getText(edtdescription);
                String income=fn.getText(edtincome);

                if(fn.checkNullity(occupationname)==true){
                    fn.setERROR(edtoccupationname,"Empty Field");
                }
                if(fn.checkNullity(description)==true){
                    fn.setERROR(edtdescription,"Empty Field");
                }

                if(fn.checkNullity(income)==true){
                    fn.setERROR(edtincome,"Empty Field");
                }

                else{
                    listener.passdOtherData(occupationname, description,income);



                    //new BackGroundThread(getActivity(),"Adding Property",1).execute(new Functions().property(0,location,plotnumber,selected,ocupancy,income,userid,))
                }

                break;



            case R.id.imgotheroccupation:
                listener.ButtonPressed(2,0,4);
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences pref=getActivity().getSharedPreferences(Constants.STORAGE_PREF, Context.MODE_PRIVATE);
        String image=pref.getString(Constants.OTHER_IMAGE,"none");

        if(!image.equalsIgnoreCase("none")){

            imgUploadFile.setImageURI(Uri.parse(image));
        }


    }
}