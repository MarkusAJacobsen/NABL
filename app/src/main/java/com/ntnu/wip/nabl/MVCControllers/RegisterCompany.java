package com.ntnu.wip.nabl.MVCControllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ntnu.wip.nabl.Authentication.FirestoreImpl.FirestoreAuthentication;
import com.ntnu.wip.nabl.MVCView.registerCompanyView.IRegistrationListener;
import com.ntnu.wip.nabl.MVCView.registerCompanyView.RegisterCompanyView;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;

public class RegisterCompany extends AppCompatActivity implements IRegistrationListener {

    RegisterCompanyView mvcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mvcView = new RegisterCompanyView(getLayoutInflater(), null);
        this.mvcView.addSubmissionListener(this);

        setContentView(this.mvcView.getRootView());
    }


    /**
     * What to do when the user clicks the submit-button
     */
    @Override
    public void submission() {
        FireStoreClient client = new FireStoreClient(getApplicationContext());

        Company company = new Company(mvcView.getCompanyName(), mvcView.getOrganisationNumber());
        FirestoreAuthentication authentication = new FirestoreAuthentication();
        company.setOwnerId(authentication.getUId());
        client.newCompany(company);
        finish();
    }
}
