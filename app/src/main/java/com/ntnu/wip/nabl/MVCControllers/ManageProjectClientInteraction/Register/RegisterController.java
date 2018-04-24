package com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction.Register;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ntnu.wip.nabl.MVCControllers.IChangeScreen;
import com.ntnu.wip.nabl.MVCView.Register.IRegisterView;
import com.ntnu.wip.nabl.MVCView.Register.RegisterView;
import com.ntnu.wip.nabl.R;

public class RegisterController extends AppCompatActivity implements IRegisterView.ToggleListener, IChangeScreen.Fragment {
    private RegisterView mvcView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mvcView = new RegisterView(getLayoutInflater(), null);
        mvcView.registerListener(this);

        configureViewButtons();
        configureActionBar();
        setContentView(mvcView.getRootView());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void projectPressed() {
        try {
            transactionManager(RegisterProjectController.class, null);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clientPressed() {
        try {
            transactionManager(RegisterClientController.class, null);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }



    // IChangeScreen impl
    //

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

    private void configureViewButtons(){
        mvcView.setClientText(getString(R.string.client));
        mvcView.setProjectText(getString(R.string.project));
    }

    private void configureActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
