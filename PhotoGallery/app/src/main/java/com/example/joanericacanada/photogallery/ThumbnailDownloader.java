package com.example.joanericacanada.photogallery;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joanericacanada on 10/13/15.
 */
public class ThumbnailDownloader<Token> extends HandlerThread {
    private static final String TAG = "Thumbnail Downloader";
    private static final int MESSAGE_DOWNLOAD = 0;

    Handler handler;
    Map<Token, String> requestMap = Collections.synchronizedMap(new HashMap<Token, String>());
    Handler responseHandler;
    Listener<Token> listener;

    PicCache cache;
    //private LruCache<String, Bitmap> cache;

    public ThumbnailDownloader(Handler responseHandler){
        super(TAG);
        this.responseHandler = responseHandler;
        int size = ((int) (Runtime.getRuntime().maxMemory()/1024)) /5;
        cache = new PicCache(size);
    }

    public interface  Listener<Token>{
        void onThumbnailDownloaded(Token token, Bitmap thumbnail);
    }

    public void setListener(Listener<Token> listener) {
        this.listener = listener;
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onLooperPrepared(){
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    ImageView imageView = (ImageView)msg.obj;
                    Log.i(TAG, "Got a request for url: " + requestMap.get(imageView));
                    handleRequest(imageView);
                }
            }
        };
    }

    public void queueThumbnail(Token token, String url){
        Log.i(TAG, "Got a URL: " + url);
        requestMap.put(token, url);

        handler
                .obtainMessage(MESSAGE_DOWNLOAD, token)
                .sendToTarget();
    }

    private void handleRequest(final ImageView imageView){
        final Bitmap bitmap;
        try{
            final String url = requestMap.get(imageView);

            if(url == null)
                return;
            if(cache.getPic(url) != null)
                bitmap = cache.getPic(url);
            else {
                byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);
                bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
                Log.i(TAG, "Bitmap created");
                cache.setPic(url, bitmap);
            }

            responseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (requestMap.get(imageView) != url)
                        return;

                    requestMap.remove(imageView);
                    imageView.setImageBitmap(bitmap);
                }
            });
        }catch (IOException ioe){
            Log.e(TAG, "Error downloading image", ioe);
        }
    }

    public void clearQueue(){
        handler.removeMessages(MESSAGE_DOWNLOAD);
        requestMap.clear();
    }
}
