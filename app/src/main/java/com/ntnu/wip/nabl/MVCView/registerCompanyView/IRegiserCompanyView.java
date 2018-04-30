package com.ntnu.wip.nabl.MVCView.registerCompanyView;

import android.app.ActionBar;

/**
 * Created by klingen on 30.04.18.
 */

public interface IRegiserCompanyView {
    String getCompanyName();
    String getOrganisationNumber();

    void setCompanyName(String companyName);
    void setOrganisationNumber(String organisationNumber);

    void addSubmissionListener(IRegistrationListener listener);
    void addActionBar(ActionBar actionBar);
    void setActionBarTitle(String title);
}
