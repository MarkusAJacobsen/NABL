package com.ntnu.wip.nabl.MVCView.Register;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

public interface IRegisterView extends IAbstractMvcView {
    /**
     * Methods which this can invoke in Listeners
     */
    interface ToggleListener {
        /**
         * Project toggle button pressed
         */
        void projectPressed();

        /**
         * Client toggle button pressed
         */
        void clientPressed();
    }

    /**
     * Register listener
     * @param listener ToggleListener
     */
    void registerListener(ToggleListener listener);

    /**
     * Set project button text
     * @param text String
     */
    void setProjectText(String text);

    /**
     * Set client button text
     * @param text String
     */
    void setClientText(String text);
}
