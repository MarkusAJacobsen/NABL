package com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction.Register;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ntnu.wip.nabl.Exceptions.CompanyNotFoundException;
import com.ntnu.wip.nabl.MVCView.projectInput.IProjectInputView;
import com.ntnu.wip.nabl.MVCView.projectInput.ProjectInputView;
import com.ntnu.wip.nabl.Models.Address;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Models.State;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.R;
import com.ntnu.wip.nabl.Utils;

import java.util.Date;

public class RegisterProjectController extends Fragment implements IProjectInputView.ProjectInputListener {
    ProjectInputView mvcView;
    Project newModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mvcView = new ProjectInputView(inflater, null);

        populateViewSpinners();

        mvcView.registerListener(this);
        return mvcView.getRootView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.register_button, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.register:
                if(mvcView.checkValidity()) {
                    registerModel();
                    finishFragment();
                } else {
                    Toast.makeText(getContext(), getString(R.string.inputInvalid), Toast.LENGTH_SHORT).show();
                }
                break;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void registerModel(){
        newModel = new Project();

        getCoreInformation();
        getOrganisationInformation();
        getAddressInformation();

        try {
            saveModel();
        } catch (CompanyNotFoundException e) {
            Toast.makeText(getContext(), (R.string.workspaceNotSat), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveModel() throws CompanyNotFoundException {
        FireStoreClient client = new FireStoreClient(getContext());
        client.writeNewProject(newModel);
    }

    private void getCoreInformation(){
        final String projectId = mvcView.getProjectId();
        final String projectName = mvcView.getProjectName();
        final Date startDate = mvcView.getStart();
        final Date endDate = mvcView.getEnd();
        final String description = mvcView.getDescription();
        final String status = mvcView.getStatus();

        newModel.setProjectId(Integer.parseInt(projectId));
        newModel.setName(projectName);
        newModel.setStart(startDate);
        newModel.setEnd(endDate);
        newModel.setDescription(description);
        newModel.setState(State.getState(status, getContext()));
    }

    private void getOrganisationInformation(){
        final String organisationName = mvcView.getOrganisation();

        //TODO find correct organisation
    }

    private void getAddressInformation(){
        final String street = mvcView.getStreet();
        final String streetNumber = mvcView.getStreetNumber();
        final String zip = mvcView.getZipcode();
        final String city = mvcView.getCity();

        final Address projectAddress = new Address(street, Integer.parseInt(streetNumber), Integer.parseInt(zip), city);
        newModel.setAddress(projectAddress);
    }


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

    private void populateViewSpinners(){
        populateStatusSpinner();
        populateOrganisationSpinner();
    }

    private void populateStatusSpinner(){
        String[] toPopulateWith = new String[State.size()];

        for(int i = 0; i < State.size(); i++){
            toPopulateWith[i] = State.getName(i, getContext());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, toPopulateWith);
        mvcView.populateStatusSpinner(adapter);
    }

    private void populateOrganisationSpinner(){
        //TODO get organisations from firebase
    }

    private void finishFragment(){
       //TODO
    }
}
