package com.ntnu.wip.nabl.MVCControllers.ManageTimeLogging;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ntnu.wip.nabl.Authentication.FirestoreImpl.FirestoreAuthentication;
import com.ntnu.wip.nabl.Exceptions.CompanyNotFoundException;
import com.ntnu.wip.nabl.MVCControllers.ManageTimeLogging.NewInputController.LoggingInputController;
import com.ntnu.wip.nabl.MVCView.ClientsList.ClientListView;
import com.ntnu.wip.nabl.MVCView.ClientsList.IClientListView;
import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.Observers.Observer;
import com.ntnu.wip.nabl.Observers.Observers.ClientCollectionObserver;
import com.ntnu.wip.nabl.Observers.Observers.ObserverFactory;
import com.ntnu.wip.nabl.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment that shows a list of all registered clients
 * Created by a7med on 29.04.18.
 */
public class LogAClientController extends Fragment implements IClientListView.ClientListListener {
    private ClientListView mvcView;
    private List<Client> clients = new ArrayList<>();
    private Context context = null;

    /**
     * Android Fragment life cycle function, runs when the view is created
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mvcView = new ClientListView(inflater, null);
        mvcView.registerListener(this);

        try {
            fetchClients();
        } catch (CompanyNotFoundException e) {
            Toast.makeText(getContext(), (R.string.workspaceNotSat), Toast.LENGTH_SHORT).show();
        }

        return mvcView.getRootView();
    }

    /**
     * Ensure that we have context via Parent activity before it is used in the fragment
     * @param context Context
     */
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }

    /**
     * Function to go to Logging View to log a project
     */
    private void logForClient(Client client) {
        Intent loggingIntent = new Intent(this.context, LoggingInputController.class);
        loggingIntent.putExtra(getString(R.string.type), getString(R.string.client));
        loggingIntent.putExtra(getString(R.string.id), client.getId());
        loggingIntent.putExtra(getString(R.string.userID), getUserID());
        startActivity(loggingIntent);
    }

    /**
     * Function to get current user ID
     * @return String
     */
    private String getUserID() {
        return new FirestoreAuthentication().getUId();
    }

    /**
     * Function to fetch Clients data from FireBase
     */
    private void fetchClients() throws CompanyNotFoundException {
        clients.clear();
        FireStoreClient client = new FireStoreClient(getContext());
        client.getAllClients();

        Observer observer = ObserverFactory.create(ObserverFactory.CLIENT_COLLECTION);
        observer.setSubject(client);
        observer.setOnUpdateListener(receivedClients -> {

            if(context != null) {
                clients = (List) receivedClients;

                Adapter adapter = new ArrayAdapter<>(context,
                        android.R.layout.simple_list_item_1, this.clients);
                mvcView.setResourceViewerAdapter(adapter);
            }
        });
    }

    /**
     * Function that react to a click on a client element from the ListView in View
     * @param chosenClient Object
     */
    @Override
    public void clientSelected(Object chosenClient) {
        logForClient((Client) chosenClient);
    }
}
