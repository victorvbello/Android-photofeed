package com.example.victorbello.photofeed.photomap;


/**
 * Created by victorbello on 13/10/16.
 */

import org.greenrobot.eventbus.Subscribe;

import com.example.victorbello.photofeed.lib.base.EventBus;
import com.example.victorbello.photofeed.photomap.events.PhotoMapEvent;
import com.example.victorbello.photofeed.photomap.ui.PhotoMapView;


public class PhotoMapPresenterImpl implements PhotoMapPresenter{

    private EventBus eventBus;
    private PhotoMapView view;
    private PhotoMapInteractor interactor;

    public PhotoMapPresenterImpl(EventBus eventBus, PhotoMapInteractor interactor ,PhotoMapView view) {
        this.eventBus = eventBus;
        this.view=view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        this.view=null;
        eventBus.unregister(this);
    }

    @Override
    public void subscribe() {
        interactor.subscribe();
    }

    @Override
    public void unsubscribe() {
        interactor.unsubscribe();
    }

    @Override
    @Subscribe
    public void onEventMainThread(PhotoMapEvent event) {
        String error=event.getError();
        if(this.view!=null){
            if(error!=null){
                view.onPhotoError(error);
            }else{
                switch (event.getType()) {
                    case PhotoMapEvent.READ_EVENT:
                        view.addPhoto(event.getPhoto());
                        break;
                    case PhotoMapEvent.DELETE_EVENT:
                        view.removePhoto(event.getPhoto());
                        break;
                }
            }
        }
    }
}
