package com.origitech.root.origitech.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.origitech.root.origitech.R;
import com.origitech.root.origitech.constants.ConstantParams;
import com.origitech.root.origitech.constants.SetterGetter;
import com.origitech.root.origitech.databases.Database;
import com.origitech.root.origitech.functions.Functions;
import com.origitech.root.origitech.provider.DBContract;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/7/15.
 */
public class Userprofile extends Fragment implements View.OnClickListener {

    SharedPreferences prefFrag;
    private UserProfileListener listener;
    private View view;


    private Button btncreateprof;
    private EditText editemail, editphone, editfname, editsname;
    private TextView tvtemail, tvtphone, tvttfname, tvtsname;
    private ViewSwitcher vssecondname, vsfirstname, vsemail;
    private int position;

    String confirmed = "";
    Database db;
    private ImageView imagviewavatar;

    Functions funs = new Functions();

    Uri uri = null;
    private List<SetterGetter> profile;
    private String TAG="UserProfile";


    public interface UserProfileListener {

        public void ButtonPressed(int id, int crop);
        public void profiledata(String email, String phone, String sname, String fname);


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (UserProfileListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AuthenticationDialogListener");

        }


    }


  /*  @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.userprofile, container, false);


        imagviewavatar = (ImageView) view.findViewById(R.id.imgeavatar);
        btncreateprof = (Button) view.findViewById(R.id.btnCreate);
        tvtemail = (TextView) view.findViewById(R.id.tvtemail);
        tvtsname = (TextView) view.findViewById(R.id.tvtsecondnamename);
        tvttfname = (TextView) view.findViewById(R.id.tvtfirstname);
        tvtphone = (TextView) view.findViewById(R.id.editphonenumber);
        vssecondname = (ViewSwitcher) view.findViewById(R.id.switcher_secondname);
        vsfirstname = (ViewSwitcher) view.findViewById(R.id.switcher_firstname);
        vsemail = (ViewSwitcher) view.findViewById(R.id.switcher_email);

        editsname = (EditText) view.findViewById(R.id.editsecondnamename);
        editfname = (EditText) view.findViewById(R.id.editfristname);
        editemail = (EditText) view.findViewById(R.id.editemail);
        imagviewavatar.setOnClickListener(this);

      /* final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        getActivity().setSupportActionBar(toolbar);
        getActivity().getSupportActionBar().setTitle("Update Profile");
        getActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        editfname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionid, KeyEvent event) {

                if (actionid == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    //   if(!event.isShiftPressed()){
                    //  ConfirmDialog();
                    //  if( ConfirmDialog().equalsIgnoreCase("save")){
                    tvttfname.setText(funs.getText(editfname));

                    TextViewSwitcher(-1);

                    /*  }else{
                          TextViewSwitcher(-2);
*/
                    return true;
                    //  }
                } else {
                    TextViewSwitcher(-1);
                    return false;

                }


            }


        });
        editsname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionid, KeyEvent event) {
                if (actionid == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    //   if(!event.isShiftPressed()){
                    //  ConfirmDialog();
                    //  if( ConfirmDialog().equalsIgnoreCase("save")){
                    tvtsname.setText(funs.getText(editsname));


                    TextViewSwitcher(-2);

                    /*  }else{
                          TextViewSwitcher(-2);
*/
                    return true;
                    //  }
                } else {
                    TextViewSwitcher(-2);
                    return false;

                }
            }
        });
        editemail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionid, KeyEvent event) {
                if (actionid == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    //   if(!event.isShiftPressed()){
                    //  ConfirmDialog();
                    //  if( ConfirmDialog().equalsIgnoreCase("save")){
                    tvtemail.setText(funs.getText(editemail));

                    TextViewSwitcher(-3);

                    /*  }else{
                          TextViewSwitcher(-2);
*/
                    return true;
                    //  }
                } else {
                    TextViewSwitcher(-3);
                    return false;

                }
            }
        });

        tvtsname.setOnClickListener(this);
        tvtemail.setOnClickListener(this);
        tvttfname.setOnClickListener(this);


        //tvttfname.setO


        db = new Database(getActivity());
        profile = new ArrayList<SetterGetter>();
        profile = db.getProfile();


        if (profile.size() > 0) {

            //  Log.e("Userprofile", cursor.getString(cursor.getColumnIndex(DBContract.Profile.KEY_FIRSTNAME)));
            String fname = profile.get(0).getFirstName();
            String sname = profile.get(0).getSecondName();
            String email = profile.get(0).getEmail();
            String phone = profile.get(0).getMobile();

            if (fname.equalsIgnoreCase("null")) {
                fname = "Click To update";
                tvttfname.setHint(fname);
            } else {
                tvttfname.setText(fname);
                editfname.setText(fname);
            }
            if (sname.equalsIgnoreCase("null")) {
                sname = "Click to update";
                tvtsname.setHint(sname);

            } else {
                tvtsname.setText(sname);
                editsname.setText(sname);
            }
            if (email.equalsIgnoreCase("null")) {
                email = "username@host.com";
                tvtemail.setHint(email);
            } else {
                tvtemail.setText(email);
                editemail.setText(email);
            }
            if (phone.equalsIgnoreCase("null")) {
                phone = "Enter Phone Number";
                tvtphone.setHint(phone);
            } else {
                tvtphone.setText(phone);
            }


        }
        imagviewavatar.setOnClickListener(this);
        btncreateprof.setOnClickListener(this);

        imagviewavatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_history));
        return view;

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.imgeavatar:

                listener.ButtonPressed(2, 0);

                break;

            case R.id.btnCreate:

                String fname = tvttfname.getText().toString();
                String email = tvtemail.getText().toString();
                String sname = tvtsname.getText().toString();
                String phone = tvtphone.getText().toString();

                listener.profiledata(email, phone, sname, fname);
                break;

            case R.id.tvtsecondnamename:
                TextViewSwitcher(2);
                break;
            case R.id.tvtemail:
                TextViewSwitcher(3);
                break;
            case R.id.tvtfirstname:
                TextViewSwitcher(1);
                break;

        }

    }


    private void TextViewSwitcher(int switcher) {

        switch (switcher) {

            case 1:
                vsfirstname.showNext();
                break;
            case -1:
                vsfirstname.showPrevious();
                break;
            case 2:
                vssecondname.showNext();
                break;
            case -2:
                vssecondname.showPrevious();

                break;
            case 3:

                vsemail.showNext();

                break;
            case -3:

                vsemail.showPrevious();

                break;
        }
    }

    public Bitmap uriBitmap(Uri imageUri) {
        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap != null) {
            return bitmap;
        } else {
            return null;
        }
    }





    public String ConfirmDialog() {

        final android.support.v7.app.AlertDialog.Builder bbbuilder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        bbbuilder.setTitle("Confrim Changes");
        bbbuilder.setMessage(Html.fromHtml("<b> Do you want to save the Changes made? </b><b><i>  "));
        bbbuilder.setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                confirmed = "save";

                dialogInterface.cancel();


            }

        });
        bbbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                confirmed = "cancel";
                dialogInterface.cancel();

            }
        });
        bbbuilder.show();
        Toast.makeText(getActivity(), "Confirm " + confirmed, Toast.LENGTH_SHORT).show();
        return confirmed;
    }


    @Override
    public void onResume() {
        super.onResume();
        prefFrag = getActivity().getSharedPreferences(ConstantParams.PREF_DATA, Context.MODE_PRIVATE);
        String imageurl = prefFrag.getString(ConstantParams.IMAGEURI, "none");
        //  Toast.makeText(getActivity(),"image url "+ imageurl, Toast.LENGTH_LONG).show();
        if (imageurl != "none") {
            uri = Uri.parse(imageurl);
            imagviewavatar.setImageURI(uri);

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
                File cacheDir = new Functions().getDataFolder(getActivity());
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
              //  RecyclerView.Adapter adapters = recycler.getAdapter();

                // Getting the hashmap object at the specified position of the listview
                SetterGetter Cdata = new SetterGetter();
                Cdata.setPath(result.path);
                result.imageView.setImageURI(Cdata.getPath());
                //adapters.notifyItemChanged(position);
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
         //   contactViewHolder.vbackuptype.setText(ci.getBacktype());
          //  contactViewHolder.vfilename.setText(ci.getAvatar());
          //  contactViewHolder.vdate.setText(ci.getTimeAdded());



            contactViewHolder.url = "http://www.origicheck.com/includes/uploads/backups/users/" + backeupdata.get(position).getAvatar();

            if(holderpos!=RecyclerView.NO_POSITION) {              //  Log.e(TAG, contactViewHolder.url);
                contactViewHolder.holderpos = holderpos;

            }
            new ImageTask().execute(contactViewHolder);




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


}
