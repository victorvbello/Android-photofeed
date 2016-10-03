package com.example.victorbello.photofeed.main.di;


/**
 * Created by victorbello on 03/10/16.
 */


import javax.inject.Singleton;
import dagger.Component;

import com.example.victorbello.photofeed.main.ui.MainActivity;
import com.example.victorbello.photofeed.domain.di.DomainModule;
import com.example.victorbello.photofeed.lib.di.LibsModule;
import com.example.victorbello.photofeed.PhotoFeedAppModule;

@Singleton
@Component(modules={MainModule.class, DomainModule.class,LibsModule.class,PhotoFeedAppModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
}
