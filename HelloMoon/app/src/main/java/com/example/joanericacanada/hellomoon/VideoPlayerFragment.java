package com.example.joanericacanada.hellomoon;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by joanericacanada on 10/8/15.
 */
public class VideoPlayerFragment extends Fragment {
    private SurfaceView   mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private MediaPlayer mediaPlayer;

    private Button btnPlay;
    private Button btnStop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video_player, parent, false);

        /*btnPlay = (Button)v.findViewById(R.id.hellomoon_playButton);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mSurfaceView = (SurfaceView) v.findViewById(R.id.videoSurface);
                mSurfaceHolder = mSurfaceView.getHolder();
                mSurfaceHolder.addCallback(new SurfaceHolder.Callback (){
                    @Override
                    public void surfaceCreated(SurfaceHolder surfaceHolder)
                    {
                        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.apollo_17_stroll);
                        mediaPlayer.setDisplay(surfaceHolder);
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                            }
                        });
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                            }
                        });
                    }

                    @Override
                    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                    }

                    @Override
                    public void surfaceDestroyed(SurfaceHolder holder) {

                    }
                });
            }
        });*/
        mSurfaceView = (SurfaceView) v.findViewById(R.id.videoSurface);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                mediaPlayer = MediaPlayer.create(getActivity(), R.raw.apollo_17_stroll);
                mediaPlayer.setDisplay(surfaceHolder);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                    }
                });
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                    }
                });
                mediaPlayer.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        /*btnStop = (Button)v.findViewById(R.id.hellomoon_stopButton);
        btnStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
        });*/

        return v;
    }
}