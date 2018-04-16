package com.ntnu.wip.nabl.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.ntnu.wip.nabl.Consts.Poststamp;
import com.ntnu.wip.nabl.DataModels.Address;
import com.ntnu.wip.nabl.DataModels.Client;
import com.ntnu.wip.nabl.DataModels.ContactInformation;
import com.ntnu.wip.nabl.DataModels.Project;
import com.ntnu.wip.nabl.MVCView.ManageProjectClient.IManageProjectClientView;
import com.ntnu.wip.nabl.MVCView.ManageProjectClient.ManageProjectClientView;
import com.ntnu.wip.nabl.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by markusja on 4/11/18.
 */

public class ManageProjectsClientsController extends AppCompatActivity implements
                                        IManageProjectClientView.ResourceViewerListener,
                                        IManageProjectClientView.ResourceSelectorListener,
                                        IChangeScreen.Activity {
    private ManageProjectClientView mvcView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mvcView = new ManageProjectClientView(getLayoutInflater(), null);
        mvcView.registerResourceSelectorListener(this);
        mvcView.registerResourceViewerListener(this);
        mvcView.setActionBar(getSupportActionBar());
        mvcView.setActionBarTitle(getString(R.string.manageProjectsClientsTitle));
        fetchResourceSelectorItems();

        setContentView(mvcView.getRootView());
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

        if (pressedObject instanceof Project){
            target = OverviewProjectController.class;
            poststamp = Poststamp.PROJECT;
        } else if (pressedObject instanceof Client) {
            target = OverviewClientController.class;
            poststamp = Poststamp.CLIENT;
        }

        if(target != null && poststamp != null && parcel != null) {
            Intent intent = new Intent(this, target);
            intent.putExtra(poststamp, parcel);
            createAndLaunchNewActivity(intent);
        }
    }

    @Override
    public void OnSpinnerResourceSelected(int position) {
        switch(position) {
            case 0: /*Projects, fetch projects and update ResourceViewer*/
                mvcView.setResourceViewerAdapter(mockProjectListAndAdapter());
                break;
            case 1: /*Clients, fetch clients and update ResourceViewer*/
                mvcView.setResourceViewerAdapter(mockClientListAndAdapter());
                break;
            default: break;
        }
    }

    private void fetchResourceSelectorItems(){
        final String[] resourceSelectorItems = getResources().getStringArray(R.array.manageProjectAndClientsSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, resourceSelectorItems);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mvcView.setResourceSelectorAdapter(adapter);
    }

    /**
     * TMP TODO
     */
    private Adapter mockProjectListAndAdapter(){
        List<Project> mockedProjects = new ArrayList<>();

        Project project1 = new Project(0, 1000, new Address("Andeby", 10, 2609, "Lillehammer"));
        Project project2 = new Project(1, 1001, new Address("Andeby", 15, 2609, "Lillehammer"));

        mockedProjects.add(project1);
        mockedProjects.add(project2);

        return new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, mockedProjects);
    }

    /**
     * TMP TODO
     */
    private Adapter mockClientListAndAdapter(){
        List<Client> mockedClients = new ArrayList<>();

        Address address = new Address("Andeby", 10, 1313, "Andeby");
        ContactInformation contactInformation = new ContactInformation("foo@bar.com", 1212121212);
        Client client1 = new Client(0, "Donald", "Duck", contactInformation, address);

        mockedClients.add(client1);

        return new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, mockedClients);
    }

    @Override
    public void createAndLaunchNewActivity(Intent intent) {
        startActivity(intent);
    }
}
