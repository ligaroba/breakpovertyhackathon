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

public class NewOutlet extends DialogFragment implements View.OnClickListener,LoaderManager.LoaderCallbacks<Cursor>,AdapterView.OnItemSelectedListener {

    View view;
    Spinner inspinoutlets;
    Button btnback, btnnext, btnsaveoutlet;
    SimpleCursorAdapter ItemAdapter;

    String[] SPINCOLUMSCHANNEL = {DBContract.ChannelEntry._ID, DBContract.ChannelEntry.KEY_CHANNEL};
    String[] SPINFROMCHANNEL = {DBContract.ChannelEntry.KEY_CHANNEL};


    int[] SPINA = {android.R.id.text1};

    SqliteDBHelper sdb;


    ConstantsClass consta;
    private int outletID;
    private String outletname,cname,location,mobile,channel;

    EditText edcname,edmobile,edlocation,edoutletname;
    Spinner inspinchannel;


    





    @Override
    public Loader<Cursor> onCreateLoader(int loader_ID, Bundle args) {
        CursorLoader cursorLoader=null;
        switch (loader_ID) {
            case 0:
                return cursorLoader = new CursorLoader(getActivity(), DBContract.ProductEntry.CONTENT_URI,  SPINCOLUMSCHANNEL, null, null, null);


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


    public interface NewOutletData {

        public void onNewOutletDataDataShare(Bundle bundle);

        public void OnButtonClicked(int back);

       public  void newoutletsData(int rid, int tid, int aid, String outletname, String cname, String location, String mobile, String channel);
    }

    NewOutletData listsener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listsener = (NewOutletData) activity;
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
        view = inflater.inflate(R.layout.newoutlet, container, false);
        edcname = (EditText) view.findViewById(R.id.editfullname);
        edmobile = (EditText) view.findViewById(R.id.editphone);
        edoutletname= (EditText) view.findViewById(R.id.editoutletname);
        inspinchannel = (Spinner) view.findViewById(R.id.spinchannel);


       // btnnext = (Button) view.findViewById(R.id.btnoutletnext);

        sdb = new SqliteDBHelper(getActivity());
        ItemAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, sdb.getAll(DBContract.ChannelEntry.CHANNEL_TABLE), SPINFROMCHANNEL, SPINA,0);
        ItemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        inspinchannel.setAdapter(ItemAdapter);
        inspinchannel.setOnItemSelectedListener(this);
        btnsaveoutlet = (Button) view.findViewById(R.id.btnsaveoutlet);
        btnsaveoutlet.setOnClickListener(this);
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

            case R.id.btnsaveoutlet:

                SharedPreferences pref =getActivity().getSharedPreferences(consta.DATAPREF, Context.MODE_PRIVATE);

                int rid=pref.getInt(consta.REGIONID, 0);
               int tid= pref.getInt(consta.TERITORYID, 0);
               int aid= pref.getInt(consta.AREAID, 0);
               int oid= pref.getInt(consta.OUTLETID,0);

                outletname=edoutletname.getText().toString();
                cname=edcname.getText().toString();
                mobile=edmobile.getText().toString();

              listsener.newoutletsData(rid, tid, aid, outletname, cname, location, mobile, channel);





                break;
        }
    }





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinchannel:
                String[] choice = getResources().getStringArray(R.array.channels);

                    channel=choice[position];
                    //Toast.makeText(getActivity(),channel,Toast.LENGTH_LONG).show();



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


