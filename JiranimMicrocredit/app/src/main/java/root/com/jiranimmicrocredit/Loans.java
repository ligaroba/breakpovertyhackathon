package root.com.jiranimmicrocredit;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import root.com.jiranimmicrocredit.fragments.Employment;
import root.com.jiranimmicrocredit.fragments.LoanApplication;
import root.com.jiranimmicrocredit.fragments.MyApplication;
import root.com.jiranimmicrocredit.fragments.OtherOccupations;
import root.com.jiranimmicrocredit.fragments.Property;
import root.com.jiranimmicrocredit.network.FileTransfer;
import root.com.jiranimmicrocredit.utils.Constants;
import root.com.jiranimmicrocredit.utils.Functions;
import root.com.jiranimmicrocredit.utils.NetworkUtils;
import root.com.jiranimmicrocredit.utils.SetterGetters;

/**
 * Created by root on 11/5/15.
 */
public class Loans extends AppCompatActivity implements Property.CallBackInterface,Employment.CallEmpBackInterface,OtherOccupations.CallOtherBackInterface {


        private Toolbar toolbar;
        private TabLayout tabLayout;
        private ViewPager viewPager;


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
    SetterGetters setter =new SetterGetters();
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
    public static Loans context;
    private String[] extras;
    private String userid;


    private int calling_from;
    SharedPreferences pref;
    Functions fn =new Functions();
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.loans_tablayout);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Loans");

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            pref=getSharedPreferences(Constants.STORAGE_PREF,Context.MODE_PRIVATE);

            viewPager = (ViewPager) findViewById(R.id.viewpager);
            setupViewPager(viewPager);

            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);


        }

        private void setupViewPager(ViewPager viewPager) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

            adapter.addFragment(new LoanApplication(), "Apply");
            adapter.addFragment(new MyApplication(), "My Applications");
            viewPager.setAdapter(adapter);
        }

        class ViewPagerAdapter extends FragmentPagerAdapter {
            private final List<Fragment> mFragmentList = new ArrayList<>();
            private final List<String> mFragmentTitleList = new ArrayList<>();

            public ViewPagerAdapter(FragmentManager manager) {
                super(manager);
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            public void addFragment(Fragment fragment, String title) {
                mFragmentList.add(fragment);
                mFragmentTitleList.add(title);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentTitleList.get(position);
            }
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
                croppedimageuri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                cropIntent.putExtra("return-data", true);

                //start the activity - we handle returning in onActivityResult
                cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, croppedimageuri);
                context.startActivityForResult(cropIntent, PIC_CROP);
            } catch (ActivityNotFoundException anfe) {
                //display an error message
                errorMessage = "Sorry your device doesnt support Croping";
                Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        }else{
            Toast.makeText(this,"No image to crop", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void ButtonPressed(int id,int crop,int from) {
        // TODO Auto-generated method stub
        calling_from =from;
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
                    Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
                    toast.show();
                }

                break;
            case 2:
                final CharSequence[] options={"Choose from Gallery","Take a Photo", "Cancel"};

                AlertDialog.Builder imgbuilder = new AlertDialog.Builder(Loans.this);

                imgbuilder.setTitle("Picture");

                imgbuilder.setItems(options, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int item) {



                        if (options[item].equals("Take a Photo"))

                        { if(cropping==0) {

                            try {
                                Intent takephoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                imagefileuri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                                takephoto.putExtra(MediaStore.EXTRA_OUTPUT, imagefileuri);

                                startActivityForResult(takephoto, CAPTURE_IMAGE_ACTIVITY);

                            } catch (ActivityNotFoundException anfe) {
                                //display an error message
                                errorMessage = "Sorry your device doesnt support image capturing";
                                Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }else{
                            try {
                                Intent takephoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                imagefileuri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                                takephoto.putExtra(MediaStore.EXTRA_OUTPUT, imagefileuri);
                                startActivityForResult(takephoto, CAPTURE_IMAGE_CROP_ACTIVITY);

                            } catch (ActivityNotFoundException anfe) {
                                //display an error message
                                errorMessage = "Sorry your device doesnt support image capturing";
                                Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        }



                        } else if (options[item].equals("Choose from Gallery")){

                            if(cropping==0) {
                                try {
                                    Intent photopick = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(photopick, CAPTURE_IMAGE_ACTIVITY);
                                } catch (ActivityNotFoundException anfe) {
                                    //display an error message
                                    errorMessage = "Sorry your device doesnt support Image capturing";
                                    Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }else{
                                try {
                                    Intent photopick = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(photopick, CAPTURE_IMAGE_CROP_ACTIVITY);
                                } catch (ActivityNotFoundException anfe) {
                                    //display an error message
                                    errorMessage = "Sorry your device doesnt support Image capturing";
                                    Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Video");

                builder.setItems(option, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int item) {



                        if (option[item].equals("Record Video"))

                        {

                            try {
                                Intent intentvideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                                fileuri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                                intentvideo.putExtra(MediaStore.EXTRA_OUTPUT, fileuri);
                                intentvideo.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                                startActivityForResult(intentvideo, CAPTURE_VIDEO_ACTIVITY);

                            } catch(ActivityNotFoundException anfe){
                                //display an error message
                                errorMessage = "Sorry your device doesnt support video capturing";
                                Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        } else if (option[item].equals("Choose a Video")){

                            try{  Intent videopick =new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(videopick, CAPTURE_VIDEO_ACTIVITY);
                            } catch(ActivityNotFoundException anfe){
                                //display an error message
                                errorMessage = "Sorry your device doesnt support video capturing";
                                Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
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

    @Override
    public void passdOtherData(String occupationname, String description, String income) {
        if(NetworkUtils.getConnectivityStatusString(this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(context).equalsIgnoreCase("mobile data Enabled")||Constants.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||Constants.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {


            extras=new String[] {occupationname,income,description,userid};
            new FileTransfer("image", "Uploading files...",Constants.TABLE_OTHEROCCUPATION,extras, this).execute(imagePath).getStatus();


        }else {
            Toast.makeText(this, Constants.CONNECTION_FAILED, Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void passdEmpData(String employeename, String department, String period, String email, String income, String selected) {
        if(NetworkUtils.getConnectivityStatusString(this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(context).equalsIgnoreCase("mobile data Enabled")||Constants.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||Constants.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {


            extras=new String[] {userid,employeename,department,location,selected,period,income};
            new FileTransfer("image", "Uploading files...",Constants.TABLE_EMPLOYMENT,extras, this).execute(imagePath).getStatus();


        }else {
            Toast.makeText(this, Constants.CONNECTION_FAILED, Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void passdData(String propertyname, String location, String plotnumber, String Occupancy, String income,String ownership) {

        if(NetworkUtils.getConnectivityStatusString(this).equalsIgnoreCase("Wifi Enabled")||NetworkUtils.getConnectivityStatusString(context).equalsIgnoreCase("mobile data Enabled")||Constants.ConnectionStatus.equalsIgnoreCase("Wifi Enabled")||Constants.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {


            extras=new String[] {location,plotnumber,ownership,Occupancy,income,userid};
            new FileTransfer("image", "Uploading files...",Constants.TABLE_PROPERTY,extras, this).execute(imagePath).getStatus();


        }else {
            Toast.makeText(this, Constants.CONNECTION_FAILED, Toast.LENGTH_SHORT).show();

        }


    }


    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){

        return Uri.fromFile(getOutputMediaFile(type));
    }
    /** Create a File for saving an image or video */
    private  static File getOutputMediaFile(int type){

        //checking mounting of sd card
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()+"/JiraniMicrocredit");


        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdirs()) {
                Toast.makeText(context, "Failed to create Directory Please Mount SD Card", Toast.LENGTH_SHORT).show();
            }

            return null;
        }
        // creating a media file

        Date date = new Date();
        String timestamp = new SimpleDateFormat("yyyymmdd_HHmmss").format(date.getTime());

        File mediafile;
        if(type==MEDIA_TYPE_IMAGE){

            mediafile =new  File(mediaStorageDir.getPath() + File.separator + "MYSECURITY_PIC_EVIDENCE_"+ timestamp + ".jpg" );
            imageFilename = "MYSECURITY_PIC_EVIDENCE_"+ timestamp + ".jpg";

        }
        else if(type==MEDIA_TYPE_VIDEO){

            mediafile =new  File(mediaStorageDir.getPath() + File.separator + "MYSECURITY_VID_EVIDENCE_"+ timestamp + ".mp4" );
            videoFilename="MYSECURITY_VID_EVIDENCE_"+ timestamp + ".mp4";
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

    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode ==CAPTURE_IMAGE_ACTIVITY){
            if(data==null) {
                if(imagefileuri==null){
                    Toast.makeText(this,"Application closed",Toast.LENGTH_SHORT).show();
                    imagePath ="";
                }else {

                    imagePath = imagefileuri.getPath();

                    //setter.setUri(Uri.parse(imagePath));
                    switch (calling_from){

                        case 1:
                            pref.edit().putString(Constants.PROPERTY_IMAGE,imagePath.toString()).commit();
                            break;
                        case 2:
                            pref.edit().putString(Constants.EMPLOYMENT_IMAGE,imagePath.toString()).commit();
                            break;
                        case 3:
                            pref.edit().putString(Constants.RENTEAL_IMAGE,imagePath.toString()).commit();
                            break;

                        case 4:
                            pref.edit().putString(Constants.OTHER_IMAGE,imagePath.toString()).commit();
                            break;


                    }


                }
            }else{


                //  String imagePath2=data.getData().getPath();
                imagePath= getRealPathFromURI(data.getData());
                imageFilename=new File(imagePath).getName();

                switch (calling_from){

                    case 1:
                        pref.edit().putString(Constants.PROPERTY_IMAGE,imagePath.toString()).commit();
                        break;
                    case 2:
                        pref.edit().putString(Constants.EMPLOYMENT_IMAGE,imagePath.toString()).commit();
                        break;
                    case 3:
                        pref.edit().putString(Constants.RENTEAL_IMAGE,imagePath.toString()).commit();
                        break;

                    case 4:
                        pref.edit().putString(Constants.OTHER_IMAGE,imagePath.toString()).commit();
                        break;


                }
               // setter.setUri(Uri.parse(imagePath));


            }
        }else
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

}

