package com.example.victorbello.photofeed.lib.base;

/**
 * Created by victorbello on 14/09/16.
 */

import java.io.File;

public interface ImageStorage {
    String getImageUrl(String id);
    void upload(File file, String id, ImageStorageFinishedListener listener);
}
