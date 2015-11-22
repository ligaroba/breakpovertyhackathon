package root.com.jiranimmicrocredit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import root.com.jiranimmicrocredit.BackgroudThread.BackGroundThread;
import root.com.jiranimmicrocredit.utils.Constants;
import root.com.jiranimmicrocredit.utils.Functions;
import root.com.jiranimmicrocredit.utils.GridAdapter;
import root.com.jiranimmicrocredit.utils.NetworkUtils;
import root.com.jiranimmicrocredit.utils.SetterGetters;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private GridAdapter mAdapter;
    SetterGetters setter=new SetterGetters();
    //OnItemClickListener mItemClickListener;
    Functions fn =new Functions();

    private RelativeLayout incomelay,loanlay,gurantorslay,reportslay;
    private int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        incomelay= (RelativeLayout) findViewById(R.id.top_income_layout);
        loanlay= (RelativeLayout) findViewById(R.id.top_loans_layout);
        gurantorslay= (RelativeLayout) findViewById(R.id.top_guarantors_layout);
        reportslay= (RelativeLayout) findViewById(R.id.top_reports_layout);


        incomelay.setOnClickListener(this);
        loanlay.setOnClickListener(this);
        gurantorslay.setOnClickListener(this);
        reportslay.setOnClickListener(this);


       /* mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns

        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
    GridAdapter.OnItemClickListener onItemClickListener = new GridAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(MainActivity.this, "Clicked " + position, Toast.LENGTH_SHORT).show();
            }
        };

        mAdapter = new GridAdapter(this);
        mAdapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(mAdapter);*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Toast.makeText(this,"position"+ setter.getGridPosition(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.account_balance) {
            // Handle the camera action
        } else if (id == R.id.documents) {

        //} else if (id == R.id.nav_slideshow) {

            Intent intent = new Intent(MainActivity.this ,LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.contact_us) {
            Intent intent = new Intent(MainActivity.this ,HelpActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_share) {



        } else if (id == R.id.nav_send) {

            Intent intent = new Intent(MainActivity.this ,BackUpActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.settings) {

            Intent intent = new Intent(MainActivity.this ,SettingsActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.top_income_layout:
                Intent income=new Intent(MainActivity.this,IncomeSource.class);
                startActivity(income);

                break;
            case R.id.top_loans_layout:
                userid=1;

                if(NetworkUtils.getConnectivityStatusString(this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(this).equalsIgnoreCase("mobile data Enabled")|| Constants.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||Constants.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {

                    new BackGroundThread(this, "loading", 0).execute(fn.getLoans());
                    new BackGroundThread(this, "loading", 0).execute(fn.getUserLoans(userid));
                    new BackGroundThread(this, "loading", 0).execute(fn.getMyLoans(userid));
                }else{
                    Toast.makeText(this, Constants.CONNECTION_FAILED, Toast.LENGTH_SHORT).show();
                }
               // new BackGroundThread(this,"loading",0).execute(fn.getUserLoans(userid));
                Intent loans=new Intent(MainActivity.this,Loans.class);
                startActivity(loans);
                break;
            case R.id.top_guarantors_layout:
                break;
            case R.id.top_reports_layout:
                break;
        }

    }
}
