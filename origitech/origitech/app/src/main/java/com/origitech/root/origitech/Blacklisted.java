package com.origitech.root.origitech;

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
import android.text.Html;
import android.util.Log;
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
 * Created by Origicheck on 11/10/2015.
 */
public class Blacklisted extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeLayout;
    Database db;
   BlackListLisner listener;
    private CustomAdapter adapter;
    public List<SetterGetter> blackList;
    private List<SetterGetter> blackListdata;
    TextView textEmptyview;


    Toolbar toolbar;

    public interface BlackListLisner{
        public void fetchBlacklistAsych(int refresh);
    }

    View view;



   /* @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener = (BlackListLisner) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement AuthenticationDialogListener");

        }



    }*/



    @Nullable
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blacklist);

        recyclerView= (RecyclerView) findViewById(R.id.rcyblackList);
        swipeLayout=(SwipeRefreshLayout) findViewById(R.id.swipeContainer);



        db=new Database(this);
        blackListdata=db.getBlacklist();

       // Log.e("Blacklisted",blackListdata.get(0).getShopname());

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Black listed");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llmanager=new LinearLayoutManager(this);
        llmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llmanager);
        adapter= new CustomAdapter(blackListdata);
        recyclerView.setAdapter(adapter);


        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        Log.e("BLACLIST", db.getBlacklist().toString());




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

       // listener.fetchBlacklistAsych(1);
        if(NetworkUtils.getConnectivityStatusString(this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(this).equalsIgnoreCase("mobile data Enabled")|| ConstantParams.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {
            new BackgroundThread(this,"",0).execute(new Functions().getBlacklist());
        }else{
            Toast.makeText(this, ConstantParams.CONNECTION_FAILED, Toast.LENGTH_SHORT).show();;
        }

        adapter =new CustomAdapter(db.getBlacklist());
        recyclerView.setAdapter(adapter);
        swipeLayout.setRefreshing(false);
    }



    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ContactViewHolder> {


        private List<SetterGetter> blackList;
        public CustomAdapter(List<SetterGetter> blackList) {
            this.blackList = blackList;
        }

        @Override
        public int getItemCount() {
            return blackList.size();
        }

        @Override
        public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
            SetterGetter ci = blackList.get(i);
            contactViewHolder.vdealername.setText(ci.getDealername());
            contactViewHolder.vlocation.setText(ci.getLocation());
            contactViewHolder.voffencetype.setText(ci.getOffencetype());
            contactViewHolder.vshop.setText(ci.getShopname());
            contactViewHolder.vcasestatus.setText(ci.getCasestatus());
        }

        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.custom_blacklist_view, viewGroup, false);

            return new ContactViewHolder(itemView);
        }

        public  class ContactViewHolder extends RecyclerView.ViewHolder {
            protected TextView vshop;
            protected TextView vlocation;
            protected TextView vdealername;
            protected TextView voffencetype;
            protected Button vcasestatus;

            public ContactViewHolder(View v) {
                super(v);
                vshop =  (TextView) v.findViewById(R.id.txtshopname);
                vlocation = (TextView)  v.findViewById(R.id.txtlocation);
                vdealername = (TextView)  v.findViewById(R.id.tvtdealername);
                voffencetype = (TextView)  v.findViewById(R.id.txtoffence);
                vcasestatus = (Button) v.findViewById(R.id.btncasestatus);
            }
        }


    }
}
