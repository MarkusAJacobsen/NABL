package com.ntnu.wip.nabl.MVCView.OverviewClient;

import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ntnu.wip.nabl.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewClient implements IOverviewClient{
    private View rootView;

    @BindView(R.id.modify) Button modify;
    @BindView(R.id.delete) Button delete;

    public OverviewClient(LayoutInflater inflater, ViewGroup container){
        rootView = inflater.inflate(R.layout.manage_client, container);
        ButterKnife.bind(this, rootView);
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void setActionBar(ActionBar actionbar) {

    }

    @Override
    public void setActionBarTitle(String title) {

    }
}
