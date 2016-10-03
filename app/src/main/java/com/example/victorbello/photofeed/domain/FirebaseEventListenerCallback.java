package com.example.victorbello.photofeed.domain;


/**
 * Created by victorbello on 29/08/16.
 */

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface FirebaseEventListenerCallback {
    void onChilAdded(DataSnapshot snapshot);
    void onChilRemoved(DataSnapshot snapshot);
    void onCancelled(DatabaseError error);
}
