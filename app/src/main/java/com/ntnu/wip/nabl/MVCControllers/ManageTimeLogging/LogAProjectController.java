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

import com.ntnu.wip.nabl.Exceptions.CompanyNotFoundException;
import com.ntnu.wip.nabl.MVCControllers.ManageTimeLogging.NewInputController.LoggingInputController;
import com.ntnu.wip.nabl.MVCView.ProjectsList.IProjectListView;
import com.ntnu.wip.nabl.MVCView.ProjectsList.ProjectListView;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.Observers.Observer;
import com.ntnu.wip.nabl.Observers.Observers.ObserverFactory;
import com.ntnu.wip.nabl.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment that shows a list of all registered projects.
 * Created by a7med on 29.04.18.
 */
public class LogAProjectController extends Fragment implements IProjectListView.ProjectListListener {
    private ProjectListView mvcView;
    private List<Project> projects = new ArrayList<>();
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
        mvcView = new ProjectListView(inflater, null);
        mvcView.registerListener(this);

        try {
            fetchProjects();
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
    private void logForProject(Project project) {
        Intent loggingIntent = new Intent(this.context, LoggingInputController.class);
        loggingIntent.putExtra(getString(R.string.type), getString(R.string.project));
        loggingIntent.putExtra(getString(R.string.id), project.getId());
        startActivity(loggingIntent);
    }

    /**
     * Function to fetch projects from FireBase
     */
    private void fetchProjects() throws CompanyNotFoundException {
        projects.clear();
        FireStoreClient client = new FireStoreClient(getContext());
        client.getAllProjects();

        Observer observer = ObserverFactory.create(ObserverFactory.PROJECT_COLLECTION);
        observer.setSubject(client);
        observer.setOnUpdateListener(receivedProjects -> {

            if(context != null) {
                this.projects = (List) receivedProjects;
                Adapter adapter = new ArrayAdapter<>(context,
                        android.R.layout.simple_list_item_1, this.projects);
                mvcView.setResourceViewerAdapter(adapter);
            }
        });
    }

    /**
     * Function that react to a click on a project element from the ListView in View
     * @param chosenProject
     */
    @Override
    public void projectSelected(Object chosenProject) {
        logForProject((Project) chosenProject);
    }
}
