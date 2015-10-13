package com.example.joanericacanada.photogallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by joanericacanada on 10/12/15.
 */
public class PhotoGalleryFragment extends Fragment {
    GridView gridView;
    ArrayList<GalleryItem> items;

    private static final String TAG = "PhotoGalleryFragment";
    private int currentPage = 1;
    private int pageFetched = 0;
    private int scrollPosition = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        new FetchItemsTask().execute(currentPage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        gridView = (GridView)v.findViewById(R.id.gridView);
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0 && currentPage == pageFetched) {
                    scrollPosition = firstVisibleItem + visibleItemCount;

                    new FetchItemsTask().execute(currentPage);
                    currentPage += 1;
                    Log.e(TAG, "Scrolled: currentPage: " + currentPage + " - pageFetched: "
                            + pageFetched + " - totalItemCount: " + totalItemCount);
                }
            }
        });

        setupAdapter();

        return v;
    }

    void setupAdapter() {
        if (getActivity() == null || gridView == null) return;

        if (items != null) {
            gridView.setAdapter(new ArrayAdapter<GalleryItem>(getActivity(),
                    android.R.layout.simple_gallery_item, items));
            gridView.setSelection(scrollPosition);
        } else {
            gridView.setAdapter(null);
        }
    }

    private class FetchItemsTask extends AsyncTask<Integer,Void,ArrayList<GalleryItem>> {
        @Override
        protected ArrayList<GalleryItem> doInBackground(Integer... params) {
            return new FlickrFetchr().fetchItems(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<GalleryItem> item) {
            if(items != null)
                items.addAll(item);
            else
                items = item;

            setupAdapter();
            pageFetched += 1;
        }
    }
}