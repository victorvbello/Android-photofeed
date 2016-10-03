package com.example.victorbello.photofeed.lib.base;

/**
 * Created by victorbello on 20/07/16.
 */
public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
