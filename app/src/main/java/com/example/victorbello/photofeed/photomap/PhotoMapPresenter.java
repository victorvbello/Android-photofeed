package com.example.victorbello.photofeed.photomap;


/**
 * Created by victorbello on 13/10/16.
 */

import com.example.victorbello.photofeed.photomap.events.PhotoMapEvent;

public interface PhotoMapPresenter {
    void onCreate();
    void onDestroy();

    void subscribe();
    void unsubscribe();

    void onEventMainThread(PhotoMapEvent event);
}
