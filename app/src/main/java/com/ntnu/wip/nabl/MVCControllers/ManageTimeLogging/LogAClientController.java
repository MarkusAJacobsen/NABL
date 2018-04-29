package com.ntnu.wip.nabl.MVCControllers.ManageTimeLogging;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntnu.wip.nabl.MVCView.ClientsList.ClientListView;
import com.ntnu.wip.nabl.MVCView.ClientsList.IClientListView;
import com.ntnu.wip.nabl.Models.Client;

import java.util.List;

/**
 * A fragment that shows a list of all registered clients
 * Created by a7med on 29.04.18.
 */
public class LogAClientController extends Fragment implements IClientListView.ClientListListener {
    private ClientListView mvcView;
    private List<Client> clients;

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

        return this.mvcView.getRootView();
    }
}
