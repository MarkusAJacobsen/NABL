package com.ntnu.wip.nabl.MVCView.ProjectClientSelector;

import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.ntnu.wip.nabl.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by markusja on 4/11/18.
 */

public class ProjectClientSelector implements IProjectClientSelectorView {
    private View rootView;
    @BindView(R.id.resourceType) Spinner resourceSelector;
    @BindView(R.id.resource) ListView resourceViewer;
    private ResourceSelectorListener resourceSelectorListener;
    private ResourceViewerListener resourceViewerListener;
    private ActionBar actionBar;

    public ProjectClientSelector(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.manage_projects_and_clients, container);
        ButterKnife.bind(this, rootView);
        initialize();
    }

    private void initialize(){
        initializeSpinnerActions();
        initializeListViewActions();
    }

    private void initializeSpinnerActions(){
        resourceSelector.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(resourceSelectorListener != null) {
                    resourceSelectorListener.OnSpinnerResourceSelected(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initializeListViewActions(){
        resourceViewer.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(resourceViewerListener != null) {
                    resourceViewerListener.resourceSelected(adapterView.getAdapter().getItem(position));
                }
            }
        });
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void setActionBar(ActionBar actionbar) {
        this.actionBar = actionbar;
        this.actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setActionBarTitle(String title) {
        actionBar.setTitle(title);
    }

    @Override
    public void registerResourceSelectorListener(ResourceSelectorListener listener) {
        resourceSelectorListener = listener;
    }

    @Override
    public void registerResourceViewerListener(ResourceViewerListener listener) {
        resourceViewerListener = listener;
    }

    @Override
    public void setResourceViewerAdapter(Adapter adapter) {
        resourceViewer.setAdapter((ArrayAdapter) adapter);
    }

    @Override
    public void setResourceSelectorAdapter(Adapter adapter) {
        resourceSelector.setAdapter((ArrayAdapter) adapter);
    }
}
