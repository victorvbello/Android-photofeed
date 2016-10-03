package com.example.victorbello.photofeed.main;


/**
 * Created by victorbello on 16/09/16.
 */

import android.location.Location;
import com.example.victorbello.photofeed.main.events.MainEvent;

public interface MainPresenter {
    void onCreate();
    void onDestroy();

    void logout();
    void uploadPhoto(Location location, String path);
    void onEventMainThread(MainEvent event);
}
