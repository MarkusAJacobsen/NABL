package com.ntnu.wip.nabl.MVCView.MainActivity;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.ntnu.wip.nabl.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by markusja on 4/10/18.
 */

public class MainActivityView implements IMainActivityView {
    private View rootView;
    private ChangeActivityListener listener;
    private ActionBarDrawerToggle mDrawerToggle;
    private ActionBar mActionBar;
    private ConstraintLayout innerLayout;
    private ListView logEntryListView;

    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.left_drawer) ListView mDrawerList;
    @BindView(R.id.logHours) ImageButton logHoursButton;
    @BindView(R.id.defaultImage) ImageView defaultImage;

    public MainActivityView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.activity_main, container);
        ButterKnife.bind(this, rootView);

        innerLayout = rootView.findViewById(R.id.mainInnerLayout);

        configureLogButton();
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void setActionBar(ActionBar actionbar) {
        this.mActionBar = actionbar;
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public void setActionBarTitle(String title) {
        mActionBar.setTitle(title);
    }

    @Override
    public void registerListener(ChangeActivityListener listener) {
        this.listener = listener;
    }

    @Override
    public void createSideMenu(Activity target) {
        mDrawerToggle = new ActionBarDrawerToggle(
                target,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mActionBar.setCustomView(mDrawerLayout);
    }

    @Override
    public void setSideDrawerAdapter(Adapter adapter) {
        mDrawerList.setAdapter((ArrayAdapter) adapter);

        mDrawerList.setOnItemClickListener((adapterView, view, position, l) -> {
            if(listener != null) {
                listener.changeActivity(position);
            }
        });
    }

    @Override
    public void syncDrawer() {
        mDrawerToggle.syncState();
    }

    @Override
    public boolean checkOptionItemClicked(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item);
    }

    @Override
    public void addUserEntryList(ListView entries) {
        removeDefaultImage();

        final ViewGroup.LayoutParams listViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        innerLayout.addView(entries, listViewParams);

        logEntryListView = entries;
    }

    @Override
    public void removeDefaultImage() {
        innerLayout.removeView(defaultImage);
    }

    @Override
    public void configureLogButton() {
        logHoursButton.setOnClickListener(view -> {
            if(this.listener != null) {
                listener.logHoursPressedInMainView();
            }
        });
    }

    @Override
    public void refreshEntryList() {
        if(logEntryListView != null) {
            logEntryListView.invalidate();
        }
    }
}
