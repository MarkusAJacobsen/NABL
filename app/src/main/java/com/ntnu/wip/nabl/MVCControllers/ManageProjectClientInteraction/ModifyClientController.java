package com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.ntnu.wip.nabl.Consts.Poststamp;
import com.ntnu.wip.nabl.MVCView.ModifyClient.ModifyClientView;
import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.R;

public class ModifyClientController extends Fragment {
    private ModifyClientView mvcView;
    private Client model;


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
        //TODO save entry
        return true;
    }

    //
    // End other fragment overrides
    //---------------------------------------------------------------------------------------------
    // Privates
    //

    private void getDataFromArguments(Bundle args) {
        if(args.containsKey(Poststamp.CLIENT)) {
            final String parcel = args.getString(Poststamp.CLIENT);
            model = new Gson().fromJson(parcel, Client.class);
        }
    }

    private void populateView() {
        mvcView.setName(model.getName());
        mvcView.setPhone(model.getContactInformation().getPhoneNumber());
        mvcView.setCity(model.getAddress().getCity());
        mvcView.setEmail(model.getContactInformation().getEmail());
        mvcView.setHouseNumber(String.valueOf(model.getAddress().getNumber()));
        mvcView.setZipCode(model.getAddress().getZipcode());
        mvcView.setStreet(model.getAddress().getStreet());
    }

    //
    // End privates
    //---------------------------------------------------------------------------------------------


}
