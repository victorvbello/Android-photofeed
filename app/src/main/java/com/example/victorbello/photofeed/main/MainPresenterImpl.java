package com.example.victorbello.photofeed.main;

/**
 * Created by victorbello on 22/09/16.
 */

import org.greenrobot.eventbus.Subscribe;

import android.location.Location;

import com.example.victorbello.photofeed.main.events.MainEvent;
import com.example.victorbello.photofeed.main.ui.MainView;
import com.example.victorbello.photofeed.lib.base.EventBus;

public class MainPresenterImpl implements MainPresenter {

    private MainView view;
    private EventBus eventBus;
    private UploadInteractor uploadInteractor;
    private SessionInteractor sessionInteractor;

    public MainPresenterImpl(MainView view, EventBus eventBus, UploadInteractor uploadinteractor, SessionInteractor sessionInteractor){
        this.view=view;
        this.eventBus=eventBus;
        this.uploadInteractor=uploadinteractor;
        this.sessionInteractor=sessionInteractor;
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
    public void logout() {
        sessionInteractor.logout();
    }

    @Override
    public void uploadPhoto(Location location, String path) {
        uploadInteractor.uploadPhoto(location,path);
    }

    @Override
    @Subscribe
    public void onEventMainThread(MainEvent event) {
        if(this.view!=null){
            switch(event.getType()){
                case MainEvent.UPLOAD_INIT:
                    view.onUploadInit();
                    break;
                case MainEvent.UPLOAD_COMPLETE:
                    view.onUploadComplete();
                    break;
                case MainEvent.UPLOAD_ERROR:
                    view.onUploadError(event.getError());
                    break;
            }
        }
    }
}
