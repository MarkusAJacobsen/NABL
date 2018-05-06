package com.ntnu.wip.nabl.MVCView.registerCompanyView;

import android.app.ActionBar;
import android.view.View;
import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

/**
 * Created by klingen on 30.04.18.
 */

public interface IRegisterCompanyView extends IAbstractMvcView {
    /**
     * Methods which this can invoke in Listeners
     */
    interface IRegistrationListener {
        /**
         * Submit pressed
         */
        void submission();
    }

    /**
     * Get text from field 'Name'
     * @return String
     */
    String getCompanyName();

    /**
     * Get text from field 'Organization number'
     * @return String
     */
    String getOrganisationNumber();

    /**
     * Set text in field 'Company name'
     * @param companyName String
     */
    void setCompanyName(String companyName);

    /**
     * Set text in field 'Organization number'
     * @param organisationNumber String
     */
    void setOrganisationNumber(String organisationNumber);

    /**
     * Register listener
     * @param listener IRegistrationListener
     */
    void addSubmissionListener(IRegistrationListener listener);
}
