package com.example.victorbello.photofeed.login;

/**
 * Created by victorbello on 15/09/16.
 */

import com.example.victorbello.photofeed.lib.base.EventBus;
import com.example.victorbello.photofeed.login.events.LoginEvent;
import com.example.victorbello.photofeed.login.ui.LoginView;

import org.greenrobot.eventbus.Subscribe;

public class LoginPresenterImpl implements LoginPresenter {

    private EventBus eventBus;
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(EventBus eventBus, LoginView view, LoginInteractor loginInteractor) {
        this.eventBus = eventBus;
        this.loginView = view;
        this.loginInteractor = loginInteractor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        loginView=null;
        eventBus.unregister(this);
    }

    @Override
    public void validateLogin(String email, String password) {
        if(loginView!=null){
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignIn(email, password);
    }

    @Override
    public void registerNewUser(String email, String password) {
        if(loginView!=null){
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignUp(email, password);
    }

    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        switch(event.getEventType()){
            case LoginEvent.onSignInSuccess:
                onSignInSuccess(event.getCurrentUserEmail());
                break;
            case LoginEvent.onSignUpSuccess:
                onSignUpSuccess();
                break;
            case LoginEvent.onSignInError:
                onSignInError(event.getErrorMessage());
                break;
            case LoginEvent.onSignUpError:
                onSignUpError(event.getErrorMessage());
                break;
            case LoginEvent.onFailedToRecoverSession:
                onFailedToRecoverSession();
                break;
        }
    }

    private void onFailedToRecoverSession() {
        if(loginView!=null){
            loginView.hideProgress();
            loginView.enableInputs();
        }
    }

    private void onSignUpSuccess() {
        if(loginView!=null){
            loginView.newUserSuccess();
        }
    }

    private void onSignInSuccess(String email) {
        if(loginView!=null){
            loginView.setUserEmail(email);
            loginView.navigateToMainScreen();
        }
    }

    private void onSignUpError(String error) {
        if(loginView!=null){
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.newUserError(error);
        }
    }

    private void onSignInError(String error) {
        if(loginView!=null){
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.loginError(error);
        }
    }
}
