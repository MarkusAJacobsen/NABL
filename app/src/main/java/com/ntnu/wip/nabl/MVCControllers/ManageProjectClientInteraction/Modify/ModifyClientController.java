package com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction.Modify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.ntnu.wip.nabl.Consts.Poststamp;
import com.ntnu.wip.nabl.MVCControllers.IChangeScreen;
import com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction.Overview.OverviewClientController;
import com.ntnu.wip.nabl.MVCView.ModifyClient.ModifyClientView;
import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.R;


public class ModifyClientController extends Fragment implements IChangeScreen.Fragment {
    private ModifyClientView mvcView;
    private Client model;
    private boolean modelPresent;


    //---------------------------------------------------------------------------------------------
    // Fragment life cycle
    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mvcView = new ModifyClientView(inflater, null);

        getDataFromArguments(getArguments());
        populateView();
        setHasOptionsMenu(true);
        return mvcView.getRootView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    //
    // End of Fragment life cycle
    //---------------------------------------------------------------------------------------------
    // Other fragment overrides
    //

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.save_button, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save){
          updateModel();
        } else {
           switchToOverViewClient();
        }
        return super.onOptionsItemSelected(item);
    }

    //
    // End other fragment overrides
    //---------------------------------------------------------------------------------------------
    // Interfaces
    //

    @Override
    public void transactionManager(Class <? extends android.support.v4.app.Fragment> frag, Bundle args) throws
            IllegalAccessException,
            java.lang.InstantiationException {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment newFragment;

        newFragment = frag.newInstance();
        if(args != null) {
            newFragment.setArguments(args);
        }

        ft.replace(R.id.contentFrame, newFragment);
        ft.commit();
    }

    //
    // End of interfaces
    //---------------------------------------------------------------------------------------------
    // Privates
    //

    private void getDataFromArguments(Bundle args) {
        if(args != null) {
            if(args.containsKey(Poststamp.CLIENT)) {
                final String parcel = args.getString(Poststamp.CLIENT);
                model = new Gson().fromJson(parcel, Client.class);
                modelPresent = true;
            }
        }
    }

    private void populateView() {
        if(!modelPresent){
            return;
        }

        mvcView.setName(model.getName());
        mvcView.setPhone(model.getContactInformation().getPhoneNumber());
        mvcView.setCity(model.getAddress().getCity());
        mvcView.setEmail(model.getContactInformation().getEmail());
        mvcView.setHouseNumber(String.valueOf(model.getAddress().getNumber()));
        mvcView.setZipCode(model.getAddress().getZipcode());
        mvcView.setStreet(model.getAddress().getStreet());
    }

    private void updateModel(){
        if(model == null) {
            model = new Client();
        }

        model.setName(mvcView.getName());
        model.getAddress().setStreet(mvcView.getStreet());
        model.getAddress().setNumber(Integer.parseInt(mvcView.getHouseNumber()));
        model.getAddress().setCity(mvcView.getCity());
        model.getAddress().setZipcode(Integer.parseInt(mvcView.getZipcode()));
        model.getContactInformation().setEmail(mvcView.getEmail());
        model.getContactInformation().setPhoneNumber(Integer.parseInt(mvcView.getPhone()));

        //TODO update firebase

        switchToOverViewClient();

    }


    private Bundle constructArgsFromClient(){
        Bundle args = new Bundle();

        final String parcel = new Gson().toJson(this.model);
        args.putString(Poststamp.CLIENT, parcel);

        return args;
    }

    private void switchToOverViewClient(){
        try {
            transactionManager(OverviewClientController.class, constructArgsFromClient());
        } catch (IllegalAccessException | java.lang.InstantiationException e) {
            e.printStackTrace();
        }
    }

    //
    // End privates
    //---------------------------------------------------------------------------------------------


}
