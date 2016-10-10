package com.example.victorbello.photofeed.photolist.ui;

/**
 * Created by victorbello on 19/09/16.
 */
import javax.inject.Inject;

import android.support.annotation.Nullable;

import java.io.ByteArrayOutputStream;

import android.os.Bundle;
import android.net.Uri;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;


import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.example.victorbello.photofeed.R;
import com.example.victorbello.photofeed.entities.Photo;
import com.example.victorbello.photofeed.photolist.PhotoListPresenter;
import com.example.victorbello.photofeed.photolist.ui.adapters.OnItemClickListener;
import com.example.victorbello.photofeed.photolist.ui.adapters.PhotoListAdapter;


public class PhotoListFragment extends Fragment implements PhotoListView, OnItemClickListener{

    private FrameLayout container;
    private RecyclerView recyclerView;
    private ProgressBar progressbar;

    @Inject
    private PhotoListAdapter adapter;
    @Inject
    private PhotoListPresenter presenter;


    public PhotoListFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setupInjection();
        presenter.onCreate();
    }

    @Override
    public void onDestroy(){
        presenter.unsubscribe();
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.fragment_photo_list,viewGroup,false);

        container=(FrameLayout) view.findViewById(R.id.container);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        progressbar=(ProgressBar) view.findViewById(R.id.progressBar);

        setupRecyclerView();
        presenter.subscribe();
        return view;
    }

    private void setupInjection() {
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    @Override
    public void showList() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void addPhoto(Photo photo) {
        adapter.addPhoto(photo);
    }

    @Override
    public void removePhoto(Photo photo) {
        adapter.removePhoto(photo);
    }

    @Override
    public void onPhotoError(String error) {
        Snackbar.make(container,error,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onPlaceClick(Photo photo) {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:"+photo.getLatitude()+" ,"+photo.getLongitude()));
        if(intent.resolveActivity(getActivity().getPackageManager())!=null){
            startActivity(intent);
        }
    }

    @Override
    public void onShareClick(Photo photo, ImageView img) {
        Bitmap bitmap=((GlideBitmapDrawable)img.getDrawable()).getBitmap();
        Intent share=new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");

        ByteArrayOutputStream bytes=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
        String path= MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),bitmap,null,null);
        Uri imageUri=Uri.parse(path);

        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share,getString(R.string.photolist_menssage_share)));
    }

    @Override
    public void onDeleteClick(Photo photo) {
        presenter.removePhoto(photo);
    }
}
