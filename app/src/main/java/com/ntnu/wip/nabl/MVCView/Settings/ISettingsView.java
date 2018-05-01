package com.ntnu.wip.nabl.MVCView.Settings;

import android.view.View;
import android.widget.ListAdapter;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;
import com.ntnu.wip.nabl.Models.Company;

/**
 * Interface between controller and view
 * Created by klingen on 30.04.18.
 */

public interface ISettingsView extends IAbstractMvcView {
    void addSettingsListener(SettingsListener listener);
    View getRootView();
    void setListAdapter(ListAdapter adapter);
    public interface SettingsListener {
        void createNewCompany();
        void deleteCompany(Company company);
    }
}
