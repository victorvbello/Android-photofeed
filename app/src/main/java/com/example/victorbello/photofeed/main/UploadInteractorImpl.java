package com.example.victorbello.photofeed.main;

import android.location.Location;

/**
 * Created by victorbello on 22/09/16.
 */
public class UploadInteractorImpl implements UploadInteractor {

    private MainRepository repository;

    public UploadInteractorImpl(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void uploadPhoto(Location location, String path) {
        repository.uploadPhoto(location,path);
    }
}
