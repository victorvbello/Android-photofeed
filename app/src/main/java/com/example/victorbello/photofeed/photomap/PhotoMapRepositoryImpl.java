package com.example.victorbello.photofeed.photomap;


/**
 * Created by victorbello on 13/10/16.
 */

import com.example.victorbello.photofeed.photomap.events.PhotoMapEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.example.victorbello.photofeed.entities.Photo;
import com.example.victorbello.photofeed.domain.FirebaseAPI;
import com.example.victorbello.photofeed.domain.FirebaseActionListenerCallback;
import com.example.victorbello.photofeed.domain.FirebaseEventListenerCallback;
import com.example.victorbello.photofeed.lib.base.EventBus;

public class PhotoMapRepositoryImpl implements PhotoMapRepository{

    private EventBus eventBus;
    private FirebaseAPI firebaseAPI;

    public PhotoMapRepositoryImpl(EventBus eventBus, FirebaseAPI firebaseAPI) {
        this.eventBus = eventBus;
        this.firebaseAPI = firebaseAPI;
    }

    @Override
    public void subscribe() {
        firebaseAPI.checkForData(new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {}

            @Override
            public void onError(DatabaseError error) {
                post(PhotoMapEvent.READ_EVENT,error.getMessage());
            }

            @Override
            public void onError(String error) {
                post(PhotoMapEvent.READ_EVENT,error);
            }
        });

        firebaseAPI.subscribe(new FirebaseEventListenerCallback() {
            @Override
            public void onChildAdded(DataSnapshot snapshot) {
                Photo photo=snapshot.getValue(Photo.class);
                photo.setId(snapshot.getKey());

                String email=firebaseAPI.getAuthEmail();
                photo.setPublishedByMe(photo.getEmail().equals(email));

                post(PhotoMapEvent.READ_EVENT,photo);
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
                Photo photo=snapshot.getValue(Photo.class);
                photo.setId(snapshot.getKey());

                post(PhotoMapEvent.DELETE_EVENT,photo);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                post(PhotoMapEvent.READ_EVENT,error.getMessage());
            }
        });
    }

    @Override
    public void unsubscribe() {
        firebaseAPI.unsubscribe();
    }

    private void post(int type, String error){
        post(type,error,null);
    }

    private void post(int type, Photo photo){
        post(type,null, photo);
    }

    private void post(int type,String error, Photo photo){
        PhotoMapEvent event=new PhotoMapEvent();
        event.setType(type);
        event.setError(error);
        event.setPhoto(photo);
        eventBus.post(event);
    }
}
