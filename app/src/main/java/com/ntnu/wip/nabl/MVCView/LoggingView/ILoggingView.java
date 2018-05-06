package com.ntnu.wip.nabl.MVCView.LoggingView;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

/**
 * Interface connect between LoggingView (View) and it's (controller) LoggingController
 * Created by a7med on 29.04.18.
 */
public interface ILoggingView extends IAbstractMvcView {
    /**
     * Methods which this can invoke in Listeners
     */
    interface LoggingViewListener {
        /**
         * Project button pressed
         */
        void projectPressed();

        /**
         * Client button pressed
         */
        void clientPressed();
    }

    /**
     * Register a listener
     * Will enable an implementing class to invoke
     * functions in the listener.
     * @param listener CLoggingViewListener
     */
    void registerLoggingListener(LoggingViewListener listener);

    /**
     * Set Project text
     * @param text String
     */
    void setProjectText(String text);

    /**
     * Set client text
     * @param text String
     */
    void setClientText(String text);

    void updateTextViewTitle(String text);
}
