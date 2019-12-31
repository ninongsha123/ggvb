package com.tiancaijiazu.app.activitys.video.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiancaijiazu.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogueFragment extends Fragment {


    public CatalogueFragment() {
        // Required empty public constructor
    }
    public static CatalogueFragment getInstance() {
        return new CatalogueFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalogue, container, false);
    }

}
