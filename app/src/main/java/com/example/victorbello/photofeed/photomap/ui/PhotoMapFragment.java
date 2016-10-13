package com.example.victorbello.photofeed.photomap.ui;

/**
 * Created by victorbello on 19/09/16.
 */


import javax.inject.Inject;
import android.support.annotation.Nullable;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.support.design.widget.Snackbar;

import com.example.victorbello.photofeed.PhotoFeedApp;
import com.example.victorbello.photofeed.R;
import com.example.victorbello.photofeed.entities.Photo;
import com.example.victorbello.photofeed.photomap.PhotoMapPresenter;


public class PhotoMapFragment extends Fragment implements PhotoMapView {

    private FrameLayout container;
    private HashMap<String,Integer> arrMsg;

    @Inject
    public PhotoMapPresenter presenter;


    public PhotoMapFragment(){
        arrMsg=new HashMap<String,Integer>();
        arrMsg.put("10000",R.string.photolist_message_db_empty);
        arrMsg.put("20000",R.string.photolist_message_cantnot_getuser);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceEstate){
        super.onCreate(savedInstanceEstate);
        setupInjection();
        presenter.onCreate();
    }

    private void setupInjection() {
        PhotoFeedApp app=(PhotoFeedApp) getActivity().getApplication();
    }


    @Override
    public void onDestroy(){
        presenter.unsubscribe();
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_photo_map,container,false);
        container=(FrameLayout) view.findViewById(R.id.container);
        presenter.subscribe();
        return view;
    }

    @Override
    public void addPhoto(Photo photo) {
    }

    @Override
    public void removePhoto(Photo photo) {

    }

    @Override
    public void onPhotoError(String error) {
        int idMsg=Integer.getInteger(error,0);

        if(error!="") {
            Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
        }else if(idMsg!=0){
            Snackbar.make(container, arrMsg.get(idMsg), Snackbar.LENGTH_SHORT).show();
        }else{
            Snackbar.make(container, getString(R.string.photolist_message_list_empty), Snackbar.LENGTH_SHORT).show();
        }
    }
}
