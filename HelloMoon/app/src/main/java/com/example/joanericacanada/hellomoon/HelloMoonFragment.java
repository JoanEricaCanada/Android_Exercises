package com.example.joanericacanada.hellomoon;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by joanericacanada on 10/7/15.
 */
public class HelloMoonFragment extends Fragment {

    private AudioPlayer mPlayer = new AudioPlayer();
    private Button btnPlay;
    private Button btnStop;
    private Button btnPause;
    private boolean isPaused;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hello_moon, parent, false);

        isPaused = false;

        btnPlay = (Button)v.findViewById(R.id.hellomoon_playButton);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isPaused) {
                    mPlayer.pause();
                    btnPlay.setText(R.string.hellomoon_play);
                    isPaused = false;
                }else {
                    mPlayer.play(getActivity());
                    btnPlay.setText(R.string.hellomoon_pause);
                    isPaused = true;
                }
            }
        });

        btnStop = (Button)v.findViewById(R.id.hellomoon_stopButton);
        btnStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isPaused = false;
                mPlayer.stop();
            }
        });

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
    }

}

