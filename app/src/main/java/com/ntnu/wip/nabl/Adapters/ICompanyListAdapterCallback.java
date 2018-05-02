package com.ntnu.wip.nabl.Adapters;

/**
 * Interface for implementing CompanyList Item buttons
 */
public interface ICompanyListAdapterCallback {
    void deletePressed(int position);
    void selectedWorkspace(int position);
}
