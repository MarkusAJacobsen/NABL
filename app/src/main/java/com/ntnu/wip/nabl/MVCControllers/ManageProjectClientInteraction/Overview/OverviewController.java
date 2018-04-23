package com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction.Overview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ntnu.wip.nabl.Consts.Poststamp;
import com.ntnu.wip.nabl.MVCControllers.IChangeScreen;
import com.ntnu.wip.nabl.R;

public class OverviewController extends AppCompatActivity implements IChangeScreen.Fragment {
    //---------------------------------------------------------------------------------------------
    // Activity Life Cycle
    //

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_content_frame);
        configureActionBar();
        handleIntent(getIntent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void transactionManager(Class <? extends android.support.v4.app.Fragment> frag, Bundle args) throws
                                                                IllegalAccessException,
                                                                java.lang.InstantiationException {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment newFragment;

        newFragment = frag.newInstance();
        if(args != null) {
            newFragment.setArguments(args);
        }

        ft.replace(R.id.contentFrame, newFragment);
        ft.commit();
    }

    //
    // End IChangeScreen
    //---------------------------------------------------------------------------------------------
    // Privates
    //

    private void handleIntent(Intent intent){
        String parcel = null;
        Class target = null;
        String stamp = null;

        if(intent.hasExtra(Poststamp.CLIENT)) {
            stamp = Poststamp.CLIENT;
            parcel = intent.getStringExtra(stamp);
            target = OverviewClientController.class;
        } else if(intent.hasExtra(Poststamp.PROJECT)){
            stamp = Poststamp.PROJECT;
            parcel = intent.getStringExtra(stamp);
            target = OverviewProjectController.class;
        }

        Bundle args = new Bundle();
        args.putString(stamp, parcel);

        try {
            transactionManager(target, args);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void configureActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //
    // End Privates
    //---------------------------------------------------------------------------------------------
}
