package com.ntnu.wip.nabl.Controllers;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.ntnu.wip.nabl.MVCView.MainActivity.MainActivityView;
import com.ntnu.wip.nabl.R;

public class MainActivityController extends AppCompatActivity {
    MainActivityView mvcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mvcView = new MainActivityView(getLayoutInflater(), null);
        mvcView.setActionBar(getSupportActionBar());
        mvcView.createSideMenu(this);

        fetchSideMenuListItems();

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
}
