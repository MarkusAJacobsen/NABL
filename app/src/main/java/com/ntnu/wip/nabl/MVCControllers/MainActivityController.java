package com.ntnu.wip.nabl.MVCControllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ntnu.wip.nabl.Adapters.LogEntryAdapter1;
import com.ntnu.wip.nabl.Authentication.FirestoreImpl.FirestoreAuthentication;
import com.ntnu.wip.nabl.Authentication.IAuthentication;
import com.ntnu.wip.nabl.MVCControllers.ManageTimeLogging.LoggingController;
import com.ntnu.wip.nabl.MVCView.MainActivity.MainActivityView;
import com.ntnu.wip.nabl.Models.WorkDay;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.Network.IClient;
import com.ntnu.wip.nabl.Observers.Observer;
import com.ntnu.wip.nabl.Observers.Observers.ObserverFactory;
import com.ntnu.wip.nabl.Observers.Observers.SignOutObserver;
import com.ntnu.wip.nabl.R;
import com.ntnu.wip.nabl.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivityController extends AppCompatActivity implements
                                                        MainActivityView.ChangeActivityListener,
                                                        IChangeScreen.Activity {
    private String uid;
    private MainActivityView mvcView;
    private IAuthentication auth = new FirestoreAuthentication();
    private List<WorkDay> userLogEntries;
    private boolean userLogEntriesPresent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mvcView = new MainActivityView(getLayoutInflater(), null);
        mvcView.setActionBar(getSupportActionBar());
        mvcView.createSideMenu(this);

        fetchSideMenuListItems();
        mvcView.registerListener(this);

        signIn();
        uid = auth.getUId();
        fetchUserLogEntries();
        setContentView(mvcView.getRootView());
    }

    /**
     * When a returning from paused foreground activity
     */
    @Override
    protected void onResume() {
        super.onResume();

        mvcView.refreshEntryList();
    }

    /**
     * Create activity signin
     */
    private void signIn() {
        if(auth.getCurrentUser() == null) {
            startActivityForResult(auth.signIn(), auth.getResultCode());
        }
    }

    /**
     * Sign out currently logged in user
     */
    private void signOut(){
        Observer observer = ObserverFactory.create(ObserverFactory.SIGN_OUT);
        observer.setSubject(auth);
        observer.setOnUpdateListener(obj -> {
           if(auth.getCurrentUser() != null) {
               Toast.makeText(this, "Could not log out", Toast.LENGTH_SHORT).show();
           } else {
               clearSettingsPreferences();
               signIn();
           }
        });
        auth.signOut(this);
    }

    private void fetchUserLogEntries() {
        IClient client = new FireStoreClient(this);
        client.getLogEntriesByUserId(uid);

        Observer observer = ObserverFactory.create(ObserverFactory.USER_LOG_ENTRIES_INF);
        observer.setSubject(client);
        observer.setOnUpdateListener(this::handleUserEntries);
    }

    private void handleUserEntries(Object obj){
        userLogEntries = (List<WorkDay>) obj;
        sortWorkDays(userLogEntries);

        ListView entries = new ListView(this);
        Adapter entryAdapter = new LogEntryAdapter1(userLogEntries, this);
        entries.setAdapter((LogEntryAdapter1) entryAdapter);
        mvcView.addUserEntryList(entries);
    }

    private void sortWorkDays(List<WorkDay> resources) {
        Collections.sort(resources, Comparator.comparing(WorkDay::getStartTime).reversed());
    }

    /**
     * Since user specific settings are sat, remove them at logout.
     * TODO store settings in cloud instead, this way we can load settings
     */
    private void clearSettingsPreferences(){
        this.getSharedPreferences(Settings.PREFERENCE_FILE_NAME, MODE_PRIVATE).edit().clear().apply();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mvcView.syncDrawer();
    }

    /**
     * Callback for intent with expected results
     * @param requestCode Int
     * @param resultCode Int
     * @param data Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /**
         * Case Login return
         */
        if (requestCode == auth.getResultCode()) {
            if (resultCode != RESULT_OK) {
                Toast.makeText(this, "Could not login", Toast.LENGTH_SHORT).show();
                signIn();
            } else {
                Toast.makeText(this, "Hello " + auth.getFullName(), Toast.LENGTH_SHORT).show();
                uid = auth.getUId();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mvcView.checkOptionItemClicked(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    private void fetchSideMenuListItems(){
        final String[] sideMenuItems = getResources().getStringArray(R.array.sideDrawerMenuItems);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sideMenuItems);
        mvcView.setSideDrawerAdapter(adapter);
    }

    @Override
    public void changeActivity(int position) {
        Class activityClass = null;
        switch(position){
            case 0: activityClass = LoggingController.class; break;
            case 1: activityClass = ProjectClientSelectorController.class; break;
            case 2: activityClass = Summary.class; break;
            case 3: activityClass = ExportController.class; break;
            case 4: activityClass = Settings.class; break;
            case 5: signOut(); break;
            default: break;
        }

        if (activityClass != null){
            createAndLaunchNewActivity(activityClass);
        }
    }

    @Override
    public void logHoursPressedInMainView() {
        Intent intent = new Intent(this, LoggingController.class);
        startActivity(intent);
    }


    @Override
    public void createAndLaunchNewActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
