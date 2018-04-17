package com.ntnu.wip.nabl.MVCView.ModifyClient;

import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ntnu.wip.nabl.R;

import butterknife.BindView;

public class ModifyClientView implements IModifyClientView {
    private View rootView;
    @BindView(R.id.modifyName) TextView mName;
    @BindView(R.id.modifyPhone) TextView mPhone;
    @BindView(R.id.modifyEmail) TextView mEmail;
    @BindView(R.id.modifyAddress) TextView mAddress;

    public ModifyClientView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.modify_client, container);

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
