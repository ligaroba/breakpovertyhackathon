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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by root on 10/12/15.
 */
import com.kccrtms.kccrtms.Constants.ConstantsClass;
import com.kccrtms.kccrtms.Database.DBContract;
import com.kccrtms.kccrtms.Database.SqliteDBHelper;
import com.kccrtms.kccrtms.R;

public class RecordOrder extends DialogFragment implements View.OnClickListener,LoaderManager.LoaderCallbacks<Cursor>,AdapterView.OnItemSelectedListener {

    private static final int SPIN_PROD_LOAD_ID =0;
    View view;
    Spinner inspinoutlets;
    Button btnback, btnnext, btnordersave;
    SimpleCursorAdapter ItemAdapter;
    String[] SPINCOLUMSPRODUCT = {DBContract.ProductEntry._ID,DBContract.ProductEntry.KEY_PRODUCT};
    String[] SPINFROMPRODUCT = {DBContract.ProductEntry.KEY_PRODUCT};

    int[] SPINA = {android.R.id.text1};

    SqliteDBHelper sdb;
    SQLiteDatabase db;

    ConstantsClass consta;
    private int outletID;
    private String orderqty,orderprice,remarks;

    EditText edorderqty,edorderprice,edremarks;
    Spinner inspinchannel;

    private int regionID;
    private int teritoryID;
    private int areaid;
    private int channel;

    @Override
    public Loader<Cursor> onCreateLoader(int loader_ID, Bundle args) {
        CursorLoader cursorLoader;
        switch (loader_ID) {
            case 0:
                return cursorLoader = new CursorLoader(getActivity(), DBContract.ProductEntry.CONTENT_URI, SPINCOLUMSPRODUCT, null, null, null);


        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        ItemAdapter.swapCursor(null);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    public interface RecordOrderData {

        public void onNewOutletDataDataShare(Bundle bundle);

        public void OnButtonClicked(int back);

        public void ordersData(int rid, int tid, int aid, int oid, String s, Integer integer, Integer integer1, String order);
    }

    RecordOrderData listsener;




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


            getLoaderManager().initLoader(SPIN_PROD_LOAD_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.record_order, container, false);
        edorderprice = (EditText) view.findViewById(R.id.editorderprice);
        edorderqty = (EditText) view.findViewById(R.id.editorderquantity);
        edremarks= (EditText) view.findViewById(R.id.editorderremarks);
        inspinchannel = (Spinner) view.findViewById(R.id.spinselectorderprod);



        // btnnext = (Button) view.findViewById(R.id.btnoutletnext);

        sdb = new SqliteDBHelper(getActivity());


        ItemAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, sdb.getAll(DBContract.ProductEntry.PRODUCT_TABLE), SPINFROMPRODUCT, SPINA,0);
        ItemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);


        btnordersave = (Button) view.findViewById(R.id.btnordersave);
        btnordersave.setOnClickListener(this);
        inspinchannel.setAdapter(ItemAdapter);
        inspinchannel.setOnItemSelectedListener(this);


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

            case R.id.btnordersave:

                SharedPreferences pref =getActivity().getSharedPreferences(consta.DATAPREF, Context.MODE_PRIVATE);

                int rid=pref.getInt(consta.REGIONID, 0);
                int tid= pref.getInt(consta.TERITORYID, 0);
                int aid= pref.getInt(consta.AREAID, 0);
                int oid= pref.getInt(consta.OUTLETID,0);

                remarks=edremarks.getText().toString();
                orderqty=edorderqty.getText().toString();
                orderprice=edorderprice.getText().toString();

               // listsener.ordersData(rid, tid, aid, oid, String.valueOf(channel), Integer.valueOf(orderprice), Integer.valueOf(orderqty), "order");

                long id=sdb.newrecord(rid,tid,aid,oid,String.valueOf(channel), Integer.valueOf(orderprice),Integer.valueOf(orderqty),"order");
                if(id>0){
                    Toast.makeText(getActivity(),"order saved",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(getActivity(),"order failed",Toast.LENGTH_LONG).show();
                }


                break;
        }
    }





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinselectorderprod:
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                channel = cursor.getInt(cursor.getColumnIndex(DBContract.ProductEntry._ID));
                String channelName = cursor.getString(cursor.getColumnIndex(DBContract.ProductEntry.KEY_PRODUCT));


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


