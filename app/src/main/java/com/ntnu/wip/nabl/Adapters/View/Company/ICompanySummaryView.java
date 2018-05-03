package com.ntnu.wip.nabl.Adapters.View.Company;

import android.view.View;

import com.ntnu.wip.nabl.Adapters.ClientProjectAdapter;
import com.ntnu.wip.nabl.Adapters.CompanyAdapter;

public interface ICompanySummaryView {
    void setAdapter(ClientProjectAdapter adapter);
    void setCompanyName(String name);
    View getRootView();
}
