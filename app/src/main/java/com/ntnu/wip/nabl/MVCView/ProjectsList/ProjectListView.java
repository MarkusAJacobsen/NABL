package com.ntnu.wip.nabl.MVCView.ProjectsList;

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
 * View that shows a list of projects.
 * Created by a7med on 29.04.18.
 */
public class ProjectListView implements IProjectListView {
    private View rootView;
    private ProjectListListener listener;

    @BindView(R.id.projectsList) ListView projects;

    /**
     * Constrictor that bind the layout to the view
     * @param inflater
     * @param container
     */
    public ProjectListView(LayoutInflater inflater, ViewGroup container) {
        this.rootView = inflater.inflate(R.layout.project_listview, container);
        ButterKnife.bind(this, this.rootView);

        configureListView();
    }

    /**
     * Function to configure the List view and apply Item Clicking
     */
    private void configureListView() {
        this.projects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO => Do something to the item.
            }
        });
    }


    @Override
    public View getRootView() {
        return this.rootView;
    }

    @Override
    public void setActionBar(ActionBar actionbar) {
        // Implemented in LogAProjectController
    }

    @Override
    public void setActionBarTitle(String title) {
        // Implemented in LogAProjectController
    }

    @Override
    public void registerListener(ProjectListListener listener) {
        this.listener = listener;
    }

    /**
     * Function to update the ListView
     * @param adapter
     */
    @Override
    public void setResourceViewerAdapter(Adapter adapter) {
        this.projects.setAdapter((ListAdapter) adapter);
    }
}
