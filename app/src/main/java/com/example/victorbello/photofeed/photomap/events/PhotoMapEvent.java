package com.example.victorbello.photofeed.photomap.events;

/**
 * Created by victorbello on 13/10/16.
 */

import com.example.victorbello.photofeed.entities.Photo;

public class PhotoMapEvent {

    private int type;
    private Photo photo;
    private String error;

    public final static int READ_EVENT=2;
    public final static int DELETE_EVENT=3;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
