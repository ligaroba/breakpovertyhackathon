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

public class RecordSales extends DialogFragment implements View.OnClickListener,LoaderManager.LoaderCallbacks<Cursor>,AdapterView.OnItemSelectedListener {

    private static final int SPIN_PRODUCT_LOAD_ID =0 ;
    View view;
    Spinner inspinoutlets;
    Button btnback, btnnext, btnsalessave;
    SimpleCursorAdapter ItemAdapter;
    String[] SPINCOLUMSPRODUCT = {DBContract.ProductEntry._ID,DBContract.ProductEntry.KEY_PRODUCT};
    String[] SPINFROMPRODUCT = {DBContract.ProductEntry.KEY_PRODUCT};
    int[] SPINA = {android.R.id.text1};

    SqliteDBHelper sdb;
    SQLiteDatabase db;

    ConstantsClass consta;
    private int outletID;
    private String salesqty,salesprice,remarks;

    EditText edosaleqty,edsalesrprice,edremarks;
    Spinner inspinchannel;
    int channel;

    private int regionID;
    private int teritoryID;
    private int areaid;



    public interface RecordSalesData {

        public void onNewOutletDataDataShare(Bundle bundle);

        public void OnButtonClicked(int back);

        public void salesData(int rid, int tid, int aid, int oid, String s, Integer integer, Integer integer1, String sales);
    }

    RecordSalesData listsener;




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(SPIN_PRODUCT_LOAD_ID, null, this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.record_sales, container, false);
        edsalesrprice = (EditText) view.findViewById(R.id.editsaleprice);
        edosaleqty = (EditText) view.findViewById(R.id.editsalesquantity);
        edremarks= (EditText) view.findViewById(R.id.editsalesremarks);
        inspinchannel = (Spinner) view.findViewById(R.id.spinselectsalesprod);


        // btnnext = (Button) view.findViewById(R.id.btnoutletnext);

        sdb = new SqliteDBHelper(getActivity());


        ItemAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, sdb.getAll(DBContract.ProductEntry.PRODUCT_TABLE), SPINFROMPRODUCT, SPINA,0);
        ItemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);



        btnsalessave = (Button) view.findViewById(R.id.btnsalessave);

        btnsalessave.setOnClickListener(this);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnsalessave:

                SharedPreferences pref =getActivity().getSharedPreferences(consta.DATAPREF, Context.MODE_PRIVATE);

                int rid=pref.getInt(consta.REGIONID, 0);
                int tid= pref.getInt(consta.TERITORYID, 0);
                int aid= pref.getInt(consta.AREAID, 0);
                int oid= pref.getInt(consta.OUTLETID,0);

               /* SharedPreferences.Editor edit= pref.edit();
                edit.putString(consta.CHANNEL, String.valueOf(channel));
                edit.putInt(consta.SALESPRICE, Integer.valueOf(salesprice));
                edit.putInt(consta.SALESQTY, Integer.valueOf(salesqty));
                edit.putString(consta.SALES, "sales");
                edit.commit();*/
                remarks=edremarks.getText().toString();
                salesqty=edosaleqty.getText().toString();
                salesprice=edsalesrprice.getText().toString();

                long id=sdb.newrecord(rid,tid,aid,oid,String.valueOf(channel),Integer.valueOf(salesprice),Integer.valueOf(salesqty),"sales");
                if(id>0){
                    Toast.makeText(getActivity(),"sales saved",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(getActivity(),"sales failed",Toast.LENGTH_LONG).show();
            }

                break;
        }
    }





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinselectsalesprod:
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                channel = cursor.getInt(cursor.getColumnIndex(DBContract.ProductEntry._ID));
                String channelName = cursor.getString(cursor.getColumnIndex(DBContract.ProductEntry.KEY_PRODUCT));;



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


