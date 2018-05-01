package com.ntnu.wip.nabl.MVCView.ClientsList;

import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ntnu.wip.nabl.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * View that shows a list of clients.
 * Created by a7med on 29.04.18.
 */
public class ClientListView implements IClientListView {
    private View rootView;
    private ClientListListener listener;

    @BindView(R.id.clientsList) ListView clients;

    /**
     * Constrictor that bind the layout to the view
     * @param inflater
     * @param container
     */
    public ClientListView(LayoutInflater inflater, ViewGroup container) {
        this.rootView = inflater.inflate(R.layout.client_listview, container);
        ButterKnife.bind(this, this.rootView);

        configureListView();
    }

    /**
     * Function to configure the List view and apply Item Clicking
     */
    private void configureListView() {
        clients.setOnItemClickListener((adapterView, view, position, l) -> {
            if(listener != null) {
                listener.clientSelected(adapterView.getAdapter().getItem(position));
            }
        });
    }

    @Override
    public View getRootView() {
        return this.rootView;
    }

    @Override
    public void setActionBar(ActionBar actionbar) {
        // Implemented in LoggingController
    }

    @Override
    public void setActionBarTitle(String title) {
        // Implemented in LoggingController
    }

    @Override
    public void registerListener(ClientListListener listener) {
        this.listener = listener;
    }

    /**
     * Function to update the ListView
     * @param adapter
     */
    @Override
    public void setResourceViewerAdapter(Adapter adapter) {
        this.clients.setAdapter((ListAdapter) adapter);
    }
}
