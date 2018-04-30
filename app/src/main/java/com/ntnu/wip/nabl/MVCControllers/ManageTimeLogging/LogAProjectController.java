package com.ntnu.wip.nabl.MVCControllers.ManageTimeLogging;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.ntnu.wip.nabl.MVCView.ProjectsList.IProjectListView;
import com.ntnu.wip.nabl.MVCView.ProjectsList.ProjectListView;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.Observers.Observers.ProjectCollectionObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment that shows a list of all registered projects.
 * Created by a7med on 29.04.18.
 */
public class LogAProjectController extends Fragment implements IProjectListView.ProjectListListener {
    private ProjectListView mvcView;
    private List<Project> projects = new ArrayList<>();

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
        this.mvcView = new ProjectListView(inflater, null);
        this.mvcView.registerListener(this);

        fetchProjects();

        return this.mvcView.getRootView();
    }

    /**
     * Function to fetch projects from FireBase
     */
    private void fetchProjects() {
        this.projects.clear();
        FireStoreClient client = new FireStoreClient(getContext());
        client.getAllProjects();

        new ProjectCollectionObserver(client).setOnUpdateListener(projects -> {
            this.projects = (List) projects;
            Adapter adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_list_item_1, this.projects);
            mvcView.setResourceViewerAdapter(adapter);
        });
    }
}
