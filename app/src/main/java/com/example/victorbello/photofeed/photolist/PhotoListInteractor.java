package com.example.victorbello.photofeed.photolist;

/**
 * Created by victorbello on 04/10/16.
 */

import com.example.victorbello.photofeed.entities.Photo;

public interface PhotoListInteractor {
    void subscribe();
    void unsubscribe();

    void removePhoto(Photo photo);
}
