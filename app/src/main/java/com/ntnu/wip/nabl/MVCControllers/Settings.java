package com.ntnu.wip.nabl.MVCControllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.ntnu.wip.nabl.Authentication.FirestoreImpl.FirestoreAuthentication;
import com.ntnu.wip.nabl.MVCView.Settings.ISettingsView;
import com.ntnu.wip.nabl.MVCView.Settings.SettingsView;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.Network.Subscriptions;
import com.ntnu.wip.nabl.Observers.AddOnUpdateListener;
import com.ntnu.wip.nabl.Observers.IObserverSubject;
import com.ntnu.wip.nabl.Observers.Observer;
import com.ntnu.wip.nabl.R;

public class Settings extends AppCompatActivity implements ISettingsView.SettingsListener {

    private SettingsView mvcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvcView = new SettingsView(getLayoutInflater(), null);
        mvcView.addSettingsListener(this);
        setCompanyList();
        setContentView(mvcView.getRootView());
    }

    @Override
    public void createNewCompany() {
        Intent intent = new Intent(this, RegisterCompany.class);
        startActivity(intent);
    }

    @Override
    public void deleteCompany(Company company) {
        FireStoreClient client = new FireStoreClient(getApplicationContext());
        client.deleteCompany(company);
    }

    private void setCompanyList() {
        FireStoreClient client = new FireStoreClient(this);
        FirestoreAuthentication authentication = new FirestoreAuthentication();

        client.attach(new Observer() {
            @Override
            public void setSubject(IObserverSubject subject) {

            }

            @Override
            public void update() {
            }

            @Override
            public void update(Subscriptions sub) {
                if (sub == Subscriptions.COMPANIES) {
                    ArrayAdapter<Company> adapter = new ArrayAdapter<Company>(getApplicationContext(), android.R.layout.simple_list_item_activated_1);
                    adapter.addAll(client.getLastFetchedCompanies());
                    adapter.notifyDataSetChanged();
                    mvcView.setListAdapter(adapter);
                }
            }

            @Override
            public void setOnUpdateListener(AddOnUpdateListener listener) {

            }
        });


        client.getUserCompanies(authentication.getUId());



    }
}
