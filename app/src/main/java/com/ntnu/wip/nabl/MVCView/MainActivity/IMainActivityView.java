package com.ntnu.wip.nabl.MVCView.MainActivity;

import android.app.Activity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

/**
 * Created by markusja on 4/10/18.
 */

public interface IMainActivityView extends IAbstractMvcView {
    /**
     * Methods which this can invoke in Listeners
     */
    interface ChangeActivityListener{
        /**
         * Side drawer option selected
         * @param position position
         */
        void changeActivity(int position);

        /**
         * Pluss button pressed
         */
        void logHoursPressedInMainView();
    }

    /**
     * Register listener
     * @param listener ChangeActivityListener
     */
    void registerListener(ChangeActivityListener listener);

    /**
     * Create side menu
     * @param target Activity
     */
    void createSideMenu(Activity target);

    /**
     * Set side menu list adapter
     * @param adapter Adapter
     */
    void setSideDrawerAdapter(Adapter adapter);

    /**
     * Sync side menu components
     */
    void syncDrawer();

    boolean checkOptionItemClicked(MenuItem item);
}
