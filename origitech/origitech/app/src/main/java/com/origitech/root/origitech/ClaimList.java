package com.origitech.root.origitech;


import android.support.v4.app.Fragment;

/**
 * Created by root on 10/13/15.
 */


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.origitech.root.origitech.R;
import com.origitech.root.origitech.constants.ConstantParams;
import com.origitech.root.origitech.constants.SetterGetter;
import com.origitech.root.origitech.databases.Database;
import com.origitech.root.origitech.functions.Functions;
import com.origitech.root.origitech.network.BackgroundThread;
import com.origitech.root.origitech.utils.NetworkUtils;

import java.util.List;

/**
 * Created by root on 10/10/15.
 */
public class ClaimList extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    View view;
    Button btnVerify;
    TextView tvtInputCode;

    ClaimListData listener;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeLayout;
    Database db;

    private CustomAdapter adapter;
    public List<SetterGetter> ClaimList;
    private int userid;


    public interface ClaimListData{
        public void onClaimListSenData(String code);
        public void refreshClaim(int refresh);

    }






    @Nullable
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claimlist);
       // view= inflater.inflate(R.layout.claimlist, container, false);

        recyclerView= (RecyclerView) findViewById(R.id.rcyclaimList);
        swipeLayout=(SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Claim List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db=new Database(this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llmanager=new LinearLayoutManager(this);
        llmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llmanager);
        adapter= new CustomAdapter(db.getVerificationList());
        recyclerView.setAdapter(adapter);

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onRefresh() {
        if(db.getUserId()!=-1){
            userid=db.getUserId();

        if(NetworkUtils.getConnectivityStatusString(
                this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(
                this).equalsIgnoreCase("mobile data Enabled")|| ConstantParams.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {
            new BackgroundThread(
                    this,"",0).execute(new Functions().getVerificationHistory(userid));
        }else{
            Toast.makeText(this,ConstantParams.CONNECTION_FAILED,Toast.LENGTH_SHORT).show();;
        }
        }
        adapter=new CustomAdapter(db.getVerificationList());
        recyclerView.setAdapter(adapter);
        swipeLayout.setRefreshing(false);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnverify:
                String usercode = tvtInputCode.getText().toString();

                if (TextUtils.isEmpty(usercode)) {

                    Toast.makeText(this, "Please Enter Code", Toast.LENGTH_LONG).show();
                } else {

                    //listener.onVerifySenData(usercode);
                }

                break;

        }

    }


    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ContactViewHolder> {


        private List<SetterGetter> claimList;
        public CustomAdapter(List<SetterGetter> blackList) {
            this.claimList = blackList;
        }

        @Override
        public int getItemCount() {
            return claimList.size();
        }

        @Override
        public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
            SetterGetter ci = claimList.get(i);
            contactViewHolder.vcompayname.setText("Company: "+ ci.getCompanyname());
            contactViewHolder.vproductname.setText("Product :"+ ci.getProductName());
            contactViewHolder.vserial.setText("Serial:  "+ ci.getProductSerial());
            contactViewHolder.vmodal.setText("Model:  "+ ci.getModel());
            contactViewHolder.vdate.setText(ci.getTimeAdded());
            contactViewHolder.bcode.setText("code: " +ci.getUniquecode());
            contactViewHolder.vtype.setText("Type:  " +ci.getProductType());
        }

        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.custom_claim_listlayout, viewGroup, false);

            return new ContactViewHolder(itemView);
        }

        public  class ContactViewHolder extends RecyclerView.ViewHolder {
            protected TextView vcompayname;
            protected TextView vproductname;
            protected TextView vserial;
            protected TextView vmodal;
            protected TextView vdate;
            protected TextView vtype;
            protected Button bcode;

            public ContactViewHolder(View v) {
                super(v);
                vcompayname =  (TextView) v.findViewById(R.id.tvtcompanyName);
                vproductname = (TextView)  v.findViewById(R.id.txtproductName);
                vserial = (TextView)  v.findViewById(R.id.txtserial);
                vmodal = (TextView)  v.findViewById(R.id.txtmodel);
                vdate = (TextView)  v.findViewById(R.id.txtdate);
                vtype = (TextView)  v.findViewById(R.id.txttype);
                bcode = (Button) v.findViewById(R.id.btnuniquecode);
            }
        }


    }
}

