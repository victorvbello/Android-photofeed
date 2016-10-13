package com.example.victorbello.photofeed.photomap;

/**
 * Created by victorbello on 13/10/16.
 */
public class PhotoMapInteractorImpl implements PhotoMapInteractor{


    private PhotoMapRepository repository;

    public PhotoMapInteractorImpl(PhotoMapRepository repository) {
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
}
