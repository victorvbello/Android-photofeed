package com.example.victorbello.photofeed.photolist.events;

/**
 * Created by victorbello on 04/10/16.
 */

import com.example.victorbello.photofeed.entities.Photo;

public class PhotoListEvent {

    private int type;
    private Photo photo;
    private String error;

    public final static int READ_EVENT=0;
    public final static int DELETE_EVENT=1;

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
