package com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction.Modify;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ntnu.wip.nabl.Consts.Poststamp;
import com.ntnu.wip.nabl.MVCControllers.IChangeScreen;
import com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction.Overview.OverviewProjectController;
import com.ntnu.wip.nabl.MVCView.projectInput.IProjectInputView;
import com.ntnu.wip.nabl.MVCView.projectInput.ProjectInputView;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Models.State;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.R;
import com.ntnu.wip.nabl.Utils;

public class ModifyProjectController extends Fragment implements IChangeScreen.Fragment, IProjectInputView.ProjectInputListener {
    private ProjectInputView mvcView;
    private Project model;
    private boolean modelPresent;


    //---------------------------------------------------------------------------------------------
    // Fragment life cycle
    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mvcView = new ProjectInputView(inflater, null);
        mvcView.registerListener(this);

        getDataFromArguments(getArguments());

        populateStatusSpinner();
        populateView();

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
            if(mvcView.checkValidity()) {
                updateModel();
            }
        } else {
            switchToOverViewProject();
        }
        return super.onOptionsItemSelected(item);
    }

    //
    // End other fragment overrides
    //---------------------------------------------------------------------------------------------
    // Interface impl
    //

    @Override
    public void dateFieldPressed() {
        DatePickerDialog dateDialog = new DatePickerDialog(getContext());
        mvcView.setDateDialog(dateDialog);
    }

    @Override
    public void invalidDateSupplied() {
        Toast.makeText(getContext(), "Invalid date", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getEditWarningDrawable() {
        final Drawable icon = getResources().getDrawable(R.drawable.ic_error_black_18dp);
        mvcView.setWarningIcon(icon);
    }

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
    // End interface impl
    //---------------------------------------------------------------------------------------------
    // Privates
    //

    private void getDataFromArguments(Bundle args) {
        if(args != null) {
            if(args.containsKey(Poststamp.PROJECT)) {
                final String parcel = args.getString(Poststamp.PROJECT);
                model = new Gson().fromJson(parcel, Project.class);
                modelPresent = true;
            }
        }
    }

    private void populateView() {
        if (!modelPresent) {
            return;
        }

        mvcView.setProjectId(String.valueOf(model.getProjectId()));
        mvcView.setProjectName(model.getName());
        mvcView.setStreet(model.getAddress().getStreet());
        mvcView.setStreetNumber(String.valueOf(model.getAddress().getNumber()));
        mvcView.setZipcode(String.valueOf(model.getAddress().getZipcode()));
        mvcView.setCity(model.getAddress().getCity());

        if(model.getStart() != null) {
            mvcView.setStart(Utils.getHumanReadableDate(model.getStart()));
        }

        if(model.getEnd() != null) {
            mvcView.setEnd(Utils.getHumanReadableDate(model.getEnd()));
        }

        mvcView.setDescription(model.getDescription());
        mvcView.setStatus(State.getName(model.getState(), getContext()));
    }

    private void updateModel(){
        if(model == null) {
            model = new Project();
        }

        model.setProjectId(Integer.parseInt(mvcView.getProjectId()));
        model.setName(mvcView.getProjectName());
        model.getAddress().setStreet(mvcView.getStreet());
        model.getAddress().setNumber(Integer.parseInt(mvcView.getStreetNumber()));
        model.getAddress().setCity(mvcView.getCity());
        model.getAddress().setZipcode(Integer.parseInt(mvcView.getZipcode()));
        model.setState(State.getState(mvcView.getStatus(), getContext()));
        model.setStart(mvcView.getStart());
        model.setEnd(mvcView.getEnd());
        model.setDescription(mvcView.getDescription());
        // TODO model.setCompany(mvcView.getOrganisation());

        saveModel();
        switchToOverViewProject();
    }

    private void saveModel(){
        FireStoreClient client = new FireStoreClient(getContext());
        client.updateProject(model);
    }

    private Bundle constructArgsFromProject(){
        Bundle args = new Bundle();

        final String id = model.getId();
        args.putString(Poststamp.PROJECT, id);

        return args;
    }

    private void switchToOverViewProject(){
        try {
            transactionManager(OverviewProjectController.class, constructArgsFromProject());
        } catch (IllegalAccessException | java.lang.InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void populateStatusSpinner(){
        String[] toPopulateWith = new String[State.size()];

        for(int i = 0; i < State.size(); i++){
            toPopulateWith[i] = State.getName(i, getContext());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, toPopulateWith);
        mvcView.populateStatusSpinner(adapter);
    }


}
