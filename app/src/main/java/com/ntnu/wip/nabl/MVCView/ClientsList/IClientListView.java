package com.ntnu.wip.nabl.MVCView.ClientsList;

import android.widget.Adapter;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

/**
 * Interface connect between ClientListView (View) and it's (controller) LogAClientController
 * Created by a7med on 29.04.18.
 */
public interface IClientListView extends IAbstractMvcView {
    /**
     * Methods which this can invoke in Listeners
     */
    interface ClientListListener {
        /**
         * Client object selected
         * @param item Object
         */
        void clientSelected(Object item);
    }

    /**
     * Register listener
     * @param listener ClientListListener
     */
    void registerListener(ClientListListener listener);

    /**
     * Set resource list adapter
     * @param adapter Adapter
     */
    void setResourceViewerAdapter(Adapter adapter);
}
