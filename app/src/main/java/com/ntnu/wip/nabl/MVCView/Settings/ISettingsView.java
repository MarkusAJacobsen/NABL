package com.ntnu.wip.nabl.MVCView.Settings;

import android.view.View;

import com.ntnu.wip.nabl.Adapters.CompanyListAdapter;
import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

/**
 * Interface between controller and view
 * Created by klingen on 30.04.18.
 */

public interface ISettingsView extends IAbstractMvcView {
    void addSettingsListener(SettingsListener listener);
    View getRootView();
    void setListAdapter(CompanyListAdapter adapter);
    int getSelectedOption();

    interface SettingsListener {
        void createNewCompany();
        void deleteCompany(int position);
        void companySelectedAsWorkspace(int position);
    }
}
