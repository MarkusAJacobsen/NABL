package com.ntnu.wip.nabl.MVCView.OverviewProject;

import android.support.v7.app.ActionBar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ntnu.wip.nabl.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewProjectView implements IOverviewProjectView {
    private View rootView;
    @BindView(R.id.projectId) TextView mProjectId;
    @BindView(R.id.address) TextView mAddress;

    public OverviewProjectView(LayoutInflater inflater, ViewGroup container){
        rootView = inflater.inflate(R.layout.manage_project, container);
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

    @Override
    public void setProjectId(String projectId) {
        mProjectId.setText(projectId);
    }

    @Override
    public void setAddress(String address) {
        mAddress.setText(address);
    }
}
