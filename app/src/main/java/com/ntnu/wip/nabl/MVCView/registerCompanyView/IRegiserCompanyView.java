package com.ntnu.wip.nabl.MVCView.registerCompanyView;

import android.app.ActionBar;
import android.view.View;
import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

/**
 * Created by klingen on 30.04.18.
 */

public interface IRegiserCompanyView extends IAbstractMvcView {
    String getCompanyName();
    String getOrganisationNumber();

    void setCompanyName(String companyName);
    void setOrganisationNumber(String organisationNumber);

    void addSubmissionListener(IRegistrationListener listener);
    void addActionBar(ActionBar actionBar);
    void setActionBarTitle(String title);

    View getRootView();
}
