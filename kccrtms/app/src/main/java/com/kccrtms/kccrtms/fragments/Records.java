package com.kccrtms.kccrtms.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by root on 10/12/15.
 */
import com.kccrtms.kccrtms.Constants.ConstantsClass;
import com.kccrtms.kccrtms.Database.DBContract;
import com.kccrtms.kccrtms.Database.SqliteDBHelper;
import com.kccrtms.kccrtms.R;

public class Records extends Fragment implements View.OnClickListener {

    View view;

    ImageView ingorder,imgsales;


    private int regionID;
    private int teritoryID;
    private int areaid;


    public interface NewRecordData {

          public void OnButtonClicked(int back);
    }

    NewRecordData listsener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listsener = (NewRecordData) activity;
        } catch (ClassCastException cast) {
            throw new ClassCastException(activity.toString() + " Should implement ActionListener interface");
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //  getLoaderManager().initLoader(SPIN_OUTLET_LOAD_ID, null, this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.records_fragment, container, false);
        ingorder = (ImageView) view.findViewById(R.id.imgsetorders);
        imgsales = (ImageView) view.findViewById(R.id.imgsetsales);



        ingorder.setOnClickListener(this);
        imgsales.setOnClickListener(this);
        //  btnnext.setOnClickListener(this);


        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgsetorders:


                listsener.OnButtonClicked(5);

                break;

            case R.id.imgsetsales:

                 listsener.OnButtonClicked(6);


                break;
        }
    }





    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onResume() {
        super.onResume();
        listsener.OnButtonClicked(8);
    }


}


