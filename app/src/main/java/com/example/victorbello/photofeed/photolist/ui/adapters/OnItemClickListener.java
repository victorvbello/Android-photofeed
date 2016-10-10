package com.example.victorbello.photofeed.photolist.ui.adapters;

/**
 * Created by victorbello on 04/10/16.
 */

import android.widget.ImageView;
import com.example.victorbello.photofeed.entities.Photo;

public interface OnItemClickListener {
    void onPlaceClick(Photo photo);
    void onShareClick(Photo photo, ImageView img);
    void onDeleteClick(Photo photo);
}
