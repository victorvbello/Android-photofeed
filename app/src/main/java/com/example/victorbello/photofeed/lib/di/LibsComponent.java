package com.example.victorbello.photofeed.lib.di;


/**
 * Created by victorbello on 30/08/16.
 */


import javax.inject.Singleton;
import dagger.Component;

import com.example.victorbello.photofeed.PhotoFeedAppModule;

@Singleton
@Component(modules={LibsModule.class, PhotoFeedAppModule.class})
public interface LibsComponent {
}
