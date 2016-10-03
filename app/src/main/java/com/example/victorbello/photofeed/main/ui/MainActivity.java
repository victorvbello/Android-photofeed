package com.example.victorbello.photofeed.main.ui;

/**
 * Created by victorbello on 15/09/16.
 */

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.os.Build;
import android.os.Environment;
import android.net.Uri;
import android.Manifest;
import android.provider.MediaStore;
import android.database.Cursor;
import android.content.pm.ResolveInfo;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.FloatingActionButton;
import android.location.Location;

import com.example.victorbello.photofeed.PhotoListFragment;
import com.example.victorbello.photofeed.PhotoMapFragment;
import com.example.victorbello.photofeed.main.MainPresenter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import com.example.victorbello.photofeed.R;
import com.example.victorbello.photofeed.PhotoFeedApp;
import com.example.victorbello.photofeed.login.ui.LoginActivity;
import com.example.victorbello.photofeed.main.ui.adapters.MainSectionsPagerAdapter;


public class MainActivity extends AppCompatActivity implements MainView,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener{

    private Toolbar toolbar;
    private TabLayout tablayout;
    private ViewPager viewPager;
    private FloatingActionButton floatingActionButton;

    @Inject
    public MainPresenter prensenter;

    @Inject
    public MainSectionsPagerAdapter adapter;

    @Inject
    public SharedPreferences sharedPreferences;

    private String photoPath;
    private PhotoFeedApp app;
    private GoogleApiClient apiClient;
    private Location lastKnownLocation;
    private boolean resolvingError=false;
    private static final int  REQUEST_RESOLVE_ERROR=0;
    private static final int PERMISSIONS_REQUEST_LOCATION=1;
    private static final int REQUEST_PICTURE=2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (PhotoFeedApp) getApplication();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tablayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(this);

        setupInjection();
        setupNavigation();
        setupGoogleAPIClient();

        prensenter.onCreate();

    }

    @Override
    public void onStart() {
        super.onStart();
        apiClient.connect();
    }

    @Override
    public void onStop() {
        apiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        prensenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View view){
        if(view.getId()==R.id.fab){
            takePicture();
        }
    }
    private void setupGoogleAPIClient() {
        if (apiClient == null) {
            apiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void setupNavigation() {
        String email = sharedPreferences.getString(app.getEmailKey(), getString(R.string.app_name));
        toolbar.setTitle(email);
        setSupportActionBar(toolbar);

        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
    }

    private void setupInjection() {
        String[] titles=new String[]{getString(R.string.main_title_list),
                                    getString(R.string.main_title_map)};
        Fragment[] fragments =new Fragment[] {new PhotoListFragment(), new PhotoMapFragment()};

        app.getMainComponent(this,getSupportFragmentManager(),fragments,titles).inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        prensenter.logout();
        sharedPreferences.edit().clear().commit();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED ) {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSIONS_REQUEST_LOCATION);
            }
            return;
        }
        if(LocationServices.FusedLocationApi.getLocationAvailability(apiClient).isLocationAvailable()){
            lastKnownLocation=LocationServices.FusedLocationApi.getLastLocation(apiClient);
        }else{
            showSnackbar(R.string.main_error_location_notavailable);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch(requestCode){
            case PERMISSIONS_REQUEST_LOCATION:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if(LocationServices.FusedLocationApi.getLocationAvailability(apiClient).isLocationAvailable()){
                        lastKnownLocation=LocationServices.FusedLocationApi.getLastLocation(apiClient);
                    }else{
                        showSnackbar(R.string.main_error_location_notavailable);
                    }
                }
                break;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        apiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
       if(resolvingError){
           return;
       }else if(connectionResult.hasResolution()){
           resolvingError=true;
           try{
               connectionResult.startResolutionForResult(this,REQUEST_RESOLVE_ERROR);
           }catch(IntentSender.SendIntentException e){
               e.printStackTrace();
           }
       }else{
           resolvingError=true;
           GoogleApiAvailability.getInstance().getErrorDialog(this,connectionResult.getErrorCode(),REQUEST_RESOLVE_ERROR).show();
       }
    }

    @Override
    protected void onActivityResult(int resquestCode, int resultCode, Intent data){
        super.onActivityResult(resquestCode, resultCode, data);
        if(resquestCode==REQUEST_RESOLVE_ERROR){
            resolvingError=false;
            if(resquestCode==RESULT_OK){
                if(!apiClient.isConnecting()&& !apiClient.isConnected()){
                    apiClient.connect();
                }
            }
        }else if(resquestCode==REQUEST_PICTURE){
            if(resultCode==RESULT_OK){
                boolean fromCamera=(data==null || data.getData()==null);
                if(fromCamera){
                    addToGallery();
                }else{
                    photoPath=getRealPathFromURI(data.getData());
                }
                prensenter.uploadPhoto(lastKnownLocation,photoPath);
            }
        }
    }

    @Override
    public void onUploadInit() {
        showSnackbar(R.string.main_notice_upload_init);
    }

    @Override
    public void onUploadComplete() {
        showSnackbar(R.string.main_notice_upload_complete);
    }

    @Override
    public void onUploadError(String error) {
        showSnackbar(error);
    }

    public void takePicture(){
        Intent chooserIntent=null;

        List<Intent> intentList=new ArrayList<>();
        Intent pickIntent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra("return-data",true);

        File photoFile=getFile();
        if(photoFile!=null){
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            if(cameraIntent.resolveActivity(getPackageManager())!=null){
                intentList=addIntenstToList(intentList,cameraIntent);
            }
        }

        if(pickIntent.resolveActivity(getPackageManager())!=null){
            intentList=addIntenstToList(intentList,pickIntent);
        }

        if(intentList.size()>0){
            chooserIntent=Intent.createChooser(intentList.remove(intentList.size()-1),getString(R.string.main_message_picture_source));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,intentList.toArray(new Parcelable[]{}));
        }

        if(chooserIntent!=null){
            startActivityForResult(chooserIntent,REQUEST_PICTURE);
        }
    }

    private File getFile(){
        File photoFile=null;
        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName="JPEG_"+timeStamp+"_";
        File storageDir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try{
            storageDir.mkdirs();
            photoFile=File.createTempFile(imageFileName,".jpg",storageDir);
            photoPath=photoFile.getAbsolutePath();
        }catch (IOException e){
            android.util.Log.e("Error GetFile",e.getLocalizedMessage());
            showSnackbar(R.string.main_error_dispatch_camera);
        }
        return photoFile;
    }

    private List<Intent> addIntenstToList(List<Intent> list , Intent intent){
        List<ResolveInfo> resInfo=getPackageManager().queryIntentActivities(intent,0);
        for(ResolveInfo resolveInfo: resInfo){
            String packageName=resolveInfo.activityInfo.packageName;
            Intent targetIntent=new Intent(intent);
            targetIntent.setPackage(packageName);
            list.add(targetIntent);
        }
        return list;
    }

    private void addToGallery(){
        Intent mediaScanIntent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file=new File(photoPath);
        Uri contentUri=Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    private String getRealPathFromURI(Uri contentURI){
        String result=null;
        Cursor cursor = getContentResolver().query(contentURI,null,null,null,null);
        if(cursor==null){
            result=contentURI.getPath();
        }else{
            if(contentURI.toString().contains("mediaKey")){
                cursor.close();
                try{
                    File file=File.createTempFile("tempImg",".jpg",getCacheDir());
                    InputStream input=getContentResolver().openInputStream(contentURI);
                    OutputStream output=new FileOutputStream(file);
                    try{
                        byte[] buffer=new byte[4*1024];
                        int read;
                        while((read=input.read(buffer))!=-1){
                            output.write(buffer,0,read);
                        }
                        output.flush();
                        result=file.getAbsolutePath();
                    }finally{
                        output.close();
                        input.close();
                    }
                }catch (Exception e){

                }
            }else{
                cursor.moveToFirst();
                int dataColumn=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result=cursor.getString(dataColumn);
                cursor.close();
            }
        }
        return result;
    }

    private void showSnackbar(String msg){
        Snackbar.make(viewPager,msg,Snackbar.LENGTH_SHORT).show();
    }

    private void showSnackbar(int strResource){
        Snackbar.make(viewPager,strResource,Snackbar.LENGTH_SHORT).show();
    }
}
