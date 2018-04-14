package com.ntnu.wip.nabl.MVCView.MainActivity;

import android.app.Activity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

/**
 * Created by markusja on 4/10/18.
 */

public interface IMainActivityView extends IAbstractMvcView {
    interface ChangeActivityListener{
        void changeActivity(int position);
    }

    void registerListener(ChangeActivityListener listener);
    void createSideMenu(Activity target);
    void setSideDrawerAdapter(Adapter adapter);
    void syncDrawer();
    boolean checkOptionItemClicked(MenuItem item);
}
