package com.ntnu.wip.nabl.MVCControllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ntnu.wip.nabl.MVCView.registerCompanyView.IRegistrationListener;
import com.ntnu.wip.nabl.MVCView.registerCompanyView.RegisterCompanyView;
import com.ntnu.wip.nabl.R;

public class RegisterCompany extends AppCompatActivity implements IRegistrationListener {

    RegisterCompanyView mvcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mvcView = new RegisterCompanyView(getLayoutInflater(), null);
        this.mvcView.addSubmissionListener(this);

        this.mvcView.addActionBar(getActionBar());
        this.mvcView.setActionBarTitle(getString(R.string.createCompanyTitle));

        setContentView(R.layout.activity_register_company);
    }



    /**
     * What to do when the user clicks the submit-button
     */
    @Override
    public void submission() {
        Toast.makeText(this, "Something", Toast.LENGTH_SHORT);
    }
}
