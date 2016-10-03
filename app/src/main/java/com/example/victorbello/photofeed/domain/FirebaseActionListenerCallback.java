package com.example.victorbello.photofeed.domain;


/**
 * Created by victorbello on 29/08/16.
 */

import com.google.firebase.database.DatabaseError;

public interface FirebaseActionListenerCallback {
    void onSuccess();
    void onError(DatabaseError error);
    void onError(String error);
}
