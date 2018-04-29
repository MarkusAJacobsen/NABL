package com.ntnu.wip.nabl.MVCControllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ntnu.wip.nabl.Authentication.FirestoreImpl.FirestoreAuthentication;
import com.ntnu.wip.nabl.Authentication.IAuthentication;
import com.ntnu.wip.nabl.MVCControllers.ManageTimeLogging.LoggingController;
import com.ntnu.wip.nabl.MVCView.MainActivity.MainActivityView;
import com.ntnu.wip.nabl.Observers.AddOnUpdateListener;
import com.ntnu.wip.nabl.Observers.Observers.SignOutObserver;
import com.ntnu.wip.nabl.R;

public class MainActivityController extends AppCompatActivity implements
                                                        MainActivityView.ChangeActivityListener,
                                                        IChangeScreen.Activity {
    MainActivityView mvcView;
    IAuthentication auth = new FirestoreAuthentication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mvcView = new MainActivityView(getLayoutInflater(), null);
        mvcView.setActionBar(getSupportActionBar());
        mvcView.createSideMenu(this);

        fetchSideMenuListItems();
        mvcView.registerListener(this);

        signIn();

        setContentView(mvcView.getRootView());
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
        new SignOutObserver(auth).setOnUpdateListener(obj -> {
           if(auth.getCurrentUser() != null) {
               Toast.makeText(this, "Could not log out", Toast.LENGTH_SHORT).show();
           } else {
               signIn();
           }
        });

        auth.signOut(this);

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
            case 2: break;
            case 3: activityClass = ExportController.class; break;
            case 4: break;
            case 5: signOut(); break;
            default: break;
        }

        if(activityClass != null){
            createAndLaunchNewActivity(activityClass);
        }
    }


    @Override
    public void createAndLaunchNewActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
