package com.example.victorbello.photofeed.lib;


/**
 * Created by victorbello on 14/09/16.
 */

import java.io.File;
import java.io.IOException;
import java.util.Map;
import android.os.AsyncTask;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import com.example.victorbello.photofeed.lib.base.ImageStorage;
import com.example.victorbello.photofeed.lib.base.ImageStorageFinishedListener;

public class CloudinaryImageStorage implements ImageStorage {

    private Cloudinary cloudinary;

    public CloudinaryImageStorage(Cloudinary cloudinary){
        this.cloudinary=cloudinary;
    }

    @Override
    public String getImageUrl(String id) {
        return cloudinary.url().generate(id);
    }

    @Override
    public void upload(final File file, final String id, final ImageStorageFinishedListener listener) {
        new AsyncTask<Void, Void, Void>(){
            boolean success=false;
            @Override
            protected Void doInBackground(Void... voids) {
                Map params= ObjectUtils.asMap("public_id",id);
                try{
                    cloudinary.uploader().upload(file,params);
                    success=true;
                }catch(IOException e){
                    listener.onError(e.getLocalizedMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid){
                if(success){
                    listener.onSuccess();
                }
            }
        }.execute();
    }
}
