package com.ntnu.wip.nabl.MVCView.ProjectsList;

import android.widget.Adapter;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

/**
 * Interface connect between ProjectListView (View) and it's (controller) LogAProjectController
 * Created by a7med on 29.04.18.
 */
public interface IProjectListView extends IAbstractMvcView{
    // Functions to implement in the controller
    interface ProjectListListener {
        void projectSelected(Object item);
    }

    // Functions to implement in the View
    void registerListener(ProjectListListener listener);
    void setResourceViewerAdapter(Adapter adapter);
}
