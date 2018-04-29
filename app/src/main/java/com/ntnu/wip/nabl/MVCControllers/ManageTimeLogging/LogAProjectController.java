package com.ntnu.wip.nabl.MVCControllers.ManageTimeLogging;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntnu.wip.nabl.MVCView.ProjectsList.IProjectListView;
import com.ntnu.wip.nabl.MVCView.ProjectsList.ProjectListView;
import com.ntnu.wip.nabl.Models.Project;

import java.util.List;

/**
 * A fragment that shows a list of all registered projects.
 * Created by a7med on 29.04.18.
 */
public class LogAProjectController extends Fragment implements IProjectListView.ProjectListListener {
    private ProjectListView mvcView;
    private List<Project> projects;

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

        return this.mvcView.getRootView();
    }
}
