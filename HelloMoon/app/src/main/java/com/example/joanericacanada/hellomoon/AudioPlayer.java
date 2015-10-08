package com.example.joanericacanada.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by joanericacanada on 10/7/15.
 */
public class AudioPlayer {
    private MediaPlayer mPlayer;
    private int length = 0;

    public void stop() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
            length = 0;
        }
    }
    public void play(Context c) {
        if(length == 0){
            stop();
            mPlayer = MediaPlayer.create(c, R.raw.one_small_step);
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    stop();
                }
            });
        }else
            mPlayer.seekTo(length);

        mPlayer.start();
    }

    public void pause(){
            mPlayer.pause();
            length = mPlayer.getCurrentPosition();
    }
}
