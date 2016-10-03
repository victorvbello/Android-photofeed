package com.example.victorbello.photofeed.main;

/**
 * Created by victorbello on 22/09/16.
 */

import android.location.Location;

import com.example.victorbello.photofeed.entities.Photo;
import com.example.victorbello.photofeed.domain.FirebaseAPI;
import com.example.victorbello.photofeed.lib.base.EventBus;
import com.example.victorbello.photofeed.lib.base.ImageStorage;
import com.example.victorbello.photofeed.lib.base.ImageStorageFinishedListener;
import com.example.victorbello.photofeed.main.events.MainEvent;

import java.io.File;

public class MainRepositoryImpl implements MainRepository {

    private FirebaseAPI firebaseAPI;
    private EventBus eventBus;
    private ImageStorage imageStorage;

    public MainRepositoryImpl(FirebaseAPI firebaseAPI, EventBus eventBus, ImageStorage imageStorage) {
        this.firebaseAPI = firebaseAPI;
        this.eventBus = eventBus;
        this.imageStorage = imageStorage;
    }

    @Override
    public void logout() {
        firebaseAPI.logout();
    }

    @Override
    public void uploadPhoto(Location location, String path) {
        final String newPhotoId=firebaseAPI.create();
        final Photo photo=new Photo();
        photo.setId(newPhotoId);
        photo.setEmail(firebaseAPI.getAuthEmail());
        if(location!=null){
            photo.setLatitude(location.getLatitude());
            photo.setLongitude(location.getLongitude());
        }
        post(MainEvent.UPLOAD_INIT);
        ImageStorageFinishedListener listener=new ImageStorageFinishedListener() {
            @Override
            public void onSuccess() {
                String url=imageStorage.getImageUrl(newPhotoId);
                photo.setUrl(url);
                firebaseAPI.update(photo);
                post(MainEvent.UPLOAD_COMPLETE);
            }

            @Override
            public void onError(String error) {
                post(MainEvent.UPLOAD_ERROR,error);
            }
        };
        imageStorage.upload(new File(path), newPhotoId,listener);
    }

    private void post(int type){
        post(type,null);
    }

    private void post(int type,String error ){
        MainEvent event=new MainEvent();
        event.setType(type);
        event.setError(error);
        eventBus.post(event);
    }
}
