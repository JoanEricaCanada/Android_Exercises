package com.example.joanericacanada.photogallery;

import android.support.v4.app.Fragment;

/**
 * Created by joanericacanada on 10/14/15.
 */
public class PhotoPageActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment(){
        return new PhotoPageFragment();
    }
}
