package com.example.victorbello.photofeed.photolist;


/**
 * Created by victorbello on 04/10/16.
 */

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.example.victorbello.photofeed.entities.Photo;
import com.example.victorbello.photofeed.lib.base.EventBus;
import com.example.victorbello.photofeed.domain.FirebaseAPI;
import com.example.victorbello.photofeed.photolist.events.PhotoListEvent;
import com.example.victorbello.photofeed.domain.FirebaseActionListenerCallback;
import com.example.victorbello.photofeed.domain.FirebaseEventListenerCallback;


public class PhotoListRepositoryImpl implements PhotoListRepository{

    private EventBus eventBus;
    private FirebaseAPI firebaseAPI;

    public PhotoListRepositoryImpl(EventBus eventBus, FirebaseAPI firebaseAPI) {
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
                post(PhotoListEvent.READ_EVENT,error.getMessage());
            }

            @Override
            public void onError(String error) {
                post(PhotoListEvent.READ_EVENT,error);
            }
        });

        firebaseAPI.subscribe(new FirebaseEventListenerCallback() {
            @Override
            public void onChilAdded(DataSnapshot snapshot) {
                Photo photo=snapshot.getValue(Photo.class);
                photo.setId(snapshot.getKey());

                String email=firebaseAPI.getAuthEmail();
                boolean publishedByMy=photo.getEmail().equals(email);
                photo.setPublishedByMe(publishedByMy);

                post(PhotoListEvent.READ_EVENT,photo);
            }

            @Override
            public void onChilRemoved(DataSnapshot snapshot) {
                Photo photo=snapshot.getValue(Photo.class);
                photo.setId(snapshot.getKey());
                post(PhotoListEvent.DELETE_EVENT,photo);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                post(PhotoListEvent.READ_EVENT,error.getMessage());
            }
        });
    }

    @Override
    public void unsubscribe() {
        firebaseAPI.unsubscribe();
    }

    @Override
    public void removePhoto(final Photo photo) {
        firebaseAPI.remove(photo, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                post(PhotoListEvent.DELETE_EVENT,photo);
            }

            @Override
            public void onError(DatabaseError error) {
                post(PhotoListEvent.DELETE_EVENT,error.getMessage());
            }

            @Override
            public void onError(String error) {
                post(PhotoListEvent.DELETE_EVENT,error);
            }
        });
    }

    private void post(int type,String error){
        post(type,error,null);
    }

    private void post(int type, Photo photo){
        post(type,null,photo);
    }

    private void post(int type, String error, Photo photo){
        PhotoListEvent event = new PhotoListEvent();
        event.setType(type);
        event.setError(error);
        event.setPhoto(photo);
        eventBus.post(event);
    }
}
