package com.example.victorbello.photofeed.photolist.di;


/**
 * Created by ragnarok on 12/10/16.
 */


import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

import com.example.victorbello.photofeed.domain.FirebaseAPI;
import com.example.victorbello.photofeed.domain.Util;
import com.example.victorbello.photofeed.entities.Photo;
import com.example.victorbello.photofeed.lib.base.EventBus;
import com.example.victorbello.photofeed.lib.base.ImageLoader;
import com.example.victorbello.photofeed.photolist.PhotoListInteractor;
import com.example.victorbello.photofeed.photolist.PhotoListInteractorImpl;
import com.example.victorbello.photofeed.photolist.PhotoListPresenter;
import com.example.victorbello.photofeed.photolist.PhotoListPresenterImpl;
import com.example.victorbello.photofeed.photolist.PhotoListRepository;
import com.example.victorbello.photofeed.photolist.PhotoListRepositoryImpl;
import com.example.victorbello.photofeed.photolist.ui.PhotoListView;
import com.example.victorbello.photofeed.photolist.ui.adapters.OnItemClickListener;
import com.example.victorbello.photofeed.photolist.ui.adapters.PhotoListAdapter;



@Module
public class PhotoListModule {

    private PhotoListView view;
    private OnItemClickListener listener;

    public PhotoListModule(PhotoListView view, OnItemClickListener onItemClickListener) {
        this.view = view;
        this.listener = onItemClickListener;
    }

    @Provides
    @Singleton
    PhotoListView providesPhotoListView(){
        return this.view;
    }

    @Provides
    @Singleton
    PhotoListPresenter providesPhotoListPresenter(EventBus eventBus, PhotoListInteractor interactor){
        return new PhotoListPresenterImpl(view,eventBus,interactor);
    }

    @Provides
    @Singleton
    PhotoListInteractor providesPhotoListInteractor(PhotoListRepository repository){
        return new PhotoListInteractorImpl(repository);
    }

    @Provides
    @Singleton
    PhotoListRepository providesPhotoListRepository(EventBus eventBus, FirebaseAPI firebaseAPI){
        return new PhotoListRepositoryImpl(eventBus,firebaseAPI);
    }

    @Provides
    @Singleton
    PhotoListAdapter providesPhotoListAdapter(Util util, List<Photo> photoList, ImageLoader imageloader, OnItemClickListener listener){
        return new PhotoListAdapter(util, photoList, imageloader, listener);
    }

    @Provides
    @Singleton
    OnItemClickListener providesOnItemClickListener(){
        return this.listener;
    }

    @Provides
    @Singleton
    List<Photo> providesPhotoList(){
        return new ArrayList<Photo>();
    }

}
