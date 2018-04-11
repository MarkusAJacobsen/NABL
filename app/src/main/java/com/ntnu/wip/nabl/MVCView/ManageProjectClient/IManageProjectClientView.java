package com.ntnu.wip.nabl.MVCView.ManageProjectClient;

import android.widget.Adapter;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

/**
 * Created by markusja on 4/11/18.
 */

public interface IManageProjectClientView extends IAbstractMvcView {
    interface ResourceViewerListener {
        void resourceSelected(Object pressedObject);
    }

    interface ResourceSelectorListener {
        void OnSpinnerResourceSelected(int position);
    }

    void registerResourceSelectorListener(ResourceSelectorListener listener);
    void registerResourceViewerListener(ResourceViewerListener listener);
    void setResourceViewerAdapter(Adapter adapter);
    void setResourceSelectorAdapter(Adapter adapter);

}
