package com.example.victorbello.photofeed.photolist;

/**
 * Created by victorbello on 04/10/16.
 */

import com.example.victorbello.photofeed.entities.Photo;
import com.example.victorbello.photofeed.photolist.events.PhotoListEvent;

public interface PhotoListPresenter {
    void onCreate();
    void onDestroy();

    void subscribe();
    void unsubscribe();

    void removePhoto(Photo photo);
    void onEventMainThread(PhotoListEvent event);
}
