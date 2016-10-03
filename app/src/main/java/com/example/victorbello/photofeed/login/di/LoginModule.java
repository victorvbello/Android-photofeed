package com.example.victorbello.photofeed.login.di;


/**
 * Created by victorbello on 16/09/16.
 */


import dagger.Module;
import dagger.Provides;

import com.example.victorbello.photofeed.domain.FirebaseAPI;
import com.example.victorbello.photofeed.lib.base.EventBus;
import com.example.victorbello.photofeed.login.LoginInteractor;
import com.example.victorbello.photofeed.login.LoginInteractorImpl;
import com.example.victorbello.photofeed.login.LoginPresenter;
import com.example.victorbello.photofeed.login.LoginPresenterImpl;
import com.example.victorbello.photofeed.login.LoginRepository;
import com.example.victorbello.photofeed.login.LoginRepositoryImpl;
import com.example.victorbello.photofeed.login.ui.LoginView;

import javax.inject.Singleton;

@Module
public class LoginModule {

    LoginView loginView;

    public LoginModule(LoginView loginView) {
        this.loginView = loginView;
    }

    @Provides
    @Singleton
    public LoginView providesLoginView(){
        return this.loginView;
    }

    @Provides
    @Singleton
    public LoginPresenter providesLoginPresenter(EventBus eventBus, LoginView view, LoginInteractor loginInteractor){
        return new LoginPresenterImpl(eventBus, view, loginInteractor);
    }

    @Provides
    @Singleton
    public LoginInteractor providesLoginInteractor(LoginRepository loginRepository){
        return new LoginInteractorImpl(loginRepository);
    }

    @Provides
    @Singleton
    public LoginRepository providesLoginRepository(EventBus eventBus, FirebaseAPI firebaseAPI){
        return new LoginRepositoryImpl(eventBus, firebaseAPI);
    }
}
