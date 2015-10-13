package com.example.joanericacanada.photogallery;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by joanericacanada on 10/13/15.
 */
public class PicCache {
    private String key;
    private Bitmap pic;
    private int size;

    private LruCache<String, Bitmap> cache;

    public PicCache(int size){
        this.size = size;
        cache = new LruCache<>(size);
    }

    public Bitmap getPic(String k){
        return cache.get(k);
    }

    public void setPic(String k, Bitmap b){
        cache.put(k, b);
    }

}
