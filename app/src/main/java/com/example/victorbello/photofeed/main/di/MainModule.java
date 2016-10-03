package com.example.victorbello.photofeed.main.di;


/**
 * Created by victorbello on 03/10/16.
 */
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.victorbello.photofeed.domain.FirebaseAPI;
import com.example.victorbello.photofeed.lib.base.EventBus;
import com.example.victorbello.photofeed.lib.base.ImageStorage;
import com.example.victorbello.photofeed.main.MainPresenter;
import com.example.victorbello.photofeed.main.MainPresenterImpl;
import com.example.victorbello.photofeed.main.MainRepository;
import com.example.victorbello.photofeed.main.MainRepositoryImpl;
import com.example.victorbello.photofeed.main.SessionInteractor;
import com.example.victorbello.photofeed.main.SessionInteractorImpl;
import com.example.victorbello.photofeed.main.UploadInteractor;
import com.example.victorbello.photofeed.main.UploadInteractorImpl;
import com.example.victorbello.photofeed.main.ui.MainView;
import com.example.victorbello.photofeed.main.ui.adapters.MainSectionsPagerAdapter;


@Module
public class MainModule {
    private MainView view;
    private String[] titles;
    private Fragment[] fragments;
    private FragmentManager fragmentManager;

    public MainModule(MainView view, String[] titles, Fragment[] fragments, FragmentManager fragmentManager) {
        this.view = view;
        this.titles = titles;
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
    }

    @Provides
    @Singleton
    MainView providesMainView(){
        return this.view;
    }

    @Provides
    @Singleton
    MainPresenter providesMainMainPresenter(MainView view, EventBus eventBus, UploadInteractor uploadinteractor, SessionInteractor sessionInteractor){
        return new MainPresenterImpl(view, eventBus, uploadinteractor, sessionInteractor);
    }

    @Provides
    @Singleton
    UploadInteractor providesUploadInteractor(MainRepository repository){
        return new UploadInteractorImpl(repository);
    }

    @Provides
    @Singleton
    SessionInteractor providesSessionInteractor(MainRepository repository){
        return new SessionInteractorImpl(repository);
    }

    @Provides
    @Singleton
    MainRepository providesMainRepository(FirebaseAPI firebaseAPI, EventBus eventBus, ImageStorage imageStorage){
        return new MainRepositoryImpl(firebaseAPI, eventBus, imageStorage);
    }

    @Provides
    @Singleton
    MainSectionsPagerAdapter providesAdapter(FragmentManager fm, String[] titles, Fragment[] fragments){
        return new MainSectionsPagerAdapter(fm, titles, fragments);
    }

    @Provides
    @Singleton
    FragmentManager providesFragmentManager(){
        return this.fragmentManager;
    }

    @Provides
    @Singleton
    String[] providesStringArrayForAdapter(){
        return this.titles;
    }

    @Provides
    @Singleton
    Fragment[] providesFragmentArrayForAdapter(){
        return this.fragments;
    }

}
