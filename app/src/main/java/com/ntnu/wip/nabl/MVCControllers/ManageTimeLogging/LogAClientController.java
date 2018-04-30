package com.ntnu.wip.nabl.MVCControllers.ManageTimeLogging;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.ntnu.wip.nabl.MVCView.ClientsList.ClientListView;
import com.ntnu.wip.nabl.MVCView.ClientsList.IClientListView;
import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.Observers.Observer;
import com.ntnu.wip.nabl.Observers.Observers.ClientCollectionObserver;
import com.ntnu.wip.nabl.Observers.Observers.ObserverFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment that shows a list of all registered clients
 * Created by a7med on 29.04.18.
 */
public class LogAClientController extends Fragment implements IClientListView.ClientListListener {
    private ClientListView mvcView;
    private List<Client> clients = new ArrayList<>();

    /**
     * Android Fragment life cycle function, runs when the view is created
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return Fragment View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mvcView = new ClientListView(inflater, null);
        this.mvcView.registerListener(this);

        fetchClients();

        return this.mvcView.getRootView();
    }

    /**
     * Function to fetch Clients data from FireBase
     */
    private void fetchClients() {
        this.clients.clear();
        FireStoreClient client = new FireStoreClient(getContext());
        client.getAllClients();

        Observer observer = ObserverFactory.create(ObserverFactory.CLIENT_COLLECTION);
        observer.setSubject(client);
        observer.setOnUpdateListener(clients -> {
            this.clients = (List) clients;
            Adapter adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_list_item_1, this.clients);
            mvcView.setResourceViewerAdapter(adapter);
        });
    }
}
