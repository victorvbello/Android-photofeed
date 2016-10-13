package com.example.victorbello.photofeed.photolist.di;


/**
 * Created by ragnarok on 12/10/16.
 */
import javax.inject.Singleton;

import dagger.Component;

import com.example.victorbello.photofeed.PhotoFeedAppModule;
import com.example.victorbello.photofeed.domain.di.DomainModule;
import com.example.victorbello.photofeed.lib.di.LibsModule;
import com.example.victorbello.photofeed.photolist.ui.PhotoListFragment;

@Singleton
@Component(modules={PhotoListModule.class, DomainModule.class, LibsModule.class, PhotoFeedAppModule.class})
public interface PhotoListComponent {
    void inject(PhotoListFragment fragment);
}
