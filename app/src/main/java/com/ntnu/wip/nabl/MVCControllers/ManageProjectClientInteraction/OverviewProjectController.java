package com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.ntnu.wip.nabl.Consts.Poststamp;
import com.ntnu.wip.nabl.MVCView.OverviewProject.OverviewProjectView;
import com.ntnu.wip.nabl.Models.Project;

import java.util.Locale;

public class OverviewProjectController extends Fragment {
    private OverviewProjectView mvcView;
    private Project model;
    private boolean modelPresent;

    //---------------------------------------------------------------------------------------------
    // Fragment lifecycle
    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mvcView = new OverviewProjectView(inflater, null);

        getDataFromArguments(getArguments());
        populateView();

        return(mvcView.getRootView());
    }

    //
    // End Fragment lifecycle
    //---------------------------------------------------------------------------------------------
    // Privates
    //

    private void getDataFromArguments(Bundle args) {
        if(args.containsKey(Poststamp.PROJECT)) {
            final String parcel = args.getString(Poststamp.PROJECT);
            model = new Gson().fromJson(parcel, Project.class);
            modelPresent = true;
        }
    }

    private void populateView(){
        if(!modelPresent) {
            return;
        }

        mvcView.setProjectId(String.valueOf(model.getProjectId()));

        final String address = String.format(Locale.getDefault(), "%s %s, %s %s",
                model.getAddress().getStreet(),
                model.getAddress().getNumber(),
                model.getAddress().getZipcode(),
                model.getAddress().getCity());

        mvcView.setAddress(address);
    }

    //
    // End privates
    //---------------------------------------------------------------------------------------------
}
