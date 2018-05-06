package com.ntnu.wip.nabl.MVCView.ProjectClientSelector;

import android.widget.Adapter;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

/**
 * Created by markusja on 4/11/18.
 */

/**
 * ProjectClientSelectorView method contract
 */
public interface IProjectClientSelectorView extends IAbstractMvcView {
    /**
     * Methods this can invoke in a listener
     */
    interface ResourceListener {
        /**
         * Resource selected
         * @param pressedObject Object
         */
        void resourceSelected(Object pressedObject);
        void OnSpinnerResourceSelected(int position);

        /**
         * Register button pressed
         */
        void registerPressed();
    }

    /**
     * Register listener
     * @param listener ResourceListener
     */
    void registerResourceListener(ResourceListener listener);

    /**
     * Set adapter in Resource viewer List
     * @param adapter Adapter
     */
    void setResourceViewerAdapter(Adapter adapter);

    /**
     * Set adapter in resource selector List
     * @param adapter Adapter
     */
    void setResourceSelectorAdapter(Adapter adapter);

    /**
     * Get selected spinner item position
     * @return int
     */
    int getSpinnerSelected();

    /**
     * Set text to be displayed in an empty list
     * @param text String
     */
    void setEmptyIdentifier(String text);
}
