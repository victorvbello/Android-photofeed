package com.example.victorbello.photofeed.login.di;


/**
 * Created by victorbello on 16/09/16.
 */


import javax.inject.Singleton;

import dagger.Component;

import com.example.victorbello.photofeed.PhotoFeedAppModule;
import com.example.victorbello.photofeed.domain.di.DomainModule;
import com.example.victorbello.photofeed.lib.di.LibsModule;
import com.example.victorbello.photofeed.login.ui.LoginActivity;

@Singleton
@Component(modules={LoginModule.class, DomainModule.class, LibsModule.class, PhotoFeedAppModule.class})
public interface LoginComponent {
    void inject(LoginActivity activity);
}
