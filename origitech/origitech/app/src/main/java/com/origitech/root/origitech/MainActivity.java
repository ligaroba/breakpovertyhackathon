package com.origitech.root.origitech;



import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.origitech.root.origitech.constants.ConstantParams;
import com.origitech.root.origitech.constants.SetterGetter;
import com.origitech.root.origitech.databases.Database;
import com.origitech.root.origitech.fragments.BackupFiles;
import com.origitech.root.origitech.fragments.CheckUserprofile;

import com.origitech.root.origitech.fragments.DashboardFragment;
import com.origitech.root.origitech.fragments.Report;
import com.origitech.root.origitech.fragments.ReportList;
import com.origitech.root.origitech.fragments.Userprofile;
import com.origitech.root.origitech.fragments.Verification;
import com.origitech.root.origitech.fragments.Verification.VerificationData;
import com.origitech.root.origitech.functions.Functions;
import com.origitech.root.origitech.network.BackgroundThread;

import com.origitech.root.origitech.network.FileTransfer;
import com.origitech.root.origitech.utils.NetworkUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CheckUserprofile.CheckUserProfileListener, BackupFiles.BackUpListener,VerificationData,Userprofile.UserProfileListener,Report.ReportData{


    private static final String TAG = "MAINACTIVITY";
    private static MainActivity ActivityContext;
    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    FragmentManager fm=getSupportFragmentManager();

    private BackupFiles backupFiles;

    SharedPreferences pref;

    SharedPreferences.Editor prefeditor;







    private static final String UPLOAD_VIDEO_NAME="video";
    private static final String UPLOAD_AUDIO_NAME="audio";
    private static final String UPLOAD_PICTURE_NAME="picture";

    String imagePath="";
    String videoPath="";
    String audioPath="";
    String cropedimagePath="";
    String errorMessage;
    Uri picUri;
    int resultcode;
    int imageRcode,vidaRcode,audioRcode;
    static String imageFilename="No Image";
    static  String videoFilename="No Video";
    static String audioFilename="No Audio";
    static String location;
    private Uri fileuri=null;
    private Uri imagefileuri,croppedimageuri;
    File fileobject;

    boolean audioDone=false;
    boolean videoDone=false;
    boolean imageDone=false;
    private static final int PIC_CROP = 2;
    private static final int MEDIA_TYPE_IMAGE = 4;
    private static final int MEDIA_TYPE_VIDEO=3;
    private static final int CAPTURE_VIDEO_ACTIVITY=200;
    private static final int CAPTURE_IMAGE_ACTIVITY=300;
    private static final int CAPTURE_IMAGE_CROP_ACTIVITY=350;
    private static final int CAPTURE_AUDIO_ACTIVITY=400;

    private static final int CAPTURE_IMAGE_KITKAT_ACTIVITY=360;
    private boolean isInternetPresent=true;

    Verification fragVerification;
    Userprofile userprofile;
    Report report;
    Blacklisted blacklist;
    ClaimList claimlist;
    int userid;
    DashboardFragment dashboard;

    Database db;
    private CheckUserprofile checkUserprofile;
    private ReportList reportlist;
    private BackupList backuplist;
    private BackupList backupList;
    private List<SetterGetter> profile;
    private SharedPreferences userPref;
    private String[] extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setupCollapsingToolbarLayout();
        setupToolbar();


        if(savedInstanceState==null && ConstantParams.CLAIMCODE.equalsIgnoreCase("")){
            fragVerification=new Verification();
            fm.beginTransaction().add(R.id.content,fragVerification).addToBackStack(null).commit();
        }


        db=new Database(this);
        if(db.getUserId()!=-1){


            profile=new ArrayList<SetterGetter>();
            profile=db.getProfile();

            TextView tvtuserprofile= (TextView) findViewById(R.id.tvtuserprofile);
            String email=profile.get(0).getEmail();
            if(email.equalsIgnoreCase("null")) {
                tvtuserprofile.setText("update your email");
            }else{

                tvtuserprofile.setText(email);
            }
        }

        //setupCollapsingToolbarLayout();
        //ConstantParams.CLAIMCODE="";
        ActivityContext=new MainActivity();

        // Initializing Toolbar and setting it as the actionbar

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation);
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                //Closing drawer on item click
                drawerLayout.closeDrawers();
                fragVerification=new Verification();
                userPref=getSharedPreferences(ConstantParams.PREF_USERID, Context.MODE_PRIVATE);
               SharedPreferences.Editor checkdata=userPref.edit();
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.home:
                        fragVerification=new Verification();
                        fm.beginTransaction().replace(R.id.content, fragVerification).addToBackStack(null).commit();


                        return true;

                    case R.id.profile:
                        if(db.getUserId()!=-1){
                            userid=db.getUserId();
                        userprofile=new Userprofile();


                            fm.beginTransaction().replace(R.id.content, userprofile).addToBackStack(null).commit();


                        }else{
                            checkUserprofile=new CheckUserprofile();
                            checkdata.putString(ConstantParams.GOTINTENT,ConstantParams.GOTOUSERPROFILE).commit();
                            String gointetnt= userPref.getString(ConstantParams.GOTINTENT, "none");
                             checkUserprofile.show(fm,"checkUserprofile");
                        }
                        return true;
                    case R.id.claims:
                        if(db.getUserId()!=-1){
                            userid=db.getUserId();


                           if(NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("mobile data Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {
                                new BackgroundThread(MainActivity.this,"claiming",0).execute(new Functions().getVerificationHistory(userid));
                           }else{

                              Toast.makeText(MainActivity.this,ConstantParams.REFRESH_FAILED,Toast.LENGTH_SHORT).show();;
                            }
                            Intent intentMain= new Intent(MainActivity.this, ClaimList.class);
                            startActivity(intentMain);


                        }else{
                            checkUserprofile=new CheckUserprofile();
                            checkdata.putString(ConstantParams.GOTINTENT,ConstantParams.GOTOCLAIMLIST).commit();
                            checkUserprofile.show(fm,"checkUserprofile");
                        }

                        return true;
                    case R.id.backup:
                        backupFiles=new BackupFiles();
                        if(db.getUserId()!=-1){
                            userid=db.getUserId();
                            backupFiles.show(fm, "backupFiles");
                        }else{
                            checkUserprofile=new CheckUserprofile();
                            checkdata.putString(ConstantParams.GOTINTENT,ConstantParams.GOTOBACKUP).commit();
                            checkUserprofile.show(fm,"checkUserprofile");

                        }

                        return true;
                    case R.id.backup_list:
                     //   backupList=new BackupList();
                       // Toast.makeText(MainActivity.this,"backup_list",Toast.LENGTH_SHORT).show();;
                        if(db.getUserId()!=-1){
                            userid=db.getUserId();
                             if(NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("mobile data Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {
                            new BackgroundThread(MainActivity.this,"",0).execute(new Functions().getBackuplist(userid));
                            }else{


                                 Intent intentMain= new Intent(MainActivity.this, BackupList.class);
                                 startActivity(intentMain);
                                // fm.beginTransaction().replace(R.id.content, backupList).addToBackStack(null).commit();
                                Toast.makeText(MainActivity.this,ConstantParams.REFRESH_FAILED,Toast.LENGTH_SHORT).show();;

                            }

                        }else{
                            checkUserprofile=new CheckUserprofile();
                            checkdata.putString(ConstantParams.GOTINTENT,ConstantParams.GOTOBACKUPLIST).commit();
                            checkUserprofile.show(fm,"checkUserprofile");

                        }

                        return true;
                    case R.id.blacklist:


                        //Toast.makeText(MainActivity.this,"blacklist",Toast.LENGTH_SHORT).show();;

                     if(NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("mobile data Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {
                            new BackgroundThread(MainActivity.this,"",0).execute(new Functions().getBlacklist());
                        }else{

                         Intent intentMain= new Intent(MainActivity.this, Blacklisted.class);
                         startActivity(intentMain);

                       //  fm.beginTransaction().replace(R.id.content, blacklist).addToBackStack(null).commit();
                         Toast.makeText(MainActivity.this, ConstantParams.REFRESH_FAILED, Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    case R.id.protected_brands:

                        return true;
                    case R.id.feedback:

                        return true;

                    case R.id.help:

                        return true;

                    default:

                        return true;
                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

   /* private void setupCollapsingToolbarLayout() {

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);
        if(collapsingToolbarLayout != null){
            //collapsingToolbarLayout.setCollapsedTitleTextColor(0xED1C24);
            //collapsingToolbarLayout.setExpandedTitleColor(0xED1C24);
        }
    }
*/
    private void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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



    public void picCrop(Uri pic){

        if(pic!=null) {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(pic, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            try {
                croppedimageuri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE,this);
                cropIntent.putExtra("return-data", true);

                //start the activity - we handle returning in onActivityResult
                cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, croppedimageuri);
                startActivityForResult(cropIntent, PIC_CROP);
            } catch (ActivityNotFoundException anfe) {
                //display an error message
                errorMessage = "Sorry your device doesnt support Croping";
                Toast toast = Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        }else{
            Toast.makeText(this,"No image to crop",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        fragVerification=new Verification();


        if (ConstantParams.CLAIMCODE.equalsIgnoreCase("Claimcode")) {

            if(db.getUserId()!=-1){
                Log.e("verifycode  result", ConstantParams.CLAIMCODE);
                checkUserprofile = new CheckUserprofile();
                checkUserprofile.show(fm, "checkUserprofile");
                ConstantParams.CLAIMCODE="";
            }else{

            }




        }else if (ConstantParams.CLAIMCODE.equalsIgnoreCase("saveClaim")){

            final SharedPreferences userPref = getSharedPreferences(ConstantParams.PREF_USERID, Context.MODE_PRIVATE);
            if(db.getUserId()!=-1){
                userid = db.getUserId();
                int iddatafile=userPref.getInt(ConstantParams.DATAFILEID,0);
                int idproduct=userPref.getInt(ConstantParams.PRODUCTID,0);

                Log.e(TAG,"userid " + userid + "  idprod " + idproduct + " iddatafile" + iddatafile);

                if(iddatafile>0&&idproduct>0) {
             if(NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("mobile data Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {
                    new BackgroundThread(this, "saving code", 0).execute(new Functions().codeClaim(userid, idproduct, iddatafile));
             }else{
                    Toast.makeText(this,ConstantParams.CONNECTION_FAILED,Toast.LENGTH_SHORT).show();;
                }
                    ConstantParams.CLAIMCODE = "";
                }else{
                    Toast.makeText(this,"Failed to associate your code",Toast.LENGTH_LONG).show();;
                }

            }else{

                checkUserprofile = new CheckUserprofile();
                checkUserprofile.show(fm, "checkUserprofile");
                ConstantParams.CLAIMCODE="";

            }





        }else if (ConstantParams.CLAIMCODE.equalsIgnoreCase("Report")){
            report=new Report();
            if(db.getUserId()!=-1){
                userid = db.getUserId();
                report.show(fm, "report");
                ConstantParams.CLAIMCODE="";

            }else{
                checkUserprofile = new CheckUserprofile();
                checkUserprofile.show(fm, "checkUserprofile");
                ConstantParams.CLAIMCODE="";

            }



        }else if (ConstantParams.CLAIMCODE.equalsIgnoreCase("ClaimList")){

            claimlist=new ClaimList();

            report=new Report();
            if(db.getUserId()!=-1){
               /* userid = db.getUserId();
                fm.beginTransaction().replace(R.id.content, claimlist).addToBackStack(null).commit();
                ConstantParams.CLAIMCODE="";*/

            }else{
                checkUserprofile = new CheckUserprofile();
                checkUserprofile.show(fm, "checkUserprofile");
                ConstantParams.CLAIMCODE="";

            }

        }else if(ConstantParams.CLAIMCODE.equalsIgnoreCase("viewreport")){

            if(db.getUserId()!=-1){
                userid = db.getUserId();
                reportlist=new ReportList();
                fm.beginTransaction().replace(R.id.content, reportlist).addToBackStack(null).commit();
                ConstantParams.CLAIMCODE="";

            }else{
                checkUserprofile = new CheckUserprofile();
                checkUserprofile.show(fm, "checkUserprofile");
                ConstantParams.CLAIMCODE="";
            }


        }
        else if(ConstantParams.CLAIMCODE.equalsIgnoreCase(ConstantParams.UPLOAD_BACKUP)){

            if(db.getUserId()!=-1){
                userid = db.getUserId();
               /* backuplist=new BackupList();
                fm.beginTransaction().replace(R.id.content, backuplist).addToBackStack(null).commit();*/
                ConstantParams.CLAIMCODE="";

            }else{
                checkUserprofile = new CheckUserprofile();
                checkUserprofile.show(fm, "checkUserprofile");
                ConstantParams.CLAIMCODE="";
            }


        }
        else if(ConstantParams.CLAIMCODE.equalsIgnoreCase(ConstantParams.GOTOBACKUPLIST)){

            if(db.getUserId()!=-1){
                userid = db.getUserId();
              /*  backuplist=new BackupList();
                fm.beginTransaction().replace(R.id.content, backuplist).addToBackStack(null).commit();
                ConstantParams.CLAIMCODE="";*/

            }else{
                checkUserprofile = new CheckUserprofile();
                checkUserprofile.show(fm, "checkUserprofile");
                ConstantParams.CLAIMCODE="";
            }


        }


        // Intent service =new Intent(MainActivity.this, Backgroundservice.class);
        // service.putExtra(ConstantParams.ACTION,ConstantParams.)


    }
    /*@Override
    public void onBackPressed() {

        int count = fm.getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            fm.popBackStack();
        }

    }*/

    @Override
    public void ButtonPressed(int id,int crop) {
        // TODO Auto-generated method stub
        final int cropping=crop;
        switch (id) {
            case 1:
                final CharSequence[] list={"Choose an Audio file","Record Audio", "Cancel"};
                //  alertintent.showAlertDialog("Audio",list);

                try{
                    Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                    intent.setType("audio/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Audio "),CAPTURE_AUDIO_ACTIVITY);
                } catch(ActivityNotFoundException anfe){
                    //display an error message
                    errorMessage = "Sorry your device doesnt support audio capturing";
                    Toast toast = Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT);
                    toast.show();
                }

                break;
            case 2:
                final CharSequence[] options={"Choose from Gallery","Take a Photo", "Cancel"};

                AlertDialog.Builder imgbuilder = new AlertDialog.Builder(MainActivity.this);

                imgbuilder.setTitle("Picture");

                imgbuilder.setItems(options, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int item) {



                        if (options[item].equals("Take a Photo"))

                        { if(cropping==0) {

                            try {
                                Intent takephoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                imagefileuri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE,MainActivity.this);
                                takephoto.putExtra(MediaStore.EXTRA_OUTPUT, imagefileuri);
                                startActivityForResult(takephoto, CAPTURE_IMAGE_ACTIVITY);

                            } catch (ActivityNotFoundException anfe) {
                                //display an error message
                                errorMessage = "Sorry your device doesnt support image capturing";
                                Toast toast = Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }else{
                            try {

                                imagefileuri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE,MainActivity.this);
                                if(imagefileuri!=null) {
                                    Intent takephoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    //Toast.makeText(MainActivity.this, "imagefileuri" + imagefileuri.toString(), Toast.LENGTH_SHORT).show();
                                    takephoto.putExtra(MediaStore.EXTRA_OUTPUT, imagefileuri);
                                    startActivityForResult(takephoto, CAPTURE_IMAGE_CROP_ACTIVITY);
                                }

                            } catch (ActivityNotFoundException anfe) {
                                //display an error message
                                errorMessage = "Sorry your device doesnt support image capturing";
                                Toast toast = Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        }



                        } else if (options[item].equals("Choose from Gallery")){

                            if(cropping==0) {
                                try {
                                    Intent photopick;
                                  //  if(Build.VERSION.SDK_INT<19) {
                                        photopick = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(photopick, CAPTURE_IMAGE_ACTIVITY);
                                  /*  }else{
                                        Intent intent = new Intent();
                                        intent.setType("image/*");
                                        intent.setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                                                CAPTURE_IMAGE_KITKAT_ACTIVITY);
                                       /* photopick=new Intent(Intent.ACTION_PICK);
                                        photopick.addCategory(Intent.CATEGORY_OPENABLE);
                                        photopick.setType("images/*");
                                        startActivityForResult(photopick,CAPTURE_IMAGE_KITKAT_ACTIVITY);
                                    }*/





                                } catch (ActivityNotFoundException anfe) {
                                    //display an error message
                                    errorMessage = "Sorry your device doesnt support Image capturing";
                                    Toast toast = Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }else{
                                try {
                                    Intent photopick;


                                    photopick= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(photopick, CAPTURE_IMAGE_CROP_ACTIVITY);

                                } catch (ActivityNotFoundException anfe) {
                                    //display an error message
                                    errorMessage = "Sorry your device doesnt support Image capturing";
                                    Toast toast = Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                            }


                        }
                        else if (options[item].equals("Cancel")) {


                            dialog.dismiss();

                        }



                    }

                });

                imgbuilder.show();


                break;
            case 3:
                final CharSequence[] option={"Choose a Video","Record Video", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Video");

                builder.setItems(option, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int item) {



                        if (option[item].equals("Record Video"))

                        {

                            try {
                                Intent intentvideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                                fileuri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO,MainActivity.this);
                                intentvideo.putExtra(MediaStore.EXTRA_OUTPUT, fileuri);
                                intentvideo.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                                startActivityForResult(intentvideo, CAPTURE_VIDEO_ACTIVITY);

                            } catch(ActivityNotFoundException anfe){
                                //display an error message
                                errorMessage = "Sorry your device doesnt support video capturing";
                                Toast toast = Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        } else if (option[item].equals("Choose a Video")){

                            try{  Intent videopick =new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(videopick, CAPTURE_VIDEO_ACTIVITY);
                            } catch(ActivityNotFoundException anfe){
                                //display an error message
                                errorMessage = "Sorry your device doesnt support video capturing";
                                Toast toast = Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT);
                                toast.show();
                            }


                        }else if (option[item].equals("Cancel")) {


                            dialog.dismiss();

                        }



                    }

                });

                builder.show();


                break;

            default:
                break;
        }
    }
    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type,Context context){
        if(getOutputMediaFile(type,context)==null){
            // Toast.makeText(context, "No directory created", Toast.LENGTH_SHORT).show();
            return null;
        }else{
            return Uri.fromFile(getOutputMediaFile(type,context));
        }

    }
    /** Create a File for saving an image or video */
    private  static File getOutputMediaFile(int type ,Context context){

        //checking mounting of sd card
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/OrigiCheck");
        // Toast.makeText(context, mediaStorageDir.toString(), Toast.LENGTH_SHORT).show();


        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdirs()) {
                Toast.makeText(context, "Failed to create Directory Please Mount SD Card or Free some space   " , Toast.LENGTH_SHORT).show();
            }

            return null;
        }
        // creating a media file

        Date date = new Date();
        String timestamp = new SimpleDateFormat("yyyymmdd_HHmmss").format(date.getTime());

        File mediafile;
        if(type==MEDIA_TYPE_IMAGE){

            mediafile =new  File(mediaStorageDir.getPath() + File.separator + "ORIGICHECK_PIC_EVIDENCE_"+ timestamp + ".jpg" );
            imageFilename = "ORIGICHECK"+ timestamp + ".jpg";

        }
        else if(type==MEDIA_TYPE_VIDEO){

            mediafile =new  File(mediaStorageDir.getPath() + File.separator + "ORIGICHECK_VID_EVIDENCE_"+ timestamp + ".mp4" );
            videoFilename="ORIGICHECK"+ timestamp + ".mp4";
        } else{

            return null;
        }
        return mediafile;

    }
    public String getRealPathFromURI(Uri contentUri) {
        String [] proj={MediaStore.Images.Media.DATA};
        android.database.Cursor cursor = getContentResolver().query(contentUri,
                proj,     // Which columns to return
                null,     // WHERE clause; which rows to return (all rows)
                null,     // WHERE clause selection arguments (none)
                null);     // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }







    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        pref=getSharedPreferences(ConstantParams.PREF_DATA,MODE_PRIVATE);
        prefeditor =pref.edit();

        if(requestCode ==CAPTURE_IMAGE_ACTIVITY){
            if(data==null) {
                if(imagefileuri==null){
                    Toast.makeText(this,"Application closed",Toast.LENGTH_SHORT).show();
                    imagePath ="";
                }else {

                    imagePath = imagefileuri.getPath();
                    prefeditor.putString(ConstantParams.IMAGEURI,imagefileuri.toString()).commit();



                }
            }

            else{


                //  String imagePath2=data.getData().getPath();
                ConstantParams.IMAGE=data.getData();
                prefeditor.putString(ConstantParams.IMAGEURI,data.getData().toString()).commit();
                imagePath= getRealPathFromURI(data.getData());
                //   imageFilename=new File(imagePath).getName();







            }
        }/*else if(requestCode==CAPTURE_IMAGE_KITKAT_ACTIVITY){
            imagefileuri=data.getData();
            grantUriPermission(getPackageName(), imagefileuri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            final int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            this.getContentResolver().takePersistableUriPermission(imagefileuri, takeFlags);
            try {
               // ;
                prefeditor.putString(ConstantParams.IMAGEURI, getImagePath(imagefileuri).toString()).commit();


            } catch (IOException e) {
                e.printStackTrace();
            }
            // grantUriPermission(getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //noinspection ResourceType,ResourceType,ResourceType
//            this.getContentResolver().takePersistableUriPermission(imagefileuri, takeFlags);


            ;

        }*/

        else
        if(requestCode ==CAPTURE_IMAGE_CROP_ACTIVITY){
            if(data==null) {
                if(imagefileuri==null) {
                    Toast.makeText(this, "Application closed", Toast.LENGTH_SHORT).show();
                    imagePath = "";
                }else{
                    cropedimagePath=imagefileuri.getPath();
                    picCrop(imagefileuri);
                }
            }else{

                picCrop(data.getData());
                //  String imagePath2=data.getData().getPath();
                // imagePath= getRealPathFromURI(data.getData());
                // imageFilename=new File(imagePath).getName();




            }
        }else  if(requestCode ==PIC_CROP){
            if(resultCode==RESULT_OK){
                if(data==null) {

                    if( croppedimageuri==null){
                        Toast.makeText(this,"Application closed",Toast.LENGTH_SHORT).show();
                        cropedimagePath ="";
                    }else{

                    }
                }else{

                    //  cropedimagePath= data.getData().getPath();
                    //  imageFilename=new File(cropedimagePath).getName();

                    Toast.makeText(this,"Cropped image name is :  "+ imageFilename + "   path  " + cropedimagePath.toString(),Toast.LENGTH_LONG).show();

                }

            }else {
                Toast.makeText(this, "Application Closed", Toast.LENGTH_LONG).show();
            }


        }

        else if(requestCode ==CAPTURE_AUDIO_ACTIVITY){
            if(resultCode==RESULT_OK){

                audioPath= getRealPathFromURI(data.getData());
                audioFilename=new File(audioPath).getName();

            }else if (resultCode==RESULT_CANCELED){

                Toast.makeText(this, "User cancelled the audio capture.",
                        Toast.LENGTH_LONG).show();
                audioPath="";
            }




        }
        else  if(requestCode ==CAPTURE_VIDEO_ACTIVITY){

            if(resultCode == RESULT_OK) {

                // Video captured and saved to fileUri specified in the Intent
                if(data!=null) {

                    videoPath=getRealPathFromURI(data.getData());
                    videoFilename=new File(videoPath).getName();

                } else {
                    if(fileuri==null) {
                        Toast.makeText(this, "Application closed", Toast.LENGTH_LONG).show();
                        videoPath="";
                    }else{
                        videoPath=fileuri.getPath();
                        //  videoFilename=new File(videoPath).getName();
                    }
                }

            } else if (resultCode == RESULT_CANCELED) {

                // User cancelled the video capture
                Toast.makeText(this, "User cancelled the video capture.",
                        Toast.LENGTH_LONG).show();

            } else {

                // Video capture failed, advise user
                Toast.makeText(this, "Video capture failed.",
                        Toast.LENGTH_LONG).show();
            }


        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private String getImagePath(Uri uri) {

        Cursor cursor=getContentResolver().query(uri,null,null,null,null,null);
        cursor.moveToFirst();
        String document_id=cursor.getString(0);
        document_id=document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor =getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,MediaStore.Images.Media._ID + "=? ", new String[] {document_id},null);
        cursor.moveToFirst();
        String path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        Toast.makeText(this,path,Toast.LENGTH_SHORT).show();
        cursor.close();
        return path;
    }


    @Override
    public void DataPassed(String backuptype){


        if(NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("mobile data Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {

            extras=new String[] {backuptype};
            new FileTransfer("backuppersonal", "Uploading files...","tbl_backup",extras, this).execute(imagePath).getStatus();


       }else {
            Toast.makeText(this, ConstantParams.CONNECTION_FAILED, Toast.LENGTH_SHORT).show();

        }



}

    @Override
    public void onVerifySenData(String code) {


      if(NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("mobile data Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {
            new BackgroundThread(this,"verifying code",0).execute(new Functions().verifyCode(code));


      }else{
            Toast.makeText(this,ConstantParams.CONNECTION_FAILED,Toast.LENGTH_SHORT).show();;
        }
    }


    @Override
    public void profiledata(String email, String phone, String sname, String fname) {
        if(db.getUserId()!=-1){
            userid=db.getUserId();

            if(NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("mobile data Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {

                extras=new String[] { fname,sname,phone,email};
                new FileTransfer("users", "Uploading files...","tbl_users",extras, this).execute(imagePath).getStatus();


            }else {
                Toast.makeText(this, ConstantParams.CONNECTION_FAILED, Toast.LENGTH_SHORT).show();
                ;
            }



        }else {
            checkUserprofile=new CheckUserprofile();
            checkUserprofile.show(fm, "checkUserprofile");

        }

    }

    @Override
    public void onReportSenData(String code) {

    }

    @Override
    public void onReportDataSend(String proname, String model, String dealer,String shop,Uri uri) {

         if(NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("mobile data Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {
           extras=new String[] {proname,model,location,dealer,shop};
           new FileTransfer("reportevidence", "Uploading files...","tbl_reports",extras, this).execute(imagePath).getStatus();


        }else {
            Toast.makeText(this, ConstantParams.CONNECTION_FAILED, Toast.LENGTH_SHORT).show();

        }




    }

   /* @Override
    public void fetchBlacklistAsych(int refresh) {
        switch (refresh){
            case 1:

          /* if(NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("mobile data Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {
                    new BackgroundThread(MainActivity.this,"",0).execute(new Functions().getBlacklist());
              }else{
                    Toast.makeText(this,ConstantParams.CONNECTION_FAILED,Toast.LENGTH_SHORT).show();;
           }

                break;
            case 2:

               /* if(NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("mobile data Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {
                    new BackgroundThread(MainActivity.this,"",0).execute(new Functions().getBackuplist(userid));
                }else{
                    Toast.makeText(this,ConstantParams.CONNECTION_FAILED,Toast.LENGTH_SHORT).show();;
                }*/
/*
                break;
        }


    }*/
    private Uri getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        //return image;
       return getImageUri(this, image);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
   /* @Override
    public void onClaimListSenData(String code) {

    }

   /* @Override
    public void refreshClaim(int refresh) {

        switch (refresh){
            case 1:
                if(db.getUserId()!=-1){
                    userid=db.getUserId();

                    if(NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("mobile data Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {
                        new BackgroundThread(MainActivity.this,"",0).execute(new Functions().getVerificationHistory(userid));
                    }else{
                        Toast.makeText(this,ConstantParams.CONNECTION_FAILED,Toast.LENGTH_SHORT).show();;
                    }


                   //claimlist=new ClaimList();
                   //fm.beginTransaction().replace(R.id.content, claimlist).addToBackStack(null).commit();
                }else{
                    checkUserprofile=new CheckUserprofile();
                    checkUserprofile.show(fm,"checkUserprofile");
                }
                break;
        }

    }*/

    @Override
    public void checkprofiledata(String intentAction,String phone,String password) {




       if(NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(MainActivity.this).equalsIgnoreCase("mobile data Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||ConstantParams.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {
            new BackgroundThread(MainActivity.this,intentAction,1).execute(new Functions().checkProfile(phone,password));
       }else{
            Toast.makeText(this,ConstantParams.CONNECTION_FAILED,Toast.LENGTH_SHORT).show();;
        }

    }

   /* @Override
    public void OnselectedItem(int id, String name) {

    }

    @Override
    public void ListviewItemClicked(int id) {

    }*/
}