package com.example.victorbello.photofeed.photolist.ui.adapters;

/**
 * Created by victorbello on 04/10/16.
 */

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageButton;

import de.hdodenhof.circleimageview.CircleImageView;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import com.example.victorbello.photofeed.R;
import com.example.victorbello.photofeed.domain.Util;
import com.example.victorbello.photofeed.entities.Photo;
import com.example.victorbello.photofeed.lib.base.ImageLoader;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {
    private Util util;
    private List<Photo> photoList;
    private ImageLoader imageloader;
    private OnItemClickListener listener;

    public PhotoListAdapter(Util util, List<Photo> photoList, ImageLoader imageloader, OnItemClickListener listener) {
        this.util = util;
        this.photoList = photoList;
        this.imageloader = imageloader;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.row_photos,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Photo currentPhoto=photoList.get(position);
        holder.setOnItemClickListener(currentPhoto, listener);

        imageloader.load(holder.imgMain,currentPhoto.getUrl());
        imageloader.load(holder.imgAvatar,currentPhoto.getEmail());
        holder.txtUser.setText(currentPhoto.getEmail());

        double lat=currentPhoto.getLatitude();
        double lng=currentPhoto.getLongitude();

        if(lat!=0.0 && lng!=0.0){
            holder.txtPlace.setText(util.getFromLocation(lat,lng));
        }

        if(currentPhoto.isPublishedByMe()){
            holder.imgDelete.setVisibility(View.VISIBLE);
        }else{
            holder.imgDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void addPhoto(Photo photo){
        photoList.add(0,photo);
        notifyDataSetChanged();
    }

    public void removePhoto(Photo photo){
        photoList.remove(photo);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView imgAvatar;
        public TextView txtUser;
        public ImageView imgMain;
        public TextView txtPlace;
        public ImageButton imgShare;
        public ImageButton imgDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatar=(CircleImageView) itemView.findViewById(R.id.imgAvatar);
            txtUser=(TextView) itemView.findViewById(R.id.txtUser);
            imgMain=(ImageView) itemView.findViewById(R.id.imgMain);
            txtPlace=(TextView) itemView.findViewById(R.id.txtPlace);
            imgShare=(ImageButton) itemView.findViewById(R.id.imgShare);
            imgDelete=(ImageButton) itemView.findViewById(R.id.imgDelete);
        }

        public void setOnItemClickListener(final Photo photo, final OnItemClickListener listener){
            txtPlace.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    listener.onPlaceClick(photo);
                }
            });
            imgShare.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    listener.onShareClick(photo,imgMain);
                }
            });
            imgDelete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    listener.onDeleteClick(photo);
                }
            });
        }
    }
}
