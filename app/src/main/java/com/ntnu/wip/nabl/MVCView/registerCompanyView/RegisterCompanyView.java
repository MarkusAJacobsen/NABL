package com.ntnu.wip.nabl.MVCView.registerCompanyView;

import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ntnu.wip.nabl.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * controls the view for adding a new company
 * Created by klingen on 30.04.18.
 */

public class RegisterCompanyView implements IRegisterCompanyView {

    private View rootView;
    private ActionBar actionBar;

    @BindView(R.id.companyName)
    EditText companyName;

    @BindView(R.id.organisationNumber)
    EditText organistationNumber;

    @BindView(R.id.submitCompany)
    Button submissionButton;

    private List<IRegistrationListener> sumbissionListeners;

    public RegisterCompanyView(LayoutInflater inflater, ViewGroup group) {
        sumbissionListeners = new ArrayList<>();
        rootView = inflater.inflate(R.layout.activity_register_company, group);
        ButterKnife.bind(this, rootView);

        // Add a listener that triggers the listseners
        submissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (IRegistrationListener listener: sumbissionListeners) {
                    listener.submission();
                }
            }
        });
    }

    @Override
    public String getCompanyName() {
        return companyName.getText().toString();
    }

    @Override
    public String getOrganisationNumber() {
        return organistationNumber.getText().toString();
    }

    @Override
    public void setCompanyName(String companyName) {
        this.companyName.setText(companyName);
    }

    @Override
    public void setOrganisationNumber(String organisationNumber) {
        this.organistationNumber.setText(organisationNumber);
    }

    @Override
    public void addSubmissionListener(IRegistrationListener listener) {
        sumbissionListeners.add(listener);
    }

    @Override
    public void setActionBarTitle(String title) {
        this.actionBar.setTitle(title);
    }

    @Override
    public View getRootView() {
        return this.rootView;
    }

    @Override
    public void setActionBar(android.support.v7.app.ActionBar actionbar) {
        this.actionBar = actionbar;
        this.actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
