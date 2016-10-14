package com.example.victorbello.photofeed;

/**
 * Created by victorbello on 30/08/16.
 */

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.victorbello.photofeed.domain.di.DomainModule;
import com.example.victorbello.photofeed.lib.di.LibsModule;
import com.example.victorbello.photofeed.login.di.DaggerLoginComponent;
import com.example.victorbello.photofeed.login.di.LoginComponent;
import com.example.victorbello.photofeed.login.di.LoginModule;
import com.example.victorbello.photofeed.login.ui.LoginView;
import com.example.victorbello.photofeed.main.di.DaggerMainComponent;
import com.example.victorbello.photofeed.main.di.MainComponent;
import com.example.victorbello.photofeed.main.di.MainModule;
import com.example.victorbello.photofeed.main.ui.MainView;
import com.example.victorbello.photofeed.photolist.di.DaggerPhotoListComponent;
import com.example.victorbello.photofeed.photolist.di.PhotoListComponent;
import com.example.victorbello.photofeed.photolist.di.PhotoListModule;
import com.example.victorbello.photofeed.photolist.ui.PhotoListFragment;
import com.example.victorbello.photofeed.photolist.ui.PhotoListView;
import com.example.victorbello.photofeed.photolist.ui.adapters.OnItemClickListener;
import com.example.victorbello.photofeed.photomap.di.DaggerPhotoMapComponent;
import com.example.victorbello.photofeed.photomap.di.PhotoMapComponent;
import com.example.victorbello.photofeed.photomap.di.PhotoMapModule;
import com.example.victorbello.photofeed.photomap.ui.PhotoMapFragment;
import com.example.victorbello.photofeed.photomap.ui.PhotoMapView;
import com.google.firebase.database.FirebaseDatabase;

public class PhotoFeedApp extends Application{

    public final static String EMAIL_KEY="email";
    public final static String SHARED_PREFERENCES_NAME="UserPrefs";

    private DomainModule domainModuel;
    private PhotoFeedAppModule photoFeedAppModule;
    private LibsModule libsModule;

    @Override
    public void onCreate(){
        super.onCreate();
        initModules();
        initFirebase();
    }

    public static String getEmailKey() {
        return EMAIL_KEY;
    }

    private void initModules() {
        photoFeedAppModule= new PhotoFeedAppModule(this);
        domainModuel=new DomainModule();
        libsModule=new LibsModule(null);
    }

    private void initFirebase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public static String getSharedPreferencesName() {
        return SHARED_PREFERENCES_NAME;
    }

    public LoginComponent getLoginComponent(LoginView view){
        return DaggerLoginComponent
                .builder()
                .photoFeedAppModule(photoFeedAppModule)
                .domainModule(domainModuel)
                .libsModule(libsModule)
                .loginModule(new LoginModule(view))
                .build();
    }

    public MainComponent getMainComponent(MainView view,  FragmentManager fragmentManager,Fragment[] fragments,String[] titles){
        return DaggerMainComponent
                .builder()
                .photoFeedAppModule(photoFeedAppModule)
                .domainModule(domainModuel)
                .libsModule(libsModule)
                .mainModule(new MainModule(view, titles, fragments, fragmentManager))
                .build();
    }

    public PhotoListComponent getPhotoListComponent(Fragment fragment,PhotoListView view, OnItemClickListener onItemClickListener){
        return DaggerPhotoListComponent
                .builder()
                .photoFeedAppModule(photoFeedAppModule)
                .domainModule(domainModuel)
                .libsModule(new LibsModule(fragment))
                .photoListModule(new PhotoListModule(view,onItemClickListener))
                .build();
    }

    public PhotoMapComponent getPhotoMapComponent(Fragment fragment, PhotoMapView view){
        return DaggerPhotoMapComponent
                .builder()
                .photoFeedAppModule(photoFeedAppModule)
                .domainModule(domainModuel)
                .libsModule(new LibsModule(fragment))
                .photoMapModule(new PhotoMapModule(view))
                .build();
    }
}
