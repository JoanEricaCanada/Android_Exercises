package com.example.joanericacanada.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by joanericacanada on 10/6/15.
 */
public class CrimeListActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment(){
        return new CrimeListFragment();

    }
}
