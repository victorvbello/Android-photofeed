package com.example.victorbello.photofeed.photomap.ui;


/**
 * Created by victorbello on 13/10/16.
 */

import com.example.victorbello.photofeed.entities.Photo;

public interface PhotoMapView {
    void addPhoto(Photo photo);
    void removePhoto(Photo photo);
    void onPhotoError(String error);
}
