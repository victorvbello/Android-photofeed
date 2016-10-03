package com.example.victorbello.photofeed.main;

/**
 * Created by victorbello on 22/09/16.
 */
public class SessionInteractorImpl implements SessionInteractor {

    private MainRepository repository;

    public SessionInteractorImpl(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void logout() {
        repository.logout();
    }
}
