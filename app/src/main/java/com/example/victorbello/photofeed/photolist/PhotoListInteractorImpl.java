package com.example.victorbello.photofeed.photolist;

/**
 * Created by victorbello on 04/10/16.
 */

import com.example.victorbello.photofeed.entities.Photo;

public class PhotoListInteractorImpl implements PhotoListInteractor{

    private PhotoListRepository repository;

    public PhotoListInteractorImpl(PhotoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribe() {
        repository.subscribe();
    }

    @Override
    public void unsubscribe() {
        repository.unsubscribe();
    }

    @Override
    public void removePhoto(Photo photo) {
        repository.removePhoto(photo);
    }
}
