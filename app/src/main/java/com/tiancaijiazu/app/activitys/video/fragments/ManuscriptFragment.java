package com.tiancaijiazu.app.activitys.video.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.user_fragment.OriginalFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManuscriptFragment extends Fragment {


    public ManuscriptFragment() {
        // Required empty public constructor
    }
    public static ManuscriptFragment getInstance() {
        return new ManuscriptFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manuscript, container, false);
    }

}
