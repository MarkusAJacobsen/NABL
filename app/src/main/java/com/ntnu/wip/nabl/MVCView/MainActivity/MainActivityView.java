package com.ntnu.wip.nabl.MVCView.MainActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntnu.wip.nabl.R;

/**
 * Created by markusja on 4/10/18.
 */

public class MainActivityView implements IMainActivityView {
    View rootView;

    public MainActivityView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.activity_main, container);
    }

    @Override
    public View getRootView() {
        return rootView;
    }
}
