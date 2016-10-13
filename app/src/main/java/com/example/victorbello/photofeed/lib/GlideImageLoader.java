package com.example.victorbello.photofeed.lib;


/**
 * Created by victorbello on 21/07/16.
 */

import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.victorbello.photofeed.lib.base.ImageLoader;

public class GlideImageLoader implements ImageLoader {

    private RequestManager glideRequestManager;

    public GlideImageLoader(RequestManager glideRequestManager){
        this.glideRequestManager=glideRequestManager;
    }

    @Override
    public void load(ImageView imageloader, String URL) {
        this.glideRequestManager.load(URL)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop()
                                .override(400,400)
                                .into(imageloader);
    }
}
