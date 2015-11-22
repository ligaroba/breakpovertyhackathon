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
public class Regions extends Fragment implements View.OnClickListener,LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemSelectedListener {

    View view;
    Spinner inspinregion, inspinteritory,inspinarea;
    Button btnback, btnnext;
    SimpleCursorAdapter ItemAdapter;
    SimpleCursorAdapter teriAdapter;
    SimpleCursorAdapter areaAdapter;
    //private List<SetterGetter> list;
    String[] SPINCOLUMSREGION = {DBContract.RegionEntry._ID, DBContract.RegionEntry.KEY_REGIONNAME};
    String[] SPINFROMREGION = {DBContract.RegionEntry.KEY_REGIONNAME};


    String[] SPINCOLUMSTERITORY = {DBContract.TeritoryEntry._ID, DBContract.TeritoryEntry.KEY_TERITORYNAME};
    String[] SPINFROMTERITORY = {DBContract.TeritoryEntry.KEY_TERITORYNAME};



    String[] SPINCOLUMSAREA = {DBContract.AreaEntry._ID,DBContract.AreaEntry.CAREA_NAME};
    String[] SPINFROMAREA = {DBContract.AreaEntry.CAREA_NAME};

    int[] SPINA = {android.R.id.text1};



    private int productID;
    String quantityItem;

    int SPIN_REGION_LOAD_ID = 0;
    int SPIN_TERITORY_LOAD_ID = 1;
    int SPIN_LOAD_AREA_ID = 2;
    int ITEM_LIST_LOAD_ID = 1;




    SQLiteDatabase db;
    private String rowId;
    int swapCursor = 0;

    int[] itempos=new int[]{};
    ConstantsClass consta;
    SqliteDBHelper sdb;
    private int regionID;
    private String regionname;
    private  int teritoryID;

    private String teritoryname;
    private String areaname;
    private int areaid;


    public interface RegionsData {

        public void onDataShare(Bundle data);

    }

    RegionsData listsener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listsener = (RegionsData) activity;
        } catch (ClassCastException cast) {
            throw new ClassCastException(activity.toString() + " Should implement ActionListener interface");
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(SPIN_REGION_LOAD_ID, null, this);
        getLoaderManager().initLoader(SPIN_TERITORY_LOAD_ID, null, this);
        getLoaderManager().initLoader(SPIN_LOAD_AREA_ID, null, this);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.setup_regions, container, false);


        btnnext = (Button) view.findViewById(R.id.btnregionnext);
        inspinarea =(Spinner) view.findViewById(R.id.spinarea);
        inspinteritory=(Spinner) view.findViewById(R.id.spinteritory);
        inspinregion =(Spinner) view.findViewById(R.id.spinregion);


        sdb=new SqliteDBHelper(getActivity());

        SharedPreferences pref=getActivity().getSharedPreferences(consta.LOGINPREF,1);
        if(pref.getString(consta.USERID, null)!=null){
            String userid = pref.getString(consta.USERID, null);
        }




        ItemAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, sdb.getRegions(), SPINFROMREGION, SPINA,0);

        ItemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);


        inspinregion.setAdapter(ItemAdapter);


        if(sdb.getRegions()!=null) {
            inspinregion.setOnItemSelectedListener(this);
            inspinteritory.setOnItemSelectedListener(this);
            inspinarea.setOnItemSelectedListener(this);
        }
       // btnback.setOnClickListener(this);
        btnnext.setOnClickListener(this);


        if (savedInstanceState != null) {


                  //  String selection = DukaContract.ShoppingEntry.SHOPPING_TABLE + "." + DukaContract.ShoppingEntry._ID + " = " + savedInstanceState.getIntegerArrayList(ROWID).get(k);
                   // cursor = getActivity().getContentResolver().query(DukaContract.ShoppingEntry.CONTENT_URI, null, selection, null, null);


            }









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
            case R.id.btnregionnext://

                Bundle data=new Bundle();
                data.putInt(consta.REGIONID,regionID);
                data.putInt(consta.TERITORYID, teritoryID);
                data.putInt(consta.AREAID,areaid);

                SharedPreferences pref =getActivity().getSharedPreferences(consta.DATAPREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit=pref.edit();
                edit.putInt(consta.REGIONID,regionID);
                edit.putInt(consta.TERITORYID, teritoryID);
                edit.putInt(consta.AREAID, areaid);
                edit.commit();


                    listsener.onDataShare(data);
                      break;

        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int loader_ID, Bundle bundle) {

        CursorLoader cursorLoader;
        switch (loader_ID) {
            case 0:
                return cursorLoader = new CursorLoader(getActivity(), DBContract.RegionEntry.CONTENT_URI, SPINCOLUMSREGION, null, null, null);


            case 1:
                String selection =  DBContract.TeritoryEntry.TERITORY_TABLE + "." +DBContract.TeritoryEntry.COLUMN_LOC_KEY + " = " + regionID;
               return cursorLoader=new CursorLoader(getActivity(),DBContract.TeritoryEntry.CONTENT_URI,SPINCOLUMSTERITORY,selection,null,null);
               // cursor = getActivity().getContentResolver().query(DukaContract.ShoppingEntry.CONTENT_URI, null, selection, n

            case 3:
                String selectiond =  DBContract.AreaEntry.AREA_TABLE + "." +DBContract.AreaEntry.COLUMN_LOC_KEY_REGION+ " = " + regionID +DBContract.AreaEntry.AREA_TABLE + "." +DBContract.AreaEntry.COLUMN_LOC_KEY_TERITORY+ " = " + teritoryID;;
               return cursorLoader=new CursorLoader(getActivity(),DBContract.AreaEntry.CONTENT_URI,SPINCOLUMSAREA,selectiond,null,null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }



    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

        ItemAdapter.swapCursor(null);
        teriAdapter.swapCursor(null);
        areaAdapter.swapCursor(null);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinregion:

                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                regionID = cursor.getInt(cursor.getColumnIndex(DBContract.RegionEntry._ID));
                regionname= cursor.getString(cursor.getColumnIndex(DBContract.RegionEntry.KEY_REGIONNAME));


                teriAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, sdb.getTeritory(regionID), SPINFROMTERITORY, SPINA,0);
                teriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                inspinteritory.setAdapter(teriAdapter);

                break;
            case R.id.spinteritory:

                Cursor cursorter = (Cursor) parent.getItemAtPosition(position);
                teritoryID = cursorter.getInt(cursorter.getColumnIndex(DBContract.TeritoryEntry._ID));
                teritoryname= cursorter.getString(cursorter.getColumnIndex(DBContract.TeritoryEntry.KEY_TERITORYNAME));

                areaAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, sdb.getArea(regionID, teritoryID), SPINFROMAREA, SPINA,0);
                areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                inspinarea.setAdapter(areaAdapter);




                inspinarea.setAdapter(areaAdapter);

                break;

            case R.id.spinarea:
                Cursor cursorarea = (Cursor) parent.getItemAtPosition(position);
                areaid = cursorarea.getInt(cursorarea.getColumnIndex(DBContract.AreaEntry._ID));
                areaname= cursorarea.getString(cursorarea.getColumnIndex(DBContract.AreaEntry.CAREA_NAME));

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
