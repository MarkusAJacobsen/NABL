package com.ntnu.wip.nabl.MVCView.ProjectClientSelector;

import android.widget.Adapter;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

/**
 * Created by markusja on 4/11/18.
 */

public interface IProjectClientSelectorView extends IAbstractMvcView {
    interface ResourceListener {
        void resourceSelected(Object pressedObject);
        void OnSpinnerResourceSelected(int position);
        void registerPressed();
    }

    void registerResourceListener(ResourceListener listener);
    void setResourceViewerAdapter(Adapter adapter);
    void setResourceSelectorAdapter(Adapter adapter);
    int getSpinnerSelected();
    void setEmptyIdentifier(String text);
}
