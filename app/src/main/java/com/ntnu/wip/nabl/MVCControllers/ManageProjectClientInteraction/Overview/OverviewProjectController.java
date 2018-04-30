package com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction.Overview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.ntnu.wip.nabl.Consts.Poststamp;
import com.ntnu.wip.nabl.MVCControllers.IChangeScreen;
import com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction.Modify.ModifyProjectController;
import com.ntnu.wip.nabl.MVCView.OverviewProject.OverviewProjectView;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Models.State;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.Observers.Observers.ProjectObserver;
import com.ntnu.wip.nabl.R;
import com.ntnu.wip.nabl.MVCControllers.UserAuthentification.Utils;

import java.util.Locale;

public class OverviewProjectController extends Fragment implements IChangeScreen.Fragment{
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.overview_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.modify:
                modifyPressed();
                break;
            case R.id.delete:
                deleteModel();
                break;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }

    //
    // End Fragment lifecycle
    //---------------------------------------------------------------------------------------------
    // IChangeScreen Implementation
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
    // End IChangeScreen
    //---------------------------------------------------------------------------------------------
    // Privates
    //

    private void getDataFromArguments(Bundle args) {
        if(args.containsKey(Poststamp.PROJECT)) {
            final String id = args.getString(Poststamp.PROJECT);
            fetchModel(id);
        }
    }


    private void fetchModel(String id) {
        FireStoreClient client = new FireStoreClient(getContext());
        client.getProject(id);
        new ProjectObserver(client).setOnUpdateListener(obj -> {
            model = (Project) obj;
            modelPresent = true;
            populateView();
        });
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
        mvcView.setProjectName(model.getName());
        mvcView.setState(State.getName(model.getState(), getContext()));
        mvcView.setDescription(model.getDescription());

        if(model.getStart() != null) {
            mvcView.setStart(Utils.getHumanReadableDate(model.getStart()));
        } else {
            mvcView.setStart("-");
        }

        if(model.getEnd() != null) {
            mvcView.setEnd(Utils.getHumanReadableDate(model.getEnd()));
        } else {
            mvcView.setEnd("-");
        }
    }

    private void modifyPressed() {
        final String parcel = new Gson().toJson(model);
        Bundle args = new Bundle();
        args.putString(Poststamp.PROJECT, parcel);


        try {
            transactionManager(ModifyProjectController.class, args);
        } catch (IllegalAccessException | java.lang.InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void deleteModel(){
        FireStoreClient client = new FireStoreClient(getContext());
        client.deleteProject(model);
    }
  
    //
    // End privates
    //---------------------------------------------------------------------------------------------
}
