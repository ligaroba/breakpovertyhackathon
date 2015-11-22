package root.com.jiranimmicrocredit;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import root.com.jiranimmicrocredit.fragments.MyApplication;
import root.com.jiranimmicrocredit.storage.SqlDBHandler;
import root.com.jiranimmicrocredit.utils.Constants;
import root.com.jiranimmicrocredit.utils.SetterGetters;


/**
 * Created by Origicheck on 30/10/2015.
 */
public class ApplicationDetails extends AppCompatActivity {


    int color;
    private RecyclerView recyclerplans;
    private CustomAdapter adapter;
    List<SetterGetters> plansdata;


    ImageButton call,sms,email;
    private SqlDBHandler db;
    private SharedPreferences pref;

    public ApplicationDetails() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applicationdetails);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Loan Application Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pref=getSharedPreferences(Constants.APP_DATA, Context.MODE_PRIVATE);
       int idloanapplication= pref.getInt(Constants.PARAM_ID_LOANAPPLICATION,0);
        if (idloanapplication != 0) {



        recyclerplans = (RecyclerView)findViewById(R.id.recyler_plans);
        recyclerplans.setHasFixedSize(true);
        LinearLayoutManager llmanager=new LinearLayoutManager(this);
        llmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerplans.setLayoutManager(llmanager);

        db=new SqlDBHandler(this);
        plansdata=db.getMyLoanDetails(idloanapplication);
        adapter= new CustomAdapter(plansdata);
        recyclerplans.setAdapter(adapter);

            // and get whatever type user account id is
        }







    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
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
            contactViewHolder.vpaymentperiod.setText("Payment Duration: "+ ci.getPaymentPeriod()+ " weeks");
            contactViewHolder.vcurrentpaymentstatus.setText("Status: "+ci.getLoanStatus());
            contactViewHolder.vexpectedendinstallment.setText("Payment Ends: " +ci.getPaymentEndDate());
            contactViewHolder.vinstalment.setText("Instalments " +ci.getInstalmentAmount());
            contactViewHolder.vtotalamountpaid.setText("Total Amount Paid: " +ci.getTotalAmountPaid());
            contactViewHolder.vtotalamounttobepaid.setText("Total Amount To Be Paid: " +ci.getTotalAmounttobePaid());
            contactViewHolder.vpurpose.setText(Html.fromHtml("Purpose: <br> " +ci.getPurpose()));
            contactViewHolder.vtimeadded.setText(Html.fromHtml("Time Applied:  " +ci.getTimeAdded()));
            contactViewHolder.loanAppId=ci.getId();

        }

        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.custom_loans_card, viewGroup, false);

            return new ContactViewHolder(itemView);
        }

        public  class ContactViewHolder extends RecyclerView.ViewHolder {

            protected TextView vloan_amount;
            protected TextView vpaymentperiod;
            protected TextView vcurrentpaymentstatus;
            protected TextView vrpaymentStartDate;
            protected TextView vexpectedendinstallment;
            protected TextView vinstalment;
            protected TextView vtotalamountpaid;
            protected TextView vtotalamounttobepaid;
            protected TextView vpurpose;
            protected TextView vtimeadded;



            public int loanAppId;


            public ContactViewHolder(View v) {
                super(v);
                vrpaymentStartDate =  (TextView) v.findViewById(R.id.paymentstartdate);
                vloan_amount = (TextView)  v.findViewById(R.id.loanamount);
                vexpectedendinstallment = (TextView)  v.findViewById(R.id.expected_end_installment);
                vcurrentpaymentstatus= (TextView) v.findViewById(R.id.currentpaymentstatus);

                vpaymentperiod = (TextView)  v.findViewById(R.id.paymentduration);
                vinstalment = (TextView)  v.findViewById(R.id.instalment);
                vtotalamountpaid= (TextView) v.findViewById(R.id.totalamountpaid);
                vtotalamounttobepaid = (TextView)  v.findViewById(R.id.totalamounttobepaid);
                vpurpose = (TextView)  v.findViewById(R.id.purpose);
                vtimeadded= (TextView) v.findViewById(R.id.timeadded);
        ;



            }


        }



    }
}

