package com.example.victorbello.photofeed.domain.di;


/**
 * Created by victorbello on 30/08/16.
 */


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import android.content.Context;

import android.location.Geocoder;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.victorbello.photofeed.domain.FirebaseAPI;
import com.example.victorbello.photofeed.domain.Util;

@Module
public class DomainModule {

    @Provides
    @Singleton
    FirebaseAPI providesFirebaseAPI(DatabaseReference databaseReference){
        return new FirebaseAPI(databaseReference);
    }

    @Provides
    @Singleton
    DatabaseReference providesDatabaseReference(){
        return FirebaseDatabase.getInstance().getReference();
    }

    @Provides
    @Singleton
    Util providesUtil(Geocoder geocoder){
        return new Util(geocoder);
    }

    @Provides
    @Singleton
    Geocoder providesGeocoder(Context context){
        return new Geocoder(context);
    }

}
