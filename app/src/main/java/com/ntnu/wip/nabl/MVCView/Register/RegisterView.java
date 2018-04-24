package com.ntnu.wip.nabl.MVCView.Register;

import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.ntnu.wip.nabl.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterView implements IRegisterView {
    private View rootView;
    private ToggleListener listener;

    @BindView(R.id.project) ToggleButton mProjectButton;
    @BindView(R.id.client) ToggleButton mClientButton;

    public RegisterView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.register, container);
        ButterKnife.bind(this, rootView);

        configureButtons();
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
    public void registerListener(ToggleListener listener) {
        this.listener = listener;
    }

    @Override
    public void setProjectText(String text) {
        mProjectButton.setText(text);           //First created
        mProjectButton.setTextOff(text);        //Toggle off
        mProjectButton.setTextOn(text);         //Toggle on
    }

    @Override
    public void setClientText(String text) {
        mClientButton.setText(text);
        mClientButton.setTextOn(text);
        mClientButton.setTextOff(text);
    }

    private void configureButtons() {
        configureClientButton();
        configureProjectButton();
    }

    private void configureClientButton(){
        mClientButton.setOnCheckedChangeListener((compoundButton, checked) -> {
            if(checked) {
                mProjectButton.setChecked(false);
                if(listener != null) {
                    listener.clientPressed();
                }
            }
        });
    }

    private void configureProjectButton(){
        mProjectButton.setOnCheckedChangeListener((compoundButton, checked) -> {
            if(checked) {
                mClientButton.setChecked(false);
                if(listener != null) {
                    listener.projectPressed();
                }
            }
        });
    }


}
