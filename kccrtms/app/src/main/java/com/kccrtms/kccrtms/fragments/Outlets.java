package com.kccrtms.kccrtms.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.Spinner;

import com.kccrtms.kccrtms.Constants.ConstantsClass;
import com.kccrtms.kccrtms.Database.DBContract;
import com.kccrtms.kccrtms.Database.SqliteDBHelper;
import com.kccrtms.kccrtms.R;

/**
 * Created by root on 10/12/15.
 */
public class Outlets extends Fragment implements View.OnClickListener,LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemSelectedListener {

    View view;
    Spinner inspinoutlets;
    Button btnback, btnnext,btnnewoutlet;
    SimpleCursorAdapter ItemAdapter;

    String[] SPINCOLUMSOUTLET = {DBContract.OutletEntry._ID, DBContract.OutletEntry.OUTLET_NAME};
    String[] SPINFROMOUTLET = {DBContract.OutletEntry.OUTLET_NAME};



    int[] SPINA = {android.R.id.text1};

    SqliteDBHelper sdb;

    private int productID;
    String quantityItem;

    int SPIN_OUTLET_LOAD_ID = 0;





    SQLiteDatabase db;
    private String rowId;
    int swapCursor = 0;

    int[] itempos=new int[]{};
    ConstantsClass consta;
    private int outletID;
    private String outletname;

    private int regionID;
     private int teritoryID;
    private int areaid;



    public interface OutletData {

        public void onOutletDataDataShare(Bundle bundle);
        public void OnButtonClicked(int back);
    }

    OutletData listsener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listsener = (OutletData) activity;
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
        view = inflater.inflate(R.layout.outlets, container, false);
        btnback = (Button) view.findViewById(R.id.btnoutletback);
        btnnewoutlet = (Button) view.findViewById(R.id.btnaddoutlet);
        btnnext = (Button) view.findViewById(R.id.btnoutletnext);
        inspinoutlets =(Spinner) view.findViewById(R.id.spinselectoutlet);
        sdb =new SqliteDBHelper(getActivity());

       ;



        SharedPreferences pref =getActivity().getSharedPreferences(consta.DATAPREF, Context.MODE_PRIVATE);

        if(pref!=null){


            regionID=pref.getInt(consta.REGIONID, 0);
            teritoryID= pref.getInt(consta.TERITORYID, 0);
            areaid= pref.getInt(consta.AREAID, 0);


            if(sdb.getOutlets(regionID,teritoryID, areaid)!=null){

                ItemAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, sdb.getOutlets(regionID,teritoryID, areaid), SPINFROMOUTLET, SPINA);
                ItemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                inspinoutlets.setAdapter(ItemAdapter);
                inspinoutlets.setOnItemSelectedListener(this);
            }else{

                listsener.OnButtonClicked(2);
            }

        }





        btnback.setOnClickListener(this);
        btnnext.setOnClickListener(this);
        btnnewoutlet.setOnClickListener(this);


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
            case R.id.btnoutletnext://


                SharedPreferences pref =getActivity().getSharedPreferences(consta.DATAPREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit=pref.edit();
                edit.putInt(consta.OUTLETID, outletID);
                edit.commit();
                listsener.OnButtonClicked(7);

                   break;
            case R.id.btnoutletback:

                listsener.OnButtonClicked(1);


                break;
            case R.id.btnaddoutlet:

                listsener.OnButtonClicked(2);


                break;
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int loader_ID, Bundle bundle) {

        CursorLoader cursorLoader;
        switch (loader_ID) {
            case 0:
                String selectiond = DBContract.OutletEntry.OUTLETS_TABLE+"."+ DBContract.OutletEntry.COLUMN_LOC_KEY_AREA + " =" + areaid + DBContract.OutletEntry.OUTLETS_TABLE + "." +DBContract.OutletEntry.COLUMN_LOC_KEY+ " = " + regionID +DBContract.OutletEntry.OUTLETS_TABLE + "." +DBContract.OutletEntry.COLUMN_LOC_KEY_TERITORY+ " = " + teritoryID;
                return cursorLoader = new CursorLoader(getActivity(), DBContract.RegionEntry.CONTENT_URI, SPINCOLUMSOUTLET, null, null, null);




        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        inspinoutlets.setAdapter(ItemAdapter);

    }



    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

        ItemAdapter.swapCursor(null);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinselectoutlet:

                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                outletID = cursor.getInt(cursor.getColumnIndex(DBContract.OutletEntry._ID));
                outletname= cursor.getString(cursor.getColumnIndex(DBContract.OutletEntry.OUTLET_NAME));



                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }









    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
