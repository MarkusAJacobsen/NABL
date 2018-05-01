package com.ntnu.wip.nabl.MVCControllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

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
        mvcView.setActionBar(getSupportActionBar());
        mvcView.setActionBarTitle(getString(R.string.pref_title_system_sync_settings));
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
        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);

        builder.setMessage(getString(R.string.wantTodDeleteString))
        .setPositiveButton(getString(android.R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FireStoreClient client = new FireStoreClient(getApplicationContext());
                client.deleteCompany(company);
            }
        })
        .setNegativeButton(getString(android.R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // I DONT DO SHIT!
            }
        })
                .setTitle(R.string.deleteTitleString)

        .setIcon(android.R.drawable.ic_dialog_alert);

        AlertDialog alert = builder.create();
        alert.show();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivityController.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
