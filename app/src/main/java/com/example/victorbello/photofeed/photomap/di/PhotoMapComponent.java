package com.example.victorbello.photofeed.photomap.di;


/**
 * Created by ragnarok on 12/10/16.
 */

import com.example.victorbello.photofeed.PhotoFeedAppModule;
import com.example.victorbello.photofeed.domain.di.DomainModule;
import com.example.victorbello.photofeed.photomap.PhotoMapPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={PhotoMapModule.class, DomainModule.class, PhotoFeedAppModule.class})
public interface PhotoMapComponent {
    void inject(PhotoMapPresenter fragment);
}
