package com.ntnu.wip.nabl.MVCControllers;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.ntnu.wip.nabl.Authentication.FirestoreImpl.FirestoreAuthentication;
import com.ntnu.wip.nabl.MVCView.registerCompanyView.IRegistrationListener;
import com.ntnu.wip.nabl.MVCView.registerCompanyView.RegisterCompanyView;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.R;

public class RegisterCompany extends AppCompatActivity implements IRegistrationListener {

    RegisterCompanyView mvcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvcView = new RegisterCompanyView(getLayoutInflater(), null);
        mvcView.addSubmissionListener(this);
        mvcView.setActionBar(getSupportActionBar());
        mvcView.setActionBarTitle(getString(R.string.makeCompany));

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                ComponentName name = getCallingActivity();
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
