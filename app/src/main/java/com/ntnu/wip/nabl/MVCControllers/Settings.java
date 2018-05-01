package com.ntnu.wip.nabl.MVCControllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ntnu.wip.nabl.Adapters.CompanyListAdapter;
import com.ntnu.wip.nabl.Authentication.FirestoreImpl.FirestoreAuthentication;
import com.ntnu.wip.nabl.Exceptions.CompanyNotFoundException;
import com.ntnu.wip.nabl.MVCView.Settings.ISettingsView;
import com.ntnu.wip.nabl.MVCView.Settings.SettingsView;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.Network.Subscriptions;
import com.ntnu.wip.nabl.Observers.AddOnUpdateListener;
import com.ntnu.wip.nabl.Observers.IObserverSubject;
import com.ntnu.wip.nabl.Observers.Observer;
import com.ntnu.wip.nabl.R;

import java.util.List;

public class Settings extends AppCompatActivity implements ISettingsView.SettingsListener {

    private SettingsView mvcView;
    private List<Company> companies;
    private Company selectedWorkspace;
    private FireStoreClient client;
    private FirestoreAuthentication authentication;

    /**
     * Settings preference file name
     */
    public static final String PREFERENCE_FILE_NAME = "settings_preferences";

    /**
     * Preference field for saved workspace/company
     */
    public static final String SELECTED_WORKSPACE_PREFERENCE_FIELD = "selectedWorkspace";

    /**
     * Preference field name for selected workspace/company position in list
     */
    public static final String SAVED_WORKSPACE_POSITION_PREFERENCE_FIELD = "savedOption";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvcView = new SettingsView(getLayoutInflater(), null);
        mvcView.addSettingsListener(this);
        mvcView.setActionBar(getSupportActionBar());
        mvcView.setActionBarTitle(getString(R.string.pref_title_system_sync_settings));
        createDependencies();
        setCompanyList();
        setContentView(mvcView.getRootView());
    }

    /**
     * Establish needed dependencies for this class to function
     */
    private void createDependencies(){
        client = new FireStoreClient(this);
        authentication = new FirestoreAuthentication();
    }

    @Override
    public void createNewCompany() {
        Intent intent = new Intent(this, RegisterCompany.class);
        startActivity(intent);
    }

    @Override
    public void deleteCompany(int position) {
        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);

        builder.setMessage(getString(R.string.wantTodDeleteString))
        .setPositiveButton(getString(android.R.string.yes), (dialog, which) -> {
            FireStoreClient client = new FireStoreClient(getApplicationContext());
            client.deleteCompany(companies.get(position));
        })
        .setNegativeButton(getString(android.R.string.no), (dialog, which) -> {
            // I DONT DO SHIT!
        })
        .setTitle(R.string.deleteTitleString)
        .setIcon(android.R.drawable.ic_dialog_alert);

        AlertDialog alert = builder.create();
        alert.show();

    }

    /**
     * Company selected in the Company list. This selection will be saved to
     * preferences and represents the current company which a user will
     * log hours/create project/client against
     * @param position int
     */
    @Override
    public void companySelectedAsWorkspace(int position) {
        selectedWorkspace = companies.get(position);
    }

    private void setCompanyList() {
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
                    companies = client.getLastFetchedCompanies();
                    CompanyListAdapter adapter = new CompanyListAdapter(getApplicationContext(), companies, getSavedOption());
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
                saveSelectedWorkspaceCompany();
                Intent intent = new Intent(this, MainActivityController.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Save selected workspace/company to preferences.
     * Do this by marshall the data structure and put it
     * as a string to preferences.
     */
    private void saveSelectedWorkspaceCompany(){
        if(selectedWorkspace == null) {
            return;
        }

        final int savedOption = mvcView.getSelectedOption();
        final String selectedWorkSpaceString = new Gson().toJson(selectedWorkspace);

        SharedPreferences preferences = getSharedPreferences(PREFERENCE_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(SELECTED_WORKSPACE_PREFERENCE_FIELD, selectedWorkSpaceString);
        editor.putInt(SAVED_WORKSPACE_POSITION_PREFERENCE_FIELD, savedOption);
        editor.apply();

        Toast.makeText(getApplicationContext(), getString(R.string.workspaceSat), Toast.LENGTH_SHORT).show();
    }

    /**
     * Fetched selected workspace position from preferences.
     * If not found return default 0 - the first element in any list
     * @return int
     */
    private int getSavedOption(){
        SharedPreferences preferences = getSharedPreferences(PREFERENCE_FILE_NAME, MODE_PRIVATE);

        return preferences.getInt(SAVED_WORKSPACE_POSITION_PREFERENCE_FIELD, 0);
    }
}
