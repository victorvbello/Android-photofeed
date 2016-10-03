package com.example.victorbello.photofeed;

/**
 * Created by victorbello on 19/09/16.
 */

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;

public class PhotoListFragment extends Fragment {

    public PhotoListFragment(){

    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_photo_list,container,false);
    }
}
