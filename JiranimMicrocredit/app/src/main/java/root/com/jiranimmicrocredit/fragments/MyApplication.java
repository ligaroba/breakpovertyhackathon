package root.com.jiranimmicrocredit.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import root.com.jiranimmicrocredit.ApplicationDetails;
import root.com.jiranimmicrocredit.R;
import root.com.jiranimmicrocredit.storage.SqlDBHandler;
import root.com.jiranimmicrocredit.utils.Constants;
import root.com.jiranimmicrocredit.utils.SetterGetters;

/**
 * Created by root on 11/13/15.
 */
public class MyApplication extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private View view;
    private int SPIN_LOAD_ID=0;
    private RecyclerView recyclerplans;
    private CustomAdapter adapter;
    SqlDBHandler db;
    private List<SetterGetters> plansdata;
    SharedPreferences pref;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(SPIN_LOAD_ID, null, this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.plans, container, false);
        recyclerplans = (RecyclerView) view.findViewById(R.id.recyler_plans);
        recyclerplans.setHasFixedSize(true);
        LinearLayoutManager llmanager=new LinearLayoutManager(getActivity());
        llmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerplans.setLayoutManager(llmanager);
        pref=getActivity().getSharedPreferences(Constants.APP_DATA, Context.MODE_PRIVATE);


        db=new SqlDBHandler(getActivity());
        plansdata=db.getMyLoan();
        adapter= new CustomAdapter(plansdata);
        recyclerplans.setAdapter(adapter);


        if (savedInstanceState != null) {





        }





        return view;
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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ContactViewHolder> {


        private List<SetterGetters> loansList;
        public CustomAdapter(List<SetterGetters> blackList) {
            this.loansList = blackList;
        }

        @Override
        public int getItemCount() {
            return loansList.size();
        }

        @Override
        public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
            SetterGetters ci = loansList.get(i);
            contactViewHolder.vrpaymentStartDate.setText("Payment Starts: "+ci.getPaymentStartDate());
            contactViewHolder.vloan_amount.setText("Loan Amount: "+ci.getLoanAmount());
           // contactViewHolder.vpaymentperiod.setText(ci.getPaymentPeriod());
            contactViewHolder.vcurrentpaymentstatus.setText("Status: "+ci.getLoanStatus());
            contactViewHolder.vexpectedendinstallment.setText("Payment Ends: " +ci.getPaymentEndDate());
            contactViewHolder.loanAppId=ci.getId();
            contactViewHolder.lmoredetails.setOnClickListener(new CardClickedListener(contactViewHolder.loanAppId));
        }

        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.activity_myloans, viewGroup, false);

            return new ContactViewHolder(itemView);
        }

        public  class ContactViewHolder extends RecyclerView.ViewHolder {
            protected TextView vrpaymentStartDate;
            protected TextView vloan_amount;
            protected TextView vpaymentperiod;
            protected TextView vcurrentpaymentstatus;
            protected TextView vexpectedendinstallment;
            protected LinearLayout lmoredetails;

            public int loanAppId;


            public ContactViewHolder(View v) {
                super(v);
                vrpaymentStartDate =  (TextView) v.findViewById(R.id.paymentstartdate);
                vloan_amount = (TextView)  v.findViewById(R.id.loan_amount);
               // vpaymentperiod = (TextView)  v.findViewById(R.id.paymentperiod);
                vexpectedendinstallment = (TextView)  v.findViewById(R.id.expected_end_installment);
                vcurrentpaymentstatus= (TextView) v.findViewById(R.id.current_payment_status);
                lmoredetails= (LinearLayout) v.findViewById(R.id.moredetails);


            }


        }



    }


    public class CardClickedListener implements View.OnClickListener {
        private int loanAppId;

        public CardClickedListener(int loanAppId) {
            this.loanAppId=loanAppId;
        }

        @Override
        public void onClick(View view) {
            Log.e("loanAppId", String.valueOf(loanAppId));
            pref.edit().putInt(Constants.PARAM_ID_LOANAPPLICATION, loanAppId).commit();
            Intent intent =new Intent(getActivity(),ApplicationDetails.class);
           // intent.putExtra(Constants.PARAM_ID_LOANAPPLICATION, loanAppId);
            getActivity().startActivity(intent);

        }
    }




}
