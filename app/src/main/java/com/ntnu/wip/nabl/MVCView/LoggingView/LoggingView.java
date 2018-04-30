package com.ntnu.wip.nabl.MVCView.LoggingView;

import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ntnu.wip.nabl.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A Class that represent the View of Logging activity (LoggingController)
 * Created by a7med on 29.04.18.
 */
public class LoggingView implements ILoggingView {
    private View rootView;
    private LoggingViewListener listener;
    private ActionBar actionBar;

    @BindView(R.id.projectBtn) ToggleButton chooseProjectView;
    @BindView(R.id.clientBtn) ToggleButton chooseClientView;
    @BindView(R.id.listView) FrameLayout showList;
    @BindView(R.id.listViewTitle) TextView title;


    /**
     * Constructor to bind the layout to the view, and configure menu buttons.
     * @param inflater
     * @param container
     */
    public LoggingView(LayoutInflater inflater, ViewGroup container) {
        this.rootView = inflater.inflate(R.layout.logging_layout, container);
        ButterKnife.bind(this, this.rootView);

        configureButtons();
    }

    /**
     * Function to configure both buttons
     */
    private void configureButtons() {
        configureProjectButton();
        configureClientButton();
    }

    /**
     * Function to configure Project button on Clicking
     */
    private void configureProjectButton(){
        this.chooseProjectView.setOnCheckedChangeListener((compoundButton, checked) -> {
            if(checked) {
                this.chooseClientView.setChecked(false);
                if(listener != null) {
                    listener.projectPressed();
                }
            }
        });
    }

    /**
     * Function to configure Client button on Clicking
     */
    private void configureClientButton(){
        this.chooseClientView.setOnCheckedChangeListener((compoundButton, checked) -> {
            if(checked) {
                this.chooseProjectView.setChecked(false);
                if(listener != null) {
                    listener.clientPressed();
                }
            }
        });
    }

    //
    // End of privates functions
    //---------------------------------------------------------------------------------------------
    // Interface Implementation
    //

    @Override
    public View getRootView() {
        return this.rootView;
    }

    @Override
    public void setActionBar(ActionBar actionbar) {
        this.actionBar = actionbar;
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setActionBarTitle(String title) {
        actionBar.setTitle(title);
    }

    @Override
    public void registerLoggingListener(LoggingViewListener listener) {
        this.listener = listener;
    }

    /**
     * Function to add text value to the button (when it's checked, not checked and as default)
     * @param text  text to show on the button
     */
    @Override
    public void setProjectText(String text) {
        this.chooseProjectView.setText(text);
        this.chooseProjectView.setTextOn(text);
        this.chooseProjectView.setTextOff(text);
    }

    /**
     * Function to add text value to the button (when it's checked, not checked and as default)
     * @param text  text to show on the button
     */
    @Override
    public void setClientText(String text) {
        this.chooseClientView.setText(text);
        this.chooseClientView.setTextOn(text);
        this.chooseClientView.setTextOff(text);
    }

    @Override
    public void updateTextViewTitle(String text) {
        this.title.setText(text);
    }
}
