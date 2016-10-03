package com.example.victorbello.photofeed.main;


/**
 * Created by victorbello on 16/09/16.
 */

import android.location.Location;

public interface UploadInteractor {
    void uploadPhoto(Location location, String path);
}
