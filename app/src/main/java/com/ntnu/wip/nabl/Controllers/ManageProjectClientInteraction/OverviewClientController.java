package com.ntnu.wip.nabl.Controllers.ManageProjectClientInteraction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ntnu.wip.nabl.Consts.Poststamp;
import com.ntnu.wip.nabl.DataModels.Client;
import com.ntnu.wip.nabl.MVCView.OverviewClient.IOverviewClientView;
import com.ntnu.wip.nabl.MVCView.OverviewClient.OverviewClientView;

import java.lang.reflect.Type;

public class OverviewClientController extends AppCompatActivity implements IOverviewClientView.ButtonListener {
    private OverviewClientView mvcView;
    private Client model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mvcView = new OverviewClientView(getLayoutInflater(), null);

        getDataFromIntent();

        setContentView(mvcView.getRootView());
    }

    @Override
    public void modifyPressed() {

    }

    @Override
    public void deletePressed() {

    }

    private void getDataFromIntent(){
        final Intent passedIntent = getIntent();

        if(passedIntent.hasExtra(Poststamp.CLIENT)) {
            final String parcel = passedIntent.getStringExtra(Poststamp.CLIENT);
            final Type classIdentifier = new TypeToken<Client>(){}.getType();
            this.model = new Gson().fromJson(parcel,classIdentifier);
        }
    }
}
