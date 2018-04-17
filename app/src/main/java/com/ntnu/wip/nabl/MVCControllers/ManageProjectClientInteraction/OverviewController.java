package com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
        handleIntent(getIntent());
    }


    //
    // End of Activity Life Cycle
    //---------------------------------------------------------------------------------------------

    @Override
    public void transactionManager(Class <? extends Fragment> frag, Bundle args) throws IllegalAccessException, InstantiationException {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment newFragment;

        newFragment = frag.newInstance();
        if(args != null) {
            newFragment.setArguments(args);
        }

        ft.replace(R.id.contentFrame, newFragment);
        ft.commit();
    }

    public void handleIntent(Intent intent){
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
}
