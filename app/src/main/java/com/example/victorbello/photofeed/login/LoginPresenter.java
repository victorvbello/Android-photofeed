package com.example.victorbello.photofeed.login;

/**
 * Created by victorbello on 15/09/16.
 */

import com.example.victorbello.photofeed.login.events.LoginEvent;

public interface LoginPresenter {
    void onCreate();
    void onDestroy();
    void validateLogin(String email,String password);
    void registerNewUser(String email,String password);
    void onEventMainThread(LoginEvent event);
}
