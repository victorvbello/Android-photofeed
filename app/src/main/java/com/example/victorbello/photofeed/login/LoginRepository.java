package com.example.victorbello.photofeed.login;

/**
 * Created by victorbello on 15/09/16.
 */
public interface LoginRepository {
    void signUp(String email,String password);
    void signIn(String email,String password);
}
