package com.example.victorbello.photofeed.login;

/**
 * Created by victorbello on 15/09/16.
 */
public interface LoginInteractor {
    void doSignUp(String email, String password);
    void doSignIn(String email, String password);
}
