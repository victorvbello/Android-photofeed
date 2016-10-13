package com.example.victorbello.photofeed.domain;


/**
 * Created by victorbello on 29/08/16.
 */

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface FirebaseEventListenerCallback {
    void onChildAdded(DataSnapshot snapshot);
    void onChildRemoved(DataSnapshot snapshot);
    void onCancelled(DatabaseError error);
}
