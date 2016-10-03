package com.example.victorbello.photofeed.lib.di;


/**
 * Created by victorbello on 21/07/16.
 */

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

import android.support.v4.app.Fragment;
import android.content.Context;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.Glide;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.example.victorbello.photofeed.lib.CloudinaryImageStorage;
import com.example.victorbello.photofeed.lib.base.ImageLoader;
import com.example.victorbello.photofeed.lib.GlideImageLoader;
import com.example.victorbello.photofeed.lib.base.EventBus;
import com.example.victorbello.photofeed.lib.GreenRobotEventBus;
import com.example.victorbello.photofeed.lib.base.ImageStorage;

@Module
public class LibsModule {
    private Fragment fragment;

    public LibsModule(Fragment fragment){
        this.fragment=fragment;
    }

    @Provides
    @Singleton
    public ImageLoader providesImageLoader(RequestManager requestManager){
        return new GlideImageLoader(requestManager);

    }

    @Provides
    @Singleton
    public RequestManager providesRequestManager(Fragment fragment){
        return Glide.with(fragment);
    }

    @Provides
    @Singleton
    public Fragment providesFragment(){
        return this.fragment;
    }

    @Provides
    @Singleton
    public EventBus providesEventBus(org.greenrobot.eventbus.EventBus eventBus){
        return new GreenRobotEventBus(eventBus);
    }

    @Provides
    @Singleton
    public org.greenrobot.eventbus.EventBus providesLibraryEventBus(){
        return org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Provides
    @Singleton
    ImageStorage providesImageStorage(Cloudinary cloudinary){
        return new CloudinaryImageStorage(cloudinary);
    }

    @Provides
    @Singleton
    Cloudinary providesCloudinary(Context context){
        return new Cloudinary(Utils.cloudinaryUrlFromContext(context));
    }

}
