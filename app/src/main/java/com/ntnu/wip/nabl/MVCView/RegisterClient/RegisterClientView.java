package com.ntnu.wip.nabl.MVCView.RegisterClient;

import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntnu.wip.nabl.R;

public class RegisterClientView implements IRegisterClientView {
    private View rootView;

    public RegisterClientView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.client_input, container);

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
