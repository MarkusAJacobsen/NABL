package com.ntnu.wip.nabl.MVCControllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.ntnu.wip.nabl.Consts.Poststamp;
import com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction.Overview.OverviewController;
import com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction.Register.RegisterController;
import com.ntnu.wip.nabl.Models.Address;
import com.ntnu.wip.nabl.Models.Category;
import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Models.ContactInformation;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.MVCView.ProjectClientSelector.IProjectClientSelectorView;
import com.ntnu.wip.nabl.MVCView.ProjectClientSelector.ProjectClientSelector;
import com.ntnu.wip.nabl.Models.State;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.Observers.AddOnUpdateListener;
import com.ntnu.wip.nabl.Observers.ClientCollectionObserver;
import com.ntnu.wip.nabl.Observers.ProjectCollectionObserver;
import com.ntnu.wip.nabl.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by markusja on 4/11/18.
 */

public class ProjectClientSelectorController extends AppCompatActivity implements
                                                        IProjectClientSelectorView.ResourceListener,
                                                        IChangeScreen.Activity {
    private ProjectClientSelector mvcView;
    List<Project> projects = new ArrayList<>();
    List<Client> clients = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mvcView = new ProjectClientSelector(getLayoutInflater(), null);
        mvcView.registerResourceListener(this);
        mvcView.setActionBar(getSupportActionBar());
        mvcView.setActionBarTitle(getString(R.string.manageProjectsClientsTitle));
        fetchResourceSelectorItems();

        setContentView(mvcView.getRootView());
    }

    @Override
    public void onResume(){
        super.onResume();
        checkSpinnerSelected();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void resourceSelected(Object pressedObject) {
        Class target = null;
        String poststamp = null;
        final String parcel = new Gson().toJson(pressedObject);
        target = OverviewController.class;

        if (pressedObject instanceof Project){
            poststamp = Poststamp.PROJECT;
        } else if (pressedObject instanceof Client) {
            poststamp = Poststamp.CLIENT;
        }

        if(poststamp != null && parcel != null) {
            Intent intent = new Intent(this, target);
            intent.putExtra(poststamp, parcel);
            createAndLaunchNewActivity(intent);
        }
    }

    @Override
    public void registerPressed() {
        Intent intent = new Intent(this, RegisterController.class);
        createAndLaunchNewActivity(intent);
    }

    @Override
    public void OnSpinnerResourceSelected(int position) {
        switch(position) {
            case 0: /*Projects, fetch projects and update ResourceViewer*/
                projectSelected();
                break;
            case 1: /*Clients, fetch clients and update ResourceViewer*/
                clientSelected();
                break;
            default: break;
        }
    }

    private void projectSelected(){
        FireStoreClient client = new FireStoreClient(getApplicationContext());
        client.getAllProjects();
        ProjectCollectionObserver observer = new ProjectCollectionObserver(client);

        observer.setOnUpdateListener(projects -> {
            this.projects = projects;
            Adapter adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, this.projects);
            mvcView.setResourceViewerAdapter(adapter);
        });
    }

    private void clientSelected(){
        FireStoreClient client = new FireStoreClient(getApplicationContext());
        client.getAllClients();

        new ClientCollectionObserver(client).setOnUpdateListener(clients -> {
            this.clients = clients;
            Adapter adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, this.clients);
            mvcView.setResourceViewerAdapter(adapter);
        });
    }

    private void fetchResourceSelectorItems(){
        final String[] resourceSelectorItems = getResources().getStringArray(R.array.manageProjectAndClientsSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, resourceSelectorItems);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mvcView.setResourceSelectorAdapter(adapter);
    }

    private void checkSpinnerSelected(){
        final int position = mvcView.getSpinnerSelected();
        switch(position) {
            case 0:
                projectSelected();
                break;
            case 1:
                clientSelected();
                break;
            default: break;
        }
    }

    @Override
    public void createAndLaunchNewActivity(Intent intent) {
        startActivity(intent);
    }
}
