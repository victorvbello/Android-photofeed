package com.example.victorbello.photofeed.photolist;

/**
 * Created by victorbello on 04/10/16.
 */

import com.example.victorbello.photofeed.entities.Photo;
import com.example.victorbello.photofeed.lib.base.EventBus;
import com.example.victorbello.photofeed.photolist.events.PhotoListEvent;
import com.example.victorbello.photofeed.photolist.ui.PhotoListView;

import org.greenrobot.eventbus.Subscribe;

public class PhotoListPresenterImpl implements PhotoListPresenter {

    private PhotoListView view;
    private EventBus eventBus;
    private PhotoListInteractor interactor;


    public PhotoListPresenterImpl(PhotoListView view, EventBus eventBus, PhotoListInteractor interactor) {
        this.view = view;
        this.eventBus = eventBus;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view=null;
        eventBus.unregister(this);
    }

    @Override
    public void subscribe() {
        if(view!=null){
            view.hideList();
            view.showProgress();
        }
        interactor.subscribe();
    }

    @Override
    public void unsubscribe() {
        interactor.unsubscribe();
    }

    @Override
    public void removePhoto(Photo photo) {
        interactor.removePhoto(photo);
    }

    @Override
    @Subscribe
    public void onEventMainThread(PhotoListEvent event) {
        String error=event.getError();
        if(this.view!=null){
            view.hideProgress();
            view.showList();
            if(error!=null){
                view.onPhotoError(error);
            }else{
                switch (event.getType()){
                    case PhotoListEvent.READ_EVENT:
                        view.addPhoto(event.getPhoto());
                        break;
                    case PhotoListEvent.DELETE_EVENT:
                        view.removePhoto(event.getPhoto());
                        break;
                }
            }
        }

    }
}
