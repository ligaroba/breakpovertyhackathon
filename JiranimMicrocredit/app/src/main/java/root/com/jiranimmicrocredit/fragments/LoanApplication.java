package root.com.jiranimmicrocredit.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import root.com.jiranimmicrocredit.BackgroudThread.BackGroundThread;
import root.com.jiranimmicrocredit.R;
import root.com.jiranimmicrocredit.providers.DBContract;
import root.com.jiranimmicrocredit.utils.Constants;
import root.com.jiranimmicrocredit.utils.Functions;
import root.com.jiranimmicrocredit.utils.NetworkUtils;
import root.com.jiranimmicrocredit.utils.SetterGetters;


/**
 * Created by root on 11/25/14.
 */
public class LoanApplication extends Fragment implements View.OnClickListener,LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemSelectedListener {
    View view;
    Spinner spn_available_Loans;
    EditText edtloanAmount,edtrepaymentdate,editpurpose;
    Button btnapply;
    SimpleCursorAdapter ItemAdapter;
    private List<SetterGetters> list;
    String[] SPINCOLUMS = {DBContract.LoanCluster._ID, DBContract.LoanCluster.KEY_LOANAME};
    String[] SPINFROM = {DBContract.LoanCluster.KEY_LOANAME};

    int[] SPINA = {android.R.id.text1};
    Functions fn=new Functions();



    int SPIN_LOAD_ID = 0;
    int ITEM_LIST_LOAD_ID = 1;
    long rowid;


    SQLiteQueryBuilder getProduct;
    SQLiteDatabase db;

    private String productName;
    int[] itempos=new int[]{};
    Constants consta;

    int userid=1;
    private int loanID;
    private String loanname;
    private String repaymentdate;

    public interface ApllicationListener {

        public void onDataShare(Bundle bundle);
    }

    ApllicationListener listsener;


   /* @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listsener = (ApllicationListener) activity;
        } catch (ClassCastException cast) {
            throw new ClassCastException(activity.toString() + " Should implement ActionListener interface");
        }

    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(SPIN_LOAD_ID, null, this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.loan_application, container, false);

        btnapply = (Button) view.findViewById(R.id.btnapply);
        edtloanAmount = (EditText) view.findViewById(R.id.edit_loan_amount);
      //  edtrepaymentdate = (EditText) view.findViewById(R.id.edit_repayment_date);
        editpurpose = (EditText) view.findViewById(R.id.edit_purpose);

//        edtrepaymentdate.setEnabled(false);
      //  spn_available_Loans = (Spinner) view.findViewById(R.id.spn_available_Loans);
       /* if(NetworkUtils.getConnectivityStatusString(getActivity()).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(getActivity()).equalsIgnoreCase("mobile data Enabled")||Constants.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||Constants.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {


            new BackGroundThread(getActivity(), "loading", 0).execute(fn.getUserLoans(userid));

        }*/


        ItemAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, null, SPINFROM, SPINA);

        if (savedInstanceState != null) {





        }
        ItemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
       // spn_available_Loans.setAdapter(ItemAdapter);
       // spn_available_Loans.setOnItemSelectedListener(this);
        btnapply.setOnClickListener(this);




        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnapply:
                String loanAmount=fn.getText(edtloanAmount);
                String purpose=fn.getText(editpurpose);


                if(fn.checkNullity(loanAmount)==true){
                    fn.setERROR(edtloanAmount,"Empty Field");
                }
                if(fn.checkNullity(purpose)==true){
                    fn.setERROR(editpurpose,"Empty Field");
                }

                else{
                    String macaddress="DSFJKAFADHJDF";

                    if(NetworkUtils.getConnectivityStatusString(getActivity()).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(getActivity()).equalsIgnoreCase("mobile data Enabled")||Constants.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||Constants.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {

                        new BackGroundThread(getActivity(), "sending application", 1).execute(fn.loanApplication(0, userid, Integer.parseInt(loanAmount),macaddress, purpose));
                    }else {
                        Toast.makeText(getActivity(), Constants.CONNECTION_FAILED, Toast.LENGTH_SHORT).show();

                    }

                }



                break;
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int loader_ID, Bundle bundle) {

        CursorLoader cursorLoader;
        switch (loader_ID) {
            case 0:

                return cursorLoader = new CursorLoader(getActivity(), DBContract.LoanCluster.CONTENT_URI, SPINCOLUMS, null, null, null);


        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        ItemAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        ItemAdapter.swapCursor(null);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
         /*   case R.id.spn_available_Loans:
                int repaymentperiod=0;
                int repaymentdays=0;

                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                loanID = cursor.getInt(cursor.getColumnIndex(DBContract.LoanCluster._ID));
                loanname = cursor.getString(cursor.getColumnIndex(DBContract.LoanCluster.KEY_LOANAME));
                Cursor dcursor=null;


                try {

                    String selection = DBContract.LoanCluster.LOAN_CLUSTER_TABLE + "." + DBContract.LoanCluster._ID + " = " + loanID;
                    String[] projection={DBContract.LoanCluster.KEY_REPAYMENTPERIOD};

                    dcursor = getActivity().getContentResolver().query(DBContract.LoanCluster.CONTENT_URI, projection, selection, null, null);
                if(dcursor.moveToFirst()){
                 repaymentperiod= Integer.parseInt(dcursor.getString(dcursor.getColumnIndex(DBContract.LoanCluster.KEY_REPAYMENTPERIOD)));
                }
                    repaymentdays= repaymentperiod*7;
                    Date dt=new Date();

                    SimpleDateFormat sdf=new SimpleDateFormat("dd-mm-yyyy");
                    Calendar c=Calendar.getInstance();
                    c.add(Calendar.DAY_OF_MONTH,repaymentdays);
                    repaymentdate=sdf.format(c.getTime());
                    edtrepaymentdate.setText(repaymentdate);


                } catch (IndexOutOfBoundsException in) {
                    in.printStackTrace();
                }


                break;
                */

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