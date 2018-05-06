package com.ntnu.wip.nabl.MVCView.Settings;

import android.view.View;

import com.ntnu.wip.nabl.Adapters.CompanyListAdapter;
import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

/**
 * Interface between controller and view
 * Created by klingen on 30.04.18.
 */

public interface ISettingsView extends IAbstractMvcView {
    /**
     * Register listener
     * @param listener SettingsListener
     */
    void addSettingsListener(SettingsListener listener);

    /**
     * Set Company List adapter
     * @param adapter CompanyListAdapter
     */
    void setListAdapter(CompanyListAdapter adapter);

    /**
     * Get Company List Selected option
     * @return int
     */
    int getSelectedOption();

    /**
     * Methods which this can invoke in Listeners
     */
    interface SettingsListener {

        /**
         * Create new company pressed
         */
        void createNewCompany();

        /**
         * Delete company pressd
         * @param position int
         */
        void deleteCompany(int position);

        /**
         * Company selected as current workspace
         * @param position int
         */
        void companySelectedAsWorkspace(int position);
    }
}
