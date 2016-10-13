package com.example.victorbello.photofeed.photomap.di;


/**
 * Created by ragnarok on 12/10/16.
 */


import com.example.victorbello.photofeed.domain.FirebaseAPI;
import com.example.victorbello.photofeed.lib.base.EventBus;
import com.example.victorbello.photofeed.photomap.PhotoMapInteractor;
import com.example.victorbello.photofeed.photomap.PhotoMapInteractorImpl;
import com.example.victorbello.photofeed.photomap.PhotoMapPresenter;
import com.example.victorbello.photofeed.photomap.PhotoMapPresenterImpl;
import com.example.victorbello.photofeed.photomap.PhotoMapRepository;
import com.example.victorbello.photofeed.photomap.PhotoMapRepositoryImpl;
import com.example.victorbello.photofeed.photomap.ui.PhotoMapView;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class PhotoMapModule {

    private PhotoMapView view;

    public PhotoMapModule(PhotoMapView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    PhotoMapView providesPhotoListView(){
        return this.view;
    }

    @Provides
    @Singleton
    PhotoMapPresenter providesPhotoMapPresenter(EventBus eventBus, PhotoMapInteractor interactor){
        return new PhotoMapPresenterImpl(eventBus,interactor,view);
    }

    @Provides
    @Singleton
    PhotoMapInteractor providesPhotoListInteractor(PhotoMapRepository repository){
        return new PhotoMapInteractorImpl(repository);
    }

    @Provides
    @Singleton
    PhotoMapRepository providesPhotoListRepository(EventBus eventBus, FirebaseAPI firebaseAPI){
        return new PhotoMapRepositoryImpl(eventBus,firebaseAPI);
    }

}
