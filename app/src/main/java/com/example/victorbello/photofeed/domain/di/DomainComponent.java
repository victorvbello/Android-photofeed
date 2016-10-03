package com.example.victorbello.photofeed.domain.di;


/**
 * Created by victorbello on 30/08/16.
 */

import com.example.victorbello.photofeed.PhotoFeedAppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={DomainModule.class, PhotoFeedAppModule.class})
public interface DomainComponent {
}
