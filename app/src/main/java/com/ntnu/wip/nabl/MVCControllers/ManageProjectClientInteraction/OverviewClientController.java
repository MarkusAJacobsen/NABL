package com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.ntnu.wip.nabl.Consts.Poststamp;
import com.ntnu.wip.nabl.MVCControllers.IChangeScreen;
import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.MVCView.OverviewClient.IOverviewClientView;
import com.ntnu.wip.nabl.MVCView.OverviewClient.OverviewClientView;
import com.ntnu.wip.nabl.R;

import java.util.Locale;

public class OverviewClientController extends Fragment implements IOverviewClientView.ButtonListener,
                                                                    IChangeScreen.Fragment {
    private OverviewClientView mvcView;
    private Client model;
    private boolean modelPresent = false;

    //---------------------------------------------------------------------------------------------
    // Fragment lifecycle
    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mvcView = new OverviewClientView(inflater, null);
        mvcView.registerListener(this);

        getDataFromArguments(getArguments());

        populateView();

        return mvcView.getRootView();
    }


    //
    // End of fragment Lifecycle
    //---------------------------------------------------------------------------------------------
    // Listener Implementations
    //

    @Override
    public void modifyPressed() {
        final String parcel = new Gson().toJson(model);
        Bundle args = new Bundle();
        args.putString(Poststamp.CLIENT, parcel);


        try {
            transactionManager(ModifyClientController.class, args);
        } catch (IllegalAccessException | java.lang.InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePressed() {

    }

    //
    // End Listener Implementation
    //---------------------------------------------------------------------------------------------
    // IChangeScreen Implementation
    //

    @Override
    public void transactionManager(Class <? extends Fragment> frag, Bundle args) throws
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


    private void getDataFromArguments(Bundle args){
        if(args.containsKey(Poststamp.CLIENT)) {
            final String parcel = args.getString(Poststamp.CLIENT);
            model = new Gson().fromJson(parcel, Client.class);
            modelPresent = true;
        }
    }

    private void populateView(){
        if(!modelPresent) {
            return;
        }

        mvcView.setName(String.format("%s %s", model.getGivenName(), model.getSurname()));
        mvcView.setEmail(model.getContactInformation().getEmail());
        mvcView.setPhone(String.valueOf(model.getContactInformation().getPhoneNumber()));

        final String address = String.format(Locale.getDefault(), "%s %s, %s %s",
                model.getAddress().getStreet(),
                model.getAddress().getNumber(),
                model.getAddress().getZipcode(),
                model.getAddress().getCity());
        mvcView.setAddress(address);
    }

}
