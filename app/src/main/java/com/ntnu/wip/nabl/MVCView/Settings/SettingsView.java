package com.ntnu.wip.nabl.MVCView.Settings;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.R;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Creates the view for the settings
 * Created by klingen on 30.04.18.
 */

public class SettingsView implements ISettingsView {
    private View rootView;
    private ActionBar actionBar;


    @BindView(R.id.createCompanySettingsButton)
    Button submitButton;

    @BindView(R.id.companyList)
    ListView companyList;

    List<ISettingsView.SettingsListener> settingsListenerList;

    public SettingsView(LayoutInflater inflater, ViewGroup group) {
        settingsListenerList = new ArrayList<>();
        rootView = inflater.inflate(R.layout.activity_settings, group);
        ButterKnife.bind(this, rootView);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ISettingsView.SettingsListener listener: settingsListenerList) {
                    listener.createNewCompany();
                }
            }
        });
    }

    @Override
    public void addSettingsListener(SettingsListener listener) {
        settingsListenerList.add(listener);
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void setActionBar(ActionBar actionbar) {
        this.actionBar = actionbar;
        this.actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setActionBarTitle(String title) {
        actionBar.setTitle(title);
    }

    @Override
    public void setListAdapter(ListAdapter adapter) {
        this.companyList.setAdapter(adapter);

        this.companyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Company company = (Company) companyList.getAdapter().getItem(position);



                for (ISettingsView.SettingsListener listener: settingsListenerList) {
                    listener.deleteCompany(company);
                }
            }
        });
        // Add some sort of listener or similar
    }
}
