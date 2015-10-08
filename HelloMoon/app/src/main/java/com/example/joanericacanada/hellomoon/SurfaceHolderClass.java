package com.example.joanericacanada.hellomoon;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;

/**
 * Created by joanericacanada on 10/8/15.
 */
public class SurfaceHolderClass implements SurfaceHolder.Callback{

    private MediaPlayer mediaPlayer;
    private Context context;
    private SurfaceHolder surfaceHolder;
    protected Activity activity;

    public SurfaceHolderClass(Context c, SurfaceHolder surfaceHolder, Activity activity){
        context = c;
        this.surfaceHolder = surfaceHolder;
        this.activity = activity;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        mediaPlayer = MediaPlayer.create(context, R.raw.apollo_17_stroll);
        mediaPlayer.setDisplay(surfaceHolder);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mediaPlayer)
            {
                activity.finish();
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
            @Override
            public void onPrepared(MediaPlayer mediaPlayer)
            {
                mediaPlayer.start();
            }
        });
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}