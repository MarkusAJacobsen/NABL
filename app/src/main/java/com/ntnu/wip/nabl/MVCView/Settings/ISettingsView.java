package com.ntnu.wip.nabl.MVCView.Settings;

import android.view.View;
import android.widget.ListAdapter;

<<<<<<< HEAD
import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;
=======
>>>>>>> 16beba26f912a5a10fb4197e1aff9d5e814caecb
import com.ntnu.wip.nabl.Models.Company;

/**
 * Interface between controller and view
 * Created by klingen on 30.04.18.
 */

<<<<<<< HEAD
public interface ISettingsView extends IAbstractMvcView {
=======
public interface ISettingsView {
>>>>>>> 16beba26f912a5a10fb4197e1aff9d5e814caecb
    void addSettingsListener(SettingsListener listener);
    View getRootView();
    void setListAdapter(ListAdapter adapter);
    public interface SettingsListener {
        void createNewCompany();
        void deleteCompany(Company company);
    }
}
