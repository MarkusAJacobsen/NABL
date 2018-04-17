package com.ntnu.wip.nabl.Controllers.ManageProjectClientInteraction;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ntnu.wip.nabl.Consts.Poststamp;
import com.ntnu.wip.nabl.Controllers.IChangeScreen;
import com.ntnu.wip.nabl.DataModels.Client;
import com.ntnu.wip.nabl.MVCView.ModifyClient.ModifyClientView;
import com.ntnu.wip.nabl.MVCView.OverviewClient.IOverviewClientView;
import com.ntnu.wip.nabl.MVCView.OverviewClient.OverviewClientView;

import java.lang.reflect.Type;
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

        getDataFromArguments(getArguments());

        populateView();

        return mvcView.getRootView();
    }


    //
    // End of fragment Lifecycle
    //---------------------------------------------------------------------------------------------
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
