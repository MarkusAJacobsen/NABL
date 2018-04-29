package com.ntnu.wip.nabl.MVCControllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.ntnu.wip.nabl.MVCControllers.ManageTimeLogging.LoggingController;
import com.ntnu.wip.nabl.MVCView.MainActivity.MainActivityView;
import com.ntnu.wip.nabl.R;

public class MainActivityController extends AppCompatActivity implements
                                                        MainActivityView.ChangeActivityListener,
                                                        IChangeScreen.Activity {
    MainActivityView mvcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mvcView = new MainActivityView(getLayoutInflater(), null);
        mvcView.setActionBar(getSupportActionBar());
        mvcView.createSideMenu(this);

        fetchSideMenuListItems();
        mvcView.registerListener(this);

        setContentView(mvcView.getRootView());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mvcView.syncDrawer();
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
