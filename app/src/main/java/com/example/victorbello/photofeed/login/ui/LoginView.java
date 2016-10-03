package com.example.victorbello.photofeed.login.ui;

/**
 * Created by victorbello on 15/09/16.
 */
public interface LoginView {
    void enableInputs();
    void disableInputs();
    void showProgress();
    void hideProgress();

    void handleSignUp();
    void handleSignIn();

    void navigateToMainScreen();
    void loginError(String error);

    void newUserSuccess();
    void newUserError(String Error);

    void setUserEmail(String email);
}
