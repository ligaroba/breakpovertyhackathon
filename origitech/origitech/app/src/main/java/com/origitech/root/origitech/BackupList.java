package com.origitech.root.origitech;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.origitech.root.origitech.R;
import com.origitech.root.origitech.constants.ConstantParams;
import com.origitech.root.origitech.constants.SetterGetter;
import com.origitech.root.origitech.databases.Database;
import com.origitech.root.origitech.fragments.CheckUserprofile;
import com.origitech.root.origitech.functions.Functions;
import com.origitech.root.origitech.network.BackgroundThread;
import com.origitech.root.origitech.utils.NetworkUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/29/15.
 */
public class BackupList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recycler;
    Database db;
    List<SetterGetter> backeupdata=new ArrayList<SetterGetter>();
    SwipeRefreshLayout swipeLayout;
    public String TAG="BackupList";
    private CustomAdapter adapter;
    TextView textEmptyview;
    int position;

View view;

    //instantiating interfaces
    private OnItemSelected listener;
    private int userid;


    //interfaces
    public interface OnItemSelected {
        public void OnselectedItem(int id, String name);
        public void ListviewItemClicked(int id);
        public void fetchBlacklistAsych(int refresh);
    }



    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backuplist);
        swipeLayout=(SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // TODO Auto-generated method stub

        recycler =(RecyclerView) findViewById(R.id.rcybackupList);


        db = new Database(this);

        backeupdata = db.getBackup();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Backup list");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Log.e(TAG, "" + personData.size() + "data" + personData.toArray());

      //  ArrayAdapter<SetterGetter> adapter = new CustomAdapter(getActivity(),R.layout.custom_backup_listlayout,backeupdata);

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);




        db=new Database(this);
        recycler.setHasFixedSize(true);
        LinearLayoutManager llmanager=new LinearLayoutManager(this);
        llmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(llmanager);
        adapter= new CustomAdapter(backeupdata);
        recycler.setAdapter(adapter);

        adapter.notifyDataSetChanged();

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
            if(NetworkUtils.getConnectivityStatusString(this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(this).equalsIgnoreCase("mobile data Enabled")|| ConstantParams.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {
            new BackgroundThread(this,"",0).execute(new Functions().getBackuplist(userid));
        }else{
            Toast.makeText(this, ConstantParams.CONNECTION_FAILED, Toast.LENGTH_SHORT).show();;
        }

            adapter =new CustomAdapter(db.getBackup());
        recycler.setAdapter(adapter);
        swipeLayout.setRefreshing(false);

        }else {

        }
    }



    class ImageTask extends AsyncTask<CustomAdapter.ContactViewHolder, Void,  CustomAdapter.ContactViewHolder> {

        private static final int IO_BUFFER_SIZE = 4 * 1024;


        @Override
        protected  CustomAdapter.ContactViewHolder  doInBackground(CustomAdapter.ContactViewHolder... viholder) {
            InputStream iStream = null;
            CustomAdapter.ContactViewHolder viewHolder=viholder[0];
            String imgUrl = viewHolder.url;
            //(String) hm[0].get("image_path");
            position =viewHolder.holderpos;
            // (Integer) hm[0].get("position");

            Log.e(TAG,"viewHolder.holderpos" + viewHolder.holderpos);


            URL rURL = null;
            try {
                rURL = new URL(imgUrl);


                URLConnection connection = rURL.openConnection();
                connection.setConnectTimeout(10240);
                // BufferedReader inputStream = new BufferedReader(new InputStreamReader(rURL.openStream()));
                InputStream inputStream = new BufferedInputStream(rURL.openStream(), 10240);
                Log.e(TAG,inputStream.toString() + " from this url:" + rURL);
                File cacheDir = new Functions().getDataFolder(BackupList.this);
                File cacheFile = new File(cacheDir, "back_" + position + ".png");



                FileOutputStream outputStream = new FileOutputStream(cacheFile);


                byte[] byt = new byte[2048];
                int length;

                while ((length=inputStream.read(byt))!=-1){

                    outputStream.write(byt,0,length);
                }
                outputStream.flush();
                outputStream.close();
                InputStream fileInputStream = new FileInputStream(cacheFile);

                new Functions().decodeSampledBitmapFromStream((FileInputStream) fileInputStream, 100, 100);



                viewHolder.path=Uri.fromFile(cacheFile);


                fileInputStream.close();



                // Returning the HashMap object containing the image path and position
                Log.e(TAG, cacheFile.getPath().toString());
                return viewHolder;


            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e(TAG,e.toString());
            } catch (IOException oi) {
                oi.printStackTrace();
                Log.e(TAG, oi.toString());
            }


            return null;


        }


        @Override
        protected void onPostExecute( CustomAdapter.ContactViewHolder  result) {

            if(result!=null) {



                // Getting adapter of the listview
              RecyclerView.Adapter adapters = recycler.getAdapter();

                // Getting the hashmap object at the specified position of the listview
                SetterGetter Cdata = new SetterGetter();
                Cdata.setPath(result.path);
                result.imageView.setImageURI(Cdata.getPath());
                adapters.notifyItemChanged(position);
               // adapters.notifyDataSetChanged();

            }else{
                //  Toast.makeText(getActivity(), ConstantsClass.INTERNET_MSG,Toast.LENGTH_LONG).show();
            }
        }
    }
    private static class ViewHolder {
        int position;
        ImageView imageView;
        String url;
        Uri path;
        int id;
        TextView tvdes;
        TextView tvname;

    }





        public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ContactViewHolder> {


            private List<SetterGetter> backeupdata ;
            public CustomAdapter(List<SetterGetter> backeupdata) {

                this.backeupdata = backeupdata;
            }

            @Override
            public int getItemCount() {
                return backeupdata.size();
            }


            @Override
            public long getItemId(int position) {

                return position;
            }


            @Override
            public void onBindViewHolder(ContactViewHolder contactViewHolder, int position) {
                  int   holderpos =contactViewHolder.getAdapterPosition();
                SetterGetter ci = backeupdata.get(position);
                contactViewHolder.vbackuptype.setText(ci.getBacktype());
                contactViewHolder.vfilename.setText(ci.getAvatar());
                contactViewHolder.vdate.setText(ci.getTimeAdded());



                contactViewHolder.url = "http://www.origicheck.com/includes/uploads/backups/personal/" + backeupdata.get(position).getAvatar();

                if(holderpos!=RecyclerView.NO_POSITION) {
               Log.e(TAG, contactViewHolder.url);
                    contactViewHolder.holderpos = holderpos;

                }
                contactViewHolder.imageView.setOnClickListener(new registerClickcallback(contactViewHolder));



            }

            @Override
            public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.
                        from(viewGroup.getContext()).
                        inflate(R.layout.custom_backup_listlayout, viewGroup, false);

                return new ContactViewHolder(itemView);
            }

            public  class ContactViewHolder extends RecyclerView.ViewHolder {
                protected TextView vbackuptype;
                protected TextView vfilename;
                protected TextView vdate;
                protected  ImageView imageView;
                int holderpos;
                String url;
                Uri path;
                int id;


                public ContactViewHolder(View v) {
                    super(v);
                    vbackuptype =  (TextView) v.findViewById(R.id.txtbackuptype);
                    vfilename = (TextView)  v.findViewById(R.id.txtfilename);
                    vdate = (TextView)  v.findViewById(R.id.txtdate);
                    imageView = (ImageView) v.findViewById(R.id.imagedownload);

                }
            }


        }

    private class  registerClickcallback implements View.OnClickListener{
        int mposition;
        int id;
        Uri imag;
        CustomAdapter.ContactViewHolder viewHolder;
        public  registerClickcallback(CustomAdapter.ContactViewHolder viewHolder){

            this.viewHolder=viewHolder;
            this.mposition=viewHolder.holderpos;
            id=viewHolder.id;
            imag=viewHolder.path;
        }

        @Override
        public void onClick(View view) {
            SetterGetter itemclicked = backeupdata.get(mposition) ;
            listener.ListviewItemClicked(id);

            ImageTask imageLoaderTask = new ImageTask();
            Log.e(TAG, viewHolder.url);
            //  String full_url = "http://myroad.co.ke/mysecurity/uploads/persons/wanted/" + imgUrl;
            imageLoaderTask.execute(viewHolder);

        }




    }
    }

