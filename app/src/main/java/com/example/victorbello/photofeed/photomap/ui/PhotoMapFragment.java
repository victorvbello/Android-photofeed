package com.example.victorbello.photofeed.photomap.ui;

/**
 * Created by victorbello on 19/09/16.
 */


import javax.inject.Inject;
import android.support.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.os.Build;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.support.design.widget.Snackbar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;

import com.example.victorbello.photofeed.PhotoFeedApp;
import com.example.victorbello.photofeed.R;
import com.example.victorbello.photofeed.entities.Photo;
import com.example.victorbello.photofeed.photomap.PhotoMapPresenter;



public class PhotoMapFragment extends Fragment implements PhotoMapView , OnMapReadyCallback{

    private static final int PERMISSIONS_REQUEST_LOCATION=1;

    private FrameLayout container;
    private HashMap<String,Integer> arrMsg;
    private GoogleMap map;
    private HashMap<Marker,Photo> markers;



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
        markers=new HashMap<Marker, Photo>();
        presenter.onCreate();
    }

    private void setupInjection() {
        PhotoFeedApp app=(PhotoFeedApp) getActivity().getApplication();
        app.getPhotoMapComponent(this,this).inject(this);
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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm=getChildFragmentManager();
        SupportMapFragment mapFragment=(SupportMapFragment) fm.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void addPhoto(Photo photo) {
        LatLng location=new LatLng(photo.getLatitude(),photo.getLongitude());

        Marker marker=map.addMarker(new MarkerOptions().position(location));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location,6));
        markers.put(marker,photo);
    }

    @Override
    public void removePhoto(Photo photo) {
        for(Map.Entry<Marker,Photo> entry: markers.entrySet()){
            Photo currentPhoto=entry.getValue();
            Marker currentMarker=entry.getKey();
            if(currentPhoto.getId().equals(photo.getId())){
                currentMarker.remove();
                markers.remove(currentMarker);
                break;
            }
        }
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        presenter.subscribe();
        if(ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSIONS_REQUEST_LOCATION);
            }
            return;
        }
        map.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults){
        switch (requestCode){
            case PERMISSIONS_REQUEST_LOCATION:{
                if(grantResults.length>0
                        && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if(map!=null){
                        map.setMyLocationEnabled(true);
                    }
                }
                return;
            }
        }
    }
}
